package com.watsoo.device.management.dto;
public class ConfigurationDTO {

	private String softwareVersion;

	public ConfigurationDTO(String softwareVersion) {
		super();
		this.softwareVersion = softwareVersion;
	}

	public ConfigurationDTO() {
		super();
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

}
