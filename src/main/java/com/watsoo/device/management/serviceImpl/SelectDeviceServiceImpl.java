package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.dto.IssueDeviceDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.IssuedList;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.ClientRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.IssuedRepository;
import com.watsoo.device.management.repository.StateRepository;
import com.watsoo.device.management.service.SelectDeviceService;

@Service
public class SelectDeviceServiceImpl implements SelectDeviceService {

	@Autowired
	private IssuedRepository issuedRepository;

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private StateRepository stateRepository;

   @Override
   public Response<?> saveData(IssueDeviceDTO issuedListDTO) {
	   Response<Object> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
		IssuedList issuedList = new IssuedList();
		if (issuedListDTO.getClientId() != null && issuedListDTO.getClientId() > 0 && issuedListDTO.getBoxs() != null && issuedListDTO.getBoxs().size() > 0
				&& issuedListDTO.getStateId() != null && issuedListDTO.getStateId() > 0) {
			Long selectedBoxes = 0l;
			for (BoxDTO box : issuedListDTO.getBoxs()) {
				selectedBoxes = selectedBoxes + box.getDeviceIds().size();
			}
			if (issuedListDTO.getRequestDevices() != null && issuedListDTO.getRequestDevices() != selectedBoxes) {
				response.setData(null);
				response.setMessage("Select correct number of devices");
				response.setResponseCode(HttpStatus.OK.value());
			} else {
				for (BoxDTO box : issuedListDTO.getBoxs()) {
					// Optional<Box> boxOptional = boxRepository.findById(box.getId());
					// if (box.getDeviceIds().size()==boxOptional.get().getCurrentQuantity()) {
					if (box.getDeviceIds() != null && box.getDeviceIds().size() > 0) {
						for (Long deviceId : box.getDeviceIds()) {
							if (deviceId != null) {
								Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
								if (deviceOptional.isPresent()) {
									deviceOptional.get().setStatus(StatusMaster.BOX_DISPATCH_FULLY);
									deviceRepository.save(deviceOptional.get());
								}
							}
						}
					}

					// }//else {
					// get not selected devices
					List<Long> ids = boxRepository.getDeviceIds(box.getId());
					List<Long> notSelectedDevices = new ArrayList<>(ids);
					notSelectedDevices.removeAll(box.getDeviceIds());
					if (notSelectedDevices!=null && notSelectedDevices.size()>0) {
						for (Long id : ids) {
							Optional<Device> deviceOptional = deviceRepository.findById(id);
							if (deviceOptional.isPresent()) {
								deviceOptional.get().setStatus(StatusMaster.DEVICE_PACKED);
								deviceRepository.save(deviceOptional.get());
							}
						}
					}
//					if (ids != null && ids.size() > 0) {
//						for (Long id : ids) {
//							Optional<Device> deviceOptional = deviceRepository.findById(id);
//							if (deviceOptional.isPresent()) {
//								deviceOptional.get().setStatus(StatusMaster.DEVICE_PACKED);
//								deviceRepository.save(deviceOptional.get());
//							}
//						}
//					}

					// }
					Optional<Box> boxOptional = boxRepository.findById(box.getId());
					boxOptional.get().setCurrentQuantity((double) box.getDeviceIds().size());
					boxOptional.get().setStatus(StatusMaster.BOX_DISPATCH_FULLY);
					boxRepository.save(boxOptional.get());
				}
				response.setData(null);
				response.setMessage("Devices added successfully");
				response.setResponseCode(HttpStatus.OK.value());
			}
			// .set
			issuedList = convertToEntity(issuedListDTO);
			issuedRepository.save(issuedList);
		} else {
			response.setData(null);
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		}
		return response;
   }
   
   private IssuedList convertToEntity(IssueDeviceDTO dto) {
		Optional<Client> client = clientRepository.findById(dto.getClientId());
		//client.setId(dto.getClientId());
		Optional<State> state = stateRepository.findById(dto.getStateId());
		//state.setId(dto.getId());
		return new IssuedList(null, client.get(), state.get(), dto.getIssuedDate(), dto.getRequestDevices(), dto.getCreatedAt(),
				dto.getCreatedBy(), dto.getUpdatedAt(), dto.getUpdatedBy(), null, null);
	}
}