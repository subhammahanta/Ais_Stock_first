package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;

public interface ModelConfigService {

	Response<Object> checkModelConfig(DeviceCommandDTO deviceCommandDTO);

	Response<Object> addModelConfig(DeviceCommandDTO deviceCommandDTO);

	Response<Object> addModelConfigV2(DeviceCommandDTO deviceCommandDTO);

}
