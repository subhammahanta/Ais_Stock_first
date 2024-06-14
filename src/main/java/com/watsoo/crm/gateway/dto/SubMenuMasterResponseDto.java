package com.watsoo.crm.gateway.dto;

public class SubMenuMasterResponseDto {
	private Long id;
	private String name;
	private Boolean isVisible;
	private Long menuId;
	private String menuName;
	private String link;
	private Boolean isAdminVisible;
	public SubMenuMasterResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubMenuMasterResponseDto(Long id, String name, Boolean isVisible, Long menuId, String menuName,
			String link) {
		super();
		this.id = id;
		this.name = name;
		this.isVisible = isVisible;
		this.menuId = menuId;
		this.menuName = menuName;
		this.link = link;

	}

	public SubMenuMasterResponseDto(Long id, String name, Boolean isVisible, Long menuId, String menuName) {
		super();
		this.id = id;
		this.name = name;
		this.isVisible = isVisible;
		this.menuId = menuId;
		this.menuName = menuName;
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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getIsAdminVisible() {
		return isAdminVisible;
	}

	public void setIsAdminVisible(Boolean isAdminVisible) {
		this.isAdminVisible = isAdminVisible;
	}

}
