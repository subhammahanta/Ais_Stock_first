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
@Table(name = "excel_device_database_device_mapping")
public class ExcelDeviceDatabaseDeviceMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "new_imei_no")
	private String newImeiNo;

	@Column(name = "new_iccid_no")
	private String newIccidNo;

	@Column(name = "new_uuid_no")
	private String newUuidNo;

	@OneToOne
	@JoinColumn(name = "new_state_id")
	private State newState;

	@Column(name = "new_issue_date")
	private Date newIssueDate;

	@Column(name = "new_device_activation_date")
	private Date newDeviceActivatedDate;

	@Column(name = "new_sim1_number")
	private String newSim1Number;

	@Column(name = "new_sim2_number")
	private String newSim2Number;

	@Column(name = "new_sim1_activation_date")
	private Date newSim1ActivationDate;

	@Column(name = "new_sim2_activation_date")
	private Date newSim2ActivationDate;

	@Column(name = "new_client_name")
	private String newClientNames;

	@Column(name = "old_imei_no")
	private String oldImeiNo;

	@Column(name = "old_iccid_no")
	private String oldIccidNo;

	@Column(name = "old_uuid_no")
	private String oldUuidNo;

	@OneToOne
	@JoinColumn(name = "old_state_id")
	private State oldState;

	@Column(name = "old_issue_date")
	private Date oldIssueDate;

	@Column(name = "old_device_activation_date")
	private Date oldDeviceActivatedDate;

	@Column(name = "old_sim1_number")
	private String oldSim1Number;

	@Column(name = "old_sim2_number")
	private String oldSim2Number;

	@Column(name = "old_sim1_activation_date")
	private Date oldSim1ActivationDate;

	@Column(name = "old_sim2_activation_date")
	private Date oldSim2ActivationDate;

	@Column(name = "old_client_name")
	private String oldClientNames;

	@Column(name = "old_sim1_expire_date")
	private Date oldSim1ExpireDate;

	@Column(name = "old_sim2_expire_date")
	private Date oldSim2ExpireDate;

	@Column(name = "old_sim1_operator")
	private String oldSim1Operator;

	@Column(name = "old_sim2_operator")
	private String oldSim2Operator;

	@Column(name = "new_sim1_expire_date")
	private Date newSim1ExpireDate;

	@Column(name = "new_sim2_expire_date")
	private Date newSim2ExpireDate;

	@Column(name = "new_sim1_operator")
	private String newSim1Operator;

	@Column(name = "new_sim2_operator")
	private String newSim2Operator;

	public ExcelDeviceDatabaseDeviceMapping() {
		super();
	}

	public ExcelDeviceDatabaseDeviceMapping(Long id, String newImeiNo, String newIccidNo, String newUuidNo,
			State newState, Date newIssueDate, Date newDeviceActivatedDate, String newSim1Number, String newSim2Number,
			Date newSim1ActivationDate, Date newSim2ActivationDate, String newClientNames, String oldImeiNo,
			String oldIccidNo, String oldUuidNo, State oldState, Date oldIssueDate, Date oldDeviceActivatedDate,
			String oldSim1Number, String oldSim2Number, Date oldSim1ActivationDate, Date oldSim2ActivationDate,
			String oldClientNames) {
		super();
		this.id = id;
		this.newImeiNo = newImeiNo;
		this.newIccidNo = newIccidNo;
		this.newUuidNo = newUuidNo;
		this.newState = newState;
		this.newIssueDate = newIssueDate;
		this.newDeviceActivatedDate = newDeviceActivatedDate;
		this.newSim1Number = newSim1Number;
		this.newSim2Number = newSim2Number;
		this.newSim1ActivationDate = newSim1ActivationDate;
		this.newSim2ActivationDate = newSim2ActivationDate;
		this.newClientNames = newClientNames;
		this.oldImeiNo = oldImeiNo;
		this.oldIccidNo = oldIccidNo;
		this.oldUuidNo = oldUuidNo;
		this.oldState = oldState;
		this.oldIssueDate = oldIssueDate;
		this.oldDeviceActivatedDate = oldDeviceActivatedDate;
		this.oldSim1Number = oldSim1Number;
		this.oldSim2Number = oldSim2Number;
		this.oldSim1ActivationDate = oldSim1ActivationDate;
		this.oldSim2ActivationDate = oldSim2ActivationDate;
		this.oldClientNames = oldClientNames;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNewImeiNo() {
		return newImeiNo;
	}

	public void setNewImeiNo(String newImeiNo) {
		this.newImeiNo = newImeiNo;
	}

	public String getNewIccidNo() {
		return newIccidNo;
	}

	public void setNewIccidNo(String newIccidNo) {
		this.newIccidNo = newIccidNo;
	}

	public String getNewUuidNo() {
		return newUuidNo;
	}

	public void setNewUuidNo(String newUuidNo) {
		this.newUuidNo = newUuidNo;
	}

	public State getNewState() {
		return newState;
	}

	public void setNewState(State newState) {
		this.newState = newState;
	}

	public Date getNewIssueDate() {
		return newIssueDate;
	}

	public void setNewIssueDate(Date newIssueDate) {
		this.newIssueDate = newIssueDate;
	}

	public Date getNewDeviceActivatedDate() {
		return newDeviceActivatedDate;
	}

	public void setNewDeviceActivatedDate(Date newDeviceActivatedDate) {
		this.newDeviceActivatedDate = newDeviceActivatedDate;
	}

	public String getNewSim1Number() {
		return newSim1Number;
	}

	public void setNewSim1Number(String newSim1Number) {
		this.newSim1Number = newSim1Number;
	}

	public String getNewSim2Number() {
		return newSim2Number;
	}

	public void setNewSim2Number(String newSim2Number) {
		this.newSim2Number = newSim2Number;
	}

	public Date getNewSim1ActivationDate() {
		return newSim1ActivationDate;
	}

	public void setNewSim1ActivationDate(Date newSim1ActivationDate) {
		this.newSim1ActivationDate = newSim1ActivationDate;
	}

	public Date getNewSim2ActivationDate() {
		return newSim2ActivationDate;
	}

	public void setNewSim2ActivationDate(Date newSim2ActivationDate) {
		this.newSim2ActivationDate = newSim2ActivationDate;
	}

	public String getNewClientNames() {
		return newClientNames;
	}

	public void setNewClientNames(String newClientNames) {
		this.newClientNames = newClientNames;
	}

	public String getOldImeiNo() {
		return oldImeiNo;
	}

	public void setOldImeiNo(String oldImeiNo) {
		this.oldImeiNo = oldImeiNo;
	}

	public String getOldIccidNo() {
		return oldIccidNo;
	}

	public void setOldIccidNo(String oldIccidNo) {
		this.oldIccidNo = oldIccidNo;
	}

	public String getOldUuidNo() {
		return oldUuidNo;
	}

	public void setOldUuidNo(String oldUuidNo) {
		this.oldUuidNo = oldUuidNo;
	}

	public State getOldState() {
		return oldState;
	}

	public void setOldState(State oldState) {
		this.oldState = oldState;
	}

	public Date getOldIssueDate() {
		return oldIssueDate;
	}

	public void setOldIssueDate(Date oldIssueDate) {
		this.oldIssueDate = oldIssueDate;
	}

	public Date getOldDeviceActivatedDate() {
		return oldDeviceActivatedDate;
	}

	public void setOldDeviceActivatedDate(Date oldDeviceActivatedDate) {
		this.oldDeviceActivatedDate = oldDeviceActivatedDate;
	}

	public String getOldSim1Number() {
		return oldSim1Number;
	}

	public void setOldSim1Number(String oldSim1Number) {
		this.oldSim1Number = oldSim1Number;
	}

	public String getOldSim2Number() {
		return oldSim2Number;
	}

	public void setOldSim2Number(String oldSim2Number) {
		this.oldSim2Number = oldSim2Number;
	}

	public Date getOldSim1ActivationDate() {
		return oldSim1ActivationDate;
	}

	public void setOldSim1ActivationDate(Date oldSim1ActivationDate) {
		this.oldSim1ActivationDate = oldSim1ActivationDate;
	}

	public Date getOldSim2ActivationDate() {
		return oldSim2ActivationDate;
	}

	public void setOldSim2ActivationDate(Date oldSim2ActivationDate) {
		this.oldSim2ActivationDate = oldSim2ActivationDate;
	}

	public String getOldClientNames() {
		return oldClientNames;
	}

	public void setOldClientNames(String oldClientNames) {
		this.oldClientNames = oldClientNames;
	}

	public Date getOldSim1ExpireDate() {
		return oldSim1ExpireDate;
	}

	public void setOldSim1ExpireDate(Date oldSim1ExpireDate) {
		this.oldSim1ExpireDate = oldSim1ExpireDate;
	}

	public Date getOldSim2ExpireDate() {
		return oldSim2ExpireDate;
	}

	public void setOldSim2ExpireDate(Date oldSim2ExpireDate) {
		this.oldSim2ExpireDate = oldSim2ExpireDate;
	}

	public String getOldSim1Operator() {
		return oldSim1Operator;
	}

	public void setOldSim1Operator(String oldSim1Operator) {
		this.oldSim1Operator = oldSim1Operator;
	}

	public String getOldSim2Operator() {
		return oldSim2Operator;
	}

	public void setOldSim2Operator(String oldSim2Operator) {
		this.oldSim2Operator = oldSim2Operator;
	}

	public Date getNewSim1ExpireDate() {
		return newSim1ExpireDate;
	}

	public void setNewSim1ExpireDate(Date newSim1ExpireDate) {
		this.newSim1ExpireDate = newSim1ExpireDate;
	}

	public Date getNewSim2ExpireDate() {
		return newSim2ExpireDate;
	}

	public void setNewSim2ExpireDate(Date newSim2ExpireDate) {
		this.newSim2ExpireDate = newSim2ExpireDate;
	}

	public String getNewSim1Operator() {
		return newSim1Operator;
	}

	public void setNewSim1Operator(String newSim1Operator) {
		this.newSim1Operator = newSim1Operator;
	}

	public String getNewSim2Operator() {
		return newSim2Operator;
	}

	public void setNewSim2Operator(String newSim2Operator) {
		this.newSim2Operator = newSim2Operator;
	}

}
