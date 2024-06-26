package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.DeviceRenewalRequestDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.PaginationV2;
import com.watsoo.device.management.dto.Response;

public interface DeviceRenewalRequestService {

    Response<?> saveDeviceRenewalRequest(DeviceRenewalRequestDTO deviceRenewalRequestDTO);

    PaginationV2<?> getDeviceRenewalRequest(GenericRequestBody genericRequestBody);

    PaginationV2<?> getDeviceRenewalRequest(String reqCode,int pageNo,int pageSize);
}
