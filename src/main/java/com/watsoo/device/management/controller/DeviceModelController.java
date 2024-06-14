package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.DeviceModel;
import com.watsoo.device.management.repository.DeviceModelRepository;
import com.watsoo.device.management.service.DeviceModelService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class DeviceModelController {

	@Autowired
	private DeviceModelRepository modelRepository;

	@Autowired
	private DeviceModelService modelService;

	@GetMapping("/device-model/all")
	List<DeviceModel> allDeviceModel() {
		return modelRepository.findAll();
	}

	@PostMapping("/add/model")
	public Response<?> addDeviceModel(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		if (deviceCommandDTO.getDeviceModel() != null && deviceCommandDTO.getDeviceModel() != ""
				&& deviceCommandDTO.getTacNo() != null && deviceCommandDTO.getTacNo() != ""
				&& deviceCommandDTO.getUserId() != null) {
			Response<Object> response = modelService.addDeviceModel(deviceCommandDTO);
			return response;
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
	}
}
