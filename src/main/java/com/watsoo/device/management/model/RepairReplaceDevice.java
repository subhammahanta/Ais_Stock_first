package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.watsoo.device.management.dto.RepairReplaceDeviceDTO;
import com.watsoo.device.management.enums.Status;

@Entity
@Table(name = "repair_replace_device")
public class RepairReplaceDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy = "repairReplaceDevice")
	private Set<RepairDeviceCharges> repairDeviceCharges;

	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "repair_replace_device_master_id")
	@JsonBackReference
	private RepairReplaceDeviceMaster repairReplaceDeviceMaster;

	@Column(name = "imei")
	private String imei;

	@Column(name = "iccid")
	private String iccid;

	@Column(name = "issued_at")
	private Date issuedAt;

	@Column(name = "warranty_date")
	private Date warrantyDate;

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

	@Column(name = "open_box_image_before_testing")
	private String openBoxImageBeforeTesting;

	@Column(name = "close_box_image_before_testing")
	private String closeBoxImageBeforeTesting;

	@Column(name = "open_box_image_after_testing")
	private String openBoxImageAfterTesting;

	@Column(name = "close_box_image_after_testing")
	private String closeBoxImageAfterTesting;

	@Column(name = "reject_reason")
	private String rejectReason;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "repair_cost")
	private Double repairCost;

	@Column(name = "repaired_by_user")
	private String repairedByUser;

	@Column(name = "repaired_by")
	private Long repairedBy;

	@Column(name = "repaired_at")
	private Date repairedAt;

	@Column(name = "rejected_by_user")
	private String rejectedByUser;

	@Column(name = "rejected_by")
	private Long rejectedBy;

	@Column(name = "rejected_at")
	private Date rejectedAt;

	@Column(name = "replaced_by_imei")
	private String replacedByImei;

	public RepairReplaceDevice() {
		super();
	}

	public RepairReplaceDevice(Long id, Set<RepairDeviceCharges> repairDeviceCharges,
			RepairReplaceDeviceMaster repairReplaceDeviceMaster, String imei, String iccid, Date issuedAt,
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

	public Set<RepairDeviceCharges> getRepairDeviceCharges() {
		return repairDeviceCharges;
	}

	public void setRepairDeviceCharges(Set<RepairDeviceCharges> repairDeviceCharges) {
		this.repairDeviceCharges = repairDeviceCharges;
	}

	public RepairReplaceDeviceMaster getRepairReplaceDeviceMaster() {
		return repairReplaceDeviceMaster;
	}

	public void setRepairReplaceDeviceMaster(RepairReplaceDeviceMaster repairReplaceDeviceMaster) {
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

	public RepairReplaceDeviceDTO convertToDTO() {
		return new RepairReplaceDeviceDTO(this.getId(),
				repairDeviceCharges.stream().map(RepairDeviceCharges::convertToDTO).collect(Collectors.toSet()), null,
				this.getImei(), this.getIccid(), this.getIssuedAt(), this.getWarrantyDate(), this.getBoxNo(),
				this.getMfgLotNo(), this.getIsDifferentClientDevice(), this.getIssuedTo(), this.getCreatedBy(),
				this.getCreatedAt(), this.getIsActive(), this.getUpdatedBy(), this.getUpdatedAt(),
				this.getOpenBoxImageBeforeTesting(), this.getCloseBoxImageBeforeTesting(),
				this.getOpenBoxImageAfterTesting(), this.getCloseBoxImageAfterTesting(), this.getRejectReason(),
				this.getStatus(), this.getRepairCost(), this.getRepairedByUser(), this.getRepairedBy(),
				this.getRepairedAt(), this.getRejectedByUser(), this.getRejectedBy(), this.getRejectedAt(),
				this.getReplacedByImei());
	}

	public RepairReplaceDeviceDTO convertToDTOV2() {
		return new RepairReplaceDeviceDTO(this.getId(), null, null, this.getImei(), this.getIccid(), this.getIssuedAt(),
				this.getWarrantyDate(), this.getBoxNo(), this.getMfgLotNo(), this.getIsDifferentClientDevice(),
				this.getIssuedTo(), this.getCreatedBy(), this.getCreatedAt(), this.getIsActive(), this.getUpdatedBy(),
				this.getUpdatedAt(), this.getOpenBoxImageBeforeTesting(), this.getCloseBoxImageBeforeTesting(),
				this.getOpenBoxImageAfterTesting(), this.getCloseBoxImageAfterTesting(), this.getRejectReason(),
				this.getStatus(), this.getRepairCost(), this.getRepairedByUser(), this.getRepairedBy(),
				this.getRepairedAt(), this.getRejectedByUser(), this.getRejectedBy(), this.getRejectedAt(),
				this.getReplacedByImei());
	}

}
