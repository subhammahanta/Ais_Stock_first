package com.watsoo.device.management.serviceImpl;

import com.watsoo.device.management.dto.DeviceRenewal;
import com.watsoo.device.management.dto.DeviceRenewalRequestDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.*;
import com.watsoo.device.management.repository.*;
import com.watsoo.device.management.service.DeviceRenewalRequestService;
import com.watsoo.device.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    @Override
    public void saveDeviceRenewalRequest(DeviceRenewalRequestDTO deviceRenewalRequestDTO) {

        //To fetch total no of request code present in DB
          int total_request_code=deviceRenewalRequestRepository.countTotalItems();

          String requestCode=generateRequestCode(total_request_code);

         Optional<User> user=   userRepository.findById(deviceRenewalRequestDTO.getUserId());

        DeviceRenewalRequest deviceRenewalRequest=new DeviceRenewalRequest();
         if(user.isPresent()){

             deviceRenewalRequest.setCreatedBy(deviceRenewalRequestDTO.getUserId());
         }
         else {
             throw new RuntimeException("User Not Found");
         }

        deviceRenewalRequest.setReqCode(requestCode);
         deviceRenewalRequest.setCreatedAt(new Date());

        //Saving the DeviceRenewalRequest into DB
        DeviceRenewalRequest request_id = deviceRenewalRequestRepository.save(deviceRenewalRequest);

          Long requestId=request_id.getId();
          List<DeviceRenewal> deviceRenewalsList= deviceRenewalRequestDTO.getDeviceRenewalList();

           deviceRenewalsList.stream().forEach(item->{

                 String iccidNo=item.getIccidNo();

           Optional<Device> deviceOptional= deviceRepository.findByIccidNo(iccidNo);

               RenewalDevice renewalDevice=new RenewalDevice();

             if(deviceOptional.isPresent()){
                 Device device=deviceOptional.get();
                 renewalDevice.setDeviceId(device.getId());
                 renewalDevice.setImeiNo(device.getImeiNo());
                 renewalDevice.setIccidNo(device.getIccidNo());
                 renewalDevice.setOldExpiryDate(device.getSim2ExpiryDate());

                 SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                 try {
                     Date date = inputFormat.parse(item.getDate());
                 renewalDevice.setNewExpiryDate(date);
                 renewalDevice.setRequestId(requestId);
                  renewalDeviceRepository.save(renewalDevice);

                 } catch (ParseException e) {
                     throw new RuntimeException(e);
                 }
             }
             else {

                 throw  new RuntimeException("Iccid not found");

             }

           });
        System.out.println("Done..!!");
    }

    private String generateRequestCode(int totalRequestCode) {

          final String template="R00";

          totalRequestCode+=1;
          return  template+totalRequestCode;
    }
}