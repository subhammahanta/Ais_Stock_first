package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.ClientSubscriptionMasterDTO;
import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.SubscriptionAmountDTO;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.ClientSubscriptionMaster;
import com.watsoo.device.management.model.Platform;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.StatePlatformMaster;
import com.watsoo.device.management.model.SubscriptionMaster;
import com.watsoo.device.management.repository.ClientSubscriptionMasterRepository;
import com.watsoo.device.management.repository.StatePlatformMasterRepository;
import com.watsoo.device.management.repository.SubscriptionMasterRepository;
import com.watsoo.device.management.service.ClientSubscriptionMasterService;

@Service
public class ClientSubscriptionMasterServiceImpl implements ClientSubscriptionMasterService {

	@Autowired
	private ClientSubscriptionMasterRepository clientSubscriptionMasterRepository;

	@Autowired
	private SubscriptionMasterRepository subscriptionMasterRepository;

	@Autowired
	private StatePlatformMasterRepository statePlatformMasterRepository;

	@Override
	public List<ClientSubscriptionMasterDTO> getAllClientSubsMaster() {
		List<ClientSubscriptionMaster> clientSubsMstrList = clientSubscriptionMasterRepository.findAll();
		List<ClientSubscriptionMasterDTO> list = new ArrayList<>();

		if (clientSubsMstrList != null && !clientSubsMstrList.isEmpty()) {
			for (ClientSubscriptionMaster clientSubsMstr : clientSubsMstrList) {
				ClientSubscriptionMasterDTO clientSubscriptionMasterDTO = clientSubsMstr
						.convertEntityToDTO(clientSubsMstr);
				list.add(clientSubscriptionMasterDTO);
			}
			return list;
		}
		return null;
	}

