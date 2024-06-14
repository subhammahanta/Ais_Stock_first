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
@Table(name = "master_command")
public class Command implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "command")
	private String command;

	@Column(name = "response")
	private String response;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "key_to_verify")
	private String keyToVerify;
	
	@Column(name = "sequence_no")
	private Integer sequenceNo;
	
	@Column(name = "waiting_time")
	private Integer waitingTime;
	
	@Column(name = "is_after_model_config")
	private Boolean isAfterModelConfig;

	public Command() {
		super();
	}

	public Command(Long id, String command, String response, Boolean isActive, Date createdAt, Long createdBy,
			String keyToVerify, Integer sequenceNo) {
		super();
		this.id = id;
		this.command = command;
		this.response = response;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.keyToVerify = keyToVerify;
		this.sequenceNo = sequenceNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getKeyToVerify() {
		return keyToVerify;
	}

	public void setKeyToVerify(String keyToVerify) {
		this.keyToVerify = keyToVerify;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Integer getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Boolean getIsAfterModelConfig() {
		return isAfterModelConfig;
	}

	public void setIsAfterModelConfig(Boolean isAfterModelConfig) {
		this.isAfterModelConfig = isAfterModelConfig;
	}

}
