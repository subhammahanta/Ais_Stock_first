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

import com.watsoo.device.management.dto.ClientDTO;

@Entity
@Table(name = "client")
public class ClientLite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_id")
	private String companyId;
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "company_address")
	private String companyAddress;
	@Column(name = "state_id")
	private Long state;
	@Column(name = "city")
	private String city;
	@Column(name = "email")
	private String email;
	@Column(name = "phone_number")
	private Long phoneNumber;
	@Column(name = "pan_number")
	private String panNumber;
	@Column(name = "company_logo")
	private String companyLogo;
	@Column(name = "company_code")
	private String companyCode;
	@Column(name = "last_issue_date")
	private Date lastIssueDate;
	@Column(name = "last_issue_quantity")
	private Long lastIssueQuantity;
	@Column(name = "is_active")
	private Boolean isActive;
	@Column(name = "gst_number")
	private String gstNumber;
	@Column(name = "is_own")
	private Boolean isOwn;
	@Column(name = "ais_admin_user_id")
	private Long aisUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getLastIssueDate() {
		return lastIssueDate;
	}

	public void setLastIssueDate(Date lastIssueDate) {
		this.lastIssueDate = lastIssueDate;
	}

	public Long getLastIssueQuantity() {
		return lastIssueQuantity;
	}

	public void setLastIssueQuantity(Long lastIssueQuantity) {
		this.lastIssueQuantity = lastIssueQuantity;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public Boolean getIsOwn() {
		return isOwn;
	}

	public void setIsOwn(Boolean isOwn) {
		this.isOwn = isOwn;
	}

	public Long getAisUserId() {
		return aisUserId;
	}

	public void setAisUserId(Long aisUserId) {
		this.aisUserId = aisUserId;
	}

	public ClientLite(Long id, String companyId, String companyName, String companyAddress, Long state, String city,
			String email, Long phoneNumber, String panNumber, String companyLogo, String companyCode,
			Date lastIssueDate, Long lastIssueQuantity, Boolean isActive) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.state = state;
		this.city = city;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.panNumber = panNumber;
		this.companyLogo = companyLogo;
		this.companyCode = companyCode;
		this.lastIssueDate = lastIssueDate;
		this.lastIssueQuantity = lastIssueQuantity;
		this.isActive = isActive;
	}

	public ClientLite(Long id, String companyId, String companyName, String companyAddress, Long state, String city,
			String email, Long phoneNumber, String panNumber, String companyLogo, String companyCode,
			Date lastIssueDate, Long lastIssueQuantity, Boolean isActive, String gstNumber, Boolean isOwn) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.state = state;
		this.city = city;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.panNumber = panNumber;
		this.companyLogo = companyLogo;
		this.companyCode = companyCode;
		this.lastIssueDate = lastIssueDate;
		this.lastIssueQuantity = lastIssueQuantity;
		this.isActive = isActive;
		this.gstNumber = gstNumber;
		this.isOwn = isOwn;
	}

	public ClientLite() {
		super();
	}

	public ClientDTO convertEntityToDto(ClientLite client) {

		return new ClientDTO(client.getId(), client.getCompanyId(), client.getCompanyName(), client.getCompanyAddress(),
				client.getState(), client.getCity(), client.getEmail(), client.getPhoneNumber(), client.getPanNumber(),
				client.getCompanyLogo(), client.getCompanyCode(), client.getLastIssueDate(),
				client.getLastIssueQuantity(), client.getIsActive(), client.getGstNumber(), client.getIsOwn());
	}

}
