package com.watsoo.device.management.dto;

import java.util.Objects;

public class ExcelClientDTO {

	private String clientName;

	private String state;

	public ExcelClientDTO() {
		super();
	}

	public ExcelClientDTO(String clientName, String state) {
		super();
		this.clientName = clientName;
		this.state = state;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExcelClientDTO other = (ExcelClientDTO) obj;
		return Objects.equals(clientName, other.clientName) && Objects.equals(state, other.state);
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientName, state);
	}

	@Override
	public String toString() {
		return "ExcelClientDTO [clientName=" + clientName + ", state=" + state + "]";
	}

}
