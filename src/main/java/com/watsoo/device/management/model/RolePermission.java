package com.watsoo.device.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role_permission")
public class RolePermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "for_mobile")
	private Boolean forMobile;

	@Column(name = "for_web")
	private Boolean forWeb;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToOne
	@JoinColumn(name="permission_id")
	private Permission permission;

	@OneToOne
	@JoinColumn(name = "permission_category_id")
	private PermissionCategory permissionCatagory;

	@OneToOne
	@JoinColumn(name="role_id")
	private Role role;
	
//	@OneToOne
//	@JoinColumn(name = "group_type_id")
//	private GroupType group_type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getForMobile() {
		return forMobile;
	}

	public void setForMobile(Boolean forMobile) {
		this.forMobile = forMobile;
	}

	public Boolean getForWeb() {
		return forWeb;
	}

	public void setForWeb(Boolean forWeb) {
		this.forWeb = forWeb;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public PermissionCategory getPermissionCatagory() {
		return permissionCatagory;
	}

	public void setPermissionCatagory(PermissionCategory permissionCatagory) {
		this.permissionCatagory = permissionCatagory;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

//	public GroupType getGroup_type() {
//		return group_type;
//	}
//
//	public void setGroup_type(GroupType group_type) {
//		this.group_type = group_type;
//	}

	public RolePermission() {
		super();
		
	}
	
	 public RolePermission(Role role, Permission permission, PermissionCategory permissionCategory, boolean forMobile, boolean forWeb, boolean isActive) {
	        this.role = role;
	        this.permission = permission;
	        this.permissionCatagory = permissionCategory;
	        this.forMobile = forMobile;
	        this.forWeb = forWeb;
	        this.isActive = isActive;
	    }

	public RolePermission(Boolean forMobile, Boolean forWeb, Boolean isActive, Permission permission,
			PermissionCategory permissionCatagory, Role role) {
		super();
		this.forMobile = forMobile;
		this.forWeb = forWeb;
		this.isActive = isActive;
		this.permission = permission;
		this.permissionCatagory = permissionCatagory;
		this.role = role;
	}

}
