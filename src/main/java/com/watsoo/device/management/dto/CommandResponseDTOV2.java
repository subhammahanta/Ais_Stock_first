package com.watsoo.device.management.dto;

import java.util.List;

import com.watsoo.device.management.model.Command;
import com.watsoo.device.management.model.ModelConfig;

public class CommandResponseDTOV2 {

	private List<Command> commands;
	private List<ModelConfig> modelConfigs;
	private List<Command> finalCommands;

	public CommandResponseDTOV2() {
		super();
	}

	public CommandResponseDTOV2(List<Command> commands, List<ModelConfig> modelConfigs) {
		super();
		this.commands = commands;
		this.modelConfigs = modelConfigs;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public List<ModelConfig> getModelConfigs() {
		return modelConfigs;
	}

	public void setModelConfigs(List<ModelConfig> modelConfigs) {
		this.modelConfigs = modelConfigs;
	}

	public List<Command> getFinalCommands() {
		return finalCommands;
	}

	public void setFinalCommands(List<Command> finalCommands) {
		this.finalCommands = finalCommands;
	}

}
