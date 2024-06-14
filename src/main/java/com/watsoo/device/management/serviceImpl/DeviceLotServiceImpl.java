package com.watsoo.device.management.serviceImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.DeviceLotResponseDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceLot;
import com.watsoo.device.management.model.DeviceModel;
import com.watsoo.device.management.model.EmsMaster;
import com.watsoo.device.management.model.ModelConfig;
import com.watsoo.device.management.model.Operators;
import com.watsoo.device.management.model.Provider;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.TestedDevice;
import com.watsoo.device.management.repository.DeviceLotRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.ModelConfigRepository;
import com.watsoo.device.management.repository.TestedDeviceRepository;
import com.watsoo.device.management.service.DeviceLotService;

@Service
public class DeviceLotServiceImpl implements DeviceLotService {

	@Autowired
	private ModelConfigRepository modelConfigRepository;

	@Autowired
	private DeviceLotRepository lotRepository;

	@Autowired
	private TestedDeviceRepository testedDeviceRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;

	// @Autowired
	// private EmsMasterRepository emsRepository;

	@Override
	public Response<Object> addDeviceLot(DeviceCommandDTO deviceCommandDTO) {
		if (deviceCommandDTO.getClientId() != null && deviceCommandDTO.getClientId() > 0
				&& deviceCommandDTO.getStateId() != null && deviceCommandDTO.getStateId() > 0
				&& deviceCommandDTO.getOrderQuantity() != null && deviceCommandDTO.getOrderQuantity() > 0
				&& deviceCommandDTO.getEmsMasterId() != null && deviceCommandDTO.getEmsMasterCode() != null
				&& deviceCommandDTO.getEmsMasterCode() != "") {

			List<ModelConfig> modelConfigList = modelConfigRepository
					.findByStateAndModelId(deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId());
			if (modelConfigList == null || modelConfigList.isEmpty()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "No configuration found");
			}
			DeviceLot deviceLot = new DeviceLot();
			DeviceLot lastAddedLot = lotRepository.findLastLot();
			if (lastAddedLot == null) {
				deviceLot.setSystemLotId("LT-1");
			} else {
				String numericPart = lastAddedLot.getSystemLotId().replaceAll("[^\\d]", "");
				int lastId = Integer.parseInt(numericPart);
				int nextId = lastId + 1;
				deviceLot.setSystemLotId("LT-" + nextId);
			}
			// generate mgf-lot-id
			String mgfLotId = generateMgfLotId(deviceCommandDTO.getEmsMasterCode());
			// Optional<EmsMaster> emsOptional =
			// emsRepository.findById(deviceCommandDTO.getEmsMasterId());
			// if (!emsOptional.isPresent()) {
			// return new Response<>(HttpStatus.BAD_REQUEST.value(), "EMS not found");
			// }
			// EmsMaster emsMaster = emsOptional.get();

			// deviceLot.setTac(new VtsTac(deviceCommandDTO.getTacId()));
			deviceLot.setModelId(new DeviceModel(deviceCommandDTO.getModelId()));
			deviceLot.setClient(new Client(deviceCommandDTO.getClientId()));
			deviceLot.setState(new State(deviceCommandDTO.getStateId()));
			deviceLot.setOrderQuantity(deviceCommandDTO.getOrderQuantity());
			deviceLot.setIsActive(true);
			deviceLot.setCreatedAt(new Date());
			deviceLot.setCreatedBy(deviceCommandDTO.getUserId());
			deviceLot.setOperators(new Operators(deviceCommandDTO.getOperatorId()));
			deviceLot.setProvider(new Provider(deviceCommandDTO.getProviderId()));
			deviceLot.setExternalVoltage(deviceCommandDTO.getExternalVoltage());
			deviceLot.setEmsMaster(new EmsMaster(deviceCommandDTO.getEmsMasterId()));
			deviceLot.setMfgLotId(mgfLotId);
			lotRepository.save(deviceLot);
			return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Client,State and Order Quantity can't be null");
		}
	}

	private String generateMgfLotId(String code) {
		LocalDate currentDate = LocalDate.now();
	    int day = currentDate.getDayOfMonth();
	    String month = String.format("%02d", currentDate.getMonthValue());
	    String year = String.valueOf(currentDate.getYear());
	    String dayOfWeek = currentDate.getDayOfWeek().toString().substring(0, 3);
	    Random random = new Random();
	    int randomNumber = random.nextInt(1000);
	    String formattedNumber = String.format("%03d", randomNumber);
	    String mgfLotId = code + year + month + day + dayOfWeek + formattedNumber;
	    return mgfLotId;
	}

	@Override
	public Pagination<List<DeviceLot>> getAllDeviceLot(GenericRequestBody requestDTO) {
		Pagination<List<DeviceLot>> response = new Pagination<>();
		if (requestDTO.getImeiNo() != null && requestDTO.getImeiNo() != "") {
			Optional<Device> findByImeiNo = deviceRepository.findByImeiNo(requestDTO.getImeiNo());
			if (findByImeiNo.isPresent() && findByImeiNo.get().getLotId() != null) {
				requestDTO.setLotId(findByImeiNo.get().getLotId());
			}
		}
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
				pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
						Sort.by("id").descending());
			}
			Page<DeviceLot> page = lotRepository.findAll(requestDTO, pageRequest);
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
	public Response<Object> getAllDeviceByLotId(DeviceCommandDTO request) {
		if (request.getLotId() == null && request.getLotId() < 1) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Lot Id can't be null");
		}
		Optional<DeviceLot> deviceLotOptional = lotRepository.findById(request.getLotId());
		List<TestedDevice> devices = testedDeviceRepository.findAllByLotId(request.getLotId());
		DeviceLotResponseDTO responseDTO = new DeviceLotResponseDTO();
		responseDTO.setDeviceLot(deviceLotOptional.get());
		responseDTO.setDevices(devices);
		return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDTO);
	}

	@Override
	public Pagination<List<TestedDevice>> getAllDeviceByLotIdFilter(GenericRequestBody requestDTO) {
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
	public DeviceLot getDeviceLotByLotId(GenericRequestBody request) {
		Optional<DeviceLot> deviceLotOptional = lotRepository.findById(request.getLotId());
		return deviceLotOptional.get();
	}
}
