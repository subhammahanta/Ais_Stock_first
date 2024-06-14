package com.watsoo.device.management.dto;

import java.util.Date;

public class IccidMasterDTO {

	private Long id;

	private String iccidNo;

	private String provider;

	private String sim1Operator;

	private String sim2Operator;

	private String sim1;

	private String sim2;

	private Date simActivationDate;

	private Date simExpiryDate;

	private Date createdAt;

	private Long createdBy;
	
	private String iccidOperator;

	public IccidMasterDTO() {
		super();
	}

	public IccidMasterDTO(Long id, String iccidNo, String provider, String sim1Operator, String sim2Operator,
			String sim1, String sim2, Date simActivationDate, Date simExpiryDate, Date createdAt, Long createdBy) {
		super();
		this.id = id;
		this.iccidNo = iccidNo;
		this.provider = provider;
		this.sim1Operator = sim1Operator;
		this.sim2Operator = sim2Operator;
		this.sim1 = sim1;
		this.sim2 = sim2;
		this.simActivationDate = simActivationDate;
		this.simExpiryDate = simExpiryDate;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIccidNo() {
		return iccidNo;
	}

	public void setIccidNo(String iccidNo) {
		this.iccidNo = iccidNo;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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

	public String getSim1() {
		return sim1;
	}

	public void setSim1(String sim1) {
		this.sim1 = sim1;
	}

	public String getSim2() {
		return sim2;
	}

	public void setSim2(String sim2) {
		this.sim2 = sim2;
	}

	public Date getSimActivationDate() {
		return simActivationDate;
	}

	public void setSimActivationDate(Date simActivationDate) {
		this.simActivationDate = simActivationDate;
	}

	public Date getSimExpiryDate() {
		return simExpiryDate;
	}

	public void setSimExpiryDate(Date simExpiryDate) {
		this.simExpiryDate = simExpiryDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getIccidOperator() {
		return iccidOperator;
	}

	public void setIccidOperator(String iccidOperator) {
		this.iccidOperator = iccidOperator;
	}

}
