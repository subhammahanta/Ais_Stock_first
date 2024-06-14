package com.watsoo.device.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watsoo.device.management.dto.DeviceSimDetailsDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateCmdMstrResponseDTO;
import com.watsoo.device.management.dto.TcPackets;
import com.watsoo.device.management.service.IssuedConfigurationService;

@RestController
@RequestMapping("api")
public class IssuedConfigurationController {
	@Autowired
	IssuedConfigurationService issuedConfigurationService;

	@GetMapping("get/not/config/issued/devices")
	public ResponseEntity<?> getNotConfigIssuedDevice() {
		Response<?> response = issuedConfigurationService.getNotConfigIssuedDevice();
		return new ResponseEntity<>(response.getData(), HttpStatus.OK);
	}

	@PostMapping("update/issued/device/packets/status")
	public ResponseEntity<?> updateIssuedDevicePacketStatus(@RequestBody List<TcPackets> tcPacketList) {
		Response<?> response = issuedConfigurationService.updateIssuedDevicePacketDetails(tcPacketList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("update/issued/device/config/status")
	public ResponseEntity<?> updateIssuedDeviceConfigStatus(@RequestBody List<TcPackets> tcPacketList) {
		Response<?> response = issuedConfigurationService.updateIssuedDeviceConfiguration(tcPacketList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("update/device/sim/details")
	public ResponseEntity<?> updateDeviceSimDetails(@RequestBody List<DeviceSimDetailsDTO> deviceSimDetailsDTOList) {
		Response<?> response = issuedConfigurationService.updateDeviceSimDetails(deviceSimDetailsDTOList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("override/device/sim/details")
	public ResponseEntity<?> overrideDeviceSimDetails(@RequestBody List<DeviceSimDetailsDTO> deviceSimDetailsDTOList) {
		Response<?> response = issuedConfigurationService.overrideDeviceSimDetails(deviceSimDetailsDTOList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("get/devices/command")
	public ResponseEntity<?> getDevicesCommands(@RequestBody StateCmdMstrResponseDTO stateCmdMstrResponseDTO) {
		if (stateCmdMstrResponseDTO != null && stateCmdMstrResponseDTO.getImeiNumberList() != null
				&& !stateCmdMstrResponseDTO.getImeiNumberList().isEmpty()) {
			Response<?> response = issuedConfigurationService.getDevicesCommands(stateCmdMstrResponseDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("box/unboxing")
	public ResponseEntity<?> boxUnboxing(@RequestBody List<Long> boxIds) {

		Response<?> resoponse = issuedConfigurationService.boxUnboxing(boxIds);
		return new ResponseEntity<>(resoponse, HttpStatus.OK);
	}

}
