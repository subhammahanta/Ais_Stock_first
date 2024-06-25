package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.*;
import com.watsoo.device.management.model.DeviceRenewalRequest;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface DeviceRenewalRequestService {

    Response<?> saveDeviceRenewalRequest(DeviceRenewalRequestDTO deviceRenewalRequestDTO);



    PaginationV2<?> findByCriteria(String search, Date fromDate, Date toDate, Integer pageNo, Integer pageSize);

    PaginationV2<?> getAllRenewalDeviceByRequestCode(String reqCode);
}
