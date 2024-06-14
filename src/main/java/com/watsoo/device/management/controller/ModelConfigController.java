package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.ModelConfigService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ModelConfigController {

	@Autowired
	private ModelConfigService modelConfigService;
	
	@PostMapping("/model-config/check")
	public Response<?> checkModelConfig(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		Response<Object> response = modelConfigService.checkModelConfig(deviceCommandDTO);
		return response;
	}
	
	@PostMapping("/model-config/add")
	public Response<?> addModelConfig(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		Response<Object> response = modelConfigService.addModelConfigV2(deviceCommandDTO);
		return response;
	}
}
