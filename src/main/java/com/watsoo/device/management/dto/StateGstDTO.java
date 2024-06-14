package com.watsoo.device.management.dto;

public class StateGstDTO {

	private Long stateId;
	private String gst;
    private Long clientStateGstId;
    
	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public Long getClientStateGstId() {
		return clientStateGstId;
	}

	public void setClientStateGstId(Long clientStateGstId) {
		this.clientStateGstId = clientStateGstId;
	}

	public StateGstDTO(Long stateId, String gst) {
		super();
		this.stateId = stateId;
		this.gst = gst;
	}

	public StateGstDTO() {
		super();
	}

}
