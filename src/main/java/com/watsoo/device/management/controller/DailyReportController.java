package com.watsoo.device.management.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.DeviceService;

@RestController
public class DailyReportController {

	@Autowired
	private DeviceService deviceService;

	@GetMapping("/get/all/todays/created/device")
	public ResponseEntity<?> countNumberOfDeviceCreatedToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = new Date();
		Response<?> deviceObj = deviceService.countNumberOfDeviceCreatedToday();
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	}
}
