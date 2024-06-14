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
import com.watsoo.device.management.dto.SiteDTO;
import com.watsoo.device.management.dto.UserDTO;

@Entity
@Table(name = "site")
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "site_name")
	private String siteName;

	@Column(name = "site_code")
	private String siteCode;

	@Column(name = "site_address")
	private String siteAddress;

	@JsonIgnore
	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "lontitude")
	private Double longitude;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "site_owner_id")
	private User siteOwnerId;

	@Column(name = "no_of_user")
	private Integer noOfUser;

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

	public Site() {
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

	public User getSiteOwnerId() {
		return siteOwnerId;
	}

	public void setSiteOwnerId(User siteOwnerId) {
		this.siteOwnerId = siteOwnerId;
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

	public Site(Long id) {
		super();
		this.id = id;
	}

	public Site(Long id, String siteName) {
		super();
		this.id = id;
		this.siteName = siteName;
	}

	public Site(Long id, String siteName, String siteCode, String siteAddress, Long companyId, Double latitude,
			Double longitude, String phoneNo, Boolean isActive, User siteOwnerId, Integer noOfUser, Date createdAt,
			User createdBy, Date modifiedAt, Long modifiedBy) {
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
		this.noOfUser = noOfUser;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public SiteDTO convertToSiteDto() {
		return new SiteDTO(this.getId(), this.getSiteName(), this.getSiteCode(), this.getSiteAddress(),
				this.getCompanyId() != null && this.getCompanyId() != null ? this.getCompanyId() : null,
				this.getLatitude(), this.getLongitude(), this.getPhoneNo(),
				this.getIsActive() != null ? this.getIsActive() : true,
				this.getSiteOwnerId() != null && this.getSiteOwnerId().getId() != null ? this.getSiteOwnerId().getId()
						: null,
				this.getSiteOwnerId() != null && this.getSiteOwnerId().getName() != null
						? this.getSiteOwnerId().getName()
						: null,
				this.getNoOfUser(), this.getCreatedAt(), this.getCreatedBy().getId(), this.getModifiedAt(),
				this.getModifiedBy(),
				this.getCreatedBy() != null && this.getCreatedBy().getName() != null ? this.getCreatedBy().getName()
						: null,
				new UserDTO(
						this.getSiteOwnerId() != null && this.getSiteOwnerId().getId() != null
								? this.getSiteOwnerId().getId()
								: null,
						this.getSiteOwnerId() != null && this.getSiteOwnerId().getName() != null
								? this.getSiteOwnerId().getName()
								: null));
	}

}
