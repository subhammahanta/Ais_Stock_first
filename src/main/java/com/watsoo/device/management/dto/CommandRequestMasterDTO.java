package com.watsoo.device.management.dto;

import java.util.Date;

import com.watsoo.device.management.enums.StatusEnum;
import com.watsoo.device.management.model.User;

public class CommandRequestMasterDTO {

	private Long id;

	private Integer totalCount;

	private StatusEnum status;

	private Date createdAt;

	private Long createdBy;

	private String requestCode;
	
	private User user;

	public CommandRequestMasterDTO() {
		super();
	}

	public CommandRequestMasterDTO(Long id, Integer totalCount, StatusEnum status, Date createdAt, Long createdBy,
			String requestCode) {
		super();
		this.id = id;
		this.totalCount = totalCount;
		this.status = status;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.requestCode = requestCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
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

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
	
	
