package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "user_sub_menu_map")
public class UserSubMenuMap {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(name = "role_id")
	private Long role;

	@OneToOne
	@JoinColumn(name = "sub_menu_id")
	private SubMenuMaster subMenuMstr;

	@Column(name = "is_active")
	private Boolean isActive;

	@CreationTimestamp
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "is_considerable")
	private Boolean isConsiderable;

	public UserSubMenuMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserSubMenuMap(Long id, Long role, SubMenuMaster subMenuMstr, Boolean isActive, Date createdOn,
			Long createdBy, Long userId, Boolean isConsiderable) {
		super();
		this.id = id;
		this.role = role;
		this.subMenuMstr = subMenuMstr;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.userId = userId;
		this.isConsiderable = isConsiderable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public SubMenuMaster getSubMenuMstr() {
		return subMenuMstr;
	}

	public void setSubMenuMstr(SubMenuMaster subMenuMstr) {
		this.subMenuMstr = subMenuMstr;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getIsConsiderable() {
		return isConsiderable;
	}

	public void setIsConsiderable(Boolean isConsiderable) {
		this.isConsiderable = isConsiderable;
	}

}
