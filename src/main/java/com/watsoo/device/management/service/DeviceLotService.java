package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.DeviceLot;
import com.watsoo.device.management.model.TestedDevice;

public interface DeviceLotService {

	Response<Object> addDeviceLot(DeviceCommandDTO deviceCommandDTO);

	Pagination<List<DeviceLot>> getAllDeviceLot(GenericRequestBody requestDTO);

	Response<Object> getAllDeviceByLotId(DeviceCommandDTO deviceCommandDTO);

	Pagination<List<TestedDevice>> getAllDeviceByLotIdFilter(GenericRequestBody request);

	DeviceLot getDeviceLotByLotId(GenericRequestBody request);

}
