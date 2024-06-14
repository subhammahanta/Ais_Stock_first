package com.watsoo.device.management.dto;

public class ProductTypeDTO {
	private Long id;
	private String name;
	private Boolean isActive;
	public ProductTypeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductTypeDTO(Long id, String name, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
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
	
}
