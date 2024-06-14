package com.watsoo.device.management.dto;

public class StockUpdateResponseDTO {

	private int responseCode;

	private String message;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StockUpdateResponseDTO() {
		super();
	}

	public StockUpdateResponseDTO(int responseCode, String message) {
		super();
		this.responseCode = responseCode;
		this.message = message;
	}

}