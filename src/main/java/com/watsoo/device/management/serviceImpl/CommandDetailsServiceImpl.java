package com.watsoo.device.management.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.CommandsDetailsDTO;
import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.model.CommandDetails;
import com.watsoo.device.management.model.CommandRequestMaster;
import com.watsoo.device.management.repository.CommandDetailsRepository;
import com.watsoo.device.management.repository.CommandRequestMasterRepository;
import com.watsoo.device.management.service.CommandDetailsService;
import com.watsoo.device.management.service.EmailServiceVM;
import com.watsoo.device.management.util.DateUtil;

@Service
public class CommandDetailsServiceImpl implements CommandDetailsService {
	@Autowired
	private CommandDetailsRepository commandDetailsRepository;
	
	@Autowired
	private FileStorageService fileSystemStorageService;
	
	@Autowired
	private EmailServiceVM emailServiceVM;
	
	@Autowired
	private CommandRequestMasterRepository commandRequestMasterRepository;

	@Override
	public Pagination<List<CommandsDetailsDTO>> getAllCommandDetails(GenericRequestBody requestDTO) {
		Pagination<List<CommandsDetailsDTO>> response = new Pagination<>();
		List<CommandsDetailsDTO> deviceList = new ArrayList<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
				pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize());
			}
			Page<CommandDetails> page = commandDetailsRepository.findAllV2(requestDTO, pageRequest);

			for (CommandDetails device : page.getContent()) {
				CommandsDetailsDTO commandsDetailsDTO = device.convertEntityToDTO(device);
				deviceList.add(commandsDetailsDTO);
			}
			response.setData(deviceList);
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<CommandsDetailsDTO> findAllByMasterId(Long masterId) {
		List<CommandsDetailsDTO> list = new ArrayList<>();
		List<CommandDetails> commandDetailsList = commandDetailsRepository.findByRequestId(masterId);
		for (CommandDetails commandDetails : commandDetailsList) {
			CommandsDetailsDTO commandsDetailsDTO = commandDetails.convertEntityToDTO(commandDetails);
			list.add(commandsDetailsDTO);
		}

		return list;
	}

	@Override
	public Response<Object> generateConfigureDevicesExcelAndNotify(GenericRequestBody requestDTO) {
		List<CommandDetails> commandDetailsList = commandDetailsRepository
				.findByRequestId(requestDTO.getId());
		Optional<CommandRequestMaster> optionalComm = commandRequestMasterRepository.findById(requestDTO.getId());
		Workbook workbook = generateUnConfigureDevicesExcel(commandDetailsList);
		String excelUrl = convertMultipartToUrl(workbook);
		// send mail
		Date date = new Date();
		if (requestDTO.getNotifyEmailId() != null && requestDTO.getNotifyEmailId() != "") {
			emailServiceVM.sendConfigureDevicesExcelMail(requestDTO.getNotifyEmailId(),
					DateUtil.localDateTimeToStringInFormat(
							DateUtil.addMinutesToJavaUtilDate(date, Constant.IST_OFFSET_IN_MINUTES),
							Constant.DATE_FORMAT_DD_MM_YYYY),
					excelUrl,optionalComm.get().getRequestCode());
			return new Response<>(HttpStatus.OK.value(), "Mail sent Successfully");
		}else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Unable to send Mail");
		}
	}
	
	public String convertMultipartToUrl(Workbook workBook) {
		// Convert Workbook to MultipartFile
		String fileUrl = "";
		String filename = "Device.xlsx";
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

	private Workbook generateUnConfigureDevicesExcel(List<CommandDetails> commandDetailsList) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("UnSuccess Device Configuration");
		List<String> headers = Arrays.asList("SN", "IMEI Number", "Command","Response", "Command Shoot Time");
		Row headerRow = sheet.createRow(0);
		IntStream.range(0, headers.size()).forEach(i -> {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers.get(i));
		});
		for (int i = 0; i < commandDetailsList.size(); i++) {
			Row row = sheet.createRow(i + 1);
			CommandDetails data = commandDetailsList.get(i);
			row.createCell(0).setCellValue(i + 1);
			row.createCell(1).setCellValue(data.getImei());
			row.createCell(2).setCellValue(data.getCommand());
			row.createCell(3).setCellValue(data.getLastShootResponse());
			if (data.getLastShootTime() != null) {
				row.createCell(4).setCellValue(DateUtil.localDateTimeToStringInFormatDDMMYYYYHHMMA(DateUtil
						.addMinutesToJavaUtilDate(data.getLastShootTime(), Constant.IST_OFFSET_IN_MINUTES)));
			}
		}
		return workbook;
	}
}
