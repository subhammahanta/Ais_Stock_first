package com.watsoo.device.management.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.watsoo.device.management.model.Role;

public class RoleDTO {

	private Long id;

	private String name;

	private Long companyId;

	public RoleDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public RoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Long> webPermissionIdList = new HashSet<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Long> mobilePermissionIdList = new HashSet<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Long> webPermissionCategoryIdList = new HashSet<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Long> mobilePermissionCategoryIdList = new HashSet<>();

	private Boolean isActive;

	private List<PermissionDTO> webPermissionDTOList;

	private List<PermissionDTO> mobilePermissionDTOList;

	private List<PermissionCategoryDTO> webPermissionCategoryDTOList;

	private List<PermissionCategoryDTO> mobilePermissionCategoryDTOList;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Set<Long> getWebPermissionIdList() {
		return webPermissionIdList;
	}

	public void setWebPermissionIdList(Set<Long> webPermissionIdList) {
		this.webPermissionIdList = webPermissionIdList;
	}

	public Set<Long> getMobilePermissionIdList() {
		return mobilePermissionIdList;
	}

	public void setMobilePermissionIdList(Set<Long> mobilePermissionIdList) {
		this.mobilePermissionIdList = mobilePermissionIdList;
	}

	public Set<Long> getWebPermissionCategoryIdList() {
		return webPermissionCategoryIdList;
	}

	public void setWebPermissionCategoryIdList(Set<Long> webPermissionCategoryIdList) {
		this.webPermissionCategoryIdList = webPermissionCategoryIdList;
	}

	public Set<Long> getMobilePermissionCategoryIdList() {
		return mobilePermissionCategoryIdList;
	}

	public void setMobilePermissionCategoryIdList(Set<Long> mobilePermissionCategoryIdList) {
		this.mobilePermissionCategoryIdList = mobilePermissionCategoryIdList;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<PermissionDTO> getWebPermissionDTOList() {
		return webPermissionDTOList;
	}

	public void setWebPermissionDTOList(List<PermissionDTO> webPermissionDTOList) {
		this.webPermissionDTOList = webPermissionDTOList;
	}

	public List<PermissionDTO> getMobilePermissionDTOList() {
		return mobilePermissionDTOList;
	}

	public void setMobilePermissionDTOList(List<PermissionDTO> mobilePermissionDTOList) {
		this.mobilePermissionDTOList = mobilePermissionDTOList;
	}

	public List<PermissionCategoryDTO> getWebPermissionCategoryDTOList() {
		return webPermissionCategoryDTOList;
	}

	public void setWebPermissionCategoryDTOList(List<PermissionCategoryDTO> webPermissionCategoryDTOList) {
		this.webPermissionCategoryDTOList = webPermissionCategoryDTOList;
	}

	public List<PermissionCategoryDTO> getMobilePermissionCategoryDTOList() {
		return mobilePermissionCategoryDTOList;
	}

	public void setMobilePermissionCategoryDTOList(List<PermissionCategoryDTO> mobilePermissionCategoryDTOList) {
		this.mobilePermissionCategoryDTOList = mobilePermissionCategoryDTOList;
	}

	public RoleDTO(Long id, String name, Long companyId, List<PermissionDTO> webPermissionDTOList,
			List<PermissionDTO> mobilePermissionDTOList, List<PermissionCategoryDTO> webPermissionCategoryDTOList,
			List<PermissionCategoryDTO> mobilePermissionCategoryDTOList, Boolean isActive) {
		this.id = id;
		this.name = name;
		this.companyId = companyId;
		this.webPermissionDTOList = webPermissionDTOList;
		this.mobilePermissionDTOList = mobilePermissionDTOList;
		this.webPermissionCategoryDTOList = webPermissionCategoryDTOList;
		this.mobilePermissionCategoryDTOList = mobilePermissionCategoryDTOList;
		this.isActive = isActive;
	}

	public Role convertDTOToEntity() {
		return new Role(this.name, this.companyId, true);
	}

}
