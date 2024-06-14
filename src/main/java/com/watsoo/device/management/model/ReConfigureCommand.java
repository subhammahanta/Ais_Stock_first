package com.watsoo.device.management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "re_configure_command")
public class ReConfigureCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String command;

	private String response;

	@Column(name = "reconfigure_box_id")
	private Long reConfigureBoxId;
	
	@Column(name = "key_to_verify")
	private String keyToVerify;
	
	@Column(name = "waiting_time")
	private Integer waitingTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getReConfigureBoxId() {
		return reConfigureBoxId;
	}

	public void setReConfigureBoxId(Long reConfigureBoxId) {
		this.reConfigureBoxId = reConfigureBoxId;
	}

	public ReConfigureCommand() {
		super();
	}

	public String getKeyToVerify() {
		return keyToVerify;
	}

	public void setKeyToVerify(String keyToVerify) {
		this.keyToVerify = keyToVerify;
	}

	public Integer getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
	}

	public ReConfigureCommand(Long id, String command, String response, Long reConfigureBoxId, String keyToVerify,
			Integer waitingTime) {
		super();
		this.id = id;
		this.command = command;
		this.response = response;
		this.reConfigureBoxId = reConfigureBoxId;
		this.keyToVerify = keyToVerify;
		this.waitingTime = waitingTime;
	}

}
