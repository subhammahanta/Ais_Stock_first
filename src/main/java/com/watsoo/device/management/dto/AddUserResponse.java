package com.watsoo.device.management.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.watsoo.device.management.enums.Gender;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddUserResponse {
	private Long id;

	private String employeeCode;

	private String systemGeneratedEmployeeCode;

	private String name;

	private String email;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String phoneNumber;

	private String address;

	private String image;

	private Double centerLatitude;

	private Double centerLongitude;

	private Double zoomLevel;

	private Integer dateFilterLimit;

	private Date createdAt;

	private Long createdBy;

	private Date updatedAt;

	private Long updatedBy;

	private Boolean isActive;

	private Boolean showDefaultDashboard;

	private Double fuelPrice;

	private Boolean showAddress;

	private Boolean isCustomer;

	private Boolean isDealer;

	private Long coreUserId;

	private String userLogoUrl;

	private Long minVehicleOnlineTime;

	private Boolean allowPasswordChange;

	private String visibleMobileTabValue;

	private Boolean isMobileRequired;

	private Boolean isAddressproofRequired;

	private Boolean isIdProofRequired;

	private Double negativeAmountLimit;

	private Long limitUpdatedBy;
	
	private Integer designation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public Integer getDateFilterLimit() {
		return dateFilterLimit;
	}

	public void setDateFilterLimit(Integer dateFilterLimit) {
		this.dateFilterLimit = dateFilterLimit;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getShowDefaultDashboard() {
		return showDefaultDashboard;
	}

	public void setShowDefaultDashboard(Boolean showDefaultDashboard) {
		this.showDefaultDashboard = showDefaultDashboard;
	}

	public Double getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(Double fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public Boolean getShowAddress() {
		return showAddress;
	}

	public void setShowAddress(Boolean showAddress) {
		this.showAddress = showAddress;
	}

	public Boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Boolean getIsDealer() {
		return isDealer;
	}

	public void setIsDealer(Boolean isDealer) {
		this.isDealer = isDealer;
	}

	public Long getCoreUserId() {
		return coreUserId;
	}

	public void setCoreUserId(Long coreUserId) {
		this.coreUserId = coreUserId;
	}

	public String getUserLogoUrl() {
		return userLogoUrl;
	}

	public void setUserLogoUrl(String userLogoUrl) {
		this.userLogoUrl = userLogoUrl;
	}

	public Long getMinVehicleOnlineTime() {
		return minVehicleOnlineTime;
	}

	public void setMinVehicleOnlineTime(Long minVehicleOnlineTime) {
		this.minVehicleOnlineTime = minVehicleOnlineTime;
	}

	public Boolean getAllowPasswordChange() {
		return allowPasswordChange;
	}

	public void setAllowPasswordChange(Boolean allowPasswordChange) {
		this.allowPasswordChange = allowPasswordChange;
	}

	public String getVisibleMobileTabValue() {
		return visibleMobileTabValue;
	}

	public void setVisibleMobileTabValue(String visibleMobileTabValue) {
		this.visibleMobileTabValue = visibleMobileTabValue;
	}

	public Boolean getIsMobileRequired() {
		return isMobileRequired;
	}

	public void setIsMobileRequired(Boolean isMobileRequired) {
		this.isMobileRequired = isMobileRequired;
	}

	public Boolean getIsAddressproofRequired() {
		return isAddressproofRequired;
	}

	public void setIsAddressproofRequired(Boolean isAddressproofRequired) {
		this.isAddressproofRequired = isAddressproofRequired;
	}

	public Boolean getIsIdProofRequired() {
		return isIdProofRequired;
	}

	public void setIsIdProofRequired(Boolean isIdProofRequired) {
		this.isIdProofRequired = isIdProofRequired;
	}

	public Double getNegativeAmountLimit() {
		return negativeAmountLimit;
	}

	public void setNegativeAmountLimit(Double negativeAmountLimit) {
		this.negativeAmountLimit = negativeAmountLimit;
	}

	public Long getLimitUpdatedBy() {
		return limitUpdatedBy;
	}

	public void setLimitUpdatedBy(Long limitUpdatedBy) {
		this.limitUpdatedBy = limitUpdatedBy;
	}

	public Integer getDesignation() {
		return designation;
	}

	public void setDesignation(Integer designation) {
		this.designation = designation;
	}

}
