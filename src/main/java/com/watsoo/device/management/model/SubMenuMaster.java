package com.watsoo.device.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.watsoo.crm.gateway.dto.SubMenuMasterResponseDto;
import com.watsoo.device.management.dto.SubMenuMasterDto;

@Entity
@Table(name = "sub_menu_master")
public class SubMenuMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String desc;

	@Column(name = "is_visible")
	private Boolean isVisible;

	@CreationTimestamp
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "menu_id")
	private Long menuId;

	@Column(name = "menu_name")
	private String menuName;

	@Column(name = "link")
	private String link;
	
	@Column(name = "icon")
	private String icon;
	
	@Column(name = "is_admin_visible")
	private Boolean isAdminVisible;

	public SubMenuMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getLink() {
		return this.link;
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

	public Boolean getIsAdminVisible() {
		return isAdminVisible;
	}

	public void setIsAdminVisible(Boolean isAdminVisible) {
		this.isAdminVisible = isAdminVisible;
	}

	public SubMenuMaster(Long id, String name, String desc, Boolean isVisible, Date createdOn, Long createdBy,
			Long menuId, String menuName, String link) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.isVisible = isVisible;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.menuId = menuId;
		this.menuName = menuName;
		this.link = link;
	}

	public SubMenuMasterDto convertToSubMenuMasterDto() {
		return new SubMenuMasterDto(this.getId(), this.getName(), this.getIsVisible(), this.getMenuId(),
				this.getMenuName(), this.getLink(),this.getIcon());
	}
	
	public SubMenuMasterResponseDto convertToSubMenuMasterRespDto() {
		return new SubMenuMasterResponseDto(this.getId(), this.getName(), this.getIsVisible(), this.getMenuId(),
				this.getMenuName(), this.getLink());
	}

}