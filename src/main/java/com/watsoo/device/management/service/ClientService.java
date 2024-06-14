package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.ClientDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;

public interface ClientService {

	Response<?> saveOrUpdateCompany(ClientDTO clientDTO);

	Response<?> getAllOrGetById(Long id, int pageNo, int pageSize);

	Response<?> getAll();

	Response<?> getById(Long id);

	Pagination<List<ClientDTO>> getAllClient(int pageNo, int pageSize, ClientDTO clientDTO);

	Response<?> addAisAdminUser(ClientDTO clientDTO);

}
