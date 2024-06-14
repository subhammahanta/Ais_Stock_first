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
@Table(name = "box_transaction")
public class BoxTransaction implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "quantity")
	private Double quantity;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "is_Active")
	private Boolean isActive;
	@OneToOne
	@JoinColumn(name = "box_id")
	private Box box;
	public BoxTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoxTransaction(Long id, User createdBy, Date createdAt, Double quantity, String remarks, Boolean isActive,
			Box box) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.quantity = quantity;
		this.remarks = remarks;
		this.isActive = isActive;
		this.box = box;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	
}
