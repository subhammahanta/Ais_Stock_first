package com.watsoo.device.management.dto;

public class RequestCommandReponse {
	
	private int responseCode;
	
	private String message;
	
	private CommandRequestDTO data;

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

	public CommandRequestDTO getData() {
		return data;
	}

	public void setData(CommandRequestDTO data) {
		this.data = data;
	}

}
