package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.CommandRequestDto;
import com.watsoo.device.management.dto.CommandRequestMasterDTO;
import com.watsoo.device.management.dto.CommandsDetailsDTO;
import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.service.CommandDetailsService;
import com.watsoo.device.management.service.CommandRequestMasterService;
import com.watsoo.device.management.service.CommandService;

@RestController
public class CommandController {

	@Autowired
	private CommandService commandService;

	@Autowired
	private CommandRequestMasterService commandRequestMasterService;

	@Autowired
	private CommandDetailsService commandDetailsService;

	@PostMapping(value = "api/commands/send")
	public ResponseEntity<?> shootCommand(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		if (deviceCommandDTO.getCommand() != null && deviceCommandDTO.getCommand() != ""
				&& deviceCommandDTO.getImeiNo() != null && deviceCommandDTO.getImeiNo() != ""
				&& deviceCommandDTO.getUserId() != null && deviceCommandDTO.getUserId() > 0
				&& deviceCommandDTO.getUserName() != null && deviceCommandDTO.getUserName() != ""
				&& deviceCommandDTO.getVehicleId() != null && deviceCommandDTO.getVehicleId() > 0) {
			Response<String> response = commandService.shootCoreCommandDevice(deviceCommandDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

	
//	//get command one by one
//	@PostMapping(value = "api/get/commands")
//	public ResponseEntity<?> getCommand(@RequestBody DeviceCommandDTO deviceCommandDTO) {
//		if (deviceCommandDTO.getLotId() != null && deviceCommandDTO.getLotId() >0) {
//			Response<Object> response = commandService.getCommand(deviceCommandDTO);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@PostMapping(value = "api/all/commands")
	public Response<?> getAllCommand(@RequestBody DeviceCommandDTO deviceCommandDTO) {
//		if (deviceCommandDTO.getLotId() != null && deviceCommandDTO.getLotId() >0) {
			Response<Object> response = commandService.getAllCommand(deviceCommandDTO);
			return response;
//		} else {
//			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
//		}
	}
	
	@PostMapping(value = "api/save/command/response")
	public Response<?> saveCommandResponse(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		if (deviceCommandDTO.getLotId() != null && deviceCommandDTO.getLotId() >0) {
			Response<Object> response = commandService.saveCommandResponse(deviceCommandDTO);
			return response;
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
	}
	
	@PostMapping(value = "api/save/tested/device")
	public Response<?> saveTestedDevice(@RequestBody DeviceCommandDTO deviceCommandDTO) {
		if (deviceCommandDTO.getLotId() != null && deviceCommandDTO.getLotId() >0) {
			Response<Object> response = commandService.saveTestedDevice(deviceCommandDTO);
			return response;
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
	}

	@PostMapping(value = "/api/device/config/request")
	public CustomResponse shootExcelCommand(@RequestBody CommandRequestDto cmdDTO) {
		if (cmdDTO != null) {
			if (cmdDTO.getUserId() != null && !cmdDTO.equals(null)) {

				CommandRequestDto response = commandService.shootCommandByList(cmdDTO);
				if (response == null) {
					return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
							HttpStatus.BAD_REQUEST.getReasonPhrase());
				}
				return new CustomResponse(HttpStatus.OK.value(), response, HttpStatus.OK.getReasonPhrase());

			} else
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "UserId is Must");
		} else {
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
	}

	@PostMapping("command/All")
	public CustomResponse getAllCommands(@RequestBody GenericRequestBody requestDTO) {

		if (requestDTO.getPageNo() != null && requestDTO.getPageSize() != null && requestDTO.getPageSize() > 0) {
			Pagination<List<CommandRequestMasterDTO>> commandMasterLst = commandRequestMasterService
					.getAllCommands(requestDTO);
			return new CustomResponse(HttpStatus.OK.value(), commandMasterLst, HttpStatus.OK.getReasonPhrase());
		} else {
			List<CommandRequestMasterDTO> list = commandRequestMasterService.findAll();
			return new CustomResponse(HttpStatus.OK.value(), list, HttpStatus.OK.getReasonPhrase());
		}

	}

	@PostMapping("commandDetails/All")
	public CustomResponse getAllCommandDetails(@RequestBody GenericRequestBody requestDTO) {

		if (requestDTO.getPageNo() != null && requestDTO.getPageSize() != null && requestDTO.getPageSize() > 0) {
			Pagination<List<CommandsDetailsDTO>> commandDetailsList = commandDetailsService
					.getAllCommandDetails(requestDTO);
			return new CustomResponse(HttpStatus.OK.value(), commandDetailsList, HttpStatus.OK.getReasonPhrase());
		} else {
			List<CommandsDetailsDTO> list = commandDetailsService.findAllByMasterId(requestDTO.getMasterId());
			return new CustomResponse(HttpStatus.OK.value(), list, HttpStatus.OK.getReasonPhrase());
		}

	}
	
	@PostMapping("/device/configure/notify")
	public Response<Object> generateConfigureDevicesExcelAndNotify(@RequestBody GenericRequestBody requestDTO) throws Exception {
		Response<Object>response = commandDetailsService.generateConfigureDevicesExcelAndNotify(requestDTO);
		return response;
	}
}
