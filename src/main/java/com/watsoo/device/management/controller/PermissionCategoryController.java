package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.PermissionCategoryDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.PermissionCategoryService;

@RestController
@RequestMapping("permissionCategory")
@CrossOrigin
public class PermissionCategoryController {

	@Autowired
	private PermissionCategoryService permissionCategoryService;

	@GetMapping("/v1/permission_category")
	public ResponseEntity<?> getAllPermissionCategory() {
		Response<?> response = permissionCategoryService.getAllPermissionCategory();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
