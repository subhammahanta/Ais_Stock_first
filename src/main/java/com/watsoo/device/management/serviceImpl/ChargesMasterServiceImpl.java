package com.watsoo.device.management.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.model.ChargesMaster;
import com.watsoo.device.management.repository.ChargesMasterRepository;
import com.watsoo.device.management.service.ChargesMasterService;

@Service
public class ChargesMasterServiceImpl implements ChargesMasterService {

	@Autowired
	private ChargesMasterRepository chargesMasterRepository;

	@Override
	public List<ChargesMaster> getAllCharges() {
		List<ChargesMaster> allCharges = chargesMasterRepository.findAll();
		return allCharges.stream().filter(o -> o != null && !o.getDeviceIssue().equalsIgnoreCase("newDevice"))
				.collect(Collectors.toList());
	}

}
