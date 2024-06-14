package com.watsoo.device.management.dto;

import java.util.Date;

public class CompanyDto {

	private Long id;

	private String name;

	private String bussinessName;

	private String type;

	private Boolean isActive;

	private String address;

	private String contactNo;

	private String gstNo;

	private String panNo;

	private String email;

	private Date createdAt;

	private UserDTO createdBy;

	private Date modifiedAt;

	private Long modifiedBy;

	public CompanyDto() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public UserDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public CompanyDto(Long id, String name, String bussinessName, String type, Boolean isActive, String address,
			String contactNo, String gstNo, String panNo, String email, Date createdAt, UserDTO createdBy,
			Date modifiedAt, Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.bussinessName = bussinessName;
		this.type = type;
		this.isActive = isActive;
		this.address = address;
		this.contactNo = contactNo;
		this.gstNo = gstNo;
		this.panNo = panNo;
		this.email = email;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

}