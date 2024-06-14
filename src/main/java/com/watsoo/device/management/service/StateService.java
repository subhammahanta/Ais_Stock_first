package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateCommandDTO;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.StateCmdMstrEntity;

public interface StateService {

	List<State> getAllStates();

	Object getById(Long id);

	List<StateCmdMstrEntity> getAllStateCmd();

	Pagination<List<StateCommandDTO>> getStateCommand(int pageNo, int pageSize, StateCommandDTO dto);

	Response<?> addUpdateConfig(StateCommandDTO dto);

}
