package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.DeviceLazyEntity;
import com.watsoo.device.management.repository.DeviceLazyRepository;
import com.watsoo.device.management.service.AisSyncService;
import com.watsoo.device.management.util.DateUtil;

@Service
public class AisSyncServiceImpl implements AisSyncService {

	@Autowired
	private DeviceLazyRepository deviceLazyRepo;

	@Override
	public List<DeviceLazyEntity> getAllDevice(GenericRequestBody requestDTO) {
		List<DeviceLazyEntity> deviceLazyList = new ArrayList<>();
		try {
			Date currentDate = new Date();
			Date thresholdDate = DateUtil.addDaysToDate(currentDate,
					requestDTO.getMinRenwalRequireDays() != null ? requestDTO.getMinRenwalRequireDays()
							: Constant.DEFAULT_MIN_DEVICE_RENWAL_NOTIFY_DAYS);
			deviceLazyList = deviceLazyRepo.findAll();
			for (DeviceLazyEntity deviceLazyEntity : deviceLazyList) {
				if (deviceLazyEntity.getSim1ExpiryDate() != null) {
					if (deviceLazyEntity.getSim1ExpiryDate().before(new Date())) {
						deviceLazyEntity.setIsExpired(true);
						deviceLazyEntity.setIsRenewalRequired(true);
					}
					if (deviceLazyEntity.getSim1ExpiryDate().after(currentDate)
							&& deviceLazyEntity.getSim1ExpiryDate().before(thresholdDate)) {
						deviceLazyEntity.setIsRenewalRequired(true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceLazyList;
	}

	@Override
	public CustomResponse updateDeviceExpiry(GenericRequestBody requestDTO) {
		CustomResponse response = null;
		try {
			if (requestDTO.getImeiNo() != null) {
				Optional<DeviceLazyEntity> device = deviceLazyRepo.findByImeiNo(requestDTO.getImeiNo());
				if (device.isPresent()) {
					if (requestDTO.getAddSimExpireDays() != null) {
						Date simUpdateExpireDate = DateUtil.addDaysToDate(device.get().getSim1ExpiryDate(),
								requestDTO.getAddSimExpireDays());
						device.get().setSim1ExpiryDate(simUpdateExpireDate);
						device.get().setSim2ExpiryDate(simUpdateExpireDate);
						device.get().setLastRenewalDate(new Date());
					}
					if (requestDTO.getAddAppExpireDays() != null) {
						Date appUpdateExpireDate = DateUtil.addDaysToDate(device.get().getAppExpiryDate(),
								requestDTO.getAddAppExpireDays());
						device.get().setAppExpiryDate(appUpdateExpireDate);
					}
					DeviceLazyEntity updatedDevice = deviceLazyRepo.save(device.get());
					response = new CustomResponse(HttpStatus.OK.value(), updatedDevice,
							HttpStatus.OK.getReasonPhrase());
				}
			} else {
				response = new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
						HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return response;
	}

	@Override
	public Object activateDevice(GenericRequestBody requestDTO) {
		CustomResponse response = null;
		try {
			if (requestDTO.getImeiNo() != null) {
				Optional<DeviceLazyEntity> device = deviceLazyRepo.findByImeiNo(requestDTO.getImeiNo());
				if (device.isPresent()) {
					if(device.get().getSim1ActivationDate() == null) {
						if (requestDTO.getAddSimExpireDays() != null) {
							Date simUpdateExpireDate = DateUtil.addDaysToDate(DateUtil.getDateFromLong(requestDTO.getSimActivationDate()),
									requestDTO.getAddSimExpireDays());
							device.get().setSim1ActivationDate(DateUtil.getDateFromLong(requestDTO.getSimActivationDate()));
							device.get().setSim2ActivationDate(DateUtil.getDateFromLong(requestDTO.getSimActivationDate()));
							device.get().setSim1ExpiryDate(simUpdateExpireDate);
							device.get().setSim2ExpiryDate(simUpdateExpireDate);
						}
						if (requestDTO.getAddAppExpireDays() != null) {
							Date appUpdateExpireDate = DateUtil.addDaysToDate(DateUtil.getDateFromLong(requestDTO.getAppActivationDate()),
									requestDTO.getAddAppExpireDays());
							device.get().setAppExpiryDate(appUpdateExpireDate);
						}
						DeviceLazyEntity updatedDevice = deviceLazyRepo.save(device.get());
						response = new CustomResponse(HttpStatus.OK.value(), updatedDevice,
								HttpStatus.OK.getReasonPhrase());
					} else {
						response = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "SIM ALREADY IN ACTIVATION STATE!!!",
								HttpStatus.BAD_REQUEST.getReasonPhrase());
					}
				}
			} else {
				response = new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
						HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return response;
	}

	
}
