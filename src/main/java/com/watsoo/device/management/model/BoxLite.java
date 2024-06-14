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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.util.DateUtil;

@Entity
@Table(name = "box")
public class BoxLite implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "box_no")
	private String boxNo;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "updated_by")
	private Long updatedBy;
	@Column(name = "updated_at")
	private Date updatedAt;
	@Column(name = "quantity")
	private Double quantity;
	@Column(name = "current_quantity")
	private Double currentQuantity;
	@Column(name = "state_id")
	private Long state;
	@Column(name = "is_Active")
	private Boolean isActive;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "last_opened_by")
	private Long lastOpenedBy;
	@Column(name = "last_opened_at")
	private Date lastOpenedAt;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusMaster status;
	@ManyToOne
	@JoinColumn(name = "issue_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private IssuedList issuedList;

	@OneToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	public BoxLite() {
		super();

	}

	public BoxLite(Long id, String boxNo, Long createdBy, Date createdAt, Long updatedBy, Date updatedAt,
			Double quantity, Double currentQuantity, Long state, Boolean isActive, String remarks, Long lastOpenedBy,
			Date lastOpenedAt) {
		super();
		this.id = id;
		this.boxNo = boxNo;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.quantity = quantity;
		this.currentQuantity = currentQuantity;
		this.state = state;
		this.isActive = isActive;
		this.remarks = remarks;
		this.lastOpenedBy = lastOpenedBy;
		this.lastOpenedAt = lastOpenedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Double currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getLastOpenedBy() {
		return lastOpenedBy;
	}

	public void setLastOpenedBy(Long lastOpenedBy) {
		this.lastOpenedBy = lastOpenedBy;
	}

	public Date getLastOpenedAt() {
		return lastOpenedAt;
	}

	public void setLastOpenedAt(Date lastOpenedAt) {
		this.lastOpenedAt = lastOpenedAt;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public StatusMaster getStatus() {
		return status;
	}

	public void setStatus(StatusMaster status) {
		this.status = status;
	}

	public IssuedList getIssuedList() {
		return issuedList;
	}

	public void setIssuedList(IssuedList issuedList) {
		this.issuedList = issuedList;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public BoxDTO convertEntityToDto(BoxLite box) {
		return new BoxDTO(box.getId(), null,
				box.getCreatedAt() != null ? DateUtil.addMinutesToJavaUtilDate(box.getCreatedAt(), 330) : null, null,
				box.getUpdatedAt() != null ? DateUtil.addMinutesToJavaUtilDate(box.getUpdatedAt(), 330) : null,
				box.getQuantity(), box.getCurrentQuantity(), null, box.getIsActive(), null, remarks, null,
				box.getLastOpenedAt() != null ? DateUtil.addMinutesToJavaUtilDate(box.getLastOpenedAt(), 330) : null,
				box.getBoxNo(), box.getStatus());
	}

	public BoxDTO convertEntityToDtoV2(BoxLite box) {
		return new BoxDTO(box.getId(), null,
				box.getCreatedAt() != null ? DateUtil.addMinutesToJavaUtilDate(box.getCreatedAt(), 0) : null, null,
				box.getUpdatedAt() != null ? DateUtil.addMinutesToJavaUtilDate(box.getUpdatedAt(), 0) : null,
				box.getQuantity(), box.getCurrentQuantity(), null, box.getIsActive(), null, remarks, null,
				box.getLastOpenedAt() != null ? DateUtil.addMinutesToJavaUtilDate(box.getLastOpenedAt(), 0) : null,
				box.getBoxNo(), box.getStatus());
	}

}