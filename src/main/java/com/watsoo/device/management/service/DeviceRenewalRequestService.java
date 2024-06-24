package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.DeviceRenewalRequestDTO;
import com.watsoo.device.management.dto.DeviceRenewalResponseDTO;
import com.watsoo.device.management.dto.Response;

public interface DeviceRenewalRequestService {

    Response<?> saveDeviceRenewalRequest(DeviceRenewalRequestDTO deviceRenewalRequestDTO);

    Response<?> getRenewalDevices(Integer pageNo,Integer pageSize);
}
