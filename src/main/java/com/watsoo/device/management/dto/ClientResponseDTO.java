package com.watsoo.device.management.dto;

public class ClientResponseDTO {

	private Integer responseCode;

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public ClientResponseDTO(Integer responseCode) {
		super();
		this.responseCode = responseCode;
	}

	public ClientResponseDTO() {
		super();
	}

}
