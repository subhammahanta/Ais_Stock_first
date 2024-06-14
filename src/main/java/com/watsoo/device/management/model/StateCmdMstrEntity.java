package com.watsoo.device.management.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.watsoo.device.management.dto.StateCmdMstrDTO;
import com.watsoo.device.management.dto.StateCommandDTO;

@Entity
@Table(name = "state_command_mstr")
public class StateCmdMstrEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@Column(name = "cmd_name")
	private String comndName;

	@Column(name = "cmd")
	private String command;

	@Transient
	private String password;

	@Column(name = "is_active")
	private Boolean isActive;

	public StateCmdMstrEntity() {
		super();
	}

	public StateCmdMstrEntity(Long id, State state, String comndName, String command) {
		super();
		this.id = id;
		this.state = state;
		this.comndName = comndName;
		this.command = command;
	}

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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public StateCmdMstrEntity(Long id, State state, Client client, String comndName, String command, String password,
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

	public StateCmdMstrDTO convertEntityToDto(StateCmdMstrEntity entity) {
		return new StateCmdMstrDTO(entity.getId(),
				entity.getState() != null ? entity.getState().convertEntityToDto(entity.getState()) : null,
						entity.getClient(), entity.getComndName(), entity.getCommand(), entity.getPassword());

	}

	public StateCommandDTO convertToDto(List<ConfigChangeTrail> configChangeTrails, StateCmdMstrEntity state) {
		return new StateCommandDTO(state.getId(), state.getState(), state.getClient(), state.getComndName(),
				state.getCommand(), state.getPassword(),
				configChangeTrails.stream().map(ConfigChangeTrail::convertToDto).collect(Collectors.toList()),
				state.getIsActive());
	}

	public StateCommandDTO convertToDtoV2(StateCmdMstrEntity state) {
		return new StateCommandDTO(state.getId(), state.getState(), state.getClient(), state.getComndName(),
				state.getCommand(), state.getPassword(),
				state.getIsActive());
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
