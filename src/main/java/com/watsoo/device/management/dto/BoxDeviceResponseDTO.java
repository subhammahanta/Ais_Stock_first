package com.watsoo.device.management.dto;

import java.util.List;

public class BoxDeviceResponseDTO {

	private BoxDeviceDTO boxDeviceDTO;

	private List<ReConfigureDevicesDTO> reConfigureDevicesDTO;

	public BoxDeviceResponseDTO() {
		super();
	}

	public BoxDeviceResponseDTO(BoxDeviceDTO boxDeviceDTO, List<ReConfigureDevicesDTO> reConfigureDevicesDTO) {
		super();
		this.boxDeviceDTO = boxDeviceDTO;
		this.reConfigureDevicesDTO = reConfigureDevicesDTO;
	}

	public BoxDeviceDTO getBoxDeviceDTO() {
		return boxDeviceDTO;
	}

	public void setBoxDeviceDTO(BoxDeviceDTO boxDeviceDTO) {
		this.boxDeviceDTO = boxDeviceDTO;
	}

	public List<ReConfigureDevicesDTO> getReConfigureDevicesDTO() {
		return reConfigureDevicesDTO;
	}

	public void setReConfigureDevicesDTO(List<ReConfigureDevicesDTO> reConfigureDevicesDTO) {
		this.reConfigureDevicesDTO = reConfigureDevicesDTO;
	}

}
