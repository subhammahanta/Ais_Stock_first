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

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.ResponseDTO;
import com.watsoo.device.management.model.DeviceLot;
import com.watsoo.device.management.model.TestedDevice;
import com.watsoo.device.management.service.DeviceLotService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class DeviceLotController {

	@Autowired
	private DeviceLotService deviceLotService;
	
	@PostMapping("/device-lot/add")
	public Response<?> addDeviceLot(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		Response<Object> response = deviceLotService.addDeviceLot(deviceCommandDTO);
		return response;
	}
	
	@PostMapping("/device-lot/all")
	public ResponseEntity<?> getAllDeviceLot(@RequestBody GenericRequestBody requestDTO) {
		Pagination<List<DeviceLot>>  deviceLotList= deviceLotService.getAllDeviceLot(requestDTO);
		return new ResponseEntity<>(deviceLotList, HttpStatus.OK);
	}
	
//	@PostMapping("/v2/device/lot_id")
//	public Response<?> getAllDeviceByLotId(@RequestBody DeviceCommandDTO deviceCommandDTO) {
//		Response<Object> response = deviceLotService.getAllDeviceByLotId(deviceCommandDTO);
//		return response;
//	}
	
	@PostMapping("/device/lot_id")
	public ResponseEntity<?> getAllDeviceByLotIdV2(@RequestBody GenericRequestBody request) {
		Pagination<List<TestedDevice>> response = deviceLotService.getAllDeviceByLotIdFilter(request);
		DeviceLot deviceLot = deviceLotService.getDeviceLotByLotId(request);
		ResponseDTO resp = new ResponseDTO();
		resp.setLot(deviceLot);
		resp.setResponse(response);		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
