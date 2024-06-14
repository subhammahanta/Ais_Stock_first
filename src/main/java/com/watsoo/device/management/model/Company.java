package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watsoo.device.management.dto.CompanyDto;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "business_name")
	private String businessName;

	@Column(name = "type")
	private String type;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "address")
	private String address;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "email")
	private String email;

	@Column(name = "created_at")
	private Date createdAt;

	@OneToOne
	@JoinColumn(name = "created_by")
	@JsonIgnore
	private User createdBy;

	@Column(name = "modified_at")
	private Date modifiedAt;

	@Column(name = "modified_by")
	private Long modifiedBy;

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

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
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

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(Long id, String name, String businessName, String type, Boolean isActive, String address,
			String contactNo, String gstNo, String panNo, String email, Date createdAt, User createdBy, Date modifiedAt,
			Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.businessName = businessName;
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

	public Company(Long id) {
		super();
		this.id = id;
	}

	public CompanyDto convertToCompanyDto() {
		return new CompanyDto(this.getId(), this.getName(), this.getBusinessName(), this.getType(), this.getIsActive(),
				this.getAddress(), this.getContactNo(), this.getGstNo(), this.getPanNo(), this.getEmail(),
				this.getCreatedAt(), this.getCreatedBy().convertToDTO(), this.getModifiedAt(), this.getModifiedBy());
	}

}
