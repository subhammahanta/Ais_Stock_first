package com.watsoo.device.management.dto;

import java.util.Date;

public class ChargesMasterDTO {
	
	private Long id;

	private String deviceIssue;

	private Double charges;

	private Long createdBy;

	private Date createdAt;

	private Boolean isActive;

	private Long updatedBy;

	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceIssue() {
		return deviceIssue;
	}

	public void setDeviceIssue(String deviceIssue) {
		this.deviceIssue = deviceIssue;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ChargesMasterDTO() {
		super();
	}

	public ChargesMasterDTO(Long id, String deviceIssue, Double charges, Long createdBy, Date createdAt,
			Boolean isActive, Long updatedBy, Date updatedAt) {
		super();
		this.id = id;
		this.deviceIssue = deviceIssue;
		this.charges = charges;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.isActive = isActive;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
	}
	
}
