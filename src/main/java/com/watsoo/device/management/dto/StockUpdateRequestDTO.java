package com.watsoo.device.management.dto;

import java.util.Date;

public class StockUpdateRequestDTO {

	private Integer procuredQuantity;

	private Integer disbursedQuantity;

	private String tokenNumber;

	private String remarks;

	private Date updateDate;

	private Integer createdBy;

	private String createdUserName;

	private Boolean isProcured;

	private Boolean isDisbursed;

	private String sku;

	public Integer getProcuredQuantity() {
		return procuredQuantity;
	}

	public void setProcuredQuantity(Integer procuredQuantity) {
		this.procuredQuantity = procuredQuantity;
	}

	public Integer getDisbursedQuantity() {
		return disbursedQuantity;
	}

	public void setDisbursedQuantity(Integer disbursedQuantity) {
		this.disbursedQuantity = disbursedQuantity;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public Boolean getIsProcured() {
		return isProcured;
	}

	public void setIsProcured(Boolean isProcured) {
		this.isProcured = isProcured;
	}

	public Boolean getIsDisbursed() {
		return isDisbursed;
	}

	public void setIsDisbursed(Boolean isDisbursed) {
		this.isDisbursed = isDisbursed;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public StockUpdateRequestDTO() {
		super();
	}

	public StockUpdateRequestDTO(Integer procuredQuantity, Integer disbursedQuantity, String tokenNumber,
			String remarks, Date updateDate, Integer createdBy, String createdUserName, Boolean isProcured,
			Boolean isDisbursed, String sku) {
		super();
		this.procuredQuantity = procuredQuantity;
		this.disbursedQuantity = disbursedQuantity;
		this.tokenNumber = tokenNumber;
		this.remarks = remarks;
		this.updateDate = updateDate;
		this.createdBy = createdBy;
		this.createdUserName = createdUserName;
		this.isProcured = isProcured;
		this.isDisbursed = isDisbursed;
		this.sku = sku;
	}

}
