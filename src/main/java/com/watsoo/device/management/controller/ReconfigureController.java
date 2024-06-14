package com.watsoo.device.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.PaginationV2;
import com.watsoo.device.management.dto.ReconfigureDeviceResponseDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.ReconfigureService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ReconfigureController {

	@Autowired
	private ReconfigureService reconfigureService;

	@PostMapping("/v2/device/reconfigure")
	public Response<?> saveDeviceToReconfigure(@RequestBody GenericRequestBody request) {
		return reconfigureService.saveDeviceToReconfigure(request);
	}

	@GetMapping("/v2/all/reconfigure/device")
	public ResponseEntity<?> getAllReconfigureDevice() {
		return reconfigureService.getAllReconfigureDevice();
	}

	@PostMapping("/all/reconfigure/device")
	public Response<?> getAllReconfigureDeviceV2(@RequestBody GenericRequestBody request) {
		PaginationV2<Map<String, List<ReconfigureDeviceResponseDTO>>> response = reconfigureService
				.getAllReconfigureDeviceV2(request);
		return new Response<PaginationV2<Map<String, List<ReconfigureDeviceResponseDTO>>>>(HttpStatus.OK.value(),
				HttpStatus.OK.getReasonPhrase(), response);
	}

	@PostMapping("/device/info")
	public Response<?> getDeviceInfo(@RequestBody GenericRequestBody request) {
		return reconfigureService.getDeviceInfo(request);
	}

}
