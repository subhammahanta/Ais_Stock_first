package com.watsoo.device.management.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.model.IccidMaster;
import com.watsoo.device.management.model.Operators;
import com.watsoo.device.management.model.Provider;
import com.watsoo.device.management.repository.OperatorsRepository;
import com.watsoo.device.management.repository.ProviderRepository;
import com.watsoo.device.management.service.IccidMasterService;

@RestController
@RequestMapping("api")
@CrossOrigin
public class IccidMasterController {
	
	@Autowired
	private IccidMasterService iccidMasterService;
	
	@Autowired
	private OperatorsRepository operatorsRepository;
	
	@Autowired
	private ProviderRepository providerRepository;

	@PostMapping("v1/get/all/iccid")
	public ResponseEntity<?> getAllIccidMaster(@RequestBody GenericRequestBody requestDTO) {
		Pagination<List<IccidMaster>>  iccidMasterList= iccidMasterService.getAllIccidMaster(requestDTO);
		return new ResponseEntity<>(iccidMasterList, HttpStatus.OK);
	}
	
	@PostMapping(value = "v1/add/iccid/from/excel")
	public ResponseEntity<?> addIccidFromExcelFile(@RequestParam("file") MultipartFile file,@RequestParam("userId") Long userId) {
		Map<Integer, String> response = iccidMasterService.addIccidFromExcelFile(file,userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "v1/all/operators")
	public ResponseEntity<List<Operators>> allPossibleOperators(){
		return new ResponseEntity<>(operatorsRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "v1/all/provider")
	public ResponseEntity<List<Provider>> allPossibleProviders(){
		return new ResponseEntity<>(providerRepository.findAll(), HttpStatus.OK);
	}

}
