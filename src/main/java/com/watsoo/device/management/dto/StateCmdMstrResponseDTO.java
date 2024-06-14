package com.watsoo.device.management.dto;

import java.util.List;

public class StateCmdMstrResponseDTO {

	private String imeiNumber;
	private List<String> imeiNumberList;
	private List<StateCmdMstrDTO> stateCmdMstrDTOList;

	public StateCmdMstrResponseDTO() {
		super();
		
	}

	public StateCmdMstrResponseDTO(String imeiNumber, List<String> imeiNumberList,
			List<StateCmdMstrDTO> stateCmdMstrDTOList) {
		super();
		this.imeiNumber = imeiNumber;
		this.imeiNumberList = imeiNumberList;
		this.stateCmdMstrDTOList = stateCmdMstrDTOList;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public List<String> getImeiNumberList() {
		return imeiNumberList;
	}

	public void setImeiNumberList(List<String> imeiNumberList) {
		this.imeiNumberList = imeiNumberList;
	}

	public List<StateCmdMstrDTO> getStateCmdMstrDTOList() {
		return stateCmdMstrDTOList;
	}

	public void setStateCmdMstrDTOList(List<StateCmdMstrDTO> stateCmdMstrDTOList) {
		this.stateCmdMstrDTOList = stateCmdMstrDTOList;
	}

}
