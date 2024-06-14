package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.PermissionCategoryDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.RoleDTO;
import com.watsoo.device.management.model.Permission;
import com.watsoo.device.management.model.PermissionCategory;
import com.watsoo.device.management.model.Role;
import com.watsoo.device.management.model.RolePermission;
import com.watsoo.device.management.repository.PermissionCatagoryRepository;
import com.watsoo.device.management.repository.PermissionRepository;
import com.watsoo.device.management.repository.RolePermissionRepository;
import com.watsoo.device.management.repository.RoleRepository;
import com.watsoo.device.management.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionCatagoryRepository permissionCategoryRepository;

	@Override
	public Response<?> addRole(RoleDTO roleDTO) {
		try {
			Optional<Role> roleOptional = roleRepository.findByNameAndCompanyId(roleDTO.getName(),
					roleDTO.getCompanyId());
			if (roleOptional.isPresent()) {
				new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Already exist with given name and company.", null);
			}

			Role role = roleDTO.convertDTOToEntity();
			role = roleRepository.save(role);

			List<RolePermission> rolePermissionList = new ArrayList<>();
			if (!roleDTO.getWebPermissionIdList().isEmpty()) {
				List<Permission> permissionList = permissionRepository.findAllById(roleDTO.getWebPermissionIdList());
				for (Permission permission : permissionList) {
					rolePermissionList.add(new RolePermission(role, permission, null, false, true, true));
				}
			}

			if (!roleDTO.getMobilePermissionIdList().isEmpty()) {
				List<Permission> permissionList = permissionRepository.findAllById(roleDTO.getMobilePermissionIdList());
				for (Permission permission : permissionList) {
					rolePermissionList.add(new RolePermission(role, permission, null, true, false, true));
				}
			}

			if (!roleDTO.getWebPermissionCategoryIdList().isEmpty()) {
				List<PermissionCategory> permissionCategoryList = permissionCategoryRepository
						.findAllById(roleDTO.getWebPermissionCategoryIdList());
				for (PermissionCategory permissionCategory : permissionCategoryList) {
					rolePermissionList.add(new RolePermission(role, null, permissionCategory, false, true, true));
				}
			}

			if (!roleDTO.getMobilePermissionCategoryIdList().isEmpty()) {
				List<PermissionCategory> permissionCategoryList = permissionCategoryRepository
						.findAllById(roleDTO.getMobilePermissionCategoryIdList());
				for (PermissionCategory permissionCategory : permissionCategoryList) {
					rolePermissionList.add(new RolePermission(role, null, permissionCategory, true, false, true));
				}
			}

			if (!rolePermissionList.isEmpty()) {
				rolePermissionRepository.saveAll(rolePermissionList);
			}

			return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), role);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> updateRole(RoleDTO roleDTO) {
		try {
			Optional<Role> roleOptional = roleRepository.findById(roleDTO.getId());
			if (!roleOptional.isPresent()) {
				throw new RuntimeException("role not found");
			}
			Role role = roleDTO.convertDTOToEntity();
			role = roleRepository.save(role);

			if (!roleDTO.getWebPermissionIdList().isEmpty() || !roleDTO.getMobilePermissionIdList().isEmpty()) {

				List<RolePermission> alreadyPresent = rolePermissionRepository.findByRole(role);
				List<RolePermission> rolePermissionList = new ArrayList<>();

				if (!roleDTO.getWebPermissionIdList().isEmpty()) {
					Set<Long> webPermissionId = roleDTO.getWebPermissionIdList();
					for (RolePermission rolePermission : alreadyPresent) {
						if (rolePermission.getPermission() != null
								&& webPermissionId.contains(rolePermission.getPermission().getId())) {
							webPermissionId.remove(rolePermission.getPermission().getId());
							alreadyPresent.remove(rolePermission);
						}
					}

					List<Permission> permissionList = permissionRepository.findAllById(webPermissionId);
					for (Permission permission : permissionList) {
						rolePermissionList.add(new RolePermission(role, permission, null, false, true, true));
					}
				}

				if (!roleDTO.getMobilePermissionIdList().isEmpty()) {
					Set<Long> mobilePermissionId = roleDTO.getMobilePermissionIdList();
					for (RolePermission rolePermission : alreadyPresent) {
						if (rolePermission.getPermission() != null
								&& mobilePermissionId.contains(rolePermission.getPermission().getId())) {
							mobilePermissionId.remove(rolePermission.getPermission().getId());
							alreadyPresent.remove(rolePermission);
						}
					}

					List<Permission> permissionList = permissionRepository.findAllById(mobilePermissionId);
					for (Permission permission : permissionList) {
						rolePermissionList.add(new RolePermission(role, permission, null, true, false, true));
					}
				}

				if (!alreadyPresent.isEmpty()) {
					rolePermissionRepository.deleteAll(alreadyPresent);
				}

				rolePermissionRepository.saveAll(rolePermissionList);
			}

			return new Response<>(HttpStatus.OK.value(), "Role updated successfully!!", role);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> getAllRole(Long companyId) {
		try {
			List<?> collect = roleRepository.findByCompanyId(companyId).stream().filter(Role::getIsActive)
					.map(this::getRoleDTO).collect(Collectors.toList());
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), collect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	public Response<?> getRoleDTO(Role role) {
		try {
			List<RolePermission> rolePermissionList = rolePermissionRepository.findByRole(role);

			List<Permission> webPermission = rolePermissionList.stream().filter(RolePermission::getForWeb)
					.map(RolePermission::getPermission).filter(Objects::nonNull).collect(Collectors.toList());

			List<Permission> mobilePermission = rolePermissionList.stream().filter(RolePermission::getForMobile)
					.map(RolePermission::getPermission).filter(Objects::nonNull).collect(Collectors.toList());

			List<PermissionCategoryDTO> webPermissionCategoryList = rolePermissionList.stream()
					.filter(RolePermission::getForWeb).map(RolePermission::getPermissionCatagory)
					.filter(Objects::nonNull)
					.map(permissionCategory -> permissionCategory.permissionCategoryToDTO(
							permissionRepository.getByPermissionCategoryId(permissionCategory.getId())))
					.collect(Collectors.toList());

			List<PermissionCategoryDTO> mobilePermissionCategoryList = rolePermissionList.stream()
					.filter(RolePermission::getForMobile).map(RolePermission::getPermissionCatagory)
					.filter(Objects::nonNull)
					.map(permissionCategory -> permissionCategory.permissionCategoryToDTO(
							permissionRepository.getByPermissionCategoryId(permissionCategory.getId())))
					.collect(Collectors.toList());

			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), role.convertToDTO(webPermission,
					mobilePermission, webPermissionCategoryList, mobilePermissionCategoryList));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> getRole(Long roleId) {
		try {
			Optional<Role> roleOptional = roleRepository.findById(roleId);
			if (!roleOptional.isPresent()) {
				throw new RuntimeException("Role not found.");
			}
			Role role = roleOptional.get();
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), getRoleDTO(role));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}
}
