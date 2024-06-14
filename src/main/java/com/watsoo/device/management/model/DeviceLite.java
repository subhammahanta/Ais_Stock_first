package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.watsoo.device.management.dto.DeviceDTO;
import com.watsoo.device.management.enums.StatusMaster;

@Entity
@Table(name = "device")
public class DeviceLite implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "iccid_no")
	private String iccidNo;

	@Column(name = "uuid_no")
	private String uuidNo;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "software_version")
	private String softwareVersion;

	//@JsonInclude(Include.NON_NULL)
	@Column(name = "detail")
	private String detail;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "old_iccid")
	private String oldIccid;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "iccid_updated_at")
	private Date iccidUpdatedAt;

	@Column(name = "old_imei")
	private String oldImei;

	@Column(name = "imei_updated_at")
	private Date imeiUpdatedAt;

	@OneToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "modified_by")
	private Long modifiedBy;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	@Enumerated(EnumType.STRING)
	private StatusMaster status;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "issue_date")
	private Date issueDate;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_command_send")
	private Boolean isCommandSend;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "command_send_date")
	private Date commandSendDate;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_configuration_complete")
	private Boolean isConfigurationComplete;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "config_done_date")
	private Date configDoneDate;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "device_activation_date")
	private Date deviceActivatedDate;

	@Column(name = "last_command")
	private String lastCommand;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_number")
	private String sim1Number;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_number")
	private String sim2Number;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_operator")
	private String sim1Operator;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_operator")
	private String sim2Operator;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_activation_date")
	private Date sim1ActivationDate;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_expiry_date")
	private Date sim1ExpiryDate;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_activation_date")
	private Date sim2ActivationDate;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_expiry_date")
	private Date sim2ExpiryDate;
	
	@Column(name = "client_id")
	private Long clientId;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_config_activated")
	private Boolean isConfigActivated;
	
	@Transient
	private Long userId;

	@Transient
	private String userName;

	@Transient
	private String userEmail;

	@Transient
	private String token;

	@Transient
	private String stateRefKey;

	@Transient
	private String responseMsg;
	@Transient
	private String clientName;

	@Transient
	private String boxNo;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "box_code")
	private String boxCode;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "order_id")
	private Long orderId;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "order_code")
	private String orderCode;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "packet")
	private String packet;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "packed_date")
	private Date packedDate;

	@Column(name = "client_name")
	private String clientNames;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "request_body")
	private String requestBody;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_own_device")
	private Boolean isOwnDevice;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_return_type")
	private Boolean isReturnType;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_replace_type")
	private Boolean isReplaceType;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_repair_type")
	private Boolean isRepairType;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim1_provider")
	private String sim1Provider;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "sim2_provider")
	private String sim2Provider;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name = "is_created_from_excel")
	private Boolean isCreatedFromExcel;
	
	@Column(name = "mfg_lot_id")
	private String mfgLotId;
	
	@Column(name = "replaced_by_device_id")
	private Long replacedByDeviceId;

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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public StatusMaster getStatus() {
		return status;
	}

	public void setStatus(StatusMaster status) {
		this.status = status;
	}

	public String getStateRefKey() {
		return stateRefKey;
	}

	public void setStateRefKey(String stateRefKey) {
		this.stateRefKey = stateRefKey;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Boolean getIsConfigurationComplete() {
		return isConfigurationComplete;
	}

	public void setIsConfigurationComplete(Boolean isConfigurationComplete) {
		this.isConfigurationComplete = isConfigurationComplete;
	}

	public Boolean getIsCommandSend() {
		return isCommandSend;
	}

	public void setIsCommandSend(Boolean isCommandSend) {
		this.isCommandSend = isCommandSend;
	}

	public Date getCommandSendDate() {
		return commandSendDate;
	}

	public void setCommandSendDate(Date commandSendDate) {
		this.commandSendDate = commandSendDate;
	}

	public Date getConfigDoneDate() {
		return configDoneDate;
	}

	public void setConfigDoneDate(Date configDoneDate) {
		this.configDoneDate = configDoneDate;
	}

	public DeviceLite(String imeiNo, String iccidNo, String uuidNo, String softwareVersion, String detail) {
		super();
		this.imeiNo = imeiNo;
		this.iccidNo = iccidNo;
		this.uuidNo = uuidNo;
		this.softwareVersion = softwareVersion;
		this.detail = detail;
	}

	public DeviceLite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLastCommand() {
		return lastCommand;
	}

	public void setLastCommand(String lastCommand) {
		this.lastCommand = lastCommand;
	}

	public Date getDeviceActivatedDate() {
		return deviceActivatedDate;
	}

	public void setDeviceActivatedDate(Date deviceActivatedDate) {
		this.deviceActivatedDate = deviceActivatedDate;
	}

	public String getSim1Number() {
		return sim1Number;
	}

	public void setSim1Number(String sim1Number) {
		this.sim1Number = sim1Number;
	}

	public String getSim2Number() {
		return sim2Number;
	}

	public void setSim2Number(String sim2Number) {
		this.sim2Number = sim2Number;
	}

	public String getSim1Operator() {
		return sim1Operator;
	}

	public void setSim1Operator(String sim1Operator) {
		this.sim1Operator = sim1Operator;
	}

	public String getSim2Operator() {
		return sim2Operator;
	}

	public void setSim2Operator(String sim2Operator) {
		this.sim2Operator = sim2Operator;
	}

	public Date getSim1ActivationDate() {
		return sim1ActivationDate;
	}

	public void setSim1ActivationDate(Date sim1ActivationDate) {
		this.sim1ActivationDate = sim1ActivationDate;
	}

	public Date getSim1ExpiryDate() {
		return sim1ExpiryDate;
	}

	public void setSim1ExpiryDate(Date sim1ExpiryDate) {
		this.sim1ExpiryDate = sim1ExpiryDate;
	}

	public Date getSim2ActivationDate() {
		return sim2ActivationDate;
	}

	public void setSim2ActivationDate(Date sim2ActivationDate) {
		this.sim2ActivationDate = sim2ActivationDate;
	}

	public Date getSim2ExpiryDate() {
		return sim2ExpiryDate;
	}

	public void setSim2ExpiryDate(Date sim2ExpiryDate) {
		this.sim2ExpiryDate = sim2ExpiryDate;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Boolean getIsConfigActivated() {
		return isConfigActivated;
	}

	public void setIsConfigActivated(Boolean isConfigActivated) {
		this.isConfigActivated = isConfigActivated;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public Date getPackedDate() {
		return packedDate;
	}

	public void setPackedDate(Date packedDate) {
		this.packedDate = packedDate;
	}

	public String getClientNames() {
		return clientNames;
	}

	public void setClientNames(String clientNames) {
		this.clientNames = clientNames;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Boolean getIsOwnDevice() {
		return isOwnDevice;
	}

	public void setIsOwnDevice(Boolean isOwnDevice) {
		this.isOwnDevice = isOwnDevice;
	}

	public Boolean getIsReturnType() {
		return isReturnType;
	}

	public void setIsReturnType(Boolean isReturnType) {
		this.isReturnType = isReturnType;
	}

	public Boolean getIsReplaceType() {
		return isReplaceType;
	}

	public void setIsReplaceType(Boolean isReplaceType) {
		this.isReplaceType = isReplaceType;
	}

	public Boolean getIsRepairType() {
		return isRepairType;
	}

	public void setIsRepairType(Boolean isRepairType) {
		this.isRepairType = isRepairType;
	}

	public String getSim1Provider() {
		return sim1Provider;
	}

	public void setSim1Provider(String sim1Provider) {
		this.sim1Provider = sim1Provider;
	}

	public String getSim2Provider() {
		return sim2Provider;
	}

	public void setSim2Provider(String sim2Provider) {
		this.sim2Provider = sim2Provider;
	}

	public Boolean getIsCreatedFromExcel() {
		return isCreatedFromExcel;
	}

	public void setIsCreatedFromExcel(Boolean isCreatedFromExcel) {
		this.isCreatedFromExcel = isCreatedFromExcel;
	}

	public String getMfgLotId() {
		return mfgLotId;
	}

	public void setMfgLotId(String mfgLotId) {
		this.mfgLotId = mfgLotId;
	}

	public Long getReplacedByDeviceId() {
		return replacedByDeviceId;
	}

	public void setReplacedByDeviceId(Long replacedByDeviceId) {
		this.replacedByDeviceId = replacedByDeviceId;
	}

	public DeviceDTO convertEntityToDTO() {
		return new DeviceDTO(this.id, this.imeiNo, this.iccidNo, this.uuidNo, this.softwareVersion, this.detail,
				this.createdAt, this.updatedAt, null, this.oldIccid, this.iccidUpdatedAt, this.oldImei,
				this.imeiUpdatedAt, this.createdBy.convertToDTO(), this.modifiedBy, null, null);
	}
}
