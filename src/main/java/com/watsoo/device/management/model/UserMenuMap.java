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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "user_menu_map")
public class UserMenuMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private MenuMaster menuMstr;

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
    
    public UserMenuMap(Long id, Long roleId, MenuMaster menuMstr, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.menuMstr = menuMstr;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public UserMenuMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public MenuMaster getMenuMstr() {
        return menuMstr;
    }

    public void setMenuMstr(MenuMaster menuMstr) {
        this.menuMstr = menuMstr;
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
