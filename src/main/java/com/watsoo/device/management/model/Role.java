package com.watsoo.device.management.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.PermissionCategoryDTO;
import com.watsoo.device.management.dto.RoleDTO;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "name")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(Long id) {
		super();
		this.id = id;
	}

	public Role(String name, Long companyId, Boolean isActive) {
		super();
		this.name = name;
		this.companyId = companyId;
		this.isActive = isActive;
	}

	public Role(Long companyId, Boolean isActive, String name) {
		super();
		this.companyId = companyId;
		this.isActive = isActive;
		this.name = name;
	}

	public RoleDTO convertToDTO(List<Permission> webPermissionList, List<Permission> mobilePermissionList,
			List<PermissionCategoryDTO> webPermissionCategoryDTOList,
			List<PermissionCategoryDTO> mobilePermissionCategoryDTOList) {
		return new RoleDTO(this.id, this.name, this.companyId,
				webPermissionList.stream().map(Permission::convertEntityToDTO).collect(Collectors.toList()),
				mobilePermissionList.stream().map(Permission::convertEntityToDTO).collect(Collectors.toList()),
				webPermissionCategoryDTOList, mobilePermissionCategoryDTOList, this.isActive);
	}

	public RoleDTO covertToDTOV2() {
		return new RoleDTO(this.id, this.name);
	}
}
