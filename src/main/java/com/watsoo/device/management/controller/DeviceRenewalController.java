package com.watsoo.device.management.controller;
import com.watsoo.device.management.dto.*;

import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class DeviceRenewalController {


    @Autowired
    private DeviceRenewalRequestService deviceRenewalRequestService;


    @PostMapping("/save/device_renewal_request")
    public Response<?> saveDeviceRenewalData(@RequestBody DeviceRenewalRequestDTO deviceRenewalRequest){

         Response<?> response= deviceRenewalRequestService.saveDeviceRenewalRequest(deviceRenewalRequest);


        return  response;
    }

    @GetMapping ("/get/All/device_renewal_request")
    public Response<?> getDeviceRenewalData(@RequestBody DeviceRenewalPaginationDTO deviceRenewalPaginationDTO){



         Response<?> response= deviceRenewalRequestService.getRenewalDevices(deviceRenewalPaginationDTO.getPageNo(),deviceRenewalPaginationDTO.getPageSize(),deviceRenewalPaginationDTO.getSearch(),deviceRenewalPaginationDTO.getFromDate(),deviceRenewalPaginationDTO.getToDate());


        return  response;
    }


}
