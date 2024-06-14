package com.watsoo.device.management.dto;

import java.util.Date;

public class TcPackets {

	private Integer id;

	private String deviceName;

	private String hexValue;

	private String packet;

	private String imeiNumber;

	private Date createdDate;

	private Date commandSendDate;

	private Boolean commmandSend;

	private String lastCommand;

	private Boolean configDone;

	private Boolean isDeviceActivated;

	private String stateCommand;
	
	private Integer stateId;

	public TcPackets(Integer id, String deviceName, String hexValue, String packet, String imeiNumber,
			Date createdDate) {
		super();
		this.id = id;
		this.deviceName = deviceName;
		this.hexValue = hexValue;
		this.packet = packet;
		this.imeiNumber = imeiNumber;
		this.createdDate = createdDate;
	}

	public TcPackets(Integer id, String deviceName, String hexValue, String packet, String imeiNumber, Date createdDate,
			Date commandSendDate, Boolean commmandSend, String lastCommand, Boolean configDone,
			Boolean isDeviceActivated) {
		super();
		this.id = id;
		this.deviceName = deviceName;
		this.hexValue = hexValue;
		this.packet = packet;
		this.imeiNumber = imeiNumber;
		this.createdDate = createdDate;
		this.commandSendDate = commandSendDate;
		this.commmandSend = commmandSend;
		this.lastCommand = lastCommand;
		this.configDone = configDone;
		this.isDeviceActivated = isDeviceActivated;
	}

	public TcPackets() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getHexValue() {
		return hexValue;
	}

	public void setHexValue(String hexValue) {
		this.hexValue = hexValue;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCommandSendDate() {
		return commandSendDate;
	}

	public void setCommandSendDate(Date commandSendDate) {
		this.commandSendDate = commandSendDate;
	}

	public Boolean getCommmandSend() {
		return commmandSend;
	}

	public void setCommmandSend(Boolean commmandSend) {
		this.commmandSend = commmandSend;
	}

	public Boolean getConfigDone() {
		return configDone;
	}

	public void setConfigDone(Boolean configDone) {
		this.configDone = configDone;
	}

	public String getLastCommand() {
		return lastCommand;
	}

	public void setLastCommand(String lastCommand) {
		this.lastCommand = lastCommand;
	}

	public Boolean getIsDeviceActivated() {
		return isDeviceActivated;
	}

	public void setIsDeviceActivated(Boolean isDeviceActivated) {
		this.isDeviceActivated = isDeviceActivated;
	}

	public String getStateCommand() {
		return stateCommand;
	}

	public void setStateCommand(String stateCommand) {
		this.stateCommand = stateCommand;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	

}