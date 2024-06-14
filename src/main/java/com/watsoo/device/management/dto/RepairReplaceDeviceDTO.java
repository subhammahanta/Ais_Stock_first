package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.watsoo.device.management.enums.Status;

public class RepairReplaceDeviceDTO {

	private Long id;

	private Set<RepairDeviceChargesDTO> repairDeviceCharges;

	private RepairReplaceDeviceMasterDTO repairReplaceDeviceMaster;

	private String imei;

	private String iccid;

	private Date issuedAt;

	private Date warrantyDate;

	private String boxNo;

	private String mfgLotNo;

	private Boolean isDifferentClientDevice;

	private String issuedTo;

	private Long createdBy;

	private Date createdAt;

	private Boolean isActive;

	private Long updatedBy;

	private Date updatedAt;

	private String openBoxImageBeforeTesting;

	private String closeBoxImageBeforeTesting;

	private String openBoxImageAfterTesting;

	private String closeBoxImageAfterTesting;

	private String rejectReason;

	@Enumerated(EnumType.STRING)
	private Status status;

	private Double repairCost;

	private String repairedByUser;

	private Long repairedBy;

	private Date repairedAt;

	private String rejectedByUser;

	private Long rejectedBy;

	private Date rejectedAt;

	private String replacedByImei;

	public RepairReplaceDeviceDTO() {
		super();
	}

	public RepairReplaceDeviceDTO(Long id, Set<RepairDeviceChargesDTO> repairDeviceCharges,
			RepairReplaceDeviceMasterDTO repairReplaceDeviceMaster, String imei, String iccid, Date issuedAt,
			Date warrantyDate, String boxNo, String mfgLotNo, Boolean isDifferentClientDevice, String issuedTo,
			Long createdBy, Date createdAt, Boolean isActive, Long updatedBy, Date updatedAt,
			String openBoxImageBeforeTesting, String closeBoxImageBeforeTesting, String openBoxImageAfterTesting,
			String closeBoxImageAfterTesting, String rejectReason, Status status, Double repairCost,
			String repairedByUser, Long repairedBy, Date repairedAt, String rejectedByUser, Long rejectedBy,
			Date rejectedAt, String replacedByImei) {
		super();
		this.id = id;
		this.repairDeviceCharges = repairDeviceCharges;
		this.repairReplaceDeviceMaster = repairReplaceDeviceMaster;
		this.imei = imei;
		this.iccid = iccid;
		this.issuedAt = issuedAt;
		this.warrantyDate = warrantyDate;
		this.boxNo = boxNo;
		this.mfgLotNo = mfgLotNo;
		this.isDifferentClientDevice = isDifferentClientDevice;
		this.issuedTo = issuedTo;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.isActive = isActive;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.openBoxImageBeforeTesting = openBoxImageBeforeTesting;
		this.closeBoxImageBeforeTesting = closeBoxImageBeforeTesting;
		this.openBoxImageAfterTesting = openBoxImageAfterTesting;
		this.closeBoxImageAfterTesting = closeBoxImageAfterTesting;
		this.rejectReason = rejectReason;
		this.status = status;
		this.repairCost = repairCost;
		this.repairedByUser = repairedByUser;
		this.repairedBy = repairedBy;
		this.repairedAt = repairedAt;
		this.rejectedByUser = rejectedByUser;
		this.rejectedBy = rejectedBy;
		this.rejectedAt = rejectedAt;
		this.replacedByImei = replacedByImei;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<RepairDeviceChargesDTO> getRepairDeviceCharges() {
		return repairDeviceCharges;
	}

	public void setRepairDeviceCharges(Set<RepairDeviceChargesDTO> repairDeviceCharges) {
		this.repairDeviceCharges = repairDeviceCharges;
	}

	public RepairReplaceDeviceMasterDTO getRepairReplaceDeviceMaster() {
		return repairReplaceDeviceMaster;
	}

	public void setRepairReplaceDeviceMaster(RepairReplaceDeviceMasterDTO repairReplaceDeviceMaster) {
		this.repairReplaceDeviceMaster = repairReplaceDeviceMaster;
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

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
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

	public String getOpenBoxImageBeforeTesting() {
		return openBoxImageBeforeTesting;
	}

	public void setOpenBoxImageBeforeTesting(String openBoxImageBeforeTesting) {
		this.openBoxImageBeforeTesting = openBoxImageBeforeTesting;
	}

	public String getCloseBoxImageBeforeTesting() {
		return closeBoxImageBeforeTesting;
	}

	public void setCloseBoxImageBeforeTesting(String closeBoxImageBeforeTesting) {
		this.closeBoxImageBeforeTesting = closeBoxImageBeforeTesting;
	}

	public String getOpenBoxImageAfterTesting() {
		return openBoxImageAfterTesting;
	}

	public void setOpenBoxImageAfterTesting(String openBoxImageAfterTesting) {
		this.openBoxImageAfterTesting = openBoxImageAfterTesting;
	}

	public String getCloseBoxImageAfterTesting() {
		return closeBoxImageAfterTesting;
	}

	public void setCloseBoxImageAfterTesting(String closeBoxImageAfterTesting) {
		this.closeBoxImageAfterTesting = closeBoxImageAfterTesting;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getRepairCost() {
		return repairCost;
	}

	public void setRepairCost(Double repairCost) {
		this.repairCost = repairCost;
	}

	public String getRepairedByUser() {
		return repairedByUser;
	}

	public void setRepairedByUser(String repairedByUser) {
		this.repairedByUser = repairedByUser;
	}

	public Long getRepairedBy() {
		return repairedBy;
	}

	public void setRepairedBy(Long repairedBy) {
		this.repairedBy = repairedBy;
	}

	public Date getRepairedAt() {
		return repairedAt;
	}

	public void setRepairedAt(Date repairedAt) {
		this.repairedAt = repairedAt;
	}

	public String getRejectedByUser() {
		return rejectedByUser;
	}

	public void setRejectedByUser(String rejectedByUser) {
		this.rejectedByUser = rejectedByUser;
	}

	public Long getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(Long rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public Date getRejectedAt() {
		return rejectedAt;
	}

	public void setRejectedAt(Date rejectedAt) {
		this.rejectedAt = rejectedAt;
	}

	public String getReplacedByImei() {
		return replacedByImei;
	}

	public void setReplacedByImei(String replacedByImei) {
		this.replacedByImei = replacedByImei;
	}

}
