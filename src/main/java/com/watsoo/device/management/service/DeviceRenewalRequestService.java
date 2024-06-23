package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.DeviceRenewalRequestDTO;
import com.watsoo.device.management.dto.DeviceRenewalResponseDTO;
import com.watsoo.device.management.dto.Response;

import java.util.List;

public interface DeviceRenewalRequestService {

    Response<Object> saveDeviceRenewalRequest(DeviceRenewalRequestDTO deviceRenewalRequestDTO);

    Response<?> getRenewalDevicesById(Long requestId);
}
