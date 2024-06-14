package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watsoo.device.management.dto.BoxDeviceDTO;
import com.watsoo.device.management.dto.BoxDeviceResponseDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.ReConfigureBoxDTO;
import com.watsoo.device.management.dto.ReConfigureDevicesDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.BoxTransaction;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.GetCommandKey;
import com.watsoo.device.management.model.Provider;
import com.watsoo.device.management.model.ReConfigureBox;
import com.watsoo.device.management.model.ReConfigureCommand;
import com.watsoo.device.management.model.ReConfigureDeviceTrail;
import com.watsoo.device.management.model.ReConfigureDevices;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.BoxTransactionRepository;
import com.watsoo.device.management.repository.DeviceLiteRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.GetCommandKeyRepository;
import com.watsoo.device.management.repository.ReConfigureBoxRepository;
import com.watsoo.device.management.repository.ReConfigureCommandRepository;
import com.watsoo.device.management.repository.ReConfigureDeviceTrailRepository;
import com.watsoo.device.management.repository.ReConfigureDevicesRepository;
import com.watsoo.device.management.service.ReConfigureBoxService;
import com.watsoo.device.management.util.TokenUtility;

@Service
public class ReConfigureBoxServiceImpl implements ReConfigureBoxService {

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private BoxDeviceRepository boxDeviceRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private ReConfigureDeviceTrailRepository reConfigureDeviceTrailRepository;

	@Autowired
	private ReConfigureBoxRepository reConfigureBoxRepository;

	@Autowired
	private ReConfigureDevicesRepository reConfigureDevicesRepository;

	// @Autowired
	// private ConfigCommandRepository commandRepository;

	@Autowired
	private BoxTransactionRepository boxTransactionRepository;

	@Autowired
	private GetCommandKeyRepository getCommandKeyRepository;

	@Autowired
	private ReConfigureCommandRepository reConfigureCommandRepository;

	@Autowired
	private DeviceLiteRepository deviceLiteRepository;

	//	@Autowired
	//	private BoxDeviceV2Repository boxDeviceV2Repository;

