package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.watsoo.device.management.enums.Gender;
import com.watsoo.device.management.model.Company;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.model.UserType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

	private Long id;

	private String salutation;

	private String firstName;

	private String lastName;

	private UserType userType;

	private String name;

	private String mobileNo;

	private String email;

	private String officialEmail;

	private Long company;

	private Boolean isActive;

	private Date createdAt;

	private Long createdBy;

	private Date updatedAt;

	private Long updatedBy;

	private String address;

	private List<SiteDTO> siteList;
	
	//-------------------//
	private String employeeCode;

	private String systemGeneratedEmployeeCode;


	private Gender gender;


	private String phoneNumber;



	private String image;

	private Long designation;

	private Double centerLatitude;

	private Double centerLongitude;

	private Double zoomLevel;

	private List<SiteDTO> site;

	private List<RoleDTO> roleDTOList;

	private String password;
	private String token;
	private Boolean showAddress;
	private String oldPassword;

	private Double negativeAmountLimit;
	private Double currentBalance;
	//------------------//
	public UserDTO() {
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

	public List<SiteDTO> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<SiteDTO> siteList) {
		this.siteList = siteList;
	}

	public User convertToEntity() {
		return new User(this.getId(), this.getSalutation(), this.getFirstName(), this.getLastName(), this.getUserType(),
				this.getFirstName(), this.getMobileNo(), this.getEmail(), this.getOfficialEmail(), new Company(this.getCompany()),
				this.getIsActive(), this.getCreatedAt(), this.getCreatedBy(), this.getUpdatedAt(), this.getUpdatedBy(),
				this.getAddress());
	}

	public UserDTO(Long id, String salutation, String firstName, String lastName, UserType userType, String name,
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

	public UserDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getSystemGeneratedEmployeeCode() {
		return systemGeneratedEmployeeCode;
	}

	public void setSystemGeneratedEmployeeCode(String systemGeneratedEmployeeCode) {
		this.systemGeneratedEmployeeCode = systemGeneratedEmployeeCode;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getDesignation() {
		return designation;
	}

	public void setDesignation(Long designation) {
		this.designation = designation;
	}

	public Double getCenterLatitude() {
		return centerLatitude;
	}

	public void setCenterLatitude(Double centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

	public Double getCenterLongitude() {
		return centerLongitude;
	}

	public void setCenterLongitude(Double centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public Double getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(Double zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public List<SiteDTO> getSite() {
		return site;
	}

	public void setSite(List<SiteDTO> site) {
		this.site = site;
	}

	public List<RoleDTO> getRoleDTOList() {
		return roleDTOList;
	}

	public void setRoleDTOList(List<RoleDTO> roleDTOList) {
		this.roleDTOList = roleDTOList;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getShowAddress() {
		return showAddress;
	}

	public void setShowAddress(Boolean showAddress) {
		this.showAddress = showAddress;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Double getNegativeAmountLimit() {
		return negativeAmountLimit;
	}

	public void setNegativeAmountLimit(Double negativeAmountLimit) {
		this.negativeAmountLimit = negativeAmountLimit;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

}
