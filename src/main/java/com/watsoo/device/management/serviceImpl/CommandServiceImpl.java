package com.watsoo.device.management.serviceImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.AddDeviceResponse;
import com.watsoo.device.management.dto.CommandRequestDto;
import com.watsoo.device.management.dto.CommandResponseDTO;
import com.watsoo.device.management.dto.CommandResponseDTOV2;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GPSDevicesDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.CommandStatusEnum;
import com.watsoo.device.management.enums.StatusEnum;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Command;
import com.watsoo.device.management.model.CommandDetails;
import com.watsoo.device.management.model.CommandRequestMaster;
import com.watsoo.device.management.model.Configuration;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceLot;
import com.watsoo.device.management.model.DeviceLotMaster;
import com.watsoo.device.management.model.IccidMaster;
import com.watsoo.device.management.model.ModelConfig;
import com.watsoo.device.management.model.Provider;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.TestedDevice;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.model.UserDeviceCommand;
import com.watsoo.device.management.repository.CommandDetailsRepository;
import com.watsoo.device.management.repository.CommandRepository;
import com.watsoo.device.management.repository.CommandRequestMasterRepository;
import com.watsoo.device.management.repository.ConfigurationRepository;
import com.watsoo.device.management.repository.DeviceLotMasterRepository;
import com.watsoo.device.management.repository.DeviceLotRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.IccidMasterRepository;
import com.watsoo.device.management.repository.ModelConfigRepository;
import com.watsoo.device.management.repository.TestedDeviceRepository;
import com.watsoo.device.management.repository.UserDeviceCommandRepository;
import com.watsoo.device.management.service.CommandService;
import com.watsoo.device.management.util.HttpUtility;
import com.watsoo.device.management.util.Utility;

@Service
public class CommandServiceImpl implements CommandService {

	@Autowired
	private HttpUtility httpUtility;

	@Autowired
	private UserDeviceCommandRepository userDeviceCommandRepository;

	@Autowired
	private DeviceLotRepository lotRepository;

	@Autowired
	private CommandRepository commandRepository;

	@Autowired
	private DeviceLotMasterRepository lotMasterRepository;

	@Autowired
	private ModelConfigRepository modelConfigRepository;

	@Autowired
	private TestedDeviceRepository testedDeviceRepository;

	@Autowired
	private DeviceRepository deviceRepository;

//	@Autowired
//	private StateRepository stateRepository;

	@Autowired
	private IccidMasterRepository iccidMasterRepository;

	@Value("${ais.admin.add.device}")
	private String addDeviceUrl;

	// private UserDeviceCommandRepository commandRepository;

	@Autowired
	private CommandRequestMasterRepository commandRqstMasterRepo;

	@Autowired
	private CommandDetailsRepository commandDetailsRepository;

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Value("${traccar.base.url}")
	private String coreBaseUrl;

	@Value("${traccar.user.name}")
	private String coreUser;

	@Value("${traccar.user.password}")
	private String corePassword;

	@Override
	public Response<String> shootCoreCommandDevice(DeviceCommandDTO deviceCommandDTO) {
		Response<String> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		JsonObject requestBody = null;
		String coreResponse = "";
		requestBody = Utility.commandJsonObject(deviceCommandDTO.getDeviceId(), deviceCommandDTO.getCommand());
		coreResponse = httpUtility.httpPost(Constant.COMMAND_SEND_API, deviceCommandDTO.getUserName(),
				deviceCommandDTO.getPassword(), requestBody);
		UserDeviceCommand command = new UserDeviceCommand();
		command.setCommand(deviceCommandDTO.getCommand());
		command.setCreatedAt(new Date());
		command.setImeiNo(deviceCommandDTO.getImeiNo());
		command.setCreatedBy(deviceCommandDTO.getUserId());
		command.setResponse(coreResponse);
		command.setVehicleId(deviceCommandDTO.getVehicleId());
		userDeviceCommandRepository.save(command);
		response.setData(coreResponse);
		response.setMessage(HttpStatus.OK.getReasonPhrase());
		response.setResponseCode(HttpStatus.OK.value());

		return response;
	}

