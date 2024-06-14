package com.watsoo.device.management.dto;

import java.util.Date;

public class FilterDateDTO {
	private Date fromDate;
	private Date todDate;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getTodDate() {
		return todDate;
	}

	public void setTodDate(Date todDate) {
		this.todDate = todDate;
	}

}
