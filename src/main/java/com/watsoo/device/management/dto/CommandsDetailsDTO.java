package com.watsoo.device.management.dto;

import java.util.Date;

public class CommandsDetailsDTO {

	private Long id;

	private Long masterId;

	private String imei;

	private String command;

	private String lastShootResponse;
	
	private Date lastShootTime;


	public CommandsDetailsDTO(Long id, String imei, Long masterId, String command, String lastShootResponse,Date lastShootTime) {
		super();
		this.id = id;
		this.masterId = masterId;
		this.imei = imei;
		this.command = command;
		this.lastShootResponse = lastShootResponse;
		this.lastShootTime= lastShootTime;
	}

	public CommandsDetailsDTO(Long id, Long masterId, String command, String lastShootResponse) {
		this.id=id;
		this.masterId=masterId;
		this.command=command;
		this.lastShootResponse=lastShootResponse;
	}

	public CommandsDetailsDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getLastShootResponse() {
		return lastShootResponse;
	}

	public void setLastShootResponse(String lastShootResponse) {
		this.lastShootResponse = lastShootResponse;
	}

	public Date getLastShootTime() {
		return lastShootTime;
	}

	public void setLastShootTime(Date lastShootTime) {
		this.lastShootTime = lastShootTime;
	}


	
}
