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

@Entity
@Table(name = "re_configure_device_trail")
public class ReConfigureDeviceTrail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@OneToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "command")
	private String command;

	@Column(name = "response")
	private String response;

	@Column(name = "re_config_box_id")
	private Long reConfigBoxId;

	public ReConfigureDeviceTrail() {
		super();
	}

	public ReConfigureDeviceTrail(Long id, String imeiNo, User createdBy, Date createdAt, String command,
			String response, Long reConfigBoxId) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.command = command;
		this.response = response;
		this.reConfigBoxId = reConfigBoxId;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	public Long getReConfigBoxId() {
		return reConfigBoxId;
	}

	public void setReConfigBoxId(Long reConfigBoxId) {
		this.reConfigBoxId = reConfigBoxId;
	}

}
