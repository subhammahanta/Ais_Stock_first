package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;
import com.watsoo.device.management.model.SubscriptionMaster;
import com.watsoo.device.management.repository.SubscriptionMasterRepository;
import com.watsoo.device.management.service.SubscriptionMasterService;

@Service
public class SubscriptionMasterServiceImpl implements SubscriptionMasterService {

	@Autowired
	private SubscriptionMasterRepository subscriptionMasterRepository;

	@Override
	public List<SubscriptionMasterDTO> getAllSubscriptionMaster() {
		List<SubscriptionMaster> subscriptionTypeList = subscriptionMasterRepository.findAll();
		List<SubscriptionMasterDTO> list = new ArrayList<>();
		if ( subscriptionTypeList !=null && !subscriptionTypeList.isEmpty()) {
			for (SubscriptionMaster subscriptionMaster : subscriptionTypeList) {
				SubscriptionMasterDTO subscriptionTypeDTO = subscriptionMaster.convertEntityToDTO(subscriptionMaster);
				list.add(subscriptionTypeDTO);
			}
		}
		return list;
	}

	public CustomResponse getAllSubscriptionMasterById(Integer id) {

		try {
			Optional<SubscriptionMaster> subsMaster = subscriptionMasterRepository.findById(id);
			if (subsMaster.isPresent()) {
				SubscriptionMasterDTO subscriptionMasterDTO = subsMaster.get().convertEntityToDTO(subsMaster.get());
				return new CustomResponse(HttpStatus.OK.value(), subscriptionMasterDTO,
						HttpStatus.OK.getReasonPhrase());
			}
			return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.getReasonPhrase());
		} catch (Exception e) {
			return new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Pagination<List<SubscriptionMasterDTO>> getAllSubscription(GenericRequestBody request) {
		Pagination<List<SubscriptionMasterDTO>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (request.getPageSize() != null && request.getPageNo() != null && request.getPageSize() > 0) {
				pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
			}
			Page<SubscriptionMaster> page = subscriptionMasterRepository.findAll(request, pageRequest);
			List<SubscriptionMasterDTO> resp = page.getContent().stream().map(e -> e.convertEntityToDTO(e)).collect(Collectors.toList());
			response.setData(resp);
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
