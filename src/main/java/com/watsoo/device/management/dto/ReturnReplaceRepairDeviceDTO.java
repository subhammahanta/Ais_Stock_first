package com.watsoo.device.management.dto;

public class ReturnReplaceRepairDeviceDTO {

	private String originalImei;

	private String replacedByImei;

	public String getOriginalImei() {
		return originalImei;
	}

	public void setOriginalIimei(String originalImei) {
		this.originalImei = originalImei;
	}

	public String getReplacedByImei() {
		return replacedByImei;
	}

	public void setReplacedByImei(String replacedByImei) {
		this.replacedByImei = replacedByImei;
	}

	public ReturnReplaceRepairDeviceDTO(String originalImei, String replacedByImei) {
		super();
		this.originalImei = originalImei;
		this.replacedByImei = replacedByImei;
	}

	public ReturnReplaceRepairDeviceDTO() {
		super();
	}

}
