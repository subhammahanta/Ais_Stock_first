package com.watsoo.device.management.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.IssuedList;
import com.watsoo.device.management.model.ReturnDevice;
import com.watsoo.device.management.model.ReturnDeviceMaster;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.DeviceLiteRepository;
import com.watsoo.device.management.repository.IssuedRepository;
import com.watsoo.device.management.repository.ReturnDeviceMasterRepository;
import com.watsoo.device.management.repository.ReturnDeviceRepository;
import com.watsoo.device.management.service.ReturnDeviceService;

@Service
public class ReturnDeviceServiceImpl implements ReturnDeviceService {

	@Autowired
	private ReturnDeviceMasterRepository returnDeviceMasterRepository;

	@Autowired
	private DeviceLiteRepository deviceLiteRepository;

	@Autowired
	private ReturnDeviceRepository returnDeviceRepository;

	@Autowired
	private BoxDeviceRepository boxDeviceRepository;

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private IssuedRepository issuedRepository;

	@Override
	public Response<?> returnDevices(GenericRequestBody request) {
		if (request.getReturnDeviceMasterId() == null) {
			if (request.getClientId() == null || request.getStateId() == null || request.getTotalDevice() == null
					|| request.getTotalDevice() <= 0 || request.getUserId() == null || request.getUserName() == null
					|| request.getUserName() == "") {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "add mandatory field");
			}
			//exist
			List<ReturnDeviceMaster> returnDeviceMaster2 = returnDeviceMasterRepository.findByDebitNoteAndEwayBill(request.getDebitNoteNo(),request.getEwayBillNo());
			if (!returnDeviceMaster2.isEmpty()) {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "DebitNote Or EwayBill already exist");
			}
			ReturnDeviceMaster returnDeviceMaster = new ReturnDeviceMaster();
			returnDeviceMaster.setClient(new Client(request.getClientId()));
			returnDeviceMaster.setState(new State(request.getStateId()));
			returnDeviceMaster.setRequestedQuantity(request.getTotalDevice());
			returnDeviceMaster.setCurrentQuantity(0);
			returnDeviceMaster.setCreatedAt(new Date());
			returnDeviceMaster.setCreatedBy(request.getUserId());
			returnDeviceMaster.setCreatedUserName(request.getUserName());
			returnDeviceMaster.setDebitNoteImage(request.getDebitNoteImage());
			returnDeviceMaster.setDebitNoteNo(request.getDebitNoteNo());
			returnDeviceMaster.setEwayBillImage(request.getEwayBillImage());
			returnDeviceMaster.setEwayBillNo(request.getEwayBillNo());
			returnDeviceMaster.setIsActive(true);
			returnDeviceMaster.setReason(request.getReason());
			Random random = new Random();
			int randomNumber = random.nextInt(1000000000);
			String formattedNumber = String.format("%09d", randomNumber);
			returnDeviceMaster.setReqCode(formattedNumber);
			ReturnDeviceMaster obj = returnDeviceMasterRepository.save(returnDeviceMaster);
			return new Response<>(HttpStatus.CREATED.value(), "Request created",obj);
		} else {
			Optional<ReturnDeviceMaster> rdmOpt = returnDeviceMasterRepository
					.findById(request.getReturnDeviceMasterId());
			ReturnDeviceMaster returnDeviceMaster = rdmOpt.get();
			if (request.getTotalDevice() == null || request.getTotalDevice() <=0 || request.getTotalDevice() < returnDeviceMaster.getCurrentQuantity() ) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "enter valid device count");
			}
			returnDeviceMaster.setRequestedQuantity(request.getTotalDevice());
			returnDeviceMaster.setDebitNoteImage(request.getDebitNoteImage());
			returnDeviceMaster.setDebitNoteNo(request.getDebitNoteNo());
			returnDeviceMaster.setEwayBillImage(request.getEwayBillImage());
			returnDeviceMaster.setEwayBillNo(request.getEwayBillNo());
			returnDeviceMaster.setUpdatedAt(new Date());
			returnDeviceMaster.setUpdatedBy(request.getUserId());
			returnDeviceMaster.setUpdatedUserName(request.getUserName());
			ReturnDeviceMaster obj = returnDeviceMasterRepository.save(returnDeviceMaster);
			return new Response<>(HttpStatus.OK.value(), "Request updated",obj);
		}

	}

	@Override
	public Pagination<List<ReturnDeviceMaster>> getAllReturnDeviceRequest(GenericRequestBody request) {
		Pagination<List<ReturnDeviceMaster>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (request.getPageSize() != null && request.getPageNo() != null && request.getPageSize() > 0) {
				pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
			}
			Page<ReturnDeviceMaster> page = returnDeviceMasterRepository.findAll(request, pageRequest);
			response.setData(page.getContent());
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response<?> addReturnDevice(GenericRequestBody dto) {
		if (dto.getReturnDeviceMasterId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "ReturnDeviceMasterId can not be null");
		}
		Optional<ReturnDeviceMaster> retOptional = returnDeviceMasterRepository.findById(dto.getReturnDeviceMasterId());
		if (!retOptional.isPresent()) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "ReturnDeviceMaster not found");
		}
		ReturnDeviceMaster returnDeviceMaster = retOptional.get();

		if (returnDeviceMaster.getCurrentQuantity() < returnDeviceMaster.getRequestedQuantity()) {
			DeviceLite device = deviceLiteRepository.findByImeiOrIccidOrUuid(dto.getSearch().trim());
			if (device == null) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "Device not found");
			}
			if (!device.getStatus().equals(StatusMaster.ISSUED_DEVICES)) {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),"Device Not Issued Yet");
			}
			List<ReturnDevice> returnDevices = returnDeviceMaster.getReturnDevices();
			Optional<ReturnDevice> flag = returnDevices.stream().filter(o->o!= null && o.getImei().equalsIgnoreCase(device.getImeiNo())).findFirst();
			if (flag.isPresent()) {
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Device already added");
			}
			ReturnDevice returnDevice = new ReturnDevice();
			returnDevice.setBoxNo(device.getBoxCode());
			returnDevice.setCreatedAt(new Date());
			returnDevice.setCreatedBy(dto.getUserId());
			returnDevice.setIccid(device.getIccidNo());
			returnDevice.setImei(device.getImeiNo());
			returnDevice.setIsActive(true);
			if (returnDeviceMaster.getClient().getId().equals(device.getClientId())) {
				returnDevice.setIsDifferentClientDevice(false);
			} else {
				returnDevice.setIsDifferentClientDevice(true);
			}
			returnDevice.setIssuedAt(device.getIssueDate());
			returnDevice.setIssuedTo(device.getClientName());
			returnDevice.setMfgLotNo(device.getMfgLotId());
			returnDevice.setReturnDeviceMaster(returnDeviceMaster);
			ReturnDevice response = returnDeviceRepository.save(returnDevice);
			returnDeviceMaster.setCurrentQuantity(returnDeviceMaster.getCurrentQuantity()+1);
			returnDeviceMasterRepository.save(returnDeviceMaster);
			// create transaction
			device.setBoxCode(null);
			device.setClientId(null);
			device.setClientName(null);
			device.setIsReturnType(true);
			device.setIssueDate(null);
			device.setStatus(StatusMaster.DEVICE_PACKED);
			deviceLiteRepository.save(device);
			BoxDevice boxDevice = boxDeviceRepository.findByDeviceIdV2(device.getId());
			if (boxDevice != null) {
				boxDevice.setIsActive(false);
				boxDevice.setIsIssued(false);
				boxDevice.setIsPresent(false);
				boxDeviceRepository.save(boxDevice);
				Box box = boxDevice.getBox();
				box.setUpdatedAt(new Date());
				box.setUpdatedBy(new User(dto.getUserId()));
				box.setCurrentQuantity(box.getCurrentQuantity() != 0 ? box.getCurrentQuantity() - 1 : 0);
				box.setQuantity(box.getQuantity() != 0 ? box.getQuantity() - 1 : 0);
				if (box.getCurrentQuantity() == 0) {
					box.setIsActive(false);
				}
				boxRepository.save(box);
				Optional<IssuedList> issOptional = issuedRepository.findById(box.getIssuedList().getSerialNumber());
				if (issOptional.isPresent()) {
					IssuedList issued = issOptional.get();
					issued.setUpdatedAt(new Date());
					issued.setUpdatedBy(dto.getUserId());
					issued.setQuantity(issued.getQuantity() - 1);
					issuedRepository.save(issued);
					returnDevice.setInvoiceNo(issued.getIssueSlipNumber());
					response = returnDeviceRepository.save(returnDevice);
					return new Response<>(HttpStatus.OK.value(), "Device Successfully added",response);
				}else {
					return new Response<>(HttpStatus.OK.value(), "Device add but no Issue List found",response);
				}
				
			}else {
				return new Response<>(HttpStatus.OK.value(), "Device add but no box found",response);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"current quantity is greater or equal to requested quantity");
		}
	}

}
