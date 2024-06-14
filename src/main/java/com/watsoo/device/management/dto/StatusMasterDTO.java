package com.watsoo.device.management.dto;

import java.util.Date;

public class StatusMasterDTO {
	private Long id;
	private String name;
	private Boolean isActive;
	private Date createdAt;
	public StatusMasterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StatusMasterDTO(Long id, String name, Boolean isActive, Date createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.createdAt = createdAt;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}