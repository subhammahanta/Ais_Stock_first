package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watsoo.device.management.dto.RepairReplaceDeviceMasterDTO;

@Entity
@Table(name = "repair_replace_device_master")
public class RepairReplaceDeviceMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy = "repairReplaceDeviceMaster")
	@JsonIgnore
	private List<RepairReplaceDevice> repairReplaceDevices;

	@Column(name = "repair_code")
	private String repairCode;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	@Column(name = "total_device")
	private Integer totalDevice;

	@Column(name = "repaired_device")
	private Integer repairedDevice;

	@Column(name = "replaced_device")
	private Integer replacedDevice;

	@Column(name = "pending_device")
	private Integer pendingDevice;

	@Column(name = "eway_bill_no")
	private String ewayBillNo;

	@Column(name = "eway_bill_image")
	private String ewayBillImage;

	@Column(name = "created_user_name")
	private String createdUserName;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_user_name")
	private String updatedUserName;

	@Column(name = "total_cost")
	private Double totalCost;

	@Column(name = "current_quantity")
	private Integer currentQuantity;

	public RepairReplaceDeviceMaster() {
		super();
	}

	public RepairReplaceDeviceMaster(Long id, List<RepairReplaceDevice> repairReplaceDevices, String repairCode,
			Client client, State state, Integer totalDevice, Integer repairedDevice, Integer replacedDevice,
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

	public List<RepairReplaceDevice> getRepairReplaceDevices() {
		return repairReplaceDevices;
	}

	public void setRepairReplaceDevices(List<RepairReplaceDevice> repairReplaceDevices) {
		this.repairReplaceDevices = repairReplaceDevices;
	}

	public String getRepairCode() {
		return repairCode;
	}

	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
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

	public RepairReplaceDeviceMasterDTO convertToDTO() {

		return new RepairReplaceDeviceMasterDTO(this.getId(),
				repairReplaceDevices.stream().map(RepairReplaceDevice::convertToDTOV2).collect(Collectors.toList()),
				this.getRepairCode(), client.convertEntityToDto(client), state.convertEntityToDto(state),
				this.getTotalDevice(), this.getRepairedDevice(), this.getReplacedDevice(), this.getPendingDevice(),
				this.getEwayBillNo(), this.getEwayBillImage(), this.getCreatedUserName(), this.getCreatedBy(),
				this.getCreatedAt(), this.getIsActive(), this.getUpdatedBy(), this.getUpdatedAt(),
				this.getUpdatedUserName(), this.getTotalCost(), this.getCurrentQuantity());
	}

}
