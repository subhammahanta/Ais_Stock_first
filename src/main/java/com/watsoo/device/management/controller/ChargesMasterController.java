package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.model.ChargesMaster;
import com.watsoo.device.management.service.ChargesMasterService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ChargesMasterController {
	
	@Autowired
	private ChargesMasterService chargesMasterService;
	
	@GetMapping("/v1/all/charges")
	public List<ChargesMaster> getAll(){
		return chargesMasterService.getAllCharges();
	}

}
