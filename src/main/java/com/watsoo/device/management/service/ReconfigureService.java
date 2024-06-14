package com.watsoo.device.management.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.PaginationV2;
import com.watsoo.device.management.dto.ReconfigureDeviceResponseDTO;
import com.watsoo.device.management.dto.Response;

public interface ReconfigureService {

	Response<?> saveDeviceToReconfigure(GenericRequestBody request);

	ResponseEntity<?> getAllReconfigureDevice();

	PaginationV2<Map<String, List<ReconfigureDeviceResponseDTO>>> getAllReconfigureDeviceV2(GenericRequestBody request);

	Response<?> getDeviceInfo(GenericRequestBody request);

	Response<?> getDeviceInfoV2(GenericRequestBody request);

}
