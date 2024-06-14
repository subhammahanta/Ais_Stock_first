package com.watsoo.device.management.dto;

public class BoxRequestDTO {

	private String boxesList;

	private Integer requestedQuantity;

	public String getBoxesList() {
		return boxesList;
	}

	public void setBoxesList(String boxesList) {
		this.boxesList = boxesList;
	}

	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

}