	@Override
	public CustomResponse getClentSubsMstrById(Integer id) {
		try {
			Optional<ClientSubscriptionMaster> statePLatFormMstr = clientSubscriptionMasterRepository.findById(id);
			if (statePLatFormMstr.isPresent()) {
				ClientSubscriptionMasterDTO statePlatformMasterDTO = statePLatFormMstr.get()
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
	public CustomResponse getAllClentSubsMstrByClientIdAndStateId(Long clientId, Long stateId) {
		List<ClientSubscriptionMasterDTO> list = new ArrayList<>();
		try {
			List<ClientSubscriptionMaster> statePlatFormMstrList = clientSubscriptionMasterRepository
					.findByClientAndStateId(clientId, stateId);
			if (statePlatFormMstrList != null && !statePlatFormMstrList.isEmpty()) {
				for (ClientSubscriptionMaster clientSubscriptionMaster : statePlatFormMstrList) {
					ClientSubscriptionMasterDTO clientSubscriptionMasterDTO = clientSubscriptionMaster
							.convertEntityToDTO(clientSubscriptionMaster);
					list.add(clientSubscriptionMasterDTO);
				}
				return new CustomResponse(HttpStatus.OK.value(), list, HttpStatus.OK.getReasonPhrase());
			}
			return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.getReasonPhrase());

		} catch (Exception e) {
			return new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<Object> getClientSubscription(GenericRequestBody request) {
		if (request.getClientId() == null) {
			return new Response<Object>(HttpStatus.BAD_REQUEST.value(), "ClientId can't be null");
		}
		StatePlatformMaster statePlatform = statePlatformMasterRepository.findByStateIdV2(request.getStateId());
		Map<String, Map<String, List<ClientSubscriptionMaster>>> resMap = new HashMap<>();
		List<ClientSubscriptionMaster> clientSubscriptionMasters = new ArrayList<>();
		if (request.getStateId() != null) {
			clientSubscriptionMasters = clientSubscriptionMasterRepository.findByClientAndStateId(request.getClientId(),
					request.getStateId());
		} else {
			clientSubscriptionMasters = clientSubscriptionMasterRepository.findByClientId(request.getClientId());
		}
		if (clientSubscriptionMasters == null || clientSubscriptionMasters.isEmpty()) {
			return new Response<Object>(HttpStatus.NOT_FOUND.value(),
					"Subscription not found...Add subscription first");
		}
		Map<String, List<ClientSubscriptionMaster>> stateSubscriptionMap = clientSubscriptionMasters.stream()
				.filter(o -> o != null).collect(Collectors.groupingBy(o -> o.getState().getName()));
		Map<String, List<ClientSubscriptionMaster>> stateSubscriptionTypeMap = new HashMap<>();
		for (Map.Entry<String, List<ClientSubscriptionMaster>> entry : stateSubscriptionMap.entrySet()) {
			List<ClientSubscriptionMaster> value = entry.getValue();
			stateSubscriptionTypeMap = value.stream()
					.collect(Collectors.groupingBy(o -> o.getSubscriptionMaster().getSubscriptionType().getName()));
			resMap.put(entry.getKey(), stateSubscriptionTypeMap);
		}
		//add on
		List<ClientSubscriptionMaster> convertStatePlatformToClientSubscription = new ArrayList<>();
		ClientSubscriptionMaster clientSubscriptionMaster = new ClientSubscriptionMaster();
		if (statePlatform != null) {
			clientSubscriptionMaster.setAmount(statePlatform.getPlateformCharges());
		}else {
			clientSubscriptionMaster.setAmount(null);
		}
		
		convertStatePlatformToClientSubscription.add(clientSubscriptionMaster);
		stateSubscriptionTypeMap.put("State Platform Charges", convertStatePlatformToClientSubscription);
		return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), resMap);
	}

	@Override
	public Response<Object> checkClientSubscription(GenericRequestBody request) {
		if (request.getClientId() == null || request.getStateId() == null) {
			return new Response<Object>(HttpStatus.BAD_REQUEST.value(), "ClientId or StateId can't be null");
		}

		Map<String, Map<String, List<ClientSubscriptionMaster>>> resMap = new HashMap<>();
		List<ClientSubscriptionMaster> clientSubscriptionMasters = new ArrayList<>();
		clientSubscriptionMasters = clientSubscriptionMasterRepository.findByClientAndStateId(request.getClientId(),
				request.getStateId());
		StatePlatformMaster statePlatform = statePlatformMasterRepository.findByStateIdV2(request.getStateId());
		// return default subscription
		if (clientSubscriptionMasters == null || clientSubscriptionMasters.isEmpty()) {
			Map<String, Map<String, List<ClientSubscriptionMasterDTO>>> defaultResponse = new HashMap<>();
			List<SubscriptionMaster> defaultSubscription = subscriptionMasterRepository.findAll();
			List<ClientSubscriptionMasterDTO> subscriptionMasterDTOs = defaultSubscription.stream()
					.map(e -> e.subscriptionMasterToClientSubscriptionMasterDTO(e)).collect(Collectors.toList());
			for (ClientSubscriptionMasterDTO update : subscriptionMasterDTOs) {
				update.setState(new State(request.getStateId()));
				update.setClient(new Client(request.getClientId()));
				update.setStatePlatformMstr(statePlatform);
			}
			Map<String, List<ClientSubscriptionMasterDTO>> defaultSubscriptionTypeMap = subscriptionMasterDTOs.stream()
					.collect(Collectors.groupingBy(o -> o.getSubscriptionType().getName()));
			//add on
			List<ClientSubscriptionMasterDTO> convertStatePlatformToClientSubscription = new ArrayList<>();
			ClientSubscriptionMasterDTO clientSubscriptionMaster = new ClientSubscriptionMasterDTO();
			if (statePlatform != null) {
				clientSubscriptionMaster.setAmount(statePlatform.getPlateformCharges());
			}else {
				clientSubscriptionMaster.setAmount(null);
			}
			
			convertStatePlatformToClientSubscription.add(clientSubscriptionMaster);
			defaultSubscriptionTypeMap.put("State Platform Charges", convertStatePlatformToClientSubscription);
			defaultResponse.put("DEFAULT", defaultSubscriptionTypeMap);
			return new Response<Object>(HttpStatus.OK.value(), "DEFAULT", defaultResponse);
		}

		Map<String, List<ClientSubscriptionMaster>> stateSubscriptionMap = clientSubscriptionMasters.stream()
				.filter(o -> o != null).collect(Collectors.groupingBy(o -> o.getState().getName()));
		Map<String, List<ClientSubscriptionMaster>> stateSubscriptionTypeMap = new HashMap<>();
		for (Map.Entry<String, List<ClientSubscriptionMaster>> entry : stateSubscriptionMap.entrySet()) {
			List<ClientSubscriptionMaster> value = entry.getValue();
			stateSubscriptionTypeMap = value.stream()
					.collect(Collectors.groupingBy(o -> o.getSubscriptionMaster().getSubscriptionType().getName()));
			resMap.put(entry.getKey(), stateSubscriptionTypeMap);
		}
		//add on
		List<ClientSubscriptionMaster> convertStatePlatformToClientSubscription = new ArrayList<>();
		ClientSubscriptionMaster clientSubscriptionMaster = new ClientSubscriptionMaster();
		if (statePlatform != null) {
			clientSubscriptionMaster.setAmount(statePlatform.getPlateformCharges());
		}else {
			clientSubscriptionMaster.setAmount(null);
		}
		
		convertStatePlatformToClientSubscription.add(clientSubscriptionMaster);
		stateSubscriptionTypeMap.put("State Platform Charges", convertStatePlatformToClientSubscription);
		return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), resMap);
	}

