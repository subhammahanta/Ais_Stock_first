package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.CommandRequestMasterDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;

public interface CommandRequestMasterService {

	Pagination<List<CommandRequestMasterDTO>> getAllCommands(GenericRequestBody requestDTO);

	List<CommandRequestMasterDTO> findAll();

}
