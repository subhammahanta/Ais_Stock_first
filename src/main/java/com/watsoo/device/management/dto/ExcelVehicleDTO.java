package com.watsoo.device.management.dto;

import java.util.Date;

import com.watsoo.device.management.model.State;

public class ExcelVehicleDTO {

	private Long id;
	private String imeiNo;
	private String ccidNo;
	private String uidNo;
	private String sim1Number;
	private String sim2Number;
	private Date sim1ActivationDate;
	private Date sim2ActivationDate;
	private Date deviceIssueDate;
	private Date deviceActivationDate;
	private Date deviceExpiryDate;
	private String clientName;
	private String state;
	private Date sim1ExpireDate;
	private Date sim2ExpireDate;
	private String sim1Operator;
	private String sim2Operator;
	private String simProvider;
	private String sellPerson;
	private Long clientId;
	private State stateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getCcidNo() {
		return ccidNo;
	}

	public void setCcidNo(String ccidNo) {
		this.ccidNo = ccidNo;
	}

	public String getUidNo() {
		return uidNo;
	}

	public void setUidNo(String uidNo) {
		this.uidNo = uidNo;
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

	public Date getSim1ActivationDate() {
		return sim1ActivationDate;
	}

	public void setSim1ActivationDate(Date sim1ActivationDate) {
		this.sim1ActivationDate = sim1ActivationDate;
	}

	public Date getSim2ActivationDate() {
		return sim2ActivationDate;
	}

	public void setSim2ActivationDate(Date sim2ActivationDate) {
		this.sim2ActivationDate = sim2ActivationDate;
	}

	public Date getDeviceIssueDate() {
		return deviceIssueDate;
	}

	public void setDeviceIssueDate(Date deviceIssueDate) {
		this.deviceIssueDate = deviceIssueDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getSim1ExpireDate() {
		return sim1ExpireDate;
	}

	public void setSim1ExpireDate(Date sim1ExpireDate) {
		this.sim1ExpireDate = sim1ExpireDate;
	}

	public Date getSim2ExpireDate() {
		return sim2ExpireDate;
	}

	public void setSim2ExpireDate(Date sim2ExpireDate) {
		this.sim2ExpireDate = sim2ExpireDate;
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

	public Date getDeviceActivationDate() {
		return deviceActivationDate;
	}

	public void setDeviceActivationDate(Date deviceActivationDate) {
		this.deviceActivationDate = deviceActivationDate;
	}

	public Date getDeviceExpiryDate() {
		return deviceExpiryDate;
	}

	public void setDeviceExpiryDate(Date deviceExpiryDate) {
		this.deviceExpiryDate = deviceExpiryDate;
	}

	public String getSimProvider() {
		return simProvider;
	}

	public void setSimProvider(String simProvider) {
		this.simProvider = simProvider;
	}

	public String getSellPerson() {
		return sellPerson;
	}

	public void setSellPerson(String sellPerson) {
		this.sellPerson = sellPerson;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public State getStateId() {
		return stateId;
	}

	public void setStateId(State stateId) {
		this.stateId = stateId;
	}

	public ExcelVehicleDTO() {
		super();
	}

	public ExcelVehicleDTO(Long id, String imeiNo, String ccidNo, String uidNo, String sim1Number, String sim2Number,
			Date sim1ActivationDate, Date sim2ActivationDate, Date deviceIssueDate, Date deviceActivationDate,
			Date deviceExpiryDate, String clientName, String state, Date sim1ExpireDate, Date sim2ExpireDate,
			String sim1Operator, String sim2Operator, String simProvider, String sellPerson) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.ccidNo = ccidNo;
		this.uidNo = uidNo;
		this.sim1Number = sim1Number;
		this.sim2Number = sim2Number;
		this.sim1ActivationDate = sim1ActivationDate;
		this.sim2ActivationDate = sim2ActivationDate;
		this.deviceIssueDate = deviceIssueDate;
		this.deviceActivationDate = deviceActivationDate;
		this.deviceExpiryDate = deviceExpiryDate;
		this.clientName = clientName;
		this.state = state;
		this.sim1ExpireDate = sim1ExpireDate;
		this.sim2ExpireDate = sim2ExpireDate;
		this.sim1Operator = sim1Operator;
		this.sim2Operator = sim2Operator;
		this.simProvider = simProvider;
		this.sellPerson = sellPerson;
	}

}
