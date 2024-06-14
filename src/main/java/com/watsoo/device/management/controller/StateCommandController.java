package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateCommandDTO;
import com.watsoo.device.management.service.StateService;

@RestController
@RequestMapping("api")
@CrossOrigin
public class StateCommandController {
	
	@Autowired
	private StateService stateService;

	@PostMapping(value = "/all/state-command")
	public ResponseEntity<?> getStateCommand(@RequestParam(required = true, defaultValue = "0") int pageNo,
			@RequestParam(required = true, defaultValue = "0") int pageSize, @RequestBody StateCommandDTO dto) {
		Pagination<List<StateCommandDTO>> response = stateService.getStateCommand(pageNo, pageSize, dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/add-update-delete/state-command")
	public ResponseEntity<?> addUpdateConfig(@RequestBody StateCommandDTO dto) {
		Response<?> config = stateService.addUpdateConfig(dto);
		return new ResponseEntity<>(config, HttpStatus.OK);
	}
}
