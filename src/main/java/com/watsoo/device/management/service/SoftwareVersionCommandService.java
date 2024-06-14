package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.Response;

public interface SoftwareVersionCommandService {

	Response<?> getAllSoftwareVersionCommand();

	Response<?> getRevertCommand();

}
