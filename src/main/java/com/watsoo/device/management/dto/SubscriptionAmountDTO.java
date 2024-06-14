package com.watsoo.device.management.dto;

public class SubscriptionAmountDTO {

	private Long subscriptionMasterId;
	private Double amount;
	private Integer clientSubscriptionMasterId;
	private Long statePlatformMstrId;

	public Long getSubscriptionMasterId() {
		return subscriptionMasterId;
	}

	public void setSubscriptionMasterId(Long subscriptionMasterId) {
		this.subscriptionMasterId = subscriptionMasterId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getClientSubscriptionMasterId() {
		return clientSubscriptionMasterId;
	}

	public void setClientSubscriptionMasterId(Integer clientSubscriptionMasterId) {
		this.clientSubscriptionMasterId = clientSubscriptionMasterId;
	}

	public Long getStatePlatformMstrId() {
		return statePlatformMstrId;
	}

	public void setStatePlatformMstrId(Long statePlatformMstrId) {
		this.statePlatformMstrId = statePlatformMstrId;
	}

}
