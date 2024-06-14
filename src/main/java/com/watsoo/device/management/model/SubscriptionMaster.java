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

import com.watsoo.device.management.dto.ClientSubscriptionMasterDTO;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;

@Entity
@Table(name = "subscription_master")
public class SubscriptionMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "subs_type_id")
	private SubscriptionType subscriptionType;

	@Column(name = "viewAs")
	private String viewAs;

	@Column(name = "defaultAmount")
	private Double defaultAmount;

	@Column(name = "totalDays")
	private Integer totalDays;

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
	private boolean isactive;

	public SubscriptionMaster() {
		super();
	}

	public SubscriptionMaster(Long id, SubscriptionType subscriptionTypeId, String viewAs, Double defaultAmount,
			Integer totalDays, Date createdAt, Long createdBy, String createdByName, Date updatedAt, Long updatedBy,
			String updatedByName, boolean isactive) {
		super();
		this.id = id;
		this.subscriptionType = subscriptionTypeId;
		this.viewAs = viewAs;
		this.defaultAmount = defaultAmount;
		this.totalDays = totalDays;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.createdByName = createdByName;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.updatedByName = updatedByName;
		this.isactive = isactive;
	}

	public SubscriptionMaster(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getViewAs() {
		return viewAs;
	}

	public void setViewAs(String viewAs) {
		this.viewAs = viewAs;
	}

	public Double getDefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(Double defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
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

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public SubscriptionMasterDTO convertEntityToDTO(SubscriptionMaster subsMaster) {
		return new SubscriptionMasterDTO(subsMaster.getId(), subsMaster.getSubscriptionType().getId(),
				subsMaster.getViewAs(), subsMaster.getDefaultAmount(), subsMaster.getTotalDays());
	}

	public ClientSubscriptionMasterDTO subscriptionMasterToClientSubscriptionMasterDTO(SubscriptionMaster e) {
		return new ClientSubscriptionMasterDTO(e.getTotalDays(), e.getDefaultAmount(), e.getSubscriptionType(), e);
	}

}
