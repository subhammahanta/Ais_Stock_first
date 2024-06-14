package com.watsoo.device.management.dto;

import java.util.Date;

public class ProductStatusMappingDTO {
	private Long id;
	private ProductTypeDTO productType;
	private StatusMasterDTO statusMaster;
	private Boolean isActive;
	private Date created_At;
	private Long productTypeId;
	public ProductStatusMappingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductStatusMappingDTO(Long id, ProductTypeDTO productType, StatusMasterDTO statusMaster, Boolean isActive,
			Date created_At) {
		super();
		this.id = id;
		this.productType = productType;
		this.statusMaster = statusMaster;
		this.isActive = isActive;
		this.created_At = created_At;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductTypeDTO getProductType() {
		return productType;
	}
	public void setProductType(ProductTypeDTO productType) {
		this.productType = productType;
	}
	public StatusMasterDTO getStatusMaster() {
		return statusMaster;
	}
	public void setStatusMaster(StatusMasterDTO statusMaster) {
		this.statusMaster = statusMaster;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreated_At() {
		return created_At;
	}
	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}
	public Long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
}
