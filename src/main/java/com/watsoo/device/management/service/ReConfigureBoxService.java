package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.BoxDeviceDTO;
import com.watsoo.device.management.dto.BoxDeviceResponseDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.ReConfigureBoxDTO;
import com.watsoo.device.management.dto.ReConfigureDevicesDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.ReConfigureCommand;

public interface ReConfigureBoxService {

	Response<?> unboxDevice(GenericRequestBody request);

	Response<?> saveCommandResponse(GenericRequestBody request);

	Response<?> checkReConfigureCommandExist(GenericRequestBody request);

	Response<?> saveReConfigureCommand(GenericRequestBody request);

	Response<?> reconfigureDevice(GenericRequestBody request);

	Response<?> finishReconfigure(GenericRequestBody request);

	Response<BoxDeviceResponseDTO> getBoxDevice(GenericRequestBody request);

	Pagination<List<ReConfigureBoxDTO>> getAllReConfigureBox(GenericRequestBody request);

	Pagination<List<ReConfigureDevicesDTO>> getAllReConfigureBoxDevices(GenericRequestBody request);

	Response<BoxDeviceDTO> getBoxDeviceV2(GenericRequestBody request);

	Response<List<ReConfigureCommand>> getAllCommands(GenericRequestBody request);

	Response<?> finishReconfigureV2(GenericRequestBody request);

	Response<?> saveReConfigureCommandV2(GenericRequestBody request);

	Response<?> getDeviceInfoV2(GenericRequestBody request);

//	Response<?> addReconfigureDevice(GenericRequestBody request);

	Response<?> reconfigureDeviceV2(GenericRequestBody request);

}
