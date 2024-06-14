package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_device_command")
public class UserDeviceCommand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "command")
	private String command;
	
	@Column(name = "vehicle_id")
	private Long vehicleId;
	
	@Column(name = "response")
	private String response;

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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
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

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public UserDeviceCommand() {
		super();
	}

	public UserDeviceCommand(Long id, String imeiNo, Long createdBy, Date createdAt, String command, Long vehicleId,
			String response) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.command = command;
		this.vehicleId = vehicleId;
		this.response = response;
	}
	
}
