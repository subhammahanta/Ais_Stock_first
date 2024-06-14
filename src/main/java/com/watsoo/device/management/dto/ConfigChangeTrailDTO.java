package com.watsoo.device.management.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.watsoo.device.management.enums.OperationType;

public class ConfigChangeTrailDTO {

	private Long id;

	private Long stateCmdMstrId;

	private Date modifiedAt;

	private Long modifiedBy;

	private String user;

	@Enumerated(EnumType.STRING)
	private OperationType operationType;

	private String updatedCommand;

	private String lastCommand;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStateCmdMstrId() {
		return stateCmdMstrId;
	}

	public void setStateCmdMstrId(Long stateCmdMstrId) {
		this.stateCmdMstrId = stateCmdMstrId;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public String getUpdatedCommand() {
		return updatedCommand;
	}

	public void setUpdatedCommand(String updatedCommand) {
		this.updatedCommand = updatedCommand;
	}

	public String getLastCommand() {
		return lastCommand;
	}

	public void setLastCommand(String lastCommand) {
		this.lastCommand = lastCommand;
	}

	public ConfigChangeTrailDTO(Long id, Long stateCmdMstrId, Date modifiedAt, Long modifiedBy,
			OperationType operationType, String user, String updatedCommand, String lastCommand) {
		super();
		this.id = id;
		this.stateCmdMstrId = stateCmdMstrId;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
		this.operationType = operationType;
		this.user = user;
		this.updatedCommand = updatedCommand;
		this.lastCommand = lastCommand;
	}

	public ConfigChangeTrailDTO() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
