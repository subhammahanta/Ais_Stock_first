package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.UserDTO;

public interface UserService {

	Response<?> findUserById(Long id);

	Response<?> createUser(UserDTO userDTO);

}
