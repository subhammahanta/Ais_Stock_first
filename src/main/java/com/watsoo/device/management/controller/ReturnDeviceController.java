package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.ReturnDeviceMaster;
import com.watsoo.device.management.service.ReturnDeviceService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ReturnDeviceController {

	@Autowired
	private ReturnDeviceService returnService;

	@PostMapping("/v1/add/return/request")
	public Response<?> returnDevices(@RequestBody GenericRequestBody dto) {
		return returnService.returnDevices(dto);
	}
	
	@PostMapping("/v1/return/device/request/all")
	public ResponseEntity<?> getAllReturnDeviceRequest(@RequestBody GenericRequestBody request) {
		Pagination<List<ReturnDeviceMaster>>  returnDeviceRequestList= returnService.getAllReturnDeviceRequest(request);
		return new ResponseEntity<>(returnDeviceRequestList, HttpStatus.OK);
	}
	
	@PostMapping("/v1/add/device/return")
	public Response<?> addReturnDevice(@RequestBody GenericRequestBody dto) {
		return returnService.addReturnDevice(dto);
	}
}
