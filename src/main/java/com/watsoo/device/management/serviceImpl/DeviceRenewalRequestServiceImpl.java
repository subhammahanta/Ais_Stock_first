package com.watsoo.device.management.serviceImpl;

import com.watsoo.device.management.dto.*;
import com.watsoo.device.management.model.*;
import com.watsoo.device.management.repository.*;
import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DeviceRenewalRequestServiceImpl implements DeviceRenewalRequestService {
    @Autowired
    private DeviceRenewalRequestRepository deviceRenewalRequestRepository;

    @Autowired
    private RenewalDeviceRepository renewalDeviceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IccidMasterRepository iccidMasterRepository;

    @Autowired
    private UserRepository userRepository;

    private static RenewalDevice renewalDevice;

    private static DeviceRenewalSavedDataResponse deviceRenewalSavedDataResponse=null;


        int iccidNotFoundCount=0;

    @Override
    public Response<?> saveDeviceRenewalRequest(DeviceRenewalRequestDTO deviceRenewalRequestDTO) {

        //To fetch total no of request code present in DB
        int total_request_code = deviceRenewalRequestRepository.countTotalItems();


        String requestCode = generateRequestCode();

        Optional<User> user = userRepository.findById(deviceRenewalRequestDTO.getUserId());

        DeviceRenewalRequest deviceRenewalRequest = new DeviceRenewalRequest();
        if (user.isPresent()) {

            deviceRenewalRequest.setCreatedBy(deviceRenewalRequestDTO.getUserId());
        } else {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "User doesnot exists");
        }

        deviceRenewalRequest.setReqCode(requestCode);
        deviceRenewalRequest.setCreatedAt(new Date());



        //Saving the DeviceRenewalRequest into DB
//        DeviceRenewalRequest  savedDeviceRenewalObject = deviceRenewalRequestRepository.save(deviceRenewalRequest);

        //Long requestId = savedDeviceRenewalObject.getId();

        List<DeviceRenewal> deviceRenewalsList = deviceRenewalRequestDTO.getDeviceRenewalList();
        List<DeviceRenewalSavedDataResponse> deviceRenewalSavedDataResponses=new ArrayList<>();
        List<DeviceRenewalSavedDataResponse> deviceRenewalUnSavedDataResponses=new ArrayList<>();

       int deviceRenewalListSize=deviceRenewalsList.size();

        this.iccidNotFoundCount=0;
        deviceRenewalsList.stream().forEach(item -> {

            String iccidNo = item.getIccidNo();

            deviceRenewalSavedDataResponse=new DeviceRenewalSavedDataResponse();

            Optional<Device> deviceOptional = deviceRepository.findByIccidNo(iccidNo);

             renewalDevice = new RenewalDevice();

            if (deviceOptional.isPresent()) {

                Device device = deviceOptional.get();
                renewalDevice.setDeviceId(device.getId());
                renewalDevice.setImeiNo(device.getImeiNo());
                renewalDevice.setIccidNo(device.getIccidNo());
                renewalDevice.setOldExpiryDate(device.getSim2ExpiryDate());

                deviceRenewalSavedDataResponse.setIccidNo(device.getIccidNo());
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    if(item.getDate()!=null){
                    Date date = inputFormat.parse(item.getDate());
                    renewalDevice.setNewExpiryDate(date);

                    }
                    else{
                        renewalDevice.setNewExpiryDate(null);
                    }
        DeviceRenewalRequest  savedDeviceRenewalObject = deviceRenewalRequestRepository.save(deviceRenewalRequest);
                    renewalDevice.setDeviceRenewalRequest(savedDeviceRenewalObject);
               RenewalDevice renewalDevice1=     renewalDeviceRepository.save(renewalDevice);
                    deviceRenewalSavedDataResponse.setNewExpiryDate(renewalDevice1.getNewExpiryDate());
                    deviceRenewalSavedDataResponse.setUpdated(true);
                    deviceRenewalSavedDataResponses.add(deviceRenewalSavedDataResponse);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //Deleting
                //this.deviceRenewalRequestRepository.delete(savedDeviceRenewalObject);
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = inputFormat.parse(item.getDate());
                deviceRenewalSavedDataResponse.setNewExpiryDate(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                    deviceRenewalSavedDataResponse.setUpdated(false);
                    deviceRenewalSavedDataResponse.setIccidNo(iccidNo);

                    deviceRenewalSavedDataResponses.add(deviceRenewalSavedDataResponse);

                 iccidNotFoundCount=iccidNotFoundCount+1;
             //throw new ResourceNotFoundException("ICCID not found");

            }

        });

        if(iccidNotFoundCount==0) {
            return new Response<>(HttpStatus.OK.value(), deviceRenewalSavedDataResponses, "Updated  Successfully", requestCode);
        }


        else if(iccidNotFoundCount==deviceRenewalListSize){
            System.out.println("**&(*&*&*&*&*&ICCIDNOTFOUNDCOUNT= "+iccidNotFoundCount);

            return new Response<>(HttpStatus.NOT_FOUND.value(), deviceRenewalSavedDataResponses, "No Such  ICCID's  FOUND", requestCode);
        }

        else if(iccidNotFoundCount>0){
            System.out.println("&^&^&^&^&^ICCIDNOTFOUNDCOUNT= "+iccidNotFoundCount);

            return new Response<>(HttpStatus.OK.value(), deviceRenewalSavedDataResponses, "Updated Successfully with some unsucessful attempts (No such  Iccid Found)", requestCode);

        }


        else{
            return  null;
        }

    }




    private String generateRequestCode() {

        String businessPrefix = "REQ";
        UUID uuid = UUID.randomUUID();
        String uniqueRequestCode = businessPrefix + "-" + uuid.toString();
        return  uniqueRequestCode;
    }




}
