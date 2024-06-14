package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.watsoo.device.management.enums.OperationEnum;
import com.watsoo.device.management.enums.StatusEnum;

@Entity
@Table(name = "return_replace_repair_device")
public class ReturnReplaceRepair implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "imei")
	private String imei;

	@Enumerated(EnumType.STRING)
	@Column(name = "operation")
	private OperationEnum operation;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "replace_by_imei")
	private String replaceByImei;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusEnum status;
	
	@Column(name = "remark")
	private String remark;

	public ReturnReplaceRepair() {
		super();
	}

	public ReturnReplaceRepair(Long id, String imei, OperationEnum operation, String clientName, Long createdBy,
			Date createdAt, String replaceByImei, StatusEnum status) {
		super();
		this.id = id;
		this.imei = imei;
		this.operation = operation;
		this.clientName = clientName;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.replaceByImei = replaceByImei;
		this.status = status;
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

	public OperationEnum getOperation() {
		return operation;
	}

	public void setOperation(OperationEnum operation) {
		this.operation = operation;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public String getReplaceByImei() {
		return replaceByImei;
	}

	public void setReplaceByImei(String replaceByImei) {
		this.replaceByImei = replaceByImei;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
