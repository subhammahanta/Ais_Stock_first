package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.RepairReplaceDeviceDTO;
import com.watsoo.device.management.dto.RepairReplaceDeviceMasterDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.Status;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.ChargesMaster;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.model.RepairDeviceCharges;
import com.watsoo.device.management.model.RepairReplaceDevice;
import com.watsoo.device.management.model.RepairReplaceDeviceMaster;
import com.watsoo.device.management.model.ReturnDeviceMaster;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.repository.ChargesMasterRepository;
import com.watsoo.device.management.repository.DeviceLiteRepository;
import com.watsoo.device.management.repository.RepairDeviceChargesRepository;
import com.watsoo.device.management.repository.RepairReplaceDeviceMasterRepository;
import com.watsoo.device.management.repository.RepairReplaceDeviceRepository;
import com.watsoo.device.management.service.RepairReplaceDeviceService;

@Service
public class RepairReplaceDeviceServiceImpl implements RepairReplaceDeviceService {

	@Autowired
	private RepairReplaceDeviceMasterRepository repairReplaceDeviceMasterRepository;

	@Autowired
	private DeviceLiteRepository deviceLiteRepository;

	@Autowired
	private RepairReplaceDeviceRepository repairReplaceDeviceRepository;

	@Autowired
	private ChargesMasterRepository chargesMasterRepository;

	@Autowired
	private RepairDeviceChargesRepository deviceChargesRepository;

