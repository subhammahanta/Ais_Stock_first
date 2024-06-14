package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.RoleDTO;
import com.watsoo.device.management.service.RoleService;

@RestController
@RequestMapping("role")
@CrossOrigin
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/v1/role/add")
	public ResponseEntity<?> addRole(@RequestBody RoleDTO roleDTO) {
		Response<?> response = roleService.addRole(roleDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/v1/role/update")
	public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO) {
		Response<?> response = roleService.updateRole(roleDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/v1/role/all")
	public ResponseEntity<?> getAllRole(@RequestParam Long companyId) {

		Response<?> response = roleService.getAllRole(companyId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/v1/role")
	public ResponseEntity<?> getRole(@RequestParam Long roleId) {
		Response<?> response = roleService.getRole(roleId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
