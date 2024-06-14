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

import org.hibernate.annotations.CreationTimestamp;

import com.watsoo.device.management.dto.UserDTO;

@Entity
@Table(name = "user")
public class UserLite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "salutation")
	private String salutation;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@OneToOne
	@JoinColumn(name = "user_type")
	private UserType userType;

	@Column(name = "name")
	private String name;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "email")
	private String email;

	@Column(name = "official_email")
	private String officialEmail;

	@Column(name = "company_id")
	private Long company;

	@Column(name = "is_active")
	private Boolean isActive;

	@CreationTimestamp
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@CreationTimestamp
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "address")
	private String address;

	public UserLite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserLite(Long id, String salutation, String firstName, String lastName, UserType userType, String name,
			String mobileNo, String email, String officialEmail, Long company, Boolean isActive, Date createdAt,
			Long createdBy, Date updatedAt, Long updatedBy, String address) {
		super();
		this.id = id;
		this.salutation = salutation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.name = name;
		this.mobileNo = mobileNo;
		this.email = email;
		this.officialEmail = officialEmail;
		this.company = company;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.address = address;
	}

	public UserDTO convertToDTO() {
		return new UserDTO(this.getId(), this.getSalutation(), this.getFirstName(), this.getLastName(),
				this.getUserType(), this.getName(), this.getMobileNo(), this.getEmail(), this.getOfficialEmail(),
				this.getCompany() != null ? this.getCompany() : null, this.getIsActive(), this.getCreatedAt(),
				this.getCreatedBy() != null ? this.getCreatedBy() : null, this.getUpdatedAt(),
				this.getUpdatedBy() != null ? this.getUpdatedBy() : null, this.getAddress());
	}

	public UserLite(Long id) {
		super();
		this.id = id;
	}

}
