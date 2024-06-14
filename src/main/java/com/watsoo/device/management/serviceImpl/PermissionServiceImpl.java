package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.PermissionDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.Permission;
import com.watsoo.device.management.repository.PermissionRepository;
import com.watsoo.device.management.service.PermisionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermisionService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Response<?> getAllPermission(Long id) {
		try {
			List<PermissionDTO> response = new ArrayList<>();
			List<Permission> permissionList = permissionRepository.getByGroupId(id, true);
			for (Permission permission : permissionList) {
				if (permission.getUrl() != null) {
					PermissionDTO dto = new PermissionDTO(permission.getName(), permission.getUrl());
					response.add(dto);
				}
			}
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}
}
