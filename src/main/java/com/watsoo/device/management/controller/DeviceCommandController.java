package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.CommandTransactionsService;

@CrossOrigin
@RestController
public class DeviceCommandController {

	@Autowired
	private CommandTransactionsService commandTransactionsService;

	@PostMapping(value = "/shootCommand/device")
	public ResponseEntity<?> shootCommand(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		if (deviceCommandDTO.getCommand() != null && deviceCommandDTO.getCommand() != ""
				&& deviceCommandDTO.getImeiNo() != null && deviceCommandDTO.getImeiNo() != ""
				&& deviceCommandDTO.getUserId() != null && deviceCommandDTO.getUserId() > 0
				&& deviceCommandDTO.getUserName() != null && deviceCommandDTO.getUserName() != "") {
			Response<String> response = commandTransactionsService.shootCommand(deviceCommandDTO);
			if (response.getResponseCode() == 200) {
				commandTransactionsService.updateDeviceCommandChangeTrail(deviceCommandDTO);
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
}
