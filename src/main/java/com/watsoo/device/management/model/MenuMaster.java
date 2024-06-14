package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.watsoo.crm.gateway.dto.MenuMstrRespDTO;
import com.watsoo.device.management.dto.MenuMasterDto;

@Entity
@Table(name = "menu_master")
public class MenuMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String desc;

	@CreationTimestamp
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "is_admin_visible")
	private Boolean isAdminVisible;

	@Column(name = "link")
	private String link;
	
	@Column(name = "icon")
	private String icon;

	public MenuMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public Boolean getIsAdminVisible() {
		return isAdminVisible;
	}

	public void setIsAdminVisible(Boolean isAdminVisible) {
		this.isAdminVisible = isAdminVisible;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public MenuMaster(Long id, String name, String desc, Date createdOn, Long createdBy, Boolean isAdminVisible,
			String link) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.isAdminVisible = isAdminVisible;
		this.link = link;
	}

	public MenuMasterDto convertToMenuMasterDto() {
		return new MenuMasterDto(this.getId(), this.getName(), this.getIsAdminVisible(), this.getLink(),this.getIcon());
	}
	
	public MenuMstrRespDTO convertToMenuMasterRespDto() {
		return new MenuMstrRespDTO(this.getId(), this.getName(), this.getIsAdminVisible(), this.getLink());
	}

}