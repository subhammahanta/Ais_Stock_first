package com.watsoo.device.management.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.ClientLiteV2;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.UserLiteV2;
import com.watsoo.device.management.repository.ClientLiteV2Repository;
import com.watsoo.device.management.repository.DeviceLiteRepository;
import com.watsoo.device.management.repository.UserLiteV2Repository;
import com.watsoo.device.management.service.EmailServiceVM;
import com.watsoo.device.management.util.DateUtil;

@Service
public class DeviceThreadService {

	private final ExecutorService executorService;
	private final AtomicBoolean isGeneratingExcel = new AtomicBoolean(false);

	@Autowired
	private EmailServiceVM emailServiceVM;
	
	@Autowired
	private DeviceLiteRepository deviceLiteRepository;
	
	@Autowired
	private ClientLiteV2Repository clientLiteRepository;

	@Autowired
	private FileStorageService fileSystemStorageService;

	@Autowired
	private UserLiteV2Repository userLiteRepo;
	
	public DeviceThreadService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public synchronized boolean isRequestInProgress() {
		return isGeneratingExcel.get();
	}

	public synchronized void executeMethodInNewThread(GenericRequestBody requestDTO) {
		if (!isGeneratingExcel.getAndSet(true)) {
			executorService.submit(() -> {
				try {
					generateExcelAndNotify(requestDTO);
				} catch (IOException e) {
					e.printStackTrace();
				}
				isGeneratingExcel.set(false);
			});
		}
	}
	
