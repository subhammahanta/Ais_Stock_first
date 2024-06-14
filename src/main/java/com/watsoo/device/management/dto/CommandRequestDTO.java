package com.watsoo.device.management.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CommandRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String reqCode;

	private String softwareVersion;

	private Long createdBy;

	private Date createdAt;

	private String command;

	private List<DeviceCommand> deviceCommandList;

	// private Double successPercentage;

	private Integer totalDevice;

	private Integer successCount;

	private Date fromDate;

	private Date toDate;
	
	private String fromVersion;
	
	private String toVersion;
	
	private String currentVersion;
	
	private Boolean isDone;
	
	private Boolean isReverted;
	
	private Integer versionUpdateSuccessfullCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReqCode() {
		return reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
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

	public Integer getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(Integer totalDevice) {
		this.totalDevice = totalDevice;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<DeviceCommand> getDeviceCommandList() {
		return deviceCommandList;
	}

	public void setDeviceCommandList(List<DeviceCommand> deviceCommandList) {
		this.deviceCommandList = deviceCommandList;
	}

	public String getFromVersion() {
		return fromVersion;
	}

	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}

	public String getToVersion() {
		return toVersion;
	}

	public void setToVersion(String toVersion) {
		this.toVersion = toVersion;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public Boolean getIsReverted() {
		return isReverted;
	}

	public void setIsReverted(Boolean isReverted) {
		this.isReverted = isReverted;
	}

	public Integer getVersionUpdateSuccessfullCount() {
		return versionUpdateSuccessfullCount;
	}

	public void setVersionUpdateSuccessfullCount(Integer versionUpdateSuccessfullCount) {
		this.versionUpdateSuccessfullCount = versionUpdateSuccessfullCount;
	}

	public CommandRequestDTO(Long id, String reqCode, String softwareVersion, Long createdBy, Date createdAt,
			String command, Integer totalDevice, Integer successCount, Date fromDate, Date toDate) {
		super();
		this.id = id;
		this.reqCode = reqCode;
		this.softwareVersion = softwareVersion;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.command = command;
		this.totalDevice = totalDevice;
		this.successCount = successCount;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public CommandRequestDTO() {
		super();
	}

	public CommandRequestDTO(Long id2, String reqCode2, String softwareVersion2, Long createdBy2, Date createdAt2,
			String command2, Integer totalDevice2, List<DeviceCommand> deviceCommandList) {
		super();
		this.id = id2;
		this.reqCode = reqCode2;
		this.softwareVersion = softwareVersion2;
		this.createdBy = createdBy2;
		this.createdAt = createdAt2;
		this.command = command2;
		this.totalDevice = totalDevice2;
		this.deviceCommandList = deviceCommandList;
	}
//
//	public CommandRequestDTO(Long id2, String reqCode2, String softwareVersion2, Long createdBy2, Date createdAt2,
//			String command2, Integer totalDevice2) {
//		super();
//		this.id = id2;
//		this.reqCode = reqCode2;
//		this.softwareVersion = softwareVersion2;
//		this.createdBy = createdBy2;
//		this.createdAt = createdAt2;
//		this.command = command2;
//		this.totalDevice = totalDevice2;
//	}
}
