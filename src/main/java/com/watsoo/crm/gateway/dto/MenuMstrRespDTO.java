package com.watsoo.crm.gateway.dto;

public class MenuMstrRespDTO {

	private Long id;
	private String name;
	private Boolean isVisible;
	private String link;

	public MenuMstrRespDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenuMstrRespDTO(Long id, String name, Boolean isVisible, String link) {
		super();
		this.id = id;
		this.name = name;
		this.isVisible = isVisible;
		this.link = link;
	}

	public MenuMstrRespDTO(Long id, String name, Boolean isVisible) {
		super();
		this.id = id;
		this.name = name;
		this.isVisible = isVisible;
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

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
