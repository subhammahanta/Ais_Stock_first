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
@Table(name = "model_config")
public class ModelConfig implements Serializable {

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

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "key_to_verify")
	private String keyToVerify;

	@Column(name = "model_id")
	private Long modelId;

	@Column(name = "state_id")
	private Long stateId;

	@Column(name = "model_config_sequence_no")
	private Integer modelConfigSequenceNo;

	@Column(name = "provider_id")
	private Long providerId;

	@Column(name = "operator_id")
	private Long operatorId;
	
	@Column(name = "client_id")
	private Long clientId;
	
	@Column(name = "is_default")
	private Boolean isDefault;
	
	@Column(name = "waiting_time")
	private Integer waitingTime;
	
	public ModelConfig() {
		super();
	}

	public ModelConfig(Long id, String command, String response, Boolean isActive, Date createdAt, Long createdBy,
			Date updatedAt, Long updatedBy, String keyToVerify, Long modelId, Long stateId,
			Integer modelConfigSequenceNo, Long providerId, Long operatorId, Long clientId) {
		super();
		this.id = id;
		this.command = command;
		this.response = response;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.keyToVerify = keyToVerify;
		this.modelId = modelId;
		this.stateId = stateId;
		this.modelConfigSequenceNo = modelConfigSequenceNo;
		this.providerId = providerId;
		this.operatorId = operatorId;
		this.clientId = clientId;
	}

	public ModelConfig(Long id) {
		super();
		this.id = id;
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getKeyToVerify() {
		return keyToVerify;
	}

	public void setKeyToVerify(String keyToVerify) {
		this.keyToVerify = keyToVerify;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Integer getModelConfigSequenceNo() {
		return modelConfigSequenceNo;
	}

	public void setModelConfigSequenceNo(Integer modelConfigSequenceNo) {
		this.modelConfigSequenceNo = modelConfigSequenceNo;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
	}

}
