package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.UnBoxDeviceDto;
import com.watsoo.device.management.service.UnBoxService;

@RestController
@RequestMapping(value = "api")
public class UnBoxController {
	
	@Autowired
	private UnBoxService unBoxService;
	
	@PostMapping("/unbox/device")
	public ResponseEntity<?> unBoxDevice(@RequestParam Long boxId){
		return new ResponseEntity<>(unBoxService.unBoxDevice(boxId),HttpStatus.OK);
	}
}