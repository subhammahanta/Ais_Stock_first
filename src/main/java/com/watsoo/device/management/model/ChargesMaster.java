package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.ChargesMasterDTO;

@Entity
@Table(name = "charges_master")
public class ChargesMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "device_issue")
	private String deviceIssue;

	@Column(name = "charges")
	private Double charges;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	public ChargesMaster() {
		super();
	}

	public ChargesMaster(Long id, String deviceIssue, Double charges, Long createdBy, Date createdAt, Boolean isActive,
			Long updatedBy, Date updatedAt) {
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

	public ChargesMasterDTO convertToDTO() {
		return new ChargesMasterDTO(this.getId(), this.getDeviceIssue(), this.getCharges(), this.getCreatedBy(),
				this.getCreatedAt(), this.getIsActive(), this.getUpdatedBy(), this.getUpdatedAt());
	}

}
