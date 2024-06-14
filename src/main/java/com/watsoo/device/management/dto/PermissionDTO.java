package com.watsoo.device.management.dto;

import java.util.List;

import com.watsoo.device.management.model.GroupTypeName;

public class PermissionDTO {

	private Long id;

	private String name;

	private boolean forMobile;

	private boolean forWeb;

	private List<PermissionCategoryDTO> permissionCategoryDTOs;

	private boolean isActive;

	private String url;

	private GroupTypeName groupType;

	private List<GroupTypeNameDTO> groupTypeList;

	public PermissionDTO() {
		super();
	}

	public PermissionDTO(Long id, String name, boolean forMobile, boolean forWeb, boolean isActive,
			GroupTypeName groupType) {
		this.id = id;
		this.name = name;
		this.forMobile = forMobile;
		this.forWeb = forWeb;
		this.isActive = isActive;
		this.groupType = groupType;
	}

	public PermissionDTO(Long id, String name, boolean forMobile, boolean forWeb, boolean isActive,
			List<GroupTypeNameDTO> groupTypeList) {
		this.id = id;
		this.name = name;
		this.forMobile = forMobile;
		this.forWeb = forWeb;
		this.isActive = isActive;
		this.groupTypeList = groupTypeList;
	}

	public PermissionDTO(String name, String url) {
		super();
		this.name = name;
		this.url = url;
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

	public boolean isForMobile() {
		return forMobile;
	}

	public void setForMobile(boolean forMobile) {
		this.forMobile = forMobile;
	}

	public boolean isForWeb() {
		return forWeb;
	}

	public void setForWeb(boolean forWeb) {
		this.forWeb = forWeb;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GroupTypeName getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupTypeName groupType) {
		this.groupType = groupType;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<GroupTypeNameDTO> getGroupTypeList() {
		return groupTypeList;
	}

	public void setGroupTypeList(List<GroupTypeNameDTO> groupTypeList) {
		this.groupTypeList = groupTypeList;
	}

	public List<PermissionCategoryDTO> getPermissionCategoryDTOs() {
		return permissionCategoryDTOs;
	}

	public void setPermissionCategoryDTOs(List<PermissionCategoryDTO> permissionCategoryDTOs) {
		this.permissionCategoryDTOs = permissionCategoryDTOs;
	}

}
