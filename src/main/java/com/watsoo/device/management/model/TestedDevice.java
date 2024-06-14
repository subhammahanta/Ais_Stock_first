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
@Table(name = "tested_device")
public class TestedDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "iccid_no")
	private String iccidNo;

	@Column(name = "uuid_no")
	private String uuidNo;

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

	@Column(name = "is_testing_completed")
	private Boolean isTestingCompleted;
	
	@Column(name = "device_id")
	private Long deviceId;
	
	@Column(name = "detail")
	private String detail;
	
	@Column(name = "software_version")
	private String softwareVersion;
	
	@Column(name = "lot_id")
	private Long lotId;
	
	@Column(name = "is_rejected")
	private Boolean isRejected;
	
	@Column(name = "rejected_by")
	private Long rejectedBy;
	
	@Column(name = "remark")
	private String remark;

	public TestedDevice() {
		super();
	}

	public TestedDevice(Long id, String imeiNo, String iccidNo, String uuidNo, Boolean isActive, Date createdAt,
			Long createdBy, Date updatedAt, Long updatedBy, Boolean isTestingCompleted, Long deviceId, String detail,
			String softwareVersion) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.iccidNo = iccidNo;
		this.uuidNo = uuidNo;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.isTestingCompleted = isTestingCompleted;
		this.deviceId = deviceId;
		this.detail = detail;
		this.softwareVersion = softwareVersion;
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

	public String getUuidNo() {
		return uuidNo;
	}

	public void setUuidNo(String uuidNo) {
		this.uuidNo = uuidNo;
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

	public Boolean getIsTestingCompleted() {
		return isTestingCompleted;
	}

	public void setIsTestingCompleted(Boolean isTestingCompleted) {
		this.isTestingCompleted = isTestingCompleted;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public Long getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(Long rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
