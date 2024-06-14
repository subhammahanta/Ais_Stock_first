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

import com.watsoo.device.management.dto.BoxDeviceDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.ReConfigureBoxDTO;
import com.watsoo.device.management.dto.ReConfigureDevicesDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.ReConfigureBoxService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ReConfigureOrUnboxController {

	@Autowired
	private ReConfigureBoxService reconfigureBoxService;

	@PostMapping("/device/unbox")
	public Response<?> unboxDevice(@RequestBody GenericRequestBody request) {
		Response<?> response = reconfigureBoxService.unboxDevice(request);
		return response;
	}

	@PostMapping("/save/command_response")
	public Response<?> saveCommandResponse(@RequestBody GenericRequestBody request) {
		Response<?> response = reconfigureBoxService.saveCommandResponse(request);
		return response;
	}

	@PostMapping("/reconfigure_command/exist")
	public Response<?> checkReConfigureCommandExist(@RequestBody GenericRequestBody request) {
		Response<?> response = reconfigureBoxService.checkReConfigureCommandExist(request);
		return response;
	}

	@PostMapping("/save/reconfigure_command")
	public Response<?> saveReConfigureCommand(@RequestBody GenericRequestBody request) {
		Response<?> response = reconfigureBoxService.saveReConfigureCommandV2(request);
		return response;
	}

	@PostMapping("/device/reconfigure")
	public Response<?> reconfigureDevice(@RequestBody GenericRequestBody request) {
		Response<?> response = reconfigureBoxService.reconfigureDeviceV2(request);
		return response;
	}

	@PostMapping("/reconfigure/finish")
	public Response<?> finishReconfigure(@RequestBody GenericRequestBody request) {
		Response<?> response = reconfigureBoxService.finishReconfigureV2(request);
		return response;
	}

	@PostMapping("/get/box_device")
	public Response<?> getBoxDevice(@RequestBody GenericRequestBody request) {
		Response<BoxDeviceDTO> response = reconfigureBoxService.getBoxDeviceV2(request);
		return response;
	}

	@PostMapping("/get/reconfigure_box")
	public ResponseEntity<?> getAllReConfigureBox(@RequestBody GenericRequestBody request) {
		Pagination<List<ReConfigureBoxDTO>> response = reconfigureBoxService.getAllReConfigureBox(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/get/reconfigure_box_device")
	public ResponseEntity<?> getAllReConfigureBoxDevices(@RequestBody GenericRequestBody request) {
		if (request.getIsCompleted() == null || request.getIsCompleted() == false
				|| request.getReConfigureBoxId() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Pagination<List<ReConfigureDevicesDTO>> response = reconfigureBoxService.getAllReConfigureBoxDevices(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/get/reconfig_commands")
	public Response<?> getAllReConfigCommands(@RequestBody GenericRequestBody request) {
		return reconfigureBoxService.getAllCommands(request);
	}
	
	@PostMapping("/v2/device/info")
	public Response<?> getDeviceInfoV2(@RequestBody GenericRequestBody request) {
		return reconfigureBoxService.getDeviceInfoV2(request);
	}
	
//	@PostMapping("/add/reconfigure/device")
//	public Response<?> addReconfigureDevice(@RequestBody GenericRequestBody request) {
//		return reconfigureBoxService.addReconfigureDevice(request);
//	}
}
