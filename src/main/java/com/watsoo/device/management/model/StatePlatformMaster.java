package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.watsoo.device.management.dto.StatePlatformMasterDTO;
import com.watsoo.device.management.dto.SubMenuMasterDto;
import com.watsoo.device.management.dto.SubscriptionMasterDTO;
import com.watsoo.device.management.dto.SubscriptionTypeDTO;

@Entity
@Table(name = "state_platform_master")
public class StatePlatformMaster {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	private Double plateformCharges;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_by_name")
	private String createdByName;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_by_name")
	private String updatedByName;

	@Column(name = "is_active")
	private boolean isActive;

	public StatePlatformMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatePlatformMaster(Integer id, State state, Double plateformCharges, Date createdAt, Long createdBy,
			String createdByName, Date updatedAt, Long updatedBy, String updatedByName, boolean isActive) {
		super();
		this.id = id;
		this.state = state;
		this.plateformCharges = plateformCharges;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.createdByName = createdByName;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.updatedByName = updatedByName;
		this.isActive = isActive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Double getPlateformCharges() {
		return plateformCharges;
	}

	public void setPlateformCharges(Double plateformCharges) {
		this.plateformCharges = plateformCharges;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}




	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public StatePlatformMasterDTO convertEntityToDTO(StatePlatformMaster statePlatformMaster) {
		// TODO Auto-generated method stub
		return new StatePlatformMasterDTO(statePlatformMaster.getId(), statePlatformMaster.getState().getId(),
				statePlatformMaster.getPlateformCharges(), statePlatformMaster.getCreatedByName(), statePlatformMaster.getIsActive());
	}

}
