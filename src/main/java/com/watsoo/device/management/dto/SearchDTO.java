package com.watsoo.device.management.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.watsoo.device.management.enums.ProductType;

public class SearchDTO {

	private Integer pageNo;

	private Integer pageSize;
	
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	
	@Enumerated(EnumType.STRING)
	private String statusMaster;
	
	private Long statusMasterId;
	
	private Long productTypeId;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getStatusMaster() {
		return statusMaster;
	}

	public void setStatusMaster(String statusMaster) {
		this.statusMaster = statusMaster;
	}

	public Long getStatusMasterId() {
		return statusMasterId;
	}

	public void setStatusMasterId(Long statusMasterId) {
		this.statusMasterId = statusMasterId;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
}
