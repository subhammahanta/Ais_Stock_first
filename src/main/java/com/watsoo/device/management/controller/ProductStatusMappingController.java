//package com.watsoo.device.management.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.watsoo.device.management.dto.SearchDTO;
//import com.watsoo.device.management.model.Device;
//import com.watsoo.device.management.scheduler.ProductStatusMappingService;
//
//@Controller
//public class ProductStatusMappingController {
//	
//	@Autowired
//	private ProductStatusMappingService productStatusMappingService;
//
//	@PostMapping(value = "")
//	public ResponseEntity<?> getAllDevices(@RequestBody SearchDTO searchDTO) {
//		List<Device> deviceList = productStatusMappingService.getAllDevice(searchDTO);
//		return new ResponseEntity<>(null);
//	}
//}
