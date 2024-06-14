package com.watsoo.device.management.serviceImpl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.IccidMasterDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.model.IccidMaster;
import com.watsoo.device.management.model.Operators;
import com.watsoo.device.management.model.Provider;
import com.watsoo.device.management.repository.IccidMasterRepository;
import com.watsoo.device.management.repository.OperatorsRepository;
import com.watsoo.device.management.repository.ProviderRepository;
import com.watsoo.device.management.service.IccidMasterService;

@Service
public class IccidMasterServiceImpl implements IccidMasterService {

	@Autowired
	private IccidMasterRepository iccidMasterRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private OperatorsRepository operatorsRepository;

	@Override
	public Pagination<List<IccidMaster>> getAllIccidMaster(GenericRequestBody requestDTO) {
		Pagination<List<IccidMaster>> response = new Pagination<>();
		List<IccidMaster> iccidMasters = new ArrayList<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageSize() > 0) {
				pageRequest = PageRequest.of(requestDTO.getPageNo(), requestDTO.getPageSize(),
						Sort.by("id").descending());
			}
			Page<IccidMaster> page = iccidMasterRepository.findAll(requestDTO, pageRequest);
			iccidMasters = page.getContent();
			response.setData(iccidMasters);
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Map<Integer, String> addIccidFromExcelFile(MultipartFile file, Long userId) {
		Map<Integer, String> unsuccessList = new HashMap<>();
		try {
			List<Provider> possibleProvider = providerRepository.findAll();
			Map<String, Provider> possibleProviderMap = possibleProvider.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(Provider::getName, Function.identity()));
			List<Operators> possibleOperators = operatorsRepository.findAll();
			Map<String, Operators> possibleOperatorsMap = possibleOperators.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(Operators::getName, Function.identity()));
			List<IccidMaster> iccids = iccidMasterRepository.findAll();
			Map<String, IccidMaster> iccidsMap = iccids.stream().filter(o -> o.getId() != null)
					.collect(Collectors.toMap(IccidMaster::getIccidNo, Function.identity()));
			List<IccidMaster> iccidList = new ArrayList<>();
			DataFormatter formatter = new DataFormatter();
			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Integer rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();

