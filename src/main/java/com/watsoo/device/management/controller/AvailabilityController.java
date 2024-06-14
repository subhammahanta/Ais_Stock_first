package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.BoxRequestDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.AvailabilityService;

@RestController
@RequestMapping("api")
public class AvailabilityController {

	@Autowired
	private AvailabilityService availabilityService;

	@PostMapping("/v2/availability")
	public ResponseEntity<Response<?>> checkDeviceAvailability(@RequestParam int requestedQuantity) {
		Response<?> response = availabilityService.checkDeviceAvailability(requestedQuantity);

		if (response.getResponseCode() == HttpStatus.OK.value()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@PostMapping("/availability")
	public ResponseEntity<Response<?>> checkDeviceAvailabilityV2(@RequestBody BoxRequestDTO dto) {
		Response<?> response = availabilityService.checkDeviceAvailabilityV4(dto);
		if (response.getResponseCode() == HttpStatus.OK.value()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
}
