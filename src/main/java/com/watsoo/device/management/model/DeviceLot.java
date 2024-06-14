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

@Entity
@Table(name = "device_lot")
public class DeviceLot implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_quantity")
	private Integer orderQuantity;

	@Column(name = "tested_quantity")
	private Integer testedQuantity;

	@Column(name = "pending_quantity")
	private Integer pendingQuantity;

//	@Column(name = "client_id")
//	private Long clientId;

	@Column(name = "is_completed")
	private Boolean isCompleted;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

//	@Column(name = "state_id")
//	private Long stateId;
//	
//	@Column(name = "model_id")
//	private Long modelId;
//	
//	@Column(name = "client_name")
//	private String clientNames;
//	
//	@Column(name = "state_name")
//	private String stateName;
	
	@OneToOne
	@JoinColumn(name = "operators_id")
	private Operators operators;
	
	@OneToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;
	
	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;
	
	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@OneToOne
	@JoinColumn(name = "model_id")
	private DeviceModel modelId;
	
	@Column(name = "system_lot_id")
	private String systemLotId;
	
//	@OneToOne
//	@JoinColumn(name = "vts_tac_id")
//	private VtsTac tac;
	
	@Column(name = "external_voltage")
	private Double externalVoltage;
	
	@OneToOne
	@JoinColumn(name ="ems_master_id" )
	private EmsMaster emsMaster;

	@Column(name = "mfg_lot_id")
	private String mfgLotId;
	
	public DeviceLot() {
		super();
	}

//	public DeviceLot(Long id, Integer orderQuantity, Integer testedQuantity, Integer pendingQuantity, Long clientId,
//			Boolean isCompleted, Boolean isActive, Date createdAt, Long createdBy, Long stateId, Long modelId) {
//		super();
//		this.id = id;
//		this.orderQuantity = orderQuantity;
//		this.testedQuantity = testedQuantity;
//		this.pendingQuantity = pendingQuantity;
//		this.clientId = clientId;
//		this.isCompleted = isCompleted;
//		this.isActive = isActive;
//		this.createdAt = createdAt;
//		this.createdBy = createdBy;
//		this.stateId = stateId;
//		this.modelId = modelId;
//	}

	public Long getId() {
		return id;
	}

	public DeviceLot(Long id, Integer orderQuantity, Integer testedQuantity, Integer pendingQuantity, Boolean isCompleted,
		Boolean isActive, Date createdAt, Long createdBy, DeviceModel modelId, Operators operators, Provider provider,
		State state, Client client) {
	super();
	this.id = id;
	this.orderQuantity = orderQuantity;
	this.testedQuantity = testedQuantity;
	this.pendingQuantity = pendingQuantity;
	this.isCompleted = isCompleted;
	this.isActive = isActive;
	this.createdAt = createdAt;
	this.createdBy = createdBy;
	this.modelId = modelId;
	this.operators = operators;
	this.provider = provider;
	this.state = state;
	this.client = client;
}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Integer getTestedQuantity() {
		return testedQuantity;
	}

	public void setTestedQuantity(Integer testedQuantity) {
		this.testedQuantity = testedQuantity;
	}

	public Integer getPendingQuantity() {
		return pendingQuantity;
	}

	public void setPendingQuantity(Integer pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}

//	public Long getClientId() {
//		return clientId;
//	}
//
//	public void setClientId(Long clientId) {
//		this.clientId = clientId;
//	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

//	public Long getStateId() {
//		return stateId;
//	}
//
//	public void setStateId(Long stateId) {
//		this.stateId = stateId;
//	}

	public DeviceModel getModelId() {
		return modelId;
	}

	public void setModelId(DeviceModel modelId) {
		this.modelId = modelId;
	}

	public Operators getOperators() {
		return operators;
	}

	public void setOperators(Operators operators) {
		this.operators = operators;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getSystemLotId() {
		return systemLotId;
	}

	public void setSystemLotId(String systemLotId) {
		this.systemLotId = systemLotId;
	}

//	public VtsTac getTac() {
//		return tac;
//	}
//
//	public void setTac(VtsTac tac) {
//		this.tac = tac;
//	}

	public Double getExternalVoltage() {
		return externalVoltage;
	}

	public void setExternalVoltage(Double externalVoltage) {
		this.externalVoltage = externalVoltage;
	}

	public EmsMaster getEmsMaster() {
		return emsMaster;
	}

	public void setEmsMaster(EmsMaster emsMaster) {
		this.emsMaster = emsMaster;
	}

	public String getMfgLotId() {
		return mfgLotId;
	}

	public void setMfgLotId(String mfgLotId) {
		this.mfgLotId = mfgLotId;
	}

}
