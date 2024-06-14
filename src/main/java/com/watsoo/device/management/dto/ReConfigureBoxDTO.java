package com.watsoo.device.management.dto;

import java.util.Date;

import com.watsoo.device.management.model.User;

public class ReConfigureBoxDTO {

	private Long id;

	private Long boxId;

	private String boxNo;

	private User createdBy;

	private Date createdAt;

	private User updatedBy;

	private Date updatedAt;

	private Integer totalDevice;

	private Integer totalConfigureDevice;

	private Integer totalUnboxDevice;

	private Boolean isActive;

	private String configCommand;

	private Boolean isCompleted;

	private String reConfigBoxCode;
	
	private StateDTO stateDTO;
	
	private String unsettledBoxCode;
	
	private ProviderDTO provider;

	public ReConfigureBoxDTO() {
		super();
	}

	public ReConfigureBoxDTO(Long id, Long boxId, String boxNo, User createdBy, Date createdAt, User updatedBy,
			Date updatedAt, Integer totalDevice, Integer totalConfigureDevice, Integer totalUnboxDevice,
			Boolean isActive, String configCommand, Boolean isCompleted, String reConfigBoxCode) {
		super();
		this.id = id;
		this.boxId = boxId;
		this.boxNo = boxNo;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.totalDevice = totalDevice;
		this.totalConfigureDevice = totalConfigureDevice;
		this.totalUnboxDevice = totalUnboxDevice;
		this.isActive = isActive;
		this.configCommand = configCommand;
		this.isCompleted = isCompleted;
		this.reConfigBoxCode = reConfigBoxCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(Integer totalDevice) {
		this.totalDevice = totalDevice;
	}

	public Integer getTotalConfigureDevice() {
		return totalConfigureDevice;
	}

	public void setTotalConfigureDevice(Integer totalConfigureDevice) {
		this.totalConfigureDevice = totalConfigureDevice;
	}

	public Integer getTotalUnboxDevice() {
		return totalUnboxDevice;
	}

	public void setTotalUnboxDevice(Integer totalUnboxDevice) {
		this.totalUnboxDevice = totalUnboxDevice;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getConfigCommand() {
		return configCommand;
	}

	public void setConfigCommand(String configCommand) {
		this.configCommand = configCommand;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getReConfigBoxCode() {
		return reConfigBoxCode;
	}

	public void setReConfigBoxCode(String reConfigBoxCode) {
		this.reConfigBoxCode = reConfigBoxCode;
	}

	public StateDTO getStateDTO() {
		return stateDTO;
	}

	public void setStateDTO(StateDTO stateDTO) {
		this.stateDTO = stateDTO;
	}

	public String getUnsettledBoxCode() {
		return unsettledBoxCode;
	}

	public void setUnsettledBoxCode(String unsettledBoxCode) {
		this.unsettledBoxCode = unsettledBoxCode;
	}

	public ProviderDTO getProvider() {
		return provider;
	}

	public void setProvider(ProviderDTO provider) {
		this.provider = provider;
	}

}
