package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.dto.ConfigChangeTrailDTO;
import com.watsoo.device.management.enums.OperationType;

@Entity
@Table(name = "config_change_trail")
public class ConfigChangeTrail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "state_command_mstr_id")
	private Long stateCmdMstrId;

	@Column(name = "modified_at")
	private Date modifiedAt;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "user")
	private String user;

	@Column(name = "operation_type")
	@Enumerated(EnumType.STRING)
	private OperationType operationType;

	@Column(name = "updated_command")
	private String updatedCommand;

	@Column(name = "last_command")
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public ConfigChangeTrail(Long id, Long stateCmdMstrId, Date modifiedAt, Long modifiedBy,
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

	public ConfigChangeTrail() {
		super();
	}

	public ConfigChangeTrailDTO convertToDto() {
		return new ConfigChangeTrailDTO(this.id, this.stateCmdMstrId, this.modifiedAt, this.getModifiedBy(),
				this.getOperationType(), this.getUser(), this.getUpdatedCommand(), this.getLastCommand());
	}
}
