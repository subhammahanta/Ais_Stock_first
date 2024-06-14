package com.watsoo.device.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_raw_data_permission")
public class UserRawDataPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "raw_server_id")
	private Long rawServerId;

	@Column(name = "imei")
	private String imei;

	public UserRawDataPermission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRawDataPermission(Long id, Long userId, Long rawServerId, String imei) {
		super();
		this.id = id;
		this.userId = userId;
		this.rawServerId = rawServerId;
		this.imei = imei;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRawServerId() {
		return rawServerId;
	}

	public void setRawServerId(Long rawServerId) {
		this.rawServerId = rawServerId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}
