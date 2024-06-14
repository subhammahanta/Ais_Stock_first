package com.watsoo.device.management.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.model.IccidMaster;

public interface IccidMasterService {
	
	Pagination<List<IccidMaster>> getAllIccidMaster(GenericRequestBody requestDTO);

	Map<Integer, String> addIccidFromExcelFile(MultipartFile file, Long userId);

}
