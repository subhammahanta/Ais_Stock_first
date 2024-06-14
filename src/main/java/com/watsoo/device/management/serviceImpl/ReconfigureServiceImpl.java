package com.watsoo.device.management.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.PaginationV2;
import com.watsoo.device.management.dto.ReconfigureDeviceResponseDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.repository.DeviceLiteRepository;
import com.watsoo.device.management.repository.UserRepository;
import com.watsoo.device.management.service.ReconfigureService;
import com.watsoo.device.management.util.PaginationUtil;

@Service
public class ReconfigureServiceImpl implements ReconfigureService {

	@Value("${ais.own.core.user}")
	private String userName;

	@Value("${ais.own.core.password}")
	private String password;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Value("${ais.reconfigure.device.fetch.url}")
	private String fetchReconfigureDeviceUrl;

	@Value("${ais.device.reconfigure.add.url}")
	private String addDeviceReconfigureUrl;

	@Autowired
	private DeviceLiteRepository deviceRepository;

	@Override
	public Response<?> saveDeviceToReconfigure(GenericRequestBody request) {
		if (request == null || request.getImeiNoList() == null || request.getImeiNoList().isEmpty()
				|| request.getRequestedBy() == null || request.getCommand() == null || request.getCommand().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}

		try {
			ResponseEntity<String> reconfigureDevice = reconfigureDevice(request);
			if (reconfigureDevice != null) {
				// System.out.println(reconfigureDevice.getBody());
				if (reconfigureDevice.getBody().equals("200")) {
					return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
				} else {
					return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
							HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
				}
			} else {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"An error occurred during device reconfiguration.");
		}
	}

	public ResponseEntity<String> reconfigureDevice(GenericRequestBody request) {
		try {
			String basicAuth = "Basic " + (convertStringToBase64(userName + ":" + password));
			String reconfigureDeviceUrl = addDeviceReconfigureUrl;
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.set(HttpHeaders.AUTHORIZATION, basicAuth.trim());
			headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("imeiNumbers", request.getImeiNoList());
			requestBody.put("requestedBy", request.getRequestedBy());
			requestBody.put("command", request.getCommand().trim());
			requestBody.put("boxnumber", request.getBoxCode().trim());
			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
			return restTemplate.exchange(reconfigureDeviceUrl, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String convertStringToBase64(String string) {
		String encoded = Base64.getEncoder().encodeToString(string.getBytes());
		return encoded;
	}

	@Override
	public ResponseEntity<?> getAllReconfigureDevice() {
		List<User> allUser = userRepository.findAll();
		Map<Long, User> userMap = allUser.stream().collect(Collectors.toMap(User::getId, Function.identity()));
		String url = fetchReconfigureDeviceUrl;
		String basicAuth = "Basic " + (convertStringToBase64(userName + ":" + password));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", basicAuth.trim());
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		String body = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<ReconfigureDeviceResponseDTO> devices = objectMapper.readValue(body,
					new TypeReference<List<ReconfigureDeviceResponseDTO>>() {
					});
			devices.forEach(device -> {
				User user = userMap.get(device.getRequestedby());
				device.setUser(user.getName());
			});
			return new ResponseEntity<>(devices, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public PaginationV2<Map<String, List<ReconfigureDeviceResponseDTO>>> getAllReconfigureDeviceV2(
			GenericRequestBody request) {
		List<User> allUser = userRepository.findAll();
		Map<Long, User> userMap = allUser.stream().collect(Collectors.toMap(User::getId, Function.identity()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fromdateStr = dateFormat.format(request.getFromDate());
		String todateStr = dateFormat.format(request.getToDate());
		String url = String.format("%s?fromdate=%s&todate=%s&imeinumber=%s", fetchReconfigureDeviceUrl, fromdateStr,
				todateStr, request.getImeiNo());
		// String url = fetchReconfigureDeviceUrl;
		String basicAuth = "Basic " + (convertStringToBase64(userName + ":" + password));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", basicAuth.trim());
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			String body = response.getBody();
			List<ReconfigureDeviceResponseDTO> devices = objectMapper.readValue(body,
					new TypeReference<List<ReconfigureDeviceResponseDTO>>() {
					});
			devices.forEach(device -> {
				User user = userMap.get(device.getRequestedby());
				device.setUser(user.getName());
			});
			Map<String, List<ReconfigureDeviceResponseDTO>> reqCodeMap = devices.stream().filter(x -> x != null)
					.collect(Collectors.groupingBy(ReconfigureDeviceResponseDTO::getRequestedcode));
			if (request.getPageSize() != null && request.getPageNo() != null) {
				// Comparator<ReconfigureDeviceResponseDTO> comparator = Comparator
				// .comparing(ReconfigureDeviceResponseDTO::getId);
				// PaginationV2<List<ReconfigureDeviceResponseDTO>> paginatedDevices =
				// PaginationUtil.paginate(devices,
				// request.getPageNo(), request.getPageSize(), comparator);
				PaginationV2<Map<String, List<ReconfigureDeviceResponseDTO>>> paginatedReqCodeMap = PaginationUtil
						.paginateMap(reqCodeMap, request.getPageNo(), request.getPageSize());
				return paginatedReqCodeMap;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Response<?> getDeviceInfo(GenericRequestBody request) {
		if (request.getIsBoxSearch() == null && request.getSearch() == null && request.getSearch() == "") {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}

		if (request.getIsBoxSearch()) {
			// if (request.getBoxCode() == null || request.getBoxCode() == "") {
			// return new Response<>(HttpStatus.BAD_REQUEST.value(), "BOX NO CAN'T BE
			// EMPTY");
			// }
			List<DeviceLite> list = deviceRepository.findByBoxCode(request.getSearch());
			if (list.isEmpty()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(),
						"NO DEVICE NOT FOUND IN BOX :" + request.getBoxCode());
			}
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), list);
		} else {
			// if (request.getImeiNo() == null || request.getImeiNo() == "") {
			// return new Response<>(HttpStatus.BAD_REQUEST.value(), "IMEI CAN'T BE EMPTY");
			// }
			List<DeviceLite> device = deviceRepository.findByImei(request.getSearch());
			if (device.isEmpty()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "DEVICE NOT FOUND");
			}

			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), device);
		}
	}

	@Override
	public Response<?> getDeviceInfoV2(GenericRequestBody request) {
		if (request.getSearch() == null && request.getSearch() == "") {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}

		DeviceLite device = deviceRepository.findByImeiOrIccidOrUuid(request.getSearch());
		if (device == null) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "DEVICE NOT FOUND");
		}

		return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), device);
	}

}
