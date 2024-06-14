package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.UnBoxDeviceDto;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.BoxDevice;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.UnBoxDevice;
import com.watsoo.device.management.repository.BoxDeviceRepository;
import com.watsoo.device.management.repository.BoxRepository;
import com.watsoo.device.management.repository.DeviceRepository;
import com.watsoo.device.management.repository.UnBoxRepository;
import com.watsoo.device.management.service.UnBoxService;

@Service
public class UnBoxServiceImpl implements UnBoxService{
	
	@Autowired
	private UnBoxRepository unBoxRepository;
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private BoxDeviceRepository boxDeviceRepository;
	@Autowired
	private DeviceRepository deviceRepo;

	@Override
	public UnBoxDeviceDto unBoxDevice(Long boxId) {
		
		Optional<Box> boxOptional = boxRepository.findById(boxId);
	
		try {
		    Box box = boxOptional.get();
		    
		    if (box.getStatus() == StatusMaster.BOX_IN_STOCK) {
		        Double boxQuantity = box.getQuantity();
		        String boxNumber = box.getBoxNo();
	
		        List<BoxDevice> boxDeviceList = boxDeviceRepository.findAllByIds(boxId);
		        List<Long> deviceIds = boxDeviceList.stream()
		                .filter(o -> o.getDevice().getId() != null)
		                .map(o -> o.getDevice().getId())
		                .collect(Collectors.toList());
	
		        List<Device> devices = deviceRepo.findAllByDeviceIds(deviceIds);
		        List<Device> deviceList = new ArrayList<>();
	
		        for (Device device : devices) {
		            device.setStatus(StatusMaster.DEVICE_PACKED);
		            device.setUpdatedAt(new Date());
		            deviceList.add(device);
		        }
	
		        deviceRepo.saveAll(deviceList);
		        if(boxId != null || boxId == box.getId()) {
		        		boxRepository.deleteByBoxId(boxId);
		        		boxDeviceRepository.deleteByBoxId(boxId);
		        }
	
		        UnBoxDevice unBoxDevice = new UnBoxDevice();
		        unBoxDevice.setBoxNumber(boxNumber);
		        unBoxDevice.setUnboxDate(new Date());
//		        extra some work setUnBoxBye
		        unBoxDevice.setUnboxBy(null);
		        unBoxDevice.setQuantity(boxQuantity);
		        
		        UnBoxDevice unBoxDeviceDetails = unBoxRepository.save(unBoxDevice);
		        
		        UnBoxDeviceDto unBoxDeviceDto = unBoxDeviceDetails.convertToDto();
		        return unBoxDeviceDto;
		        
		    	} else {
		    		return null;
		    	}
		} catch (Exception e) {
		    e.printStackTrace(); 
		    return null;
	    }
    }
}
