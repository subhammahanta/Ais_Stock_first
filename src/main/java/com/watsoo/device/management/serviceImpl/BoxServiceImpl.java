package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
import com.watsoo.device.management.dto.BoxDeviceDTO;
import com.watsoo.device.management.dto.DeviceDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StockUpdateRequestDTO;
import com.watsoo.device.management.dto.StockUpdateResponseDTO;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.BoxLite;
import com.watsoo.device.management.model.BoxTransaction;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.Provider;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.model.UserLite;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxLiteRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.BoxTransactionRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.StateRepository;
import com.watsoo.device.management.repository.UserLiteRepository;
import com.watsoo.device.management.service.BoxService;
import com.watsoo.device.management.util.TokenUtility;

@Service
public class BoxServiceImpl implements BoxService {
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private BoxDeviceRepository boxDeviceRepository;
	@Autowired
	private BoxTransactionRepository boxTransactionRepository;
	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private BoxLiteRepository boxLiteRepository;
	@Autowired
	private UserLiteRepository userLiteRepo;
	@Autowired
	private StateRepository stateRepo;

	@Value("${store.update.stock.url}")
	private String url;

	@Override
	public BoxDTO saveBoxDetails(BoxDTO boxDto) {
		// TODO Auto-generated method stub
		Box box = boxDto.convertDtoToEntity(boxDto);
		box.setCurrentQuantity(box.getQuantity());
		box.setStatus(StatusMaster.BOX_IN_STOCK);
		Box boxDetails = boxRepository.save(box);
		BoxTransaction boxTransaction = new BoxTransaction();
		boxTransaction.setBox(boxDetails);
		boxTransaction.setCreatedAt(new Date());
		boxTransaction.setCreatedBy(boxDetails.getCreatedBy());
		boxTransaction.setIsActive(true);
		boxTransaction.setQuantity(boxDetails.getQuantity());
		BoxTransaction boxTransactionDetails = boxTransactionRepository.save(boxTransaction);
		List<BoxDevice> boxDeviceList = new ArrayList<>();
		List<Device> deviceList = deviceRepo.findAllById(boxDto.getDeviceIds());
		for (Long deviceId : boxDto.getDeviceIds()) {
			BoxDevice boxDevice = new BoxDevice();
			boxDevice.setBox(boxDetails);
			Device device = new Device();
			device.setId(deviceId);
			boxDevice.setDevice(device);
			boxDevice.setEntryTransactionId(boxTransactionDetails);
			boxDevice.setIsActive(true);
			boxDevice.setIsPresent(true);
			boxDeviceList.add(boxDevice);
		}

		for (Device device : deviceList) {
			device.setStatus(StatusMaster.BOX_PACKED);
			device.setUpdatedAt(new Date());
			device.setBoxCode(boxDetails.getBoxNo());
		}

		deviceRepo.saveAll(deviceList);
		boxDeviceRepository.saveAll(boxDeviceList);
		BoxDTO boxDetailsDto = boxDetails.convertEntityToDto(boxDetails);
		return boxDetailsDto;
	}

	@Override
	public Pagination<List<BoxDTO>> getAllBox(int pageNo, int pageSize, BoxDTO boxDto) {
		// TODO Auto-generated method stub
		Pagination<List<BoxDTO>> response = new Pagination<>();
		List<BoxDTO> boxDtoList = new ArrayList<>();
		Page<Box> boxDetails = null;
		Pageable pageRequest = Pageable.unpaged();
		if (pageSize != 0) {
			pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
		}
		// Date fromDate = null;
		// Date toDate = null;
		// if (boxDto.getStartDate() != null && boxDto.getEndDate() != null) {
		// fromDate = DateUtil.getFromDateWithTime(boxDto.getStartDate());
		// toDate = DateUtil.getFromDateWithTime(boxDto.getEndDate());
		// boxDto.setFromDate(fromDate);
		// boxDto.setToDate(toDate);
		// }
		boxDetails = boxRepository.findAll(boxDto, pageRequest);
		for (Box box : boxDetails.getContent()) {
			BoxDTO boxDetailsDto = box.convertEntityToDto(box);
			boxDtoList.add(boxDetailsDto);
		}

		if (boxDto.getId() != null && boxDtoList != null && !boxDtoList.isEmpty()) {
			List<BoxDevice> boxDeviceList = boxDeviceRepository.FindByBox_idAndIsActiveTrue(boxDtoList.get(0).getId());
			if (boxDeviceList != null && !boxDeviceList.isEmpty()) {
				List<DeviceDTO> deviceDtoList = boxDeviceList.stream().filter(x -> x != null).map(y -> y.getDevice())
						.map(x -> x.convertEntityToDTO()).collect(Collectors.toList());
				boxDtoList.get(0).setDeviceDtoList(deviceDtoList);
			}
		}
		response.setData(boxDtoList);
		response.setNumberOfElements(boxDetails.getNumberOfElements());
		response.setTotalElements(boxDetails.getTotalElements());
		response.setTotalPages(boxDetails.getTotalPages());

		return response;
	}

