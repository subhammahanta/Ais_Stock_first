package com.watsoo.device.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.LoginRequest;
import com.watsoo.device.management.dto.LoginResponse;
import com.watsoo.device.management.dto.LoginResponseV2;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.LoginService;

@RestController
@RequestMapping("api")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<?> saveDeviceData(@RequestBody LoginResponse loginDTO) {
		Response<LoginResponse> reponse = loginService.login(loginDTO);
		return new ResponseEntity<>(reponse, HttpStatus.CREATED);
	}

	@PostMapping(value = "/v2/change/password")
	public ResponseEntity<?> changePasswordV2(@RequestBody LoginResponse loginRequest) {
		LoginResponse loginResponse = loginService.changePasswordV2(loginRequest);
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}
	
	@PostMapping("/v2/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginDTO) throws Exception {
		Response<?> reponse = loginService.loginV2(loginDTO);
		return new ResponseEntity<>(reponse, HttpStatus.valueOf(reponse.getResponseCode()));
	}
	
	@PostMapping(value = "/v3/login")
	public ResponseEntity<?> loginV2(@RequestBody LoginRequest loginRequest) {
		try {
			//change to loginV4 from loginV3 for is admin visible
			Response<?> reponse = loginService.loginV4(loginRequest);
			return new ResponseEntity<>(reponse, HttpStatus.valueOf(reponse.getResponseCode()));
		} catch (Exception e) {
			return new ResponseEntity<>(null,null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
