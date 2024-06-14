package com.watsoo.device.management.dto;

import java.util.Date;

public class RepairDeviceChargesDTO {
private Long id;
	
	private RepairReplaceDeviceDTO repairReplaceDevice;

	private ChargesMasterDTO chargesMaster;

	private Long createdBy;

	private Date createdAt;

	private Boolean isActive;

	private Long updatedBy;

	private Date updatedAt;

	public RepairDeviceChargesDTO() {
		super();
	}

	public RepairDeviceChargesDTO(Long id, RepairReplaceDeviceDTO repairReplaceDevice, ChargesMasterDTO chargesMaster,
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

	public RepairReplaceDeviceDTO getRepairReplaceDevice() {
		return repairReplaceDevice;
	}

	public void setRepairReplaceDevice(RepairReplaceDeviceDTO repairReplaceDevice) {
		this.repairReplaceDevice = repairReplaceDevice;
	}

	public ChargesMasterDTO getChargesMaster() {
		return chargesMaster;
	}

	public void setChargesMaster(ChargesMasterDTO chargesMaster) {
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
	
}
