package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.watsoo.device.management.enums.OperationEnum;
import com.watsoo.device.management.enums.StatusEnum;

public class ReturnReplaceRepairDTO {

	private Long id;

	private String imeiList;

	@Enumerated(EnumType.STRING)
	private OperationEnum operation;

	private String clientName;

	private Long createdBy;

	private Date createdAt;

	private String replaceByImei;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	private String imeiNo;
	
	private Long fromDate;
	
	private Long toDate;
	
	private List<ReturnReplaceRepairDeviceDTO> deviceDto;
	
	private Long clientId;
	
	private Boolean isOurClient;
	
	private String remark;

	public ReturnReplaceRepairDTO() {
		super();
	}

	public ReturnReplaceRepairDTO(Long id, String imeiList, OperationEnum operation, String clientName, Long createdBy,
			Date createdAt, String replaceByImei, StatusEnum status, String imeiNo) {
		super();
		this.id = id;
		this.imeiList = imeiList;
		this.operation = operation;
		this.clientName = clientName;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.replaceByImei = replaceByImei;
		this.status = status;
		this.imeiNo = imeiNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImeiList() {
		return imeiList;
	}

	public void setImeiList(String imeiList) {
		this.imeiList = imeiList;
	}

	public OperationEnum getOperation() {
		return operation;
	}

	public void setOperation(OperationEnum operation) {
		this.operation = operation;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getReplaceByImei() {
		return replaceByImei;
	}

	public void setReplaceByImei(String replaceByImei) {
		this.replaceByImei = replaceByImei;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public Long getFromDate() {
		return fromDate;
	}

	public void setFromDate(Long fromDate) {
		this.fromDate = fromDate;
	}

	public Long getToDate() {
		return toDate;
	}

	public void setToDate(Long toDate) {
		this.toDate = toDate;
	}

	public List<ReturnReplaceRepairDeviceDTO> getDeviceDto() {
		return deviceDto;
	}

	public void setDeviceDto(List<ReturnReplaceRepairDeviceDTO> deviceDto) {
		this.deviceDto = deviceDto;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Boolean getIsOurClient() {
		return isOurClient;
	}

	public void setIsOurClient(Boolean isOurClient) {
		this.isOurClient = isOurClient;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
