package com.watsoo.device.management.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.GroupTypeNameDTO;
import com.watsoo.device.management.dto.PermissionDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.GroupTypeName;
import com.watsoo.device.management.repository.GroupTypeNameRepository;
import com.watsoo.device.management.service.GroupTypeNameService;
import com.watsoo.device.management.service.PermisionService;

@Service
public class GroupTypeNameServiceImpl implements GroupTypeNameService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GroupTypeNameRepository groupTypeNameRepository;

	@Autowired
	private PermisionService permisionService;

	@Override
	public Response<?> getAllGroups() {
		try {
			List<GroupTypeNameDTO> groupDTOList = groupTypeNameRepository.findAll().stream().map(e -> e.convertToDTO())
					.collect(Collectors.toList());
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), groupDTOList);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> getGroup(Long id) {
		try {
			GroupTypeNameDTO response = new GroupTypeNameDTO();
			Optional<GroupTypeName> name = groupTypeNameRepository.findById(id);
			if (name.isPresent()) {
				List<PermissionDTO> permissionList = (List<PermissionDTO>) permisionService.getAllPermission(id)
						.getData();
				response.setId(name.get().getId());
				response.setName(name.get().getName());
				response.setPermissionDTOList(permissionList);
			}
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

}
