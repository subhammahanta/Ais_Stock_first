package com.watsoo.device.management.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.CommandStatusEnum;
import com.watsoo.device.management.model.DeviceLotMaster;
import com.watsoo.device.management.model.TestedDevice;
import com.watsoo.device.management.repository.DeviceLiteV2Repository;
import com.watsoo.device.management.repository.DeviceLotMasterRepository;
import com.watsoo.device.management.repository.DeviceLotRepository;
import com.watsoo.device.management.repository.TestedDeviceRepository;
import com.watsoo.device.management.service.TestedDeviceService;

@Service
public class TestedDeviceServiceImpl implements TestedDeviceService {

	@Autowired
	private TestedDeviceRepository testedDeviceRepository;

	@Autowired
	private DeviceLiteV2Repository deviceRepository;

	@Autowired
	private DeviceLotRepository lotRepository;

	@Autowired
	private DeviceLotMasterRepository lotMasterRepository;

	@Override
	public Pagination<List<TestedDevice>> getAllTestedDevice(GenericRequestBody requestDTO) {
		Pagination<List<TestedDevice>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
				pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
						Sort.by("id").descending());
			}
			Page<TestedDevice> page = testedDeviceRepository.findAll(requestDTO, pageRequest);
			response.setData(page.getContent());
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response<Boolean> rejectTestedDevice(GenericRequestBody requestDTO) {
		TestedDevice testedDevice = testedDeviceRepository.findByImeiNo(requestDTO.getImeiNo().trim());
		Boolean res = false;
		// lot master update
		if (testedDevice != null && testedDevice.getIsTestingCompleted() == null) {
			testedDevice.setIsActive(false);
			testedDevice.setIsRejected(true);
			testedDevice.setRejectedBy(requestDTO.getUserId());
			testedDevice.setRemark(requestDTO.getRemark());
			testedDeviceRepository.save(testedDevice);
			DeviceLotMaster lotMaster = new DeviceLotMaster();
			lotMaster.setLotId(testedDevice.getLotId());
			lotMaster.setCommand("MODEL CONFIGURATION CHECK");
			lotMaster.setResponse("DEVICE REJECTED AFTER MODEL CONFIGURATION");
			lotMaster.setCreatedAt(new Date());
			lotMaster.setCreatedBy(requestDTO.getUserId());
			lotMaster.setIsActive(true);
			lotMaster.setStatus(CommandStatusEnum.REJECT);
			lotMaster.setTestedDeviceId(testedDevice.getId());
			lotMaster.setFailureReason(requestDTO.getRemark());
			lotMasterRepository.save(lotMaster);
			res = true;
			return new Response<>(HttpStatus.OK.value(), "DEVICE REJECTED SUCCESSFULLY", res);
		} else if (testedDevice != null && testedDevice.getIsTestingCompleted() != null
				&& testedDevice.getIsTestingCompleted() == true) {
			// delete from device and reject in testdevice update lot quantity
			lotRepository.updateLotQuantity(testedDevice.getLotId());
			deviceRepository.deleteById(testedDevice.getDeviceId());
			testedDevice.setIsActive(false);
			testedDevice.setIsRejected(true);
			testedDevice.setRejectedBy(requestDTO.getUserId());
			testedDevice.setRemark(requestDTO.getRemark());
			testedDevice.setDeviceId(null);
			testedDeviceRepository.save(testedDevice);
			DeviceLotMaster lotMaster = new DeviceLotMaster();
			lotMaster.setLotId(testedDevice.getLotId());
			lotMaster.setCommand("QUALITY CHECK");
			lotMaster.setResponse("DEVICE REJECTED AT QUALITY CHECK");
			lotMaster.setCreatedAt(new Date());
			lotMaster.setCreatedBy(requestDTO.getUserId());
			lotMaster.setIsActive(true);
			lotMaster.setStatus(CommandStatusEnum.REJECT);
			lotMaster.setTestedDeviceId(testedDevice.getId());
			lotMaster.setFailureReason(requestDTO.getRemark());
			lotMasterRepository.save(lotMaster);
			res = true;
			return new Response<>(HttpStatus.OK.value(), "DEVICE REJECTED SUCCESSFULLY", res);
		} else {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "DEVICE NOT FOUND", res);
		}

	}

	@Override
	public Response<Object> getAllCommandResponseByDeviceId(DeviceCommandDTO requestDTO) {
		if (requestDTO.getLotId() != null && requestDTO.getTestDeviceId() != null) {
			List<DeviceLotMaster> lotMasters = lotMasterRepository
					.getAllCommandResponseByDeviceId(requestDTO.getLotId(), requestDTO.getTestDeviceId());
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), lotMasters);
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
	}

}
