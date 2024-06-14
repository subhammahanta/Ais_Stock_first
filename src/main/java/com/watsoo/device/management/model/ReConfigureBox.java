package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.watsoo.device.management.dto.ProviderDTO;
import com.watsoo.device.management.dto.ReConfigureBoxDTO;
import com.watsoo.device.management.dto.StateDTO;

@Entity
@Table(name = "re_configure_box")
public class ReConfigureBox implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "box_Id")
	private Long boxId;

	@Column(name = "box_no")
	private String boxNo;

	@OneToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@Column(name = "created_at")
	private Date createdAt;

	@OneToOne
	@JoinColumn(name = "updated_by")
	private User updatedBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "total_device")
	private Integer totalDevice;

	@Column(name = "total_configure_device")
	private Integer totalConfigureDevice;

	@Column(name = "total_unbox_device")
	private Integer totalUnboxDevice;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "config_command")
	private String configCommand;

	@Column(name = "is_completed")
	private Boolean isCompleted;

	@Column(name = "re_config_box_code")
	private String reConfigBoxCode;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State stateId;

	@Column(name = "unsettled_box_code")
	private String unsettledBoxCode;
	
	@OneToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	public ReConfigureBox() {
		super();
	}

	public ReConfigureBox(Long id, Long boxId, String boxNo, User createdBy, Date createdAt, User updatedBy,
			Date updatedAt, Integer totalDevice, Integer totalConfigureDevice, Integer totalUnboxDevice,
			Boolean isActive, String configCommand, Boolean isCompleted, String reConfigBoxCode,
			String unsettledBoxCode) {
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
		this.unsettledBoxCode = unsettledBoxCode;
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

	public State getStateId() {
		return stateId;
	}

	public void setStateId(State stateId) {
		this.stateId = stateId;
	}

	public String getUnsettledBoxCode() {
		return unsettledBoxCode;
	}

	public void setUnsettledBoxCode(String unsettledBoxCode) {
		this.unsettledBoxCode = unsettledBoxCode;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public static ReConfigureBoxDTO convertToDTO(ReConfigureBox reConfigureBox) {
		ReConfigureBoxDTO reConfigureBoxDTO = new ReConfigureBoxDTO();
		reConfigureBoxDTO.setId(reConfigureBox.getId());
		reConfigureBoxDTO.setBoxId(reConfigureBox.getBoxId());
		reConfigureBoxDTO.setBoxNo(reConfigureBox.getBoxNo());
		reConfigureBoxDTO.setCreatedBy(reConfigureBox.getCreatedBy());
		reConfigureBoxDTO.setCreatedAt(reConfigureBox.getCreatedAt());
		reConfigureBoxDTO.setUpdatedBy(reConfigureBox.getUpdatedBy());
		reConfigureBoxDTO.setUpdatedAt(reConfigureBox.getUpdatedAt());
		reConfigureBoxDTO.setTotalDevice(reConfigureBox.getTotalDevice());
		reConfigureBoxDTO.setTotalConfigureDevice(reConfigureBox.getTotalConfigureDevice());
		reConfigureBoxDTO.setTotalUnboxDevice(reConfigureBox.getTotalUnboxDevice());
		reConfigureBoxDTO.setIsActive(reConfigureBox.getIsActive());
		reConfigureBoxDTO.setConfigCommand(reConfigureBox.getConfigCommand());
		reConfigureBoxDTO.setIsCompleted(reConfigureBox.getIsCompleted());
		reConfigureBoxDTO.setReConfigBoxCode(reConfigureBox.getReConfigBoxCode());
		StateDTO stateDTO = new StateDTO();
		stateDTO.setId(reConfigureBox.getStateId().getId());
		stateDTO.setName(reConfigureBox.getStateId().getName());
		reConfigureBoxDTO.setStateDTO(stateDTO);
		reConfigureBoxDTO.setUnsettledBoxCode(reConfigureBox.getUnsettledBoxCode());
		return reConfigureBoxDTO;
	}
	
	public static ReConfigureBoxDTO convertToDTOV2(ReConfigureBox reConfigureBox) {
		ReConfigureBoxDTO reConfigureBoxDTO = new ReConfigureBoxDTO();
		reConfigureBoxDTO.setId(reConfigureBox.getId());
		reConfigureBoxDTO.setBoxId(reConfigureBox.getBoxId());
		reConfigureBoxDTO.setBoxNo(reConfigureBox.getBoxNo());
		reConfigureBoxDTO.setCreatedBy(reConfigureBox.getCreatedBy());
		reConfigureBoxDTO.setCreatedAt(reConfigureBox.getCreatedAt());
		reConfigureBoxDTO.setUpdatedBy(reConfigureBox.getUpdatedBy());
		reConfigureBoxDTO.setUpdatedAt(reConfigureBox.getUpdatedAt());
		reConfigureBoxDTO.setTotalDevice(reConfigureBox.getTotalDevice());
		reConfigureBoxDTO.setTotalConfigureDevice(reConfigureBox.getTotalConfigureDevice());
		reConfigureBoxDTO.setTotalUnboxDevice(reConfigureBox.getTotalUnboxDevice());
		reConfigureBoxDTO.setIsActive(reConfigureBox.getIsActive());
		reConfigureBoxDTO.setConfigCommand(reConfigureBox.getConfigCommand());
		reConfigureBoxDTO.setIsCompleted(reConfigureBox.getIsCompleted());
		reConfigureBoxDTO.setReConfigBoxCode(reConfigureBox.getReConfigBoxCode());
		StateDTO stateDTO = new StateDTO();
		stateDTO.setId(reConfigureBox.getStateId().getId());
		stateDTO.setName(reConfigureBox.getStateId().getName());
		reConfigureBoxDTO.setStateDTO(stateDTO);
		reConfigureBoxDTO.setUnsettledBoxCode(reConfigureBox.getUnsettledBoxCode());
		ProviderDTO providerDTO = new ProviderDTO();
		if (reConfigureBox.getProvider() != null) {
			providerDTO.setId(reConfigureBox.getProvider().getId());
			providerDTO.setName(reConfigureBox.getProvider().getName());
		}
		reConfigureBoxDTO.setProvider(providerDTO);		
		return reConfigureBoxDTO;
	}
}