	@Override
	public Response<?> repairDevices(GenericRequestBody request) {
		if (request.getRepairDeviceMasterId() == null) {
			if (request.getClientId() == null || request.getStateId() == null || request.getTotalDevice() == null
					|| request.getTotalDevice() <= 0 || request.getUserId() == null || request.getUserName() == null
					|| request.getUserName() == "") {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "add mandatory field");
			}
			//exist
			List<RepairReplaceDeviceMaster> returnDeviceMaster2 = repairReplaceDeviceMasterRepository.findByEwayBill(request.getEwayBillNo());
			if (!returnDeviceMaster2.isEmpty()) {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(), "EwayBill already exist");
			}
			RepairReplaceDeviceMaster repairReplaceDeviceMaster = new RepairReplaceDeviceMaster();
			repairReplaceDeviceMaster.setClient(new Client(request.getClientId()));
			repairReplaceDeviceMaster.setState(new State(request.getStateId()));
			repairReplaceDeviceMaster.setTotalDevice(request.getTotalDevice());
			repairReplaceDeviceMaster.setPendingDevice(request.getTotalDevice());
			repairReplaceDeviceMaster.setReplacedDevice(0);
			repairReplaceDeviceMaster.setCurrentQuantity(0);
			repairReplaceDeviceMaster.setRepairedDevice(0);
			repairReplaceDeviceMaster.setCreatedAt(new Date());
			repairReplaceDeviceMaster.setCreatedBy(request.getUserId());
			repairReplaceDeviceMaster.setCreatedUserName(request.getUserName());
			repairReplaceDeviceMaster.setEwayBillImage(request.getEwayBillImage());
			repairReplaceDeviceMaster.setEwayBillNo(request.getEwayBillNo());
			repairReplaceDeviceMaster.setIsActive(true);
			repairReplaceDeviceMaster.setTotalCost(0d);
			Random random = new Random();
			int randomNumber = random.nextInt(1000000000);
			String formattedNumber = String.format("%09d", randomNumber);
			repairReplaceDeviceMaster.setRepairCode(formattedNumber);
			RepairReplaceDeviceMaster obj = repairReplaceDeviceMasterRepository.save(repairReplaceDeviceMaster);
			return new Response<>(HttpStatus.CREATED.value(), "Request created", obj);
		} else {
			Optional<RepairReplaceDeviceMaster> rrdmOpt = repairReplaceDeviceMasterRepository
					.findById(request.getRepairDeviceMasterId());
			RepairReplaceDeviceMaster repairReplaceDeviceMaster = rrdmOpt.get();
			if (request.getTotalDevice() == null || request.getTotalDevice() <= 0
					|| request.getTotalDevice() < repairReplaceDeviceMaster.getReplacedDevice()
							+ repairReplaceDeviceMaster.getRepairedDevice()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "enter valid device count");
			}
			repairReplaceDeviceMaster.setTotalDevice(request.getTotalDevice());
			repairReplaceDeviceMaster.setEwayBillImage(request.getEwayBillImage());
			repairReplaceDeviceMaster.setEwayBillNo(request.getEwayBillNo());
			repairReplaceDeviceMaster.setUpdatedAt(new Date());
			repairReplaceDeviceMaster.setUpdatedBy(request.getUserId());
			repairReplaceDeviceMaster.setUpdatedUserName(request.getUserName());
			RepairReplaceDeviceMaster obj = repairReplaceDeviceMasterRepository.save(repairReplaceDeviceMaster);
			return new Response<>(HttpStatus.OK.value(), "Request updated", obj);
		}

	}

	@Override
	public Pagination<List<RepairReplaceDeviceMaster>> getAllRepairDeviceRequest(GenericRequestBody request) {
		Pagination<List<RepairReplaceDeviceMaster>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (request.getPageSize() != null && request.getPageNo() != null && request.getPageSize() > 0) {
				pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
			}
			Page<RepairReplaceDeviceMaster> page = repairReplaceDeviceMasterRepository.findAll(request, pageRequest);
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
	public Response<?> addRepairDevice(GenericRequestBody dto) {
		if (dto.getRepairDeviceMasterId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "RepairDeviceMasterId can not be null");
		}
		Optional<RepairReplaceDeviceMaster> repOptional = repairReplaceDeviceMasterRepository
				.findById(dto.getRepairDeviceMasterId());
		if (!repOptional.isPresent()) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "RepairReplaceDeviceMaster not found");
		}
		RepairReplaceDeviceMaster repairReplaceDeviceMaster = repOptional.get();

		if (repairReplaceDeviceMaster.getCurrentQuantity() < repairReplaceDeviceMaster.getTotalDevice()) {
			DeviceLite device = deviceLiteRepository.findByImeiOrIccidOrUuid(dto.getSearch().trim());
			if (device == null) {
				return new Response<>(HttpStatus.NOT_FOUND.value(), "Device not found");
			}
			if (!device.getStatus().equals(StatusMaster.ISSUED_DEVICES)) {
				return new Response<>(HttpStatus.EXPECTATION_FAILED.value(),"Device Not Issued Yet");
			}
			List<RepairReplaceDevice> repairReplaceDevices = repairReplaceDeviceMaster.getRepairReplaceDevices();
			Optional<RepairReplaceDevice> flag = repairReplaceDevices.stream()
					.filter(o -> o != null && o.getImei().equalsIgnoreCase(device.getImeiNo())).findFirst();
			if (flag.isPresent()) {
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "Device already added");
			}
			RepairReplaceDevice repairReplaceDevice = new RepairReplaceDevice();
			repairReplaceDevice.setBoxNo(device.getBoxCode());
			repairReplaceDevice.setCreatedAt(new Date());
			repairReplaceDevice.setCreatedBy(dto.getUserId());
			repairReplaceDevice.setIccid(device.getIccidNo());
			repairReplaceDevice.setImei(device.getImeiNo());
			repairReplaceDevice.setIsActive(true);
			if (repairReplaceDeviceMaster.getClient().getId().equals(device.getClientId())) {
				repairReplaceDevice.setIsDifferentClientDevice(false);
			} else {
				repairReplaceDevice.setIsDifferentClientDevice(true);
			}
			repairReplaceDevice.setIssuedAt(device.getIssueDate());
			repairReplaceDevice.setIssuedTo(device.getClientName());
			repairReplaceDevice.setMfgLotNo(device.getMfgLotId());
			repairReplaceDevice.setRepairReplaceDeviceMaster(repairReplaceDeviceMaster);
			repairReplaceDevice.setRepairCost(0d);
			repairReplaceDevice.setStatus(Status.PENDING);
			RepairReplaceDevice response = repairReplaceDeviceRepository.save(repairReplaceDevice);
			repairReplaceDeviceMaster.setCurrentQuantity(repairReplaceDeviceMaster.getCurrentQuantity() + 1);
			repairReplaceDeviceMasterRepository.save(repairReplaceDeviceMaster);
			device.setIsRepairType(true);
			device.setUpdatedAt(new Date());
			deviceLiteRepository.save(device);
			return new Response<>(HttpStatus.CREATED.value(), "Device added successfully", response);
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"current quantity is greater or equal to requested quantity");
		}
	}

	@Override
	public Response<?> repairDevice(GenericRequestBody dto) {
		if (dto.getRepairDeviceId() == null || dto.getRepairDeviceMasterId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"RepairDeviceMasterId and RepairDeviceId can not be null");
		}
		RepairReplaceDevice repairDevice = repairReplaceDeviceRepository
				.findByRepairDeviceIdAndRepairDeviceMasterId(dto.getRepairDeviceId(), dto.getRepairDeviceMasterId());

		if (repairDevice == null) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Device not found");
		}

		RepairReplaceDeviceMaster repairReplaceDeviceMaster = repairDevice.getRepairReplaceDeviceMaster();
		Optional<DeviceLite> deviceOpt = deviceLiteRepository.findByImeiNo(repairDevice.getImei());
		DeviceLite device = deviceOpt.get();
		if (dto.getOpenBoxImageBeforeTesting() != null && dto.getOpenBoxImageBeforeTesting() != "") {
			repairDevice.setOpenBoxImageBeforeTesting(dto.getOpenBoxImageBeforeTesting());
		}
		if (dto.getCloseBoxImageBeforeTesting() != null && dto.getCloseBoxImageBeforeTesting() != "") {
			repairDevice.setCloseBoxImageBeforeTesting(dto.getCloseBoxImageBeforeTesting());
		}
		if (dto.getOpenBoxImageAfterTesting() != null && dto.getOpenBoxImageAfterTesting() != "") {
			repairDevice.setOpenBoxImageAfterTesting(dto.getOpenBoxImageAfterTesting());	
		}
		if (dto.getCloseBoxImageAfterTesting() != null && dto.getCloseBoxImageAfterTesting() != "") {
			repairDevice.setCloseBoxImageAfterTesting(dto.getCloseBoxImageAfterTesting());
		}
		if (dto.getIsRejected() != null && !dto.getIsRejected()) {
			// repair
			if (!repairDevice.getStatus().equals(Status.PENDING)) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "DEVICE ALREADY REPAIRED OR REPLACED");
			}
			if (!dto.getChargesId().isEmpty()) {
				List<ChargesMaster> charges = chargesMasterRepository.findAllByChargesId(dto.getChargesId());
				List<RepairDeviceCharges> list = new ArrayList<>();
				Double repairCost = 0d;
				for (ChargesMaster chargesMaster : charges) {
					RepairDeviceCharges deviceCharges = new RepairDeviceCharges();
					deviceCharges.setChargesMaster(chargesMaster);
					deviceCharges.setCreatedAt(new Date());
					deviceCharges.setCreatedBy(dto.getUserId());
					deviceCharges.setIsActive(true);
					deviceCharges.setRepairReplaceDevice(repairDevice);
					list.add(deviceCharges);
					repairCost = repairCost + chargesMaster.getCharges();
				}
				deviceChargesRepository.saveAll(list);
				repairDevice.setRepairCost(repairCost);

			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "ADD DEVICE CHARGES");
			}
			device.setIsRepairType(true);
			device.setUpdatedAt(new Date());
			repairDevice.setRepairedAt(new Date());
			repairDevice.setRepairedBy(dto.getUserId());
			repairDevice.setRepairedByUser(dto.getUserName());
			repairDevice.setStatus(Status.TESTING_PENDING);
			repairReplaceDeviceMaster.setPendingDevice(repairReplaceDeviceMaster.getPendingDevice() - 1);
			repairReplaceDeviceMaster.setRepairedDevice(repairReplaceDeviceMaster.getRepairedDevice() + 1);
			repairReplaceDeviceMaster
					.setTotalCost(repairReplaceDeviceMaster.getTotalCost() + repairDevice.getRepairCost());
		} else if (dto.getIsRejected() != null && dto.getIsRejected()) {
			// replace
			if (!repairDevice.getStatus().equals(Status.PENDING)) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "DEVICE ALREADY REPAIRED OR REPLACED");
			}
			Optional<DeviceLite> optional = deviceLiteRepository.findById(dto.getReplacedByDeviceId());
			DeviceLite deviceLite = optional.get();
			device.setIsReplaceType(true);
			device.setReplacedByDeviceId(deviceLite.getId());
			deviceLite.setStatus(StatusMaster.REPLACED_DEVICE);
			deviceLite.setClientId(device.getClientId());
			// deviceLite.setClientName(device.getClientName());
			deviceLite.setState(device.getState());
			deviceLite.setClientNames(device.getClientNames());
			deviceLite.setIssueDate(new Date());
			deviceLite.setUpdatedAt(new Date());
			deviceLite.setModifiedBy(dto.getUserId());
			repairDevice.setRejectedAt(new Date());
			repairDevice.setRejectedBy(dto.getUserId());
			repairDevice.setRejectedByUser(dto.getUserName());
			repairDevice.setStatus(Status.REPLACED);
			repairDevice.setReplacedByImei(deviceLite.getImeiNo());
			ChargesMaster cost = chargesMasterRepository.findNewDeviceCost("newDevice");
			repairDevice.setRepairCost(cost.getCharges());
			repairReplaceDeviceMaster.setReplacedDevice(repairReplaceDeviceMaster.getReplacedDevice() + 1);
			repairReplaceDeviceMaster.setPendingDevice(repairReplaceDeviceMaster.getPendingDevice() - 1);
			repairReplaceDeviceMaster
					.setTotalCost(repairReplaceDeviceMaster.getTotalCost() + repairDevice.getRepairCost());
			RepairDeviceCharges deviceCharges = new RepairDeviceCharges();
			deviceCharges.setCreatedAt(new Date());
			deviceCharges.setCreatedBy(dto.getUserId());
			deviceCharges.setIsActive(true);
			deviceCharges.setChargesMaster(cost);
			deviceCharges.setRepairReplaceDevice(repairDevice);
			deviceChargesRepository.save(deviceCharges);
			deviceLiteRepository.save(deviceLite);
		}
		deviceLiteRepository.save(device);
		repairReplaceDeviceMasterRepository.save(repairReplaceDeviceMaster);
		repairReplaceDeviceRepository.save(repairDevice);
		return new Response<>(HttpStatus.OK.value(), "Device Updated successfully");
	}

	@Override
	public Pagination<List<RepairReplaceDeviceMasterDTO>> getAllRepairDeviceRequestV2(GenericRequestBody request) {
		Pagination<List<RepairReplaceDeviceMasterDTO>> response = new Pagination<>();
		try {
			Pageable pageRequest = Pageable.unpaged();
			if (request.getPageSize() != null && request.getPageNo() != null && request.getPageSize() > 0) {
				pageRequest = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
			}
			Page<RepairReplaceDeviceMaster> page = repairReplaceDeviceMasterRepository.findAll(request, pageRequest);
			List<RepairReplaceDeviceMasterDTO> resp = page.getContent().stream().map(e -> e.convertToDTO()).collect(Collectors.toList());
			response.setData(resp);
			response.setNumberOfElements(page.getNumberOfElements());
			response.setTotalElements(page.getTotalElements());
			response.setTotalPages(page.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response<?> getRepairCharges(GenericRequestBody dto) {
		if (dto.getRepairDeviceId() == null || dto.getRepairDeviceMasterId() == null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),
					"RepairDeviceMasterId and RepairDeviceId can not be null");
		}
		RepairReplaceDevice repairDevice = repairReplaceDeviceRepository
				.findByRepairDeviceIdAndRepairDeviceMasterId(dto.getRepairDeviceId(), dto.getRepairDeviceMasterId());

		if (repairDevice == null) {
			return new Response<>(HttpStatus.NOT_FOUND.value(), "Device not found");
		}
		RepairReplaceDeviceDTO response = repairDevice.convertToDTO();
		//RepairReplaceDeviceMaster repairReplaceDeviceMaster = repairDevice.getRepairReplaceDeviceMaster();
		return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), response);
	}

}