			while (iterator.hasNext()) {
				Row row = iterator.next();
				rowNumber++;
				if (rowNumber == 1) {
					continue;
				}

				boolean isEmptyRow = true;
				for (int i = 0; i <= 8; i++) {
					Cell cell = row.getCell(i);
					if (cell != null && cell.getCellTypeEnum() != CellType.BLANK
							&& !formatter.formatCellValue(cell).trim().isEmpty()) {
						isEmptyRow = false;
						break;
					}
				}
				if (isEmptyRow) {
					continue;
				}

				IccidMasterDTO iccidMaster = new IccidMasterDTO();
				int cellId = 0;
				Boolean isValid = true;
				String reason = null;

				for (int i = 0; i <= 8; i++) {
					Cell cell = row.getCell(i);
					try {
						switch (cellId) {
						case 0:
							// Provider
							if (cell.getCellTypeEnum() != CellType.BLANK) {
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String provider = formatter.formatCellValue(cell);
//									Optional<Provider> flag = possibleProvider
//											.stream().filter(o -> o.getId() != null && o.getName() != null
//													&& o.getName() != "" && o.getName().equalsIgnoreCase(provider))
//											.findFirst();
//									if (provider != null && flag.isPresent()) {
//										iccidMaster.setProvider(provider);
//									} else {
//										isValid = false;
//										reason = "invalid sim provider";
//									}
									Provider provider2 = possibleProviderMap.get(provider.trim());
									if (provider2 != null) {
										iccidMaster.setProvider(provider.trim());
									} else {
										isValid = false;
										reason = "invalid sim provider";
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim provider";
								}
							} else {
								isValid = false;
								reason = "Provider can not be " + reason;
							}
							break;
						case 1:
							// iccid
							if (cell.getCellTypeEnum() != CellType.BLANK) {
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String iccid = formatter.formatCellValue(cell);
									if (iccid != null && !iccid.isEmpty()) {
//										Optional<IccidMaster> flag = iccids.stream()
//												.filter(x -> x.getIccidNo() != null && x.getIccidNo() != ""
//														&& x.getIccidNo().equalsIgnoreCase(iccid.trim()))
//												.findAny();
//
//										if (!flag.isPresent()) {
//											iccidMaster.setIccidNo(iccid);
//										} else {
//											isValid = false;
//											reason = "already exist";
//										}

										IccidMaster iccid2 = iccidsMap.get(iccid.trim());
										if (iccid2 == null) {
											iccidMaster.setIccidNo(iccid.trim());
										} else {
											isValid = false;
											reason = "already exist";
										}

									} else {
										isValid = false;
										reason = "iccid can not be " + reason;
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid iccid";
								}
							} else {
								isValid = false;
								reason = "iccid can not be " + reason;
							}
							break;
						case 2:
							// sim1 operator
							if (cell.getCellTypeEnum() != CellType.BLANK) {
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim1Operator = formatter.formatCellValue(cell);
//									Optional<Operators> flag = possibleOperators
//											.stream().filter(o -> o.getId() != null && o.getName() != null
//													&& o.getName() != "" && o.getName().equalsIgnoreCase(sim1Operator))
//											.findFirst();
//									if (sim1Operator != null && flag.isPresent()) {
//										iccidMaster.setSim1Operator(sim1Operator);
//									} else {
//										isValid = false;
//										reason = "invalid sim1 operator";
//									}
									Operators sim1Operator2 = possibleOperatorsMap.get(sim1Operator.trim());
									if (sim1Operator2 != null) {
										iccidMaster.setSim1Operator(sim1Operator.trim());
									} else {
										isValid = false;
										reason = "invalid sim1 operator";
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim1 operator";
								}
							} else {
								isValid = false;
								reason = "Sim1 operator can not be " + reason;
							}
							break;
						case 3:
							// sim2 operator
							if (cell.getCellTypeEnum() != CellType.BLANK) {
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String sim2Operator = formatter.formatCellValue(cell);
//									Optional<Operators> flag = possibleOperators
//											.stream().filter(o -> o.getId() != null && o.getName() != null
//													&& o.getName() != "" && o.getName().equalsIgnoreCase(sim2Operator))
//											.findFirst();
//									if (sim2Operator != null && flag.isPresent()) {
//										iccidMaster.setSim2Operator(sim2Operator);
//									} else {
//										isValid = false;
//										reason = "invalid sim2 operator";
//									}
									Operators sim2Operator2 = possibleOperatorsMap.get(sim2Operator.trim());
									if (sim2Operator2 != null) {
										iccidMaster.setSim2Operator(sim2Operator.trim());
									} else {
										isValid = false;
										reason = "invalid sim2 operator";
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid sim2 operator";
								}
							} else {
								isValid = false;
								reason = "Sim2 operator can not be " + reason;
							}
							break;
						case 4:
							// sim activation date
							if (cell != null) {
								if (cell.getCellTypeEnum() != CellType.BLANK) {
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
													iccidMaster.setSimActivationDate(date);
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
							break;
						case 5:
							// sim expiry date
							if (cell != null) {
								if (cell.getCellTypeEnum() != CellType.BLANK) {
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
													iccidMaster.setSimExpiryDate(date);
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
							break;
						case 6:
							// sim1
							if (cell != null) {
								if (cell.getCellTypeEnum() != CellType.BLANK) {
									if (cell.getCellTypeEnum() == CellType.STRING) {
										String sim1 = formatter.formatCellValue(cell);
										if (sim1 != null) {
											iccidMaster.setSim1(sim1);
										}
									} else {
										isValid = false;
										reason = reason + " " + "invalid sim1";
									}
								} else {
									isValid = false;
									reason = "Sim1 can not be " + reason;
								}
							}
							break;
						case 7:
							// sim2
							if (cell != null) {
								if (cell.getCellTypeEnum() != CellType.BLANK) {
									if (cell.getCellTypeEnum() == CellType.STRING) {
										String sim2 = formatter.formatCellValue(cell);
										if (sim2 != null) {
											iccidMaster.setSim2(sim2);
										}
									} else {
										isValid = false;
										reason = reason + " " + "invalid sim2";
									}
								} else {
									isValid = false;
									reason = "Sim2 can not be " + reason;
								}
							}
							break;
						case 8:
							// Iccid Operator
							// if (cell != null) {
							if (cell.getCellTypeEnum() != CellType.BLANK) {
								if (cell.getCellTypeEnum() == CellType.STRING) {
									String iccidOperator = formatter.formatCellValue(cell);
//									if (iccidOperator != null) {
//										iccidMaster.setIccidOperator(iccidOperator);
//									}
									Operators iccidOperator2 = possibleOperatorsMap.get(iccidOperator.trim());
									if (iccidOperator2 != null) {
										iccidMaster.setIccidOperator(iccidOperator.trim());
									} else {
										isValid = false;
										reason = "invalid iccid operator";
									}
								} else {
									isValid = false;
									reason = reason + " " + "invalid Iccid Operator";
								}
							} else {
								isValid = false;
								reason = "Iccid Operator can not be " + reason;
							}
						default:
							break;
						}

						cellId++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if ((iccidMaster.getSim1() == null || iccidMaster.getSim1() == "")
						&& (iccidMaster.getSim2() == null || iccidMaster.getSim2() == "")) {
					isValid = false;
					reason = "Both Sim1 And Sim2 Can Not Be Empty.. Atleast One Number Is Required";
				}
				if (isValid) {
					IccidMaster master = new IccidMaster();
					master.setProvider(iccidMaster.getProvider());
					master.setIccidNo(iccidMaster.getIccidNo());
					master.setSim1Operator(iccidMaster.getSim1Operator());
					master.setSim2Operator(iccidMaster.getSim2Operator());
					master.setSimActivationDate(iccidMaster.getSimActivationDate());
					master.setSimExpiryDate(iccidMaster.getSimExpiryDate());
					master.setSim1(iccidMaster.getSim1());
					master.setSim2(iccidMaster.getSim2());
					master.setIccidOperator(iccidMaster.getIccidOperator());
					master.setCreatedAt(new Date());
					master.setCreatedBy(userId);
					iccidList.add(master);
					reason = "added successfully";
					unsuccessList.put(rowNumber, reason);
				} else {
					unsuccessList.put(rowNumber, reason);
				}
			}
			workbook.close();
			iccidMasterRepository.saveAll(iccidList);
			return unsuccessList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

}
