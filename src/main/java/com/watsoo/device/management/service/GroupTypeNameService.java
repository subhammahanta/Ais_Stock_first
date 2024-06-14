package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.GroupTypeNameDTO;
import com.watsoo.device.management.dto.Response;

public interface GroupTypeNameService {

	Response<?> getAllGroups();

	Response<?> getGroup(Long id);

}