	@Override
	public Response<?> unboxDevice(GenericRequestBody request) {
		Optional<Box> boxOptional = boxRepository.findById(request.getBoxId());
		Optional<ReConfigureBox> rcbOptional = reConfigureBoxRepository.findById(request.getReConfigureBoxId());
		try {
			Box box = boxOptional.get();
			ReConfigureBox reConfigureBox = rcbOptional.get();
			if (box.getStatus() == StatusMaster.BOX_IN_STOCK) {
				List<BoxDevice> boxDeviceList = boxDeviceRepository.findAllByIds(request.getBoxId());
				List<Long> deviceIds = boxDeviceList.stream().filter(o -> o.getDevice().getId() != null)
						.map(o -> o.getDevice().getId()).collect(Collectors.toList());
				List<Device> devices = deviceRepository.findAllByDeviceIds(deviceIds);
				List<Device> deviceList = new ArrayList<>();
				for (Device device : devices) {
					device.setStatus(StatusMaster.DEVICE_PACKED);
					device.setUpdatedAt(new Date());
					device.setBoxCode(null);
					device.setModifiedBy(request.getUserId());
					deviceList.add(device);
				}
				deviceRepository.saveAll(deviceList);
				List<BoxDevice> boxDevices = new ArrayList<>();
				int count = 0;
				for (BoxDevice boxDevice : boxDeviceList) {
					if (boxDevice.getIsPresent() != null && boxDevice.getIsPresent()) {
						count++;
					}
					boxDevice.setIsActive(false);
					boxDevice.setIsPresent(false);
					boxDevices.add(boxDevice);
				}
				boxDeviceRepository.saveAll(boxDevices);
				box.setIsActive(false);
				box.setCurrentQuantity(0d);
				box.setQuantity(0d);
				box.setStatus(null);
				box.setUpdatedAt(new Date());
				box.setUpdatedBy(new User(request.getUserId()));
				boxRepository.save(box);
				if (reConfigureBox.getTotalConfigureDevice() != null && reConfigureBox.getTotalConfigureDevice() == 0) {
					Box boxToInactive = boxRepository.findByBoxNumber(reConfigureBox.getReConfigBoxCode());
					if (boxToInactive.getCurrentQuantity() != null && boxToInactive.getCurrentQuantity() == 0d) {
						boxToInactive.setIsActive(false);
						boxRepository.save(boxToInactive);
					}
				}
				reConfigureBox.setUpdatedAt(new Date());
				reConfigureBox.setUpdatedBy(new User(request.getUserId()));
				reConfigureBox.setIsCompleted(true);
				reConfigureBox.setTotalUnboxDevice(count);
				reConfigureBoxRepository.save(reConfigureBox);
				return new Response<>(HttpStatus.OK.value(), "Unbox Completed");
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Box status is not in BOX_IN_STOCK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<?> saveCommandResponse(GenericRequestBody request) {
		try {
			if (request.getReConfigureBoxId() == null || request.getCommand() == null || request.getCommand() == ""
					|| request.getImeiNo() == null || request.getImeiNo() == "") {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			ReConfigureDeviceTrail trail = new ReConfigureDeviceTrail();
			trail.setCommand(request.getCommand());
			trail.setCreatedAt(new Date());
			trail.setCreatedBy(new User(request.getUserId()));
			trail.setImeiNo(request.getImeiNo());
			trail.setReConfigBoxId(request.getReConfigureBoxId());
			trail.setResponse(request.getResponse());
			ReConfigureDeviceTrail reConfigureDeviceTrail = reConfigureDeviceTrailRepository.save(trail);
			return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(),
					reConfigureDeviceTrail);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<?> checkReConfigureCommandExist(GenericRequestBody request) {
		try {
			if (request.getBoxId() == null) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			ReConfigureBox reConfigureBox = reConfigureBoxRepository.findExistReConfigureBoxByBoxId(request.getBoxId());
			if (reConfigureBox == null) {
				return new Response<>(HttpStatus.NOT_FOUND.value(),
						"Configure command not found !! Add command first..");
			}
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), reConfigureBox);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<?> saveReConfigureCommand(GenericRequestBody request) {
		try {
			if (request.getCommand() == null || request.getCommand() == "" || request.getBoxCode() == null
					|| request.getBoxCode() == "") {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			Box box = boxRepository.findByBoxCode(request.getBoxCode().trim(), "BOX_IN_STOCK");
			if (box == null) {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						"Box not found!! Please check box code Or Box status..");
			}
			ReConfigureBox findExistReConfigureBoxByBoxId = reConfigureBoxRepository
					.findExistReConfigureBoxByBoxId(box.getId());
			if (findExistReConfigureBoxByBoxId != null) {
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(),
						"Configuration already exist for this box !!");
			}
			List<GetCommandKey> getCommandKeys = getCommandKeyRepository.findAll();
			Map<String, GetCommandKey> commandKeyMap = getCommandKeys.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(GetCommandKey::getCommandKey, Function.identity()));
			ReConfigureBox reConfigureBox = new ReConfigureBox();
			reConfigureBox.setBoxId(box.getId());
			reConfigureBox.setBoxNo(box.getBoxNo());
			reConfigureBox.setConfigCommand(request.getCommand());
			reConfigureBox.setCreatedAt(new Date());
			reConfigureBox.setCreatedBy(new User(request.getUserId()));
			reConfigureBox.setIsActive(true);
			reConfigureBox.setIsCompleted(false);
			// create box-no
			reConfigureBox.setReConfigBoxCode(TokenUtility.getReConfigBoxNumber(box.getState().getId()));
			reConfigureBox.setTotalConfigureDevice(0);
			reConfigureBox.setTotalDevice(0);
			reConfigureBox.setTotalUnboxDevice(0);
			reConfigureBox.setStateId(new State(request.getStateId()));
			ReConfigureBox rcb = reConfigureBoxRepository.save(reConfigureBox);
			Box createBox = new Box();
			createBox.setBoxNo(rcb.getReConfigBoxCode());
			createBox.setCreatedAt(new Date());
			createBox.setCreatedBy(new User(request.getUserId()));
			createBox.setQuantity(0d);
			createBox.setCurrentQuantity(0d);
			createBox.setIsActive(true);
			createBox.setState(new State(request.getStateId()));
			createBox.setStatus(StatusMaster.BOX_IN_STOCK);
			Box boxDetails = boxRepository.save(createBox);
			BoxTransaction boxTransaction = new BoxTransaction();
			boxTransaction.setBox(boxDetails);
			boxTransaction.setCreatedAt(new Date());
			boxTransaction.setCreatedBy(boxDetails.getCreatedBy());
			boxTransaction.setIsActive(true);
			boxTransaction.setQuantity(0d);
			boxTransactionRepository.save(boxTransaction);
			// create all commands
			List<ReConfigureCommand> createReConfigureCommands = new ArrayList<>();
			Map<String, String> keyToVerify = new HashMap<>();
			keyToVerify.put("IMEI", "CHECK_IMEI_NUMBER");
			String key = "";
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				key = objectMapper.writeValueAsString(keyToVerify);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			ReConfigureCommand firstCommand = new ReConfigureCommand();
			firstCommand.setCommand("GET SINFO1:");
			firstCommand.setReConfigureBoxId(rcb.getId());
			firstCommand.setKeyToVerify(key);
			firstCommand.setWaitingTime(10000);
			createReConfigureCommands.add(firstCommand);
			ReConfigureCommand secondCommand = new ReConfigureCommand();
			secondCommand.setCommand(request.getCommand());
			secondCommand.setReConfigureBoxId(rcb.getId());
			secondCommand.setWaitingTime(10000);
			createReConfigureCommands.add(secondCommand);
			for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {
				if (request.getCommand().contains(entry.getKey())) {
					ReConfigureCommand config = new ReConfigureCommand();
					config.setCommand(entry.getValue().getCommand());
					String extractValue = extractValue(request.getCommand(), entry.getKey());
					config.setResponse(extractValue);
					config.setReConfigureBoxId(rcb.getId());
					createReConfigureCommands.add(config);
				}
			}
			reConfigureCommandRepository.saveAll(createReConfigureCommands);
			return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), rcb);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	public static String extractValue(String input, String key) {
		String patternString = key + ":(.*?)(?:,|$)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	@Override
	public Response<?> reconfigureDevice(GenericRequestBody request) {
		try {
			if (request.getReConfigureBoxId() == null || request.getImeiNo() == null || request.getImeiNo() == "") {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			Optional<ReConfigureBox> rcbOptional = reConfigureBoxRepository.findById(request.getReConfigureBoxId());
			ReConfigureBox rcb = rcbOptional.get();
			ReConfigureDevices configureDevices = reConfigureDevicesRepository
					.findByImeiAndReConfigBoxId(request.getImeiNo(), request.getReConfigureBoxId());
			List<String> list = new ArrayList<>();
			list.add(rcb.getReConfigBoxCode());
			list.add(rcb.getBoxNo());
			List<Box> boxList = boxRepository.findByBoxCodes(list, StatusMaster.BOX_IN_STOCK.name());
			Optional<Box> newReConfigureboxOpt = boxList.stream()
					.filter(o -> o.getBoxNo().equalsIgnoreCase(rcb.getReConfigBoxCode())).findFirst();
			Optional<Box> oldReConfigureboxOpt = boxList.stream()
					.filter(o -> o.getBoxNo().equalsIgnoreCase(rcb.getBoxNo())).findFirst();
			Box newReConfigurebox = newReConfigureboxOpt.get();
			Box oldReConfigurebox = oldReConfigureboxOpt.get();
			BoxTransaction newBoxTransaction = boxTransactionRepository.findByBoxId(newReConfigurebox.getId());
			// BoxTransaction oldBoxTransaction =
			// boxTransactionRepository.findByBoxId(oldReConfigurebox.getId());
			Optional<Device> device = deviceRepository.findByImeiNo(request.getImeiNo());
			BoxDevice oldMapping = boxDeviceRepository.findByBoxIdAndDeviceId(rcb.getBoxId(), device.get().getId());
			if (configureDevices != null) {
				if (configureDevices.getIsReConfigure()) {
					return new Response<>(HttpStatus.OK.value(), "Device Already ReConfigured");
				} else {
					if (request.getIsReConfigured() == true) {
						rcb.setTotalConfigureDevice(rcb.getTotalConfigureDevice() + 1);
						reConfigureBoxRepository.save(rcb);
						// add device in box and map with box-device and update transaction and update
						// boxcode in device remove box from old box
						oldMapping.setIsActive(false);
						oldMapping.setIsPresent(false);
						boxDeviceRepository.save(oldMapping);
						// update box quantity
						// Box oldBox = boxTransaction.getBox();
						oldReConfigurebox.setCurrentQuantity(oldReConfigurebox.getCurrentQuantity() - 1);
						oldReConfigurebox.setQuantity(oldReConfigurebox.getQuantity() - 1);
						boxRepository.save(oldReConfigurebox);
						// update device
						device.get().setBoxCode(newReConfigurebox.getBoxNo());
						device.get().setUpdatedAt(new Date());
						device.get().setModifiedBy(request.getUserId());
						device.get().setState(rcb.getStateId());
						deviceRepository.save(device.get());
						newBoxTransaction.setQuantity(newBoxTransaction.getQuantity() + 1);
						boxTransactionRepository.save(newBoxTransaction);
						BoxDevice boxDevice = new BoxDevice();
						boxDevice.setBox(newReConfigurebox);
						boxDevice.setDevice(device.get());
						boxDevice.setEntryTransactionId(newBoxTransaction);
						boxDevice.setIsActive(true);
						boxDevice.setIsPresent(true);
						boxDeviceRepository.save(boxDevice);
						newReConfigurebox.setQuantity(newReConfigurebox.getQuantity() + 1);
						newReConfigurebox.setCurrentQuantity(newReConfigurebox.getCurrentQuantity() + 1);
						boxRepository.save(newReConfigurebox);
					}
					configureDevices.setIsReConfigure(request.getIsReConfigured());
					configureDevices.setUpdatedAt(new Date());
					configureDevices.setUpdatedBy(new User(request.getUserId()));
					ReConfigureDevices reConfigureDevices = reConfigureDevicesRepository.save(configureDevices);
					return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), reConfigureDevices);
				}
			} else {
				if (request.getIsReConfigured() == true) {
					rcb.setTotalConfigureDevice(rcb.getTotalConfigureDevice() + 1);
					reConfigureBoxRepository.save(rcb);
					// add device in box and map with box-device and update transaction and update
					// boxcode in device remove box from old box
					oldMapping.setIsActive(false);
					oldMapping.setIsPresent(false);
					boxDeviceRepository.save(oldMapping);
					// update box quantity
					// Box oldBox = boxTransaction.getBox();
					oldReConfigurebox.setCurrentQuantity(oldReConfigurebox.getCurrentQuantity() - 1);
					oldReConfigurebox.setQuantity(oldReConfigurebox.getQuantity() - 1);
					boxRepository.save(oldReConfigurebox);
					// update device
					device.get().setBoxCode(newReConfigurebox.getBoxNo());
					device.get().setUpdatedAt(new Date());
					device.get().setModifiedBy(request.getUserId());
					device.get().setState(rcb.getStateId());
					deviceRepository.save(device.get());
					newBoxTransaction.setQuantity(newBoxTransaction.getQuantity() + 1);
					boxTransactionRepository.save(newBoxTransaction);
					BoxDevice boxDevice = new BoxDevice();
					boxDevice.setBox(newReConfigurebox);
					boxDevice.setDevice(device.get());
					boxDevice.setEntryTransactionId(newBoxTransaction);
					boxDevice.setIsActive(true);
					boxDevice.setIsPresent(true);
					boxDeviceRepository.save(boxDevice);
					newReConfigurebox.setQuantity(newReConfigurebox.getQuantity() + 1);
					newReConfigurebox.setCurrentQuantity(newReConfigurebox.getCurrentQuantity() + 1);
					boxRepository.save(newReConfigurebox);
				}
				ReConfigureDevices reConfigureDevice = new ReConfigureDevices();
				reConfigureDevice.setCreatedAt(new Date());
				reConfigureDevice.setCreatedBy(new User(request.getUserId()));
				reConfigureDevice.setUpdatedAt(new Date());
				reConfigureDevice.setUpdatedBy(new User(request.getUserId()));
				reConfigureDevice.setImeiNo(request.getImeiNo());
				reConfigureDevice.setIsReConfigure(request.getIsReConfigured());
				reConfigureDevice.setReConfigBoxId(request.getReConfigureBoxId());
				ReConfigureDevices reConfigureDevices = reConfigureDevicesRepository.save(reConfigureDevice);
				return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(),
						reConfigureDevices);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<?> finishReconfigure(GenericRequestBody request) {
		Optional<Box> boxOptional = boxRepository.findById(request.getBoxId());
		Optional<ReConfigureBox> reConfigureBoxOptional = reConfigureBoxRepository
				.findById(request.getReConfigureBoxId());
		List<ReConfigureDevices> reConfigureDevices = reConfigureDevicesRepository
				.findByReConfigureBoxId(request.getReConfigureBoxId());
		try {
			List<String> reconfigureImei = reConfigureDevices.stream()
					.filter(o -> o != null && o.getImeiNo() != null && o.getImeiNo() != "").map(x -> x.getImeiNo())
					.collect(Collectors.toList());
			ReConfigureBox reConfigureBox = reConfigureBoxOptional.get();
			Box box = boxOptional.get();
			if (box.getStatus() == StatusMaster.BOX_IN_STOCK) {
				List<BoxDevice> boxDeviceList = boxDeviceRepository.findAllByIds(request.getBoxId());
				List<Long> deviceIds = boxDeviceList.stream().filter(o -> o.getDevice().getId() != null)
						.map(o -> o.getDevice().getId()).collect(Collectors.toList());
				List<Device> devices = deviceRepository.findAllByDeviceIds(deviceIds);
				// update device box_no
				// to-do
				List<Device> unboxDevices = devices.stream()
						.filter(device -> !reconfigureImei.contains(device.getImeiNo())).collect(Collectors.toList());
				//
				List<Long> unmapDeviceIds = unboxDevices.stream().filter(o -> o.getId() != null).map(o -> o.getId())
						.collect(Collectors.toList());

				List<BoxDevice> unmapboxDevices = boxDeviceList.stream()
						.filter(o -> !unmapDeviceIds.contains(o.getDevice().getId())).collect(Collectors.toList());

				List<Device> deviceList = new ArrayList<>();
				for (Device device : unboxDevices) {
					device.setStatus(StatusMaster.DEVICE_PACKED);
					device.setUpdatedAt(new Date());
					device.setBoxCode(null);
					device.setModifiedBy(request.getUserId());
					deviceList.add(device);
				}
				deviceRepository.saveAll(deviceList);
				List<BoxDevice> boxDevices = new ArrayList<>();
				for (BoxDevice boxDevice : unmapboxDevices) {
					boxDevice.setIsActive(false);
					boxDevice.setIsPresent(false);
					boxDevices.add(boxDevice);
				}
				boxDeviceRepository.saveAll(boxDevices);
				box.setCurrentQuantity(box.getCurrentQuantity() - unboxDevices.size());
				box.setQuantity(box.getQuantity() - unboxDevices.size());
				box.setUpdatedAt(new Date());
				box.setUpdatedBy(new User(request.getUserId()));
				boxRepository.save(box);

				reConfigureBox.setTotalUnboxDevice(unboxDevices.size());
				reConfigureBox.setUpdatedAt(new Date());
				reConfigureBox.setUpdatedBy(new User(request.getUserId()));
				reConfigureBox.setIsCompleted(true);
				reConfigureBoxRepository.save(reConfigureBox);
				return new Response<>(HttpStatus.OK.value(), "Reconfiguration Completed for this Box");
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Box status is not in BOX_IN_STOCK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<BoxDeviceResponseDTO> getBoxDevice(GenericRequestBody request) {
		if (request.getReConfigureBoxId() == null || request.getBoxId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
		Response<BoxDeviceResponseDTO> response = new Response<>();
		List<BoxDevice> boxList = boxDeviceRepository.FindByBox_idAndIsActiveTrue(request.getBoxId());
		if (boxList != null && boxList.size() > 0) {
			BoxDeviceDTO boxDeviceDTO = new BoxDeviceDTO();
			boxDeviceDTO.setBoxDTO(boxList.get(0).getBox().convertEntityToDto(boxList.get(0).getBox()));
			List<Device> mapDeviceList = boxList.stream().filter(o -> o.getDevice() != null).map(o -> o.getDevice())
					.collect(Collectors.toList());
			boxDeviceDTO.setDeviceList(mapDeviceList);
			List<ReConfigureDevicesDTO> reConfigureDevicesDTOlist = new ArrayList<>();
			List<ReConfigureDevices> reConfigureDevices = reConfigureDevicesRepository
					.findByReConfigureBoxId(request.getReConfigureBoxId());
			for (ReConfigureDevices reConfigureDevice : reConfigureDevices) {
				ReConfigureDevicesDTO dto = ReConfigureDevices.convertToDTO(reConfigureDevice);
				reConfigureDevicesDTOlist.add(dto);
			}
			BoxDeviceResponseDTO boxDeviceResponseDTO = new BoxDeviceResponseDTO();
			boxDeviceResponseDTO.setBoxDeviceDTO(boxDeviceDTO);
			boxDeviceResponseDTO.setReConfigureDevicesDTO(reConfigureDevicesDTOlist);
			response.setData(boxDeviceResponseDTO);
			response.setMessage(HttpStatus.OK.getReasonPhrase());
			response.setResponseCode(HttpStatus.OK.value());
			return response;
		} else {
			response.setData(null);
			response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
			response.setResponseCode(HttpStatus.NOT_FOUND.value());
			return response;
		}
	}

	@Override
	public Pagination<List<ReConfigureBoxDTO>> getAllReConfigureBox(GenericRequestBody request) {
		Pagination<List<ReConfigureBoxDTO>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (request.getPageSize() != null && request.getPageNo() != null && request.getPageSize() > 0) {
				pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
			}
			Page<ReConfigureBox> page = reConfigureBoxRepository.findAll(request, pageRequest);
			List<ReConfigureBoxDTO> resp = page.getContent().stream().map(e -> ReConfigureBox.convertToDTOV2(e))
					.collect(Collectors.toList());
			response.setData(resp);
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Pagination<List<ReConfigureDevicesDTO>> getAllReConfigureBoxDevices(GenericRequestBody request) {
		Pagination<List<ReConfigureDevicesDTO>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (request.getPageSize() != null && request.getPageNo() != null && request.getPageSize() > 0) {
				pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
			}
			Page<ReConfigureDevices> page = reConfigureDevicesRepository.findAll(request, pageRequest);
			List<ReConfigureDevicesDTO> resp = page.getContent().stream().map(e -> ReConfigureDevices.convertToDTO(e))
					.collect(Collectors.toList());
			response.setData(resp);
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response<BoxDeviceDTO> getBoxDeviceV2(GenericRequestBody request) {
		if (request.getReConfigureBoxId() == null || request.getBoxId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
		List<BoxDevice> boxList = boxDeviceRepository.FindByBox_idAndIsActiveTrue(request.getBoxId());
		if (boxList != null && boxList.size() > 0) {
			BoxDeviceDTO boxDeviceDTO = new BoxDeviceDTO();
			
			boxDeviceDTO.setBoxDTO(boxList.get(0).getBox().convertEntityToDto(boxList.get(0).getBox()));
			Collections.sort(boxList, (boxDevice1, boxDevice2) -> boxDevice2.getId().compareTo(boxDevice1.getId()));
			List<Device> mapDeviceList = boxList.stream().filter(o -> o.getDevice() != null).map(o -> o.getDevice())
					.collect(Collectors.toList());
			boxDeviceDTO.setDeviceList(mapDeviceList);
			List<ReConfigureDevices> reConfigureDevices = reConfigureDevicesRepository
					.findByReConfigureBoxId(request.getReConfigureBoxId());
			Map<String, ReConfigureDevices> imeiMap = reConfigureDevices.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(ReConfigureDevices::getImeiNo, Function.identity()));

			for (Device device : mapDeviceList) {
				if (imeiMap.containsKey(device.getImeiNo())) {
					device.setIsConfigured(true);
					ReConfigureDevices reConfigureDevices2 = imeiMap.get(device.getImeiNo());
					device.setReconfiguredAt(reConfigureDevices2.getUpdatedAt());
				}
			}
			return new Response<BoxDeviceDTO>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), boxDeviceDTO);
		} else {
			return new Response<BoxDeviceDTO>(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
		}
	}

	@Override
	public Response<?> finishReconfigureV2(GenericRequestBody request) {
		try {
			Optional<ReConfigureBox> reConfigureBoxOptional = reConfigureBoxRepository
					.findById(request.getReConfigureBoxId());
			if (!reConfigureBoxOptional.isPresent()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "Reconfiguration Box Not Found");
			}
			ReConfigureBox reConfigureBox = reConfigureBoxOptional.get();
			if (reConfigureBox.getIsCompleted() != null && reConfigureBox.getIsCompleted()) {
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Box completely reconfigured or unbox!!!..");
			}
			List<BoxDevice> boxDeviceList = boxDeviceRepository
					.findAllByIdsAndIsPresent(reConfigureBoxOptional.get().getBoxId());
			if (boxDeviceList.isEmpty()) {
				reConfigureBox.setIsCompleted(true);
				reConfigureBox.setUpdatedAt(new Date());
				reConfigureBox.setUpdatedBy(new User(request.getUserId()));
				reConfigureBoxRepository.save(reConfigureBox);
				// old and new box update
				Optional<Box> oldBoxOpt = boxRepository.findById(reConfigureBox.getBoxId());
				Box oldBox = oldBoxOpt.get();
				oldBox.setUpdatedAt(new Date());
				oldBox.setUpdatedBy(new User(request.getUserId()));
				oldBox.setQuantity(0d);
				oldBox.setCurrentQuantity(0d);
				boxRepository.save(oldBox);
				return new Response<>(HttpStatus.NOT_FOUND.value(), "Device Not Found In This Box");
			}
			Box box = boxDeviceList.get(0).getBox();

			if (box.getStatus() == StatusMaster.BOX_IN_STOCK) {
				List<BoxDevice> updateOldMapping = new ArrayList<>();
				for (BoxDevice bd : boxDeviceList) {
					bd.setIsActive(false);
					bd.setIsPresent(false);
					updateOldMapping.add(bd);
				}
				boxDeviceRepository.saveAll(updateOldMapping);
				box.setUpdatedAt(new Date());
				box.setUpdatedBy(new User(request.getUserId()));
				box.setStatus(null);
				box.setIsActive(false);
				box.setQuantity(0d);
				box.setCurrentQuantity(0d);
				box.setUpdatedAt(new Date());
				box.setUpdatedBy(new User(request.getUserId()));
				boxRepository.save(box);

				List<Long> deviceIds = boxDeviceList.stream().filter(o -> o.getDevice().getId() != null)
						.map(o -> o.getDevice().getId()).collect(Collectors.toList());
				List<Device> devices = deviceRepository.findAllByDeviceIds(deviceIds);
				// create box
				Box newBox = new Box();
				Double quantity = (double) devices.size();
				newBox.setCurrentQuantity(quantity);
				newBox.setQuantity(50d);
				newBox.setStatus(StatusMaster.BOX_IN_STOCK);
				newBox.setCreatedAt(new Date());
				newBox.setCreatedBy(new User(request.getUserId()));
				newBox.setBoxNo(TokenUtility.getBoxNextNumber(box.getState().getId()));
				newBox.setIsActive(true);
				newBox.setState(box.getState());
				if (box.getProvider() != null) {
					newBox.setProvider(box.getProvider());
				}
				Box boxDetails = boxRepository.save(newBox);
				BoxTransaction boxTransaction = new BoxTransaction();
				boxTransaction.setBox(boxDetails);
				boxTransaction.setCreatedAt(new Date());
				boxTransaction.setCreatedBy(boxDetails.getCreatedBy());
				boxTransaction.setIsActive(true);
				boxTransaction.setQuantity(boxDetails.getQuantity());
				BoxTransaction boxTransactionDetails = boxTransactionRepository.save(boxTransaction);
				List<BoxDevice> newBoxDeviceList = new ArrayList<>();

				for (Device device : devices) {
					device.setUpdatedAt(new Date());
					device.setModifiedBy(request.getUserId());
					device.setBoxCode(boxDetails.getBoxNo());
					BoxDevice boxDevice = new BoxDevice();
					boxDevice.setBox(boxDetails);
					boxDevice.setDevice(device);
					boxDevice.setEntryTransactionId(boxTransactionDetails);
					boxDevice.setIsActive(true);
					boxDevice.setIsPresent(true);
					newBoxDeviceList.add(boxDevice);
				}

				deviceRepository.saveAll(devices);
				boxDeviceRepository.saveAll(newBoxDeviceList);

				reConfigureBox.setUnsettledBoxCode(boxDetails.getBoxNo());
				reConfigureBox.setTotalUnboxDevice(devices.size());
				reConfigureBox.setUpdatedAt(new Date());
				reConfigureBox.setUpdatedBy(new User(request.getUserId()));
				reConfigureBox.setIsCompleted(true);
				reConfigureBoxRepository.save(reConfigureBox);
				return new Response<>(HttpStatus.OK.value(), "Reconfiguration Completed for this Box");
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Box status is not in BOX_IN_STOCK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<List<ReConfigureCommand>> getAllCommands(GenericRequestBody request) {
		if (request.getReConfigureBoxId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "RECONFIGURE  BOX ID CAN'T BE NULL");
		}
		List<ReConfigureCommand> list = reConfigureCommandRepository
				.findByReconfigureBoxId(request.getReConfigureBoxId());
		if (list.isEmpty()) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Reconfiguration Command Not Found");
		}
		return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), list);
	}

	@Override
	public Response<?> saveReConfigureCommandV2(GenericRequestBody request) {
		try {
			if (request.getCommand() == null || request.getCommand() == "") {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			if (request.getBoxCode() == null || request.getBoxCode() == "") {
				List<GetCommandKey> getCommandKeys = getCommandKeyRepository.findAll();
				Map<String, GetCommandKey> commandKeyMap = getCommandKeys.stream().filter(o -> o.getId() != null)
						.collect(Collectors.toMap(GetCommandKey::getCommandKey, Function.identity()));
				ReConfigureBox reConfigureBox = new ReConfigureBox();
				reConfigureBox.setBoxId(null);
				reConfigureBox.setBoxNo(null);
				reConfigureBox.setConfigCommand(request.getCommand());
				reConfigureBox.setCreatedAt(new Date());
				reConfigureBox.setCreatedBy(new User(request.getUserId()));
				reConfigureBox.setIsActive(true);
				reConfigureBox.setIsCompleted(false);
				// create box-no
				reConfigureBox.setReConfigBoxCode(TokenUtility.getReConfigBoxNumber(request.getStateId()));
				reConfigureBox.setTotalConfigureDevice(0);
				reConfigureBox.setTotalDevice(0);
				reConfigureBox.setTotalUnboxDevice(0);
				reConfigureBox.setStateId(new State(request.getStateId()));
				reConfigureBox.setProvider(new Provider(request.getSimProviderId()));
				ReConfigureBox rcb = reConfigureBoxRepository.save(reConfigureBox);
				Box createBox = new Box();
				createBox.setBoxNo(rcb.getReConfigBoxCode());
				createBox.setCreatedAt(new Date());
				createBox.setCreatedBy(new User(request.getUserId()));
				createBox.setQuantity(50d);
				createBox.setCurrentQuantity(0d);
				createBox.setIsActive(true);
				createBox.setState(new State(request.getStateId()));
				createBox.setStatus(StatusMaster.BOX_IN_STOCK);
				createBox.setProvider(rcb.getProvider());
				Box boxDetails = boxRepository.save(createBox);
				BoxTransaction boxTransaction = new BoxTransaction();
				boxTransaction.setBox(boxDetails);
				boxTransaction.setCreatedAt(new Date());
				boxTransaction.setCreatedBy(boxDetails.getCreatedBy());
				boxTransaction.setIsActive(true);
				boxTransaction.setQuantity(0d);
				boxTransactionRepository.save(boxTransaction);
				// create all commands
				List<ReConfigureCommand> createReConfigureCommands = new ArrayList<>();
				Map<String, String> keyToVerify = new HashMap<>();
				keyToVerify.put("IMEI", "CHECK_IMEI_NUMBER");
				String key = "";
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					key = objectMapper.writeValueAsString(keyToVerify);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				ReConfigureCommand firstCommand = new ReConfigureCommand();
				firstCommand.setCommand("GET SINFO1:");
				firstCommand.setReConfigureBoxId(rcb.getId());
				firstCommand.setKeyToVerify(key);
				firstCommand.setWaitingTime(10000);
				createReConfigureCommands.add(firstCommand);
				ReConfigureCommand secondCommand = new ReConfigureCommand();
				secondCommand.setCommand(request.getCommand());
				secondCommand.setReConfigureBoxId(rcb.getId());
				secondCommand.setWaitingTime(10000);
				createReConfigureCommands.add(secondCommand);
				for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {
					if (request.getCommand().contains(entry.getKey())) {
						ReConfigureCommand config = new ReConfigureCommand();
						config.setCommand(entry.getValue().getCommand());
						String extractValue = extractValue(request.getCommand(), entry.getKey());
						config.setResponse(extractValue);
						config.setReConfigureBoxId(rcb.getId());
						createReConfigureCommands.add(config);
					}
				}
				reConfigureCommandRepository.saveAll(createReConfigureCommands);
				return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), rcb);
			} else {
				Box box = boxRepository.findByBoxCode(request.getBoxCode().trim(), "BOX_IN_STOCK");
				if (box == null) {
					return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
							"Box not found!! Please check box code Or Box status..");
				}
				ReConfigureBox findExistReConfigureBoxByBoxId = reConfigureBoxRepository
						.findExistReConfigureBoxByBoxId(box.getId());
				if (findExistReConfigureBoxByBoxId != null) {
					return new Response<>(HttpStatus.ALREADY_REPORTED.value(),
							"Configuration already exist for this box !!");
				}
				List<GetCommandKey> getCommandKeys = getCommandKeyRepository.findAll();
				Map<String, GetCommandKey> commandKeyMap = getCommandKeys.stream().filter(o -> o.getId() != null)
						.collect(Collectors.toMap(GetCommandKey::getCommandKey, Function.identity()));
				ReConfigureBox reConfigureBox = new ReConfigureBox();
				reConfigureBox.setBoxId(box.getId());
				reConfigureBox.setBoxNo(box.getBoxNo());
				reConfigureBox.setConfigCommand(request.getCommand());
				reConfigureBox.setCreatedAt(new Date());
				reConfigureBox.setCreatedBy(new User(request.getUserId()));
				reConfigureBox.setIsActive(true);
				reConfigureBox.setIsCompleted(false);
				// create box-no
				reConfigureBox.setReConfigBoxCode(TokenUtility.getReConfigBoxNumber(box.getState().getId()));
				reConfigureBox.setTotalConfigureDevice(0);
				reConfigureBox.setTotalDevice(0);
				reConfigureBox.setTotalUnboxDevice(0);
				reConfigureBox.setStateId(new State(request.getStateId()));
				reConfigureBox.setProvider(new Provider(request.getSimProviderId()));
				ReConfigureBox rcb = reConfigureBoxRepository.save(reConfigureBox);
				Box createBox = new Box();
				createBox.setBoxNo(rcb.getReConfigBoxCode());
				createBox.setCreatedAt(new Date());
				createBox.setCreatedBy(new User(request.getUserId()));
				createBox.setQuantity(50d);
				createBox.setCurrentQuantity(0d);
				createBox.setIsActive(true);
				createBox.setState(new State(request.getStateId()));
				createBox.setStatus(StatusMaster.BOX_IN_STOCK);
				createBox.setProvider(rcb.getProvider());
				Box boxDetails = boxRepository.save(createBox);
				BoxTransaction boxTransaction = new BoxTransaction();
				boxTransaction.setBox(boxDetails);
				boxTransaction.setCreatedAt(new Date());
				boxTransaction.setCreatedBy(boxDetails.getCreatedBy());
				boxTransaction.setIsActive(true);
				boxTransaction.setQuantity(0d);
				boxTransactionRepository.save(boxTransaction);
				// create all commands
				List<ReConfigureCommand> createReConfigureCommands = new ArrayList<>();
				Map<String, String> keyToVerify = new HashMap<>();
				keyToVerify.put("IMEI", "CHECK_IMEI_NUMBER");
				String key = "";
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					key = objectMapper.writeValueAsString(keyToVerify);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				ReConfigureCommand firstCommand = new ReConfigureCommand();
				firstCommand.setCommand("GET SINFO1:");
				firstCommand.setReConfigureBoxId(rcb.getId());
				firstCommand.setKeyToVerify(key);
				firstCommand.setWaitingTime(10000);
				createReConfigureCommands.add(firstCommand);
				ReConfigureCommand secondCommand = new ReConfigureCommand();
				secondCommand.setCommand(request.getCommand());
				secondCommand.setReConfigureBoxId(rcb.getId());
				secondCommand.setWaitingTime(10000);
				createReConfigureCommands.add(secondCommand);
				for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {
					if (request.getCommand().contains(entry.getKey())) {
						ReConfigureCommand config = new ReConfigureCommand();
						config.setCommand(entry.getValue().getCommand());
						String extractValue = extractValue(request.getCommand(), entry.getKey());
						config.setResponse(extractValue);
						config.setReConfigureBoxId(rcb.getId());
						createReConfigureCommands.add(config);
					}
				}
				reConfigureCommandRepository.saveAll(createReConfigureCommands);
				return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), rcb);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	@Override
	public Response<?> getDeviceInfoV2(GenericRequestBody request) {
		if (request.getSearch() == null && request.getSearch() == "") {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}

		DeviceLite device = deviceLiteRepository.findByImeiOrIccidOrUuid(request.getSearch());
		if (device == null) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "DEVICE NOT FOUND");
		}

		return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), device);
	}

	//	@Override
	//	public Response<?> addReconfigureDevice(GenericRequestBody request) {
	//		try {
	//			if (request.getReConfigureBoxId() == null || request.getImeiNo() == null || request.getImeiNo() == ""
	//					|| request.getReConfigBoxCode() == null || request.getReConfigBoxCode() == "") {
	//				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	//			}
	//			DeviceLite device = deviceLiteRepository.findByImeiOrIccidOrUuid(request.getImeiNo());
	//			if (!device.getStatus().equals(StatusMaster.BOX_PACKED)
	//					|| !device.getStatus().equals(StatusMaster.DEVICE_PACKED)) {
	//				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
	//						"Failed Due to device is in : " + device.getStatus());
	//			}
	//			ReConfigureBox reConfigureBox = reConfigureBoxRepository.getById(request.getReConfigureBoxId());
	//			if (reConfigureBox.getTotalDevice() == 50) {
	//				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
	//						"Box quantity can't be more than 50.. Add Device in new box..");
	//			}
	//			Box updateBox = boxRepository.findByBoxNumber(request.getReConfigBoxCode());
	//			BoxTransaction boxTransactionDetails = boxTransactionRepository.findByBoxId(updateBox.getId());
	//			if (device.getStatus().equals(StatusMaster.DEVICE_PACKED)) {
	//
	//				ReConfigureDevices reConfigureDevices = new ReConfigureDevices();
	//				reConfigureDevices.setCreatedAt(new Date());
	//				reConfigureDevices.setCreatedBy(new User(request.getUserId()));
	//				reConfigureDevices.setImeiNo(device.getImeiNo());
	//				reConfigureDevices.setIsReConfigure(true);
	//				reConfigureDevices.setReConfigBoxId(reConfigureBox.getId());
	//				reConfigureDevicesRepository.save(reConfigureDevices);
	//				device.setState(new State(reConfigureBox.getStateId().getId()));
	//				device.setStatus(StatusMaster.BOX_PACKED);
	//				device.setUpdatedAt(new Date());
	//				device.setBoxCode(updateBox.getBoxNo());
	//				device.setModifiedBy(request.getUserId());
	//				deviceLiteRepository.save(device);
	//				Box oldBox = boxRepository.findByBoxNumber(device.getBoxCode());
	//				oldBox.setCurrentQuantity(updateBox.getCurrentQuantity() - 1);
	//				oldBox.setUpdatedAt(new Date());
	//				oldBox.setUpdatedBy(new User(request.getUserId()));
	//				boxRepository.save(oldBox);
	//				BoxDevice boxDevices = boxDeviceRepository.findByDeviceIdV2(device.getId());
	//				boxDevices.setIsActive(false);
	//				boxDevices.setIsPresent(false);
	//				boxDeviceRepository.save(boxDevices);
	//				updateBox.setCurrentQuantity(updateBox.getCurrentQuantity() + 1);
	//				updateBox.setUpdatedAt(new Date());
	//				updateBox.setUpdatedBy(new User(request.getUserId()));
	//				boxRepository.save(updateBox);
	//				BoxDeviceV2 boxDevice = new BoxDeviceV2();
	//				boxDevice.setBox(updateBox);
	//				boxDevice.setDevice(device);
	//				boxDevice.setEntryTransactionId(boxTransactionDetails);
	//				boxDevice.setIsActive(true);
	//				boxDevice.setIsPresent(true);
	//				boxDeviceV2Repository.save(boxDevice);
	//				reConfigureBox.setUpdatedAt(new Date());
	//				reConfigureBox.setUpdatedBy(new User(request.getUserId()));
	//				reConfigureBox.setTotalConfigureDevice(reConfigureBox.getTotalConfigureDevice() + 1);
	//				reConfigureBox.setTotalDevice(reConfigureBox.getTotalDevice() + 1);
	//				reConfigureBoxRepository.save(reConfigureBox);
	//				return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
	//			} else {
	//				ReConfigureDevices reConfigureDevices = new ReConfigureDevices();
	//				reConfigureDevices.setCreatedAt(new Date());
	//				reConfigureDevices.setCreatedBy(new User(request.getUserId()));
	//				reConfigureDevices.setImeiNo(device.getImeiNo());
	//				reConfigureDevices.setIsReConfigure(true);
	//				reConfigureDevices.setReConfigBoxId(reConfigureBox.getId());
	//				reConfigureDevicesRepository.save(reConfigureDevices);
	//				device.setState(new State(reConfigureBox.getStateId().getId()));
	//				device.setStatus(StatusMaster.BOX_PACKED);
	//				device.setUpdatedAt(new Date());
	//				device.setBoxCode(updateBox.getBoxNo());
	//				device.setModifiedBy(request.getUserId());
	//				deviceLiteRepository.save(device);
	//				updateBox.setCurrentQuantity(updateBox.getCurrentQuantity() + 1);
	//				updateBox.setUpdatedAt(new Date());
	//				updateBox.setUpdatedBy(new User(request.getUserId()));
	//				boxRepository.save(updateBox);
	//				BoxDeviceV2 boxDevice = new BoxDeviceV2();
	//				boxDevice.setBox(updateBox);
	//				boxDevice.setDevice(device);
	//				boxDevice.setEntryTransactionId(boxTransactionDetails);
	//				boxDevice.setIsActive(true);
	//				boxDevice.setIsPresent(true);
	//				boxDeviceV2Repository.save(boxDevice);
	//				reConfigureBox.setUpdatedAt(new Date());
	//				reConfigureBox.setUpdatedBy(new User(request.getUserId()));
	//				reConfigureBox.setTotalConfigureDevice(reConfigureBox.getTotalConfigureDevice() + 1);
	//				reConfigureBox.setTotalDevice(reConfigureBox.getTotalDevice() + 1);
	//				reConfigureBoxRepository.save(reConfigureBox);
	//				return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
	//					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	//		}
	//	}

	public Response<?> reconfigureDeviceV2(GenericRequestBody request) {
		try {
			if (request.getReConfigureBoxId() == null || request.getImeiNo() == null || request.getImeiNo() == "") {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
			Optional<ReConfigureBox> rcbOptional = reConfigureBoxRepository.findById(request.getReConfigureBoxId());
			ReConfigureBox rcb = rcbOptional.get();
			if (rcb.getTotalConfigureDevice() < 50) {

			} else {
				return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "BOX QUANTITY EXCEEDS !!");
			}
			ReConfigureDevices configureDevices = reConfigureDevicesRepository
					.findByImeiAndReConfigBoxId(request.getImeiNo(), request.getReConfigureBoxId());
			Optional<Device> device = deviceRepository.findByImeiNo(request.getImeiNo());

			if (device.get().getSim1Provider() == null || rcb.getProvider() == null
					|| !device.get().getSim1Provider().equalsIgnoreCase(rcb.getProvider().getName())) {
				return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "Sim Provider Not Matched !!");
			}

			List<String> list = new ArrayList<>();
			list.add(rcb.getReConfigBoxCode());
			String requestBoxNo = rcb.getBoxNo();
			Box isDifferentBox = null;
			if (rcb.getBoxNo() != null && (device.get().getBoxCode() != null
					&& device.get().getBoxCode().equalsIgnoreCase(rcb.getBoxNo()))) {
				list.add(rcb.getBoxNo());
			} else {

				// if (request.getIsReConfigured() == true) {
				if (device.isPresent()) {
					if (device.get().getStatus().name().equalsIgnoreCase(StatusMaster.DEVICE_PACKED.name())) {
						// ADD IN RRECONFIGURED BOX
						// INCREMENT RECONFIGURED BOX
						// UPDATE DEVICE BOX CODE
						// return success;

						if (configureDevices != null) {
							if (configureDevices.getIsReConfigure()) {
								return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(),
										"Device Already ReConfigured!!");
							}
							if (request.getIsReConfigured() == true) {

								Box newReConfigurebox = boxRepository.findByBoxNumber(rcb.getReConfigBoxCode());
								if (newReConfigurebox.getCurrentQuantity().intValue() < newReConfigurebox.getQuantity()
										.intValue()) {

								} else {
									return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "Box Is Completed!!!");
								}
								newReConfigurebox.setCurrentQuantity(newReConfigurebox.getCurrentQuantity() + 1);
								newReConfigurebox.setUpdatedAt(new Date());
								newReConfigurebox.setUpdatedBy(new User(request.getUserId()));
								boxRepository.save(newReConfigurebox);

								BoxTransaction newBoxTransaction = boxTransactionRepository
										.findByBoxId(newReConfigurebox.getId());

								device.get().setState(new State(rcb.getStateId().getId()));
								device.get().setStatus(StatusMaster.BOX_PACKED);
								device.get().setUpdatedAt(new Date());
								device.get().setBoxCode(rcb.getReConfigBoxCode());
								device.get().setModifiedBy(request.getUserId());
								deviceRepository.save(device.get());

								BoxDevice boxDevice = new BoxDevice();
								boxDevice.setBox(newReConfigurebox);
								boxDevice.setDevice(device.get());
								boxDevice.setEntryTransactionId(newBoxTransaction);
								boxDevice.setIsActive(true);
								boxDevice.setIsPresent(true);
								boxDeviceRepository.save(boxDevice);

								rcb.setUpdatedAt(new Date());
								rcb.setUpdatedBy(new User(request.getUserId()));
								rcb.setTotalConfigureDevice(rcb.getTotalConfigureDevice() + 1);
								rcb.setTotalDevice(rcb.getTotalDevice() + 1);
								reConfigureBoxRepository.save(rcb);
							}
							configureDevices.setIsReConfigure(request.getIsReConfigured());
							configureDevices.setUpdatedAt(new Date());
							configureDevices.setUpdatedBy(new User(request.getUserId()));
							ReConfigureDevices reConfigureDevices = reConfigureDevicesRepository.save(configureDevices);
							return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
									reConfigureDevices);
						} else {
							if (request.getIsReConfigured() == true) {
								Box newReConfigurebox = boxRepository.findByBoxNumber(rcb.getReConfigBoxCode());
								if (newReConfigurebox.getCurrentQuantity().intValue() < newReConfigurebox.getQuantity()
										.intValue()) {

								} else {
									return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "Box Is Completed!!!");
								}
								BoxTransaction newBoxTransaction = boxTransactionRepository
										.findByBoxId(newReConfigurebox.getId());

								device.get().setState(new State(rcb.getStateId().getId()));
								device.get().setStatus(StatusMaster.BOX_PACKED);
								device.get().setUpdatedAt(new Date());
								device.get().setBoxCode(rcb.getReConfigBoxCode());
								device.get().setModifiedBy(request.getUserId());
								deviceRepository.save(device.get());

								newReConfigurebox.setCurrentQuantity(newReConfigurebox.getCurrentQuantity() + 1);
								newReConfigurebox.setUpdatedAt(new Date());
								newReConfigurebox.setUpdatedBy(new User(request.getUserId()));
								boxRepository.save(newReConfigurebox);

								BoxDevice boxDevice = new BoxDevice();
								boxDevice.setBox(newReConfigurebox);
								boxDevice.setDevice(device.get());
								boxDevice.setEntryTransactionId(newBoxTransaction);
								boxDevice.setIsActive(true);
								boxDevice.setIsPresent(true);
								boxDeviceRepository.save(boxDevice);

								rcb.setUpdatedAt(new Date());
								rcb.setUpdatedBy(new User(request.getUserId()));
								rcb.setTotalConfigureDevice(rcb.getTotalConfigureDevice() + 1);
								rcb.setTotalDevice(rcb.getTotalDevice() + 1);
								reConfigureBoxRepository.save(rcb);
							}
							ReConfigureDevices reConfigureDevice = new ReConfigureDevices();
							reConfigureDevice.setCreatedAt(new Date());
							reConfigureDevice.setCreatedBy(new User(request.getUserId()));
							reConfigureDevice.setUpdatedAt(new Date());
							reConfigureDevice.setUpdatedBy(new User(request.getUserId()));
							reConfigureDevice.setImeiNo(request.getImeiNo());
							reConfigureDevice.setIsReConfigure(request.getIsReConfigured());
							reConfigureDevice.setReConfigBoxId(request.getReConfigureBoxId());
							ReConfigureDevices reConfigureDevices = reConfigureDevicesRepository
									.save(reConfigureDevice);
							return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(),
									reConfigureDevices);
						}

					} else if (device.get().getStatus().name().equalsIgnoreCase(StatusMaster.BOX_PACKED.name())) {
						//
						list.add(device.get().getBoxCode());
						rcb.setBoxNo(device.get().getBoxCode());
						isDifferentBox = boxRepository.findByBoxNumber(device.get().getBoxCode());
						//
					} else if (device.get().getStatus().name().equalsIgnoreCase(StatusMaster.ISSUED_DEVICES.name())) {
						return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "Device Already Issued!!!");
					} else if (device.get().getStatus().name().equalsIgnoreCase(StatusMaster.TESTED.name())) {
						return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "Device Is In Tested Status!!!");
					}
				}
				// }
			}

			List<Box> boxList = boxRepository.findByBoxCodes(list, StatusMaster.BOX_IN_STOCK.name());
			Optional<Box> newReConfigureboxOpt = boxList.stream()
					.filter(o -> o.getBoxNo().equalsIgnoreCase(rcb.getReConfigBoxCode())).findFirst();
			Optional<Box> oldReConfigureboxOpt = boxList.stream()
					.filter(o -> o.getBoxNo().equalsIgnoreCase(rcb.getBoxNo())).findFirst();
			// SETTING REQUEST BOX NUMBER
			rcb.setBoxNo(requestBoxNo);
			// END SETTING REQUEST BOX NUMBER
			Box newReConfigurebox = newReConfigureboxOpt.get();
			Box oldReConfigurebox = oldReConfigureboxOpt.get();
			BoxTransaction newBoxTransaction = boxTransactionRepository.findByBoxId(newReConfigurebox.getId());
			// BoxTransaction oldBoxTransaction =
			// boxTransactionRepository.findByBoxId(oldReConfigurebox.getId());
			BoxDevice oldMapping = null;
			if (isDifferentBox != null) {
				oldMapping = boxDeviceRepository.findByBoxIdAndDeviceId(isDifferentBox.getId(), device.get().getId());
			} else {
				oldMapping = boxDeviceRepository.findByBoxIdAndDeviceId(rcb.getBoxId(), device.get().getId());
			}
			if (configureDevices != null) {
				if (configureDevices.getIsReConfigure()) {
					return new Response<>(HttpStatus.OK.value(), "Device Already ReConfigured");
				} else {
					if (request.getIsReConfigured() == true) {
						if (newReConfigurebox.getCurrentQuantity().intValue() < newReConfigurebox.getQuantity()
								.intValue()) {

						} else {
							return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "Box Is Completed!!!");
						}
						rcb.setTotalConfigureDevice(rcb.getTotalConfigureDevice() + 1);
						reConfigureBoxRepository.save(rcb);
						// add device in box and map with box-device and update transaction and update
						// boxcode in device remove box from old box
						oldMapping.setIsActive(false);
						oldMapping.setIsPresent(false);
						boxDeviceRepository.save(oldMapping);
						// update box quantity
						// Box oldBox = boxTransaction.getBox();
						oldReConfigurebox.setCurrentQuantity(oldReConfigurebox.getCurrentQuantity() - 1);
						oldReConfigurebox.setQuantity(oldReConfigurebox.getQuantity() - 1);
						boxRepository.save(oldReConfigurebox);
						// update device
						device.get().setBoxCode(newReConfigurebox.getBoxNo());
						device.get().setUpdatedAt(new Date());
						device.get().setModifiedBy(request.getUserId());
						device.get().setState(rcb.getStateId());
						deviceRepository.save(device.get());
						newBoxTransaction.setQuantity(newBoxTransaction.getQuantity() + 1);
						boxTransactionRepository.save(newBoxTransaction);
						BoxDevice boxDevice = new BoxDevice();
						boxDevice.setBox(newReConfigurebox);
						boxDevice.setDevice(device.get());
						boxDevice.setEntryTransactionId(newBoxTransaction);
						boxDevice.setIsActive(true);
						boxDevice.setIsPresent(true);
						boxDeviceRepository.save(boxDevice);
						// newReConfigurebox.setQuantity(newReConfigurebox.getQuantity() + 1);
						newReConfigurebox.setCurrentQuantity(newReConfigurebox.getCurrentQuantity() + 1);
						boxRepository.save(newReConfigurebox);
					}
					configureDevices.setIsReConfigure(request.getIsReConfigured());
					configureDevices.setUpdatedAt(new Date());
					configureDevices.setUpdatedBy(new User(request.getUserId()));
					ReConfigureDevices reConfigureDevices = reConfigureDevicesRepository.save(configureDevices);
					return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), reConfigureDevices);
				}
			} else {
				if (request.getIsReConfigured() == true) {
					if (newReConfigurebox.getCurrentQuantity().intValue() < newReConfigurebox.getQuantity()
							.intValue()) {

					} else {
						return new Response<>(HttpStatus.NOT_ACCEPTABLE.value(), "Box Is Completed!!!");
					}
					rcb.setTotalConfigureDevice(rcb.getTotalConfigureDevice() + 1);
					reConfigureBoxRepository.save(rcb);
					// add device in box and map with box-device and update transaction and update
					// boxcode in device remove box from old box
					oldMapping.setIsActive(false);
					oldMapping.setIsPresent(false);
					boxDeviceRepository.save(oldMapping);
					// update box quantity
					// Box oldBox = boxTransaction.getBox();
					oldReConfigurebox.setCurrentQuantity(oldReConfigurebox.getCurrentQuantity() - 1);
					oldReConfigurebox.setQuantity(oldReConfigurebox.getQuantity() - 1);
					boxRepository.save(oldReConfigurebox);
					// update device
					device.get().setBoxCode(newReConfigurebox.getBoxNo());
					device.get().setUpdatedAt(new Date());
					device.get().setModifiedBy(request.getUserId());
					device.get().setState(rcb.getStateId());
					deviceRepository.save(device.get());
					newBoxTransaction.setQuantity(newBoxTransaction.getQuantity() + 1);
					boxTransactionRepository.save(newBoxTransaction);
					BoxDevice boxDevice = new BoxDevice();
					boxDevice.setBox(newReConfigurebox);
					boxDevice.setDevice(device.get());
					boxDevice.setEntryTransactionId(newBoxTransaction);
					boxDevice.setIsActive(true);
					boxDevice.setIsPresent(true);
					boxDeviceRepository.save(boxDevice);
					// newReConfigurebox.setQuantity(newReConfigurebox.getQuantity() + 1);
					newReConfigurebox.setCurrentQuantity(newReConfigurebox.getCurrentQuantity() + 1);
					boxRepository.save(newReConfigurebox);
				}
				ReConfigureDevices reConfigureDevice = new ReConfigureDevices();
				reConfigureDevice.setCreatedAt(new Date());
				reConfigureDevice.setCreatedBy(new User(request.getUserId()));
				reConfigureDevice.setUpdatedAt(new Date());
				reConfigureDevice.setUpdatedBy(new User(request.getUserId()));
				reConfigureDevice.setImeiNo(request.getImeiNo());
				reConfigureDevice.setIsReConfigure(request.getIsReConfigured());
				reConfigureDevice.setReConfigBoxId(request.getReConfigureBoxId());
				ReConfigureDevices reConfigureDevices = reConfigureDevicesRepository.save(reConfigureDevice);
				return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(),
						reConfigureDevices);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}
}
