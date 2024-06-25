package com.watsoo.device.management.controller;
import com.watsoo.device.management.dto.*;

import com.watsoo.device.management.model.DeviceRenewalRequest;
import com.watsoo.device.management.service.DeviceRenewalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @PostMapping  ("/get/All/device_renewal_request")
 public ResponseEntity<?> getDeviceRenewalData(@RequestBody DeviceRenewalPaginationDTO deviceRenewalPaginationDTO){




          PaginationV2<?> data= deviceRenewalRequestService.findByCriteria(deviceRenewalPaginationDTO.getSearch(),
                  deviceRenewalPaginationDTO.getFromDate(),deviceRenewalPaginationDTO.getToDate(),
                  deviceRenewalPaginationDTO.getPageNo(),deviceRenewalPaginationDTO.getPageSize());




        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("/get/All/device_renewal_request/{reqCode}")
  ResponseEntity<?>   getAllRenewalDeviceData(@PathVariable("reqCode")String reqCode){

         PaginationV2<?> paginationV2=   this.deviceRenewalRequestService.getAllRenewalDeviceByRequestCode(reqCode);
            return new ResponseEntity<>(paginationV2,HttpStatus.OK);
    }


}
