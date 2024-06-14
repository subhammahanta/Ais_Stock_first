package com.watsoo.device.management.dto;

public class MenuMasterDto {
	private Long id;
	private String name;
	private Boolean isVisible;
	private String link;
	private String icon;

	public MenuMasterDto() {
	}

	public MenuMasterDto(Long id, String name, Boolean isVisible, String link,String icon) {
		this.id = id;
		this.name = name;
		this.isVisible = isVisible;
		this.link = link;
		this.icon= icon;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
