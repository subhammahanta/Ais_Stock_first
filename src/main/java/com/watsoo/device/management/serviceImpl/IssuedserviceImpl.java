package com.watsoo.device.management.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.dto.DeviceDTO;
import com.watsoo.device.management.dto.IssueDeviceDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StockUpdateRequestDTO;
import com.watsoo.device.management.dto.StockUpdateResponseDTO;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.IssuedList;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.ClientRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.IssuedRepository;
import com.watsoo.device.management.repository.StateRepository;
import com.watsoo.device.management.service.IssuedService;

@Service
public class IssuedserviceImpl implements IssuedService {

	@Autowired
	private IssuedRepository issuedRepository;

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private BoxDeviceRepository boxDeviceRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private StateRepository stateRepository;

	@Value("${store.update.stock.url}")
	private String url;

	@Override
	public Pagination<List<IssueDeviceDTO>> getIssuedList(int pageNo, int pageSize, IssueDeviceDTO dto) {

		Pagination<List<IssueDeviceDTO>> response = new Pagination<>();
		List<IssueDeviceDTO> issueDtoList = new ArrayList<>();
		Page<IssuedList> issueDetails = null;
		Pageable pageRequest = Pageable.unpaged();
		if (pageSize != 0) {
			pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("serialNumber").descending());
		}
		issueDetails = issuedRepository.findAll(dto, pageRequest);

		for (IssuedList issuedList : issueDetails.getContent()) {
			IssueDeviceDTO issueDetailsDto = issuedList.convertEntityToDto(issuedList);
			issueDtoList.add(issueDetailsDto);

		}
		response.setData(issueDtoList);
		response.setNumberOfElements(issueDetails.getNumberOfElements());
		response.setTotalElements(issueDetails.getTotalElements());
		response.setTotalPages(issueDetails.getTotalPages());

