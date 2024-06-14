package com.watsoo.device.management.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.DeviceModel;
import com.watsoo.device.management.repository.DeviceModelRepository;
import com.watsoo.device.management.service.DeviceModelService;

@Service
public class DeviceModelServiceImpl implements DeviceModelService {

	@Autowired
	private DeviceModelRepository modelRepository;

	@Override
	public Response<Object> addDeviceModel(DeviceCommandDTO deviceCommandDTO) {
		try {
			DeviceModel deviceModel = null;
			if (deviceCommandDTO.getDeviceModelId() != null) {
				Optional<DeviceModel> deviceModelOp = modelRepository.findById(deviceCommandDTO.getDeviceModelId());
				deviceModel = deviceModelOp.get();
				deviceModel.setUpdatedAt(new Date());
				deviceModel.setUpdatedBy(deviceCommandDTO.getUserId());
			} else {
				deviceModel = new DeviceModel();
				deviceModel.setCreatedAt(new Date());
				deviceModel.setCreatedBy(deviceCommandDTO.getUserId());
				deviceModel.setIsActive(true);
			}
			deviceModel.setTacNo(deviceCommandDTO.getTacNo());
			deviceModel.setModel(deviceCommandDTO.getDeviceModel());
			modelRepository.save(deviceModel);
			return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

}
