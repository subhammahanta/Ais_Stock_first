package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.AllCommandRequestResponse;
import com.watsoo.device.management.dto.CommandRequestDTO;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.service.DeviceCommandRequestService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class DeviceCommandRequestController {

	@Autowired
	private DeviceCommandRequestService deviceCommandRequestService;

	@PostMapping("command/request")
	public ResponseEntity<?> commandRequest(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		return new ResponseEntity<>(deviceCommandRequestService.commandRequest(deviceCommandDTO), HttpStatus.OK);
	}

	@PostMapping(value = "all/request")
	public ResponseEntity<?> getAllCommandRequest(@RequestParam(required = true, defaultValue = "0") int pageNo,
			@RequestParam(required = true, defaultValue = "0") int pageSize, @RequestBody CommandRequestDTO dto) {
		AllCommandRequestResponse response = deviceCommandRequestService.getAllCommandRequest(pageNo,
				pageSize, dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("get/request/by/id")
	public ResponseEntity<?> getCommandRequestById(@RequestBody CommandRequestDTO dto) {
		return new ResponseEntity<>(deviceCommandRequestService.getCommandRequestById(dto), HttpStatus.OK);
	}
	
	@PostMapping("revert/command/request")
	public ResponseEntity<?> revertCommandRequest(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		return new ResponseEntity<>(deviceCommandRequestService.revertCommandRequest(deviceCommandDTO), HttpStatus.OK);
	}
}
