package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

public class RepairReplaceDeviceMasterDTO {
	
	private Long id;

	private List<RepairReplaceDeviceDTO> repairReplaceDevices;

	private String repairCode;

	private ClientDTO client;

	private StateDTO state;

	private Integer totalDevice;

	private Integer repairedDevice;

	private Integer replacedDevice;

	private Integer pendingDevice;

	private String ewayBillNo;

	private String ewayBillImage;

	private String createdUserName;

	private Long createdBy;

	private Date createdAt;

	private Boolean isActive;

	private Long updatedBy;

	private Date updatedAt;

	private String updatedUserName;

	private Double totalCost;
	
	private Integer currentQuantity;

	public RepairReplaceDeviceMasterDTO() {
		super();
	}

	public RepairReplaceDeviceMasterDTO(Long id, List<RepairReplaceDeviceDTO> repairReplaceDevices, String repairCode,
			ClientDTO client, StateDTO state, Integer totalDevice, Integer repairedDevice, Integer replacedDevice,
			Integer pendingDevice, String ewayBillNo, String ewayBillImage, String createdUserName, Long createdBy,
			Date createdAt, Boolean isActive, Long updatedBy, Date updatedAt, String updatedUserName, Double totalCost,
			Integer currentQuantity) {
		super();
		this.id = id;
		this.repairReplaceDevices = repairReplaceDevices;
		this.repairCode = repairCode;
		this.client = client;
		this.state = state;
		this.totalDevice = totalDevice;
		this.repairedDevice = repairedDevice;
		this.replacedDevice = replacedDevice;
		this.pendingDevice = pendingDevice;
		this.ewayBillNo = ewayBillNo;
		this.ewayBillImage = ewayBillImage;
		this.createdUserName = createdUserName;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.isActive = isActive;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.updatedUserName = updatedUserName;
		this.totalCost = totalCost;
		this.currentQuantity = currentQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<RepairReplaceDeviceDTO> getRepairReplaceDevices() {
		return repairReplaceDevices;
	}

	public void setRepairReplaceDevices(List<RepairReplaceDeviceDTO> repairReplaceDevices) {
		this.repairReplaceDevices = repairReplaceDevices;
	}

	public String getRepairCode() {
		return repairCode;
	}

	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public StateDTO getState() {
		return state;
	}

	public void setState(StateDTO state) {
		this.state = state;
	}

	public Integer getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(Integer totalDevice) {
		this.totalDevice = totalDevice;
	}

	public Integer getRepairedDevice() {
		return repairedDevice;
	}

	public void setRepairedDevice(Integer repairedDevice) {
		this.repairedDevice = repairedDevice;
	}

	public Integer getReplacedDevice() {
		return replacedDevice;
	}

	public void setReplacedDevice(Integer replacedDevice) {
		this.replacedDevice = replacedDevice;
	}

	public Integer getPendingDevice() {
		return pendingDevice;
	}

	public void setPendingDevice(Integer pendingDevice) {
		this.pendingDevice = pendingDevice;
	}

	public String getEwayBillNo() {
		return ewayBillNo;
	}

	public void setEwayBillNo(String ewayBillNo) {
		this.ewayBillNo = ewayBillNo;
	}

	public String getEwayBillImage() {
		return ewayBillImage;
	}

	public void setEwayBillImage(String ewayBillImage) {
		this.ewayBillImage = ewayBillImage;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedUserName() {
		return updatedUserName;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Integer getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Integer currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
	
}
