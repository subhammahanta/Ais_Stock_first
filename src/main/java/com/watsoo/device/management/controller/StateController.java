package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.service.StateService;

@RestController
@RequestMapping("api")
@CrossOrigin
public class StateController {

	@Autowired
	private StateService stateService;

	@GetMapping("v1/getAll/states")
	public ResponseEntity<?> getAllStates() {
		return new ResponseEntity<>(stateService.getAllStates(), HttpStatus.OK);
	}
	
	@GetMapping("v1/getBy/Id")
	public ResponseEntity<?> getById(@RequestParam Long id) {
		return new ResponseEntity<>(stateService.getById(id), HttpStatus.OK);
	}
}
