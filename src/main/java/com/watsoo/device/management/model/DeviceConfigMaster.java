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
@Table(name = "device_config_master")
public class DeviceConfigMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "retry_count")
	private Integer retryCount;

	@Column(name = "last_command_shoot_at")
	private Date lastCommandShootAt;

	@Column(name = "is_success")
	private Boolean isSuccess;

	public DeviceConfigMaster() {
		super();
	}

	public DeviceConfigMaster(Long id, String imeiNo, Integer retryCount, Date lastCommandShootAt, Boolean isSuccess) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.retryCount = retryCount;
		this.lastCommandShootAt = lastCommandShootAt;
		this.isSuccess = isSuccess;
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

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Date getLastCommandShootAt() {
		return lastCommandShootAt;
	}

	public void setLastCommandShootAt(Date lastCommandShootAt) {
		this.lastCommandShootAt = lastCommandShootAt;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
