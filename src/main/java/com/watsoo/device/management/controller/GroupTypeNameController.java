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

import com.watsoo.device.management.dto.GroupTypeNameDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.GroupTypeNameService;

@RestController
@RequestMapping("groups")
@CrossOrigin
public class GroupTypeNameController {

	@Autowired
	private GroupTypeNameService groupTypeNameService;

	@GetMapping("v1/group/all")
	public ResponseEntity<?> getAllGroups() {
		Response<?> response = groupTypeNameService.getAllGroups();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("v1/get/groupBy/permission")
	public ResponseEntity<?> getGroupByAllPermissions(@RequestParam Long id) {
		Response<?> response = groupTypeNameService.getGroup(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
