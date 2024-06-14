package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.DeviceDashboardService;

@RestController
@RequestMapping("api")
public class DeviceDashboardController {

	@Autowired
	private DeviceDashboardService deviceDashboardService;

	@GetMapping("get/dashboard/data")
	public ResponseEntity<?> saveDeviceData() throws Exception {
		Response<?> response = deviceDashboardService.getDashBoardDTO();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
