package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.watsoo.device.management.dto.CommandsDetailsDTO;

@Entity
@Table(name = "command_details")
public class CommandDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private CommandRequestMaster commandRequestMaster;

	@Column(name = "imei")
	private String imei;

	@Column(name = "command")
	private String command;

	@Column(name = "last_shoot_time")
	private Date lastShootTime;

	@Column(name = "last_shoot_response")
	private String lastShootResponse;

	public CommandDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommandDetails(Long id, CommandRequestMaster commandRequestMaster, String imei, String command,
			Date lastShootTime, String lastShootResponse) {
		super();
		this.id = id;
		this.commandRequestMaster = commandRequestMaster;
		this.imei = imei;
		this.command = command;
		this.lastShootTime = lastShootTime;
		this.lastShootResponse = lastShootResponse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public CommandRequestMaster getCommandRequestMaster() {
		return commandRequestMaster;
	}

	public void setCommandRequestMaster(CommandRequestMaster commandRequestMaster) {
		this.commandRequestMaster = commandRequestMaster;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Date getLastShootTime() {
		return lastShootTime;
	}

	public void setLastShootTime(Date lastShootTime) {
		this.lastShootTime = lastShootTime;
	}

	public String getLastShootResponse() {
		return lastShootResponse;
	}

	public void setLastShootResponse(String lastShootResponse) {
		this.lastShootResponse = lastShootResponse;
	}

	public CommandsDetailsDTO convertEntityToDTO(CommandDetails commandDetails) {
		// TODO Auto-generated method stub
		return new CommandsDetailsDTO(commandDetails.getId(), commandDetails.getImei(),
				commandDetails.getCommandRequestMaster().getId(), commandDetails.getCommand(),
				commandDetails.getLastShootResponse(), commandDetails.getLastShootTime());
	}

}
