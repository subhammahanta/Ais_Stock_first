//package com.watsoo.device.management.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.watsoo.device.management.model.VtsTac;
//import com.watsoo.device.management.repository.VtsTacRepository;
//
//@RestController
//@RequestMapping("api")
//@CrossOrigin
//public class TacController {
//
//	@Autowired
//	private VtsTacRepository tacRepository;
//	
//	@GetMapping(value = "v1/all/tac")
//	public ResponseEntity<List<VtsTac>> allPossibleProviders(){
//		return new ResponseEntity<>(tacRepository.findAll(), HttpStatus.OK);
//	}
//}
