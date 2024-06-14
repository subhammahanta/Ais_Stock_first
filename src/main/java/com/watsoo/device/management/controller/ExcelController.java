package com.watsoo.device.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.service.ExcelService;

@RestController
public class ExcelController {

	@Autowired
	private ExcelService excelService;

//	@PostMapping("/upload")
//	public ResponseEntity<Response<?>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
//		Response<Object> response = excelService.readExcelDataAndSaveInDb(file);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	@PostMapping(value = "v1/import/updated/vehicle/excel")
	public ResponseEntity<?> importUpdatedVehicleExcelFile(@RequestParam("file") MultipartFile file) {
		Map<Integer, String> response = excelService.importUpdatedVehicleExcelFile(file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "v1/create/device/excel")
	public ResponseEntity<?> createDeviceFromExcelFile(@RequestParam("file") MultipartFile file) {
		Map<Integer, String> response = excelService.createDeviceFromExcelFile(file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "v1/map/client/database-excel")
	public ResponseEntity<?> mapClientDatabaseExcel(@RequestParam("file") MultipartFile file) {
		List<Map<Object, String>> response = excelService.mapClientDatabaseExcel(file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("v1/create/box/from/excel")
	public ResponseEntity<?> createBoxFromExcel() {
		return new ResponseEntity<>(excelService.createBoxFromExcel(), HttpStatus.CREATED);
	}
	
	@PostMapping("v1/map/client/device/from/excel")
	public ResponseEntity<?> mapCorrectClientWithDevice(@RequestParam("file") MultipartFile file) {
		Map<Integer, String> response = excelService.mapCorrectClientWithDevice(file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "v1/update/device/from/excel")
	public ResponseEntity<?> updateDeviveFromExcelFile(@RequestParam("file") MultipartFile file) {
		Map<Integer, String> response = excelService.importUpdatedVehicleExcelFileV2(file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
