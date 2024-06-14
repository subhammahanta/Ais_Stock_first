package com.watsoo.device.management.dto;

public class SoapDTO {

	private String displayName;
	private String firstName;
	private String lastName;
	private String name;
	private String password;
	private String zimbraAccountStatus;

	public SoapDTO() {
		super();
	}

	public SoapDTO(String displayName, String firstName, String lastName, String name, String password,String zimbraAccountStatus) {
		super();
		this.displayName = displayName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.password = password;
		this.zimbraAccountStatus = zimbraAccountStatus;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getZimbraAccountStatus() {
		return zimbraAccountStatus;
	}

	public void setZimbraAccountStatus(String zimbraAccountStatus) {
		this.zimbraAccountStatus = zimbraAccountStatus;
	}

}
