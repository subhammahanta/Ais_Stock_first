package com.watsoo.device.management.serviceImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.DeviceCommandDTO;
import com.watsoo.device.management.dto.GPSDevicesDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.DeviceCommandChangeTrail;
import com.watsoo.device.management.repository.DeviceCommandChangeTrailRepository;
import com.watsoo.device.management.service.CommandTransactionsService;
import com.watsoo.device.management.util.HttpUtilityV2;
import com.watsoo.device.management.util.Utility;

@Service
public class CommandTransactionsServiceImpl implements CommandTransactionsService {

	@Autowired
	private HttpUtilityV2 httpUtility;

	@Autowired
	private DeviceCommandChangeTrailRepository repository;

	@Value("${ais.all.core.user}")
	private String user1;

	@Value("${ais.all.core.password}")
	private String password1;

	@Value("${ais.all.core.url}")
	private String url1;

	@Value("${ais.own.core.user}")
	private String user2;

	@Value("${ais.own.core.password}")
	private String password2;

	@Value("${ais.own.core.url}")
	private String url2;

	@Override
	public Response<String> shootCommand(DeviceCommandDTO deviceCommandDTO) {
		Response<String> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		try {
			if (!deviceCommandDTO.getImeiNo().isEmpty() && !deviceCommandDTO.getCommand().isEmpty()) {
				String deviceResponse = httpUtility.httpGet(url1 + Constant.DEVICE_API, user1, password1);
				if (deviceResponse != null) {
					Type listOfMyClassObject = new TypeToken<ArrayList<GPSDevicesDTO>>() {
					}.getType();
					List<GPSDevicesDTO> jsonArrayObj = new Gson().fromJson(deviceResponse, listOfMyClassObject);
					if (jsonArrayObj != null && jsonArrayObj.size() > 0) {
						GPSDevicesDTO deviceExist = jsonArrayObj.stream()
								.filter(o -> o.getUniqueId().equalsIgnoreCase(deviceCommandDTO.getImeiNo().trim()))
								.findAny().orElse(null);
						if (deviceExist != null) {
							JsonObject requestBody = Utility.commandJsonObject(deviceExist.getId(),
									deviceCommandDTO.getCommand());
							String coreResponse = httpUtility.httpPost(url1 + Constant.COMMAND_SEND_API, user1,
									password1, requestBody);
							response.setData(coreResponse);
							response.setMessage(coreResponse);
							response.setResponseCode(HttpStatus.OK.value());
						} else {
							String otherServerDeviceResponse = httpUtility.httpGet(url2 + Constant.DEVICE_API, user2,
									password2);
							if (otherServerDeviceResponse != null) {
								Type listOfMyClassObject2 = new TypeToken<ArrayList<GPSDevicesDTO>>() {
								}.getType();
								List<GPSDevicesDTO> jsonArrayObj2 = new Gson().fromJson(otherServerDeviceResponse,
										listOfMyClassObject2);
								if (jsonArrayObj2 != null && jsonArrayObj2.size() > 0) {
									GPSDevicesDTO deviceExistInOtherServer = jsonArrayObj2.stream().filter(
											o -> o.getUniqueId().equalsIgnoreCase(deviceCommandDTO.getImeiNo().trim()))
											.findAny().orElse(null);
									if (deviceExistInOtherServer != null) {
										JsonObject requestBody = Utility.commandJsonObject(
												deviceExistInOtherServer.getId(), deviceCommandDTO.getCommand());
										String coreResponse = httpUtility.httpPost(url2 + Constant.COMMAND_SEND_API,
												user2, password2, requestBody);
										response.setData(coreResponse);
										response.setMessage(coreResponse);
										response.setResponseCode(HttpStatus.OK.value());
									} else {
										response.setData(null);
										response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
										response.setResponseCode(HttpStatus.NOT_FOUND.value());
									}
								}
							} else {
								response.setData(null);
								response.setMessage("Core Not Responding!!!");
								response.setResponseCode(HttpStatus.EXPECTATION_FAILED.value());
							}
						}
					}
				} else {
					response.setData(null);
					response.setMessage("Core Not Responding!!!");
					response.setResponseCode(HttpStatus.EXPECTATION_FAILED.value());
				}
			} else {
				response.setData(null);
				response.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
				response.setResponseCode(HttpStatus.EXPECTATION_FAILED.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public void updateDeviceCommandChangeTrail(DeviceCommandDTO deviceCommandDTO) {
		DeviceCommandChangeTrail trail = new DeviceCommandChangeTrail();
		trail.setModifiedAt(new Date());
		trail.setModifiedBy(deviceCommandDTO.getUserName());
		trail.setImeiNo(deviceCommandDTO.getImeiNo());
		trail.setAfterUpdate(deviceCommandDTO.getCommand());
		trail.setUserId(deviceCommandDTO.getUserId());
		repository.save(trail);
	}

}
