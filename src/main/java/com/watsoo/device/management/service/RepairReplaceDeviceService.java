package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.RepairReplaceDeviceMasterDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.RepairReplaceDeviceMaster;

public interface RepairReplaceDeviceService {

	Response<?> repairDevices(GenericRequestBody dto);

	Pagination<List<RepairReplaceDeviceMaster>> getAllRepairDeviceRequest(GenericRequestBody request);

	Response<?> addRepairDevice(GenericRequestBody dto);

	Response<?> repairDevice(GenericRequestBody dto);

	Pagination<List<RepairReplaceDeviceMasterDTO>> getAllRepairDeviceRequestV2(GenericRequestBody request);

	Response<?> getRepairCharges(GenericRequestBody dto);

}
