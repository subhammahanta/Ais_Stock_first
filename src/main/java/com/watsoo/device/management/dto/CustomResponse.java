package com.watsoo.device.management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {

	private Integer status;

	private Object data;

	private String message;

	private String error;

	private Integer size;

	private Long totalRecords;

	public CustomResponse() {
		super();
	}

	public CustomResponse(Integer status, Object data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public CustomResponse(Integer status, Object data, String message, String error) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
		this.error = error;
	}

	public CustomResponse(Integer status, Object data, String message, String error, Integer size) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
		this.error = error;
		this.size = size;
	}

	public CustomResponse(Integer status, Object data, String message, String error, Integer size, Long totalRecords) {
		this.status = status;
		this.data = data;
		this.message = message;
		this.error = error;
		this.size = size;
		this.totalRecords = totalRecords;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

}
