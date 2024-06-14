package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.IssueDeviceDTO;
import com.watsoo.device.management.service.SelectDeviceService;

@RequestMapping("api")
@RestController
public class SelectDeviceController {
	
	@Autowired
	private SelectDeviceService selectDeviceService;
	
	@PostMapping("devices/pack")
	public ResponseEntity<?> packDevices(IssueDeviceDTO selectDeviceDTO) {
		Response<?> response = selectDeviceService.saveData(selectDeviceDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
