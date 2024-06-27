package com.watsoo.device.management.serviceImpl;

import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.*;
import com.watsoo.device.management.exception.ResourceNotFoundException;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceRenewalRequest;
import com.watsoo.device.management.model.RenewalDevice;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.repository.*;
import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeviceRenewalRequestServiceImpl implements DeviceRenewalRequestService {

    Logger logger = LoggerFactory.getLogger(DeviceRenewalRequestServiceImpl.class);

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

    private static DeviceRenewalSavedDataResponse deviceRenewalSavedDataResponse = null;


    int iccidNotFoundCount = 0;

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
        List<DeviceRenewalSavedDataResponse> deviceRenewalSavedDataResponses = new ArrayList<>();
        List<DeviceRenewalSavedDataResponse> deviceRenewalUnSavedDataResponses = new ArrayList<>();

        int deviceRenewalListSize = deviceRenewalsList.size();

        this.iccidNotFoundCount = 0;
        deviceRenewalsList.stream().forEach(item -> {

            String iccidNo = item.getIccidNo();

            deviceRenewalSavedDataResponse = new DeviceRenewalSavedDataResponse();

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
                    if (item.getDate() != null) {
                        Date date = inputFormat.parse(item.getDate());
                        renewalDevice.setNewExpiryDate(date);

                    } else {
                        renewalDevice.setNewExpiryDate(null);
                    }
                    DeviceRenewalRequest savedDeviceRenewalObject = deviceRenewalRequestRepository.save(deviceRenewalRequest);
                    renewalDevice.setDeviceRenewalRequest(savedDeviceRenewalObject);
                    RenewalDevice renewalDevice1 = renewalDeviceRepository.save(renewalDevice);
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

                iccidNotFoundCount = iccidNotFoundCount + 1;
                //throw new ResourceNotFoundException("ICCID not found");

            }

        });

        if (iccidNotFoundCount == 0) {
            return new Response<>(HttpStatus.OK.value(), deviceRenewalSavedDataResponses, "Updated  Successfully", requestCode);
        } else if (iccidNotFoundCount == deviceRenewalListSize) {
            System.out.println("**&(*&*&*&*&*&ICCIDNOTFOUNDCOUNT= " + iccidNotFoundCount);

            return new Response<>(HttpStatus.NOT_FOUND.value(), deviceRenewalSavedDataResponses, "No Such  ICCID's  FOUND", requestCode);
        } else if (iccidNotFoundCount > 0) {
            System.out.println("&^&^&^&^&^ICCIDNOTFOUNDCOUNT= " + iccidNotFoundCount);

            return new Response<>(HttpStatus.OK.value(), deviceRenewalSavedDataResponses, "Updated Successfully with some unsucessful attempts (No such  Iccid Found)", requestCode);

        } else {
            return null;
        }

    }


    private String generateRequestCode() {

        String businessPrefix = "REQ";
        UUID uuid = UUID.randomUUID();
        String uniqueRequestCode = businessPrefix + "-" + uuid.toString();
        return uniqueRequestCode;
    }

    @Override
    public PaginationV2<?> getDeviceRenewalRequest(GenericRequestBody genericRequestBody) {

        PageRequest pageRequest = PageRequest.of(genericRequestBody.getPageNo(), genericRequestBody.getPageSize(), Sort.Direction.DESC, "created_at");
        PaginationV2 paginationV2 = new PaginationV2();

        Page<DeviceRenewalRequest> deviceRenewalRequestsPaging = null;

        if (genericRequestBody.getSearch() != null && !genericRequestBody.getSearch().isEmpty() && !genericRequestBody.getSearch().equals("")) {

            if(genericRequestBody.getSearch().matches("^[0-9]+")){
                Page<RenewalDevice> collect = this.renewalDeviceRepository
                        .findAllByImeiNo(genericRequestBody.getSearch(),pageRequest);

                List<DeviceRenewalResponseDTO> allById =
                        collect
                                .stream()
                                .map(renew ->
                                {
                                    if (renew.getDeviceRenewalRequest().getReqCode() != null && !renew.getDeviceRenewalRequest().getReqCode().equals("")) {
                                        Optional<DeviceRenewalRequest> byId = this.deviceRenewalRequestRepository.findByReqCode(renew.getDeviceRenewalRequest().getReqCode());

                                        logger.info("Data : "+ byId.get().getId() +" "+byId.get().getReqCode()+" "+byId.get().getCreatedBy()+" "+byId.get().getCreatedAt());
                                        if (byId.isPresent()) {
                                            Optional<User> user = this.userRepository.findById(byId.get().getCreatedBy());
                                            if(user.isPresent()){
                                                DeviceRenewalResponseDTO deviceRenewalResponse = new DeviceRenewalResponseDTO();
                                                deviceRenewalResponse.setRequestCode(byId.get().getReqCode());
                                                deviceRenewalResponse.setRequestDate(byId.get().getCreatedAt());
                                                deviceRenewalResponse.setCreatedBy(user.get().getName());
                                                deviceRenewalResponse.setDevices(new ArrayList<>());
                                                deviceRenewalResponse.setTotalDevices(this.renewalDeviceRepository.deviceCountForRequest(byId.get().getId()));
                                                return deviceRenewalResponse;
                                            }
                                        }
                                    }
                                    return  null;
                                })
                                .collect(Collectors.toList());


                paginationV2.setPageSize(genericRequestBody.getPageSize());
                paginationV2.setTotalItems(collect.getTotalElements());
                paginationV2.setItems(allById);

                return paginationV2;
            }else{
                deviceRenewalRequestsPaging = this.deviceRenewalRequestRepository.findByReqCode(genericRequestBody.getSearch(), pageRequest);
            }
        } else if (genericRequestBody.getFromDate() == 0 && genericRequestBody.getToDate() == 0
                && genericRequestBody.getSearch().equals("") && genericRequestBody.getSearch().isEmpty()) {

            deviceRenewalRequestsPaging = this.deviceRenewalRequestRepository.findAll(pageRequest);

        } else if ((genericRequestBody.getFromDate() != null && genericRequestBody.getToDate() != null) &&
                genericRequestBody.getPageSize() != 0) {

            logger.info("From Date Receive : " + genericRequestBody.getFromDate());
            logger.info("To Date Receive : " + genericRequestBody.getToDate());

            SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD);
            String fromDate = sdf.format(new Date(genericRequestBody.getFromDate()));
            String toDate = sdf.format(new Date(genericRequestBody.getToDate()));
            logger.info("After converting From Date to UTC : " + new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(new Date()));
            logger.info("After converting To Date to UTC  :" + toDate);

            try {

                deviceRenewalRequestsPaging = this.deviceRenewalRequestRepository
                        .findAllCreatedAtBetween(sdf.parse(fromDate), sdf.parse(toDate), pageRequest);

            } catch (ParseException e) {
                throw new RuntimeException("Date Parsing Exception");
            }
        }

        if (deviceRenewalRequestsPaging != null && !deviceRenewalRequestsPaging.isEmpty()) {

            List<DeviceRenewalResponseDTO> deviceRenewalResponse = deviceRenewalRequestsPaging
                    .get()
                    .map(object -> {
                        DeviceRenewalResponseDTO deviceRenewalResponseDTO = new DeviceRenewalResponseDTO();
                        try {
                            Optional<User> user = userRepository.findById(object.getCreatedBy());
                            if (user.isPresent()) {
                                deviceRenewalResponseDTO.setRequestCode(object.getReqCode());
                                deviceRenewalResponseDTO.setCreatedBy(user.get().getName());
                                deviceRenewalResponseDTO.setRequestDate(object.getCreatedAt());
                                deviceRenewalResponseDTO.setDevices(new ArrayList<>());
                                deviceRenewalResponseDTO.setTotalDevices(this.renewalDeviceRepository.deviceCountForRequest(object.getId()));
                            }
                        } catch (NoSuchElementException exception) {
                            throw new ResourceNotFoundException("User Not Found With Give ID");
                        }
                        return deviceRenewalResponseDTO;
                    })
                    .collect(Collectors.toList());

            paginationV2.setTotalItems(deviceRenewalRequestsPaging.getTotalElements());
            paginationV2.setPageSize(genericRequestBody.getPageSize());
            paginationV2.setItems(deviceRenewalResponse);
        } else {
            paginationV2.setItems(new ArrayList<>());
        }

        return paginationV2;
    }

    @Override
    public PaginationV2<?> getDeviceRenewalRequest(String reqCode, int pageNo, int pageSize) {


        Optional<DeviceRenewalRequest> byReqCode = null;
        if (reqCode != null && !reqCode.equals("") && !reqCode.isEmpty()) {

            byReqCode = this.deviceRenewalRequestRepository.findByReqCode(reqCode);

        }
        PaginationV2 paginationV2 = new PaginationV2();

        if (byReqCode != null && byReqCode.isPresent()) {

            DeviceRenewalRequest deviceRenewalRequest = byReqCode.get();
            try {

                if (pageSize == 0 && pageNo == 0) {
                    List<RenewalDevice> allByRequestId = this.renewalDeviceRepository.findAllByRequestId(deviceRenewalRequest.getId());
                    paginationV2.setItems(allByRequestId);
                    paginationV2.setTotalItems(allByRequestId.size());
                } else {
                    Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id");
                    Page<RenewalDevice> allByRequestId = this.renewalDeviceRepository.findAllByRequestId(deviceRenewalRequest.getId(), pageRequest);
                    paginationV2.setItems(allByRequestId.getContent());
                    paginationV2.setTotalItems(allByRequestId.getTotalElements());
                }
                paginationV2.setPageSize(pageSize);
            } catch (NoSuchElementException e) {
                throw new ResourceNotFoundException("No Request Present with Request Code : " + reqCode);
            }
        } else {
            paginationV2.setItems(new ArrayList<>());
        }
        return paginationV2;
    }

}