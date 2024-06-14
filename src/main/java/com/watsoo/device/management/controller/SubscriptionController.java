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

import com.watsoo.device.management.dto.ClientSubscriptionMasterDTO;
import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StatePlatformMasterDTO;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;
import com.watsoo.device.management.dto.SubscriptionTypeDTO;
import com.watsoo.device.management.service.ClientSubscriptionMasterService;
import com.watsoo.device.management.service.StatePlatformMasterService;
import com.watsoo.device.management.service.SubscriptionMasterService;
import com.watsoo.device.management.service.SubscriptionTypeService;

@RestController
@RequestMapping("subscription")
public class SubscriptionController {

	@Autowired
	private SubscriptionTypeService subscriptionTypeService;

	@Autowired
	private SubscriptionMasterService subscriptionMasterService;

	@Autowired
	private StatePlatformMasterService statePlatformMasterService;
	
	@Autowired
	private ClientSubscriptionMasterService clientSubscriptionMasterService;

	@GetMapping(value = "allTypeMaster")
	public CustomResponse getAllSubscriptionType() {
		List<SubscriptionTypeDTO> customResponse = subscriptionTypeService.getAllSubscriptionType();
		return new CustomResponse(HttpStatus.OK.value(), customResponse, HttpStatus.OK.getReasonPhrase());
	}

	@GetMapping(value = "allMaster")
	public CustomResponse getAllSubscriptionMaster() {
		List<SubscriptionMasterDTO> customResponse = subscriptionMasterService.getAllSubscriptionMaster();
		return new CustomResponse(HttpStatus.OK.value(), customResponse, HttpStatus.OK.getReasonPhrase());
	}

	@GetMapping(value = "allMasterById")
	public CustomResponse getAllSubscriptionMasterById(@RequestParam("id") Integer id) {
		if (id != null) {
			CustomResponse customResponse = subscriptionMasterService.getAllSubscriptionMasterById(id);
			return customResponse;
		}
		return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@GetMapping(value = "allSatePlatform")
	public CustomResponse getAllStatePlateMstr() {
		List<StatePlatformMasterDTO> statePlatformMasterDTOList = statePlatformMasterService.getAllPlatFormMaster();
		return new CustomResponse(HttpStatus.OK.value(), statePlatformMasterDTOList, HttpStatus.OK.getReasonPhrase());
	}

	@GetMapping(value = "statePlatformById")
	public CustomResponse getStatePlatformMstr(@RequestParam("id") Integer id) 
	{
		if (id != null) {
			CustomResponse customResponse = statePlatformMasterService.getAllStatePlateMstrById(id);
			return customResponse;
		}
		return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase());
	}
	@GetMapping(value = "statePlatformByStateId")
	public CustomResponse getAllStatePlatformMstrByStateId(@RequestParam("stateId") Long stateId) 
	{
		if (stateId != null) {
			CustomResponse customResponse = statePlatformMasterService.getAllStatePlatformByStateId(stateId);
			return customResponse;
		}
		return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@GetMapping(value = "allClentSubsMstr")
	public CustomResponse getAllClientSubscriptionMstr() {
		List<ClientSubscriptionMasterDTO> clientSubsMstrDTO = clientSubscriptionMasterService.getAllClientSubsMaster();
		return new CustomResponse(HttpStatus.OK.value(), clientSubsMstrDTO, HttpStatus.OK.getReasonPhrase());
	}


	@GetMapping(value = "clentSubsMstrById")
	public CustomResponse getClentSubsMstrById(@RequestParam("id") Integer id) 
	{
		if (id != null) {
			CustomResponse customResponse = clientSubscriptionMasterService.getClentSubsMstrById(id);
			return customResponse;
		}
		return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase());
	}
	@GetMapping(value = "ClentSubsMstrByclientIdandStateId")
	public CustomResponse getAllClentSubsMstrByClientIdAndStateId(@RequestParam("clientId") Long clientId,@RequestParam("stateId") Long stateId) 
	{
		if ( clientId != null && stateId != null) {
			CustomResponse customResponse = clientSubscriptionMasterService.getAllClentSubsMstrByClientIdAndStateId(clientId,stateId);
			return customResponse;
		}
		return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase());
	}
	
	@PostMapping("/get/client/subscription")
	public Response<?> getClientSubscription(@RequestBody GenericRequestBody request) {
		Response<Object> response = clientSubscriptionMasterService.getClientSubscription(request);
		return response;
	}
	
	@PostMapping("/check/client/subscription")
	public Response<?> checkClientSubscription(@RequestBody GenericRequestBody request) {
		Response<Object> response = clientSubscriptionMasterService.checkClientSubscription(request);
		return response;
	}

	@PostMapping("/add/client/subscription")
	public Response<?> addClientSubscription(@RequestBody GenericRequestBody request) {
		Response<Object> response = clientSubscriptionMasterService.addClientSubscription(request);
		return response;
	}
	
//	@PostMapping("/all")
//	public ResponseEntity<?> getAllSubscription(@RequestBody GenericRequestBody request) {
//		Pagination<List<SubscriptionMasterDTO>> response= subscriptionMasterService.getAllSubscription(request);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
	
	@PostMapping("/all")
	public ResponseEntity<?> getAllClientSubscription(@RequestBody GenericRequestBody request) {
		Pagination<List<ClientSubscriptionMasterDTO>> response= clientSubscriptionMasterService.getAllClientSubscription(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
