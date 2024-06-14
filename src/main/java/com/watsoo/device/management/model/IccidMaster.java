package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iccid_master")
public class IccidMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "iccid_no")
	private String iccidNo;

	@Column(name = "provider")
	private String provider;

	@Column(name = "sim1_operator")
	private String sim1Operator;

	@Column(name = "sim2_operator")
	private String sim2Operator;

	@Column(name = "sim1")
	private String sim1;

	@Column(name = "sim2")
	private String sim2;

	@Column(name = "sim_activation_date")
	private Date simActivationDate;

	@Column(name = "sim_expiry_date")
	private Date simExpiryDate;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "iccid_operator")
	private String iccidOperator;

	public IccidMaster() {
		super();
	}
	
	public IccidMaster(Long id, String iccidNo, String provider, String sim1Operator, String sim2Operator, String sim1,
			String sim2, Date simActivationDate, Date simExpiryDate, Date createdAt, Long createdBy) {
		super();
		this.id = id;
		this.iccidNo = iccidNo;
		this.provider = provider;
		this.sim1Operator = sim1Operator;
		this.sim2Operator = sim2Operator;
		this.sim1 = sim1;
		this.sim2 = sim2;
		this.simActivationDate = simActivationDate;
		this.simExpiryDate = simExpiryDate;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIccidNo() {
		return iccidNo;
	}

	public void setIccidNo(String iccidNo) {
		this.iccidNo = iccidNo;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSim1Operator() {
		return sim1Operator;
	}

	public void setSim1Operator(String sim1Operator) {
		this.sim1Operator = sim1Operator;
	}

	public String getSim2Operator() {
		return sim2Operator;
	}

	public void setSim2Operator(String sim2Operator) {
		this.sim2Operator = sim2Operator;
	}

	public String getSim1() {
		return sim1;
	}

	public void setSim1(String sim1) {
		this.sim1 = sim1;
	}

	public String getSim2() {
		return sim2;
	}

	public void setSim2(String sim2) {
		this.sim2 = sim2;
	}

	public Date getSimActivationDate() {
		return simActivationDate;
	}

	public void setSimActivationDate(Date simActivationDate) {
		this.simActivationDate = simActivationDate;
	}

	public Date getSimExpiryDate() {
		return simExpiryDate;
	}

	public void setSimExpiryDate(Date simExpiryDate) {
		this.simExpiryDate = simExpiryDate;
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

	public String getIccidOperator() {
		return iccidOperator;
	}

	public void setIccidOperator(String iccidOperator) {
		this.iccidOperator = iccidOperator;
	}
	
}
