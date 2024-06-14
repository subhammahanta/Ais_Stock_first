package com.watsoo.device.management.dto;

import java.util.List;

import com.watsoo.device.management.model.DeviceLot;
import com.watsoo.device.management.model.TestedDevice;

public class ResponseDTO {

	private Pagination<List<TestedDevice>> response;
	private DeviceLot lot;

	public ResponseDTO() {
		super();
	}

	public ResponseDTO(Pagination<List<TestedDevice>> response, DeviceLot lot) {
		super();
		this.response = response;
		this.lot = lot;
	}

	public Pagination<List<TestedDevice>> getResponse() {
		return response;
	}

	public void setResponse(Pagination<List<TestedDevice>> response) {
		this.response = response;
	}

	public DeviceLot getLot() {
		return lot;
	}

	public void setLot(DeviceLot lot) {
		this.lot = lot;
	}

}
