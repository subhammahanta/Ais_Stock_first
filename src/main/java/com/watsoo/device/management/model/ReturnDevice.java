package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "return_device")
public class ReturnDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "imei")
	private String imei;

	@Column(name = "iccid")
	private String iccid;

	@Column(name = "issued_at")
	private Date issuedAt;

	@Column(name = "box_no")
	private String boxNo;

	@Column(name = "mfg_lot_no")
	private String mfgLotNo;

	@Column(name = "is_different_client_device")
	private Boolean isDifferentClientDevice;

	@Column(name = "issued_to")
	private String issuedTo;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "return_device_master_id")
	@JsonBackReference
	private ReturnDeviceMaster returnDeviceMaster;
	
	@Column(name = "invoice_no")
	private String invoiceNo;

	public ReturnDevice() {
		super();
	}

	public ReturnDevice(Long id, String imei, String iccid, Date issuedAt, String boxNo, String mfgLotNo,
			Boolean isDifferentClientDevice, String issuedTo, Long createdBy, Date createdAt, Boolean isActive,
			Long updatedBy, Date updatedAt, ReturnDeviceMaster returnDeviceMaster,String invoiceNo) {
		super();
		this.id = id;
		this.imei = imei;
		this.iccid = iccid;
		this.issuedAt = issuedAt;
		this.boxNo = boxNo;
		this.mfgLotNo = mfgLotNo;
		this.isDifferentClientDevice = isDifferentClientDevice;
		this.issuedTo = issuedTo;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.isActive = isActive;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.returnDeviceMaster = returnDeviceMaster;
		this.invoiceNo = invoiceNo;
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

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public String getMfgLotNo() {
		return mfgLotNo;
	}

	public void setMfgLotNo(String mfgLotNo) {
		this.mfgLotNo = mfgLotNo;
	}

	public Boolean getIsDifferentClientDevice() {
		return isDifferentClientDevice;
	}

	public void setIsDifferentClientDevice(Boolean isDifferentClientDevice) {
		this.isDifferentClientDevice = isDifferentClientDevice;
	}

	public String getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(String issuedTo) {
		this.issuedTo = issuedTo;
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

	public ReturnDeviceMaster getReturnDeviceMaster() {
		return returnDeviceMaster;
	}

	public void setReturnDeviceMaster(ReturnDeviceMaster returnDeviceMaster) {
		this.returnDeviceMaster = returnDeviceMaster;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

}
