package com.watsoo.device.management.controller;

import java.io.IOException;
import java.util.List;

import com.watsoo.device.management.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.DeviceLiteV3;
import com.watsoo.device.management.service.DeviceService;
import com.watsoo.device.management.serviceImpl.DeviceThreadService;
import com.watsoo.device.management.util.TokenUtility;

@RestController
@RequestMapping("api")
public class DeviceController {

	private final DeviceThreadService threadService;

	public DeviceController(DeviceThreadService threadService) {
		this.threadService = threadService;
	}

	@Autowired
	private DeviceService deviceService;



	@PostMapping("/save/device")
	public ResponseEntity<?> saveDeviceData(@RequestBody Device device) {
		Boolean tokenValid = TokenUtility.validateToken(device.getUserId(), device.getToken());
		if (tokenValid) {
			Device deviceObj = deviceService.saveDeviceDataV2(device);
			return new ResponseEntity<>(deviceObj, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/get/device")
	public ResponseEntity<?> getDevice(@RequestParam("imei") String imei, @RequestParam("userId") Long userId,
			@RequestParam("token") String token) {
		Boolean tokenValid = TokenUtility.validateToken(userId, token);
		if (tokenValid) {
			Response<Device> deviceObj = deviceService.getDevice(imei);
			return new ResponseEntity<>(deviceObj, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("v2/get/All")
	public ResponseEntity<?> getAllDevice(@RequestBody GenericRequestBody requestDTO) {
//		Boolean tokenValid = TokenUtility.validateToken(requestDTO.getUserId(),requestDTO.getToken());
//		if (tokenValid) {
		Pagination<List<Device>> deviceObj = deviceService.getAllDevice(requestDTO);
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	} /*
		 * else { return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED); }
		 */

//	}

	@PostMapping("/get/All")
	public ResponseEntity<?> getAllDeviceV2(@RequestBody GenericRequestBody requestDTO) {
//		Boolean tokenValid = TokenUtility.validateToken(requestDTO.getUserId(),requestDTO.getToken());
//		if (tokenValid) {
		Pagination<List<DeviceLite>> deviceObj = deviceService.getAllDeviceV2(requestDTO);
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	}

	@GetMapping("/get/app/version")
	public ResponseEntity<?> getAppVersion() {
		Response<GenericRequestBody> appResponse = deviceService.getAppVersion();
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	@PostMapping("/update/status")
	public ResponseEntity<?> updateDeviceStatus(@RequestBody GenericRequestBody requestBody) {
		Response<List<Device>> appResponse = deviceService.updateDeviceStatus(requestBody);
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	@PostMapping("/update/state")
	public ResponseEntity<?> updateDeviceState(@RequestBody GenericRequestBody requestBody) {
		Response<Device> appResponse = deviceService.updateDeviceState(requestBody);
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	@GetMapping("/get/all/unique/software/version")
	public ResponseEntity<?> getAllUniqueSoftwareVersion() {
		Response<GenericRequestBody> appResponse = deviceService.getAllUniqueSoftwareVersionV2();
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	@GetMapping("/getCurrentVersion")
	public ResponseEntity<?> getCurrentVersion() {
		Response<?> appResponse = deviceService.getCurrentVersion();
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	@PostMapping("/updateSoftwareVersion")
	public ResponseEntity<?> updateSoftwareVersion(@RequestBody ConfigurationDTO dto) {
		Response<?> response = deviceService.updateSoftwareVersion(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//	@PostMapping("/update/device/last/packet")
//	public ResponseEntity<?> updateDeviceLastInfo(@RequestBody GenericRequestBody requestBody) {
//		Response<Device> appResponse = deviceService.updateDeviceLastInfo(requestBody);
//		return new ResponseEntity<>(appResponse, HttpStatus.OK);
//	}

//	@GetMapping(value = "v2/get/All") 
//	public ResponseEntity<?> getAllDeviceV2() {
//		List<Device> deviceList = deviceService.getAllDeviceV2();
//		return new ResponseEntity<>(deviceList, HttpStatus.OK);
//	}

	@GetMapping("/get/all/simoperator")
	private ResponseEntity<?> getAllSimOperator() {
		return new ResponseEntity<>(deviceService.getAllESimProvider(), HttpStatus.OK);
	}

	@GetMapping("/ais/get/device")
	public ResponseEntity<?> getAisDevice(@RequestParam("imei") String imei) {
		Response<List<Device>> deviceObj = deviceService.getAisDevice(imei);
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	}

	@PostMapping("/get/by/imei")
	public ResponseEntity<?> getAllDeviceByImei(@RequestBody GenericRequestBody requestDTO) {
		Pagination<List<DeviceLite>> deviceObj = deviceService.getAllDeviceByImei(requestDTO);
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	}

	@PostMapping("/get/all/device/by/list_of_imei")
	public ResponseEntity<?> getAllDeviceByListOfImei(@RequestBody GenericRequestBody requestDTO) {
		Response<List<DeviceLite>> deviceObj = deviceService.getAllDeviceByListOfImei(requestDTO);
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	}

	@GetMapping("all/devices")
	public List<Device> allDevice() {
		List<Device> devices = deviceService.getAllDevices();
		return devices;
	}

	// @PostMapping("device/excel/generate")
	public void generateExcelAndNotifyOld(@RequestBody GenericRequestBody requestDTO) throws IOException {
		deviceService.generateExcelAndNotify(requestDTO);
		// return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping("device/excel/generate")
	public ResponseEntity<String> generateExcelAndNotify(@RequestBody GenericRequestBody requestDTO)
			throws IOException {
		if (threadService.isRequestInProgress()) {
			return ResponseEntity.badRequest().body("Server Busy");
		}
		threadService.executeMethodInNewThread(requestDTO);
		return ResponseEntity.ok("Method started in a new thread.");
	}

	@PostMapping("/device/activation-renewal")
	public Boolean updateDeviceActivationExpiryDate(@RequestBody List<VehicleLazyEntity> request) {
		Boolean response = deviceService.updateDeviceActivationExpiryDate(request);
		return response;
	}

	@PostMapping("/get/All/device_packed")
	public ResponseEntity<?> getAllDevicePackedDevice(@RequestBody GenericRequestBody requestDTO) {
		Pagination<List<DeviceLiteV3>> deviceObj = deviceService.getAllDevicePackedDevice(requestDTO);
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	}

	@PostMapping("/device/unconfigure/notify")
	public void generateUnConfigureDevicesExcelAndNotify(@RequestBody GenericRequestBody requestDTO) throws Exception {
		deviceService.generateUnConfigureDevicesExcelAndNotify(requestDTO);
	}
	
	@PostMapping("/v1/get/device")
	public ResponseEntity<?> getDeviceByImeiNo(@RequestBody GenericRequestBody requestDTO) {
		//Boolean tokenValid = TokenUtility.validateToken(userId, token);
		//if (tokenValid) {
			Response<Device> deviceObj = deviceService.getDevice(requestDTO.getImeiNo());
			return new ResponseEntity<>(deviceObj, HttpStatus.OK);
		//} else {
		//	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		//}
	}
	
	@PostMapping("/v2/get/device")
	public ResponseEntity<?> getDeviceForReconfigure(@RequestBody GenericRequestBody requestDTO) {
		Pagination<List<DeviceLite>> deviceObj = deviceService.getDeviceForReconfigure(requestDTO);
		return new ResponseEntity<>(deviceObj, HttpStatus.OK);
	}
}
