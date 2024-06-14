package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;

public interface CommandTransactionsService {

	Response<String> shootCommand(DeviceCommandDTO deviceCommandDTO);

	void updateDeviceCommandChangeTrail(DeviceCommandDTO deviceCommandDTO);
}
