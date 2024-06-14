package com.watsoo.device.management.dto;

import java.util.List;

public class StoreResponseDTO{
    public String response_code;
    public String message;
    public List<MaterialDTO> data;
	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<MaterialDTO> getData() {
		return data;
	}
	public void setData(List<MaterialDTO> data) {
		this.data = data;
	}
	public StoreResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StoreResponseDTO(String response_code, String message, List<MaterialDTO> data) {
		super();
		this.response_code = response_code;
		this.message = message;
		this.data = data;
	}
    
    
}