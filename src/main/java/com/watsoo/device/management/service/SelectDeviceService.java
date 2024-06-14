package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.IssueDeviceDTO;

public interface SelectDeviceService {

	Response<?> saveData(IssueDeviceDTO selectDeviceDTO);

}
