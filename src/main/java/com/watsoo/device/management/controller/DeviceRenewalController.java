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



}
