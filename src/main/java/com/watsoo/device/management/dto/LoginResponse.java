package com.watsoo.device.management.dto;

import java.util.Date;

public class LoginResponse {

	private Long id;
	private String name;
	private String email;
	private Date loginTimeStamp;
	private String password;
	private String token;
	private String responsePhrase;
	private Integer responseCode;

	public LoginResponse() {
		super();
	}

	public LoginResponse(Long id, String name, String email, Date loginTimeStamp) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.loginTimeStamp = loginTimeStamp;
	}

	public LoginResponse(Long id, String name, String email, Date loginTimeStamp, String password, String token) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.loginTimeStamp = loginTimeStamp;
		this.password = password;
		this.token = token;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLoginTimeStamp() {
		return loginTimeStamp;
	}

	public void setLoginTimeStamp(Date loginTimeStamp) {
		this.loginTimeStamp = loginTimeStamp;
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

	public String getResponsePhrase() {
		return responsePhrase;
	}

	public void setResponsePhrase(String responsePhrase) {
		this.responsePhrase = responsePhrase;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	
}
