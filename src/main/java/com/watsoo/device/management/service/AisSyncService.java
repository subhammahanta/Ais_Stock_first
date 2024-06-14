package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.DeviceLazyEntity;

public interface AisSyncService {
	
	public List<DeviceLazyEntity> getAllDevice(GenericRequestBody requestDTO);

	public CustomResponse updateDeviceExpiry(GenericRequestBody requestDTO);

	public Object activateDevice(GenericRequestBody requestDTO);
}
