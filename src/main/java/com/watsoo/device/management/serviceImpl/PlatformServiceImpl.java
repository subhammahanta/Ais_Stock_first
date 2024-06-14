package com.watsoo.device.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.repository.PlatformRepository;
import com.watsoo.device.management.service.PlatformService;

@Service
public class PlatformServiceImpl implements PlatformService {

	@Autowired
	private PlatformRepository platformRepository;

	@Override
	public Response<Object> allPlatform() {
		return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
				platformRepository.findAll());
	}

}
