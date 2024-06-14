package com.watsoo.device.management.dto;

import java.util.List;

import com.watsoo.device.management.model.DeviceLot;
import com.watsoo.device.management.model.TestedDevice;

public class DeviceLotResponseDTO {

	private DeviceLot deviceLot;
	private List<TestedDevice> devices;

	public DeviceLot getDeviceLot() {
		return deviceLot;
	}

	public void setDeviceLot(DeviceLot deviceLot) {
		this.deviceLot = deviceLot;
	}

	public List<TestedDevice> getDevices() {
		return devices;
	}

	public void setDevices(List<TestedDevice> devices) {
		this.devices = devices;
	}

	public DeviceLotResponseDTO() {
		super();
	}

	public DeviceLotResponseDTO(DeviceLot deviceLot, List<TestedDevice> devices) {
		super();
		this.deviceLot = deviceLot;
		this.devices = devices;
	}

}
