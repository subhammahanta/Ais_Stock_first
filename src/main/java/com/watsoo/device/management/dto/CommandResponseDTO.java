package com.watsoo.device.management.dto;

import com.watsoo.device.management.model.Command;
import com.watsoo.device.management.model.ModelConfig;

public class CommandResponseDTO {

	private ModelConfig modelConfig;
	private Command masterCommand;
	private Long testedDeviceId;
	private Long lotId;

	public ModelConfig getModelConfig() {
		return modelConfig;
	}

	public void setModelConfig(ModelConfig modelConfig) {
		this.modelConfig = modelConfig;
	}

	public Command getMasterCommand() {
		return masterCommand;
	}

	public void setMasterCommand(Command masterCommand) {
		this.masterCommand = masterCommand;
	}

	public Long getTestedDeviceId() {
		return testedDeviceId;
	}

	public void setTestedDeviceId(Long testedDeviceId) {
		this.testedDeviceId = testedDeviceId;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public CommandResponseDTO() {
		super();
	}

	public CommandResponseDTO(ModelConfig modelConfig, Command masterCommand, Long testedDeviceId, Long lotId) {
		super();
		this.modelConfig = modelConfig;
		this.masterCommand = masterCommand;
		this.testedDeviceId = testedDeviceId;
		this.lotId = lotId;
	}

}
