package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.RoleDTO;
import com.watsoo.device.management.model.Role;

public interface RoleService {

	Response<?> getRoleDTO(Role role);

	Response<?> getAllRole(Long userId);

	Response<?> addRole(RoleDTO roleDTO);

	Response<?> updateRole(RoleDTO roleDTO);

	Response<?> getRole(Long roleId);

}
