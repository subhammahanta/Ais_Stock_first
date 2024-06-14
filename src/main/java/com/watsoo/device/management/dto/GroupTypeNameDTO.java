package com.watsoo.device.management.dto;

import java.util.List;

public class GroupTypeNameDTO {

	private Long id;

	private String name;

	// @JsonIgnore
	private List<PermissionDTO> permissionDTOList;

	public GroupTypeNameDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GroupTypeNameDTO(Long id, String name, List<PermissionDTO> permissionDTOList) {
		super();
		this.id = id;
		this.name = name;
		this.permissionDTOList = permissionDTOList;
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

	public List<PermissionDTO> getPermissionDTOList() {
		return permissionDTOList;
	}

	public void setPermissionDTOList(List<PermissionDTO> permissionDTOList) {
		this.permissionDTOList = permissionDTOList;
	}
}