	@Override
	public Pagination<List<BoxDTO>> getAllBoxLite(int pageNo, int pageSize, BoxDTO boxDto) {
		// TODO Auto-generated method stub
		Pagination<List<BoxDTO>> response = new Pagination<>();
		List<BoxDTO> boxDtoList = new ArrayList<>();
		Page<BoxLite> boxDetails = null;

		Pageable pageRequest = Pageable.unpaged();
		if (pageSize != 0) {
			pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
		}
		// Date fromDate = null;
		// Date toDate = null;
		// if (boxDto.getStartDate() != null && boxDto.getEndDate() != null) {
		// fromDate = DateUtil.getFromDateWithTime(boxDto.getStartDate());
		// toDate = DateUtil.getFromDateWithTime(boxDto.getEndDate());
		// boxDto.setFromDate(fromDate);
		// boxDto.setToDate(toDate);
		// }

		boxDetails = boxLiteRepository.findAll(boxDto, pageRequest);
		List<Long> requiredUserIds = new ArrayList<>();
		requiredUserIds.addAll(boxDetails.getContent().stream().filter(o -> o.getCreatedBy() != null)
				.map(o -> o.getCreatedBy()).collect(Collectors.toList()));
		requiredUserIds.addAll(boxDetails.getContent().stream().filter(o -> o.getUpdatedBy() != null)
				.map(o -> o.getUpdatedBy()).collect(Collectors.toList()));
		requiredUserIds.addAll(boxDetails.getContent().stream().filter(o -> o.getLastOpenedBy() != null)
				.map(o -> o.getLastOpenedBy()).collect(Collectors.toList()));
		Map<Long, UserLite> userMap = userLiteRepo.findAllById(requiredUserIds).stream().filter(o -> o.getId() != null)
				.collect(Collectors.toMap(UserLite::getId, Function.identity()));
		Map<Long, State> stateMap = stateRepo.findAll().stream().filter(o -> o.getId() != null)
				.collect(Collectors.toMap(State::getId, Function.identity()));
		for (BoxLite box : boxDetails.getContent()) {
			BoxDTO boxDetailsDto = box.convertEntityToDtoV2(box);
			if (box.getProvider() != null) {
				boxDetailsDto.setProvider(box.getProvider());
			}

			boxDetailsDto.setCreatedBy(box.getCreatedBy() != null && userMap.get(box.getCreatedBy()) != null
					? userMap.get(box.getCreatedBy()).convertToDTO()
					: null);
			boxDetailsDto.setUpdatedBy(box.getUpdatedBy() != null && userMap.get(box.getUpdatedBy()) != null
					? userMap.get(box.getUpdatedBy()).convertToDTO()
					: null);
			boxDetailsDto.setLastOpenedBy(box.getLastOpenedBy() != null && userMap.get(box.getLastOpenedBy()) != null
					? userMap.get(box.getLastOpenedBy()).convertToDTO()
					: null);
			boxDetailsDto.setState(box.getState() != null && stateMap.get(box.getState()) != null
					? stateMap.get(box.getState()).convertEntityToDto(stateMap.get(box.getState()))
					: null);
			boxDtoList.add(boxDetailsDto);
		}

		if (boxDto.getId() != null && boxDtoList != null && !boxDtoList.isEmpty()) {
			List<BoxDevice> boxDeviceList = boxDeviceRepository.FindByBox_idAndIsActiveTrue(boxDtoList.get(0).getId());
			if (boxDeviceList != null && !boxDeviceList.isEmpty()) {
				List<DeviceDTO> deviceDtoList = boxDeviceList.stream().filter(x -> x != null).map(y -> y.getDevice())
						.map(x -> x.convertEntityToDTO()).collect(Collectors.toList());
				boxDtoList.get(0).setDeviceDtoList(deviceDtoList);
			}
		}
		List<BoxDTO> boxes = boxDtoList.stream().filter(o -> o.getIsActive() != null && o.getIsActive())
				.collect(Collectors.toList());
		response.setData(boxes);
		response.setNumberOfElements(boxDetails.getNumberOfElements());
		response.setTotalElements(boxDetails.getTotalElements());
		response.setTotalPages(boxDetails.getTotalPages());

		return response;
	}

