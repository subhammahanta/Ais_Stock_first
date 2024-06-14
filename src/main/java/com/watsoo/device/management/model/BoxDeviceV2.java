package com.watsoo.device.management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "box_device")
public class BoxDeviceV2 implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "box_id")
	private Box box;

	@OneToOne
	@JoinColumn(name = "device_id")
	private DeviceLite device;
	@Column(name = "is_present")
	private Boolean isPresent;
	@Column(name = "is_active")
	private Boolean isActive;
	@Column(name = "is_issued")
	private Boolean isIssued;
	@OneToOne
	@JoinColumn(name = "entry_transaction_id")
	private BoxTransaction entryTransactionId;

	@OneToOne
	@JoinColumn(name = "removal_transaction_id")
	private BoxTransaction removalTransactionId;

	public BoxDeviceV2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BoxDeviceV2(Long id, Box box, DeviceLite device, Boolean isPresent, Boolean isActive,
			BoxTransaction entryTransactionId, BoxTransaction removalTransactionId) {
		super();
		this.id = id;
		this.box = box;
		this.device = device;
		this.isPresent = isPresent;
		this.isActive = isActive;
		this.entryTransactionId = entryTransactionId;
		this.removalTransactionId = removalTransactionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public DeviceLite getDevice() {
		return device;
	}

	public void setDevice(DeviceLite device) {
		this.device = device;
	}

	public Boolean getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public BoxTransaction getEntryTransactionId() {
		return entryTransactionId;
	}

	public void setEntryTransactionId(BoxTransaction entryTransactionId) {
		this.entryTransactionId = entryTransactionId;
	}

	public BoxTransaction getRemovalTransactionId() {
		return removalTransactionId;
	}

	public void setRemovalTransactionId(BoxTransaction removalTransactionId) {
		this.removalTransactionId = removalTransactionId;
	}

	public Boolean getIsIssued() {
		return isIssued;
	}

	public void setIsIssued(Boolean isIssued) {
		this.isIssued = isIssued;
	}
}
