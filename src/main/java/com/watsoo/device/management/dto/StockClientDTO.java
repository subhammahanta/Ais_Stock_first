package com.watsoo.device.management.dto;

public class StockClientDTO {

	private String empCode;
	private String city;
	private String state;
	private String name;
	private String contactPerson;
	private String contactNo;
	private String address;
	private String emailId;
	private String cliendCode;
	private String panCardNumber;
	private String gstNumber;
	private String visitingCardUrl;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailid(String emailId) {
		this.emailId = emailId;
	}

	public String getCliendCode() {
		return cliendCode;
	}

	public void setCliendCode(String cliendCode) {
		this.cliendCode = cliendCode;
	}

	public String getPanCardNumber() {
		return panCardNumber;
	}

	public void setPanCardNumber(String panCardNumber) {
		this.panCardNumber = panCardNumber;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getVisitingCardUrl() {
		return visitingCardUrl;
	}

	public void setVisitingCardUrl(String visitingCardUrl) {
		this.visitingCardUrl = visitingCardUrl;
	}

	public StockClientDTO(String empCode, String city, String state, String name, String contactPerson,
			String contactNo, String address, String emailId, String cliendCode, String panCardNumber, String gstNumber,
			String visitingCardUrl) {
		super();
		this.empCode = empCode;
		this.city = city;
		this.state = state;
		this.name = name;
		this.contactPerson = contactPerson;
		this.contactNo = contactNo;
		this.address = address;
		this.emailId = emailId;
		this.cliendCode = cliendCode;
		this.panCardNumber = panCardNumber;
		this.gstNumber = gstNumber;
		this.visitingCardUrl = visitingCardUrl;
	}

	public StockClientDTO() {
		super();
	}

}