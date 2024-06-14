//package com.watsoo.device.management.model;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "device_command_due")
//public class DeviceCommandDue implements Serializable{
//
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private Long id;
//
//	@Column(name = "device_id")
//	private Long deviceId;
//
//	@Column(name = "imei_no")
//	private String imeiNo;
//
//	@Column(name = "created_at")
//	private Date createdAt;
//
//	@Column(name = "created_by")
//	private Long createdBy;
//
//	@Column(name = "command")
//	private String command;
//
//	@Column(name = "response")
//	private String response;
//
//	@Column(name = "version")
//	private String version;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Long getDeviceId() {
//		return deviceId;
//	}
//
//	public void setDeviceId(Long deviceId) {
//		this.deviceId = deviceId;
//	}
//
//	public String getImeiNo() {
//		return imeiNo;
//	}
//
//	public void setImeiNo(String imeiNo) {
//		this.imeiNo = imeiNo;
//	}
//
//	public Date getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public Long getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(Long createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public String getCommand() {
//		return command;
//	}
//
//	public void setCommand(String command) {
//		this.command = command;
//	}
//
//	public String getResponse() {
//		return response;
//	}
//
//	public void setResponse(String response) {
//		this.response = response;
//	}
//
//	public String getVersion() {
//		return version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}
//
//	public DeviceCommandDue() {
//		super();
//	}
//
//	public DeviceCommandDue(Long id, Long deviceId, String imeiNo, Date createdAt, Long createdBy, String command,
//			String response, String version) {
//		super();
//		this.id = id;
//		this.deviceId = deviceId;
//		this.imeiNo = imeiNo;
//		this.createdAt = createdAt;
//		this.createdBy = createdBy;
//		this.command = command;
//		this.response = response;
//		this.version = version;
//	}
//
//}
