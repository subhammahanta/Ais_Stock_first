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
import javax.persistence.Transient;

import com.watsoo.device.management.enums.StatusMaster;

@Entity
@Table(name = "device")
public class DeviceLazyEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "iccid_no")
	private String iccidNo;

	@Column(name = "software_version")
	private String softwareVersion;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "state_id")
	private Long state;

	@Enumerated(EnumType.STRING)
	private StatusMaster status;

	@Column(name = "issue_date")
	private Date issueDate;

	@Column(name = "sim1_number")
	private String sim1Number;

	@Column(name = "sim2_number")
	private String sim2Number;

	@Column(name = "sim1_operator")
	private String sim1Operator;

	@Column(name = "sim2_operator")
	private String sim2Operator;

	@Column(name = "sim1_activation_date")
	private Date sim1ActivationDate;

	@Column(name = "sim1_expiry_date")
	private Date sim1ExpiryDate;

	@Column(name = "sim2_activation_date")
	private Date sim2ActivationDate;

	@Column(name = "sim2_expiry_date")
	private Date sim2ExpiryDate;

	@Column(name = "sim1_provider")
	private String sim1Provider;

	@Column(name = "sim2_provider")
	private String sim2Provider;

	@Column(name = "app_expiry_date")
	private Date appExpiryDate;

	@Column(name = "config_done_date")
	private Date configDoneDate;

	@Column(name = "device_activation_date")
	private Date deviceActivatedDate;
	
	@Column(name = "last_renewal_date")
	private Date lastRenewalDate;

	@Transient
	private Boolean isRenewalRequired;

	@Transient
	private Boolean isExpired;

	public DeviceLazyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceLazyEntity(Long id, String imeiNo, String iccidNo, String softwareVersion, Date createdAt,
			Date updatedAt, Long state, StatusMaster status, Date issueDate, String sim1Number, String sim2Number,
			String sim1Operator, String sim2Operator, Date sim1ActivationDate, Date sim1ExpiryDate,
			Date sim2ActivationDate, Date sim2ExpiryDate, String sim1Provider, String sim2Provider,
			Date appExpiryDate,Date configDoneDate,Date deviceActivatedDate,Date lastRenewalDate) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.iccidNo = iccidNo;
		this.softwareVersion = softwareVersion;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.state = state;
		this.status = status;
		this.issueDate = issueDate;
		this.sim1Number = sim1Number;
		this.sim2Number = sim2Number;
		this.sim1Operator = sim1Operator;
		this.sim2Operator = sim2Operator;
		this.sim1ActivationDate = sim1ActivationDate;
		this.sim1ExpiryDate = sim1ExpiryDate;
		this.sim2ActivationDate = sim2ActivationDate;
		this.sim2ExpiryDate = sim2ExpiryDate;
		this.sim1Provider = sim1Provider;
		this.sim2Provider = sim2Provider;
		this.appExpiryDate = appExpiryDate;
		this.configDoneDate=configDoneDate;
		this.deviceActivatedDate=deviceActivatedDate;
		this.lastRenewalDate=lastRenewalDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getIccidNo() {
		return iccidNo;
	}

	public void setIccidNo(String iccidNo) {
		this.iccidNo = iccidNo;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public StatusMaster getStatus() {
		return status;
	}

	public void setStatus(StatusMaster status) {
		this.status = status;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getSim1Number() {
		return sim1Number;
	}

	public void setSim1Number(String sim1Number) {
		this.sim1Number = sim1Number;
	}

	public String getSim2Number() {
		return sim2Number;
	}

	public void setSim2Number(String sim2Number) {
		this.sim2Number = sim2Number;
	}

	public String getSim1Operator() {
		return sim1Operator;
	}

	public void setSim1Operator(String sim1Operator) {
		this.sim1Operator = sim1Operator;
	}

	public String getSim2Operator() {
		return sim2Operator;
	}

	public void setSim2Operator(String sim2Operator) {
		this.sim2Operator = sim2Operator;
	}

	public Date getSim1ActivationDate() {
		return sim1ActivationDate;
	}

	public void setSim1ActivationDate(Date sim1ActivationDate) {
		this.sim1ActivationDate = sim1ActivationDate;
	}

	public Date getSim1ExpiryDate() {
		return sim1ExpiryDate;
	}

	public void setSim1ExpiryDate(Date sim1ExpiryDate) {
		this.sim1ExpiryDate = sim1ExpiryDate;
	}

	public Date getSim2ActivationDate() {
		return sim2ActivationDate;
	}

	public void setSim2ActivationDate(Date sim2ActivationDate) {
		this.sim2ActivationDate = sim2ActivationDate;
	}

	public Date getSim2ExpiryDate() {
		return sim2ExpiryDate;
	}

	public void setSim2ExpiryDate(Date sim2ExpiryDate) {
		this.sim2ExpiryDate = sim2ExpiryDate;
	}

	public String getSim1Provider() {
		return sim1Provider;
	}

	public void setSim1Provider(String sim1Provider) {
		this.sim1Provider = sim1Provider;
	}

	public String getSim2Provider() {
		return sim2Provider;
	}

	public void setSim2Provider(String sim2Provider) {
		this.sim2Provider = sim2Provider;
	}

	public Date getAppExpiryDate() {
		return appExpiryDate;
	}

	public void setAppExpiryDate(Date appExpiryDate) {
		this.appExpiryDate = appExpiryDate;
	}

	public Boolean getIsRenewalRequired() {
		return isRenewalRequired;
	}

	public void setIsRenewalRequired(Boolean isRenewalRequired) {
		this.isRenewalRequired = isRenewalRequired;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public Date getConfigDoneDate() {
		return configDoneDate;
	}

	public void setConfigDoneDate(Date configDoneDate) {
		this.configDoneDate = configDoneDate;
	}

	public Date getDeviceActivatedDate() {
		return deviceActivatedDate;
	}

	public void setDeviceActivatedDate(Date deviceActivatedDate) {
		this.deviceActivatedDate = deviceActivatedDate;
	}

	public Date getLastRenewalDate() {
		return lastRenewalDate;
	}

	public void setLastRenewalDate(Date lastRenewalDate) {
		this.lastRenewalDate = lastRenewalDate;
	}
	
}
