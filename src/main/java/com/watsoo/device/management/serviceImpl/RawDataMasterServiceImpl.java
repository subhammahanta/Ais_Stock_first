package com.watsoo.device.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.repository.RawDataRepository;
import com.watsoo.device.management.service.RawDataMasterService;

@Service
public class RawDataMasterServiceImpl implements RawDataMasterService {

	@Autowired
	private RawDataRepository rawDataRepository;

	@Override
	public Response<?> allServer() {
		return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), rawDataRepository.findAll());
	}


}
