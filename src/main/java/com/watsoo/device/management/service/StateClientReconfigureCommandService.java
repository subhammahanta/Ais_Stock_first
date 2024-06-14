package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Response;

public interface StateClientReconfigureCommandService {

	Response<?> getStateClientCommand(GenericRequestBody request);

}
