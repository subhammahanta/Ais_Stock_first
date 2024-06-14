package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.watsoo.device.management.dto.AllCommandRequestResponse;
import com.watsoo.device.management.dto.CheckExistingRequestResponse;
import com.watsoo.device.management.dto.CommandRequestDTO;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.RequestCommandReponse;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.ActionEnum;
import com.watsoo.device.management.model.SoftwareVersionCommand;
import com.watsoo.device.management.repository.SoftwareVersionCommandRepository;
import com.watsoo.device.management.service.DeviceCommandRequestService;

@Service
public class DeviceCommandRequestServiceImpl implements DeviceCommandRequestService {

	@Value("${ais.command.add.request.url}")
	private String addRequestUrl;

	@Value("${ais.command.all.request.url}")
	private String allRequestUrl;

	@Value("${ais.command.get.single.request.url}")
	private String singleRequestUrl;

	@Value("${ais.command.revert.request.url}")
	private String revertRequestUrl;
	
	@Value("${ais.command.check.existing.request}")
	private String checkExistingRequestUrl;

	@Autowired
	private SoftwareVersionCommandRepository softwareVersionCommandRepository;

	@Override
	public Response<Object> commandRequest(DeviceCommandDTO deviceCommandDTO) {
		Response<Object> response = new Response<>();
		try {
			if (deviceCommandDTO.getCommand() != null && deviceCommandDTO.getCommand() != ""
					&& deviceCommandDTO.getCreatedBy() != null && deviceCommandDTO.getImeiList() != null
					&& !deviceCommandDTO.getImeiList().isEmpty()) {
				//List<String> imeiList = checkDeviceRequestAlreadyCreated(deviceCommandDTO);
				//
//                if (imeiList != null && !imeiList.isEmpty()) {
//                	response.setData(imeiList);
//    				response.setMessage(imeiList +" Device Already Exist");
//    				response.setResponseCode(HttpStatus.ALREADY_REPORTED.value());
				//} else {
					String flag = requestCommand(deviceCommandDTO);
					if (flag.equalsIgnoreCase("OK")) {
						response.setData(null);
						response.setMessage(HttpStatus.OK.getReasonPhrase());
						response.setResponseCode(HttpStatus.OK.value());
					} else {
						response.setData(null);
						response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
						response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					}
				//}				
			} else {
				response.setData(null);
				response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
				response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private List<String> checkDeviceRequestAlreadyCreated(DeviceCommandDTO dto) {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DeviceCommandDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Object> deviceResponse = null;
		try {
			deviceResponse = template.exchange(checkExistingRequestUrl, HttpMethod.POST,
					requestEntity, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> response = new ArrayList<>();
		if (deviceResponse != null && deviceResponse.getBody() != null) {
			try {
				Object deviceResponse2 = deviceResponse.getBody();
				ObjectMapper mapper = new ObjectMapper();
				CheckExistingRequestResponse resp = new Gson().fromJson(mapper.writeValueAsString(deviceResponse2),
						CheckExistingRequestResponse.class);
				response = resp.getData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public String requestCommand(DeviceCommandDTO dto) {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DeviceCommandDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Object> deviceResponse = null;
		try {
			deviceResponse = template.exchange(addRequestUrl, HttpMethod.POST, requestEntity, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String response = "";
		if (deviceResponse != null && deviceResponse.getBody() != null) {
			try {
				Object deviceResponse2 = deviceResponse.getBody();
				ObjectMapper mapper = new ObjectMapper();
				RequestCommandReponse resp = new Gson().fromJson(mapper.writeValueAsString(deviceResponse2),
						RequestCommandReponse.class);
				response = resp.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	@Override
	public AllCommandRequestResponse getAllCommandRequest(int pageNo, int pageSize, CommandRequestDTO dto) {
//		Pagination<List<CommandRequestDTO>> response = new Pagination();
//		List<CommandRequestDTO> list = new ArrayList<>();
//		Page<CommandRequest> commandRequest = null;
//		Pageable pageRequest = Pageable.unpaged();
//		if (pageSize > 0) {
//			pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
//		}
//		commandRequest = commandRequestRepository.getAllCommandRequest(dto, pageRequest);
//		for (CommandRequest commandRequest2 : commandRequest) {
//			CommandRequestDTO commandRequestDTO = commandRequest2.convertEntityToDtoV2();
//			list.add(commandRequestDTO);
//		}
		AllCommandRequestResponse allCommandRequestResponse = getAllCommandRequestResponse(dto, pageNo, pageSize);
//		response.setData(list);
//		response.setNumberOfElements(commandRequest.getNumberOfElements());
//		response.setTotalElements(commandRequest.getTotalElements());
//		response.setTotalPages(commandRequest.getTotalPages());
		return allCommandRequestResponse;
	}

	@Override
	public Response<CommandRequestDTO> getCommandRequestById(CommandRequestDTO dto) {
		Response<CommandRequestDTO> response = new Response<>();
		if (dto.getId() != null && dto.getId() > 0) {
			CommandRequestDTO commandRequestResponse = getCommandRequestResponseById(dto);
			response.setData(commandRequestResponse);
			response.setMessage(HttpStatus.OK.getReasonPhrase());
			response.setResponseCode(HttpStatus.OK.value());
		} else {
			response.setData(null);
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		}
		return response;
	}

	public CommandRequestDTO getCommandRequestResponseById(CommandRequestDTO dto) {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CommandRequestDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Object> deviceResponse = null;
		try {
			deviceResponse = template.exchange(singleRequestUrl, HttpMethod.POST, requestEntity, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommandRequestDTO response = null;
		if (deviceResponse != null && deviceResponse.getBody() != null) {
			try {
				Object deviceResponse2 = deviceResponse.getBody();
				ObjectMapper mapper = new ObjectMapper();
				RequestCommandReponse resp = new Gson().fromJson(mapper.writeValueAsString(deviceResponse2),
						RequestCommandReponse.class);
				response = resp.getData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public AllCommandRequestResponse getAllCommandRequestResponse(CommandRequestDTO dto, int pageNo, int pageSize) {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		// Build the URL with query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(allRequestUrl).queryParam("pageNo", pageNo)
				.queryParam("pageSize", pageSize);

		// Create the URL with query parameters
		String finalUrl = builder.toUriString();
		HttpEntity<CommandRequestDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Object> deviceResponse = null;
		try {
			deviceResponse = template.exchange(finalUrl, HttpMethod.POST, requestEntity, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AllCommandRequestResponse response = null;
		if (deviceResponse != null && deviceResponse.getBody() != null) {
			try {
				Object deviceResponse2 = deviceResponse.getBody();
				ObjectMapper mapper = new ObjectMapper();
				response = new Gson().fromJson(mapper.writeValueAsString(deviceResponse2),
						AllCommandRequestResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	@Override
	public Response<?> revertCommandRequest(DeviceCommandDTO dto) {
		Response<String> response = new Response<>();
		if (dto.getRequestId() != null && dto.getRequestId() > 0
				&& (dto.getAction().equals(ActionEnum.CANCEL) || dto.getAction().equals(ActionEnum.REVERT))) {
			// get request and update
			SoftwareVersionCommand softwareVersionCommand = softwareVersionCommandRepository.findRevertCommand();
			dto.setRevertCommandTemplate(softwareVersionCommand.getCommand());
			String resp = revertCommandRequestResponse(dto);
			if (resp.equalsIgnoreCase("OK")) {
				response.setData(null);
				response.setMessage(HttpStatus.OK.getReasonPhrase());
				response.setResponseCode(HttpStatus.OK.value());
			} else {
				response.setData(null);
				response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
				response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setData(null);
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		}
		return response;
	}

	public String revertCommandRequestResponse(DeviceCommandDTO dto) {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DeviceCommandDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Object> deviceResponse = null;
		try {
			deviceResponse = template.exchange(revertRequestUrl, HttpMethod.POST, requestEntity, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String response = null;
		if (deviceResponse != null && deviceResponse.getBody() != null) {
			try {
				Object deviceResponse2 = deviceResponse.getBody();
				ObjectMapper mapper = new ObjectMapper();
				RequestCommandReponse resp = new Gson().fromJson(mapper.writeValueAsString(deviceResponse2),
						RequestCommandReponse.class);
				response = resp.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}
}
