package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.StatePlatformMasterDTO;

public interface StatePlatformMasterService {

	List<StatePlatformMasterDTO> getAllPlatFormMaster();

	CustomResponse getAllStatePlateMstrById(Integer id);

	CustomResponse getAllStatePlatformByStateId(Long stateId);




}
