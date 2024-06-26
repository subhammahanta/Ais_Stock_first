package com.watsoo.device.management.controller;
import com.watsoo.device.management.dto.*;

import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping(path = "/get/All/device_renewal_request/")
    public ResponseEntity<?> getDeviceRenewalRequest(@RequestBody GenericRequestBody genericRequestBody){

        PaginationV2<?> deviceRenewalRequest = this.deviceRenewalRequestService.getDeviceRenewalRequest(genericRequestBody);

        return new ResponseEntity<>(deviceRenewalRequest, HttpStatus.OK);
    }

    @PostMapping(path = "/get/one/device_renewal_request/{reqCode}")
    public  ResponseEntity<?> getDeviceRenewalRequest(@PathVariable("reqCode")String reqCode,@RequestBody GenericRequestBody genericRequestBody){

        PaginationV2<?> renewalRequest = this.deviceRenewalRequestService.getDeviceRenewalRequest(reqCode,genericRequestBody.getPageNo(),genericRequestBody.getPageSize());

        return ResponseEntity.ok(renewalRequest);
    }

}
