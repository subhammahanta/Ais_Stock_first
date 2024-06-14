package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.ReturnDeviceMaster;

public interface ReturnDeviceService {

	Response<?> returnDevices(GenericRequestBody dto);

	Pagination<List<ReturnDeviceMaster>> getAllReturnDeviceRequest(GenericRequestBody request);

	Response<?> addReturnDevice(GenericRequestBody dto);

}
