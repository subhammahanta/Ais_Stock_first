package com.watsoo.device.management.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.Permission;
import com.watsoo.device.management.model.PermissionCategory;
import com.watsoo.device.management.repository.PermissionRepository;
import com.watsoo.device.management.service.PermissionCategoryService;

@Service
@Transactional
public class PermissionCategoryServiceImpl implements PermissionCategoryService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Response<?> getAllPermissionCategory() {
		try {
			List<Permission> permissionList = permissionRepository.findAll();
			Map<PermissionCategory, List<Permission>> permissionCategoryMap = permissionList.stream()
					.collect(Collectors.groupingBy(Permission::getPermissionCategory));

			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
					permissionCategoryMap.entrySet().stream().filter(e -> e.getKey().getIsActive())
							.map(e -> e.getKey().permissionCategoryToDTO(e.getValue())).collect(Collectors.toList()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}
}
