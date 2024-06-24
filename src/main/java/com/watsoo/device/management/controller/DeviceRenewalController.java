package com.watsoo.device.management.controller;
import com.watsoo.device.management.dto.DeviceRenewalRequestDTO;
import com.watsoo.device.management.dto.DeviceRenewalResponseDTO;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Response;
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
    public Response<?> getDeviceRenewalData(@RequestBody GenericRequestBody genericRequestBody){



         Response<?> response= deviceRenewalRequestService.getRenewalDevices(genericRequestBody.getPageNo(),genericRequestBody.getPageSize());


        return  response;
    }


}
