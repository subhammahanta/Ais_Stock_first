package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.StateClientReconfigureCommandService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class StateClientReconfigureCommandController {
	
	@Autowired
	private StateClientReconfigureCommandService stateClientReconfigureCommandService;
	
	@PostMapping("/client-state/command")
	public Response<?> getStateClientCommand(@RequestBody GenericRequestBody request) {
		return stateClientReconfigureCommandService.getStateClientCommand(request);
	}


}
