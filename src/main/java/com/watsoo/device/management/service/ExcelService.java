package com.watsoo.device.management.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.dto.Response;

public interface ExcelService {

	//Response<Object> readExcelDataAndSaveInDb(MultipartFile file);

	Map<Integer, String> importUpdatedVehicleExcelFile(MultipartFile file);

	Map<Integer, String> createDeviceFromExcelFile(MultipartFile file);

	List<Map<Object, String>> mapClientDatabaseExcel(MultipartFile file);

	Object createBoxFromExcel();

	Map<Integer, String> mapCorrectClientWithDevice(MultipartFile file);

	Map<Integer, String> importUpdatedVehicleExcelFileV2(MultipartFile file);

}
