package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;
import com.watsoo.device.management.dto.SubscriptionTypeDTO;
import com.watsoo.device.management.model.SubscriptionMaster;
import com.watsoo.device.management.model.SubscriptionType;
import com.watsoo.device.management.repository.SubscriptionTypeRepository;
import com.watsoo.device.management.service.SubscriptionTypeService;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService
{

	@Autowired
	private SubscriptionTypeRepository subscriptionTypeRepository;
	
	@Override
	public List<SubscriptionTypeDTO> getAllSubscriptionType() {
		List<SubscriptionType> subscriptionTypeList=subscriptionTypeRepository.findAll();
		List<SubscriptionTypeDTO> list=new ArrayList<>();
		
		for(SubscriptionType subscriptionType:subscriptionTypeList)
		{
			SubscriptionTypeDTO subscriptionTypeDTO=subscriptionType.convertEntityToDTO(subscriptionType);
			list.add(subscriptionTypeDTO);
		}
		
		return list;
	}

}
