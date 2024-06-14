package com.watsoo.device.management.dto;

import java.util.List;

public class DeviceStoreRequestDto {

	private String REQUEST_TYPE_SENT;

	private Long site_id;

	private String mix_design_id;
	
	private List<Long> material_ids;

	public DeviceStoreRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DeviceStoreRequestDto(String rEQUEST_TYPE_SENT, Long site_id, String mix_design_id) {
		super();
		REQUEST_TYPE_SENT = rEQUEST_TYPE_SENT;
		this.site_id = site_id;
		this.mix_design_id = mix_design_id;
	}

	public DeviceStoreRequestDto(String requestTypeSent, Long siteId, String valueOf, List<Long> requiredItemsIds) {
		super();
		this.REQUEST_TYPE_SENT = requestTypeSent;
		this.site_id = siteId;
		this.mix_design_id = valueOf;
		this.material_ids = requiredItemsIds;
	}

	public String getREQUEST_TYPE_SENT() {
		return REQUEST_TYPE_SENT;
	}

	public void setREQUEST_TYPE_SENT(String rEQUEST_TYPE_SENT) {
		REQUEST_TYPE_SENT = rEQUEST_TYPE_SENT;
	}

	public Long getSite_id() {
		return site_id;
	}

	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}

	public String getMix_design_id() {
		return mix_design_id;
	}

	public void setMix_design_id(String mix_design_id) {
		this.mix_design_id = mix_design_id;
	}

	public List<Long> getMaterial_ids() {
		return material_ids;
	}

	public void setMaterial_ids(List<Long> material_ids) {
		this.material_ids = material_ids;
	}

}
