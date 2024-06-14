package com.watsoo.device.management.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "state_client_reconfigure_command")
public class StateClientReconfigureCommand implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	private String command;

	public StateClientReconfigureCommand() {
		super();
	}

	public StateClientReconfigureCommand(Long id, State state, Client client, String command) {
		super();
		this.id = id;
		this.state = state;
		this.client = client;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
