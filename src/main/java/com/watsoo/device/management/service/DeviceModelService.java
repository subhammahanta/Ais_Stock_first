package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;

public interface DeviceModelService {

	Response<Object> addDeviceModel(DeviceCommandDTO deviceCommandDTO);

}
