package com.watsoo.device.management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "software_version_command")
public class SoftwareVersionCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "software_version")
	private String softwareVersion;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "command")
	private String command;

	public SoftwareVersionCommand() {
		super();
	}

	public SoftwareVersionCommand(Long id, String softwareVersion, Boolean isActive, String command) {
		super();
		this.id = id;
		this.softwareVersion = softwareVersion;
		this.isActive = isActive;
		this.command = command;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
