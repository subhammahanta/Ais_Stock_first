package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.ClientSubscriptionMasterDTO;
import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;

public interface ClientSubscriptionMasterService {

	List<ClientSubscriptionMasterDTO> getAllClientSubsMaster();

	CustomResponse getClentSubsMstrById(Integer id);

	CustomResponse getAllClentSubsMstrByClientIdAndStateId(Long clientId, Long stateId);

	Response<Object> getClientSubscription(GenericRequestBody request);

	Response<Object> checkClientSubscription(GenericRequestBody request);

	Response<Object> addClientSubscription(GenericRequestBody request);

	Pagination<List<ClientSubscriptionMasterDTO>> getAllClientSubscription(GenericRequestBody request);

}
