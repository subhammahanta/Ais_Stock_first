package com.watsoo.device.management.dto;

import java.io.Serializable;
import java.util.Date;

public class DeviceCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String imeiNo;

	private String lastResponse;

	private Date commandSendDate;

	private String requestedSoftwareVersion;

	private String currentSoftwareVersion;

	private String requestedCommand;

	private Boolean isDone;
	
	private Integer retryCount;
	
	private Boolean isDeviceRespond;
	
	private Date lastPollingDate;
	
	private String fromSoftwareVersion;
	
	private Boolean isActive;
	
	private Boolean isReverted;

	public DeviceCommand() {
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

	public String getLastResponse() {
		return lastResponse;
	}

	public void setLastResponse(String lastResponse) {
		this.lastResponse = lastResponse;
	}

	public String getRequestedSoftwareVersion() {
		return requestedSoftwareVersion;
	}

	public void setRequestedSoftwareVersion(String requestedSoftwareVersion) {
		this.requestedSoftwareVersion = requestedSoftwareVersion;
	}

	public String getCurrentSoftwareVersion() {
		return currentSoftwareVersion;
	}

	public void setCurrentSoftwareVersion(String currentSoftwareVersion) {
		this.currentSoftwareVersion = currentSoftwareVersion;
	}

	public String getRequestedCommand() {
		return requestedCommand;
	}

	public void setRequestedCommand(String requestedCommand) {
		this.requestedCommand = requestedCommand;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Boolean getIsDeviceRespond() {
		return isDeviceRespond;
	}

	public void setIsDeviceRespond(Boolean isDeviceRespond) {
		this.isDeviceRespond = isDeviceRespond;
	}

	public Date getCommandSendDate() {
		return commandSendDate;
	}

	public void setCommandSendDate(Date commandSendDate) {
		this.commandSendDate = commandSendDate;
	}

	public Date getLastPollingDate() {
		return lastPollingDate;
	}

	public void setLastPollingDate(Date lastPollingDate) {
		this.lastPollingDate = lastPollingDate;
	}

	public String getFromSoftwareVersion() {
		return fromSoftwareVersion;
	}

	public void setFromSoftwareVersion(String fromSoftwareVersion) {
		this.fromSoftwareVersion = fromSoftwareVersion;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsReverted() {
		return isReverted;
	}

	public void setIsReverted(Boolean isReverted) {
		this.isReverted = isReverted;
	}

}