	private void generateExcelAndNotify(GenericRequestBody requestDTO) throws IOException {
		List<DeviceLite> deviceResponse = fetchDeviceDate(requestDTO);
		Workbook workbook = generateExcelDeviceResponse(deviceResponse);
		String excelUrl = convertMultipartToUrl(workbook);
		// send mail
		Date date = new Date();
		if (requestDTO.getNotifyEmailId() != null && requestDTO.getNotifyEmailId() != "") {
			emailServiceVM.sendDeviceActRenSupportMail(requestDTO.getNotifyEmailId(),
					DateUtil.localDateTimeToStringInFormat(
							DateUtil.addMinutesToJavaUtilDate(date, Constant.IST_OFFSET_IN_MINUTES),
							Constant.DATE_FORMAT_DD_MM_YYYY),
					excelUrl);
			System.out.println("Mail sent successfully");
		}
		System.out.println("Method running in a new thread.");
	}
	private Workbook generateExcelDeviceResponse(List<DeviceLite> deviceResponse) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Device Data");
		List<String> headers = Arrays.asList("SN", "Updated At", "Created By", "IMEI Number", "ICCID Number",
				"UUID Number", "Software Version", "Status", "State", "Client Name", "Sim Provider",
				"Sim Activation Date", "Expiry Date", "Sim1 Operator", "Sim2 Operator", "Sim1 Number", "Sim2 Number",
				"Issue Date", "Packet Date", "Packet", "Details");
		Row headerRow = sheet.createRow(0);
		IntStream.range(0, headers.size()).forEach(i -> {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers.get(i));
		});
		for (int i = 0; i < deviceResponse.size(); i++) {
			Row row = sheet.createRow(i + 1);
			DeviceLite data = deviceResponse.get(i);
			row.createCell(0).setCellValue(i + 1);
			if (data.getUpdatedAt() != null) {
				row.createCell(1).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
						DateUtil.addMinutesToJavaUtilDateV2(data.getUpdatedAt(), Constant.IST_OFFSET_IN_MINUTES)));
			}

			row.createCell(2).setCellValue((data.getCreatedBy() != null) ? data.getCreatedBy().getName() : null);
			row.createCell(3).setCellValue(data.getImeiNo());
			row.createCell(4).setCellValue(data.getIccidNo());
			row.createCell(5).setCellValue(data.getUuidNo());
			row.createCell(6).setCellValue(data.getSoftwareVersion());
			row.createCell(7).setCellValue((data.getStatus() != null) ? data.getStatus().name() : null);
			row.createCell(8).setCellValue((data.getState() != null) ? data.getState().getName() : null);
			row.createCell(9).setCellValue(data.getClientName());
			row.createCell(10).setCellValue(data.getSim1Provider());
			if (data.getSim1ActivationDate() != null) {
				row.createCell(11).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(DateUtil
						.addMinutesToJavaUtilDateV2(data.getSim1ActivationDate(), Constant.IST_OFFSET_IN_MINUTES)));
			}
			if (data.getSim1ExpiryDate() != null) {
				row.createCell(12).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
						DateUtil.addMinutesToJavaUtilDateV2(data.getSim1ExpiryDate(), Constant.IST_OFFSET_IN_MINUTES)));
			}

			row.createCell(13).setCellValue(data.getSim1Operator());
			row.createCell(14).setCellValue(data.getSim2Operator());
			row.createCell(15).setCellValue(data.getSim1Number());
			row.createCell(16).setCellValue(data.getSim2Number());
			if (data.getIssueDate() != null) {
				row.createCell(17).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
						DateUtil.addMinutesToJavaUtilDateV2(data.getIssueDate(), Constant.IST_OFFSET_IN_MINUTES)));
			}
			if (data.getPackedDate() != null) {
				row.createCell(18).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(
						DateUtil.addMinutesToJavaUtilDateV2(data.getPackedDate(), Constant.IST_OFFSET_IN_MINUTES)));
			}

			row.createCell(19).setCellValue(data.getPacket());

			row.createCell(20).setCellValue(data.getDetail());
		}
		// workbook.write(outputStream);
		// workbook.close();

		return workbook;
	}

	private List<DeviceLite> fetchDeviceDate(GenericRequestBody requestDTO) {
		List<DeviceLite> deviceResponse = new ArrayList<>();
		List<DeviceLite> devices = new ArrayList<>();
		try {
			devices = deviceLiteRepository.findAllBySearch(requestDTO);
			List<Long> userIds = devices.stream().filter(o -> o.getModifiedBy() != null).map(o -> o.getModifiedBy())
					.distinct().collect(Collectors.toList());
			List<Long> clientIds = devices.stream().filter(o -> o.getClientId() != null).map(o -> o.getClientId())
					.distinct().collect(Collectors.toList());
			List<ClientLiteV2> clientList = new ArrayList<>();
			Map<Long, ClientLiteV2> clientMap = new HashMap<>();
			if (clientIds != null && !clientIds.isEmpty()) {
				clientList = clientLiteRepository.findAllById(clientIds);
				if (clientList != null && !clientList.isEmpty()) {
					clientMap = clientList.stream().filter(x -> x != null && x.getId() != null)
							.collect(Collectors.toMap(ClientLiteV2::getId, Function.identity()));
				}
			}
			List<UserLiteV2> userList = userLiteRepo.findAllById(userIds);
			Map<Long, UserLiteV2> userMap = userList.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(UserLiteV2::getId, Function.identity()));

			for (DeviceLite device : devices) {
				UserLiteV2 lastWorkedUser = userMap.get(device.getModifiedBy()) != null
						? userMap.get(device.getModifiedBy())
						: null;
				if (lastWorkedUser != null) {
					device.setCreatedAt(DateUtil.addMinutesToJavaUtilDate(device.getCreatedAt(), 330));
					device.setUpdatedAt(DateUtil.addMinutesToJavaUtilDate(device.getUpdatedAt(), 330));
					device.setUserName(lastWorkedUser.getName());
					device.setUserEmail(lastWorkedUser.getEmail());
				}
				if (device.getClientId() != null && clientMap != null && !clientMap.isEmpty()
						&& clientMap.containsKey(device.getClientId())) {
					device.setClientName(clientMap.get(device.getClientId()).getCompanyName());
				}
				if (device.getStatus().equals(StatusMaster.BOX_PACKED)) {
					device.setBoxNo(StatusMaster.BOX_PACKED + "(" + device.getBoxCode() + ")");
				} else {
					device.setBoxNo(device.getStatus().toString());
				}
				device.setPackedDate(device.getPackedDate() != null
						? DateUtil.addMinutesToJavaUtilDate(device.getPackedDate(), Constant.ADD_IST_TIME)
						: null);
				deviceResponse.add(device);
			}
			deviceResponse = deviceResponse.stream().sorted(
					Comparator.comparing(DeviceLite::getPackedDate, Comparator.nullsLast(Comparator.naturalOrder())))
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceResponse;
	}

	public String convertMultipartToUrl(Workbook workBook) {
		// Convert Workbook to MultipartFile
		String fileUrl = "";
		String filename = "DeviceActivationRenewalPendingReport.xlsx";
		String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			workBook.write(outputStream);
			InputStream inputStream = new ByteArrayResource(outputStream.toByteArray()).getInputStream();
			MultipartFile multipartFile = new MockMultipartFile(filename, filename, contentType, inputStream);
			System.out.println("MultipartFile created successfully.");
			fileUrl = fileSystemStorageService.getFileUrl(multipartFile);
			return fileUrl;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
