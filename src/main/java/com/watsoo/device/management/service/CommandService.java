package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.CommandRequestDto;
import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;

public interface CommandService {

	Response<String> shootCoreCommandDevice(DeviceCommandDTO deviceCommandDTO);

	Response<Object> getCommand(DeviceCommandDTO deviceCommandDTO);

	Response<Object> getAllCommand(DeviceCommandDTO deviceCommandDTO);

	Response<Object> saveCommandResponse(DeviceCommandDTO deviceCommandDTO);

	Response<Object> saveTestedDevice(DeviceCommandDTO deviceCommandDTO);
	
	CommandRequestDto shootCommandByList(CommandRequestDto cmdDto);


	public void shootCommandsSchedular();



}
