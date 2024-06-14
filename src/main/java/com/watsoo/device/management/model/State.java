package com.watsoo.device.management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.StateDTO;

@Entity
@Table(name = "state")
public class State implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "reference_key")
	private String refernceKey;

	public State() {
		super();
	}

	public State(Long id) {
		super();
		this.id = id;
	}

	public State(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRefernceKey() {
		return refernceKey;
	}

	public void setRefernceKey(String refernceKey) {
		this.refernceKey = refernceKey;
	}

	public StateDTO convertEntityToDto(State state) {
		return new StateDTO(state.getId(),state.getName());
	}

}
