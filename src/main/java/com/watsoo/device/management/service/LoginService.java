package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.LoginRequest;
import com.watsoo.device.management.dto.LoginResponse;
import com.watsoo.device.management.dto.Response;

public interface LoginService {

	Response<LoginResponse> login(LoginResponse loginDTO);

	LoginResponse changePasswordV2(LoginResponse loginRequest);

	Response<?> loginV2(LoginRequest loginDTO) throws Exception;

	Response<?> loginV3(LoginRequest loginRequest);

	Response<?> loginV4(LoginRequest loginRequest);

}
