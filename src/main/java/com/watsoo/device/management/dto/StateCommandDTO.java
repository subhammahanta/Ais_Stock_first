package com.watsoo.device.management.dto;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.watsoo.device.management.enums.OperationType;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.State;

public class StateCommandDTO {

	private Long id;

	private State state;

	private Client client;

	private String comndName;

	private String command;

	private String password;

	private List<ConfigChangeTrailDTO> configDto;

	private Long stateCmdMstrId;

	private Long modifiedBy;
	
	private Boolean isActive;
	
	private String user;

	@Enumerated(EnumType.STRING)
	private OperationType operationType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
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

	public List<ConfigChangeTrailDTO> getConfigDto() {
		return configDto;
	}

	public void setConfigDto(List<ConfigChangeTrailDTO> configDto) {
		this.configDto = configDto;
	}

	public StateCommandDTO(Long id, State state, Client client, String comndName, String command, String password,
			List<ConfigChangeTrailDTO> list,Boolean isActive) {
		super();
		this.id = id;
		this.state = state;
		this.client = client;
		this.comndName = comndName;
		this.command = command;
		this.password = password;
		this.configDto = list;
		this.isActive = isActive;
	}
	public StateCommandDTO(Long id, State state, Client client, String comndName, String command, String password,
			Boolean isActive) {
		super();
		this.id = id;
		this.state = state;
		this.client = client;
		this.comndName = comndName;
		this.command = command;
		this.password = password;
		this.isActive = isActive;
	}

	public StateCommandDTO() {
		super();
	}

	public Long getStateCmdMstrId() {
		return stateCmdMstrId;
	}

	public void setStateCmdMstrId(Long stateCmdMstrId) {
		this.stateCmdMstrId = stateCmdMstrId;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
