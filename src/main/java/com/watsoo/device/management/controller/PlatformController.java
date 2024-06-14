package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.PlatformService;

@RestController
@RequestMapping("api")
public class PlatformController {

	@Autowired
	private PlatformService paltformService;

	@GetMapping("/all/platform")
	public Response<?> allPlatform() {
		return paltformService.allPlatform();
	}
}
