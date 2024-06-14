package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.UnBoxDeviceDto;

@Entity
@Table(name = "unbox_device")
public class UnBoxDevice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "box_number")
	private String boxNumber;
	
	@Column(name = "unbox_date")
	private Date unboxDate;
	
	@Column(name = "unbox_by")
	private String unboxBy;
	
	@Column(name = "quantity")
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

	public UnBoxDevice(Long id, String boxNumber, Date unboxDate, String unboxBy, Double quantity) {
		super();
		this.id = id;
		this.boxNumber = boxNumber;
		this.unboxDate = unboxDate;
		this.unboxBy = unboxBy;
		this.quantity = quantity;
	}

	public UnBoxDevice() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public UnBoxDeviceDto convertToDto() {
    	UnBoxDeviceDto dto = new UnBoxDeviceDto();
        dto.setId(this.id);
        dto.setBoxNumber(this.boxNumber);
        dto.setUnboxDate(this.unboxDate);
        dto.setUnboxBy(this.unboxBy);
        dto.setQuantity(this.quantity);
      
        return dto;
    }
}
