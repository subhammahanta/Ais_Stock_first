package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.watsoo.device.management.enums.ActionEnum;
import com.watsoo.device.management.enums.CommandStatusEnum;

public class DeviceCommandDTO {

	private String url;
	private String userName;
	private String password;
	private Integer deviceId;
	private String command;
	private String imeiNo;
	private Date modifiedAt;
	private Long userId;
	private Boolean sosOn;
	private Boolean engineImmobiliser;
	private Long vehicleId;
	private List<String> imeiList;
	private String softwareVersion;
	private Long createdBy;
	private Date createdAt;
	private Long requestId;
	private ActionEnum action;
	private String revertCommandTemplate;
    private Long clientId;
    private Long stateId;
    private String response;
    private Map<String, String> keyToVerify;
    private Integer orderQuantity;
    private Long modelId;
    private Long lotId;
    private Integer sequenceId;
    private Boolean commandResponse;
    private Integer modelConfigSequenceNo;
    private String iccidNo;
    private String uuidNo;
    private Long testDeviceId;
    private Boolean success;
    @Enumerated(EnumType.STRING)
	private CommandStatusEnum status;
    private String detail;
    private Long operatorId;
    private Long providerId;
    private Long modelConfigId;
    private Boolean isCommandClientSpecific;
    private Long tacId;
    private Double externalVoltage;
    private String failureReason;
    private Boolean isDefault;
    private String deviceModel;
    private String tacNo;
    private Long deviceModelId;
    private Long emsMasterId;
    private String emsMasterCode;
    
	public DeviceCommandDTO() {
		super();
	}

	public DeviceCommandDTO(String url, String userName, String password, Integer deviceId, String command,
			String imeiNo, Date modifiedAt, Long userId) {
		super();
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.deviceId = deviceId;
		this.command = command;
		this.imeiNo = imeiNo;
		this.modifiedAt = modifiedAt;
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getSosOn() {
		return sosOn;
	}

	public void setSosOn(Boolean sosOn) {
		this.sosOn = sosOn;
	}

	public Boolean getEngineImmobiliser() {
		return engineImmobiliser;
	}

	public void setEngineImmobiliser(Boolean engineImmobiliser) {
		this.engineImmobiliser = engineImmobiliser;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public List<String> getImeiList() {
		return imeiList;
	}

	public void setImeiList(List<String> imeiList) {
		this.imeiList = imeiList;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
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

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public ActionEnum getAction() {
		return action;
	}

	public void setAction(ActionEnum action) {
		this.action = action;
	}

	public String getRevertCommandTemplate() {
		return revertCommandTemplate;
	}

	public void setRevertCommandTemplate(String revertCommandTemplate) {
		this.revertCommandTemplate = revertCommandTemplate;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Map<String, String> getKeyToVerify() {
		return keyToVerify;
	}

	public void setKeyToVerify(Map<String, String> keyToVerify) {
		this.keyToVerify = keyToVerify;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Integer getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Boolean getCommandResponse() {
		return commandResponse;
	}

	public void setCommandResponse(Boolean commandResponse) {
		this.commandResponse = commandResponse;
	}

	public Integer getModelConfigSequenceNo() {
		return modelConfigSequenceNo;
	}

	public void setModelConfigSequenceNo(Integer modelConfigSequenceNo) {
		this.modelConfigSequenceNo = modelConfigSequenceNo;
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

	public Long getTestDeviceId() {
		return testDeviceId;
	}

	public void setTestDeviceId(Long testDeviceId) {
		this.testDeviceId = testDeviceId;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public CommandStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CommandStatusEnum status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Long modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public Boolean getIsCommandClientSpecific() {
		return isCommandClientSpecific;
	}

	public void setIsCommandClientSpecific(Boolean isCommandClientSpecific) {
		this.isCommandClientSpecific = isCommandClientSpecific;
	}

	public Long getTacId() {
		return tacId;
	}

	public void setTacId(Long tacId) {
		this.tacId = tacId;
	}

	public Double getExternalVoltage() {
		return externalVoltage;
	}

	public void setExternalVoltage(Double externalVoltage) {
		this.externalVoltage = externalVoltage;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getTacNo() {
		return tacNo;
	}

	public void setTacNo(String tacNo) {
		this.tacNo = tacNo;
	}

	public Long getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(Long deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public Long getEmsMasterId() {
		return emsMasterId;
	}

	public void setEmsMasterId(Long emsMasterId) {
		this.emsMasterId = emsMasterId;
	}

	public String getEmsMasterCode() {
		return emsMasterCode;
	}

	public void setEmsMasterCode(String emsMasterCode) {
		this.emsMasterCode = emsMasterCode;
	}

}