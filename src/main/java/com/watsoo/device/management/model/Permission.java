package com.watsoo.device.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.watsoo.device.management.dto.PermissionDTO;

@Entity
@Table(name = "permission")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "for_mobile")
	private Boolean forMobile;

	@Column(name = "for_web")
	private Boolean forWeb;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "method_type")
	private String methodType;

	@Column(name = "name")
	private String name;

	@Column(name = "url")
	private String url;

	@OneToOne
    @JoinColumn(name = "permission_category_id")
    private PermissionCategory permissionCategory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_type_id")
	private GroupTypeName groupTypeId;

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

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PermissionCategory getPermissionCategory() {
		return permissionCategory;
	}

	public void setPermissionCategory(PermissionCategory permissionCategory) {
		this.permissionCategory = permissionCategory;
	}

	public GroupTypeName getGroupTypeId() {
		return groupTypeId;
	}

	public void setGroupTypeId(GroupTypeName groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Permission(Boolean forMobile, Boolean forWeb, Boolean isActive, String methodType, String name, String url,
			PermissionCategory permissionCatagory) {
		super();
		this.forMobile = forMobile;
		this.forWeb = forWeb;
		this.isActive = isActive;
		this.methodType = methodType;
		this.name = name;
		this.url = url;
		this.permissionCategory = permissionCatagory;
	}

	public PermissionDTO convertEntityToDTO() {
		return new PermissionDTO(this.id, this.name, this.forWeb, this.forMobile, this.isActive,
				this.groupTypeId);
	}
}
