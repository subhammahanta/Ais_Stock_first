package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.watsoo.device.management.model.Site;
import com.watsoo.device.management.model.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteDTO {

	private Long id;
	private String siteName;
	private String siteCode;
	private String siteAddress;
	private Long companyId;
	private Double latitude;
	private Double longitude;
	private String phoneNo;
	private Boolean isActive;
	private Long siteOwnerId;
	private String siteOwnerName;
	private Integer noOfUser;
	private Date createdAt;
	private Long createdBy;
	private Date modifiedAt;
	private Long modifiedBy;
	private String createdByName;
	private List<RoleDTO> roles;
	private List<Site> sites;
	private Long userId;

	private UserDTO siteOwnerInfo;

	public SiteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getSiteOwnerId() {
		return siteOwnerId;
	}

	public void setSiteOwnerId(Long siteOwnerId) {
		this.siteOwnerId = siteOwnerId;
	}

	public String getSiteOwnerName() {
		return siteOwnerName;
	}

	public void setSiteOwnerName(String siteOwnerName) {
		this.siteOwnerName = siteOwnerName;
	}

	public Integer getNoOfUser() {
		return noOfUser;
	}

	public void setNoOfUser(Integer noOfUser) {
		this.noOfUser = noOfUser;
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

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public UserDTO getSiteOwnerInfo() {
		return siteOwnerInfo;
	}

	public void setSiteOwnerInfo(UserDTO siteOwnerInfo) {
		this.siteOwnerInfo = siteOwnerInfo;
	}

	public SiteDTO(Long id, String siteName, String siteCode, String siteAddress, Long companyId, Double latitude,
			Double longitude, String phoneNo, Boolean isActive, Long siteOwnerId, String siteOwnerName,
			Integer noOfUser, Date createdAt, Long createdBy, Date modifiedAt, Long modifiedBy, String createdByName,
			UserDTO siteOwnerInfo) {
		super();
		this.id = id;
		this.siteName = siteName;
		this.siteCode = siteCode;
		this.siteAddress = siteAddress;
		this.companyId = companyId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phoneNo = phoneNo;
		this.isActive = isActive;
		this.siteOwnerId = siteOwnerId;
		this.siteOwnerName = siteOwnerName;
		this.noOfUser = noOfUser;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
		this.createdByName = createdByName;
		this.siteOwnerInfo = siteOwnerInfo;

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public Site convertToSite() {
		return new Site(this.getId(), this.getSiteName(), this.getSiteCode(), this.getSiteAddress(),
				this.getCompanyId(), this.getLatitude(), this.getLongitude(),
				this.getPhoneNo(), this.getIsActive() != null ? this.getIsActive() : true,
				this.getSiteOwnerId() != null ? new User(this.getSiteOwnerId()) : null, this.getNoOfUser(),
				this.getCreatedAt(), this.getCreatedBy() != null ? new User(this.getCreatedBy()) : new User(1L),
				this.getModifiedAt(), this.getModifiedBy());
	}

}
