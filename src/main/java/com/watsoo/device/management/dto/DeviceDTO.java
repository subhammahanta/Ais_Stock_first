package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import com.watsoo.device.management.enums.StatusMaster;

public class DeviceDTO {
	private Long id;
	private String imeiNo;
	private String iccidNo;
	private String uuidNo;
	private String softwareVersion;
	private String detail;
	private Date createdAt;
	private Date updatedAt;
	private String requestBody;
	private String oldIccid;
	private Date iccidUpdatedAt;
	private String oldImei;
	private Date imeiUpdatedAt;
	private UserDTO createdBy;
	private Long modifiedBy;
	private Long stateId;
	private StateDTO stateDto;
	private Long productStatusMappingId;
	private ProductStatusMappingDTO productStatusMappingDTO;
	private String status;
	private Date issueDate;
	private Boolean IsConfigurationComplete;
	private List<String> imeiNos;
	private StatusMaster statusEnum;
	private String boxNo;
	

	public DeviceDTO() {
		super();
	}

	public DeviceDTO(Long id, String imeiNo, String iccidNo, String uuidNo, String softwareVersion, String detail,
			Date createdAt, Date updatedAt, String requestBody, String oldIccid, Date iccidUpdatedAt, String oldImei,
			Date imeiUpdatedAt, UserDTO createdBy, Long modifiedBy, StateDTO stateDto,
			ProductStatusMappingDTO productStatusMappingDTO) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.iccidNo = iccidNo;
		this.uuidNo = uuidNo;
		this.softwareVersion = softwareVersion;
		this.detail = detail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.requestBody = requestBody;
		this.oldIccid = oldIccid;
		this.iccidUpdatedAt = iccidUpdatedAt;
		this.oldImei = oldImei;
		this.imeiUpdatedAt = imeiUpdatedAt;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.stateDto = stateDto;
		this.productStatusMappingDTO = productStatusMappingDTO;
	}

	public DeviceDTO(Long id2, String imeiNo2, String iccidNo2, String uuidNo2, String softwareVersion2, String detail2,
			Date createdAt2, Date updatedAt2, String requestBody2, String oldIccid2, Date iccidUpdatedAt2,
			String oldImei2, Date imeiUpdatedAt2, Long modifiedBy2, Long state, String status) {
		this.id = id2;
		this.imeiNo = imeiNo2;
		this.iccidNo = iccidNo2;
		this.uuidNo = uuidNo2;
		this.softwareVersion = softwareVersion2;
		this.detail = detail2;
		this.createdAt = createdAt2;
		this.updatedAt = updatedAt2;
		// this.requestBody=requestBody2;
		this.oldIccid = oldIccid2;
		this.iccidUpdatedAt = iccidUpdatedAt2;
		this.oldImei = oldImei2;
		this.imeiUpdatedAt = imeiUpdatedAt2;
		// this.createdBy.id=createdBy2;
		this.modifiedBy = modifiedBy2;
		this.stateId = state;
		this.status = status;
	}

	public DeviceDTO(Long id2, String imeiNo2, String iccidNo2, String uuidNo2, String softwareVersion2, String detail2,
			Date createdAt2, Date updatedAt2, String requestBody2, String oldIccid2, Date iccidUpdatedAt2,
			String oldImei2, Date imeiUpdatedAt2,StatusMaster statusEnum) {
		this.id = id2;
		this.imeiNo = imeiNo2;
		this.iccidNo = iccidNo2;
		this.uuidNo = uuidNo2;
		this.softwareVersion = softwareVersion2;
		this.detail = detail2;
		this.createdAt = createdAt2;
		this.updatedAt = updatedAt2;
		this.oldIccid = oldIccid2;
		this.iccidUpdatedAt = iccidUpdatedAt2;
		this.oldImei = oldImei2;
		this.imeiUpdatedAt = imeiUpdatedAt2;
		this.statusEnum=statusEnum;
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

	public String getIccidNo() {
		return iccidNo;
	}

	public void setIccidNo(String iccidNo) {
		this.iccidNo = iccidNo;
	}

	public String getUuidNo() {
		return uuidNo;
	}

	public void setUuidNo(String uuidNo) {
		this.uuidNo = uuidNo;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getOldIccid() {
		return oldIccid;
	}

	public void setOldIccid(String oldIccid) {
		this.oldIccid = oldIccid;
	}

	public Date getIccidUpdatedAt() {
		return iccidUpdatedAt;
	}

	public void setIccidUpdatedAt(Date iccidUpdatedAt) {
		this.iccidUpdatedAt = iccidUpdatedAt;
	}

	public String getOldImei() {
		return oldImei;
	}

	public void setOldImei(String oldImei) {
		this.oldImei = oldImei;
	}

	public Date getImeiUpdatedAt() {
		return imeiUpdatedAt;
	}

	public void setImeiUpdatedAt(Date imeiUpdatedAt) {
		this.imeiUpdatedAt = imeiUpdatedAt;
	}

	public UserDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public StateDTO getStateDto() {
		return stateDto;
	}

	public void setStateDto(StateDTO stateDto) {
		this.stateDto = stateDto;
	}

	public Long getProductStatusMappingId() {
		return productStatusMappingId;
	}

	public void setProductStatusMappingId(Long productStatusMappingId) {
		this.productStatusMappingId = productStatusMappingId;
	}

	public ProductStatusMappingDTO getProductStatusMappingDTO() {
		return productStatusMappingDTO;
	}

	public void setProductStatusMappingDTO(ProductStatusMappingDTO productStatusMappingDTO) {
		this.productStatusMappingDTO = productStatusMappingDTO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Boolean getIsConfigurationComplete() {
		return IsConfigurationComplete;
	}

	public void setIsConfigurationComplete(Boolean isConfigurationComplete) {
		IsConfigurationComplete = isConfigurationComplete;
	}

	public List<String> getImeiNos() {
		return imeiNos;
	}

	public void setImeiNos(List<String> imeiNos) {
		this.imeiNos = imeiNos;
	}

	public StatusMaster getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(StatusMaster statusEnum) {
		this.statusEnum = statusEnum;
	}
	
	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public DeviceDTO(Long id, String imeiNo, String iccidNo, String uuidNo, String softwareVersion, String detail,
			Date createdAt, Date updatedAt, String requestBody, String oldIccid, Date iccidUpdatedAt, String oldImei,
			Date imeiUpdatedAt, UserDTO createdBy, Long modifiedBy, Long stateId, StateDTO stateDto,
			Long productStatusMappingId, ProductStatusMappingDTO productStatusMappingDTO, String status, Date issueDate,
			Boolean isConfigurationComplete, List<String> imeiNos, StatusMaster statusEnum) {
		super();
		this.id = id;
		this.imeiNo = imeiNo;
		this.iccidNo = iccidNo;
		this.uuidNo = uuidNo;
		this.softwareVersion = softwareVersion;
		this.detail = detail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.requestBody = requestBody;
		this.oldIccid = oldIccid;
		this.iccidUpdatedAt = iccidUpdatedAt;
		this.oldImei = oldImei;
		this.imeiUpdatedAt = imeiUpdatedAt;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.stateId = stateId;
		this.stateDto = stateDto;
		this.productStatusMappingId = productStatusMappingId;
		this.productStatusMappingDTO = productStatusMappingDTO;
		this.status = status;
		this.issueDate = issueDate;
		IsConfigurationComplete = isConfigurationComplete;
		this.imeiNos = imeiNos;
		this.statusEnum = statusEnum;
	}	

	public DeviceDTO convertMappedDeviceToDeviceDto(MappingVehicleDTO device) {
		return new DeviceDTO(device.getId(), device.getImeiNo(), device.getIccidNo(), device.getUuidNo(),
				device.getSoftwareVersion(), device.getDetail(), device.getCreatedAt(), device.getUpdatedAt(),
				device.getRequestBody(), device.getOldIccid(), device.getIccidUpdatedAt(), device.getOldImei(),
				device.getImeiUpdatedAt(), device.getModifiedBy(), device.getState(), device.getStatus());
	}

}
