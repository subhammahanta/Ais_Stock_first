package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.watsoo.device.management.dto.ClientSubscriptionMasterDTO;

@Entity
@Table(name = "client_subscription_master")
public class ClientSubscriptionMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@Index(name = "client_id")
	private Client client;

	@OneToOne
	@JoinColumn(name = "subscription_mstr_id")
	private SubscriptionMaster subscriptionMaster;

	@OneToOne
	@JoinColumn(name = "state_platform_mstr_id")
	private StatePlatformMaster statePlatformMstr;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "request_code")
	private String requestCode;

	@OneToOne
	@JoinColumn(name = "state_id")
	@Index(name = "state_id")
	private State state;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_by_name")
	private String createdByName;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_by_name")
	private String updatedByName;

	@Column(name = "is_active")
	private Boolean isActive;
	
	@OneToOne
	@JoinColumn(name = "platform_id")
	private Platform platform;

	public ClientSubscriptionMaster() {
		super();
	}

	public ClientSubscriptionMaster(Integer id, Client client, SubscriptionMaster subscriptionMaster,
			StatePlatformMaster statePlatformMstr, Double amount, String requestCode, State state, Date createdAt,
			Long createdBy, String createdByName, Date updatedAt, Long updatedBy, String updatedByName,
			Boolean isActive) {
		super();
		this.id = id;
		this.client = client;
		this.subscriptionMaster = subscriptionMaster;
		this.statePlatformMstr = statePlatformMstr;
		this.amount = amount;
		this.requestCode = requestCode;
		this.state = state;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.createdByName = createdByName;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.updatedByName = updatedByName;
		this.isActive = isActive;
	}

	public ClientSubscriptionMaster(Integer id, Client client, SubscriptionMaster subscriptionMaster,
			StatePlatformMaster statePlatformMstr, Double amount, String requestCode, State state, String createdByName,
			String updatedByName, Boolean isActive) {
		super();
		this.id = id;
		this.client = client;
		this.subscriptionMaster = subscriptionMaster;
		this.statePlatformMstr = statePlatformMstr;
		this.amount = amount;
		this.requestCode = requestCode;
		this.state = state;
		this.createdByName = createdByName;
		this.updatedByName = updatedByName;
		this.isActive = isActive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public SubscriptionMaster getSubscriptionMaster() {
		return subscriptionMaster;
	}

	public void setSubscriptionMaster(SubscriptionMaster subscriptionMaster) {
		this.subscriptionMaster = subscriptionMaster;
	}

	public StatePlatformMaster getStatePlatformMstr() {
		return statePlatformMstr;
	}

	public void setStatePlatformMstr(StatePlatformMaster statePlatformMstr) {
		this.statePlatformMstr = statePlatformMstr;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public ClientSubscriptionMasterDTO convertEntityToDTO(ClientSubscriptionMaster clientSubsMstr) {

		return new ClientSubscriptionMasterDTO(clientSubsMstr.getId(), clientSubsMstr.getClient().getId(),
				clientSubsMstr.getSubscriptionMaster().getId().intValue(),
				clientSubsMstr.getStatePlatformMstr().getId().intValue(), clientSubsMstr.getRequestCode(),
				clientSubsMstr.getAmount(), clientSubsMstr.getState().getId(), clientSubsMstr.getCreatedByName(),
				clientSubsMstr.getIsActive());
	}

	public ClientSubscriptionMasterDTO convertEntityToDTOV2(ClientSubscriptionMaster clientSubsMstr) {

		return new ClientSubscriptionMasterDTO(clientSubsMstr.getRequestCode(),
				clientSubsMstr.getClient().getCompanyName(), clientSubsMstr.getState().getName(),
				clientSubsMstr.getSubscriptionMaster().getSubscriptionType().getName(),
				clientSubsMstr.getSubscriptionMaster().getTotalDays(), clientSubsMstr.getAmount(),
				clientSubsMstr.getCreatedByName(), clientSubsMstr.getIsActive(),clientSubsMstr.getPlatform().getName());
	}

}