package com.watsoo.device.management.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.watsoo.device.management.dto.SubMenuMasterDto;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;
import com.watsoo.device.management.dto.SubscriptionTypeDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatePlatformMasterDTO {

	private Integer id;

	private Long state;

	private Double plateformCharges;

	private Date createdAt;

	private Long createdBy;

	private String createdByName;

	private Date updatedAt;

	private Long updatedBy;

	private String updatedByName;

	private boolean isactive;

	public StatePlatformMasterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatePlatformMasterDTO(Integer id, Long state, Double plateformCharges, Date createdAt, Long createdBy,
			String createdByName, Date updatedAt, Long updatedBy, String updatedByName, boolean isactive) {
		super();
		this.id = id;
		this.state = state;
		this.plateformCharges = plateformCharges;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.createdByName = createdByName;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.updatedByName = updatedByName;
		this.isactive = isactive;
	}

	public StatePlatformMasterDTO(Integer id, Long state, Double plateformCharges, String createdByName,
			boolean isactive) {
		super();
		this.id = id;
		this.state = state;
		this.plateformCharges = plateformCharges;
		this.createdByName = createdByName;
		this.isactive = isactive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Double getPlateformCharges() {
		return plateformCharges;
	}

	public void setPlateformCharges(Double plateformCharges) {
		this.plateformCharges = plateformCharges;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

}