	@Override
	public Response<BoxDeviceDTO> getById(Long id) {
		Response<BoxDeviceDTO> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		List<BoxDevice> boxList = boxDeviceRepository.FindByBox_idAndIsActiveTrue(id);
		BoxDeviceDTO boxDeviceDTO = new BoxDeviceDTO();
		if (boxList != null && boxList.size() > 0) {

			boxDeviceDTO.setBoxDTO(boxList.get(0).getBox().convertEntityToDtoV2(boxList.get(0).getBox()));
			List<Device> mapDeviceList = boxList.stream().filter(o -> o.getDevice() != null).map(o -> o.getDevice())
					.collect(Collectors.toList());

			if (boxList.get(0).getBox() != null && boxList.get(0).getBox().getProvider() != null) {
				BoxDTO boxDTO = boxDeviceDTO.getBoxDTO();
				boxDTO.setProvider(boxList.get(0).getBox().getProvider());
				boxDeviceDTO.setBoxDTO(boxDTO);
			}

			boxDeviceDTO.setDeviceList(mapDeviceList);
			response.setData(boxDeviceDTO);
			response.setMessage(HttpStatus.OK.getReasonPhrase());
			response.setResponseCode(HttpStatus.OK.value());
			return response;
		} else {
			Optional<Box> findById = boxRepository.findById(id);
			BoxDTO boxDTO = findById.get().convertEntityToDtoV2(findById.get());
			boxDTO.setProvider(findById.get().getProvider());
			boxDeviceDTO.setBoxDTO(boxDTO);
			response.setData(boxDeviceDTO);
			response.setMessage(HttpStatus.OK.getReasonPhrase());
			response.setResponseCode(HttpStatus.OK.value());
			return response;
		}
//		} else {
//			response.setData(null);
//			response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
//			response.setResponseCode(HttpStatus.NOT_FOUND.value());
//			return response;
//		}
	}

