package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.TestedDevice;
import com.watsoo.device.management.service.TestedDeviceService;

@RestController
@RequestMapping("api")
public class TestedDeviceController {
	
	@Autowired
	private TestedDeviceService testedDeviceService;

	@PostMapping("/tested/device/all")
	public ResponseEntity<?> getAllTestedDevice(@RequestBody GenericRequestBody requestDTO) {
		Pagination<List<TestedDevice>>  response= testedDeviceService.getAllTestedDevice(requestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/tested/device/reject")
	public Response<?> rejectTestedDevice(@RequestBody GenericRequestBody requestDTO) {
		Response<Boolean> response= testedDeviceService.rejectTestedDevice(requestDTO);
		return response;
	}
	
	@PostMapping("/tested/device/command/trail")
	public Response<?> getAllCommandResponseByDeviceId(@RequestBody DeviceCommandDTO requestDTO) {
		Response<Object> response= testedDeviceService.getAllCommandResponseByDeviceId(requestDTO);
		return response;
	}
}
