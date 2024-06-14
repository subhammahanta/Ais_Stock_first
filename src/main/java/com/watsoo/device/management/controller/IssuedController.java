package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.IssueDeviceDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.IssuedService;

@RestController
@RequestMapping("api")
public class IssuedController {

	@Autowired
	private IssuedService issuedService;
	
	
	@PostMapping("all/issued_device")
	public ResponseEntity<?> getIssueV2(@RequestParam(required = true, defaultValue = "0") int pageNo,
	@RequestParam(required = true, defaultValue = "0") int pageSize, @RequestBody IssueDeviceDTO dto){
		Pagination<List<IssueDeviceDTO>>  response = issuedService.getIssuedList(pageNo, pageSize, dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@PostMapping(value = "all/issued_device")
//	@ResponseBody
//	public ResponseEntity<?> getIssue(@RequestParam(required = true, defaultValue = "0") int pageNo,
//			@RequestParam(required = true, defaultValue = "0") int pageSize, @RequestBody IssuedList dto) {
//		Pagination<List<IssuedList>>  response = issuedService.getIssuedList(pageNo, pageSize, dto);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	@PostMapping("/save/issue")
	public ResponseEntity<?> saveIssueDeviceDetails(@RequestBody IssueDeviceDTO dto) {
		//change to v2 for stock management
		Response<?> issueDevice = issuedService.saveIssueDeviceDetails(dto);
		return new ResponseEntity<>(issueDevice, HttpStatus.CREATED);
	}

	@GetMapping("devices")
	public ResponseEntity<?> getdevices(@RequestParam Long issuedId) {
		Response<?> issueDevice = issuedService.getByIssuedId(issuedId);
		return new ResponseEntity<>(issueDevice, HttpStatus.OK);
	}
	
	@PostMapping("/device/unissue")
	public Response<?> unissueListOfDevice(@RequestBody IssueDeviceDTO dto) {
		Response<?> response = issuedService.unissueListOfDevice(dto);
		return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),response);
	}
}
