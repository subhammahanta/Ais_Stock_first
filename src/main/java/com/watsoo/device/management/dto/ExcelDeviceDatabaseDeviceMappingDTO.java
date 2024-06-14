package com.watsoo.device.management.dto;

import java.io.Serializable;
import java.util.Date;

import com.watsoo.device.management.model.State;

public class ExcelDeviceDatabaseDeviceMappingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String newImeiNo;

	private String newIccidNo;

	private String newUuidNo;

	private State newState;

	private Date newIssueDate;

	private Date newDeviceActivatedDate;

	private String newSim1Number;

	private String newSim2Number;

	private Date newSim1ActivationDate;

	private Date newSim2ActivationDate;

	private String newClientNames;

	private String oldImeiNo;

	private String oldIccidNo;

	private String oldUuidNo;

	private State oldState;

	private Date oldIssueDate;

	private Date oldDeviceActivatedDate;

	private String oldSim1Number;

	private String oldSim2Number;

	private Date oldSim1ActivationDate;

	private Date oldSim2ActivationDate;

	private String oldClientNames;

	public ExcelDeviceDatabaseDeviceMappingDTO() {
		super();
	}

	public ExcelDeviceDatabaseDeviceMappingDTO(Long id, String newImeiNo, String newIccidNo, String newUuidNo,
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

}
