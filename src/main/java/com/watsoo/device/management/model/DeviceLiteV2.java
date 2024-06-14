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

import com.watsoo.device.management.enums.StatusMaster;

@Entity
@Table(name = "device")
public class DeviceLiteV2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private StatusMaster status;

	@Column(name = "packed_date")
	private Date packedDate;
	
	@Column(name = "created_at")
	private Date createdAt;

	public DeviceLiteV2(Long id, StatusMaster status, Date packedDate) {
		super();
		this.id = id;
		this.status = status;
		this.packedDate = packedDate;
	}

	public DeviceLiteV2() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusMaster getStatus() {
		return status;
	}

	public void setStatus(StatusMaster status) {
		this.status = status;
	}

	public Date getPackedDate() {
		return packedDate;
	}

	public void setPackedDate(Date packedDate) {
		this.packedDate = packedDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
