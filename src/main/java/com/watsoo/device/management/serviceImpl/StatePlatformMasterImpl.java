package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.StatePlatformMasterDTO;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;
import com.watsoo.device.management.model.StatePlatformMaster;
import com.watsoo.device.management.model.SubscriptionMaster;
import com.watsoo.device.management.repository.StatePlatformMasterRepository;
import com.watsoo.device.management.service.StatePlatformMasterService;

@Service
public class StatePlatformMasterImpl implements StatePlatformMasterService {
	@Autowired
	private StatePlatformMasterRepository statePlatformMasterRepository;

	@Override
	public List<StatePlatformMasterDTO> getAllPlatFormMaster() {
		List<StatePlatformMaster> statePlatformMstrList = statePlatformMasterRepository.findAll();
		List<StatePlatformMasterDTO> list = new ArrayList<>();

		if (statePlatformMstrList != null && !statePlatformMstrList.isEmpty()) {
			for (StatePlatformMaster statePlatformMaster : statePlatformMstrList) {
				StatePlatformMasterDTO statePlatformMstrDTO = statePlatformMaster
						.convertEntityToDTO(statePlatformMaster);
				list.add(statePlatformMstrDTO);
			}
			return list;
		}
		return null;
	}

	@Override
	public CustomResponse getAllStatePlateMstrById(Integer id) {
		try {
			Optional<StatePlatformMaster> statePLatFormMstr = statePlatformMasterRepository.findById(id);
			if (statePLatFormMstr.isPresent()) {
				StatePlatformMasterDTO statePlatformMasterDTO = statePLatFormMstr.get()
						.convertEntityToDTO(statePLatFormMstr.get());
				return new CustomResponse(HttpStatus.OK.value(), statePlatformMasterDTO,
						HttpStatus.OK.getReasonPhrase());
			}
			return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.getReasonPhrase());
		} catch (Exception e) {
			return new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public CustomResponse getAllStatePlatformByStateId(Long stateId) {
		List<StatePlatformMasterDTO> list = new ArrayList<>();
		try {
			List<StatePlatformMaster> statePlatFormMstrList = statePlatformMasterRepository.findByStateId(stateId);
			if (statePlatFormMstrList != null && !statePlatFormMstrList.isEmpty()) {
				for (StatePlatformMaster plateformMaster : statePlatFormMstrList) {
					StatePlatformMasterDTO statePlatformMasterDTO = plateformMaster.convertEntityToDTO(plateformMaster);
					list.add(statePlatformMasterDTO);
				}
				return new CustomResponse(HttpStatus.OK.value(), list, HttpStatus.OK.getReasonPhrase());
			}
			return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.getReasonPhrase());

		} catch (Exception e) {
			return new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

}
