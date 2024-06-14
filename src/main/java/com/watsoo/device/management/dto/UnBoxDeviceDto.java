package com.watsoo.device.management.dto;

import java.util.Date;

import com.watsoo.device.management.model.UnBoxDevice;

public class UnBoxDeviceDto {
	
	private Long id;
	private String boxNumber;
	private Date unboxDate;
	private String unboxBy;
	private Double quantity;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoxNumber() {
		return boxNumber;
	}
	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}
	public Date getUnboxDate() {
		return unboxDate;
	}
	public void setUnboxDate(Date unboxDate) {
		this.unboxDate = unboxDate;
	}
	public String getUnboxBy() {
		return unboxBy;
	}
	public void setUnboxBy(String unboxBy) {
		this.unboxBy = unboxBy;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public UnBoxDeviceDto(Long id, String boxNumber, Date unboxDate, String unboxBy, Double quantity) {
		super();
		this.id = id;
		this.boxNumber = boxNumber;
		this.unboxDate = unboxDate;
		this.unboxBy = unboxBy;
		this.quantity = quantity;
	}
	public UnBoxDeviceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	  public UnBoxDevice convertToEntity() {
		   UnBoxDevice entity = new UnBoxDevice();
	        entity.setId(this.id);
	        entity.setBoxNumber(this.boxNumber);
	        entity.setUnboxDate(this.unboxDate);
	        entity.setUnboxBy(this.unboxBy);
	        entity.setQuantity(this.quantity);
	        return entity;
	    }
	
}
