package com.watsoo.device.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device_mstr")
public class DeviceMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "total_count")
	private Long totalCount;
	
	@Column(name = "store_id")
	private Long storeId;
	
	@Column(name = "use_store_composition")
	private Boolean useStoreComposition;
	
	@Column(name = "request_type_sent")
	private String requestTypeSent;
	
	@Column(name = "site_id")
	private Long siteId;
	
	public DeviceMaster() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DeviceMaster(Long id, String name, Long totalCount) {
		super();
		this.id = id;
		this.name = name;
		this.totalCount = totalCount;
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

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Boolean getUseStoreComposition() {
		return useStoreComposition;
	}

	public void setUseStoreComposition(Boolean useStoreComposition) {
		this.useStoreComposition = useStoreComposition;
	}

	public String getRequestTypeSent() {
		return requestTypeSent;
	}

	public void setRequestTypeSent(String requestTypeSent) {
		this.requestTypeSent = requestTypeSent;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
}
