//package com.watsoo.device.management.serviceImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import com.watsoo.device.management.dto.Pagination;
//import com.watsoo.device.management.dto.SearchDTO;
//import com.watsoo.device.management.model.Device;
//import com.watsoo.device.management.model.ProductStatusMapping;
//import com.watsoo.device.management.repository.ProductStatusMappingRepository;
//import com.watsoo.device.management.scheduler.ProductStatusMappingService;
//
//@Service
//public class ProductStatusMappingServiceImpl implements ProductStatusMappingService {
//	
//	@Autowired
//	private ProductStatusMappingRepository productStatusMappingRepository;
//
//	@Override
//	public List<Device> getAllDevice(SearchDTO searchDTO) {
//		Pagination<List<Device>> response = new Pagination<>();
//		List<Device> deviceList = new ArrayList<>();
//		try {
//			Pageable pageRequest = Pageable.unpaged();
//			if (searchDTO.getPageSize() != null && searchDTO.getPageSize() > 0) {
//				pageRequest = PageRequest.of(searchDTO.getPageNo(), searchDTO.getPageSize(),
//						Sort.by("id").descending());
//			}
//			Page<ProductStatusMapping> page = productStatusMappingRepository.findAll(searchDTO, pageRequest);
//		} catch (Exception e) {
//
//		}
//		return null;
//	}
//
//}
