package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;

public interface SubscriptionMasterService {

	List<SubscriptionMasterDTO> getAllSubscriptionMaster();

	CustomResponse getAllSubscriptionMasterById(Integer id);

	Pagination<List<SubscriptionMasterDTO>> getAllSubscription(GenericRequestBody request);

}