		return response;
	}

	// @Override
	// public Pagination<List<IssuedList>> getIssuedList(int pageNo, int pageSize,
	// IssuedList dto) {
	// Pagination<List<IssuedList>> response = new Pagination<>();
	// Page<IssuedList> list = null;
	// Pageable pageRequest = Pageable.unpaged();
	// if (pageSize > 0) {
	// pageRequest = PageRequest.of(pageNo, pageSize,
	// Sort.by("serialNumber").descending());
	// }
	// list = issuedRepository.getIssuedList(dto, pageRequest);
	// response.setData(list.getContent());
	// response.setNumberOfElements(list.getNumberOfElements());
	// response.setTotalElements(list.getTotalElements());
	// response.setTotalPages(list.getTotalPages());
	// return response;
	// }

	private String generateIssueSlipNumber(String companyName, Long stateId, String timestamp) {
		String stateIdString = String.format("%02d", stateId);
		String issueSlipNumber = companyName.substring(0, 3).toUpperCase() + stateIdString + timestamp;
		return issueSlipNumber;
	}

	@Override
	public Response<?> saveIssueDeviceDetails(IssueDeviceDTO dto) {
		Response<Object> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		IssuedList issuedList = new IssuedList();
		if (dto.getClientId() != null && dto.getClientId() > 0 && dto.getBoxs() != null && dto.getBoxs().size() > 0
				&& dto.getStateId() != null && dto.getStateId() > 0) {
			Long selectedBoxes = 0l;
			for (BoxDTO box : dto.getBoxs()) {
				selectedBoxes = selectedBoxes + box.getDeviceIds().size();
			}
			if (dto.getRequestDevices() != null && dto.getRequestDevices().intValue() != selectedBoxes.intValue()) {
				response.setData(null);
				response.setMessage("Select correct number of devices");
				response.setResponseCode(HttpStatus.OK.value());
			} else {
				issuedList = convertToEntity(dto);
				Optional<Client> client = clientRepository.findById(dto.getClientId());
				SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String timestamp = timestampFormat.format(new Date());

				String issueSlipNumber = generateIssueSlipNumber(issuedList.getClient().getCompanyName(),
						dto.getStateId(), timestamp);
				issuedList.setIssueSlipNumber(issueSlipNumber);

				issuedList.setCreatedAt(new Date());
				issuedRepository.save(issuedList);
				for (BoxDTO box : dto.getBoxs()) {
					if (box.getDeviceIds() != null && box.getDeviceIds().size() > 0) {
						for (Long deviceId : box.getDeviceIds()) {
							if (deviceId != null) {
								Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
								Optional<Box> boxOptional = boxRepository.findById(box.getId());
								if (deviceOptional.isPresent()) {
									deviceOptional.get().setStatus(StatusMaster.ISSUED_DEVICES);
									boxOptional.get().setIssuedList(issuedList);
									deviceOptional.get().setState(new State(dto.getStateId()));
									deviceOptional.get().setIssueDate(new Date());
									deviceOptional.get().setClientId(dto.getClientId());
									deviceOptional.get().setUpdatedAt(new Date());
									deviceOptional.get().setIsOwnDevice(dto.getIsOurClient());

									if (client.isPresent()) {
										deviceOptional.get().setClientNames(client.get().getCompanyName());
									}
									deviceRepository.save(deviceOptional.get());
								}
							}
						}
						client.get().setLastIssueDate(new Date());
						client.get().setLastIssueQuantity(selectedBoxes);
						clientRepository.save(client.get());

					}
					List<BoxDevice> boxDevices = boxDeviceRepository.getByBoxId(box.getId());
					for (BoxDevice boxDevice : boxDevices) {
						Long isRemoved = box.getDeviceIds().stream()
								.filter(o -> o.intValue() == boxDevice.getDevice().getId().intValue()).findAny()
								.orElse(null);
						if (isRemoved == null) {
							boxDevice.setIsActive(false);
							boxDevice.setIsPresent(false);
							boxDeviceRepository.save(boxDevice);
							Optional<Device> deviceOptional = deviceRepository.findById(boxDevice.getDevice().getId());
							if (deviceOptional.isPresent()) {
								deviceOptional.get().setStatus(StatusMaster.DEVICE_PACKED);
								deviceOptional.get().getId();
								deviceOptional.get().setUpdatedAt(new Date());
								deviceOptional.get().setBoxCode(null);
								deviceRepository.save(deviceOptional.get());
							}
						} else {
							boxDevice.setIsIssued(true);
						}
						// else {
						// boxDeviceRepository.delete(boxDevice);
						// }
					}
					Optional<Box> boxOptional = boxRepository.findById(box.getId());
					boxOptional.get().setCurrentQuantity((double) box.getDeviceIds().size());
					boxOptional.get().setStatus(StatusMaster.BOX_DISPATCH_FULLY);
					boxOptional.get().setState(new State(dto.getStateId()));
					boxRepository.save(boxOptional.get());
				}
				response.setData(null);
				response.setMessage("Devices added successfully");
				response.setResponseCode(HttpStatus.OK.value());
			}
		} else {
			response.setData(null);
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		}
		return response;
	}

	private IssuedList convertToEntity(IssueDeviceDTO dto) {
		Optional<Client> client = clientRepository.findById(dto.getClientId());
		// client.setId(dto.getClientId());
		Optional<State> state = stateRepository.findById(dto.getStateId());
		// state.setId(dto.getId());
		return new IssuedList(null, client.get(), state.get(), dto.getIssuedDate(), dto.getRequestDevices(),
				dto.getCreatedAt(), dto.getCreatedBy(), dto.getUpdatedAt(), dto.getUpdatedBy(), null, null);
	}

	@Override
	public Response<?> getByIssuedId(Long issuedId) {

		IssueDeviceDTO issuedListDto = new IssueDeviceDTO();
		List<Box> boxDbList = new ArrayList<>();
		Optional<IssuedList> optionalissuedDbList = issuedRepository.findById(issuedId);

		if (optionalissuedDbList.isPresent()) {

			IssuedList issuedDbList = optionalissuedDbList.get();
			boxDbList = boxRepository.findByIssueId(issuedId);
			Map<Long, Box> boxDbmap = new HashMap<>();
			Map<Long, List<BoxDevice>> boxDeviceMap = new HashMap<>();
			Set<Long> deviceSet = new HashSet<>();
			Map<Long, Device> deviceMap = new HashMap<>();

			Set<Long> setOfBoxid = new HashSet<>();
			if (boxDbList != null) {
				boxDbmap = boxDbList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
				setOfBoxid.addAll(boxDbmap.keySet());
			}

			List<BoxDevice> listOfBoxAndDevice = boxDeviceRepository.getAll(setOfBoxid).stream()
					.filter(o -> o.getIsPresent() != null && o.getIsPresent()).collect(Collectors.toList());

			if (listOfBoxAndDevice != null && !listOfBoxAndDevice.isEmpty()  && listOfBoxAndDevice.get(0).getIsActive() == true) {
				boxDeviceMap = listOfBoxAndDevice.stream().collect(Collectors.groupingBy(e -> e.getBox().getId()));
				for (BoxDevice obj : listOfBoxAndDevice) {
					deviceSet.add(obj.getDevice().getId());
				}
				List<Device> dbDeviceList = new ArrayList<>();

				if (!deviceSet.isEmpty()) {
					dbDeviceList = deviceRepository.findBySetOfIds(deviceSet);
					if (dbDeviceList != null) {
						deviceMap = dbDeviceList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
					}
				}

			}

			List<BoxDTO> finalList = new ArrayList<>();
			for (Box boxObj : boxDbList) {

				if (boxDbmap.containsKey(boxObj.getId())) {
					List<DeviceDTO> tempListDbList = new ArrayList<>();
					if (boxDeviceMap.containsKey(boxObj.getId())) {

						List<BoxDevice> deviceList = boxDeviceMap.get(boxObj.getId());
						if (!deviceList.isEmpty()) {
							for (BoxDevice boxDevice : deviceList) {

								if (deviceMap.containsKey(boxDevice.getDevice().getId())) {

									Device obj = deviceMap.get(boxDevice.getDevice().getId());

									DeviceDTO finalObj = new DeviceDTO(obj.getId(), obj.getImeiNo(), obj.getIccidNo(),
											obj.getUuidNo(), obj.getSoftwareVersion(), obj.getDetail(),
											obj.getCreatedAt(), obj.getUpdatedAt(), obj.getRequestBody(),
											obj.getOldIccid(), obj.getIccidUpdatedAt(), obj.getOldImei(),
											obj.getImeiUpdatedAt(), obj.getStatus());

									tempListDbList.add(finalObj);
								}
							}
						}
					}

					Box obj = boxDbmap.get(boxObj.getId());

					BoxDTO finalObj = new BoxDTO(obj.getId(), null, null, obj.getCreatedAt(), null, null,
							obj.getUpdatedAt(), obj.getQuantity(), obj.getCurrentQuantity(), null, null,
							obj.getIsActive(), null, null, obj.getRemarks(), null, null, obj.getLastOpenedAt(),
							obj.getBoxNo());
					finalObj.setDeviceDtoList(tempListDbList);
					finalList.add(finalObj);

				}
			}
			Client client = optionalissuedDbList.get().getClient();
			State state = optionalissuedDbList.get().getState();
			issuedListDto.setId(issuedDbList.getSerialNumber());
			issuedListDto.setClientDTO(issuedDbList.getClient().convertEntityToDto(client));
			issuedListDto.setStateDTO(issuedDbList.getState().convertEntityToDto(state));
			issuedListDto.setIssuedDate(issuedDbList.getIssuedDate());
			issuedListDto.setIssueSlipNumber(issuedDbList.getIssueSlipNumber());
			issuedListDto.setQuantity(issuedDbList.getQuantity());
			issuedListDto.setCreatedAt(issuedDbList.getCreatedAt());
			issuedListDto.setCreatedBy(issuedDbList.getCreatedBy());
			issuedListDto.setUpdatedAt(issuedDbList.getUpdatedAt());
			issuedListDto.setUpdatedAt(issuedDbList.getUpdatedAt());
			issuedListDto.setBoxs(finalList);

			if (finalList.isEmpty()) {
				return new Response<>(HttpStatus.OK.value(), "Box and device not found");
			}
		}
		return new Response<>(HttpStatus.OK.value(), "Retrieved successfully", issuedListDto);
	}

	public Boolean updateStock(StockUpdateRequestDTO request) {
		Boolean flag = false;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> inputMap = new LinkedMultiValueMap<>();
		inputMap.add("procuredQuantity", request.getProcuredQuantity());
		inputMap.add("disbursedQuantity", request.getDisbursedQuantity());
		inputMap.add("tokenNumber", "");
		inputMap.add("remarks", request.getRemarks());
		inputMap.add("updateDate", "");
		inputMap.add("createdBy", "");
		inputMap.add("createdUserName", "");
		inputMap.add("isProcured", false);
		inputMap.add("isDisbursed", false);
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(inputMap,
				headers);
		RestTemplate template = new RestTemplate();
		ResponseEntity<StockUpdateResponseDTO> response = null;
		try {
			response = template.exchange(url + "/api/update/stock?sku={sku}", HttpMethod.PUT, entity,
					StockUpdateResponseDTO.class, request.getSku());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response != null && response.getBody() != null
				&& response.getBody().getMessage().equalsIgnoreCase("Stock updated successfully!!")) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Response<?> saveIssueDeviceDetailsV2(IssueDeviceDTO dto) {
		Response<Object> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		IssuedList issuedList = new IssuedList();
		if (dto.getClientId() != null && dto.getClientId() > 0 && dto.getBoxs() != null && dto.getBoxs().size() > 0
				&& dto.getStateId() != null && dto.getStateId() > 0) {
			Long selectedBoxes = 0l;
			for (BoxDTO box : dto.getBoxs()) {
				selectedBoxes = selectedBoxes + box.getDeviceIds().size();
			}
			if (dto.getRequestDevices() != null && dto.getRequestDevices().intValue() != selectedBoxes.intValue()) {
				response.setData(null);
				response.setMessage("Select correct number of devices");
				response.setResponseCode(HttpStatus.OK.value());
			} else {
				Integer count = 0;
				issuedList = convertToEntity(dto);
				Optional<Client> client = clientRepository.findById(dto.getClientId());
				SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String timestamp = timestampFormat.format(new Date());
				String issueSlipNumber = generateIssueSlipNumber(issuedList.getClient().getCompanyName(),
						dto.getStateId(), timestamp);
				issuedList.setIssueSlipNumber(issueSlipNumber);
				issuedList.setCreatedAt(new Date());
				if (dto.getOrderId() != null && dto.getOrderId() > 0 && dto.getOrderCode() != null
						&& dto.getOrderCode() != "") {
					issuedList.setOrderId(dto.getOrderId());
					issuedList.setOrderCode(dto.getOrderCode());
				}
				IssuedList entity = issuedRepository.save(issuedList);
				for (BoxDTO box : dto.getBoxs()) {
					if (box.getDeviceIds() != null && box.getDeviceIds().size() > 0) {
						for (Long deviceId : box.getDeviceIds()) {
							if (deviceId != null) {
								Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
								Optional<Box> boxOptional = boxRepository.findById(box.getId());
								if (deviceOptional.isPresent()) {
									deviceOptional.get().setStatus(StatusMaster.ISSUED_DEVICES);
									boxOptional.get().setIssuedList(issuedList);
									deviceOptional.get().setState(new State(dto.getStateId()));
									deviceOptional.get().setIssueDate(new Date());
									deviceOptional.get().setClientId(dto.getClientId());
									deviceOptional.get().setUpdatedAt(new Date());
									if (client.isPresent()) {
										deviceOptional.get().setClientNames(client.get().getCompanyName());
									}
									// set orderId
									if (dto.getOrderId() != null && dto.getOrderId() > 0 && dto.getOrderCode() != null
											&& dto.getOrderCode() != "") {
										deviceOptional.get().setOrderId(dto.getOrderId());
										deviceOptional.get().setOrderCode(dto.getOrderCode());
									}

									deviceRepository.save(deviceOptional.get());
									// count
									++count;
								}
							}
						}
						client.get().setLastIssueDate(new Date());
						client.get().setLastIssueQuantity(selectedBoxes);
						clientRepository.save(client.get());

					}
					List<BoxDevice> boxDevices = boxDeviceRepository.getByBoxId(box.getId());
					for (BoxDevice boxDevice : boxDevices) {
						Long isRemoved = box.getDeviceIds().stream()
								.filter(o -> o.intValue() == boxDevice.getDevice().getId().intValue()).findAny()
								.orElse(null);
						if (isRemoved == null) {
							boxDevice.setIsActive(false);
							boxDevice.setIsPresent(false);
							boxDeviceRepository.save(boxDevice);
							Optional<Device> deviceOptional = deviceRepository.findById(boxDevice.getDevice().getId());
							if (deviceOptional.isPresent()) {
								deviceOptional.get().setStatus(StatusMaster.DEVICE_PACKED);
								deviceOptional.get().setUpdatedAt(new Date());
								deviceOptional.get().setBoxCode(null);
								deviceRepository.save(deviceOptional.get());
								// count
								++count;
							}
						} else {
							boxDevice.setIsIssued(true);
						}
						// else {
						// boxDeviceRepository.delete(boxDevice);
						// }
					}
					Optional<Box> boxOptional = boxRepository.findById(box.getId());
					boxOptional.get().setCurrentQuantity((double) box.getDeviceIds().size());
					boxOptional.get().setStatus(StatusMaster.BOX_DISPATCH_FULLY);
					boxOptional.get().setState(new State(dto.getStateId()));
					boxRepository.save(boxOptional.get());
				}

				// update stock start
				StockUpdateRequestDTO requestDTO = new StockUpdateRequestDTO();
				requestDTO.setRemarks(entity.getIssueSlipNumber());
				requestDTO.setDisbursedQuantity(count);
				requestDTO.setSku("MS-140");
				Boolean isStockUpdated = updateStock(requestDTO);
				if (isStockUpdated) {
					System.out.println("Stock updated successfully in stock management");
				} else {
					System.out.println("Stock unable to updated  in stock management");
				}
				// update stock end

				response.setData(null);
				response.setMessage("Devices added successfully");
				response.setResponseCode(HttpStatus.OK.value());
			}
		} else {
			response.setData(null);
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		}
		return response;
	}

	@Override
	public Response<?> unissueListOfDevice(IssueDeviceDTO dto) {
		List<Device> devices = deviceRepository.findAllByImeiNo(dto.getImeiList());
		if (devices.isEmpty()) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Device not found");
		}
		List<String> imeiList = devices.stream().map(o->o.getImeiNo()).collect(Collectors.toList());
		//not found devices
		List<String> missingDevices = dto.getImeiList().stream()
                .filter(o -> !imeiList.contains(o))
                .collect(Collectors.toList());
//		
//		if (!device.getStatus().equals(StatusMaster.ISSUED_DEVICES)) {
//			return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),"Device Not Issued Yet");
//		}
//		List<ReturnDevice> returnDevices = returnDeviceMaster.getReturnDevices();
//		Optional<ReturnDevice> flag = returnDevices.stream().filter(o->o!= null && o.getImei().equalsIgnoreCase(device.getImeiNo())).findFirst();
//		if (flag.isPresent()) {
//			return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Device already added");
//		}
//		ReturnDevice returnDevice = new ReturnDevice();
//		returnDevice.setBoxNo(device.getBoxCode());
//		returnDevice.setCreatedAt(new Date());
//		returnDevice.setCreatedBy(dto.getUserId());
//		returnDevice.setIccid(device.getIccidNo());
//		returnDevice.setImei(device.getImeiNo());
//		returnDevice.setIsActive(true);
//		if (returnDeviceMaster.getClient().getId().equals(device.getClientId())) {
//			returnDevice.setIsDifferentClientDevice(false);
//		} else {
//			returnDevice.setIsDifferentClientDevice(true);
//		}
//		returnDevice.setIssuedAt(device.getIssueDate());
//		returnDevice.setIssuedTo(device.getClientName());
//		returnDevice.setMfgLotNo(device.getMfgLotId());
//		returnDevice.setReturnDeviceMaster(returnDeviceMaster);
//		ReturnDevice response = returnDeviceRepository.save(returnDevice);
//		returnDeviceMaster.setCurrentQuantity(returnDeviceMaster.getCurrentQuantity()+1);
//		returnDeviceMasterRepository.save(returnDeviceMaster);
//		// create transaction
//		device.setBoxCode(null);
//		device.setClientId(null);
//		device.setClientName(null);
//		device.setIsReturnType(true);
//		device.setIssueDate(null);
//		device.setStatus(StatusMaster.DEVICE_PACKED);
//		deviceLiteRepository.save(device);
//		BoxDevice boxDevice = boxDeviceRepository.findByDeviceIdV2(device.getId());
//		if (boxDevice != null) {
//			boxDevice.setIsActive(false);
//			boxDevice.setIsIssued(false);
//			boxDevice.setIsPresent(false);
//			boxDeviceRepository.save(boxDevice);
//			Box box = boxDevice.getBox();
//			box.setUpdatedAt(new Date());
//			box.setUpdatedBy(new User(dto.getUserId()));
//			box.setCurrentQuantity(box.getCurrentQuantity() != 0 ? box.getCurrentQuantity() - 1 : 0);
//			box.setQuantity(box.getQuantity() != 0 ? box.getQuantity() - 1 : 0);
//			if (box.getCurrentQuantity() == 0) {
//				box.setIsActive(false);
//			}
//			boxRepository.save(box);
//			Optional<IssuedList> issOptional = issuedRepository.findById(box.getIssuedList().getSerialNumber());
//			if (issOptional.isPresent()) {
//				IssuedList issued = issOptional.get();
//				issued.setUpdatedAt(new Date());
//				issued.setUpdatedBy(dto.getUserId());
//				issued.setQuantity(issued.getQuantity() - 1);
//				issuedRepository.save(issued);
//				returnDevice.setInvoiceNo(issued.getIssueSlipNumber());
//				response = returnDeviceRepository.save(returnDevice);
//				return new Response<>(HttpStatus.OK.value(), "Device Successfully added",response);
//			}else {
//				return new Response<>(HttpStatus.OK.value(), "Device add but no Issue List found",response);
//			}
//			
//		}else {
//			return new Response<>(HttpStatus.OK.value(), "Device add but no box found",response);
//		}
		return null;
	}
}
