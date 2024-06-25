package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.ReturnReplaceRepairDTO;
import com.watsoo.device.management.dto.ReturnReplaceRepairDeviceDTO;
import com.watsoo.device.management.enums.OperationEnum;
import com.watsoo.device.management.enums.StatusEnum;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.IssuedList;
import com.watsoo.device.management.model.ReturnReplaceRepair;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.ClientRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.IssuedRepository;
import com.watsoo.device.management.service.ReturnReplaceAndRepairDeviceService;

@Service
public class ReturnReplaceAndRepairDeviceServiceImpl implements ReturnReplaceAndRepairDeviceService {

//	@Autowired
//	private ReturnReplaceAndRepairDeviceRepository repository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private BoxDeviceRepository boxDeviceRepository;

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ReturnReplaceAndRepairDeviceRepository repairDeviceRepository;
	
	@Autowired
	private IssuedRepository issuedRepository;

	@Override
	public Response<?> addForReturnOrReplaceOrRepair(ReturnReplaceRepairDTO dto) {
		Map<String, String> errorList = new HashMap<>();
		try {
			if (dto.getClientName() != null && dto.getClientName() != "" && dto.getCreatedBy() != null
					&& dto.getOperation() != null
					&& (dto.getOperation().equals(OperationEnum.REPAIR)
							|| dto.getOperation().equals(OperationEnum.REPLACE)
							|| dto.getOperation().equals(OperationEnum.RETURN))
					&& dto.getDeviceDto() != null) {
				Optional<Client> client = clientRepository.findById(dto.getClientId());
				List<Device> deviceByReplacedImeiList = new ArrayList<>();
				List<String> originalImeiList = dto.getDeviceDto().stream()
						.map(ReturnReplaceRepairDeviceDTO::getOriginalImei).collect(Collectors.toList());
				List<String> replacedByImeiList = dto.getDeviceDto().stream()
						.map(ReturnReplaceRepairDeviceDTO::getReplacedByImei).collect(Collectors.toList());
				List<BoxDevice> boxDeviceList2 = new ArrayList<>();
				if (replacedByImeiList != null && replacedByImeiList.size() > 0) {
					deviceByReplacedImeiList = deviceRepository.findAllByImeiNoIn(replacedByImeiList);
					if (deviceByReplacedImeiList != null && deviceByReplacedImeiList.size() > 0) {
						List<Long> ids = deviceByReplacedImeiList.stream().map(Device::getId)
								.collect(Collectors.toList());
						boxDeviceList2 = boxDeviceRepository.findByDeviceId(ids);
					}
				}
				List<IssuedList> updateIssuedList = new ArrayList<>();
				List<IssuedList> issuedList = issuedRepository.findAll();
				Map<Long, IssuedList> issueListMap = issuedList.stream().filter(o -> o.getSerialNumber() != null)
						.collect(Collectors.toMap(IssuedList::getSerialNumber, Function.identity()));
				
				// List<String> imeiList = parseCommaSeparatedStringToList(dto.getImeiList());
				if (originalImeiList != null && originalImeiList.size() > 0 && !originalImeiList.isEmpty()) {
					List<Device> deviceList = deviceRepository.findAllByImeiNoIn(originalImeiList);
					if (deviceList != null && !deviceList.isEmpty() && deviceList.size() > 0) {
						List<Long> devicesId = deviceList.stream().map(Device::getId).collect(Collectors.toList());
						List<BoxDevice> boxDeviceList = boxDeviceRepository.findByDeviceId(devicesId);
						List<Device> devices = new ArrayList<>();
						List<ReturnReplaceRepair> list = new ArrayList<>();
						for (String imei : originalImeiList) {
							Device device = null;
							Optional<Device> deviceFlag = deviceList.stream().filter(x -> x.getImeiNo() != null
									&& x.getImeiNo() != "" && x.getImeiNo().equalsIgnoreCase(imei)).findAny();

							if (deviceFlag.isPresent()) {
								device = deviceFlag.get();
								// RETURN
								if (dto.getOperation().equals(OperationEnum.RETURN)) {
									//to-do transaction 
									device.setBoxCode(null);
									device.setBoxNo(null);
									device.setClientId(null);
									device.setClientName(null);
									device.setOrderId(null);
									device.setIssueDate(null);
									device.setIsReturnType(true);
									device.setStatus(StatusMaster.DEVICE_PACKED);
									devices.add(device);
									Long id = device.getId();
									Optional<Box> boxFlag = boxDeviceList.stream()
											.filter(boxDevice -> boxDevice.getDevice() != null
													&& boxDevice.getDevice().getId().equals(id))
											.map(BoxDevice::getBox).findFirst();
									Optional<BoxDevice> boxDeviceFlag = boxDeviceList.stream()
											.filter(boxDevice -> boxDevice.getDevice() != null
													&& boxDevice.getDevice().getId().equals(id))
											.findFirst();
									if (boxDeviceFlag.isPresent() && boxFlag.isPresent()) {
										Box box = boxFlag.get();
										//
										IssuedList issueObj = issueListMap.get(box.getIssuedList());
										if (issueObj != null) {
											issueObj.setQuantity(issueObj.getQuantity()-1);
											updateIssuedList.add(issueObj);
										}
										BoxDevice boxDevice = boxDeviceFlag.get();
										box.setCurrentQuantity(box.getCurrentQuantity() - 1);
										box.setQuantity(box.getQuantity() - 1);
										boxDevice.setIsPresent(false);
										boxDevice.setIsActive(false);
										boxDevice.setIsIssued(false);
										boxDeviceRepository.save(boxDevice);
										boxRepository.save(box);
										// add in rrr
										ReturnReplaceRepair entity = new ReturnReplaceRepair();
										entity.setImei(imei);
										entity.setClientName(dto.getClientName());
										entity.setCreatedAt(new Date());
										entity.setOperation(dto.getOperation());
										entity.setCreatedBy(dto.getCreatedBy());
										entity.setStatus(StatusEnum.COMPLETED);
										list.add(entity);
									} else {
										errorList.put(imei, "no box-device mapping found for this imei number");
									}
								}
								// REPLACE
								if (dto.getOperation().equals(OperationEnum.REPLACE)) {

									Optional<ReturnReplaceRepairDeviceDTO> deviceDto = dto.getDeviceDto().stream()
											.filter(x -> x.getOriginalImei().equals(imei)).findFirst();
									if (originalImeiList.size() == replacedByImeiList.size()
											&& deviceByReplacedImeiList != null) {
										Optional<Device> replaceDeviceFlag = deviceByReplacedImeiList.stream()
												.filter(x -> x.getImeiNo() != null && x.getImeiNo() != ""
														&& x.getImeiNo()
																.equalsIgnoreCase(deviceDto.get().getReplacedByImei()))
												.findAny();
										if (replaceDeviceFlag.isPresent() && replaceDeviceFlag.get().getStatus() != null
												&& (replaceDeviceFlag.get().getStatus().equals(StatusMaster.BOX_PACKED))
												|| replaceDeviceFlag.get().getStatus()
														.equals(StatusMaster.DEVICE_PACKED)) {

											Long id = device.getId();
											Optional<Box> boxFlag = boxDeviceList.stream()
													.filter(boxDevice -> boxDevice.getDevice() != null
															&& boxDevice.getDevice().getId().equals(id))
													.map(BoxDevice::getBox).findFirst();
											Optional<BoxDevice> boxDeviceFlag = boxDeviceList.stream()
													.filter(boxDevice -> boxDevice.getDevice() != null
															&& boxDevice.getDevice().getId().equals(id))
													.findFirst();

											if (boxDeviceFlag.isPresent() && boxFlag.isPresent()) {

												// update replace device
												if (replaceDeviceFlag.get().getStatus()
														.equals(StatusMaster.BOX_PACKED)) {
													// get box by device id
													// get box device
													// and update
//													Optional<Box> boxFlag2 = boxDeviceList2.stream()
//															.filter(boxDevice -> boxDevice.getDevice() != null
//																	&& boxDevice.getDevice().getId()
//																			.equals(replaceDeviceFlag.get().getId()))
//															.map(BoxDevice::getBox).findFirst();
													Optional<BoxDevice> boxDeviceFlag2 = boxDeviceList2.stream()
															.filter(boxDevice -> boxDevice.getDevice() != null
																	&& boxDevice.getDevice().getId()
																			.equals(replaceDeviceFlag.get().getId()))
															.findFirst();
													replaceDeviceFlag.get().setBoxCode(device.getBoxCode());
													replaceDeviceFlag.get().setClientId(device.getClientId());
													replaceDeviceFlag.get().setClientName(device.getClientName());
													replaceDeviceFlag.get().setOrderId(device.getOrderId());
													replaceDeviceFlag.get().setIssueDate(device.getIssueDate());
													replaceDeviceFlag.get().setState(client.get().getState());
													// replaceDeviceFlag.get().setIsRepairType(true);
													replaceDeviceFlag.get().setStatus(StatusMaster.ISSUED_DEVICES);
													devices.add(replaceDeviceFlag.get());
													//Box box = boxFlag2.get();
													BoxDevice boxDevice = boxDeviceFlag2.get();
													//box.setCurrentQuantity(box.getCurrentQuantity() - 1);
													//box.setQuantity(box.getQuantity() - 1);
													boxDevice.setIsActive(true);
													boxDevice.setIsIssued(true);
													boxDevice.setIsPresent(true);
													boxDeviceRepository.save(boxDevice);
													//boxRepository.save(box);
												} else {
													// device packed
													// map
													BoxDevice boxDevice1 = new BoxDevice();
													boxDevice1.setBox(boxFlag.get());
													boxDevice1.setDevice(replaceDeviceFlag.get());
													boxDevice1.setIsActive(true);
													boxDevice1.setIsIssued(true);
													boxDevice1.setIsPresent(true);
													boxDeviceRepository.save(boxDevice1);

													replaceDeviceFlag.get().setBoxCode(boxFlag.get().getBoxNo());
													replaceDeviceFlag.get().setState(client.get().getState());
													replaceDeviceFlag.get().setStatus(StatusMaster.ISSUED_DEVICES);
													replaceDeviceFlag.get().setIssueDate(new Date());
													replaceDeviceFlag.get().setClientId(dto.getClientId());
													// replaceDeviceFlag.get().setIsOwnDevice(dto.getIsOurClient());
													replaceDeviceFlag.get().setUpdatedAt(new Date());
													replaceDeviceFlag.get().setClientNames(dto.getClientName());

													devices.add(replaceDeviceFlag.get());
												}
												//to-do transaction
												device.setBoxCode(null);
												device.setBoxNo(null);
												device.setClientId(null);
												device.setClientName(null);
												device.setOrderId(null);
												device.setIssueDate(null);
												device.setIsReplaceType(true);
												device.setStatus(StatusMaster.DEVICE_PACKED);
												devices.add(device);
												// Box box = boxFlag.get();
												BoxDevice boxDevice = boxDeviceFlag.get();
												// box.setCurrentQuantity(box.getCurrentQuantity() - 1);
												// box.setQuantity(box.getQuantity() - 1);
												boxDevice.setIsPresent(false);
												boxDevice.setIsActive(false);
												boxDevice.setIsIssued(false);
												boxDeviceRepository.save(boxDevice);
												// boxRepository.save(box);
												// add in rrr
												ReturnReplaceRepair entity = new ReturnReplaceRepair();
												entity.setReplaceByImei(replaceDeviceFlag.get().getImeiNo());
												entity.setImei(imei);
												entity.setClientName(dto.getClientName());
												entity.setCreatedAt(new Date());
												entity.setOperation(dto.getOperation());
												entity.setCreatedBy(dto.getCreatedBy());
												entity.setStatus(StatusEnum.COMPLETED);
												list.add(entity);
                                               
                                                
											} else {
												errorList.put(imei, "no box-device mapping found for this imei number");
											}
										} else {
											errorList.put(imei, "replaces device not found");
										}
									} else {
										return new Response<>(HttpStatus.BAD_REQUEST.value(),
												"origial imei size not match with replace imei size");
									}

								}
								// REPAIR
								if (dto.getOperation().equals(OperationEnum.REPAIR)) {
									device.setIsRepairType(true);
									devices.add(device);
									// add in rrr
									ReturnReplaceRepair entity = new ReturnReplaceRepair();
									entity.setImei(imei);
									entity.setClientName(dto.getClientName());
									entity.setCreatedAt(new Date());
									entity.setOperation(dto.getOperation());
									entity.setCreatedBy(dto.getCreatedBy());
									entity.setStatus(StatusEnum.PENDING);
									list.add(entity);
								}

							} else {
								// add not found list
								errorList.put(imei, "no device found for this imei number");
							}
						}
						issuedRepository.saveAll(updateIssuedList);
						deviceRepository.saveAll(devices);
						repairDeviceRepository.saveAll(list);
						return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(),
								errorList);
					} else {
						return new Response<>(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
					}

				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
				}
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
	}

	@Override
	public Pagination<List<ReturnReplaceRepair>> getAllReturnReplaceRepairDevices(int pageNo, int pageSize,
			ReturnReplaceRepairDTO dto) {

		Pagination<List<ReturnReplaceRepair>> response = new Pagination<>();
		Page<ReturnReplaceRepair> rrr = null;
		Pageable pageRequest = Pageable.unpaged();
		if (pageSize != 0) {
			pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
		}
		rrr = repairDeviceRepository.findAll(dto, pageRequest);
		response.setData(rrr.getContent());
		response.setNumberOfElements(rrr.getNumberOfElements());
		response.setTotalElements(rrr.getTotalElements());
		response.setTotalPages(rrr.getTotalPages());
		return response;
	}

	@Override
	public Response<?> repairDevice(ReturnReplaceRepairDTO dto) {
		if (dto.getStatus() != null && dto.getStatus().equals(StatusEnum.COMPLETED) && dto.getId() != null
				&& dto.getId() > 0 && dto.getRemark() != null && dto.getRemark() != "") {
			Optional<ReturnReplaceRepair> entity = repairDeviceRepository.findById(dto.getId());
			if (entity.isPresent()) {
				if (entity.get().getStatus().equals(StatusEnum.PENDING)) {
					entity.get().setStatus(StatusEnum.COMPLETED);
					entity.get().setRemark(dto.getRemark());
					repairDeviceRepository.save(entity.get());
					return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
				}
			} else {
				return new Response<>(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
	}

}
