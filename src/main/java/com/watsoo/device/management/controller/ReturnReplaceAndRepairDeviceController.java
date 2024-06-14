//package com.watsoo.device.management.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.watsoo.device.management.dto.Pagination;
//import com.watsoo.device.management.dto.Response;
//import com.watsoo.device.management.dto.ReturnReplaceRepairDTO;
//import com.watsoo.device.management.model.ReturnReplaceRepair;
//import com.watsoo.device.management.service.ReturnReplaceAndRepairDeviceService;
//
//@RestController
//@RequestMapping("api")
//public class ReturnReplaceAndRepairDeviceController {
//
//	@Autowired
//	private ReturnReplaceAndRepairDeviceService returnService;
//
//	@PostMapping("v1/add/return_replace_repair")
//	public ResponseEntity<?> addForReturnOrReplaceOrRepair(@RequestBody ReturnReplaceRepairDTO dto) {
//		Response<?> response = returnService.addForReturnOrReplaceOrRepair(dto);
//		return new ResponseEntity<>(response, HttpStatus.CREATED);
//	}
//
//	@PostMapping("v1/all/return_replace_repair")
//	public ResponseEntity<?> getAllReturnReplaceRepairDevices(
//			@RequestParam(required = true, defaultValue = "0") int pageNo,
//			@RequestParam(required = true, defaultValue = "0") int pageSize, @RequestBody ReturnReplaceRepairDTO dto) {
//		Pagination<List<ReturnReplaceRepair>> response = returnService.getAllReturnReplaceRepairDevices(pageNo,
//				pageSize, dto);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//	
//	@PostMapping("v1/repair")
//	public ResponseEntity<?> repairDevice(@RequestBody ReturnReplaceRepairDTO dto) {
//		Response<?> response = returnService.repairDevice(dto);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//}