	@Override
	public Response<Object> addClientSubscription(GenericRequestBody request) {
		if (request.getClientId() != null && request.getStateId() != null && !request.getSubscriptionAmount().isEmpty()
				&& request.getUserId() != null && request.getUserName() != null && request.getUserName() != "") {
			List<ClientSubscriptionMaster> addUpdateAllClientSubscriptionMaster = new ArrayList<>();
			StatePlatformMaster statePlatform = statePlatformMasterRepository.findByStateIdV2(request.getStateId());
			List<ClientSubscriptionMaster> allClientSubscription = clientSubscriptionMasterRepository.findAll();
			Map<Integer, ClientSubscriptionMaster> clientSubscriptionMap = allClientSubscription.stream().collect(Collectors.toMap(ClientSubscriptionMaster::getId, Function.identity()));
			
			if (request.getSubscriptionAmount().get(0).getClientSubscriptionMasterId() == null) {
				String lastSubscriptionNumber = null;
				if (allClientSubscription != null && !allClientSubscription.isEmpty()) {
					lastSubscriptionNumber = allClientSubscription.get(allClientSubscription.size() - 1)
							.getRequestCode();
				}
				// add
				for (SubscriptionAmountDTO subscriptionAmountDTO : request.getSubscriptionAmount()) {
					ClientSubscriptionMaster clientSubscription = new ClientSubscriptionMaster();
					clientSubscription.setPlatform(new Platform(request.getPlatformId()));
					clientSubscription.setIsActive(true);
					clientSubscription.setCreatedAt(new Date());
					clientSubscription.setCreatedBy(request.getUserId());
					clientSubscription.setCreatedByName(request.getUserName());
					clientSubscription.setClient(new Client(request.getClientId()));
					clientSubscription.setSubscriptionMaster(
							new SubscriptionMaster(subscriptionAmountDTO.getSubscriptionMasterId()));
					clientSubscription.setAmount(subscriptionAmountDTO.getAmount());
					clientSubscription.setState(new State(request.getStateId()));
					if (lastSubscriptionNumber == null) {
						clientSubscription.setRequestCode("SUB-1");
						lastSubscriptionNumber = "SUB-1";
					} else {
						String numericPart = lastSubscriptionNumber.replaceAll("[^\\d]", "");
						int lastId = Integer.parseInt(numericPart);
						int nextId = lastId + 1;
						clientSubscription.setRequestCode("SUB-" + nextId);
					}
					if (statePlatform != null) {
						if (!statePlatform.getPlateformCharges().equals(request.getStatePlatformCharges())) {
							// update statePlatform
							statePlatform.setPlateformCharges(request.getStatePlatformCharges());
							statePlatform.setUpdatedAt(new Date());
							statePlatform.setUpdatedBy(request.getUserId());
							statePlatform.setUpdatedByName(request.getUserName());
							StatePlatformMaster saveObj = statePlatformMasterRepository.save(statePlatform);
							clientSubscription.setStatePlatformMstr(saveObj);
						} else {
							clientSubscription.setStatePlatformMstr(statePlatform);
						}

					} else if (statePlatform == null && request.getStatePlatformCharges() != null) {
						// add new
						StatePlatformMaster newStatePlatform = new StatePlatformMaster();
						newStatePlatform.setActive(true);
						newStatePlatform.setCreatedAt(new Date());
						newStatePlatform.setCreatedBy(request.getUserId());
						newStatePlatform.setCreatedByName(request.getUserName());
						newStatePlatform.setPlateformCharges(request.getStatePlatformCharges());
						newStatePlatform.setState(new State(request.getStateId()));
						StatePlatformMaster saveObj = statePlatformMasterRepository.save(newStatePlatform);
						clientSubscription.setStatePlatformMstr(saveObj);
					}

					addUpdateAllClientSubscriptionMaster.add(clientSubscription);
				}

			} else {
				// update
				for (SubscriptionAmountDTO subscriptionAmountDTO : request.getSubscriptionAmount()) {
//					Optional<ClientSubscriptionMaster> clientSubscriptionOpt = allClientSubscription.stream()
//							.filter(o -> o.getId().equals(subscriptionAmountDTO.getClientSubscriptionMasterId()))
//							.findFirst();
					ClientSubscriptionMaster clientSubscription = clientSubscriptionMap.get(subscriptionAmountDTO.getClientSubscriptionMasterId());
					if (clientSubscription != null) {
						//ClientSubscriptionMaster clientSubscription = clientSubscriptionOpt.get();
						clientSubscription.setUpdatedAt(new Date());
						clientSubscription.setUpdatedBy(request.getUserId());
						clientSubscription.setUpdatedByName(request.getUserName());
						clientSubscription.setAmount(subscriptionAmountDTO.getAmount());
						clientSubscription.setPlatform(new Platform(request.getPlatformId()));
						if (statePlatform != null) {
							if (!statePlatform.getPlateformCharges().equals(request.getStatePlatformCharges())) {
								// update statePlatform
								statePlatform.setPlateformCharges(request.getStatePlatformCharges());
								statePlatform.setUpdatedAt(new Date());
								statePlatform.setUpdatedBy(request.getUserId());
								statePlatform.setUpdatedByName(request.getUserName());
								StatePlatformMaster saveObj = statePlatformMasterRepository.save(statePlatform);
								clientSubscription.setStatePlatformMstr(saveObj);
							} else {
								clientSubscription.setStatePlatformMstr(statePlatform);
							}

						} else if (statePlatform == null && request.getStatePlatformCharges() != null) {
							// add new
							StatePlatformMaster newStatePlatform = new StatePlatformMaster();
							newStatePlatform.setActive(true);
							newStatePlatform.setCreatedAt(new Date());
							newStatePlatform.setCreatedBy(request.getUserId());
							newStatePlatform.setCreatedByName(request.getUserName());
							newStatePlatform.setPlateformCharges(request.getStatePlatformCharges());
							newStatePlatform.setState(new State(request.getStateId()));
							StatePlatformMaster saveObj = statePlatformMasterRepository.save(newStatePlatform);
							clientSubscription.setStatePlatformMstr(saveObj);
						}
						addUpdateAllClientSubscriptionMaster.add(clientSubscription);
					} else {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "Client Subscription not found for id-"
								+ subscriptionAmountDTO.getClientSubscriptionMasterId());
					}
				}
			}
			clientSubscriptionMasterRepository.saveAll(addUpdateAllClientSubscriptionMaster);
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
	}

	@Override
	public Pagination<List<ClientSubscriptionMasterDTO>> getAllClientSubscription(GenericRequestBody request) {
		Pagination<List<ClientSubscriptionMasterDTO>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (request.getPageSize() != null && request.getPageNo() != null && request.getPageSize() > 0) {
				pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
			}
			Page<ClientSubscriptionMaster> page = clientSubscriptionMasterRepository.findAll(request, pageRequest);
			List<ClientSubscriptionMasterDTO> resp = page.getContent().stream().map(e -> e.convertEntityToDTOV2(e)).collect(Collectors.toList());
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
