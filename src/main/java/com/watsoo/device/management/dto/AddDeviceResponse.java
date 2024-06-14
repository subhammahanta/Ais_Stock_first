package com.watsoo.device.management.dto;

public class AddDeviceResponse {

	private Integer responseCode;

	private Object data;

	private String responseDescription;
	
	private Payment paymentObj;


	public Payment getPaymentObj() {
		return paymentObj;
	}

	public void setPaymentObj(Payment paymentObj) {
		this.paymentObj = paymentObj;
	}

	public AddDeviceResponse() {
		super();
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

}
