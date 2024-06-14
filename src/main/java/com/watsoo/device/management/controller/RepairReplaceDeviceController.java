package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.RepairReplaceDeviceMasterDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.RepairReplaceDeviceMaster;
import com.watsoo.device.management.service.RepairReplaceDeviceService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class RepairReplaceDeviceController {
	
	@Autowired
	private RepairReplaceDeviceService repairReplaceService;
	
	@PostMapping("/v1/add/repair/request")
	public Response<?> repairDevices(@RequestBody GenericRequestBody dto) {
		return repairReplaceService.repairDevices(dto);
	}

	@PostMapping("/v2/repair/device/request/all")
	public ResponseEntity<?> getAllRepairDeviceRequest(@RequestBody GenericRequestBody request) {
		Pagination<List<RepairReplaceDeviceMaster>> response= repairReplaceService.getAllRepairDeviceRequest(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/v1/add/device/repair")
	public Response<?> addRepairDevice(@RequestBody GenericRequestBody dto) {
		return repairReplaceService.addRepairDevice(dto);
	}

	@PostMapping("/v1/device/repair")
	public Response<?> repairDevice(@RequestBody GenericRequestBody dto) {
		return repairReplaceService.repairDevice(dto);
	}
	
	@PostMapping("/v1/repair/device/request/all")
	public ResponseEntity<?> getAllRepairDeviceRequestV2(@RequestBody GenericRequestBody request) {
		Pagination<List<RepairReplaceDeviceMasterDTO>> response= repairReplaceService.getAllRepairDeviceRequestV2(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/v1/device/repair/charges")
	public Response<?> getRepairCharges(@RequestBody GenericRequestBody dto) {
		return repairReplaceService.getRepairCharges(dto);
	}
}
