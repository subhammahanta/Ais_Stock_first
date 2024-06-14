package com.watsoo.device.management.dto;

import com.watsoo.device.management.model.Client;

public class StateCmdMstrDTO {
	private Long id;
	private StateDTO state;
	private Client client;
	private String comndName;
	private String command;
	private String password;

	public StateCmdMstrDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StateCmdMstrDTO(Long id, StateDTO state, Client client, String comndName, String command, String password) {
		super();
		this.id = id;
		this.state = state;
		this.client = client;
		this.comndName = comndName;
		this.command = command;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StateDTO getState() {
		return state;
	}

	public void setState(StateDTO state) {
		this.state = state;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getComndName() {
		return comndName;
	}

	public void setComndName(String comndName) {
		this.comndName = comndName;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
