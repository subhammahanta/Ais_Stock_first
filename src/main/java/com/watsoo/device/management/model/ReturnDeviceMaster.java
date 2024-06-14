package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "return_device_master")
public class ReturnDeviceMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy = "returnDeviceMaster")
	private List<ReturnDevice> returnDevices;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	@Column(name = "requested_quantity")
	private Integer requestedQuantity;

	@Column(name = "debit_note_no")
	private String debitNoteNo;

	@Column(name = "debit_note_image")
	private String debitNoteImage;

	@Column(name = "eway_bill_no")
	private String ewayBillNo;

	@Column(name = "eway_bill_image")
	private String ewayBillImage;

	@Column(name = "reason")
	private String reason;

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

	@Column(name = "req_code")
	private String reqCode;
	
	@Column(name = "current_quantity")
	private Integer currentQuantity;

	public ReturnDeviceMaster() {
		super();
	}

	public ReturnDeviceMaster(Long id, List<ReturnDevice> returnDevices, Client client, State state,
			Integer requestedQuantity, String debitNoteNo, String debitNoteImage, String ewayBillNo,
			String ewayBillImage, String reason, String createdUserName, Long createdBy, Date createdAt,
			Boolean isActive, Long updatedBy, Date updatedAt, String updatedUserName, String reqCode,
			Integer currentQuantity) {
		super();
		this.id = id;
		this.returnDevices = returnDevices;
		this.client = client;
		this.state = state;
		this.requestedQuantity = requestedQuantity;
		this.debitNoteNo = debitNoteNo;
		this.debitNoteImage = debitNoteImage;
		this.ewayBillNo = ewayBillNo;
		this.ewayBillImage = ewayBillImage;
		this.reason = reason;
		this.createdUserName = createdUserName;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.isActive = isActive;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.updatedUserName = updatedUserName;
		this.reqCode = reqCode;
		this.currentQuantity = currentQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ReturnDevice> getReturnDevices() {
		return returnDevices;
	}

	public void setReturnDevices(List<ReturnDevice> returnDevices) {
		this.returnDevices = returnDevices;
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

	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public String getDebitNoteNo() {
		return debitNoteNo;
	}

	public void setDebitNoteNo(String debitNoteNo) {
		this.debitNoteNo = debitNoteNo;
	}

	public String getDebitNoteImage() {
		return debitNoteImage;
	}

	public void setDebitNoteImage(String debitNoteImage) {
		this.debitNoteImage = debitNoteImage;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getReqCode() {
		return reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}

	public Integer getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Integer currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

}
