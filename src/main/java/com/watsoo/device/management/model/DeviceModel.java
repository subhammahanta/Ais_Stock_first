package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device_model")
public class DeviceModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "model")
	private String model;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "tac_no")
	private String tacNo;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	public DeviceModel() {
		super();
	}

	public DeviceModel(Long id, String model, Boolean isActive) {
		super();
		this.id = id;
		this.model = model;
		this.isActive = isActive;
	}

	public DeviceModel(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getTacNo() {
		return tacNo;
	}

	public void setTacNo(String tacNo) {
		this.tacNo = tacNo;
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

}
