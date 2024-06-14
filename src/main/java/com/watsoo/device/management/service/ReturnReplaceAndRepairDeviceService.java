package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.ReturnReplaceRepairDTO;
import com.watsoo.device.management.model.ReturnReplaceRepair;

public interface ReturnReplaceAndRepairDeviceService {

	Response<?> addForReturnOrReplaceOrRepair(ReturnReplaceRepairDTO dto);

	Pagination<List<ReturnReplaceRepair>> getAllReturnReplaceRepairDevices(int pageNo, int pageSize,
			ReturnReplaceRepairDTO dto);

	Response<?> repairDevice(ReturnReplaceRepairDTO dto);

}
