package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.dto.BoxDeviceDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;

public interface BoxService {

	BoxDTO saveBoxDetails(BoxDTO boxDto);

	Pagination<List<BoxDTO>> getAllBox(int pageNo, int pageSize, BoxDTO boxDTO);

	Response<BoxDeviceDTO> getById(Long id);

//	List<ProductTypeDTO> getAllProduct();
//
//	List<ProductStatusMappingDTO> getAllProductStatus(ProductStatusMappingDTO productStatusMappingDTO);
	BoxDTO saveBoxDetailsV2(BoxDTO boxDto);

	Pagination<List<BoxDTO>> getAllBoxLite(int pageNo, int pageSize, BoxDTO boxDto);

	Response<?> createBox(BoxDTO boxDto);

	Response<?> addDeviceInBox(BoxDTO boxDto);
}
