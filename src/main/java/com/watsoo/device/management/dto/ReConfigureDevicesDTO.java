package com.watsoo.device.management.dto;

public class ReConfigureDevicesDTO {

	private Long id;

	private String imeiNo;

	private Boolean isReConfigure;

	private Long reConfigBoxId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public Boolean getIsReConfigure() {
		return isReConfigure;
	}

	public void setIsReConfigure(Boolean isReConfigure) {
		this.isReConfigure = isReConfigure;
	}

	public Long getReConfigBoxId() {
		return reConfigBoxId;
	}

	public void setReConfigBoxId(Long reConfigBoxId) {
		this.reConfigBoxId = reConfigBoxId;
	}

}