	// @Override
	// public List<ProductTypeDTO> getAllProduct() {
	// List<ProductType> productTypeList = productTypeRepository.findAll();
	// List<ProductTypeDTO> productTypeDTOList = new ArrayList<>();
	// if (productTypeList != null && !productTypeList.isEmpty()) {
	// productTypeDTOList = productTypeList.stream().filter(x -> x != null).map(x ->
	// x.convertEntityToDto())
	// .collect(Collectors.toList());
	// }
	// return productTypeDTOList;
	// }
	//
	// @Override
	// public List<ProductStatusMappingDTO>
	// getAllProductStatus(ProductStatusMappingDTO productStatusMappingDTO) {
	// List<ProductStatusMapping> productStatusMappingList =
	// productStatusMappingRepository
	// .findByProductType_idAndIsActiveTrue(productStatusMappingDTO.getProductTypeId());
	// List<ProductStatusMappingDTO> productStatusMappingDTOList = new
	// ArrayList<>();
	// if (productStatusMappingList != null && !productStatusMappingList.isEmpty())
	// {
	// productStatusMappingDTOList = productStatusMappingList.stream().filter(x -> x
	// != null)
	// .map(y -> y.convertEntityToDto()).collect(Collectors.toList());
	// }
	// return productStatusMappingDTOList;
	//
	// }
	@Override
	public BoxDTO saveBoxDetailsV2(BoxDTO boxDto) {
		Box box = boxDto.convertDtoToEntity(boxDto);
		box.setCurrentQuantity(box.getQuantity());
		box.setStatus(StatusMaster.BOX_IN_STOCK);
		Box boxDetails = boxRepository.save(box);
		BoxTransaction boxTransaction = new BoxTransaction();
		boxTransaction.setBox(boxDetails);
		boxTransaction.setCreatedAt(new Date());
		boxTransaction.setCreatedBy(boxDetails.getCreatedBy());
		boxTransaction.setIsActive(true);
		boxTransaction.setQuantity(boxDetails.getQuantity());
		BoxTransaction boxTransactionDetails = boxTransactionRepository.save(boxTransaction);
		List<BoxDevice> boxDeviceList = new ArrayList<>();
		List<Device> deviceList = deviceRepo.findAllById(boxDto.getDeviceIds());
		for (Long deviceId : boxDto.getDeviceIds()) {
			BoxDevice boxDevice = new BoxDevice();
			boxDevice.setBox(boxDetails);
			Device device = new Device();
			device.setId(deviceId);
			boxDevice.setDevice(device);
			boxDevice.setEntryTransactionId(boxTransactionDetails);
			boxDevice.setIsActive(true);
			boxDevice.setIsPresent(true);
			boxDeviceList.add(boxDevice);
		}

		for (Device device : deviceList) {
			device.setStatus(StatusMaster.BOX_PACKED);
			device.setUpdatedAt(new Date());
			device.setBoxCode(boxDetails.getBoxNo());
		}
		// update stock start
		StockUpdateRequestDTO requestDTO = new StockUpdateRequestDTO();
		requestDTO.setRemarks(boxDetails.getBoxNo());
		requestDTO.setProcuredQuantity(deviceList.size());
		requestDTO.setSku("MS-140");
		Boolean isStockUpdated = updateStock(requestDTO);
		if (isStockUpdated) {
			System.out.println("Stock updated successfully in stock management");
		} else {
			System.out.println("Stock unable to updated  in stock management");
		}
		// update stock end
		deviceRepo.saveAll(deviceList);
		boxDeviceRepository.saveAll(boxDeviceList);
		BoxDTO boxDetailsDto = boxDetails.convertEntityToDto(boxDetails);
		return boxDetailsDto;
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
	public Response<Box> createBox(BoxDTO boxDto) {
		try {
			if (boxDto.getQuantity() == null || boxDto.getQuantity() < 0 || boxDto.getProviderId() == null
					|| boxDto.getStateId() == null || boxDto.getCreatedById() == null) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			Box box = new Box();
			box.setQuantity(boxDto.getQuantity());
			box.setCreatedAt(new Date());
			box.setCreatedBy(new User(boxDto.getCreatedById()));
			box.setBoxNo(TokenUtility.getBoxNextNumber(boxDto.getStateId()));
			box.setIsActive(true);
			box.setRemarks(boxDto.getRemarks());
			box.setState(new State(boxDto.getStateId()));
			box.setCurrentQuantity(0d);
			box.setStatus(StatusMaster.BOX_IN_STOCK);
			box.setProvider(new Provider(boxDto.getProviderId()));
			Box boxDetails = boxRepository.save(box);
			BoxTransaction boxTransaction = new BoxTransaction();
			boxTransaction.setBox(boxDetails);
			boxTransaction.setCreatedAt(new Date());
			boxTransaction.setCreatedBy(boxDetails.getCreatedBy());
			boxTransaction.setIsActive(true);
			boxTransaction.setQuantity(boxDetails.getQuantity());
			boxTransactionRepository.save(boxTransaction);
			return new Response<Box>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), boxDetails);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<Device> addDeviceInBox(BoxDTO boxDto) {
		try {
			if (boxDto.getId() == null || boxDto.getSearch() == null || boxDto.getSearch() == ""
					|| boxDto.getCreatedById() == null) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			Optional<Box> updateBoxOpt = boxRepository.findById(boxDto.getId());
			if (!updateBoxOpt.isPresent()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "BOX NOT FOUND");
			}
			Box updateBox = updateBoxOpt.get();
			if (updateBox.getCurrentQuantity() < updateBox.getQuantity()) {

			} else {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						"Current Quantity Exceeds the Requested Quantity");
			}
			BoxTransaction boxTransactionDetails = boxTransactionRepository.findByBoxId(updateBox.getId());
			List<Device> deviceList = deviceRepo.findByLikeImeiNoOrIccidNoOrUuidNo(boxDto.getSearch().trim());
			if (deviceList == null || deviceList.isEmpty()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "DEVICE NOT FOUND");
			}
			Device device = deviceList.get(0);
			if (!device.getStatus().equals(StatusMaster.DEVICE_PACKED)) {
				if (device.getStatus().equals(StatusMaster.BOX_PACKED)) {
					return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
							"Action failed due to mismatched device status :" + device.getStatus().name()
									+ " And Box Number is :" + device.getBoxCode());
				}
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						"Action failed due to mismatched device status :" + device.getStatus().name());
			}
			if (!device.getState().equals(updateBox.getState())) {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						"Action failed due to mismatched device state :" + device.getState().getName());
			}
			if (device.getSim1Provider() == null || device.getSim1Provider() == "") {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "Device unable to find SIM provider");
			}
			if (updateBox.getProvider() == null) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "Box unable to find SIM provider");
			}
			if (!device.getSim1Provider().equalsIgnoreCase(updateBox.getProvider().getName())) {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						"Action failed due to mismatched device provider  :" + device.getSim1Provider());
			}
			updateBox.setCurrentQuantity(updateBox.getCurrentQuantity() + 1);
			updateBox.setUpdatedAt(new Date());
			updateBox.setUpdatedBy(new User(boxDto.getCreatedById()));
			boxRepository.save(updateBox);
			BoxDevice boxDevice = new BoxDevice();
			boxDevice.setBox(updateBox);
			boxDevice.setDevice(device);
			boxDevice.setEntryTransactionId(boxTransactionDetails);
			boxDevice.setIsActive(true);
			boxDevice.setIsPresent(true);
			boxDeviceRepository.save(boxDevice);
			device.setStatus(StatusMaster.BOX_PACKED);
			device.setUpdatedAt(new Date());
			device.setBoxCode(updateBox.getBoxNo());
			device.setModifiedBy(boxDto.getCreatedById());
			deviceRepo.save(device);
			return new Response<Device>(HttpStatus.OK.value(), "DEVICE ADDED SUCCESSFULLY", device);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

}