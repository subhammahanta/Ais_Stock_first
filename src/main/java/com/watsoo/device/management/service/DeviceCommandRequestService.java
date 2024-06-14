package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.AllCommandRequestResponse;
import com.watsoo.device.management.dto.CommandRequestDTO;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;

public interface DeviceCommandRequestService {

	Response<Object> commandRequest(DeviceCommandDTO deviceCommandDTO);

	AllCommandRequestResponse getAllCommandRequest(int pageNo, int pageSize, CommandRequestDTO dto);
	
	Response<CommandRequestDTO> getCommandRequestById(CommandRequestDTO dto);

	Response<?> revertCommandRequest(DeviceCommandDTO deviceCommandDTO);
}
