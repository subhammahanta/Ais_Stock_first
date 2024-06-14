package com.watsoo.device.management.dto;

import java.util.List;

public class AllCommandRequestResponse {
	private int totalPages;
	private int numberOfElements;
	private int totalElements;
	private List<CommandRequestDTO> data;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public List<CommandRequestDTO> getData() {
		return data;
	}

	public void setData(List<CommandRequestDTO> data) {
		this.data = data;
	}

}
