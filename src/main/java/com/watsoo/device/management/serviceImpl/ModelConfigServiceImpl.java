package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.GetCommandKey;
import com.watsoo.device.management.model.ModelConfig;
import com.watsoo.device.management.repository.GetCommandKeyRepository;
import com.watsoo.device.management.repository.ModelConfigRepository;
import com.watsoo.device.management.service.ModelConfigService;

@Service
public class ModelConfigServiceImpl implements ModelConfigService {

	@Autowired
	private ModelConfigRepository modelConfigRepository;

	@Autowired
	private GetCommandKeyRepository getCommandKeyRepository;

	@Value("${command.sinfo1.waiting.time}")
	private Integer sinfo1WaitingTime;

	@Value("${command.sinfo2.waiting.time}")
	private Integer sinfo2WaitingTime;

	@Value("${command.calibration}")
	private String calibrationCommand;

	@Value("${command.calibration.waiting.time}")
	private Integer calibrationWaitingTime;

	@Value("${command.emergency}")
	private String emergencyOffCommand;

	@Override
	public Response<Object> checkModelConfig(DeviceCommandDTO deviceCommandDTO) {
		if (deviceCommandDTO.getModelId() != null && deviceCommandDTO.getModelId() > 0
				&& deviceCommandDTO.getStateId() != null && deviceCommandDTO.getStateId() > 0) {

			List<ModelConfig> modelConfigList = modelConfigRepository
					.findByStateAndModelId(deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId());
			if (modelConfigList == null || modelConfigList.isEmpty()) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "No configuration found");
			}
			return new Response<>(HttpStatus.OK.value(), "configuration found");
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "ModelId and StateId can't be null");
		}
	}

	@Override
	public Response<Object> addModelConfig(DeviceCommandDTO deviceCommandDTO) {

		if (deviceCommandDTO.getStateId() != null && deviceCommandDTO.getCommand() != null
				&& deviceCommandDTO.getCommand() != "") {
			List<ModelConfig> configs = new ArrayList<>();
			List<ModelConfig> list = new ArrayList<>();
			list = modelConfigRepository.findByStateIdAndModelIdAndProviderIdAndOperatorIdAndClientId(
					deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId(), deviceCommandDTO.getProviderId(),
					deviceCommandDTO.getOperatorId(), deviceCommandDTO.getClientId());
			if (list == null || list.isEmpty()) {
				list = modelConfigRepository.findByStateIdAndModelIdAndProviderIdAndOperatorId(
						deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId(), deviceCommandDTO.getProviderId(),
						deviceCommandDTO.getOperatorId());
			}
			Map<String, ModelConfig> modelConfigMap = list.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(ModelConfig::getCommand, Function.identity()));

			List<GetCommandKey> getCommandKeys = getCommandKeyRepository.findAll();
			Map<String, GetCommandKey> commandKeyMap = getCommandKeys.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(GetCommandKey::getCommandKey, Function.identity()));
			if (deviceCommandDTO.getModelConfigId() != null) {
				Optional<ModelConfig> modelOptional = modelConfigRepository
						.findById(deviceCommandDTO.getModelConfigId());
				ModelConfig updateConfig = modelOptional.get();
				if (updateConfig.getIsDefault() != null && updateConfig.getIsDefault()) {
					int seqCount = 1;

					ModelConfig modelConfig = new ModelConfig();
					modelConfig.setModelId(deviceCommandDTO.getModelId());
					modelConfig.setStateId(updateConfig.getStateId());
					modelConfig.setCommand(deviceCommandDTO.getCommand());
					modelConfig.setModelConfigSequenceNo(seqCount);
					seqCount++;
					// modelConfig.setResponse(deviceCommandDTO.getResponse());
					modelConfig.setUpdatedBy(deviceCommandDTO.getUserId());
					modelConfig.setOperatorId(deviceCommandDTO.getOperatorId());
					modelConfig.setProviderId(deviceCommandDTO.getProviderId());
					modelConfig.setClientId(deviceCommandDTO.getClientId());
					modelConfig.setUpdatedAt(new Date());
					modelConfig.setIsActive(true);
					modelConfig.setIsDefault(false);
					configs.add(modelConfig);

					// ADD CALIBRATION COMMAND
					ModelConfig modelCon = new ModelConfig();
					modelCon.setModelId(deviceCommandDTO.getModelId());
					modelCon.setStateId(updateConfig.getStateId());
					modelCon.setCommand(calibrationCommand);
					modelCon.setWaitingTime(calibrationWaitingTime);
					modelCon.setModelConfigSequenceNo(seqCount);
					seqCount++;
					// modelConfig.setResponse(deviceCommandDTO.getResponse());
					modelCon.setUpdatedBy(deviceCommandDTO.getUserId());
					modelCon.setOperatorId(deviceCommandDTO.getOperatorId());
					modelCon.setProviderId(deviceCommandDTO.getProviderId());
					modelCon.setClientId(deviceCommandDTO.getClientId());
					modelCon.setUpdatedAt(new Date());
					modelCon.setIsActive(true);
					modelCon.setIsDefault(false);
					configs.add(modelCon);
					// get key

					for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {
						if (deviceCommandDTO.getCommand().contains(entry.getKey())) {
							ModelConfig config = new ModelConfig();
							config.setModelId(deviceCommandDTO.getModelId());
							config.setStateId(updateConfig.getStateId());
							config.setCommand(entry.getValue().getCommand());
							String extractValue = extractValue(deviceCommandDTO.getCommand(), entry.getKey());
							config.setResponse(extractValue);
							config.setUpdatedBy(deviceCommandDTO.getUserId());
							config.setOperatorId(deviceCommandDTO.getOperatorId());
							config.setProviderId(deviceCommandDTO.getProviderId());
							config.setClientId(deviceCommandDTO.getClientId());
							config.setUpdatedAt(new Date());
							config.setIsActive(true);
							config.setModelConfigSequenceNo(seqCount);
							config.setIsDefault(false);
							seqCount++;
							configs.add(config);
						}
						if (entry.getKey().equalsIgnoreCase("SINFO1")) {
							ModelConfig config = null;
							config = modelConfigMap.get(entry.getValue().getCommand());
							if (config == null) {
								config = new ModelConfig();
							}
							config.setIsDefault(false);
							config.setModelId(deviceCommandDTO.getModelId());
							config.setStateId(deviceCommandDTO.getStateId());
							config.setCommand(entry.getValue().getCommand());
							Map<String, String> keyToVerify = new HashMap<>();
							keyToVerify.put("G_Fix", "AD");
							keyToVerify.put("E", "1011");
							String key = "";
							ObjectMapper objectMapper = new ObjectMapper();
							try {
								key = objectMapper.writeValueAsString(keyToVerify);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							config.setKeyToVerify(key);
							config.setWaitingTime(sinfo1WaitingTime);
							config.setUpdatedBy(deviceCommandDTO.getUserId());
							config.setOperatorId(deviceCommandDTO.getOperatorId());
							config.setProviderId(deviceCommandDTO.getProviderId());
							config.setClientId(deviceCommandDTO.getClientId());
							config.setUpdatedAt(new Date());
							config.setIsActive(true);
							config.setModelConfigSequenceNo(seqCount);
							seqCount++;
							configs.add(config);
						}
						if (entry.getKey().equalsIgnoreCase("SINFO2")) {
							ModelConfig config = null;
							config = modelConfigMap.get(entry.getValue().getCommand());
							if (config == null) {
								config = new ModelConfig();
							}
							config.setIsDefault(false);
							config.setModelId(deviceCommandDTO.getModelId());
							config.setStateId(deviceCommandDTO.getStateId());
							config.setCommand(entry.getValue().getCommand());
							Map<String, String> keyToVerify = new HashMap<>();
							keyToVerify.put("IGN", "1");
							keyToVerify.put("ExtV", "12870");
							keyToVerify.put("IntV", "004.0");
							keyToVerify.put("BC", "CHECKONLYKEY");
							String key = "";
							ObjectMapper objectMapper = new ObjectMapper();
							try {
								key = objectMapper.writeValueAsString(keyToVerify);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							config.setKeyToVerify(key);
							config.setWaitingTime(sinfo2WaitingTime);
							config.setUpdatedBy(deviceCommandDTO.getUserId());
							config.setOperatorId(deviceCommandDTO.getOperatorId());
							config.setProviderId(deviceCommandDTO.getProviderId());
							config.setClientId(deviceCommandDTO.getClientId());
							config.setUpdatedAt(new Date());
							config.setIsActive(true);
							config.setModelConfigSequenceNo(seqCount);
							seqCount++;
							configs.add(config);
						}
						if (entry.getKey().equalsIgnoreCase("SINFO4")) {
							ModelConfig config = null;
							config = modelConfigMap.get(entry.getValue().getCommand());
							if (config == null) {
								config = new ModelConfig();
							}
							config.setIsDefault(false);
							config.setModelId(deviceCommandDTO.getModelId());
							config.setStateId(deviceCommandDTO.getStateId());
							config.setCommand(entry.getValue().getCommand());
							config.setUpdatedBy(deviceCommandDTO.getUserId());
							config.setOperatorId(deviceCommandDTO.getOperatorId());
							config.setProviderId(deviceCommandDTO.getProviderId());
							config.setClientId(deviceCommandDTO.getClientId());
							config.setUpdatedAt(new Date());
							config.setIsActive(true);
							config.setModelConfigSequenceNo(seqCount);
							seqCount++;
							configs.add(config);
						}

					}

					// emergencyOffCommand
					ModelConfig modelCon2 = new ModelConfig();
					modelCon2.setModelId(deviceCommandDTO.getModelId());
					modelCon2.setStateId(updateConfig.getStateId());
					modelCon2.setCommand(emergencyOffCommand);
					// modelCon2.setWaitingTime(calibrationWaitingTime);
					modelCon2.setModelConfigSequenceNo(100);
					// seqCount++;
					// modelConfig.setResponse(deviceCommandDTO.getResponse());
					modelCon2.setUpdatedBy(deviceCommandDTO.getUserId());
					modelCon2.setOperatorId(deviceCommandDTO.getOperatorId());
					modelCon2.setProviderId(deviceCommandDTO.getProviderId());
					modelCon2.setClientId(deviceCommandDTO.getClientId());
					modelCon2.setUpdatedAt(new Date());
					modelCon2.setIsActive(true);
					modelCon2.setIsDefault(false);
					configs.add(modelCon2);

				} else {
					// client type
					ModelConfig model = list.get(list.size() - 1);
					int seqCount = model.getModelConfigSequenceNo();
					seqCount++;
					updateConfig.setCommand(deviceCommandDTO.getCommand());
					updateConfig.setUpdatedAt(new Date());
					updateConfig.setUpdatedBy(deviceCommandDTO.getUserId());
					updateConfig.setIsDefault(false);
					configs.add(updateConfig);
					for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {

						if (deviceCommandDTO.getCommand().contains(entry.getKey())) {
							// findby entry.getValue() from ModelConfig
							ModelConfig modelConfigObj = list.stream()
									.filter(o -> o.getId() != null
											&& o.getCommand().equalsIgnoreCase(entry.getValue().getCommand()))
									.findFirst().orElse(null);

							// value match
							// get value
							String extractValue = extractValue(deviceCommandDTO.getCommand(), entry.getKey());
							if (modelConfigObj != null) {
								if (!modelConfigObj.getResponse().equalsIgnoreCase(extractValue)) {
									// update
									modelConfigObj.setResponse(extractValue);
									modelConfigObj.setUpdatedAt(new Date());
									modelConfigObj.setUpdatedBy(deviceCommandDTO.getUserId());
									modelConfigObj.setIsDefault(false);
									configs.add(updateConfig);
								}
							} else {

								// create new
								ModelConfig obj = new ModelConfig();
								obj.setModelId(deviceCommandDTO.getModelId());
								obj.setStateId(deviceCommandDTO.getStateId());
								//
								obj.setCommand(entry.getValue().getCommand());
								//
								String extractValue2 = extractValue(deviceCommandDTO.getCommand(), entry.getKey());
								obj.setResponse(extractValue2);
								obj.setUpdatedBy(deviceCommandDTO.getUserId());
								obj.setOperatorId(deviceCommandDTO.getOperatorId());
								obj.setProviderId(deviceCommandDTO.getProviderId());
								obj.setModelConfigSequenceNo(seqCount);
								seqCount++;
								obj.setClientId(deviceCommandDTO.getClientId());
								obj.setUpdatedAt(new Date());
								obj.setIsActive(true);
								obj.setIsDefault(false);
								configs.add(obj);
							}
						}
						if (entry.getKey().equalsIgnoreCase("SINFO1")) {
							ModelConfig config = null;
							config = modelConfigMap.get(entry.getValue().getCommand());
							if (config == null) {
								config = new ModelConfig();
							}
							config.setModelId(deviceCommandDTO.getModelId());
							config.setStateId(deviceCommandDTO.getStateId());
							config.setCommand(entry.getValue().getCommand());
							Map<String, String> keyToVerify = new HashMap<>();
							keyToVerify.put("G_Fix", "AD");
							keyToVerify.put("E", "1011");
							String key = "";
							ObjectMapper objectMapper = new ObjectMapper();
							try {
								key = objectMapper.writeValueAsString(keyToVerify);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							config.setKeyToVerify(key);
							config.setWaitingTime(sinfo1WaitingTime);
							config.setUpdatedBy(deviceCommandDTO.getUserId());
							config.setOperatorId(deviceCommandDTO.getOperatorId());
							config.setProviderId(deviceCommandDTO.getProviderId());
							config.setClientId(deviceCommandDTO.getClientId());
							config.setUpdatedAt(new Date());
							config.setIsActive(true);
							config.setModelConfigSequenceNo(seqCount);
							seqCount++;
							configs.add(config);
						}
						if (entry.getKey().equalsIgnoreCase("SINFO2")) {
							ModelConfig config = null;
							config = modelConfigMap.get(entry.getValue().getCommand());
							if (config == null) {
								config = new ModelConfig();
							}
							config.setModelId(deviceCommandDTO.getModelId());
							config.setStateId(deviceCommandDTO.getStateId());
							config.setCommand(entry.getValue().getCommand());
							Map<String, String> keyToVerify = new HashMap<>();
							keyToVerify.put("IGN", "1");
							keyToVerify.put("ExtV", "12870");
							keyToVerify.put("IntV", "004.0");
							keyToVerify.put("BC", "CHECKONLYKEY");
							String key = "";
							ObjectMapper objectMapper = new ObjectMapper();
							try {
								key = objectMapper.writeValueAsString(keyToVerify);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							config.setKeyToVerify(key);
							config.setWaitingTime(sinfo2WaitingTime);
							config.setUpdatedBy(deviceCommandDTO.getUserId());
							config.setOperatorId(deviceCommandDTO.getOperatorId());
							config.setProviderId(deviceCommandDTO.getProviderId());
							config.setClientId(deviceCommandDTO.getClientId());
							config.setUpdatedAt(new Date());
							config.setIsActive(true);
							config.setModelConfigSequenceNo(seqCount);
							seqCount++;
							configs.add(config);
						}
						if (entry.getKey().equalsIgnoreCase("SINFO4")) {
							ModelConfig config = null;
							config = modelConfigMap.get(entry.getValue().getCommand());
							if (config == null) {
								config = new ModelConfig();
							}
							config.setModelId(deviceCommandDTO.getModelId());
							config.setStateId(deviceCommandDTO.getStateId());
							config.setCommand(entry.getValue().getCommand());
							config.setUpdatedBy(deviceCommandDTO.getUserId());
							config.setOperatorId(deviceCommandDTO.getOperatorId());
							config.setProviderId(deviceCommandDTO.getProviderId());
							config.setClientId(deviceCommandDTO.getClientId());
							config.setUpdatedAt(new Date());
							config.setIsActive(true);
							config.setModelConfigSequenceNo(seqCount);
							seqCount++;
							configs.add(config);
						}
					}

				}

			} else {
				int seqCount = 1;

				ModelConfig modelConf = new ModelConfig();
				modelConf.setIsDefault(deviceCommandDTO.getIsDefault());
				modelConf.setModelId(deviceCommandDTO.getModelId());
				modelConf.setStateId(deviceCommandDTO.getStateId());
				modelConf.setCommand(deviceCommandDTO.getCommand());
				modelConf.setModelConfigSequenceNo(seqCount);
				seqCount++;
				// modelConfig.setResponse(deviceCommandDTO.getResponse());
				modelConf.setCreatedBy(deviceCommandDTO.getUserId());
				modelConf.setOperatorId(deviceCommandDTO.getOperatorId());
				modelConf.setProviderId(deviceCommandDTO.getProviderId());
				modelConf.setClientId(deviceCommandDTO.getClientId());
				modelConf.setCreatedAt(new Date());
				modelConf.setIsActive(true);
				configs.add(modelConf);

				// ADD CALIBRATION COMMAND
				ModelConfig modelCon = new ModelConfig();
				modelCon.setIsDefault(deviceCommandDTO.getIsDefault());
				modelCon.setModelId(deviceCommandDTO.getModelId());
				modelCon.setStateId(deviceCommandDTO.getStateId());
				modelCon.setCommand(calibrationCommand);
				modelCon.setWaitingTime(calibrationWaitingTime);
				modelCon.setModelConfigSequenceNo(seqCount);
				seqCount++;
				// modelConfig.setResponse(deviceCommandDTO.getResponse());
				modelCon.setCreatedBy(deviceCommandDTO.getUserId());
				modelCon.setOperatorId(deviceCommandDTO.getOperatorId());
				modelCon.setProviderId(deviceCommandDTO.getProviderId());
				modelCon.setClientId(deviceCommandDTO.getClientId());
				modelCon.setCreatedAt(new Date());
				modelCon.setIsActive(true);
				configs.add(modelCon);

				// get key

				for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {
					if (deviceCommandDTO.getCommand().contains(entry.getKey())) {
						ModelConfig config = new ModelConfig();
						config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						//
						config.setCommand(entry.getValue().getCommand());
						//
						String extractValue = extractValue(deviceCommandDTO.getCommand(), entry.getKey());
						config.setResponse(extractValue);
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO1")) {
						ModelConfig config = new ModelConfig();
						config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						Map<String, String> keyToVerify = new HashMap<>();
						keyToVerify.put("G_Fix", "AD");
						keyToVerify.put("E", "1011");
						String key = "";
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							key = objectMapper.writeValueAsString(keyToVerify);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						config.setKeyToVerify(key);
						config.setWaitingTime(sinfo1WaitingTime);
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO2")) {
						ModelConfig config = new ModelConfig();
						config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						Map<String, String> keyToVerify = new HashMap<>();
						keyToVerify.put("IGN", "1");
						keyToVerify.put("ExtV", "12870");
						keyToVerify.put("IntV", "004.0");
						keyToVerify.put("BC", "CHECKONLYKEY");
						String key = "";
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							key = objectMapper.writeValueAsString(keyToVerify);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						config.setKeyToVerify(key);
						config.setWaitingTime(sinfo2WaitingTime);
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO4")) {
						ModelConfig config = new ModelConfig();
						config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
				}

				// emergencyOffCommand
				ModelConfig modelCon2 = new ModelConfig();
				modelCon2.setModelId(deviceCommandDTO.getModelId());
				modelCon2.setStateId(deviceCommandDTO.getStateId());
				modelCon2.setCommand(emergencyOffCommand);
				// modelCon2.setWaitingTime(calibrationWaitingTime);
				modelCon2.setModelConfigSequenceNo(100);
				// seqCount++;
				// modelConfig.setResponse(deviceCommandDTO.getResponse());
				modelCon2.setUpdatedBy(deviceCommandDTO.getUserId());
				modelCon2.setOperatorId(deviceCommandDTO.getOperatorId());
				modelCon2.setProviderId(deviceCommandDTO.getProviderId());
				modelCon2.setClientId(deviceCommandDTO.getClientId());
				modelCon2.setUpdatedAt(new Date());
				modelCon2.setIsActive(true);
				modelCon2.setIsDefault(deviceCommandDTO.getIsDefault());
				configs.add(modelCon2);

			}

			modelConfigRepository.saveAll(configs);
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "add mandatory field");
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
	public Response<Object> addModelConfigV2(DeviceCommandDTO deviceCommandDTO) {

		if (deviceCommandDTO.getStateId() != null && deviceCommandDTO.getCommand() != null
				&& deviceCommandDTO.getCommand() != "") {
			List<ModelConfig> configs = new ArrayList<>();
			List<ModelConfig> list = new ArrayList<>();
			list = modelConfigRepository.findByStateIdAndModelIdAndProviderIdAndOperatorIdAndClientId(
					deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId(), deviceCommandDTO.getProviderId(),
					deviceCommandDTO.getOperatorId(), deviceCommandDTO.getClientId());
			if (list == null || list.isEmpty()) {
				list = modelConfigRepository.findByStateIdAndModelIdAndProviderIdAndOperatorId(
						deviceCommandDTO.getStateId(), deviceCommandDTO.getModelId(), deviceCommandDTO.getProviderId(),
						deviceCommandDTO.getOperatorId());
			}
			Map<String, ModelConfig> modelConfigMap = list.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(ModelConfig::getCommand, Function.identity()));

			List<GetCommandKey> getCommandKeys = getCommandKeyRepository.findAll();
			Map<String, GetCommandKey> commandKeyMap = getCommandKeys.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(GetCommandKey::getCommandKey, Function.identity()));
			if (deviceCommandDTO.getModelConfigId() != null) {
				Optional<ModelConfig> modelOptional = modelConfigRepository
						.findById(deviceCommandDTO.getModelConfigId());
				ModelConfig updateConfig = modelOptional.get();
				// client type
				ModelConfig model = list.get(list.size() - 1);
				int seqCount = model.getModelConfigSequenceNo();
				seqCount++;
				updateConfig.setCommand(deviceCommandDTO.getCommand());
				updateConfig.setUpdatedAt(new Date());
				updateConfig.setUpdatedBy(deviceCommandDTO.getUserId());
				//updateConfig.setIsDefault(false);
				configs.add(updateConfig);
				for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {

					if (deviceCommandDTO.getCommand().contains(entry.getKey())) {
						// findby entry.getValue() from ModelConfig
						ModelConfig modelConfigObj = list.stream()
								.filter(o -> o.getId() != null
										&& o.getCommand().equalsIgnoreCase(entry.getValue().getCommand()))
								.findFirst().orElse(null);

						// value match
						// get value
						String extractValue = extractValue(deviceCommandDTO.getCommand(), entry.getKey());
						if (modelConfigObj != null) {
							if (!modelConfigObj.getResponse().equalsIgnoreCase(extractValue)) {
								// update
								modelConfigObj.setResponse(extractValue);
								modelConfigObj.setUpdatedAt(new Date());
								modelConfigObj.setUpdatedBy(deviceCommandDTO.getUserId());
								//modelConfigObj.setIsDefault(false);
								configs.add(updateConfig);
							}
						} else {

							// create new
							ModelConfig obj = new ModelConfig();
							obj.setModelId(deviceCommandDTO.getModelId());
							obj.setStateId(deviceCommandDTO.getStateId());
							//
							obj.setCommand(entry.getValue().getCommand());
							//
							String extractValue2 = extractValue(deviceCommandDTO.getCommand(), entry.getKey());
							obj.setResponse(extractValue2);
							obj.setUpdatedBy(deviceCommandDTO.getUserId());
							obj.setOperatorId(deviceCommandDTO.getOperatorId());
							obj.setProviderId(deviceCommandDTO.getProviderId());
							obj.setModelConfigSequenceNo(seqCount);
							seqCount++;
							obj.setClientId(deviceCommandDTO.getClientId());
							obj.setUpdatedAt(new Date());
							obj.setIsActive(true);
							//obj.setIsDefault(false);
							configs.add(obj);
						}
					}
					if (entry.getKey().equalsIgnoreCase("SINFO1")) {
						ModelConfig config = null;
						config = modelConfigMap.get(entry.getValue().getCommand());
						if (config == null) {
							config = new ModelConfig();
						}
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						Map<String, String> keyToVerify = new HashMap<>();
						keyToVerify.put("G_Fix", "AD");
						keyToVerify.put("E", "1011");
						String key = "";
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							key = objectMapper.writeValueAsString(keyToVerify);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						config.setKeyToVerify(key);
						config.setWaitingTime(sinfo1WaitingTime);
						config.setUpdatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setUpdatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO2")) {
						ModelConfig config = null;
						config = modelConfigMap.get(entry.getValue().getCommand());
						if (config == null) {
							config = new ModelConfig();
						}
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						Map<String, String> keyToVerify = new HashMap<>();
						keyToVerify.put("IGN", "1");
						keyToVerify.put("ExtV", "12870");
						keyToVerify.put("IntV", "004.0");
						keyToVerify.put("BC", "CHECKONLYKEY");
						String key = "";
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							key = objectMapper.writeValueAsString(keyToVerify);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						config.setKeyToVerify(key);
						config.setWaitingTime(sinfo2WaitingTime);
						config.setUpdatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setUpdatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO4")) {
						ModelConfig config = null;
						config = modelConfigMap.get(entry.getValue().getCommand());
						if (config == null) {
							config = new ModelConfig();
						}
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						config.setUpdatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setUpdatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
				}
				// update emergencyOffCommand sequence no
				ModelConfig modelCon9 = modelConfigMap.get(emergencyOffCommand);
				modelCon9.setModelConfigSequenceNo(seqCount);
				configs.add(modelCon9);
			} else {
				int seqCount = 1;

				ModelConfig modelConf = new ModelConfig();
				//modelConf.setIsDefault(deviceCommandDTO.getIsDefault());
				modelConf.setModelId(deviceCommandDTO.getModelId());
				modelConf.setStateId(deviceCommandDTO.getStateId());
				modelConf.setCommand(deviceCommandDTO.getCommand());
				modelConf.setModelConfigSequenceNo(seqCount);
				seqCount++;
				// modelConfig.setResponse(deviceCommandDTO.getResponse());
				modelConf.setCreatedBy(deviceCommandDTO.getUserId());
				modelConf.setOperatorId(deviceCommandDTO.getOperatorId());
				modelConf.setProviderId(deviceCommandDTO.getProviderId());
				modelConf.setClientId(deviceCommandDTO.getClientId());
				modelConf.setCreatedAt(new Date());
				modelConf.setIsActive(true);
				configs.add(modelConf);

				// ADD CALIBRATION COMMAND
				ModelConfig modelCon = new ModelConfig();
				//modelCon.setIsDefault(deviceCommandDTO.getIsDefault());
				modelCon.setModelId(deviceCommandDTO.getModelId());
				modelCon.setStateId(deviceCommandDTO.getStateId());
				modelCon.setCommand(calibrationCommand);
				modelCon.setWaitingTime(calibrationWaitingTime);
				modelCon.setModelConfigSequenceNo(seqCount);
				seqCount++;
				// modelConfig.setResponse(deviceCommandDTO.getResponse());
				modelCon.setCreatedBy(deviceCommandDTO.getUserId());
				modelCon.setOperatorId(deviceCommandDTO.getOperatorId());
				modelCon.setProviderId(deviceCommandDTO.getProviderId());
				modelCon.setClientId(deviceCommandDTO.getClientId());
				modelCon.setCreatedAt(new Date());
				modelCon.setIsActive(true);
				configs.add(modelCon);

				// get key

				for (Map.Entry<String, GetCommandKey> entry : commandKeyMap.entrySet()) {
					if (deviceCommandDTO.getCommand().contains(entry.getKey())) {
						ModelConfig config = new ModelConfig();
						//config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						//
						config.setCommand(entry.getValue().getCommand());
						//
						String extractValue = extractValue(deviceCommandDTO.getCommand(), entry.getKey());
						config.setResponse(extractValue);
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO1")) {
						ModelConfig config = new ModelConfig();
						//config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						Map<String, String> keyToVerify = new HashMap<>();
						keyToVerify.put("G_Fix", "AD");
						keyToVerify.put("E", "1011");
						String key = "";
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							key = objectMapper.writeValueAsString(keyToVerify);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						config.setKeyToVerify(key);
						config.setWaitingTime(sinfo1WaitingTime);
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO2")) {
						ModelConfig config = new ModelConfig();
						//config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						Map<String, String> keyToVerify = new HashMap<>();
						keyToVerify.put("IGN", "1");
						keyToVerify.put("ExtV", "12870");
						keyToVerify.put("IntV", "004.0");
						keyToVerify.put("BC", "CHECKONLYKEY");
						String key = "";
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							key = objectMapper.writeValueAsString(keyToVerify);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						config.setKeyToVerify(key);
						config.setWaitingTime(sinfo2WaitingTime);
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
					if (entry.getKey().equalsIgnoreCase("SINFO4")) {
						ModelConfig config = new ModelConfig();
						//config.setIsDefault(deviceCommandDTO.getIsDefault());
						config.setModelId(deviceCommandDTO.getModelId());
						config.setStateId(deviceCommandDTO.getStateId());
						config.setCommand(entry.getValue().getCommand());
						config.setCreatedBy(deviceCommandDTO.getUserId());
						config.setOperatorId(deviceCommandDTO.getOperatorId());
						config.setProviderId(deviceCommandDTO.getProviderId());
						config.setClientId(deviceCommandDTO.getClientId());
						config.setCreatedAt(new Date());
						config.setIsActive(true);
						config.setModelConfigSequenceNo(seqCount);
						seqCount++;
						configs.add(config);
					}
				}
				// emergencyOffCommand
				ModelConfig modelCon2 = new ModelConfig();
				modelCon2.setModelId(deviceCommandDTO.getModelId());
				modelCon2.setStateId(deviceCommandDTO.getStateId());
				modelCon2.setCommand(emergencyOffCommand);
				// modelCon2.setWaitingTime(calibrationWaitingTime);
				modelCon2.setModelConfigSequenceNo(seqCount);
				// seqCount++;
				// modelConfig.setResponse(deviceCommandDTO.getResponse());
				modelCon2.setUpdatedBy(deviceCommandDTO.getUserId());
				modelCon2.setOperatorId(deviceCommandDTO.getOperatorId());
				modelCon2.setProviderId(deviceCommandDTO.getProviderId());
				modelCon2.setClientId(deviceCommandDTO.getClientId());
				modelCon2.setUpdatedAt(new Date());
				modelCon2.setIsActive(true);
				//modelCon2.setIsDefault(deviceCommandDTO.getIsDefault());
				configs.add(modelCon2);
			}
			modelConfigRepository.saveAll(configs);
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "add mandatory field");
		}
	}

}
