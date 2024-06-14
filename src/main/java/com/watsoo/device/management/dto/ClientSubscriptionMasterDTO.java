package com.watsoo.device.management.dto;

import java.util.Date;

import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.StatePlatformMaster;
import com.watsoo.device.management.model.SubscriptionMaster;
import com.watsoo.device.management.model.SubscriptionType;

public class ClientSubscriptionMasterDTO {

	private Integer id;

	private Long clientId;

	private Integer subsMasterId;

	private Integer statePlatformMstrId;

	private String requestCode;

	private Double amount;

	private Long stateId;

	private String createdByName;

	private Boolean isActive;

	private Client client;

	private SubscriptionMaster subscriptionMaster;

	private StatePlatformMaster statePlatformMstr;

	private State state;

	private Date createdAt;

	private Long createdBy;

	private Date updatedAt;

	private Long updatedBy;

	private String updatedByName;

	private Integer totalDays;

	private String company;

	private SubscriptionType subscriptionType;

	private String stateName;

	private String type;

	private String platform;

	public ClientSubscriptionMasterDTO() {
		super();
	}

	public ClientSubscriptionMasterDTO(Integer id, Long clientId, Integer subsMasterId, Integer statePlatformMstrId,
			String requestCode, Double amount, Long stateId, String createdByName, Boolean isActive) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.subsMasterId = subsMasterId;
		this.statePlatformMstrId = statePlatformMstrId;
		this.requestCode = requestCode;
		this.amount = amount;
		this.stateId = stateId;
		this.createdByName = createdByName;
		this.isActive = isActive;

	}

	public ClientSubscriptionMasterDTO(Integer totalDays, Double defaultAmount, SubscriptionType subscriptionType2,
			SubscriptionMaster e) {
		this.totalDays = totalDays;
		this.amount = defaultAmount;
		this.subscriptionType = subscriptionType2;
		this.subscriptionMaster = e;
	}

	public ClientSubscriptionMasterDTO(String requestCode, String company, String stateName, String type,
			Integer totalDays, Double amount, String createdByName, Boolean isActive, String platform) {
		super();
		this.requestCode = requestCode;
		this.company = company;
		this.stateName = stateName;
		this.type = type;
		this.totalDays = totalDays;
		this.amount = amount;
		this.createdByName = createdByName;
		this.isActive = isActive;
		this.platform = platform;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Integer getSubsMasterId() {
		return subsMasterId;
	}

	public void setSubsMasterId(Integer subsMasterId) {
		this.subsMasterId = subsMasterId;
	}

	public Integer getStatePlatformMstrId() {
		return statePlatformMstrId;
	}

	public void setStatePlatformMstrId(Integer statePlatformMstrId) {
		this.statePlatformMstrId = statePlatformMstrId;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
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

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public SubscriptionMaster getSubscriptionMaster() {
		return subscriptionMaster;
	}

	public void setSubscriptionMaster(SubscriptionMaster subscriptionMaster) {
		this.subscriptionMaster = subscriptionMaster;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public StatePlatformMaster getStatePlatformMstr() {
		return statePlatformMstr;
	}

	public void setStatePlatformMstr(StatePlatformMaster statePlatformMstr) {
		this.statePlatformMstr = statePlatformMstr;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}