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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.watsoo.device.management.enums.StatusMaster;

@Entity
@Table(name = "device")
public class DeviceLiteV3 implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "iccid_no")
	private String iccidNo;

	@Column(name = "uuid_no")
	private String uuidNo;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "software_version")
	private String softwareVersion;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "detail")
	private String detail;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	@Enumerated(EnumType.STRING)
	private StatusMaster status;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_number")
	private String sim1Number;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_number")
	private String sim2Number;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_operator")
	private String sim1Operator;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_operator")
	private String sim2Operator;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_activation_date")
	private Date sim1ActivationDate;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_expiry_date")
	private Date sim1ExpiryDate;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_activation_date")
	private Date sim2ActivationDate;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_expiry_date")
	private Date sim2ExpiryDate;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_provider")
	private String sim1Provider;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_provider")
	private String sim2Provider;
	
	@Column(name = "mfg_lot_id")
	private String mfgLotId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getIccidNo() {
		return iccidNo;
	}

	public void setIccidNo(String iccidNo) {
		this.iccidNo = iccidNo;
	}

	public String getUuidNo() {
		return uuidNo;
	}

	public void setUuidNo(String uuidNo) {
		this.uuidNo = uuidNo;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public StatusMaster getStatus() {
		return status;
	}

	public void setStatus(StatusMaster status) {
		this.status = status;
	}

	public String getSim1Number() {
		return sim1Number;
	}

	public void setSim1Number(String sim1Number) {
		this.sim1Number = sim1Number;
	}

	public String getSim2Number() {
		return sim2Number;
	}

	public void setSim2Number(String sim2Number) {
		this.sim2Number = sim2Number;
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

	public Date getSim1ActivationDate() {
		return sim1ActivationDate;
	}

	public void setSim1ActivationDate(Date sim1ActivationDate) {
		this.sim1ActivationDate = sim1ActivationDate;
	}

	public Date getSim1ExpiryDate() {
		return sim1ExpiryDate;
	}

	public void setSim1ExpiryDate(Date sim1ExpiryDate) {
		this.sim1ExpiryDate = sim1ExpiryDate;
	}

	public Date getSim2ActivationDate() {
		return sim2ActivationDate;
	}

	public void setSim2ActivationDate(Date sim2ActivationDate) {
		this.sim2ActivationDate = sim2ActivationDate;
	}

	public Date getSim2ExpiryDate() {
		return sim2ExpiryDate;
	}

	public void setSim2ExpiryDate(Date sim2ExpiryDate) {
		this.sim2ExpiryDate = sim2ExpiryDate;
	}

	public String getSim1Provider() {
		return sim1Provider;
	}

	public void setSim1Provider(String sim1Provider) {
		this.sim1Provider = sim1Provider;
	}

	public String getSim2Provider() {
		return sim2Provider;
	}

	public void setSim2Provider(String sim2Provider) {
		this.sim2Provider = sim2Provider;
	}

	public String getMfgLotId() {
		return mfgLotId;
	}

	public void setMfgLotId(String mfgLotId) {
		this.mfgLotId = mfgLotId;
	}

	public DeviceLiteV3(Long id, String imeiNo, String iccidNo, String uuidNo, String softwareVersion, String detail,
			State state, StatusMaster status, String sim1Number, String sim2Number, String sim1Operator,
			String sim2Operator, Date sim1ActivationDate, Date sim1ExpiryDate, Date sim2ActivationDate,
			Date sim2ExpiryDate, String sim1Provider, String sim2Provider, String mfgLotId) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.iccidNo = iccidNo;
		this.uuidNo = uuidNo;
		this.softwareVersion = softwareVersion;
		this.detail = detail;
		this.state = state;
		this.status = status;
		this.sim1Number = sim1Number;
		this.sim2Number = sim2Number;
		this.sim1Operator = sim1Operator;
		this.sim2Operator = sim2Operator;
		this.sim1ActivationDate = sim1ActivationDate;
		this.sim1ExpiryDate = sim1ExpiryDate;
		this.sim2ActivationDate = sim2ActivationDate;
		this.sim2ExpiryDate = sim2ExpiryDate;
		this.sim1Provider = sim1Provider;
		this.sim2Provider = sim2Provider;
		this.mfgLotId = mfgLotId;
	}

	public DeviceLiteV3() {
		super();
	}
	
}
