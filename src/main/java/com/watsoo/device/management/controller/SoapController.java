//package com.watsoo.device.management.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.watsoo.device.management.dto.SoapDTO;
//import com.watsoo.device.management.service.SoapService;
//
//@RestController
//@RequestMapping("api")
//public class SoapController {
//
//	@Autowired
//	private SoapService soapService;
//
//	@PostMapping("/soap/update")
//	public String update(@RequestBody SoapDTO request) {
//		return soapService.update(request);
//	}
//}