package com.watsoo.device.management.serviceImpl;

import com.watsoo.device.management.Specifications.DeviceRenewalRequestSpec;
import com.watsoo.device.management.dto.*;
import com.watsoo.device.management.model.*;
import com.watsoo.device.management.repository.*;
import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        DeviceRenewalRequest  savedDeviceRenewalObject = deviceRenewalRequestRepository.save(deviceRenewalRequest);



        List<DeviceRenewal> deviceRenewalsList = deviceRenewalRequestDTO.getDeviceRenewalList();
        List<DeviceRenewalSavedDataResponse> deviceRenewalSavedDataResponses=new ArrayList<>();
//        List<DeviceRenewalSavedDataResponse> deviceRenewalUnSavedDataResponses=new ArrayList<>();

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
                    renewalDevice.setDeviceRenewalRequest(savedDeviceRenewalObject);
               RenewalDevice renewalDevice1=     renewalDeviceRepository.save(renewalDevice);
                    deviceRenewalSavedDataResponse.setNewExpiryDate(renewalDevice1.getNewExpiryDate());
                    deviceRenewalSavedDataResponse.setUpdated(true);
                    deviceRenewalSavedDataResponses.add(deviceRenewalSavedDataResponse);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //For deleting the request Code Generated and saved into the DataBase because no ICCID was Found So Request Code should not be generated
                deviceRenewalRequestRepository.delete(savedDeviceRenewalObject);
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
            //System.out.println("**&(*&*&*&*&*&ICCIDNOTFOUNDCOUNT= "+iccidNotFoundCount);

            return new Response<>(HttpStatus.NOT_FOUND.value(), deviceRenewalSavedDataResponses, "No Such  ICCID's  FOUND", requestCode);
        }

        else if(iccidNotFoundCount>0){
           // System.out.println("&^&^&^&^&^ICCIDNOTFOUNDCOUNT= "+iccidNotFoundCount);

            return new Response<>(HttpStatus.OK.value(), deviceRenewalSavedDataResponses, "Updated Successfully with some unsucessful attempts (No such  Iccid Found)", requestCode);

        }


        else{
            return  null;
        }

    }


    @Override
    public PaginationV2<?> findByCriteria(String search, Date fromDate, Date toDate, Integer pageNo, Integer pageSize) {
        Specification<DeviceRenewalRequest> spec=Specification.where(null);

        Pageable p=PageRequest.of(pageNo,pageSize);

        if( search!=null && !search.isEmpty()){
            spec=spec.and(DeviceRenewalRequestSpec.hasSearch(search));
        }

        if(fromDate!=null || toDate!=null){
            System.out.println("Inside From Date to toDate");
            spec=spec.and(DeviceRenewalRequestSpec.fromDateToDate(fromDate,toDate));
        }

     Page<DeviceRenewalRequest> deviceRenewalRequestPage=    this.deviceRenewalRequestRepository.findAll(spec,p);

        List<DeviceRenewalRequest> deviceRenewalRequestList=deviceRenewalRequestPage.getContent();

        List<RequestRenewalDeviceResponseDTO> requestRenewalDeviceResponseDTOS=new ArrayList<>();


  PaginationV2<List<RequestRenewalDeviceResponseDTO>> requestRenewalDeviceResponseDTOPaginationV2=new PaginationV2<>(pageNo,0,deviceRenewalRequestList.size(),pageSize,requestRenewalDeviceResponseDTOS);

  for(DeviceRenewalRequest items:deviceRenewalRequestList){

      RequestRenewalDeviceResponseDTO requestRenewalDeviceResponseDTO=new RequestRenewalDeviceResponseDTO();
      requestRenewalDeviceResponseDTO.setRequestCode(items.getReqCode());
      requestRenewalDeviceResponseDTO.setRequestDate(items.getCreatedAt());
      requestRenewalDeviceResponseDTO.setCreatedBy(userRepository.findById(items.getCreatedBy()).get().getName());
      requestRenewalDeviceResponseDTO.setTotalDevices(items.getRenewalDevices().size());
      requestRenewalDeviceResponseDTO.setDetails(new ArrayList<>());
      requestRenewalDeviceResponseDTOS.add(requestRenewalDeviceResponseDTO);
  }
        requestRenewalDeviceResponseDTOPaginationV2.setItems(requestRenewalDeviceResponseDTOS);

         return  requestRenewalDeviceResponseDTOPaginationV2;
    }

    @Override
    public PaginationV2<?> getAllRenewalDeviceByRequestCode(String reqCode) {

        DeviceRenewalRequest deviceRenewalRequest=   this.deviceRenewalRequestRepository.findByReqCode(reqCode);


          List<RenewalDevice> renewalDeviceList= deviceRenewalRequest.getRenewalDevices();

          List<DeviceDetailsDto> deviceDetailsDtolist=new ArrayList<>();

          for(RenewalDevice item:renewalDeviceList){
              DeviceDetailsDto deviceDetailsDto=new DeviceDetailsDto();
              deviceDetailsDto.setImeiNo(item.getImeiNo());
              deviceDetailsDto.setIccidNo(item.getIccidNo());
              deviceDetailsDto.setOldExpiryDate(item.getOldExpiryDate());
              deviceDetailsDto.setNewExpiryDate(item.getNewExpiryDate());
              deviceDetailsDto.setDeviceId(item.getDeviceId());
              deviceDetailsDto.setRequestId(item.getDeviceRenewalRequest().getId());
              deviceDetailsDtolist.add(deviceDetailsDto);

          }




        PaginationV2<List<DeviceDetailsDto>> paginationV2=new PaginationV2<>(0,0,0,0,deviceDetailsDtolist);

        return  paginationV2;
    }


    private String generateRequestCode() {

        String businessPrefix = "REQ";
        UUID uuid = UUID.randomUUID();
        String uniqueRequestCode = businessPrefix + "-" + uuid.toString();
        return  uniqueRequestCode;
    }




}
