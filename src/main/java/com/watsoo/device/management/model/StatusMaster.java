package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.StatusMasterDTO;

@Entity
@Table(name = "status_master")
public class StatusMaster implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "is_active")
	private Boolean isActive;
//	@Column(name = "created_by")
//	private User createdBy;
//	@Column(name = "created_at")
//	private Date createdAt;

	public StatusMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatusMaster(Long id, String name, Boolean isActive, User createdBy, Date createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
//		this.createdBy = createdBy;
//		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

//	public User getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(User createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public Date getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}


	public StatusMasterDTO convertEntityToDto() {
		return new StatusMasterDTO(this.id, this.name, this.isActive, new Date());
	}

}
