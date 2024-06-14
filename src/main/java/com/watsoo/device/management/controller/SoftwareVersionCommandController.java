package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.SoftwareVersionCommandService;

@RestController
@RequestMapping("api")
public class SoftwareVersionCommandController {

	@Autowired
	private SoftwareVersionCommandService versionCommandService;
	
	@GetMapping("v1/all/version-command")
	public ResponseEntity<?> getAllSoftwareVersionCommand() {
		Response<?> response = versionCommandService.getAllSoftwareVersionCommand();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("v1/revert/command")
	public ResponseEntity<?> getRevertCommand() {
		Response<?> response = versionCommandService.getRevertCommand();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
