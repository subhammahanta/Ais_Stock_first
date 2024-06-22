package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.service.AisSyncService;

@RestController
@RequestMapping("api/ais/sync")
public class AisSyncController {
	
	@Autowired
	private AisSyncService aisSyncService;
	
	@Autowired
	private AisSyncService asiAisSyncService;
	
	
	
	@PostMapping("/get/All")
	public ResponseEntity<?> getAllDevice(@RequestBody GenericRequestBody requestDTO) {
		return new ResponseEntity<>(aisSyncService.getAllDevice(requestDTO), HttpStatus.OK);
	}
	
	@PostMapping("/update/device/expiry")
	public ResponseEntity<?> updateDeviceExpiry(@RequestBody GenericRequestBody requestDTO) {
		return new ResponseEntity<>(aisSyncService.updateDeviceExpiry(requestDTO), HttpStatus.OK);
	}
	
	@PostMapping("/activate/device")
	public ResponseEntity<?> activateDevice(@RequestBody GenericRequestBody requestDTO) {
		return new ResponseEntity<>(aisSyncService.activateDevice(requestDTO), HttpStatus.OK);
	}
}
