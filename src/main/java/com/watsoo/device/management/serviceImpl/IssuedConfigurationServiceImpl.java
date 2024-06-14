package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.DeviceSimDetailsDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateCmdMstrDTO;
import com.watsoo.device.management.dto.StateCmdMstrResponseDTO;
import com.watsoo.device.management.dto.TcPackets;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.Configuration;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceConfigMaster;
import com.watsoo.device.management.model.StateCmdMstrEntity;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.ConfigurationRepository;
import com.watsoo.device.management.repository.DeviceConfigMasterRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.StateCmdMstrRepository;
import com.watsoo.device.management.service.IssuedConfigurationService;
import com.watsoo.device.management.util.DateUtil;

@Service
public class IssuedConfigurationServiceImpl implements IssuedConfigurationService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private ConfigurationRepository configurationRepo;

	@Autowired
	private StateServiceimpl stateServiceImpl;

	@Autowired
	private StateCmdMstrRepository stateCmdMstrRepository;

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private BoxDeviceRepository boxDeviceRepository;

	@Autowired
	private DeviceConfigMasterRepository deviceConfigMasterRepository;

	@Override
	public Response<?> getNotConfigIssuedDevice() {
		List<TcPackets> tcPacketsResponseDtoList = new ArrayList<>();
		String date = DateUtil.localDateTimeToStringInFormatYYYYMMDDHHMMSS(new Date());
		List<Device> deviceList = deviceRepository.findNotConfigDevices(date);
//		deviceList = deviceList.stream().filter(o -> o.getImeiNo().equalsIgnoreCase("866567066751059")).collect(Collectors.toList());
		List<StateCmdMstrEntity> stateCmdMstrListMap = stateServiceImpl.getAllStateCmd();
		if (deviceList != null && !deviceList.isEmpty()) {
			for (Device device : deviceList) {
				TcPackets tcPackets = new TcPackets();
				tcPackets.setImeiNumber(device.getImeiNo());
				if (device.getState() != null) {
					StateCmdMstrEntity stateCmd = stateCmdMstrListMap.stream()
							.filter(o -> o.getState().getId().intValue() == device.getState().getId().intValue()
									&& o.getClient().getId().intValue() == device.getClientId().intValue())
							.findAny().orElse(null);
					tcPackets.setStateId(device.getState().getId().intValue());
					if (stateCmd != null) {
						tcPackets.setStateCommand(stateCmd.getCommand());
					}
				}
				tcPacketsResponseDtoList.add(tcPackets);
			}
		}

		return new Response<>(HttpStatus.OK.value(), "Retrieved successfully", tcPacketsResponseDtoList);
	}

	@Override
	public Response<?> updateIssuedDevicePacketDetails(List<TcPackets> tcPacketList) {

		Boolean isUpdated = false;
		Integer minDeviceActivationTime = Constant.DEFAULT_MIN_DEVICE_ACTVATION_TIME;
		Configuration configExist = configurationRepo.findByKey(Constant.MIN_DEVICE_ACTVATION_TIME);
		if (configExist != null) {
			try {
				minDeviceActivationTime = Integer.valueOf(configExist.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<String> imeiNoList = tcPacketList.stream()
				.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
				.map(TcPackets::getImeiNumber).collect(Collectors.toList());
		List<Device> deviceList = deviceRepository.findAllByImeiNoIn(imeiNoList);
		if (deviceList != null && !deviceList.isEmpty()) {
			Map<String, TcPackets> tcPacketsMap = tcPacketList.stream()
					.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
					.collect(Collectors.toMap(TcPackets::getImeiNumber, Function.identity()));
			if (tcPacketsMap != null && !tcPacketsMap.isEmpty()) {
				List<Device> updatedDeviceList = new ArrayList<>();
				for (Device device : deviceList) {
					if (tcPacketsMap.containsKey(device.getImeiNo())) {
						TcPackets tcPackets = tcPacketsMap.get(device.getImeiNo());

						if (tcPackets.getPacket() != null && !tcPackets.getPacket().isEmpty()) {
							device.setPacket(tcPackets.getPacket());
							// update software version
							String softwareVersion = getSoftwareVersion(tcPackets.getPacket());
							device.setSoftwareVersion(softwareVersion);
							device.setPackedDate(new Date());
						}
						if (device.getDeviceActivatedDate() == null && device.getConfigDoneDate() != null
								&& new Date().after(DateUtil.addMinutesToJavaUtilDate(device.getConfigDoneDate(),
										minDeviceActivationTime))) {
							device.setDeviceActivatedDate(new Date());
							device.setIsConfigActivated(tcPackets.getIsDeviceActivated());
						}
						updatedDeviceList.add(device);
					}
				}
				if (updatedDeviceList != null && !updatedDeviceList.isEmpty()) {
					deviceRepository.saveAll(updatedDeviceList);
					isUpdated = true;
				}
			}
		}
		return new Response<>(HttpStatus.OK.value(), "Updated Successfully", isUpdated);
	}

	public String getSoftwareVersion(String packet) {
		List<String> subStrings = Arrays.asList(packet.split(","));
		return subStrings.get(55);
	}

	@Override
	public Response<?> updateIssuedDeviceConfiguration(List<TcPackets> tcPacketList) {

		Boolean isUpdated = false;
		List<String> imeiNoList = tcPacketList.stream()
				.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
				.map(TcPackets::getImeiNumber).collect(Collectors.toList());
		List<Device> deviceList = deviceRepository.findAllByImeiNoIn(imeiNoList);
		if (deviceList != null && !deviceList.isEmpty()) {
			Map<String, TcPackets> tcPacketsMap = tcPacketList.stream()
					.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
					.collect(Collectors.toMap(TcPackets::getImeiNumber, Function.identity()));
			if (tcPacketsMap != null && !tcPacketsMap.isEmpty()) {
				List<Device> updatedDeviceList = new ArrayList<>();
				for (Device device : deviceList) {
					if (tcPacketsMap.containsKey(device.getImeiNo())) {
						TcPackets tcPackets = tcPacketsMap.get(device.getImeiNo());
						if (tcPackets.getConfigDone() != null && tcPackets.getConfigDone()) {
							device.setIsConfigurationComplete(tcPackets.getConfigDone());
							device.setConfigDoneDate(new Date());
						}
						if (tcPackets.getCommmandSend() != null && tcPackets.getCommmandSend()) {
							device.setIsCommandSend(true);
							device.setCommandSendDate(new Date());
							device.setLastCommand(tcPackets.getLastCommand());
						}
						updatedDeviceList.add(device);
					}
				}
				if (updatedDeviceList != null && !updatedDeviceList.isEmpty()) {
					deviceRepository.saveAll(updatedDeviceList);
					isUpdated = true;
				}
			}
		}
		return new Response<>(HttpStatus.OK.value(), "Updated Successfully", isUpdated);
	}

	@Override
	public Response<?> updateDeviceSimDetails(List<DeviceSimDetailsDTO> deviceSimDetailsDTOList) {

		List<DeviceSimDetailsDTO> failedDeviceSimDetailsDTOList = new ArrayList<>();
		List<String> imeiNoList = deviceSimDetailsDTOList.stream()
				.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
				.map(DeviceSimDetailsDTO::getImeiNumber).collect(Collectors.toList());
		List<Device> deviceList = deviceRepository.findAllByImeiNoIn(imeiNoList);
		if (deviceList != null && !deviceList.isEmpty()) {
			Map<String, DeviceSimDetailsDTO> deviceSimDetailsMap = deviceSimDetailsDTOList.stream()
					.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
					.collect(Collectors.toMap(DeviceSimDetailsDTO::getImeiNumber, Function.identity()));
			Map<String, Device> deviceMap = deviceList.stream()
					.filter(x -> x != null && x.getImeiNo() != null && !x.getImeiNo().isEmpty())
					.collect(Collectors.toMap(Device::getImeiNo, Function.identity()));

			if (deviceSimDetailsMap != null && !deviceSimDetailsMap.isEmpty() && deviceMap != null
					&& !deviceMap.isEmpty()) {
				List<Device> updatedDeviceList = new ArrayList<>();
				for (DeviceSimDetailsDTO deviceSimDetailsDTO : deviceSimDetailsDTOList) {
					if (deviceMap.containsKey(deviceSimDetailsDTO.getImeiNumber())) {
						Device device = deviceMap.get(deviceSimDetailsDTO.getImeiNumber());
						Device existDevice = new Device();
						existDevice.setSim1Number(device.getSim1Number());
						existDevice.setSim1Operator(device.getSim1Operator());
						existDevice.setSim1ActivationDate(device.getSim1ActivationDate());
						existDevice.setSim1ExpiryDate(device.getSim1ExpiryDate());
						existDevice.setSim2Number(device.getSim2Number());
						existDevice.setSim2Operator(device.getSim2Operator());
						existDevice.setSim2ActivationDate(device.getSim2ActivationDate());
						existDevice.setSim2ExpiryDate(device.getSim2ExpiryDate());

						// DeviceSimDetailsDTO deviceSimDetailsDTO =
						// deviceSimDetailsMap.get(device.getImeiNo());
						int sim1ValidPoint = 0, sim2ValidPoint = 0;
						Boolean isvalid = null;

						if (deviceSimDetailsDTO.getSim1Number() != null
								&& !deviceSimDetailsDTO.getSim1Number().isEmpty()) {
							device.setSim1Number(deviceSimDetailsDTO.getSim1Number());
							sim1ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim1Operator() != null
								&& !deviceSimDetailsDTO.getSim1Operator().isEmpty()) {
							device.setSim1Operator(deviceSimDetailsDTO.getSim1Operator());
							sim1ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim1ActivationDate() != null) {
							device.setSim1ActivationDate(deviceSimDetailsDTO.getSim1ActivationDate());
							sim1ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim1ExpiryDate() != null) {
							device.setSim1ExpiryDate(deviceSimDetailsDTO.getSim1ExpiryDate());
							sim1ValidPoint++;
						}

						if (deviceSimDetailsDTO.getSim2Number() != null
								&& !deviceSimDetailsDTO.getSim2Number().isEmpty()) {
							device.setSim2Number(deviceSimDetailsDTO.getSim2Number());
							sim2ValidPoint++;
						}

						if (deviceSimDetailsDTO.getSim2Operator() != null
								&& !deviceSimDetailsDTO.getSim2Operator().isEmpty()) {
							device.setSim2Operator(deviceSimDetailsDTO.getSim2Operator());
							sim2ValidPoint++;
						}

						if (deviceSimDetailsDTO.getSim2ActivationDate() != null) {
							device.setSim2ActivationDate(deviceSimDetailsDTO.getSim2ActivationDate());
							sim2ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim2ExpiryDate() != null) {
							device.setSim2ExpiryDate(deviceSimDetailsDTO.getSim2ExpiryDate());
							sim2ValidPoint++;
						}
						if ((sim1ValidPoint == 0 && sim2ValidPoint == 4)) {
							if ((existDevice.getSim2Number() == null || existDevice.getSim2Number().isEmpty())) {
								isvalid = true;
							} else {
								isvalid = false;
								deviceSimDetailsDTO.setResponse("Sim2 Details already exist");
							}
						} else if (sim2ValidPoint == 0 && sim1ValidPoint == 4) {
							if ((existDevice.getSim1Number() == null || existDevice.getSim1Number().isEmpty())) {
								isvalid = true;
							} else {
								isvalid = false;
								deviceSimDetailsDTO.setResponse("Sim1 Details already exist");
							}
						} else if (sim1ValidPoint == 4 && sim2ValidPoint == 4) {
							if ((existDevice.getSim1Number() == null || existDevice.getSim1Number().isEmpty())
									&& (existDevice.getSim2Number() == null || existDevice.getSim2Number().isEmpty())) {
								isvalid = true;
							} else {
								isvalid = false;
								deviceSimDetailsDTO.setResponse("Sim1 and Sim2 Details already exist");
							}
						} else if ((sim1ValidPoint > 0 && sim1ValidPoint < 4)
								&& (sim2ValidPoint > 0 && sim2ValidPoint < 4)) {
							deviceSimDetailsDTO.setResponse("Please enter all details of Sim1 and Sim2  ");
						} else if (sim1ValidPoint > 0 && sim1ValidPoint < 4) {
							deviceSimDetailsDTO.setResponse("Please all details of Sim1 ");
						} else if (sim2ValidPoint > 0 && sim2ValidPoint < 4) {
							deviceSimDetailsDTO.setResponse("Please all details of Sim2 ");
						}

						if (isvalid != null && isvalid) {
							updatedDeviceList.add(device);
							deviceSimDetailsDTO.setIsUpdated(true);
							failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
						} else {
							deviceSimDetailsDTO.setIsUpdated(false);
							failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
						}

					} else {
						deviceSimDetailsDTO.setResponse("Invalid Imei Number");
						deviceSimDetailsDTO.setIsUpdated(false);
						failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
					}
				}

				if (updatedDeviceList != null && !updatedDeviceList.isEmpty()) {
					deviceRepository.saveAll(updatedDeviceList);
				}
			}
		} else {
			for (DeviceSimDetailsDTO deviceSimDetailsDTO : deviceSimDetailsDTOList) {
				deviceSimDetailsDTO.setResponse("Invalid Imei Number");
				deviceSimDetailsDTO.setIsUpdated(false);
				failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
			}
		}
		return new Response<>(HttpStatus.OK.value(), "Updated Successfully", failedDeviceSimDetailsDTOList);
	}

	@Override
	public Response<?> overrideDeviceSimDetails(List<DeviceSimDetailsDTO> deviceSimDetailsDTOList) {

		List<DeviceSimDetailsDTO> failedDeviceSimDetailsDTOList = new ArrayList<>();
		List<String> imeiNoList = deviceSimDetailsDTOList.stream()
				.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
				.map(DeviceSimDetailsDTO::getImeiNumber).collect(Collectors.toList());
		List<Device> deviceList = deviceRepository.findAllByImeiNoIn(imeiNoList);
		if (deviceList != null && !deviceList.isEmpty()) {
			Map<String, DeviceSimDetailsDTO> deviceSimDetailsMap = deviceSimDetailsDTOList.stream()
					.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
					.collect(Collectors.toMap(DeviceSimDetailsDTO::getImeiNumber, Function.identity()));
			Map<String, Device> deviceMap = deviceList.stream()
					.filter(x -> x != null && x.getImeiNo() != null && !x.getImeiNo().isEmpty())
					.collect(Collectors.toMap(Device::getImeiNo, Function.identity()));
			if (deviceSimDetailsMap != null && !deviceSimDetailsMap.isEmpty() && deviceMap != null
					&& !deviceMap.isEmpty()) {
				List<Device> updatedDeviceList = new ArrayList<>();
				for (DeviceSimDetailsDTO deviceSimDetailsDTO : deviceSimDetailsDTOList) {
					if (deviceMap.containsKey(deviceSimDetailsDTO.getImeiNumber())) {
						Device device = deviceMap.get(deviceSimDetailsDTO.getImeiNumber());
						int sim1ValidPoint = 0, sim2ValidPoint = 0;
						Boolean isvalid = null;

						if (deviceSimDetailsDTO.getSim1Number() != null
								&& !deviceSimDetailsDTO.getSim1Number().isEmpty()) {
							device.setSim1Number(deviceSimDetailsDTO.getSim1Number());
							sim1ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim1Operator() != null
								&& !deviceSimDetailsDTO.getSim1Operator().isEmpty()) {
							device.setSim1Operator(deviceSimDetailsDTO.getSim1Operator());
							sim1ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim1ActivationDate() != null) {
							device.setSim1ActivationDate(deviceSimDetailsDTO.getSim1ActivationDate());
							sim1ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim1ExpiryDate() != null) {
							device.setSim1ExpiryDate(deviceSimDetailsDTO.getSim1ExpiryDate());
							sim1ValidPoint++;
						}

						if (deviceSimDetailsDTO.getSim2Number() != null
								&& !deviceSimDetailsDTO.getSim2Number().isEmpty()) {
							device.setSim2Number(deviceSimDetailsDTO.getSim2Number());
							sim2ValidPoint++;
						}

						if (deviceSimDetailsDTO.getSim2Operator() != null
								&& !deviceSimDetailsDTO.getSim2Operator().isEmpty()) {
							device.setSim2Operator(deviceSimDetailsDTO.getSim2Operator());
							sim2ValidPoint++;
						}

						if (deviceSimDetailsDTO.getSim2ActivationDate() != null) {
							device.setSim2ActivationDate(deviceSimDetailsDTO.getSim2ActivationDate());
							sim2ValidPoint++;
						}
						if (deviceSimDetailsDTO.getSim2ExpiryDate() != null) {
							device.setSim2ExpiryDate(deviceSimDetailsDTO.getSim2ExpiryDate());
							sim2ValidPoint++;
						}
						if ((sim1ValidPoint == 0 && sim2ValidPoint == 4)) {
							isvalid = true;
						} else if (sim2ValidPoint == 0 && sim1ValidPoint == 4) {
							isvalid = true;
						} else if (sim1ValidPoint == 4 && sim2ValidPoint == 4) {
							isvalid = true;
						} else if (sim1ValidPoint > 0 && sim1ValidPoint < 4) {
							deviceSimDetailsDTO.setResponse("Please all details of Sim1 ");
						} else if (sim2ValidPoint > 0 && sim2ValidPoint < 4) {
							deviceSimDetailsDTO.setResponse("Please all details of Sim2 ");
						}

						if (isvalid != null && isvalid) {
							updatedDeviceList.add(device);
							deviceSimDetailsDTO.setIsUpdated(true);
							deviceSimDetailsDTO.setResponse(null);
							failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
						} else {
							deviceSimDetailsDTO.setIsUpdated(false);
							failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
						}
					} else {
						deviceSimDetailsDTO.setResponse("Invalid Imei Number");
						deviceSimDetailsDTO.setIsUpdated(false);
						failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
					}
				}
				if (updatedDeviceList != null && !updatedDeviceList.isEmpty()) {
					deviceRepository.saveAll(updatedDeviceList);
				}
			}
		} else {
			for (DeviceSimDetailsDTO deviceSimDetailsDTO : deviceSimDetailsDTOList) {
				deviceSimDetailsDTO.setResponse("Invalid Imei Number");
				deviceSimDetailsDTO.setIsUpdated(false);
				failedDeviceSimDetailsDTOList.add(deviceSimDetailsDTO);
			}
		}
		return new Response<>(HttpStatus.OK.value(), "Updated Successfully", failedDeviceSimDetailsDTOList);
	}

	@Override
	public Response<?> getDevicesCommands(StateCmdMstrResponseDTO stateCmdMstrResponseDTO) {
		List<StateCmdMstrResponseDTO> stateCmdMstrResponseList = new ArrayList<>();

		List<Device> deviceList = deviceRepository
				.findAllByImeiNoInAndState_idNotNullAndClientIdNotNull(stateCmdMstrResponseDTO.getImeiNumberList());
		if (deviceList != null && !deviceList.isEmpty()) {
			for (Device device : deviceList) {

				if (device.getState() != null && device.getState().getId() != null && device.getClientId() != null) {
					System.out.println(device.getState().getId());
					System.out.println(device.getClientId());
					List<StateCmdMstrEntity> stateCmdMstrEntityList = stateCmdMstrRepository
							.getAllByStateIdAndClientId(device.getClientId(), device.getState().getId());
					if (stateCmdMstrEntityList != null && !stateCmdMstrEntityList.isEmpty()) {
						List<StateCmdMstrDTO> stateCmdMstrDTOList = stateCmdMstrEntityList.stream()
								.filter(x -> x != null).map(x -> x.convertEntityToDto(x)).collect(Collectors.toList());
						if (stateCmdMstrDTOList != null && !stateCmdMstrDTOList.isEmpty()) {
							StateCmdMstrResponseDTO stateCmdMstrResponse = new StateCmdMstrResponseDTO();
							stateCmdMstrResponse.setImeiNumber(device.getImeiNo());
							stateCmdMstrResponse.setStateCmdMstrDTOList(stateCmdMstrDTOList);
							stateCmdMstrResponseList.add(stateCmdMstrResponse);
						}
					}
				}
			}
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Not Found", stateCmdMstrResponseList);
		} else {
			return new Response<>(HttpStatus.OK.value(), "Fetch Successfully", stateCmdMstrResponseList);
		}

	}

	@Override
	public Response<?> boxUnboxing(List<Long> boxIds) {
		// TODO Auto-generated method stub
		try {
			if (boxIds != null && !boxIds.isEmpty()) {
				List<Box> boxList = boxRepository.findByIdInAndIssuedList_idNotNull(boxIds);
				if (boxList != null && !boxList.isEmpty()) {
					List<Long> boxIdList = boxList.stream().filter(x -> x != null && x.getId() != null).map(Box::getId)
							.collect(Collectors.toList());
					// for (Box box : boxList) {
					if (boxIdList != null && !boxIdList.isEmpty()) {
						List<BoxDevice> boxDevicesList = boxDeviceRepository.findByBox_idInAndIsActiveTrue(boxIdList);
						if (boxDevicesList != null && !boxDevicesList.isEmpty()) {

							List<Device> deviceList = boxDevicesList.stream()
									.filter(x -> x != null && x.getDevice() != null && x.getDevice().getId() != null)
									.map(BoxDevice::getDevice).collect(Collectors.toList());
							if (deviceList != null && !deviceList.isEmpty()) {

								deviceList.stream().filter(x -> x != null)
										.forEach(x -> x.setStatus(StatusMaster.DEVICE_PACKED));

								deviceRepository.saveAll(deviceList);
								boxDeviceRepository.deleteAllInBatch(boxDevicesList);
								boxRepository.deleteAllInBatch(boxList);
							} else {
								return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Box has no device", null);
							}
						} else {
							return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Box has no device", null);
						}
					} else {
						return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Box list not exist", null);
					}
					// }

				} else {

				}
			} else {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Empty Box list", null);
			}
		} catch (Exception e) {
			return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "Not Found", null);
		}
		return new Response<>(HttpStatus.OK.value(), "Not Found", null);
	}

	public Response<?> updateIssuedDeviceConfigurationV2(List<TcPackets> tcPacketList) {
		Boolean isUpdated = false;
		List<String> imeiNoList = tcPacketList.stream()
				.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
				.map(TcPackets::getImeiNumber).collect(Collectors.toList());
		List<Device> deviceList = deviceRepository.findAllByImeiNoIn(imeiNoList);
		List<DeviceConfigMaster> deviceConfigurationMasterList = deviceConfigMasterRepository
				.findAllByImeiNoIn(imeiNoList);
		Map<String, DeviceConfigMaster> deviceConfigMasterMap = new HashMap<>();
		if (deviceConfigurationMasterList != null) {
			deviceConfigMasterMap = deviceConfigurationMasterList.stream()
					.filter(x -> x != null && x.getImeiNo() != null && !x.getImeiNo().isEmpty())
					.collect(Collectors.toMap(DeviceConfigMaster::getImeiNo, Function.identity()));
		}
		if (deviceList != null && !deviceList.isEmpty()) {
			Map<String, TcPackets> tcPacketsMap = tcPacketList.stream()
					.filter(x -> x != null && x.getImeiNumber() != null && !x.getImeiNumber().isEmpty())
					.collect(Collectors.toMap(TcPackets::getImeiNumber, Function.identity()));
			if (tcPacketsMap != null && !tcPacketsMap.isEmpty()) {
				List<Device> updatedDeviceList = new ArrayList<>();
				List<DeviceConfigMaster> updateDeviceConfigMasterList = new ArrayList<>();
				for (Device device : deviceList) {
					if (tcPacketsMap.containsKey(device.getImeiNo())) {
						TcPackets tcPackets = tcPacketsMap.get(device.getImeiNo());
						if (deviceConfigMasterMap.containsKey(device.getImeiNo())) {
							DeviceConfigMaster deviceConfigMaster = deviceConfigMasterMap.get(device.getImeiNo());
							if (!deviceConfigMaster.getIsSuccess()) {								
								if (tcPackets.getStateId() != null) {
									if (tcPackets.getStateId().equals(device.getState().getId())) {
										deviceConfigMaster.setIsSuccess(true);
										deviceConfigMaster.setLastCommandShootAt(tcPackets.getCommandSendDate());
										updateDeviceConfigMasterList.add(deviceConfigMaster);
									} else {      
										boolean isSameDay = DateUtil.datesEqualIgnoringTime(deviceConfigMaster.getLastCommandShootAt(), tcPackets.getCommandSendDate());
										if (isSameDay) {
											deviceConfigMaster.setLastCommandShootAt(tcPackets.getCommandSendDate());
											deviceConfigMaster.setRetryCount(deviceConfigMaster.getRetryCount() + 1);
											updateDeviceConfigMasterList.add(deviceConfigMaster);
										} else {
											deviceConfigMaster.setLastCommandShootAt(tcPackets.getCommandSendDate());
											deviceConfigMaster.setRetryCount(1);
											updateDeviceConfigMasterList.add(deviceConfigMaster);
										}
									}
								} else {
									boolean isSameDay = DateUtil.datesEqualIgnoringTime(deviceConfigMaster.getLastCommandShootAt(), tcPackets.getCommandSendDate());
									if (isSameDay) {
										deviceConfigMaster.setLastCommandShootAt(tcPackets.getCommandSendDate());
										deviceConfigMaster.setRetryCount(deviceConfigMaster.getRetryCount() + 1);
										updateDeviceConfigMasterList.add(deviceConfigMaster);
									} else {
										deviceConfigMaster.setLastCommandShootAt(tcPackets.getCommandSendDate());
										deviceConfigMaster.setRetryCount(1);
										updateDeviceConfigMasterList.add(deviceConfigMaster);
									}
								}
							}
						} else {
							DeviceConfigMaster configMaster = new DeviceConfigMaster();
							configMaster.setImeiNo(tcPackets.getImeiNumber());
							configMaster.setLastCommandShootAt(tcPackets.getCommandSendDate());
							configMaster.setRetryCount(1);
							if (tcPackets.getStateId() != null) {
								if (tcPackets.getStateId().equals(device.getState().getId())) {
									configMaster.setIsSuccess(true);
								} else {
									configMaster.setIsSuccess(false);
								}
							} else {
								configMaster.setIsSuccess(false);
							}
							updateDeviceConfigMasterList.add(configMaster);	
						}
						if (tcPackets.getConfigDone() != null && tcPackets.getConfigDone()) {
							device.setIsConfigurationComplete(tcPackets.getConfigDone());
							device.setConfigDoneDate(new Date());
						}
						if (tcPackets.getCommmandSend() != null && tcPackets.getCommmandSend()) {
							device.setIsCommandSend(true);
							device.setCommandSendDate(new Date());
							device.setLastCommand(tcPackets.getLastCommand());
						}
						updatedDeviceList.add(device);
					}
				}
				if (updatedDeviceList != null && !updatedDeviceList.isEmpty()) {
					deviceRepository.saveAll(updatedDeviceList);
					isUpdated = true;
				}
				if (updateDeviceConfigMasterList != null && !updateDeviceConfigMasterList.isEmpty()) {
					deviceConfigMasterRepository.saveAll(updateDeviceConfigMasterList);
				}
			}
		}
		return new Response<>(HttpStatus.OK.value(), "Updated Successfully", isUpdated);
	}
	
	public Response<?> getNotConfigIssuedDeviceV2() {
		List<TcPackets> tcPacketsResponseDtoList = new ArrayList<>();
		String date = DateUtil.localDateTimeToStringInFormatYYYYMMDDHHMMSS(new Date());
		List<Device> deviceList = deviceRepository.findNotConfigDevices(date);
//		deviceList = deviceList.stream().filter(o -> o.getImeiNo().equalsIgnoreCase("866567066751059")).collect(Collectors.toList());
//		List<StateCmdMstrEntity> stateCmdMstrListMap = stateServiceImpl.getAllStateCmd();
		if (deviceList != null && !deviceList.isEmpty()) {
			for (Device device : deviceList) {
				TcPackets tcPackets = new TcPackets();
				tcPackets.setImeiNumber(device.getImeiNo());
				if (device.getState() != null) {
//					StateCmdMstrEntity stateCmd = stateCmdMstrListMap.stream()
//							.filter(o -> o.getState().getId().intValue() == device.getState().getId().intValue()
//									&& o.getClient().getId().intValue() == device.getClientId().intValue())
//							.findAny().orElse(null);
					tcPackets.setStateId(device.getState().getId().intValue());
//					if (stateCmd != null) {
//						tcPackets.setStateCommand(stateCmd.getCommand());
//					}
				}
				tcPacketsResponseDtoList.add(tcPackets);
			}
		}

		return new Response<>(HttpStatus.OK.value(), "Retrieved successfully", tcPacketsResponseDtoList);
	}
}
