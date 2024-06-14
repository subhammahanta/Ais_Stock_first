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

import com.watsoo.device.management.dto.ReConfigureDevicesDTO;

@Entity
@Table(name = "re_configure_devices")
public class ReConfigureDevices implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@OneToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@Column(name = "created_at")
	private Date createdAt;

	@OneToOne
	@JoinColumn(name = "updated_by")
	private User updatedBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "is_re_configure")
	private Boolean isReConfigure;

	@Column(name = "re_config_box_id")
	private Long reConfigBoxId;

	public ReConfigureDevices() {
		super();
	}

	public ReConfigureDevices(Long id, String imeiNo, User createdBy, Date createdAt, User updatedBy, Date updatedAt,
			Boolean isReConfigure, Long reConfigBoxId) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.isReConfigure = isReConfigure;
		this.reConfigBoxId = reConfigBoxId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
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

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getIsReConfigure() {
		return isReConfigure;
	}

	public void setIsReConfigure(Boolean isReConfigure) {
		this.isReConfigure = isReConfigure;
	}

	public Long getReConfigBoxId() {
		return reConfigBoxId;
	}

	public void setReConfigBoxId(Long reConfigBoxId) {
		this.reConfigBoxId = reConfigBoxId;
	}
	
	public static ReConfigureDevicesDTO convertToDTO(ReConfigureDevices entity) {
        ReConfigureDevicesDTO dto = new ReConfigureDevicesDTO();
        dto.setId(entity.getId());
        dto.setImeiNo(entity.getImeiNo());
        dto.setIsReConfigure(entity.getIsReConfigure());
        dto.setReConfigBoxId(entity.getReConfigBoxId());
        return dto;
    }

}
