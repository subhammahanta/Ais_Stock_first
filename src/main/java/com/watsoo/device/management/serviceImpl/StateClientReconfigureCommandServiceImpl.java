package com.watsoo.device.management.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.StateClientReconfigureCommand;
import com.watsoo.device.management.repository.StateClientReconfigureCommandRepository;
import com.watsoo.device.management.service.StateClientReconfigureCommandService;

@Service
public class StateClientReconfigureCommandServiceImpl implements StateClientReconfigureCommandService {

	@Autowired
	private StateClientReconfigureCommandRepository stateClientReconfigureCommandRepository;

	@Override
	public Response<?> getStateClientCommand(GenericRequestBody request) {
		if (request.getClientId() == null || request.getStateId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
		List<StateClientReconfigureCommand> stateClientReconfigureCommand = stateClientReconfigureCommandRepository
				.findByStateIdAndClientId(request.getStateId(), request.getClientId());
		if (stateClientReconfigureCommand == null || stateClientReconfigureCommand.isEmpty()) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "NO COMMAND FOUND!! ADD COMMAND FIRST..");
		}
		return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
				stateClientReconfigureCommand.get(0));
	}

}