	@Override
	public Response<Object> getCommand(DeviceCommandDTO deviceCommandDTO) {
		// check lot isCompleted
		Optional<DeviceLot> deviceLotOpt = lotRepository.findById(deviceCommandDTO.getLotId());
		if (!deviceLotOpt.isPresent()) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "No DeviceLot Found");
		}
		DeviceLot lot = deviceLotOpt.get();
		if (lot.getIsCompleted()) {
			return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Lot Already Completed");
		}
		TestedDevice testedDevice = null;
		if (deviceCommandDTO.getTestDeviceId() != null && deviceCommandDTO.getTestDeviceId() > 0) {
			Optional<TestedDevice> testedDevice2 = testedDeviceRepository.findById(deviceCommandDTO.getTestDeviceId());
			if (testedDevice2.isPresent()) {
				testedDevice = testedDevice2.get();
			}
		}
		List<Command> commands = commandRepository.findAll();
		List<ModelConfig> modelConfigs = modelConfigRepository.findAll();
		List<Integer> modelConfigSeqNo = modelConfigs.stream().map(ModelConfig::getModelConfigSequenceNo).sorted()
				.collect(Collectors.toList());
		List<Integer> sequenceNos = commands.stream().map(Command::getSequenceNo).sorted().collect(Collectors.toList());

		CommandResponseDTO responseDTO = new CommandResponseDTO();
		responseDTO.setLotId(deviceCommandDTO.getLotId());

		if (deviceCommandDTO.getCommandResponse() != null && deviceCommandDTO.getCommandResponse()) {
			if ((deviceCommandDTO.getSequenceId() != null && deviceCommandDTO.getSequenceId() > 0)
					|| (deviceCommandDTO.getModelConfigSequenceNo() != null
							&& deviceCommandDTO.getModelConfigSequenceNo() > 0)) {

				if (deviceCommandDTO.getSequenceId() != null && deviceCommandDTO.getSequenceId() > 0) {
					if (deviceCommandDTO.getSequenceId() == sequenceNos.get(sequenceNos.size() - 1)) {
						// get data from model config
						// first element
						ModelConfig firstModelConfigCommand = modelConfigs.stream()
								.filter(o -> o.getModelConfigSequenceNo() == modelConfigSeqNo.get(0)).findFirst()
								.orElse(null);
						responseDTO.setModelConfig(firstModelConfigCommand);
						responseDTO.setTestedDeviceId(testedDevice.getId());
						return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
								responseDTO);
					} else {
						// TestedDevice deviceObj = null;
						if (deviceCommandDTO.getSequenceId().equals(sequenceNos.get(0))) {
							// create new tested device
							// check exist
							if (testedDevice == null) {
								testedDevice = testedDeviceRepository.findByImeiNo(deviceCommandDTO.getImeiNo());
								if (testedDevice == null) {
									TestedDevice device = new TestedDevice();
									device.setCreatedAt(new Date());
									device.setCreatedBy(deviceCommandDTO.getUserId());
									device.setIccidNo(deviceCommandDTO.getIccidNo());
									device.setUuidNo(deviceCommandDTO.getUuidNo());
									testedDevice = testedDeviceRepository.save(device);
								}
							}
						}
						int index = sequenceNos.indexOf(deviceCommandDTO.getSequenceId());
						// int nextSequenceNumber = sequenceNos.get(index + 1);
						Command nextCommand = commands.stream()
								.filter(command -> command.getSequenceNo() == sequenceNos.get(index + 1)).findFirst()
								.orElse(null);
						// save input data
						DeviceLotMaster lotMaster = new DeviceLotMaster();
						lotMaster.setLotId(deviceCommandDTO.getLotId());
						lotMaster.setCommand(deviceCommandDTO.getCommand());
						lotMaster.setResponse(deviceCommandDTO.getResponse());
						lotMaster.setCreatedAt(new Date());
						lotMaster.setCreatedBy(deviceCommandDTO.getUserId());
						lotMaster.setIsActive(true);
						lotMaster.setStatus(CommandStatusEnum.ACCEPT);
						lotMaster.setTestedDeviceId(testedDevice.getId());
						lotMasterRepository.save(lotMaster);
						responseDTO.setTestedDeviceId(testedDevice.getId());
						responseDTO.setMasterCommand(nextCommand);
						return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
								responseDTO);
					}
				} else {
					// get model config
					if (deviceCommandDTO.getModelConfigSequenceNo() == modelConfigSeqNo
							.get(modelConfigSeqNo.size() - 1)) {
						// complete test
						// to-do
						// update lot
						// testing done tested device
						testedDevice.setIsTestingCompleted(true);
						testedDeviceRepository.save(testedDevice);
						lot.setTestedQuantity(lot.getTestedQuantity() == null ? 1 : lot.getTestedQuantity() + 1);
						if (lot.getTestedQuantity() == lot.getOrderQuantity()) {
							lot.setIsCompleted(true);
						}
						lotRepository.save(lot);
						// return success
						return new Response<>(HttpStatus.OK.value(), "Device Testing completed successfully");
					} else {
						int index = modelConfigSeqNo.indexOf(deviceCommandDTO.getModelConfigSequenceNo());
						// int nextSequenceNumber = sequenceNos.get(index + 1);
						ModelConfig nextCommand = modelConfigs.stream()
								.filter(o -> o.getModelConfigSequenceNo() == modelConfigSeqNo.get(index + 1))
								.findFirst().orElse(null);
						// save input data
						DeviceLotMaster lotMaster = new DeviceLotMaster();
						lotMaster.setLotId(deviceCommandDTO.getLotId());
						lotMaster.setCommand(deviceCommandDTO.getCommand());
						lotMaster.setResponse(deviceCommandDTO.getResponse());
						lotMaster.setCreatedAt(new Date());
						lotMaster.setCreatedBy(deviceCommandDTO.getUserId());
						lotMaster.setIsActive(true);
						lotMaster.setStatus(CommandStatusEnum.ACCEPT);
						lotMaster.setTestedDeviceId(testedDevice.getId());
						lotMasterRepository.save(lotMaster);
						responseDTO.setTestedDeviceId(testedDevice.getId());
						responseDTO.setModelConfig(nextCommand);
						return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
								responseDTO);
					}
				}

			}
		} else if (deviceCommandDTO.getCommandResponse() != null && !deviceCommandDTO.getCommandResponse()) {
			// save input data
			DeviceLotMaster lotMaster = new DeviceLotMaster();
			lotMaster.setLotId(deviceCommandDTO.getLotId());
			lotMaster.setCommand(deviceCommandDTO.getCommand());
			lotMaster.setResponse(deviceCommandDTO.getResponse());
			lotMaster.setCreatedAt(new Date());
			lotMaster.setCreatedBy(deviceCommandDTO.getUserId());
			lotMaster.setIsActive(true);
			lotMaster.setStatus(CommandStatusEnum.REJECT);
			lotMaster.setTestedDeviceId(testedDevice.getId());
			lotMasterRepository.save(lotMaster);
			responseDTO.setTestedDeviceId(testedDevice.getId());
			return new Response<Object>(HttpStatus.EXPECTATION_FAILED.value(), "Testing Failed");

		} else {
			// first
			Command firstCommand = commands.stream().filter(command -> command.getSequenceNo() == sequenceNos.get(0))
					.findFirst().orElse(null);
			responseDTO.setMasterCommand(firstCommand);
			responseDTO.setTestedDeviceId(testedDevice.getId());
			return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDTO);
		}
		return null;
	}

	@Override
	public Response<Object> getAllCommand(DeviceCommandDTO deviceCommandDTO) {
		try {

			CommandResponseDTOV2 response = new CommandResponseDTOV2();
			List<ModelConfig> modelConfigs = new ArrayList<>();
			List<Command> commands = commandRepository.findAll();
			List<Command> startingCommands = commands.stream()
					.filter(o -> o.getId() != null && (o.getIsAfterModelConfig() == null || !o.getIsAfterModelConfig()))
					.collect(Collectors.toList());
			// List<Command> endingCommands = commands.stream()
			// .filter(o -> o.getId() != null && (o.getIsAfterModelConfig() != null &&
			// o.getIsAfterModelConfig()))
			// .collect(Collectors.toList());
			modelConfigs = modelConfigRepository.findByStateIdAndModelIdAndProviderIdAndOperatorIdAndClientId(
					deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId(), deviceCommandDTO.getProviderId(),
					deviceCommandDTO.getOperatorId(), deviceCommandDTO.getClientId());
			if (modelConfigs == null || modelConfigs.isEmpty()) {
				// get default command
				// modelConfigs =
				// modelConfigRepository.findByStateIdAndModelIdAndProviderIdAndOperatorId(
				// deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId(),
				// deviceCommandDTO.getProviderId(),
				// deviceCommandDTO.getOperatorId());
				modelConfigs = modelConfigRepository.findByState(deviceCommandDTO.getStateId());
			}
			if (modelConfigs != null) {
				modelConfigs = modelConfigs.stream()
						.sorted((o1, o2) -> o1.getModelConfigSequenceNo().compareTo(o2.getModelConfigSequenceNo()))
						.collect(Collectors.toList());
			}
			response.setCommands(startingCommands);
			response.setModelConfigs(modelConfigs);
			// response.setFinalCommands(endingCommands);
			return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), response);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	public CommandRequestDto shootCommandByList(CommandRequestDto commandDto) {
		try {
			// Configuration requestCodeConfig =
			// configurationRepository.findByKey("REQUEST_CODE");

			boolean hasNullImei = commandDto.getList().stream()
					.anyMatch(entry -> entry.getImei() == null || entry.getImei().isEmpty());
			boolean hasNullCommand = commandDto.getList().stream()
					.anyMatch(entry -> entry.getCommand() == null || entry.getCommand().isEmpty());

			if (hasNullCommand || hasNullImei) {

				return null;

			}

			// String requestCode = requestCodeConfig.getValue();

			CommandRequestMaster cmdRequestMstr = new CommandRequestMaster();
			cmdRequestMstr.setTotalCount(commandDto.getList().size());
			cmdRequestMstr.setCreatedAt(new Date());
			cmdRequestMstr.setCreatedBy(commandDto.getUserId());
			cmdRequestMstr.setRequestCode(updateRequestCode());
			commandRqstMasterRepo.save(cmdRequestMstr);

			List<CommandDetails> cmdList = commandDto.getList().stream().map(s -> {
				CommandDetails commandDetails = new CommandDetails();
				commandDetails.setCommandRequestMaster(cmdRequestMstr);
				commandDetails.setCommand(s.getCommand());
				commandDetails.setLastShootResponse(null);
				commandDetails.setImei(s.getImei());
				commandDetails.setLastShootTime(new Date());
				return commandDetails;
			}).collect(Collectors.toList());

			commandDetailsRepository.saveAll(cmdList);
			return commandDto;
		} catch (Exception e) {
			e.printStackTrace();
			return commandDto;
		}

	}

	@Override
	public Response<Object> saveCommandResponse(DeviceCommandDTO deviceCommandDTO) {
		try {
			TestedDevice testedDevice = null;
			if (deviceCommandDTO.getImeiNo() != null && deviceCommandDTO.getImeiNo() != ""
					&& deviceCommandDTO.getIccidNo() != null && deviceCommandDTO.getIccidNo() != ""
					&& deviceCommandDTO.getLotId() != null) {
				testedDevice = testedDeviceRepository.findByImeiNo(deviceCommandDTO.getImeiNo().trim());
				Optional<DeviceLot> deviceLotOpt = lotRepository.findById(deviceCommandDTO.getLotId());
				if (!deviceLotOpt.isPresent()) {
					return new Response<Object>(HttpStatus.NOT_FOUND.value(), "NO LOT FOUND WITH LOT_ID");
				}
				DeviceLot deviceLot = deviceLotOpt.get();
				Provider provider = deviceLot.getProvider();
				// match data from iccid master
				String actualIccid = deviceCommandDTO.getIccidNo().trim().substring(0,
						provider.getIccidLengthLimit());
				Optional<IccidMaster> iccidOptional = iccidMasterRepository.findByLikeIccidNo(actualIccid);
				if (!iccidOptional.isPresent()) {
					return new Response<Object>(HttpStatus.EXPECTATION_FAILED.value(),
							"ICCID NOT FOUND IN ICCID-MASTER");
				} else {
					IccidMaster iccidMaster = iccidOptional.get();
					boolean opFlag = deviceLot.getOperators().getName()
							.equalsIgnoreCase(iccidMaster.getSim1Operator());
					boolean proFlag = provider.getName().equalsIgnoreCase(iccidMaster.getProvider());
					if (proFlag && opFlag) {

					} else {
						return new Response<Object>(HttpStatus.EXPECTATION_FAILED.value(),
								"OPERATOR OR PROVIDER NOT MATCHED WITH THIS ICCID");
					}
				}
				if (testedDevice == null) {
				
					TestedDevice device = new TestedDevice();
					device.setIsActive(true);
					device.setCreatedAt(new Date());
					device.setCreatedBy(deviceCommandDTO.getUserId());
					device.setIccidNo(actualIccid);
					// update uuid
					device.setUuidNo(deviceCommandDTO.getUuidNo().trim());
					// device.setUuidNo(getUuidNo(deviceCommandDTO.getImeiNo().trim()));
					device.setDetail(deviceCommandDTO.getDetail().trim());
					device.setSoftwareVersion(deviceCommandDTO.getSoftwareVersion().trim());
					device.setLotId(deviceCommandDTO.getLotId());
					device.setImeiNo(deviceCommandDTO.getImeiNo().trim());
					testedDevice = testedDeviceRepository.save(device);
				}
				if (deviceCommandDTO.getCommand() != null && deviceCommandDTO.getCommand() != ""
						&& deviceCommandDTO.getCommand().trim().equalsIgnoreCase("GET SINFO1:")) {
					if (deviceCommandDTO.getDetail() == null || deviceCommandDTO.getDetail() == "") {
						return new Response<Object>(HttpStatus.BAD_REQUEST.value(), "Details can not be empty");
					}
					String pattern = "DFV:(.*?),";
					Pattern r = Pattern.compile(pattern);
					Matcher m = r.matcher(deviceCommandDTO.getDetail().trim());
					if (m.find()) {
						String firmwareVersion = m.group(1);
						System.out.println("Firmware Version: " + firmwareVersion);
						testedDevice.setSoftwareVersion(firmwareVersion);
						testedDevice.setDetail(deviceCommandDTO.getDetail().trim());
						testedDevice = testedDeviceRepository.save(testedDevice);
					} else {
						System.out.println("Firmware Version not found!");
					}
				}
				DeviceLotMaster lotMaster = new DeviceLotMaster();
				lotMaster.setLotId(deviceCommandDTO.getLotId());
				lotMaster.setCommand(deviceCommandDTO.getCommand().trim());
				lotMaster.setResponse(deviceCommandDTO.getResponse().trim());
				lotMaster.setCreatedAt(new Date());
				lotMaster.setCreatedBy(deviceCommandDTO.getUserId());
				lotMaster.setIsActive(true);
				lotMaster.setStatus(deviceCommandDTO.getStatus());
				lotMaster.setTestedDeviceId(testedDevice.getId());
				if (deviceCommandDTO.getStatus() != null
						& deviceCommandDTO.getStatus().equals(CommandStatusEnum.REJECT)) {
					lotMaster.setFailureReason(deviceCommandDTO.getFailureReason());
					testedDevice.setIsRejected(true);
					testedDevice.setRejectedBy(deviceCommandDTO.getUserId());
					testedDevice.setIsActive(false);
					testedDevice.setRemark(deviceCommandDTO.getFailureReason());
					testedDeviceRepository.save(testedDevice);
				}
				lotMasterRepository.save(lotMaster);
				if (deviceCommandDTO.getStatus() != null
						& deviceCommandDTO.getStatus().equals(CommandStatusEnum.REJECT)) {
					return new Response<Object>(HttpStatus.EXPECTATION_FAILED.value(),
							HttpStatus.EXPECTATION_FAILED.getReasonPhrase() + " Due To "
									+ deviceCommandDTO.getFailureReason());
				}
				return new Response<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), deviceCommandDTO);
			} else if (deviceCommandDTO.getImeiNo() != null || deviceCommandDTO.getImeiNo() != "") {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						HttpStatus.EXPECTATION_FAILED.getReasonPhrase() + " Due To IMEI");
			} else if (deviceCommandDTO.getIccidNo() != null || deviceCommandDTO.getIccidNo() != "") {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),
						HttpStatus.EXPECTATION_FAILED.getReasonPhrase() + " Due To CCID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
		return null;
	}

	@Override
	public Response<Object> saveTestedDevice(DeviceCommandDTO deviceCommandDTO) {
		try {
			TestedDevice testedDevice = testedDeviceRepository.findByImeiNo(deviceCommandDTO.getImeiNo());
			if (testedDevice == null) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Device not found");
			} else if (testedDevice.getIsTestingCompleted() != null && testedDevice.getIsTestingCompleted()) {
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "DEVICE ALREADY EXIST");
			}
			Optional<DeviceLot> deviceLotOpt = lotRepository.findById(deviceCommandDTO.getLotId());
			if (!deviceLotOpt.isPresent()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "No DeviceLot Found");
			}
			DeviceLot lot = deviceLotOpt.get();
			if (lot.getIsCompleted() != null && lot.getIsCompleted()) {
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Lot Already Completed");
			}
			// update lot
			lot.setTestedQuantity(lot.getTestedQuantity() == null ? 1 : lot.getTestedQuantity() + 1);
			if (lot.getTestedQuantity() == lot.getOrderQuantity()) {
				lot.setIsCompleted(true);
			}
			lot.setPendingQuantity(lot.getOrderQuantity() - lot.getTestedQuantity());

			// create device
			Device device = new Device();
			device.setUuidNo(testedDevice.getUuidNo());
			device.setTestedDeviceId(testedDevice.getId());
			device.setImeiNo(testedDevice.getImeiNo());
			device.setIccidNo(testedDevice.getIccidNo());
			device.setDetail(testedDevice.getDetail());
			device.setSoftwareVersion(testedDevice.getSoftwareVersion());
			device.setUserId(testedDevice.getCreatedBy());
			device.setLotId(testedDevice.getLotId());
			device.setMfgLotId(lot.getMfgLotId());
			device.setState(lot.getState());
			Device deviceObj = null;
			try {
				deviceObj = saveDevice(device);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (deviceObj != null) {
				// update tested device
				testedDevice.setIsTestingCompleted(true);
				testedDevice.setUpdatedAt(new Date());
				testedDevice.setUpdatedBy(deviceCommandDTO.getUserId());
				testedDevice.setDeviceId(deviceObj.getId());
				lotRepository.save(lot);
				testedDeviceRepository.save(testedDevice);
				return new Response<Object>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(),
						deviceObj);
			} else {
				return new Response<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						"something went wrong !! Device unable to save", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		}
	}

	private Device saveDevice(Device deviceDTO) {
		try {
			//State state = stateRepository.findbyReferenceKey(Constant.OTHER_STATE);
			State state = deviceDTO.getState();
			Optional<Device> imeiExist = deviceRepository.findByImeiNo(deviceDTO.getImeiNo());
			Optional<Device> iccidExist = deviceRepository.findByLikeIccidNo(deviceDTO.getIccidNo());
			Optional<IccidMaster> iccidMasterExist = iccidMasterRepository.findByLikeIccidNo(deviceDTO.getIccidNo());
			if (!iccidMasterExist.isPresent()) {
				throw new RuntimeException("Iccid not found in master");
			}
			if (imeiExist.isPresent() && !iccidExist.isPresent()) {
				if (deviceDTO.getIccidNo() != null && (imeiExist.get().getIccidNo() != null
						&& !(deviceDTO.getIccidNo().equalsIgnoreCase(imeiExist.get().getIccidNo())))) {
					imeiExist.get().setOldIccid(imeiExist.get().getIccidNo());
					imeiExist.get().setIccidUpdatedAt(new Date());
					imeiExist.get().setIccidNo(deviceDTO.getIccidNo());
				}
				if (deviceDTO.getSoftwareVersion() != null) {
					imeiExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
				}
				if (deviceDTO.getDetail() != null) {
					imeiExist.get().setDetail(deviceDTO.getDetail());
				}

				String requestBody = new Gson().toJson(deviceDTO);
				imeiExist.get().setUpdatedAt(new Date());
				imeiExist.get().setModifiedBy(deviceDTO.getUserId());
				imeiExist.get().setRequestBody(requestBody);
				imeiExist.get().setStatus(StatusMaster.TESTED);
				if (state != null) {
					imeiExist.get().setState(state);
				}
				imeiExist.get().setTestedDeviceId(deviceDTO.getTestedDeviceId());
				imeiExist.get().setLotId(deviceDTO.getLotId());
				imeiExist.get().setMfgLotId(deviceDTO.getMfgLotId());
				Device deviceObj = deviceRepository.save(imeiExist.get());
				return deviceObj;
			} else if (iccidExist.isPresent() && !imeiExist.isPresent()) {
				if (deviceDTO.getImeiNo() != null && (iccidExist.get().getImeiNo() != null
						&& !(deviceDTO.getImeiNo().equalsIgnoreCase(iccidExist.get().getImeiNo())))) {
					iccidExist.get().setOldImei(iccidExist.get().getImeiNo());
					iccidExist.get().setImeiUpdatedAt(new Date());
					iccidExist.get().setImeiNo(deviceDTO.getImeiNo());
				}
				if (deviceDTO.getSoftwareVersion() != null) {
					iccidExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
				}
				if (deviceDTO.getDetail() != null) {
					iccidExist.get().setDetail(deviceDTO.getDetail());
				}

				String requestBody = new Gson().toJson(deviceDTO);
				iccidExist.get().setUpdatedAt(new Date());
				iccidExist.get().setModifiedBy(deviceDTO.getUserId());
				iccidExist.get().setRequestBody(requestBody);
				iccidExist.get().setStatus(StatusMaster.TESTED);
				if (state != null) {
					iccidExist.get().setState(state);
				}
				iccidExist.get().setTestedDeviceId(deviceDTO.getTestedDeviceId());
				iccidExist.get().setLotId(deviceDTO.getLotId());
				iccidExist.get().setMfgLotId(deviceDTO.getMfgLotId());
				Device deviceObj = deviceRepository.save(iccidExist.get());
				return deviceObj;
			} else if (imeiExist.isPresent() && iccidExist.isPresent()) {
				if (imeiExist.get().getId() != null && iccidExist.get().getId() != null
						&& imeiExist.get().getId().intValue() == iccidExist.get().getId().intValue()) {
					if (deviceDTO.getSoftwareVersion() != null) {
						imeiExist.get().setSoftwareVersion(deviceDTO.getSoftwareVersion());
					}
					if (deviceDTO.getDetail() != null) {
						imeiExist.get().setDetail(deviceDTO.getDetail());
					}
					String requestBody = new Gson().toJson(deviceDTO);
					imeiExist.get().setUpdatedAt(new Date());
					imeiExist.get().setModifiedBy(deviceDTO.getUserId());
					imeiExist.get().setRequestBody(requestBody);
					imeiExist.get().setStatus(StatusMaster.TESTED);
					if (state != null) {
						imeiExist.get().setState(state);
					}
					imeiExist.get().setTestedDeviceId(deviceDTO.getTestedDeviceId());
					imeiExist.get().setLotId(deviceDTO.getLotId());
					imeiExist.get().setMfgLotId(deviceDTO.getMfgLotId());
					Device deviceObj = deviceRepository.save(imeiExist.get());
					return deviceObj;
				} else {
					return null;
				}
			} else {
				Device device = new Device();
				if (deviceDTO.getImeiNo() != null) {
					device.setImeiNo(deviceDTO.getImeiNo());
				} else {
					throw new RuntimeException("Imei no can't be null");
				}
				if (deviceDTO.getIccidNo() != null) {
					device.setIccidNo(deviceDTO.getIccidNo());
					IccidMaster iccidMaster = iccidMasterExist.get();
					device.setSim1Provider(iccidMaster.getProvider());
					device.setSim2Provider(iccidMaster.getProvider());
					device.setSim1ActivationDate(iccidMaster.getSimActivationDate());
					device.setSim2ActivationDate(iccidMaster.getSimActivationDate());
					device.setSim1ExpiryDate(iccidMaster.getSimExpiryDate());
					device.setSim2ExpiryDate(iccidMaster.getSimExpiryDate());
					device.setSim1Number(iccidMaster.getSim1());
					device.setSim2Number(iccidMaster.getSim2());
					device.setSim1Operator(iccidMaster.getSim1Operator());
					device.setSim2Operator(iccidMaster.getSim2Operator());
				} else {
					throw new RuntimeException("Iccid no can't be null");
				}
				if (deviceDTO.getSoftwareVersion() != null) {
					device.setSoftwareVersion(deviceDTO.getSoftwareVersion());
				}
				if (deviceDTO.getDetail() != null) {
					device.setDetail(deviceDTO.getDetail());
				}

				device.setUuidNo(deviceDTO.getUuidNo());
				String requestBody = new Gson().toJson(deviceDTO);
				device.setCreatedAt(new Date());
				device.setUpdatedAt(new Date());
				device.setCreatedBy(new User(deviceDTO.getUserId()));
				device.setModifiedBy(deviceDTO.getUserId());
				device.setRequestBody(requestBody);
				device.setStatus(StatusMaster.TESTED);
				device.setState(state);
				device.setTestedDeviceId(deviceDTO.getTestedDeviceId());
				device.setLotId(deviceDTO.getLotId());
				device.setMfgLotId(deviceDTO.getMfgLotId());
				Device deviceObj = deviceRepository.save(device);
				// add in ais-admin
				// new Thread(() -> {
				// if (addDeviceInAisAdmin(deviceObj)) {
				// System.out.println("device added in ais admin successfully");
				// }
				// }).start();

				return deviceObj;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// private String getUuidNo(String imeiNumber) {
	// SimpleDateFormat dayFormat = new SimpleDateFormat("YYYY");
	// String year = dayFormat.format(new Date());
	//
	// SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	// String month = monthFormat.format(new Date());
	//
	// Integer size = imeiNumber.length() - 8;
	// String uuid = year.substring(2, 4) + month + imeiNumber.substring(size,
	// (imeiNumber.length()));
	// return uuid;
	// }

	public Boolean addDeviceInAisAdmin(Device device) {
		Boolean res = false;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("imeiNo", device.getImeiNo());
		map.add("createdAt", new Date());
		map.add("updatedAt", new Date());
		map.add("companyId", 1l);
		map.add("isActive", true);
		map.add("chassisNumber", device.getImeiNo() + "CH");
		map.add("engineNumber", device.getImeiNo() + "EN");
		map.add("number", device.getImeiNo());
		map.add("userId", 59l);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		ResponseEntity<String> response = restTemplate.exchange(addDeviceUrl, HttpMethod.POST, entity, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = response.getBody();
		try {
			AddDeviceResponse resp = objectMapper.readValue(jsonResponse, AddDeviceResponse.class);
			if (resp != null && resp.getResponseCode() != null && resp.getResponseCode().equals(201)) {
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	public String getSoftwareVersion(String packet) {
		List<String> subStrings = Arrays.asList(packet.split(","));
		return subStrings.get(55);
	}

	private static String updateRequestCode() {
		String numericPart = "REQ" + new Random().nextInt(9000) + 1000;
		return numericPart;

	}

	public Response<String> shootCoreCommandDeviceV2(DeviceCommandDTO deviceCommandDTO) {
		Response<String> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		JsonObject requestBody = Utility.commandJsonObject(deviceCommandDTO.getDeviceId(),
				deviceCommandDTO.getCommand());
		String coreResponse = httpUtility.httpPost(
				(deviceCommandDTO.getUrl() != null ? deviceCommandDTO.getUrl() != null : "")
						+ Constant.COMMAND_SEND_API,
				deviceCommandDTO.getUserName(), deviceCommandDTO.getPassword(), requestBody);
		response.setData(coreResponse.toString());
		response.setMessage(HttpStatus.OK.getReasonPhrase());
		response.setResponseCode(HttpStatus.OK.value());
		return response;
	}

	@Override
	public void shootCommandsSchedular() {
		try {
			Configuration batchSize = configurationRepository.findByKey("BATCH_SIZE");
			Configuration requestId = configurationRepository.findByKey("CURRENT_REQ_ID");
			Configuration proceessId = configurationRepository.findByKey("CURRENT_DEV_ID");
			String coreUrl = coreBaseUrl + Constant.DEVICE_API;
			String deviceResponse = httpUtility.httpGet(coreUrl, coreUser, corePassword);
			if (deviceResponse != null) {
				Type listOfMyClassObject = new TypeToken<ArrayList<GPSDevicesDTO>>() {
				}.getType();
				List<GPSDevicesDTO> jsonArrayObj = new Gson().fromJson(deviceResponse, listOfMyClassObject);
				Map<String, GPSDevicesDTO> imeiDeviceMap = jsonArrayObj.stream().filter(o -> o.getId() != null)
						.collect(Collectors.toMap(GPSDevicesDTO::getUniqueId, Function.identity()));
				if (requestId.getValue() == null && proceessId.getValue() == null) {
					List<CommandRequestMaster> commands = commandRqstMasterRepo.findPendingRequest();
					if (!commands.isEmpty()) {
						CommandRequestMaster commandRequestMaster = commands.get(0);
						Long reqId = commandRequestMaster.getId();
						List<CommandDetails> commandList = commandDetailsRepository.findByRequestId(reqId);
						if (!commandList.isEmpty()) {
							Long lastProcessId = null;
							Integer maxSize = Integer.parseInt(batchSize.getValue());

							if (maxSize > commandList.size()) {
								maxSize = commandList.size();
							}
							for (int i = 0; i < maxSize; i++) {
								lastProcessId = commandList.get(i).getId();
								proceessId.setValue(lastProcessId.toString());
								requestId.setValue(reqId.toString());
								// SHOOT_COMMAND
								GPSDevicesDTO gpsDevicesDTO = imeiDeviceMap.get(commandList.get(i).getImei());
								if (gpsDevicesDTO != null) {
									DeviceCommandDTO requestDTO = new DeviceCommandDTO();
									requestDTO.setDeviceId(gpsDevicesDTO.getId());
									requestDTO.setCommand(commandList.get(i).getCommand());
									Response<String> coreResponse = shootCoreCommandDeviceV2(requestDTO);
									commandList.get(i).setLastShootResponse(coreResponse.getData());
									commandList.get(i).setLastShootTime(new Date());
								}
								// commandList.get(i).setLastShootResponse("DONE");
								// commandList.get(i).setLastShootTime(new Date());
								commandDetailsRepository.save(commandList.get(i));
								configurationRepository.save(proceessId);
								configurationRepository.save(requestId);
								System.out.println("proeceesId: " + lastProcessId);
								System.out.println(new Date());
							}
							if (commandList.get(commandList.size() - 1).getId().intValue() == lastProcessId
									.intValue()) {
								proceessId.setValue(null);
								requestId.setValue(null);
								configurationRepository.save(proceessId);
								configurationRepository.save(requestId);
								commandRequestMaster.setStatus(StatusEnum.COMPLETED);
								commandRqstMasterRepo.save(commandRequestMaster);
								System.out.println("request " + reqId + "completed");
							}

						}
					}
				} else {
					Long reqId = Long.parseLong(requestId.getValue());
					Long procesId = Long.parseLong(proceessId.getValue()) + 1;
					List<CommandDetails> commandList = commandDetailsRepository.findByRequestIdAndProcessId(reqId,
							procesId);
					if (!commandList.isEmpty()) {
						Long lastProcessId = procesId;
						Integer maxSize = Integer.parseInt(batchSize.getValue());

						if (maxSize > commandList.size()) {
							maxSize = commandList.size();
						}

						for (int i = 0; i < maxSize; i++) {
							lastProcessId = commandList.get(i).getId();
							proceessId.setValue(lastProcessId.toString());
							requestId.setValue(reqId.toString());
							// SHOOT_COMMAND
							GPSDevicesDTO gpsDevicesDTO = imeiDeviceMap.get(commandList.get(i).getImei());
							if (gpsDevicesDTO != null) {
								DeviceCommandDTO requestDTO = new DeviceCommandDTO();
								requestDTO.setDeviceId(gpsDevicesDTO.getId());
								requestDTO.setCommand(commandList.get(i).getCommand());
								Response<String> coreResponse = shootCoreCommandDeviceV2(requestDTO);
								commandList.get(i).setLastShootResponse(coreResponse.getData());
								commandList.get(i).setLastShootTime(new Date());
							}
							commandDetailsRepository.save(commandList.get(i));
							configurationRepository.save(proceessId);
							configurationRepository.save(requestId);
							System.out.println("proeceesId: " + lastProcessId);
							System.out.println(new Date());
						}

						Optional<CommandRequestMaster> commandRequestMaster = commandRqstMasterRepo.findById(reqId);
						if (commandList.get(commandList.size() - 1).getId().intValue() == lastProcessId.intValue()) {
							proceessId.setValue(null);
							requestId.setValue(null);
							configurationRepository.save(proceessId);
							configurationRepository.save(requestId);
							commandRequestMaster.get().setStatus(StatusEnum.COMPLETED);
							commandRqstMasterRepo.save(commandRequestMaster.get());
							System.out.println("request " + reqId + "completed");
						}

					}
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

}
