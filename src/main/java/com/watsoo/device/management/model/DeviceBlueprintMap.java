package com.watsoo.device.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "device_blueprint_map")
public class DeviceBlueprintMap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "device_mstr_id")
	private DeviceMaster deviceMstr;

	@Column(name = "material_store_id")
	private Long materialId;

	@Column(name = "material_name")
	private String materialName;

	@Column(name = "required_qty")
	private Double requiredQty;

	public DeviceBlueprintMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceBlueprintMap(Long id, DeviceMaster deviceMstr, Long materialId, String materialName,
			Double requiredQty) {
		super();
		this.id = id;
		this.deviceMstr = deviceMstr;
		this.materialId = materialId;
		this.materialName = materialName;
		this.requiredQty = requiredQty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeviceMaster getDeviceMstr() {
		return deviceMstr;
	}

	public void setDeviceMstr(DeviceMaster deviceMstr) {
		this.deviceMstr = deviceMstr;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Double getRequiredQty() {
		return requiredQty;
	}

	public void setRequiredQty(Double requiredQty) {
		this.requiredQty = requiredQty;
	}

}
