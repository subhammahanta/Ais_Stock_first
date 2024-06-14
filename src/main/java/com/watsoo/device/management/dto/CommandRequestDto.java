package com.watsoo.device.management.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.watsoo.device.management.model.Device;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandRequestDto {
	
	
	private Long id;
	private String imei;
	private String command;
	private Long userId;
	private List<CommandRequestDto> list;
	
	

	public CommandRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public CommandRequestDto(Long id, String imei, String command, Long userId, List<CommandRequestDto> list) {
		super();
		this.id = id;
		this.imei = imei;
		this.command = command;
		this.userId = userId;
		this.list = list;
	}



	public List<CommandRequestDto> getList() {
		return list;
	}

	public void setList(List<CommandRequestDto> list) {
		this.list = list;
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

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}
