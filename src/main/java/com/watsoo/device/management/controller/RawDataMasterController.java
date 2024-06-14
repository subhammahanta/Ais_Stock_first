package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.RawDataMasterService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class RawDataMasterController {

	@Autowired
	private RawDataMasterService rawDataMasterService;

	@GetMapping("/all/server")
	public Response<?> getAllServer() {
		return rawDataMasterService.allServer();
	}

}
