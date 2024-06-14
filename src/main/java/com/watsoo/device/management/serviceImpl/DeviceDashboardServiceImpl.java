package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.watsoo.device.management.dto.DashboardSectionDTO;
import com.watsoo.device.management.dto.DeviceStoreRequestDto;
import com.watsoo.device.management.dto.MaterialDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StoreResponseDTO;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceBlueprintMap;
import com.watsoo.device.management.model.DeviceMaster;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.DeviceBlueprintRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.service.DeviceDashboardService;
import com.watsoo.device.management.util.HttpUtility;

@Service
public class DeviceDashboardServiceImpl implements DeviceDashboardService {

	@Value("${store.dev.url}")
	private String storeDevUrl;
	
	@Autowired
	private DeviceRepository deviceRepo;
	
	@Autowired
	private HttpUtility httpUtility;
	
	@Autowired
	private DeviceBlueprintRepository deviceBluePrintRepository;
	
	@Autowired
	private BoxRepository boxRepo;
	
	@Override
	public Response<List<DashboardSectionDTO>> getDashBoardDTO() throws Exception {
		Response<List<DashboardSectionDTO>> response = new Response<>();
		List<DashboardSectionDTO> responseObj = new ArrayList<>();
		List<DeviceBlueprintMap> deviceBluePrintList = deviceBluePrintRepository.findAll();
		Map<DeviceMaster, List<DeviceBlueprintMap>> deviceBluePrintMap = deviceBluePrintList.stream()
				.filter(o -> o.getDeviceMstr() != null).collect(Collectors.groupingBy(o -> o.getDeviceMstr()));

		for (Entry<DeviceMaster, List<DeviceBlueprintMap>> entry : deviceBluePrintMap.entrySet()) {
			DeviceMaster deviceMstr = entry.getKey();
			List<DeviceBlueprintMap> requiredComposition = entry.getValue();
			String requestObj = "";
			List<Long> requiredItemsIds = new ArrayList<>();
			if(deviceMstr.getUseStoreComposition() != null && deviceMstr.getUseStoreComposition()) {
				DeviceStoreRequestDto deviceStoreRequestDto = new DeviceStoreRequestDto(deviceMstr.getRequestTypeSent(),
						deviceMstr.getSiteId(), String.valueOf(deviceMstr.getStoreId()),requiredItemsIds);
				requestObj = new Gson().toJson(deviceStoreRequestDto);
				String getAllStoreData = httpUtility.httpPostRequest(storeDevUrl, requestObj);

				ObjectMapper objectMapper = new ObjectMapper();

				List<Double> maxProductCreated = new ArrayList<>();
				StoreResponseDTO deviceStoreResponseDtoList = objectMapper.readValue(getAllStoreData, StoreResponseDTO.class);

				for (MaterialDTO deviceStoreResponseDto : deviceStoreResponseDtoList.getData()) {
					Double productCreated = Double.valueOf(deviceStoreResponseDto.getApproved_stock_qty())
							/ Double.valueOf(deviceStoreResponseDto.getRequired_qty());
					maxProductCreated.add(productCreated);
				}

				double value = Collections.min(maxProductCreated);
				int maxProductionAmount = (int) value;

				List<Device> devices = deviceRepo.findAll();
				DashboardSectionDTO boxDTO = new DashboardSectionDTO("BOX", 1, 5);
				responseObj.add(boxDTO);
				DashboardSectionDTO totalStorePcbDTO = new DashboardSectionDTO("Store PCB", 2, devices.size());
				responseObj.add(totalStorePcbDTO);
				DashboardSectionDTO readyPCBDTO = new DashboardSectionDTO("readyPcbDTO", 3, 10);
				responseObj.add(readyPCBDTO);
				DashboardSectionDTO possiblePcbDTO = new DashboardSectionDTO("Possible PCB", 4, maxProductionAmount);
				responseObj.add(possiblePcbDTO);
			} else {
				requiredItemsIds = requiredComposition.stream().filter(o -> o.getMaterialId() != null).map(o -> o.getMaterialId()).collect(Collectors.toList());
				DeviceStoreRequestDto deviceStoreRequestDto = new DeviceStoreRequestDto(deviceMstr.getRequestTypeSent(),
						deviceMstr.getSiteId(), "",requiredItemsIds);
				deviceStoreRequestDto.setMaterial_ids(requiredItemsIds);
				requestObj = new Gson().toJson(deviceStoreRequestDto);
				
				String getAllStoreData = httpUtility.httpPostRequest(storeDevUrl, requestObj);

				ObjectMapper objectMapper = new ObjectMapper();

				List<Double> maxProductCreated = new ArrayList<>();
				StoreResponseDTO deviceStoreResponseDtoList = objectMapper.readValue(getAllStoreData, StoreResponseDTO.class);

				for (DeviceBlueprintMap deviceStoreResponseDto : requiredComposition) {
					MaterialDTO materialConst = deviceStoreResponseDtoList.getData().stream().filter(o -> Integer.valueOf(o.getMaterial_id())== deviceStoreResponseDto.getMaterialId().intValue()).findAny().orElse(null);
					Double productCreated = Double.valueOf(materialConst.getApproved_stock_qty())
							/ deviceStoreResponseDto.getRequiredQty();
					maxProductCreated.add(productCreated);
				}

				double value = Collections.min(maxProductCreated);
				int maxProductionAmount = (int) value;

				List<Device> devices = deviceRepo.findAll();
				List<Box> boxList = boxRepo.findAll();
				Map<StatusMaster,List<Device>> deviceMap = devices.stream().filter(o -> o.getStatus()!= null).collect(Collectors.groupingBy(o -> o.getStatus()));
				DashboardSectionDTO boxDTO = new DashboardSectionDTO("BOX", 1, boxList.size());
				responseObj.add(boxDTO);
				DashboardSectionDTO totalStorePcbDTO = new DashboardSectionDTO("Store PCB", 2, deviceMap.get(StatusMaster.TESTED).size());
				responseObj.add(totalStorePcbDTO);
				DashboardSectionDTO readyPCBDTO = new DashboardSectionDTO("readyPcbDTO", 3, deviceMap.get(StatusMaster.DEVICE_PACKED).size());
				responseObj.add(readyPCBDTO);
				DashboardSectionDTO possiblePcbDTO = new DashboardSectionDTO("Possible PCB", 4, maxProductionAmount);
				responseObj.add(possiblePcbDTO);
				
			}
		}
		response.setData(responseObj);
		response.setMessage(HttpStatus.OK.getReasonPhrase());
		response.setResponseCode(HttpStatus.OK.value());
		return response;
	}
}
