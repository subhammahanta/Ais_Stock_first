package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.model.EmsMaster;
import com.watsoo.device.management.repository.EmsMasterRepository;

@CrossOrigin
@RestController
@RequestMapping("api")
public class EmsMasterController {

	@Autowired
	private EmsMasterRepository emsRepository;

	@GetMapping("/ems/all")
	public ResponseEntity<List<EmsMaster>> allEmsMaster() {
		return new ResponseEntity<>(emsRepository.findAll(), HttpStatus.OK);
	}
}
