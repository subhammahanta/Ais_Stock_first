package com.watsoo.device.management.dto;

import java.util.List;

import com.watsoo.device.management.model.Role;

public class SiteRoleDTO {

	private Long userId;

	private List<SiteDTO> siteList;

	private List<Role> roleList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<SiteDTO> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<SiteDTO> siteList) {
		this.siteList = siteList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
