package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.TestedDevice;

public interface TestedDeviceService {

	Pagination<List<TestedDevice>> getAllTestedDevice(GenericRequestBody requestDTO);

	Response<Boolean> rejectTestedDevice(GenericRequestBody requestDTO);

	Response<Object> getAllCommandResponseByDeviceId(DeviceCommandDTO requestDTO);

}
