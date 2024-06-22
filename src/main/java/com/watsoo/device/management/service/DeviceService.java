package com.watsoo.device.management.service;

import java.io.IOException;
import java.util.List;

import com.watsoo.device.management.dto.*;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.DeviceLiteV3;

public interface DeviceService {

	Device saveDeviceData(Device device);

	Response<Device> getDevice(String imei);

	Response<GenericRequestBody> getAppVersion();

	Pagination<List<Device>> getAllDevice(GenericRequestBody requestDTO);

	Response<?> countNumberOfDeviceCreatedToday();

	Response<List<Device>> updateDeviceStatus(GenericRequestBody requestBody);

	Response<Device> updateDeviceState(GenericRequestBody requestBody);

	Pagination<List<DeviceLite>> getAllDeviceV2(GenericRequestBody requestDTO);

	Response<GenericRequestBody> getAllUniqueSoftwareVersion();

	Pagination<List<DeviceLite>> getAllDeviceV3(GenericRequestBody requestDTO);

	Response<?> getCurrentVersion();

	Response<?> updateSoftwareVersion(ConfigurationDTO dto);

	// Response<Device> updateDeviceLastInfo(GenericRequestBody requestBody);

//	List<Device> getAllDeviceV2();
	List<String> getAllSimOperator();

	Response<List<Device>> getAisDevice(String imei);

	List<String> getAllESimProvider();

	Pagination<List<DeviceLite>> getAllDeviceByImei(GenericRequestBody requestDTO);

	Response<GenericRequestBody> getAllUniqueSoftwareVersionV2();

	Response<List<DeviceLite>> getAllDeviceByListOfImei(GenericRequestBody requestDTO);

	List<Device> getAllDevices();

	Pagination<List<DeviceLite>> getAllDeviceV4(GenericRequestBody requestDTO);

	Device saveDeviceDataV2(Device deviceDTO);

	void generateExcelAndNotify(GenericRequestBody requestDTO) throws IOException;

	Boolean updateDeviceActivationExpiryDate(List<VehicleLazyEntity> request);

	Pagination<List<DeviceLiteV3>> getAllDevicePackedDevice(GenericRequestBody requestDTO);

	void generateUnConfigureDevicesExcelAndNotify(GenericRequestBody requestDTO) throws Exception;

	Pagination<List<DeviceLite>> getDeviceForReconfigure(GenericRequestBody requestDTO);


}
