package com.watsoo.device.management.dto;

import com.watsoo.device.management.model.State;

public class StateDTO {
	private Long id;
	private String name;

	public StateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StateDTO(Long id) {
		super();
		this.id = id;
	}

	public StateDTO(Long id, String name) {
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

	public State convetDTOToEntity(StateDTO stateDTO) {
		
		return new State(stateDTO.getId(),stateDTO.getName());
	}
	
}
