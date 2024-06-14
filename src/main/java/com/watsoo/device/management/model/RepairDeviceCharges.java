package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watsoo.device.management.dto.RepairDeviceChargesDTO;

@Entity
@Table(name = "repair_device_charges")
public class RepairDeviceCharges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	//@OneToOne
	@JoinColumn(name = "repair_replace_device_id")
	//@JsonBackReference
	@JsonIgnore
	private RepairReplaceDevice repairReplaceDevice;

	//@ManyToOne(fetch = FetchType.EAGER)
	//@JsonBackReference
	@OneToOne
	//@JsonIgnore
	@JoinColumn(name = "charges_master_id")
	private ChargesMaster chargesMaster;

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

	public RepairDeviceCharges() {
		super();
	}

	public RepairDeviceCharges(Long id, RepairReplaceDevice repairReplaceDevice, ChargesMaster chargesMaster,
			Long createdBy, Date createdAt, Boolean isActive, Long updatedBy, Date updatedAt) {
		super();
		this.id = id;
		this.repairReplaceDevice = repairReplaceDevice;
		this.chargesMaster = chargesMaster;
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

	public RepairReplaceDevice getRepairReplaceDevice() {
		return repairReplaceDevice;
	}

	public void setRepairReplaceDevice(RepairReplaceDevice repairReplaceDevice) {
		this.repairReplaceDevice = repairReplaceDevice;
	}

	public ChargesMaster getChargesMaster() {
		return chargesMaster;
	}

	public void setChargesMaster(ChargesMaster chargesMaster) {
		this.chargesMaster = chargesMaster;
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
	
	public RepairDeviceChargesDTO convertToDTO() {
		//to-do
		return new RepairDeviceChargesDTO(this.getId(), null,chargesMaster.convertToDTO(), this.getCreatedBy(), this.getCreatedAt(), this.getIsActive(), this.getUpdatedBy(), this.getUpdatedAt());
		
	}
}
