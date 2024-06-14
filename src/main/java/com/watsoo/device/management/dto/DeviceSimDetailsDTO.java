package com.watsoo.device.management.dto;

import java.util.Date;

public class DeviceSimDetailsDTO {
	private String imeiNumber;
	private String sim1Number;
	private String sim2Number;
	private String sim1Operator;
	private String sim2Operator;
	private Date sim1ActivationDate;
	private Date sim1ExpiryDate;
	private Date sim2ActivationDate;
	private Date sim2ExpiryDate;
	private String response;
	private Boolean isUpdated;
	public DeviceSimDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeviceSimDetailsDTO(String imeiNumber, String sim1Number, String sim2Number, String sim1Operator,
			String sim2Operator, Date sim1ActivationDate, Date sim1ExpiryDate, Date sim2ActivationDate,
			Date sim2ExpiryDate, String response) {
		super();
		this.imeiNumber = imeiNumber;
		this.sim1Number = sim1Number;
		this.sim2Number = sim2Number;
		this.sim1Operator = sim1Operator;
		this.sim2Operator = sim2Operator;
		this.sim1ActivationDate = sim1ActivationDate;
		this.sim1ExpiryDate = sim1ExpiryDate;
		this.sim2ActivationDate = sim2ActivationDate;
		this.sim2ExpiryDate = sim2ExpiryDate;
		this.response = response;
	}
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	public String getSim1Number() {
		return sim1Number;
	}
	public void setSim1Number(String sim1Number) {
		this.sim1Number = sim1Number;
	}
	public String getSim2Number() {
		return sim2Number;
	}
	public void setSim2Number(String sim2Number) {
		this.sim2Number = sim2Number;
	}
	public String getSim1Operator() {
		return sim1Operator;
	}
	public void setSim1Operator(String sim1Operator) {
		this.sim1Operator = sim1Operator;
	}
	public String getSim2Operator() {
		return sim2Operator;
	}
	public void setSim2Operator(String sim2Operator) {
		this.sim2Operator = sim2Operator;
	}
	public Date getSim1ActivationDate() {
		return sim1ActivationDate;
	}
	public void setSim1ActivationDate(Date sim1ActivationDate) {
		this.sim1ActivationDate = sim1ActivationDate;
	}
	public Date getSim1ExpiryDate() {
		return sim1ExpiryDate;
	}
	public void setSim1ExpiryDate(Date sim1ExpiryDate) {
		this.sim1ExpiryDate = sim1ExpiryDate;
	}
	public Date getSim2ActivationDate() {
		return sim2ActivationDate;
	}
	public void setSim2ActivationDate(Date sim2ActivationDate) {
		this.sim2ActivationDate = sim2ActivationDate;
	}
	public Date getSim2ExpiryDate() {
		return sim2ExpiryDate;
	}
	public void setSim2ExpiryDate(Date sim2ExpiryDate) {
		this.sim2ExpiryDate = sim2ExpiryDate;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Boolean getIsUpdated() {
		return isUpdated;
	}
	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
	
}
