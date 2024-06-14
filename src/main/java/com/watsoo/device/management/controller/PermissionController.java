package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.PermissionDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.PermisionService;

@RestController
@RequestMapping("permission")
@CrossOrigin
public class PermissionController {

	@Autowired
	private PermisionService permissionService;

	@GetMapping("/v1/permission/all")
	public ResponseEntity<?> getAllPermission(@RequestParam Long id) {
		Response<?> response = permissionService.getAllPermission(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
