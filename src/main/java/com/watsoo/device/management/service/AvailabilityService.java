package com.watsoo.device.management.service;

import com.watsoo.device.management.dto.BoxRequestDTO;
import com.watsoo.device.management.dto.Response;


public interface AvailabilityService {

	Response<?> checkDeviceAvailability(int requestedQuantity);

	Response<?> checkDeviceAvailabilityV2(BoxRequestDTO dto);

	Response<?> checkDeviceAvailabilityV3(BoxRequestDTO boxRequestDTO);

	Response<?> checkDeviceAvailabilityV4(BoxRequestDTO boxRequestDTO);


}
