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
@Table(name = "device_command_change_trail")
public class DeviceCommandChangeTrail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "modified_at")
	private Date modifiedAt;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "after_update")
	private String afterUpdate;

	@Column(name = "before_update")
	private String beforeUpdate;

	public DeviceCommandChangeTrail(Long id, String imeiNo, Date modifiedAt, String modifiedBy, Long userId,
			String afterUpdate, String beforeUpdate) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
		this.userId = userId;
		this.afterUpdate = afterUpdate;
		this.beforeUpdate = beforeUpdate;
	}

	public DeviceCommandChangeTrail() {
		super();
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

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAfterUpdate() {
		return afterUpdate;
	}

	public void setAfterUpdate(String afterUpdate) {
		this.afterUpdate = afterUpdate;
	}

	public String getBeforeUpdate() {
		return beforeUpdate;
	}

	public void setBeforeUpdate(String beforeUpdate) {
		this.beforeUpdate = beforeUpdate;
	}

}
