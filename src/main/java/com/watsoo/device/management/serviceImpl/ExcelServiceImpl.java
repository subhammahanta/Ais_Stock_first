package com.watsoo.device.management.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.dto.ExcelClientDTO;
import com.watsoo.device.management.dto.ExcelDeviceDTO;
import com.watsoo.device.management.dto.ExcelVehicleDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.StateClientDTO;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.BoxTransaction;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.ExcelDeviceDatabaseDeviceMapping;
import com.watsoo.device.management.model.IssuedList;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.BoxTransactionRepository;
import com.watsoo.device.management.repository.ClientRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.ExcelDeviceDatabaseDeviceMappingRepository;
import com.watsoo.device.management.repository.IssuedRepository;
import com.watsoo.device.management.repository.StateRepository;
import com.watsoo.device.management.repository.UserRepository;
import com.watsoo.device.management.service.ExcelService;
import com.watsoo.device.management.util.DateUtil;
import com.watsoo.device.management.util.TokenUtility;

@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private ExcelDeviceDatabaseDeviceMappingRepository mappingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IssuedRepository issuedRepository;

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private BoxDeviceRepository boxDeviceRepository;

	@Autowired
	private BoxTransactionRepository boxTransactionRepository;

	public List<ExcelClientDTO> findNotExistClient(List<ExcelDeviceDTO> excelDeviceDTOs) {
		List<ExcelClientDTO> allExcelClient = new ArrayList<>();
		List<ExcelClientDTO> allDbClient = new ArrayList<>();
		List<StateClientDTO> allStateClient = clientRepository.findAllStateClient();
		for (StateClientDTO stateClientDTO : allStateClient) {
			ExcelClientDTO dto = new ExcelClientDTO();
			dto.setClientName(stateClientDTO.getCompanyName());
			dto.setState(stateClientDTO.getName());
			allDbClient.add(dto);
		}
		for (ExcelDeviceDTO excel : excelDeviceDTOs) {
			ExcelClientDTO dto = new ExcelClientDTO();
			dto.setClientName(excel.getClientName());
			dto.setState(excel.getState());
			allExcelClient.add(dto);
		}
		Set<ExcelClientDTO> distinctExcelClient = new HashSet<>(allExcelClient);
		List<ExcelClientDTO> differentObjects = distinctExcelClient
				.stream().filter(
						obj2 -> allDbClient.stream()
						.noneMatch(obj1 -> obj1.getClientName().equalsIgnoreCase(obj2.getClientName())
								&& obj1.getState().equalsIgnoreCase(obj2.getState())))
				.collect(Collectors.toList());

		return differentObjects;
	}

	// @Override
	// public Response<Object> readExcelDataAndSaveInDb(MultipartFile file) {
	// Response<Object> response = new Response<>();
	// List<ExcelDeviceDTO> deviceList = new ArrayList<>();
	// try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
	// XSSFSheet worksheet = workbook.getSheetAt(0);
	// for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
	// ExcelDeviceDTO device = new ExcelDeviceDTO();
	// XSSFRow row = worksheet.getRow(i);
	// device.setId((long) row.getCell(0).getNumericCellValue());
	// device.setUidNo(row.getCell(1).getStringCellValue());
	// device.setImeiNo(row.getCell(2).getStringCellValue());
	// device.setCcidNo(row.getCell(3).getStringCellValue());
	// device.setClientName(row.getCell(4).getStringCellValue());
	// device.setState(row.getCell(5).getStringCellValue());
	// device.setDeviceIssueDate(row.getCell(6).getDateCellValue());
	// device.setSim1Operator(row.getCell(7).getStringCellValue());
	// device.setSim1Number(row.getCell(8).getStringCellValue());
	// device.setSim1ActivationDate(row.getCell(9).getDateCellValue());
	// device.setSim1ExpireDate(row.getCell(10).getDateCellValue());
	// device.setSim2Operator(row.getCell(11).getStringCellValue());
	// device.setSim2Number(row.getCell(12).getStringCellValue());
	// device.setSim2ActivationDate(row.getCell(13).getDateCellValue());
	// device.setSim2ExpireDate(row.getCell(14).getDateCellValue());
	// device.setDeviceActivatedDate(row.getCell(9).getDateCellValue());
	// deviceList.add(device);
	// }
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// //
	// if (deviceList != null && deviceList.size() > 0) {
	// List<ExcelDeviceDTO> duplicates = new ArrayList<>();
	// try {
	// duplicates =
	// deviceList.stream().collect(Collectors.groupingBy(ExcelDeviceDTO::getImeiNo)).entrySet()
	// .stream().filter(e -> e.getValue().size() > 1).flatMap(e ->
	// e.getValue().stream())
	// .collect(Collectors.toList());
	// } catch (Exception e2) {
	// e2.printStackTrace();
	// }
	// //
	// if (duplicates != null && duplicates.size() > 0) {
	// response.setData(duplicates);
	// response.setMessage("Duplicate IMEI Found");
	// response.setResponseCode(HttpStatus.EXPECTATION_FAILED.value());
	// } else {
	// List<ExcelClientDTO> notExistClient = findNotExistClient(deviceList);
	// if (notExistClient != null && notExistClient.size() > 0) {
	// response.setData(notExistClient);
	// response.setMessage("Client Not Found");
	// response.setResponseCode(HttpStatus.EXPECTATION_FAILED.value());
	// } else {
	// // check imei
	// List<Device> dbDevices = deviceRepository.findAll();
	// List<State> allStates = stateRepository.findAll();
	// List<Client> allClients = clientRepository.findAll();
	// //
	// for (ExcelDeviceDTO excel : deviceList) {
	//
	// Device device = dbDevices.stream()
	// .filter(d -> d.getImeiNo().equalsIgnoreCase(excel.getImeiNo())).findFirst()
	// .orElse(null);
	// State state = allStates.stream().filter(obj ->
	// obj.getName().equals(excel.getState()))
	// .findFirst().orElse(null);
	// Client client = allClients.stream()
	// .filter(obj ->
	// obj.getCompanyName().equals(excel.getClientName())).findFirst()
	// .orElse(null);
	// if (device != null) {
	//
	// // check not null
	// if (device.getIssueDate() != null && device.getDeviceActivatedDate() != null
	// && device.getSim1ActivationDate() != null && device.getSim2ActivationDate()
	// != null
	// && device.getClientName() != null && device.getState().getName() != null
	// && device.getIccidNo() != null && device.getUuidNo() != null
	// && device.getSim1Number() != null && device.getSim2Number() != null) {
	// boolean areEqualIssueDates = DateUtil.areDatesEqual(device.getIssueDate(),
	// excel.getDeviceIssueDate());
	// boolean areEqualActivatedDates =
	// DateUtil.areDatesEqual(device.getDeviceActivatedDate(),
	// excel.getDeviceActivatedDate());
	// boolean areEqualSim1Dates =
	// DateUtil.areDatesEqual(device.getSim1ActivationDate(),
	// excel.getSim1ActivationDate());
	// boolean areEqualSim2Dates =
	// DateUtil.areDatesEqual(device.getSim2ActivationDate(),
	// excel.getSim2ActivationDate());
	// boolean isSameClient = device.getClientNames().equals(excel.getClientName());
	// boolean isSameState =
	// device.getState().getName().equalsIgnoreCase(excel.getState());
	// boolean isSameUid = device.getUuidNo().equalsIgnoreCase(excel.getUidNo());
	// boolean isSameCcid = device.getIccidNo().equalsIgnoreCase(excel.getCcidNo());
	// boolean isSameSim1 =
	// device.getSim1Number().equalsIgnoreCase(excel.getSim1Number());
	// boolean isSameSim2 =
	// device.getSim2Number().equalsIgnoreCase(excel.getSim2Number());
	// boolean isSameSim1operator = device.getSim1Operator()
	// .equalsIgnoreCase(excel.getSim1Operator());
	// // compare
	// if (areEqualIssueDates && areEqualActivatedDates && areEqualSim1Dates
	// && areEqualSim2Dates && isSameClient && isSameState && isSameUid &&
	// isSameCcid
	// && isSameSim1 && isSameSim2 && isSameSim1operator) {
	// // Don't save in device,add new data in excelmapping
	// ExcelDeviceDatabaseDeviceMapping deviceMapping = convertToEntity(device,
	// null,
	// null);
	// mappingRepository.save(deviceMapping);
	//
	// } else {
	// // update device ,add new old data in excelmapping
	// ExcelDeviceDatabaseDeviceMapping deviceMapping = convertToEntity(device,
	// excel,
	// state);
	// mappingRepository.save(deviceMapping);
	// // update device
	// device.setClientNames(excel.getClientName());
	// device.setClientId(client.getId());
	// device.setUuidNo(excel.getUidNo());
	// device.setStatus(StatusMaster.ISSUED_DEVICES);
	// device.setState(state);
	// device.setSim2Number(excel.getSim2Number());
	// device.setSim2ActivationDate(excel.getSim2ActivationDate());
	// device.setSim1Number(excel.getSim1Number());
	// device.setSim1ActivationDate(excel.getSim1ActivationDate());
	// device.setIssueDate(excel.getDeviceIssueDate());
	// device.setIsConfigurationComplete(true);
	// // device2.setIsConfigActivated(true);
	// device.setImeiNo(excel.getImeiNo());
	// device.setConfigDoneDate(excel.getDeviceActivatedDate());
	// device.setDeviceActivatedDate(excel.getDeviceActivatedDate());
	// device.setIccidNo(excel.getCcidNo());
	// device.setSim1Operator(excel.getSim1Operator());
	// device.setSim2Operator(excel.getSim2Operator());
	// device.setSim1ExpiryDate(excel.getSim1ExpireDate());
	// device.setSim2ExpiryDate(excel.getSim2ExpireDate());
	//
	// deviceRepository.save(device);
	// }
	// } else {
	// // update device ,add new old data in excelmapping
	// ExcelDeviceDatabaseDeviceMapping deviceMapping = convertToEntity(device,
	// excel, state);
	// mappingRepository.save(deviceMapping);
	// // update device
	// device.setClientNames(excel.getClientName());
	// device.setClientId(client.getId());
	// device.setUuidNo(excel.getUidNo());
	// device.setStatus(StatusMaster.ISSUED_DEVICES);
	// device.setState(state);
	// device.setSim2Number(excel.getSim2Number());
	// device.setSim2ActivationDate(excel.getSim2ActivationDate());
	// device.setSim1Number(excel.getSim1Number());
	// device.setSim1ActivationDate(excel.getSim1ActivationDate());
	// device.setIssueDate(excel.getDeviceIssueDate());
	// device.setIsConfigurationComplete(true);
	// // device2.setIsConfigActivated(true);
	// device.setImeiNo(excel.getImeiNo());
	// device.setConfigDoneDate(excel.getDeviceActivatedDate());
	// device.setDeviceActivatedDate(excel.getDeviceActivatedDate());
	// device.setIccidNo(excel.getCcidNo());
	// device.setSim1Operator(excel.getSim1Operator());
	// device.setSim2Operator(excel.getSim2Operator());
	// device.setSim1ExpiryDate(excel.getSim1ExpireDate());
	// device.setSim2ExpiryDate(excel.getSim2ExpireDate());
	// deviceRepository.save(device);
	// }
	// } else {
	// // add new in device and new data in excelmapping
	// ExcelDeviceDatabaseDeviceMapping deviceMapping = convertToEntity(null, excel,
	// state);
	// mappingRepository.save(deviceMapping);
	// // update device
	// Device device2 = new Device();
	// device2.setClientNames(excel.getClientName());
	// device2.setClientId(client.getId());
	// device2.setUuidNo(excel.getUidNo());
	// device2.setStatus(StatusMaster.ISSUED_DEVICES);
	// device2.setState(state);
	// device2.setSim2Number(excel.getSim2Number());
	// device2.setSim2ActivationDate(excel.getSim2ActivationDate());
	// device2.setSim1Number(excel.getSim1Number());
	// device2.setSim1ActivationDate(excel.getSim1ActivationDate());
	// device2.setIssueDate(excel.getDeviceIssueDate());
	// device2.setIsConfigurationComplete(true);
	// // device2.setIsConfigActivated(true);
	// device2.setImeiNo(excel.getImeiNo());
	// device2.setConfigDoneDate(excel.getDeviceActivatedDate());
	// device2.setDeviceActivatedDate(excel.getDeviceActivatedDate());
	// device2.setIccidNo(excel.getCcidNo());
	// device2.setSim1Operator(excel.getSim1Operator());
	// device2.setSim2Operator(excel.getSim2Operator());
	// device2.setSim1ExpiryDate(excel.getSim1ExpireDate());
	// device2.setSim2ExpiryDate(excel.getSim2ExpireDate());
	// deviceRepository.save(device2);
	// }
	// }
	// response.setData(null);
	// response.setMessage(HttpStatus.OK.getReasonPhrase());
	// response.setResponseCode(HttpStatus.OK.value());
	// }
	// }
	// }
	// return response;
	// }
	//
	// public ExcelDeviceDatabaseDeviceMapping convertToEntity(Device device,
	// ExcelDeviceDTO dto, State state) {
	// return new ExcelDeviceDatabaseDeviceMapping(null, (dto != null) ?
	// dto.getImeiNo() : null,
	// (dto != null) ? dto.getCcidNo() : null, (dto != null) ? dto.getUidNo() :
	// null,
	// (state != null) ? state : null, (dto != null) ? dto.getDeviceIssueDate() :
	// null,
	// (dto != null) ? dto.getDeviceActivatedDate() : null, (dto != null) ?
	// dto.getSim1Number() : null,
	// (dto != null) ? dto.getSim2Number() : null, (dto != null) ?
	// dto.getSim1ActivationDate() : null,
	// (dto != null) ? dto.getSim2ActivationDate() : null, (dto != null) ?
	// dto.getClientName() : null,
	// (device != null) ? device.getImeiNo() : null, (device != null) ?
	// device.getIccidNo() : null,
	// (device != null) ? device.getUuidNo() : null, (device != null) ?
	// device.getState() : null,
	// (device != null) ? device.getIssueDate() : null,
	// (device != null) ? device.getDeviceActivatedDate() : null,
	// (device != null) ? device.getSim1Number() : null, (device != null) ?
	// device.getSim2Number() : null,
	// (device != null) ? device.getSim1ActivationDate() : null,
	// (device != null) ? device.getSim2ActivationDate() : null,
	// (device != null) ? device.getClientName() : null);
	// }
	//
	@Override
	public Map<Integer, String> importUpdatedVehicleExcelFile(MultipartFile file) {
		Map<Integer, String> unsuccessList = new HashMap<>();
		try {
			List<Device> vehicleList = deviceRepository.findAll();
			List<Device> vehicles = new ArrayList<>();
			// List<VehicleLazyEntity> vehicleList = vehicleLazyRepo.findAll();
			// List<VehicleLazyEntity> vehicles = new ArrayList<>();
			DataFormatter formatter = new DataFormatter();
			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Integer rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			// SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				} else {
					rowNumber++;
				}

				ExcelVehicleDTO excelVehicleDTO = new ExcelVehicleDTO();
				Device vehicleLazyEntity = null;
				int cellId = 0;
				Boolean isValid = true;
				String reason = null;
				for (int i = 0; i <= 15; i++) {
					// Cell cell = cellIterator.next();
					Cell cell = row.getCell(i);
					try {
						if (row.getCell(cellId) == null || row.getCell(cellId).getCellTypeEnum() == CellType.BLANK) {

						} else {
							switch (cellId) {
							case 0:
								// Serial Number
								// if (cell.getCellTypeEnum() == CellType.NUMERIC) {
								// String slNumber = formatter.formatCellValue(cell);
								// Long id = Long.parseLong(slNumber);
								// if (slNumber != null && !slNumber.isEmpty()) {
								// excelVehicleDTO.setId(id);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid serial number";
								//
								// }
								//
								// } else {
								// isValid = false;
								// reason = reason + " " + "serial number";
								// }
								break;
							case 1:
								// uid
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String uid = formatter.formatCellValue(cell);
								// if (uid != null && !uid.isEmpty()) {
								// excelVehicleDTO.setUidNo(uid);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid uid";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid uid";
								// }

								break;
							case 2:
								// imei
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String imei = formatter.formatCellValue(cell);
									if (imei != null && !imei.isEmpty()) {
										excelVehicleDTO.setImeiNo(imei);
										// System.out.println(excelVehicleDTO.getImeiNo()+" hello");
										Optional<Device> vehicleFlag = vehicleList.stream()
												.filter(x -> x.getImeiNo() != null && x.getImeiNo() != ""
												&& x.getImeiNo().equalsIgnoreCase(excelVehicleDTO.getImeiNo()))
												.findAny();

										if (vehicleFlag.isPresent()) {
											vehicleLazyEntity = vehicleFlag.get();
											excelVehicleDTO.setImeiNo(imei);
										} else {
											isValid = false;
											reason = reason + " " + "invalid imei";
										}

									} else {
										isValid = false;
										reason = reason + " " + "invalid imei";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid imei";
								}
								break;
							case 3:
								// ccid
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String ccid = formatter.formatCellValue(cell);
								// if (ccid != null && !ccid.isEmpty()) {
								// excelVehicleDTO.setCcidNo(ccid);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid ccid";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid ccid";
								// }
								break;
							case 4:
								// Client Name
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String clientName = formatter.formatCellValue(cell);
								// if (clientName != null && !clientName.isEmpty()) {
								// excelVehicleDTO.setClientName(clientName);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid Client Name";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid Client Name";
								// }
								break;
							case 5:
								// state
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String state = formatter.formatCellValue(cell);
								// if (state != null && !state.isEmpty()) {
								// excelVehicleDTO.setState(state);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid state";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid state";
								// }
								break;
							case 6:
								// date
								// if (cell.getCellTypeEnum() == CellType.NUMERIC) {
								// String date = formatter.formatCellValue(cell);
								//
								// if (date != null && !date.isEmpty()) {
								// Date date1 = null;
								//
								// try {
								// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								// date1 = dateFormat.parse(date);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// if (date1 != null) {
								// excelVehicleDTO.setDeviceIssueDate(date1);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid date";
								// }
								//
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid date";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid date";
								// }
								break;
							case 7:
								// sellPerson
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String sellPerson = formatter.formatCellValue(cell);
								// if (sellPerson != null && !sellPerson.isEmpty()) {
								// excelVehicleDTO.setSellPerson(sellPerson);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sellPerson";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sellPerson";
								// }
								break;
							case 8:
								// sim1
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim1 = formatter.formatCellValue(cell);
									if (sim1 != null && !sim1.isEmpty() && !sim1.equals("#N/A")) {
										excelVehicleDTO.setSim1Number(sim1);
									} else {
										isValid = false;
										reason = reason + " " + "invalid sim1";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim1";
								}
								break;
							case 9:
								// sim2
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim2 = formatter.formatCellValue(cell);
									if (sim2 != null && !sim2.isEmpty() && !sim2.equals("#N/A")) {
										excelVehicleDTO.setSim2Number(sim2);
									} else {
										isValid = false;
										reason = reason + " " + "invalid sim2";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim2";
								}
								break;
							case 10:
								// sim provider
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String provider = formatter.formatCellValue(cell);
									if (provider != null && !provider.isEmpty() && !provider.equals("#N/A")) {
										excelVehicleDTO.setSimProvider(provider);
									} else {
										isValid = false;
										reason = reason + " " + "invalid sim provider";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim provider";
								}
								break;
							case 11:
								// sim1 provider
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim1Provider = formatter.formatCellValue(cell);
									if (sim1Provider != null && !sim1Provider.isEmpty()
											&& !sim1Provider.equals("#N/A")) {
										excelVehicleDTO.setSim1Operator(sim1Provider);
									} else {
										isValid = false;
										reason = reason + " " + "invalid sim1 Provider";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim1 provider";
								}
								break;
							case 12:
								// sim2 provider
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim2Provider = formatter.formatCellValue(cell);
									if (sim2Provider != null && !sim2Provider.isEmpty()
											&& !sim2Provider.equals("#N/A")) {
										excelVehicleDTO.setSim2Operator(sim2Provider);
									} else {
										isValid = false;
										reason = reason + " " + "invalid sim2 Provider";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim2 provider";
								}
								break;
							case 13:
								// sim activation date
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String simActivationDate = formatter.formatCellValue(cell);
									String outputDateStr = null;
									if (simActivationDate != null && !simActivationDate.isEmpty()) {
										Date date1 = null;
										try {
											LocalDateTime inputDate = LocalDateTime.parse(simActivationDate,
													DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
											outputDateStr = inputDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
										} catch (Exception e) {
											e.printStackTrace();
										}

										if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
											try {
												LocalDate date = LocalDate.parse(simActivationDate);
												DateTimeFormatter outputFormatter = DateTimeFormatter
														.ofPattern("dd/MM/yyyy");
												outputDateStr = date.format(outputFormatter);
											} catch (Exception e) {
												e.printStackTrace();
											}
											if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
												try {
													SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
													SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
													Date date = inputFormat.parse(simActivationDate);
													outputDateStr = outputFormat.format(date);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
											if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
												try {
													SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
													SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
													Date date = inputFormat.parse(simActivationDate);
													outputDateStr = outputFormat.format(date);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
											if (outputDateStr != null && !outputDateStr.isEmpty()) {
												try {
													SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
													date1 = dateFormat.parse(outputDateStr);
												} catch (Exception e) {
													e.printStackTrace();
												}
											} else {
												// excelVehicleDTO.setDeviceActivationDate(null);
												isValid = false;
												reason = reason + " " + "invalid activation date";
											}
										} else {
											try {
												SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
												date1 = dateFormat.parse(outputDateStr);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										if (date1 != null) {
											excelVehicleDTO.setDeviceActivationDate(date1);
										} else {
											isValid = false;
											reason = reason + " " + "invalid activation date";
											// excelVehicleDTO.setDeviceActivationDate(date1);
										}
									} else {
										if (excelVehicleDTO.getSimProvider().equalsIgnoreCase("Intellia")) {

										} else {
											isValid = false;
											reason = reason + " " + "invalid activation date";
											// excelVehicleDTO.setDeviceActivationDate(null);
										}

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid activation date";
								}
								break;
							case 14:
								// sim expiry date
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String simExpiryDate = formatter.formatCellValue(cell);
									String outputDateStr = null;
									if (simExpiryDate != null && !simExpiryDate.isEmpty()) {
										Date date1 = null;
										try {
											LocalDateTime inputDate = LocalDateTime.parse(simExpiryDate,
													DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
											outputDateStr = inputDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
										} catch (Exception e) {
											e.printStackTrace();
										}

										if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
											try {
												LocalDate date = LocalDate.parse(simExpiryDate);
												DateTimeFormatter outputFormatter = DateTimeFormatter
														.ofPattern("dd/MM/yyyy");
												outputDateStr = date.format(outputFormatter);
											} catch (Exception e) {
												e.printStackTrace();
											}
											if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
												try {
													SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
													SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
													Date date = inputFormat.parse(simExpiryDate);
													outputDateStr = outputFormat.format(date);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
											if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
												try {
													SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
													SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
													Date date = inputFormat.parse(simExpiryDate);
													outputDateStr = outputFormat.format(date);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
											if (outputDateStr != null && !outputDateStr.isEmpty()) {
												try {
													SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
													date1 = dateFormat.parse(outputDateStr);
												} catch (Exception e) {
													e.printStackTrace();
												}
											} else {
												isValid = false;
												reason = reason + " " + "invalid expiry date";
												// excelVehicleDTO.setDeviceExpiryDate(null);
											}
										} else {
											try {
												SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
												date1 = dateFormat.parse(outputDateStr);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										if (date1 != null) {
											excelVehicleDTO.setDeviceExpiryDate(date1);
											// set activation date
											// Calendar calendar = Calendar.getInstance();
											// calendar.setTime(date1);
											// calendar.add(Calendar.YEAR, -1);
											// Date activationDate = calendar.getTime();
											// excelVehicleDTO.setDeviceActivationDate(activationDate);
										} else {
											isValid = false;
											reason = reason + " " + "invalid expiry date";
											// excelVehicleDTO.setDeviceActivationDate(date1);
										}
									} else {
										isValid = false;
										reason = reason + " " + "invalid expiry date";
										// excelVehicleDTO.setDeviceExpiryDate(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid expiry date";
								}
								break;
							case 15:
								break;
							default:
								break;
							}
						}
						cellId++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (vehicleLazyEntity == null) {
					isValid = false;
					reason = "no vehicle found";
				}
				if (isValid) {
					vehicleLazyEntity.setSim1Operator(excelVehicleDTO.getSim1Operator());
					vehicleLazyEntity.setSim2Operator(excelVehicleDTO.getSim2Operator());
					vehicleLazyEntity.setSim1ActivationDate(excelVehicleDTO.getDeviceActivationDate());
					vehicleLazyEntity.setSim2ActivationDate(excelVehicleDTO.getDeviceActivationDate());
					vehicleLazyEntity.setSim1ExpiryDate(excelVehicleDTO.getDeviceExpiryDate());
					vehicleLazyEntity.setSim2ExpiryDate(excelVehicleDTO.getDeviceExpiryDate());
					vehicleLazyEntity.setSim1Provider(excelVehicleDTO.getSimProvider());
					vehicleLazyEntity.setSim2Provider(excelVehicleDTO.getSimProvider());
					vehicleLazyEntity.setSim1Number(excelVehicleDTO.getSim1Number());
					vehicleLazyEntity.setSim2Number(excelVehicleDTO.getSim2Number());
					// vehicleLazyEntity.setIssueDate(excelVehicleDTO.getDeviceIssueDate());
					vehicles.add(vehicleLazyEntity);
				} else {
					unsuccessList.put(rowNumber, reason);
				}
			}
			workbook.close();
			deviceRepository.saveAll(vehicles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unsuccessList;
	}

	@Override
	public Map<Integer, String> createDeviceFromExcelFile(MultipartFile file) {
		Map<Integer, String> unsuccessList = new HashMap<>();
		try {
			Long userId = 1l;
			List<Device> vehicleList = deviceRepository.findAll();
			List<State> stateList = stateRepository.findAll();
			List<Client> clientList = clientRepository.findAll();
			Optional<User> user = userRepository.findById(userId);
			List<Device> vehicles = new ArrayList<>();
			DataFormatter formatter = new DataFormatter();
			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Integer rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				} else {
					rowNumber++;
				}

				ExcelVehicleDTO excelVehicleDTO = new ExcelVehicleDTO();
				// Device vehicleLazyEntity = null;
				int cellId = 0;
				Boolean isValid = true;
				String reason = null;
				for (int i = 0; i <= 15; i++) {
					Cell cell = row.getCell(i);
					try {
						if (row.getCell(cellId) == null || row.getCell(cellId).getCellTypeEnum() == CellType.BLANK) {

						} else {
							switch (cellId) {
							case 0:
								// Serial Number
								// if (cell.getCellTypeEnum() == CellType.NUMERIC) {
								// String slNumber = formatter.formatCellValue(cell);
								// Long id = Long.parseLong(slNumber);
								// if (slNumber != null && !slNumber.isEmpty()) {
								// excelVehicleDTO.setId(id);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid serial number";
								//
								// }
								//
								// } else {
								// isValid = false;
								// reason = reason + " " + "serial number";
								// }
								break;
							case 1:
								// uid
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String uid = formatter.formatCellValue(cell);
									if (uid != null && !uid.isEmpty()) {
										excelVehicleDTO.setUidNo(uid);
									} else {
										isValid = false;
										reason = reason + " " + "invalid uid";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid uid Column";
								}

								break;
							case 2:
								// imei
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String imei = formatter.formatCellValue(cell);
									if (imei != null && !imei.isEmpty()) {
										excelVehicleDTO.setImeiNo(imei);
										// Optional<Device> vehicleFlag = vehicleList.stream()
										// .filter(x -> x.getImeiNo() != null && x.getImeiNo() != ""
										// && x.getImeiNo().equalsIgnoreCase(excelVehicleDTO.getImeiNo()))
										// .findAny();
										//
										// if (vehicleFlag.isPresent()) {
										// vehicleLazyEntity = vehicleFlag.get();
										// excelVehicleDTO.setImeiNo(imei);
										// } else {
										// isValid = false;
										// reason = reason + " " + "invalid imei";
										// }

									} else {
										isValid = false;
										reason = reason + " " + "invalid imei ";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid imei Column";
								}
								break;
							case 3:
								// ccid
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String ccid = formatter.formatCellValue(cell);
									if (ccid != null && !ccid.isEmpty()) {
										excelVehicleDTO.setCcidNo(ccid);
									} else {
										isValid = false;
										reason = reason + " " + "invalid ccid";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid ccid Column";
								}
								break;
							case 4:
								// Client Name
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String clientName = formatter.formatCellValue(cell).trim();
									Long clientsId = 0l;
									if (clientName != null && !clientName.isEmpty()) {
										try {
											clientsId = Long.parseLong(clientName);
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (clientsId != null && clientsId != 0l) {
											Long id = clientsId;
											Optional<Client> result = clientList.stream()
													.filter(obj -> obj.getId().equals(id)).findFirst();
											if (result.isPresent()) {
												excelVehicleDTO.setClientName(result.get().getCompanyName());
												excelVehicleDTO.setClientId(id);
											} else {
												isValid = false;
												reason = reason + " " + "invalid Client";
											}

										} else {
											isValid = false;
											reason = reason + " " + "invalid Client";
										}
										// excelVehicleDTO.setClientName(clientName);
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid Client Name";
										excelVehicleDTO.setClientName(null);
										excelVehicleDTO.setClientId(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid Client Column";
								}
								break;
							case 5:
								// state
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String state = formatter.formatCellValue(cell).trim();
									Long stateId = 0l;
									if (state != null && !state.isEmpty()) {
										// excelVehicleDTO.setState(state);
										try {
											stateId = Long.parseLong(state);
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (stateId != null && stateId != 0l) {
											Long id = stateId;
											Optional<State> result = stateList.stream()
													.filter(obj -> obj.getId().equals(id)).findFirst();
											if (result.isPresent()) {
												excelVehicleDTO.setStateId(result.get());
											} else {
												isValid = false;
												reason = reason + " " + "invalid State";
											}

										} else {
											isValid = false;
											reason = reason + " " + "invalid State";
										}
									} else {
										isValid = false;
										reason = reason + " " + "invalid state";
										// excelVehicleDTO.setStateId(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid state Column";
								}
								break;
							case 6:
								// date
								if (cell.getCellTypeEnum() == CellType.NUMERIC) {
									String date = formatter.formatCellValue(cell);

									if (date != null && !date.isEmpty()) {
										Date date1 = null;

										try {
											SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
											date1 = dateFormat.parse(date);
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (date1 != null) {
											excelVehicleDTO.setDeviceIssueDate(date1);
										} else {
											// isValid = false;
											// reason = reason + " " + "invalid date";
											excelVehicleDTO.setDeviceIssueDate(null);
										}

									} else {
										// isValid = false;
										// reason = reason + " " + "invalid date";
										excelVehicleDTO.setDeviceIssueDate(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid date Column";
								}
								break;
							case 7:
								// sellPerson
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String sellPerson = formatter.formatCellValue(cell);
								// if (sellPerson != null && !sellPerson.isEmpty()) {
								// excelVehicleDTO.setSellPerson(sellPerson);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sellPerson";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sellPerson";
								// }
								break;
							case 8:
								// sim1
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim1 = formatter.formatCellValue(cell);
									if (sim1 != null && !sim1.isEmpty() && !sim1.equals("#N/A")) {
										excelVehicleDTO.setSim1Number(sim1);
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid sim1";
										excelVehicleDTO.setSim1Number(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim1 Column";
								}
								break;
							case 9:
								// sim2
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim2 = formatter.formatCellValue(cell);
									if (sim2 != null && !sim2.isEmpty() && !sim2.equals("#N/A")) {
										excelVehicleDTO.setSim2Number(sim2);
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid sim2";
										excelVehicleDTO.setSim2Number(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim2 Column";
								}
								break;
							case 10:
								// sim provider
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String provider = formatter.formatCellValue(cell);
									if (provider != null && !provider.isEmpty() && !provider.equals("#N/A")) {
										excelVehicleDTO.setSimProvider(provider);
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid sim provider";
										excelVehicleDTO.setSimProvider(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim provider Column";
								}
								break;
							case 11:
								// sim1 provider
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim1Provider = formatter.formatCellValue(cell);
									if (sim1Provider != null && !sim1Provider.isEmpty()
											&& !sim1Provider.equals("#N/A")) {
										excelVehicleDTO.setSim1Operator(sim1Provider);
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid sim1 Provider";
										excelVehicleDTO.setSim1Operator(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim1 provider Column";
								}
								break;
							case 12:
								// sim2 provider
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim2Provider = formatter.formatCellValue(cell);
									if (sim2Provider != null && !sim2Provider.isEmpty()
											&& !sim2Provider.equals("#N/A")) {
										excelVehicleDTO.setSim2Operator(sim2Provider);
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid sim2 Provider";
										excelVehicleDTO.setSim2Operator(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim2 provider Column";
								}
								break;
							case 13:
								// sim activation date
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String simActivationDate = formatter.formatCellValue(cell);
									String outputDateStr = null;
									if (simActivationDate != null && !simActivationDate.isEmpty()) {
										Date date1 = null;
										try {
											LocalDateTime inputDate = LocalDateTime.parse(simActivationDate,
													DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
											outputDateStr = inputDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
										} catch (Exception e) {
											e.printStackTrace();
										}

										if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
											try {
												LocalDate date = LocalDate.parse(simActivationDate);
												DateTimeFormatter outputFormatter = DateTimeFormatter
														.ofPattern("dd/MM/yyyy");
												outputDateStr = date.format(outputFormatter);
											} catch (Exception e) {
												e.printStackTrace();
											}
											if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
												try {
													SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
													SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
													Date date = inputFormat.parse(simActivationDate);
													outputDateStr = outputFormat.format(date);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
											if (outputDateStr != null && !outputDateStr.isEmpty()) {
												try {
													SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
													date1 = dateFormat.parse(outputDateStr);
												} catch (Exception e) {
													e.printStackTrace();
												}
											} else {
												excelVehicleDTO.setDeviceActivationDate(null);
											}
										} else {
											try {
												SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
												date1 = dateFormat.parse(outputDateStr);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										if (date1 != null) {
											excelVehicleDTO.setDeviceActivationDate(date1);
										} else {
											// isValid = false;
											// reason = reason + " " + "invalid activation date";
											excelVehicleDTO.setDeviceActivationDate(null);
										}
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid activation date";
										excelVehicleDTO.setDeviceActivationDate(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid activation date Column";
								}
								break;
							case 14:
								// sim expiry date
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String simExpiryDate = formatter.formatCellValue(cell);
									String outputDateStr = null;
									if (simExpiryDate != null && !simExpiryDate.isEmpty()) {
										Date date1 = null;
										try {
											LocalDateTime inputDate = LocalDateTime.parse(simExpiryDate,
													DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
											outputDateStr = inputDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
										} catch (Exception e) {
											e.printStackTrace();
										}

										if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
											try {
												LocalDate date = LocalDate.parse(simExpiryDate);
												DateTimeFormatter outputFormatter = DateTimeFormatter
														.ofPattern("dd/MM/yyyy");
												outputDateStr = date.format(outputFormatter);
											} catch (Exception e) {
												e.printStackTrace();
											}
											if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
												try {
													SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
													SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
													Date date = inputFormat.parse(simExpiryDate);
													outputDateStr = outputFormat.format(date);
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
											if (outputDateStr != null && !outputDateStr.isEmpty()) {
												try {
													SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
													date1 = dateFormat.parse(outputDateStr);
												} catch (Exception e) {
													e.printStackTrace();
												}
											} else {
												// isValid = false;
												// reason = reason + " " + "invalid expiry date";
												excelVehicleDTO.setDeviceExpiryDate(null);
											}
										} else {
											try {
												SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
												date1 = dateFormat.parse(outputDateStr);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										if (date1 != null) {
											excelVehicleDTO.setDeviceExpiryDate(date1);
											// set activation date
											if (excelVehicleDTO.getDeviceActivationDate() == null) {
												// Calendar calendar = Calendar.getInstance();
												// calendar.setTime(date1);
												// calendar.add(Calendar.YEAR, -1);
												// Date activationDate = calendar.getTime();
												// excelVehicleDTO.setDeviceActivationDate(activationDate);
											}

										} else {
											// isValid = false;
											// reason = reason + " " + "invalid expiry date";
											excelVehicleDTO.setDeviceActivationDate(null);
										}
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid expiry date";
										excelVehicleDTO.setDeviceExpiryDate(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid expiry date Column";
								}
								break;
							case 15:
								break;
							default:
								break;
							}
						}
						cellId++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// if (vehicleLazyEntity == null) {
				// isValid = false;
				// reason = "no vehicle found";
				// }
				if (isValid) {

					List<Device> vehiclesList = vehicleList.stream()
							.filter(x -> x.getImeiNo() != null && x.getImeiNo() != "" && x.getIccidNo() != null
							&& x.getIccidNo() != "" && x.getUuidNo() != null && x.getUuidNo() != ""
							&& (x.getImeiNo().equalsIgnoreCase(excelVehicleDTO.getImeiNo())
									|| x.getIccidNo().equalsIgnoreCase(excelVehicleDTO.getCcidNo())
									|| x.getUuidNo().equalsIgnoreCase(excelVehicleDTO.getUidNo())))
							.collect(Collectors.toList());

					if (vehiclesList != null && vehiclesList.size() > 0) {
						if (vehiclesList.size() == 1) {
							// isValid = false;
							reason = reason + " Device already exist";
							unsuccessList.put(rowNumber, reason);
						} else {
							// isValid = false;
							reason = reason + " " + "invalid imei" + " " + "invalid uid" + " " + "invalid ccid";
							unsuccessList.put(rowNumber, reason);
						}
					} else {

						Device vehicleLazyEntity = new Device();
						vehicleLazyEntity.setIccidNo(excelVehicleDTO.getCcidNo());
						vehicleLazyEntity.setImeiNo(excelVehicleDTO.getImeiNo());
						vehicleLazyEntity.setUuidNo(excelVehicleDTO.getUidNo());
						vehicleLazyEntity.setSim1Operator(excelVehicleDTO.getSim1Operator());
						vehicleLazyEntity.setSim2Operator(excelVehicleDTO.getSim2Operator());
						vehicleLazyEntity.setSim1ActivationDate(excelVehicleDTO.getDeviceActivationDate());
						vehicleLazyEntity.setSim2ActivationDate(excelVehicleDTO.getDeviceActivationDate());
						vehicleLazyEntity.setSim1ExpiryDate(excelVehicleDTO.getDeviceExpiryDate());
						vehicleLazyEntity.setSim2ExpiryDate(excelVehicleDTO.getDeviceExpiryDate());
						vehicleLazyEntity.setSim1Provider(excelVehicleDTO.getSimProvider());
						vehicleLazyEntity.setSim2Provider(excelVehicleDTO.getSimProvider());
						vehicleLazyEntity.setSim1Number(excelVehicleDTO.getSim1Number());
						vehicleLazyEntity.setSim2Number(excelVehicleDTO.getSim2Number());
						vehicleLazyEntity.setIssueDate(excelVehicleDTO.getDeviceIssueDate());
						vehicleLazyEntity.setClientId(excelVehicleDTO.getClientId());
						vehicleLazyEntity.setState(excelVehicleDTO.getStateId());
						vehicleLazyEntity.setClientNames(excelVehicleDTO.getClientName());
						vehicleLazyEntity.setClientId(excelVehicleDTO.getClientId());
						vehicleLazyEntity.setIsCreatedFromExcel(true);
						vehicleLazyEntity.setCreatedAt(new Date());
						vehicleLazyEntity.setModifiedBy(userId);
						vehicleLazyEntity.setUpdatedAt(new Date());
						vehicleLazyEntity.setCreatedBy(user.get());
						vehicleLazyEntity.setStatus(StatusMaster.ISSUED_DEVICES);
						vehicles.add(vehicleLazyEntity);
					}
				} else {
					unsuccessList.put(rowNumber, reason);
				}
			}
			workbook.close();
			deviceRepository.saveAll(vehicles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unsuccessList;
	}

	@Override
	public List<Map<Object, String>> mapClientDatabaseExcel(MultipartFile file) {
		List<Map<Object, String>> response = new ArrayList<>();
		Map<Object, String> boxPackedList = new HashMap<>();
		Map<Object, String> issuedToOtherClientList = new HashMap<>();
		Map<Object, String> invalidData = new HashMap<>();
		try {
			Long userId = 1l;
			List<Device> vehicleList = deviceRepository.findAll();
			List<State> stateList = stateRepository.findAll();
			List<Client> clientList = clientRepository.findAll();
			Optional<User> user = userRepository.findById(userId);
			List<Device> vehicles = new ArrayList<>();
			DataFormatter formatter = new DataFormatter();
			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Integer rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				} else {
					rowNumber++;
				}

				ExcelVehicleDTO excelVehicleDTO = new ExcelVehicleDTO();
				// Device vehicleLazyEntity = null;
				int cellId = 0;
				Boolean isValid = true;
				String reason = "";
				for (int i = 0; i <= 15; i++) {
					Cell cell = row.getCell(i);
					try {
						if (row.getCell(cellId) == null || row.getCell(cellId).getCellTypeEnum() == CellType.BLANK) {

						} else {
							switch (cellId) {
							case 0:
								// Serial Number
								// if (cell.getCellTypeEnum() == CellType.NUMERIC) {
								// String slNumber = formatter.formatCellValue(cell);
								// Long id = Long.parseLong(slNumber);
								// if (slNumber != null && !slNumber.isEmpty()) {
								// excelVehicleDTO.setId(id);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid serial number";
								//
								// }
								//
								// } else {
								// isValid = false;
								// reason = reason + " " + "serial number";
								// }
								break;
							case 1:
								// uid
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String uid = formatter.formatCellValue(cell);
									if (uid != null && !uid.isEmpty()) {
										excelVehicleDTO.setUidNo(uid);
									} else {
										isValid = false;
										reason = reason + " " + "invalid uid";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid uid Column";
								}

								break;
							case 2:
								// imei
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String imei = formatter.formatCellValue(cell);
									if (imei != null && !imei.isEmpty()) {
										excelVehicleDTO.setImeiNo(imei);
										// Optional<Device> vehicleFlag = vehicleList.stream()
										// .filter(x -> x.getImeiNo() != null && x.getImeiNo() != ""
										// && x.getImeiNo().equalsIgnoreCase(excelVehicleDTO.getImeiNo()))
										// .findAny();
										//
										// if (vehicleFlag.isPresent()) {
										// vehicleLazyEntity = vehicleFlag.get();
										// excelVehicleDTO.setImeiNo(imei);
										// } else {
										// isValid = false;
										// reason = reason + " " + "invalid imei";
										// }

									} else {
										isValid = false;
										reason = reason + " " + "invalid imei ";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid imei Column";
								}
								break;
							case 3:
								// ccid
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String ccid = formatter.formatCellValue(cell);
									if (ccid != null && !ccid.isEmpty()) {
										excelVehicleDTO.setCcidNo(ccid);
									} else {
										isValid = false;
										reason = reason + " " + "invalid ccid";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid ccid Column";
								}
								break;
							case 4:
								// Client Name
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String clientName = formatter.formatCellValue(cell);
									Long clientsId = 0l;
									if (clientName != null && !clientName.isEmpty()) {
										try {
											clientsId = Long.parseLong(clientName);
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (clientsId != null && clientsId != 0l) {
											Long id = clientsId;
											Optional<Client> result = clientList.stream()
													.filter(obj -> obj.getId().equals(id)).findFirst();
											if (result.isPresent()) {
												excelVehicleDTO.setClientName(result.get().getCompanyName());
												excelVehicleDTO.setClientId(id);
											} else {
												isValid = false;
												reason = reason + " " + "invalid Client";
											}

										} else {
											isValid = false;
											reason = reason + " " + "invalid Client";
										}
										// excelVehicleDTO.setClientName(clientName);
									} else {
										isValid = false;
										reason = reason + " " + "invalid Client Name";
										// excelVehicleDTO.setClientName(null);
										// excelVehicleDTO.setClientId(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid Client Column";
								}
								break;
							case 5:
								// state
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String state = formatter.formatCellValue(cell);
									Long stateId = 0l;
									if (state != null && !state.isEmpty()) {
										// excelVehicleDTO.setState(state);
										try {
											stateId = Long.parseLong(state);
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (stateId != null && stateId != 0l) {
											Long id = stateId;
											Optional<State> result = stateList.stream()
													.filter(obj -> obj.getId().equals(id)).findFirst();
											if (result.isPresent()) {
												excelVehicleDTO.setStateId(result.get());
											} else {
												isValid = false;
												reason = reason + " " + "invalid State";
											}

										} else {
											isValid = false;
											reason = reason + " " + "invalid State";
										}
									} else {
										// isValid = false;
										// reason = reason + " " + "invalid state";
										excelVehicleDTO.setStateId(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid state Column";
								}
								break;
							case 6:
								// date
								if (cell.getCellTypeEnum() == CellType.NUMERIC) {
									String date = formatter.formatCellValue(cell);

									if (date != null && !date.isEmpty()) {
										Date date1 = null;

										try {
											SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
											date1 = dateFormat.parse(date);
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (date1 != null) {
											excelVehicleDTO.setDeviceIssueDate(date1);
										} else {
											// isValid = false;
											// reason = reason + " " + "invalid date";
											excelVehicleDTO.setDeviceIssueDate(null);
										}

									} else {
										// isValid = false;
										// reason = reason + " " + "invalid date";
										excelVehicleDTO.setDeviceIssueDate(null);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid date Column";
								}
								break;
							case 7:
								// sellPerson
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String sellPerson = formatter.formatCellValue(cell);
								// if (sellPerson != null && !sellPerson.isEmpty()) {
								// excelVehicleDTO.setSellPerson(sellPerson);
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sellPerson";
								//
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sellPerson";
								// }
								break;
							case 8:
								// sim1
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String sim1 = formatter.formatCellValue(cell);
								// if (sim1 != null && !sim1.isEmpty() && !sim1.equals("#N/A")) {
								// excelVehicleDTO.setSim1Number(sim1);
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid sim1";
								// excelVehicleDTO.setSim1Number(null);
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sim1 Column";
								// }
								break;
							case 9:
								// sim2
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String sim2 = formatter.formatCellValue(cell);
								// if (sim2 != null && !sim2.isEmpty() && !sim2.equals("#N/A")) {
								// excelVehicleDTO.setSim2Number(sim2);
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid sim2";
								// excelVehicleDTO.setSim2Number(null);
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sim2 Column";
								// }
								break;
							case 10:
								// sim provider
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String provider = formatter.formatCellValue(cell);
								// if (provider != null && !provider.isEmpty() && !provider.equals("#N/A")) {
								// excelVehicleDTO.setSimProvider(provider);
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid sim provider";
								// excelVehicleDTO.setSimProvider(null);
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sim provider Column";
								// }
								break;
							case 11:
								// sim1 provider
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String sim1Provider = formatter.formatCellValue(cell);
								// if (sim1Provider != null && !sim1Provider.isEmpty()
								// && !sim1Provider.equals("#N/A")) {
								// excelVehicleDTO.setSim1Operator(sim1Provider);
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid sim1 Provider";
								// excelVehicleDTO.setSim1Operator(null);
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sim1 provider Column";
								// }
								break;
							case 12:
								// sim2 provider
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String sim2Provider = formatter.formatCellValue(cell);
								// if (sim2Provider != null && !sim2Provider.isEmpty()
								// && !sim2Provider.equals("#N/A")) {
								// excelVehicleDTO.setSim2Operator(sim2Provider);
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid sim2 Provider";
								// excelVehicleDTO.setSim2Operator(null);
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid sim2 provider Column";
								// }
								break;
							case 13:
								// sim activation date
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String simActivationDate = formatter.formatCellValue(cell);
								// String outputDateStr = null;
								// if (simActivationDate != null && !simActivationDate.isEmpty()) {
								// Date date1 = null;
								// try {
								// LocalDateTime inputDate = LocalDateTime.parse(simActivationDate,
								// DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
								// outputDateStr = inputDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								//
								// if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
								// try {
								// LocalDate date = LocalDate.parse(simActivationDate);
								// DateTimeFormatter outputFormatter = DateTimeFormatter
								// .ofPattern("dd/MM/yyyy");
								// outputDateStr = date.format(outputFormatter);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
								// try {
								// SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
								// SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
								// Date date = inputFormat.parse(simActivationDate);
								// outputDateStr = outputFormat.format(date);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// }
								// if (outputDateStr != null && !outputDateStr.isEmpty()) {
								// try {
								// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								// date1 = dateFormat.parse(outputDateStr);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// } else {
								// excelVehicleDTO.setDeviceActivationDate(null);
								// }
								// } else {
								// try {
								// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								// date1 = dateFormat.parse(outputDateStr);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// }
								// if (date1 != null) {
								// excelVehicleDTO.setDeviceActivationDate(date1);
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid activation date";
								// excelVehicleDTO.setDeviceActivationDate(null);
								// }
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid activation date";
								// excelVehicleDTO.setDeviceActivationDate(null);
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid activation date Column";
								// }
								break;
							case 14:
								// sim expiry date
								// if (cell.getCellTypeEnum() == CellType.STRING) {
								// String simExpiryDate = formatter.formatCellValue(cell);
								// String outputDateStr = null;
								// if (simExpiryDate != null && !simExpiryDate.isEmpty()) {
								// Date date1 = null;
								// try {
								// LocalDateTime inputDate = LocalDateTime.parse(simExpiryDate,
								// DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
								// outputDateStr = inputDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								//
								// if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
								// try {
								// LocalDate date = LocalDate.parse(simExpiryDate);
								// DateTimeFormatter outputFormatter = DateTimeFormatter
								// .ofPattern("dd/MM/yyyy");
								// outputDateStr = date.format(outputFormatter);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// if (outputDateStr == null || outputDateStr.trim().isEmpty()) {
								// try {
								// SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
								// SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
								// Date date = inputFormat.parse(simExpiryDate);
								// outputDateStr = outputFormat.format(date);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// }
								// if (outputDateStr != null && !outputDateStr.isEmpty()) {
								// try {
								// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								// date1 = dateFormat.parse(outputDateStr);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid expiry date";
								// excelVehicleDTO.setDeviceExpiryDate(null);
								// }
								// } else {
								// try {
								// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								// date1 = dateFormat.parse(outputDateStr);
								// } catch (Exception e) {
								// e.printStackTrace();
								// }
								// }
								// if (date1 != null) {
								// excelVehicleDTO.setDeviceExpiryDate(date1);
								// // set activation date
								// if (excelVehicleDTO.getDeviceActivationDate() == null) {
								// Calendar calendar = Calendar.getInstance();
								// calendar.setTime(date1);
								// calendar.add(Calendar.YEAR, -1);
								// Date activationDate = calendar.getTime();
								// excelVehicleDTO.setDeviceActivationDate(activationDate);
								// }
								//
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid expiry date";
								// excelVehicleDTO.setDeviceActivationDate(null);
								// }
								// } else {
								//// isValid = false;
								//// reason = reason + " " + "invalid expiry date";
								// excelVehicleDTO.setDeviceExpiryDate(null);
								// }
								// } else {
								// isValid = false;
								// reason = reason + " " + "invalid expiry date Column";
								// }
								break;
							case 15:
								break;
							default:
								break;
							}
						}
						cellId++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (isValid) {

					List<Device> vehiclesList = vehicleList.stream()
							.filter(x -> x.getImeiNo() != null && x.getImeiNo() != "" && x.getIccidNo() != null
							&& x.getIccidNo() != "" && x.getUuidNo() != null && x.getUuidNo() != ""
							&& (x.getImeiNo().equalsIgnoreCase(excelVehicleDTO.getImeiNo())
									|| x.getIccidNo().equalsIgnoreCase(excelVehicleDTO.getCcidNo())
									|| x.getUuidNo().equalsIgnoreCase(excelVehicleDTO.getUidNo())))
							.collect(Collectors.toList());

					if (vehiclesList != null && vehiclesList.size() == 1) {
						// update client

						Device existDevice = vehiclesList.get(0);

						if (existDevice.getStatus().equals(StatusMaster.DEVICE_PACKED)) {
							if (excelVehicleDTO.getClientId() != null && excelVehicleDTO.getClientName() != null
									&& excelVehicleDTO.getStateId() != null
									&& excelVehicleDTO.getDeviceIssueDate() != null) {
								existDevice.setClientId(excelVehicleDTO.getClientId());
								existDevice.setClientNames(excelVehicleDTO.getClientName());
								existDevice.setIsCreatedFromExcel(true);
								existDevice.setState(excelVehicleDTO.getStateId());
								existDevice.setIssueDate(excelVehicleDTO.getDeviceIssueDate());
								existDevice.setStatus(StatusMaster.ISSUED_DEVICES);
								vehicles.add(existDevice);
							}

						} else if (existDevice.getStatus().equals(StatusMaster.BOX_PACKED)) {
							reason = reason + " Device with IMEI no " + existDevice.getImeiNo() + " is BOX_PACKED";
							boxPackedList.put(existDevice.getImeiNo(), reason);
						} else if (existDevice.getStatus().equals(StatusMaster.ISSUED_DEVICES)) {
							// client match
							if (!existDevice.getClientId().equals(excelVehicleDTO.getClientId())) {
								reason = reason + " Device with IMEI no " + existDevice.getImeiNo()
								+ " is ISSUED to other Client";
								issuedToOtherClientList.put(existDevice.getImeiNo(), reason);
							}
						}
					} else {
						invalidData.put(rowNumber, "multiple device found");
					}
				}
			}
			response.add(invalidData);
			response.add(issuedToOtherClientList);
			response.add(boxPackedList);
			workbook.close();
			deviceRepository.saveAll(vehicles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Object createBoxFromExcel() {
		// Response<Object> response = new Response<>();
		Long userId = 7l;
		Optional<User> user = userRepository.findById(userId);
		List<Client> allClient = clientRepository.findAll();
		// List<State> allState = stateRepository.findAll();
		// get all device of excel created type
		List<Device> excelDevice = deviceRepository.findAllDeviceCreatedFromExcel();
		Map<Long, List<Device>> clientDeviceMap = excelDevice.stream().filter(x -> x.getClientId() != null)
				.collect(Collectors.groupingBy(x -> x.getClientId()));
		List<Device> deviceList = new ArrayList<>();
		for (Map.Entry<Long, List<Device>> clientDevice : clientDeviceMap.entrySet()) {
			// issue box
			Optional<Client> client = allClient.stream()
					.filter(x -> x.getId() != null && x.getId().equals(clientDevice.getKey())).findFirst();
			IssuedList issuedList = new IssuedList();
			issuedList.setClient(client.get());
			issuedList.setCreatedAt(new Date());
			issuedList.setCreatedBy(userId);
			issuedList.setState(clientDevice.getValue().get(0).getState());
			issuedList.setIssuedDate(new Date());
			SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String timestamp = timestampFormat.format(new Date());
			String issueSlipNumber = generateIssueSlipNumber(issuedList.getClient().getCompanyName(),
					issuedList.getState().getId(), timestamp);
			issuedList.setIssueSlipNumber(issueSlipNumber);
			issuedList.setQuantity((long) clientDevice.getValue().size());
			IssuedList issue = issuedRepository.save(issuedList);
			// create box
			Box box = new Box();
			box.setCreatedAt(new Date());
			box.setCreatedBy(user.get());
			box.setBoxNo(TokenUtility.getBoxNextNumber(clientDevice.getValue().get(0).getState().getId()));
			box.setCurrentQuantity((double) clientDevice.getValue().size());
			box.setIsActive(true);
			box.setQuantity((double) clientDevice.getValue().size());
			box.setState(clientDevice.getValue().get(0).getState());
			box.setStatus(StatusMaster.BOX_DISPATCH_FULLY);
			box.setIssuedList(issue);
			Box savedBox = boxRepository.save(box);
			// box transaction
			BoxTransaction boxTransaction = new BoxTransaction();
			boxTransaction.setBox(savedBox);
			boxTransaction.setCreatedAt(new Date());
			boxTransaction.setCreatedBy(user.get());
			boxTransaction.setIsActive(true);
			boxTransaction.setQuantity((double) clientDevice.getValue().size());
			BoxTransaction savedBoxTransaction = boxTransactionRepository.save(boxTransaction);
			// map box-device
			List<BoxDevice> boxDevices = new ArrayList<>();
			for (Device device : clientDevice.getValue()) {
				BoxDevice boxDevice = new BoxDevice();
				boxDevice.setBox(savedBox);
				boxDevice.setDevice(device);
				boxDevice.setEntryTransactionId(savedBoxTransaction);
				boxDevice.setIsActive(true);
				boxDevice.setIsIssued(true);
				boxDevice.setIsPresent(true);
				boxDevices.add(boxDevice);
				// update in device
				device.setBoxCode(savedBox.getBoxNo());
				deviceList.add(device);
			}
			boxDeviceRepository.saveAll(boxDevices);
		}
		deviceRepository.saveAll(deviceList);
		return true;
	}

	private String generateIssueSlipNumber(String companyName, Long stateId, String timestamp) {
		String stateIdString = String.format("%02d", stateId);
		String issueSlipNumber = companyName.substring(0, 3).toUpperCase() + stateIdString + timestamp;
		return issueSlipNumber;
	}

	@Override
	public Map<Integer, String> mapCorrectClientWithDevice(MultipartFile file) {
		Map<Integer, String> unsuccessList = new HashMap<>();
		try {
			Long userId = 1l;
			List<Device> vehicleList = deviceRepository.findAll();
			List<Client> clientList = clientRepository.findAll();
			Optional<User> user = userRepository.findById(userId);
			List<Device> vehicles = new ArrayList<>();
			DataFormatter formatter = new DataFormatter();
			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Integer rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				} else {
					rowNumber++;
				}

				ExcelVehicleDTO excelVehicleDTO = new ExcelVehicleDTO();
				Device vehicleLazyEntity = null;
				int cellId = 0;
				Boolean isValid = true;
				String reason = null;
				for (int i = 0; i <= 1; i++) {
					Cell cell = row.getCell(i);
					try {
						if (row.getCell(cellId) == null || row.getCell(cellId).getCellTypeEnum() == CellType.BLANK) {

						} else {
							switch (cellId) {
							case 0:
								// imei
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String imei = formatter.formatCellValue(cell);
									if (imei != null && !imei.isEmpty()) {
										excelVehicleDTO.setImeiNo(imei);

										Optional<Device> vehicleFlag = vehicleList.stream()
												.filter(x -> x.getImeiNo() != null && x.getImeiNo() != ""
												&& x.getImeiNo().equalsIgnoreCase(excelVehicleDTO.getImeiNo()))
												.findAny();

										if (vehicleFlag.isPresent()) {
											vehicleLazyEntity = vehicleFlag.get();
										} else {
											isValid = false;
											reason = reason + " " + "invalid imei";
										}

									} else {
										isValid = false;
										reason = reason + " " + "invalid imei";

									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid imei";
								}
								break;

							case 1:
								// Client Name
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String clientName = formatter.formatCellValue(cell);
									Long clientsId = 0l;

									if (clientName != null && !clientName.isEmpty()) {
										try {
											clientsId = Long.parseLong(clientName);
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (clientsId != null && clientsId != 0l) {
											Long id = clientsId;
											Optional<Client> result = clientList.stream()
													.filter(obj -> obj.getId().equals(id)).findFirst();
											if (result.isPresent()) {
												excelVehicleDTO.setClientName(result.get().getCompanyName());
												excelVehicleDTO.setClientId(id);
											} else {
												isValid = false;
												reason = reason + " " + "invalid Client";
											}
										} else {
											isValid = false;
											reason = reason + " " + "invalid Client";
										}
									} else {
										isValid = false;
										reason = reason + " " + "invalid Client Name";
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid Client Column";
								}
								break;
							default:
								break;
							}
						}
						cellId++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (vehicleLazyEntity == null) {
					isValid = false;
					reason = "no vehicle found";
				}
				if (isValid) {
					vehicleLazyEntity.setClientNames(excelVehicleDTO.getClientName());
					vehicleLazyEntity.setClientId(excelVehicleDTO.getClientId());
					vehicleLazyEntity.setModifiedBy(user.get().getId());
					vehicleLazyEntity.setUpdatedAt(new Date());
					vehicles.add(vehicleLazyEntity);

				} else {
					unsuccessList.put(rowNumber, reason);
				}
			}
			workbook.close();
			deviceRepository.saveAll(vehicles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unsuccessList;
	}

	@Override
	public Map<Integer, String> importUpdatedVehicleExcelFileV2(MultipartFile file) {
		Map<Integer, String> unsuccessList = new HashMap<>();
		try {
			List<Device> devices = deviceRepository.findAll();
			List<Device> deviceList = new ArrayList<>();
			DataFormatter formatter = new DataFormatter();
			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Integer rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				} else {
					rowNumber++;
				}

				ExcelVehicleDTO excelVehicleDTO = new ExcelVehicleDTO();
				Device device = null;
				int cellId = 0;
				Boolean isValid = true;
				String reason = null;
				for (int i = 0; i <= 8; i++) {
					// Cell cell = cellIterator.next();
					Cell cell = row.getCell(i);
					try {
						if (row.getCell(cellId) == null || row.getCell(cellId).getCellTypeEnum() == CellType.BLANK) {

						} else {
							switch (cellId) {
							case 0:
								// Provider
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String provider = formatter.formatCellValue(cell);
									if (provider != null) {
										excelVehicleDTO.setSimProvider(provider);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim provider";
								}
								break;
							case 1:
								// iccid
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String ccid = formatter.formatCellValue(cell);
									if (ccid != null && !ccid.isEmpty()) {
										Optional<Device> flag = devices.stream()
												.filter(x -> x.getIccidNo() != null && x.getIccidNo() != ""
												&& x.getIccidNo().contains(ccid.trim()))
												.findAny();

										if (flag.isPresent()) {
											device = flag.get();
											excelVehicleDTO.setCcidNo(ccid);
										} else {
											isValid = false;
											reason = "no device found on iccid number " + ccid;
										}
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid ccid";
								}

								break;
							case 2:
								// sim1 operator
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim1Provider = formatter.formatCellValue(cell);
									if (sim1Provider != null) {
										excelVehicleDTO.setSim1Operator(sim1Provider);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim1 operator";
								}
								break;
							case 3:
								// sim2 operator
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim2Provider = formatter.formatCellValue(cell);
									if (sim2Provider != null) {
										excelVehicleDTO.setSim2Operator(sim2Provider);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim2 operator";
								}
								break;
							case 4:
								// sim activation date
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String simActivationDate = formatter.formatCellValue(cell);
									if (simActivationDate != null) {
										Date date = null;
										try {
											try {
												SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
												date = inputFormat.parse(simActivationDate);
											} catch (Exception e) {
												e.printStackTrace();
											}

											if (date != null) {
												excelVehicleDTO.setDeviceActivationDate(date);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								break;
							case 5:
								// sim expiry date
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String simExpiryDate = formatter.formatCellValue(cell);
									if (simExpiryDate != null) {
										Date date = null;
										try {
											try {
												SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
												date = inputFormat.parse(simExpiryDate);
											} catch (Exception e) {
												e.printStackTrace();
											}

											if (date != null) {
												excelVehicleDTO.setDeviceExpiryDate(date);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								break;
							case 6:
								// sim1
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim1 = formatter.formatCellValue(cell);
									if (sim1 != null) {
										excelVehicleDTO.setSim1Number(sim1);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim1";
								}
								break;
							case 7:
								// sim2
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim2 = formatter.formatCellValue(cell);
									if (sim2 != null) {
										excelVehicleDTO.setSim2Number(sim2);
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim2";
								}
								break;
							default:
								break;
							}
						}
						cellId++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
//				if (device == null) {
//					isValid = false;
//					reason = "no vehicle found";
//				}
				if (isValid) {
					device.setSim1Operator(excelVehicleDTO.getSim1Operator());
					device.setSim2Operator(excelVehicleDTO.getSim2Operator());
					device.setSim1ActivationDate(excelVehicleDTO.getDeviceActivationDate());
					device.setSim2ActivationDate(excelVehicleDTO.getDeviceActivationDate());
					device.setSim1ExpiryDate(excelVehicleDTO.getDeviceExpiryDate());
					device.setSim2ExpiryDate(excelVehicleDTO.getDeviceExpiryDate());
					device.setSim1Provider(excelVehicleDTO.getSimProvider());
					device.setSim2Provider(excelVehicleDTO.getSimProvider());
					device.setSim1Number(excelVehicleDTO.getSim1Number());
					device.setSim2Number(excelVehicleDTO.getSim2Number());
					deviceList.add(device);
				} else {
					unsuccessList.put(rowNumber, reason);
				}
			}
			workbook.close();
			deviceRepository.saveAll(deviceList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unsuccessList;
	}
}
