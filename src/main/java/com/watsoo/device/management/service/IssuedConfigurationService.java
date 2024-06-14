package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.DeviceSimDetailsDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateCmdMstrResponseDTO;
import com.watsoo.device.management.dto.TcPackets;

public interface IssuedConfigurationService {

	// Response<?> getIssuedDeviceByImeiNumber(DeviceDTO deviceDto);

	// Response<?> updateIssuedDeviceConfiguration(DeviceDTO deviceDto);

	Response<?> getNotConfigIssuedDevice();

	Response<?> updateIssuedDeviceConfiguration(List<TcPackets> tcPacketList);

	Response<?> updateIssuedDevicePacketDetails(List<TcPackets> tcPacketList);

	Response<?> updateDeviceSimDetails(List<DeviceSimDetailsDTO> deviceSimDetailsDTOList);

	Response<?> overrideDeviceSimDetails(List<DeviceSimDetailsDTO> deviceSimDetailsDTOList);

	Response<?> getDevicesCommands(StateCmdMstrResponseDTO stateCmdMstrResponseDTO);

	Response<?> boxUnboxing(List<Long> boxIds);

}
