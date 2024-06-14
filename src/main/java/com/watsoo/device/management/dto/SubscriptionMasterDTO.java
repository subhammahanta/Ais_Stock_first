package com.watsoo.device.management.dto;

public class SubscriptionMasterDTO {

	private Long id;

	private Integer subsTypeId;

	private String viewAs;

	private Double defaultAmount;

	private Integer totalDays;

	public SubscriptionMasterDTO() {
		super();
	}

	public Integer getSubsTypeId() {
		return subsTypeId;
	}

	public void setSubsTypeId(Integer subsTypeId) {
		this.subsTypeId = subsTypeId;
	}

	public SubscriptionMasterDTO(Long id, Integer subsTypeId, String viewAs, Double defaultAmount, Integer totalDays) {
		super();
		this.id = id;
		this.subsTypeId = subsTypeId;
		this.viewAs = viewAs;
		this.defaultAmount = defaultAmount;
		this.totalDays = totalDays;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getViewAs() {
		return viewAs;
	}

	public void setViewAs(String viewAs) {
		this.viewAs = viewAs;
	}

	public Double getDefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(Double defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

}
