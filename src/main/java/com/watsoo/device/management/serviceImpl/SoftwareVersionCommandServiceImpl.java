package com.watsoo.device.management.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.SoftwareVersionCommand;
import com.watsoo.device.management.repository.SoftwareVersionCommandRepository;
import com.watsoo.device.management.service.SoftwareVersionCommandService;

@Service
public class SoftwareVersionCommandServiceImpl implements SoftwareVersionCommandService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SoftwareVersionCommandRepository versionCommandRepository;

	@Override
	public Response<?> getAllSoftwareVersionCommand() {
		try {
			List<SoftwareVersionCommand> allVersion = versionCommandRepository.findAll();
			List<SoftwareVersionCommand> finalData = allVersion.stream()
					.filter(v -> !v.getSoftwareVersion().equalsIgnoreCase("REVERT")).collect(Collectors.toList());
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), finalData);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> getRevertCommand() {
		try {
			SoftwareVersionCommand response = versionCommandRepository.findRevertCommand();
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

}
