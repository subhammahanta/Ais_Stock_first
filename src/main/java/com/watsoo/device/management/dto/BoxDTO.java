package com.watsoo.device.management.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.watsoo.device.management.enums.StatusMaster;
import com.watsoo.device.management.model.Box;
import com.watsoo.device.management.model.Provider;
import com.watsoo.device.management.model.State;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.util.TokenUtility;

public class BoxDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private UserDTO createdBy;
	private Long createdById;
	private Date createdAt;
	private UserDTO updatedBy;
	private Long updatedById;
	private Date updatedAt;
	private Double quantity;
	private Double currentQuantity;
	private StateDTO state;
	private Long stateId;
	private Boolean isActive;
	private Long productStatusMappingId;
	private ProductStatusMappingDTO productStatusMapping;
	private String remarks;
	private UserDTO lastOpenedBy;
	private Long lastOpenedById;
	private Date lastOpenedAt;
	private String boxNo;
	private Date fromDate;
	private Date toDate;
	private Long startDate;
	private Long endDate;
	private List<Long> deviceIds;
	private List<DeviceDTO> deviceDtoList;
	private String createdByName;
	private String updatedByName;
	private StatusMaster status;
	private String search;
	private Long providerId;
	private Provider provider;
	public BoxDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BoxDTO(Long id, UserDTO createdBy, Long createdById, Date createdAt, UserDTO updatedBy, Long updatedById,
			Date updatedAt, Double quantity, Double currentQuantity, StateDTO state, Long stateId, Boolean isActive,
			Long productStatusMappingId, ProductStatusMappingDTO productStatusMapping, String remarks,
			UserDTO lastOpenedBy, Long lastOpenedById, Date lastOpenedAt, String boxNo) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedById = updatedById;
		this.updatedAt = updatedAt;
		this.quantity = quantity;
		this.currentQuantity = currentQuantity;
		this.state = state;
		this.stateId = stateId;
		this.isActive = isActive;
		this.productStatusMappingId = productStatusMappingId;
		this.productStatusMapping = productStatusMapping;
		this.remarks = remarks;
		this.lastOpenedBy = lastOpenedBy;
		this.lastOpenedById = lastOpenedById;
		this.lastOpenedAt = lastOpenedAt;
		this.boxNo = boxNo;
	}

	public BoxDTO(Long id, UserDTO createdBy, Date createdAt, UserDTO updatedBy, Date updatedAt, Double quantity,
			Double currentQuantity, StateDTO state, Boolean isActive, ProductStatusMappingDTO productStatusMapping,
			String remarks, UserDTO lastOpenedBy, Date lastOpenedAt, String boxNo, StatusMaster status) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.quantity = quantity;
		this.currentQuantity = currentQuantity;
		this.state = state;
		this.isActive = isActive;
		this.productStatusMapping = productStatusMapping;
		this.remarks = remarks;
		this.lastOpenedBy = lastOpenedBy;
		this.lastOpenedAt = lastOpenedAt;
		this.boxNo = boxNo;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public UserDTO getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserDTO updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(Long updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Double currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public StateDTO getState() {
		return state;
	}

	public void setState(StateDTO state) {
		this.state = state;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getProductStatusMappingId() {
		return productStatusMappingId;
	}

	public void setProductStatusMappingId(Long productStatusMappingId) {
		this.productStatusMappingId = productStatusMappingId;
	}

	public ProductStatusMappingDTO getProductStatusMapping() {
		return productStatusMapping;
	}

	public void setProductStatusMapping(ProductStatusMappingDTO productStatusMapping) {
		this.productStatusMapping = productStatusMapping;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public UserDTO getLastOpenedBy() {
		return lastOpenedBy;
	}

	public void setLastOpenedBy(UserDTO lastOpenedBy) {
		this.lastOpenedBy = lastOpenedBy;
	}

	public Long getLastOpenedById() {
		return lastOpenedById;
	}

	public void setLastOpenedById(Long lastOpenedById) {
		this.lastOpenedById = lastOpenedById;
	}

	public Date getLastOpenedAt() {
		return lastOpenedAt;
	}

	public void setLastOpenedAt(Date lastOpenedAt) {
		this.lastOpenedAt = lastOpenedAt;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public List<DeviceDTO> getDeviceDtoList() {
		return deviceDtoList;
	}

	public void setDeviceDtoList(List<DeviceDTO> deviceDtoList) {
		this.deviceDtoList = deviceDtoList;
	}

	public List<Long> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<Long> deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public StatusMaster getStatus() {
		return status;
	}

	public void setStatus(StatusMaster status) {
		this.status = status;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Box convertDtoToEntity(BoxDTO boxDto) {
		return new Box(null, TokenUtility.getBoxNextNumber(boxDto.getStateId()), new User(boxDto.getCreatedById()),
				new Date(), null, null, boxDto.getQuantity(), boxDto.getCurrentQuantity(),
				new State(boxDto.getStateId()), true, boxDto.getRemarks(), null, null);
	}
}