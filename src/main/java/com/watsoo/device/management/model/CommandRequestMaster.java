package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.CommandRequestMasterDTO;
import com.watsoo.device.management.enums.StatusEnum;

@Entity
@Table(name = "command_request_master")
public class CommandRequestMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "total_count")
	private Integer totalCount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusEnum  status;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "request_Code")
	private String requestCode;
	

	public CommandRequestMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommandRequestMaster(Long id, Integer totalCount, Date createdAt, Long createdBy) {
		super();
		this.id = id;
		this.totalCount = totalCount;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
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
	public CommandRequestMasterDTO convertEntityToDTO(CommandRequestMaster commandRequestMaster) {
		// TODO Auto-generated method stub
		return new CommandRequestMasterDTO(commandRequestMaster.getId(), commandRequestMaster.getTotalCount(),
				commandRequestMaster.getStatus(), commandRequestMaster.getCreatedAt(), commandRequestMaster.getCreatedBy(), commandRequestMaster.getRequestCode());
	}
	
}