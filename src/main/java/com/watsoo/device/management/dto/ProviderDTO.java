package com.watsoo.device.management.dto;

public class ProviderDTO {

	private Long id;

	private String name;

	private int iccidLengthLimit;

	public ProviderDTO(Long id, String name, int iccidLengthLimit) {
		super();
		this.id = id;
		this.name = name;
		this.iccidLengthLimit = iccidLengthLimit;
	}

	public ProviderDTO() {
		super();
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

	public int getIccidLengthLimit() {
		return iccidLengthLimit;
	}

	public void setIccidLengthLimit(int iccidLengthLimit) {
		this.iccidLengthLimit = iccidLengthLimit;
	}

}
