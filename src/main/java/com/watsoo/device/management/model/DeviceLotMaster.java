package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.enums.CommandStatusEnum;

@Entity
@Table(name = "device_lot_master")
public class DeviceLotMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tested_device_id")
	private Long testedDeviceId;

	@Column(name = "lot_id")
	private Long lotId;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "command")
	private String command;

	@Column(name = "response")
	private String response;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private CommandStatusEnum status;
	
	@Column(name = "failure_reason")
	private String failureReason;

	public DeviceLotMaster() {
		super();
	}

	public DeviceLotMaster(Long id, Long testedDeviceId, Long lotId, Boolean isActive, Date createdAt, Long createdBy,
			String command, String response, CommandStatusEnum status) {
		super();
		this.id = id;
		this.testedDeviceId = testedDeviceId;
		this.lotId = lotId;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.command = command;
		this.response = response;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTestedDeviceId() {
		return testedDeviceId;
	}

	public void setTestedDeviceId(Long testedDeviceId) {
		this.testedDeviceId = testedDeviceId;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public CommandStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CommandStatusEnum status) {
		this.status = status;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

}
