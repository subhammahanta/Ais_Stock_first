package com.watsoo.device.management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "credential_master")
public class CredentialMasterLite implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	@Column(name = "name")
	private String name;

	private String password;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_type")
	private Long userTypeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = passwordEncoder.encode(password);
	}

	/**
	 * Check if given password matches with the saved one.
	 *
	 * @param password Raw password.
	 * @return True If password matches otherwise false.
	 */
	public boolean passwordMatches(String password) {
		return (passwordEncoder.matches(password, this.password));
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public CredentialMasterLite() {
		super();
		// TODO Auto-generated constructor stub
	}

}
