package com.watsoo.device.management.dto;

import java.util.List;

import com.watsoo.device.management.model.Device;

public class BoxDeviceDTO {
	
	private BoxDTO boxDTO;
	private List<Device> deviceList;
	public BoxDTO getBoxDTO() {
		return boxDTO;
	}
	public void setBoxDTO(BoxDTO boxDTO) {
		this.boxDTO = boxDTO;
	}
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	public BoxDeviceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoxDeviceDTO(BoxDTO boxDTO, List<Device> deviceList) {
		super();
		this.boxDTO = boxDTO;
		this.deviceList = deviceList;
	}
	
}
