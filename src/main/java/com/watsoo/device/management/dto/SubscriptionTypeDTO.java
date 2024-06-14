package com.watsoo.device.management.dto;

import javax.persistence.Column;

public class SubscriptionTypeDTO {

	private Integer id;
	private String name;
	public SubscriptionTypeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SubscriptionTypeDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
