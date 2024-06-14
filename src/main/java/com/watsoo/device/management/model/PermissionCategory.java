package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.PermissionCategoryDTO;

@Entity
@Table(name = "permission_category")
public class PermissionCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	public PermissionCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PermissionCategory(Boolean isActive, String name) {
		super();
		this.isActive = isActive;
		this.name = name;
	}

	public PermissionCategoryDTO permissionCategoryToDTO(List<Permission> permissionList) {
		return new PermissionCategoryDTO(this.id, this.name, this.isActive,
				permissionList.stream().map(Permission::convertEntityToDTO).collect(Collectors.toList()));
	}
}
