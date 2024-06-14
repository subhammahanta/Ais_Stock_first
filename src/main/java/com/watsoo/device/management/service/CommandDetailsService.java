package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.CommandsDetailsDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;

public interface CommandDetailsService {

	Pagination<List<CommandsDetailsDTO>> getAllCommandDetails(GenericRequestBody requestDTO);

	List<CommandsDetailsDTO> findAllByMasterId(Long masterId);

	Response<Object> generateConfigureDevicesExcelAndNotify(GenericRequestBody requestDTO);

}
