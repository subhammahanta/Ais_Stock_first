package com.watsoo.device.management.dto;

import java.util.Date;

public class ReconfigureDeviceResponseDTO {

	private int id;
	private String requestedcode;
	private String imeinumber;
	private boolean isactive;
	private Date senddate;
	private Long requestedby;
	private Date requestedon;
	private String command;
	private String user;
	private String boxnumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestedcode() {
		return requestedcode;
	}

	public void setRequestedcode(String requestedcode) {
		this.requestedcode = requestedcode;
	}

	public String getImeinumber() {
		return imeinumber;
	}

	public void setImeinumber(String imeinumber) {
		this.imeinumber = imeinumber;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Date getSenddate() {
		return senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

	public Long getRequestedby() {
		return requestedby;
	}

	public void setRequestedby(Long requestedby) {
		this.requestedby = requestedby;
	}

	public Date getRequestedon() {
		return requestedon;
	}

	public void setRequestedon(Date requestedon) {
		this.requestedon = requestedon;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getBoxnumber() {
		return boxnumber;
	}

	public void setBoxnumber(String boxnumber) {
		this.boxnumber = boxnumber;
	}

}
