package com.watsoo.device.management.controller;
import com.watsoo.device.management.dto.DeviceRenewalRequestDTO;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping ("/getRenewalDevicesById/{requestId}")
    public Response<?> saveDeviceRenewalData(@PathVariable("requestId") Long requestId){

        System.out.println(requestId);
         Response<?> response= deviceRenewalRequestService.getRenewalDevicesById(requestId);


        return  response;
    }


}
