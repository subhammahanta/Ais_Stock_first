package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.UserDTO;
import com.watsoo.device.management.service.UserService;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add/user")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		userDTO.setIsActive(true);
		Response<?> user = userService.createUser(userDTO);
		return new ResponseEntity<>(user, HttpStatus.valueOf(user.getResponseCode()));
	}

	@GetMapping("/get/user/byId")
	public ResponseEntity<?> getUserById(@RequestParam("id") Long id) {
		Response<?> response = userService.findUserById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
