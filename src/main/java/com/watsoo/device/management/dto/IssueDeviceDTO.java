package com.watsoo.device.management.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class IssueDeviceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long clientId;

	private Long stateId;

	private Date issuedDate;

	private Long quantity;

	private Date createdAt;

	private String issueSlipNumber;

	private Long createdBy;

	private Date updatedAt;

	private Long updatedBy;

	private List<BoxDTO> boxs;

	private Long requestDevices;

	private ClientDTO clientDTO;

	private StateDTO stateDTO;

	private Long orderId;

	private String orderCode;

	private Boolean isOurClient;
	
	private List<String> imeiList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setState(Long stateId) {
		this.stateId = stateId;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<BoxDTO> getBoxs() {
		return boxs;
	}

	public void setBoxs(List<BoxDTO> boxs) {
		this.boxs = boxs;
	}

	public Long getRequestDevices() {
		return requestDevices;
	}

	public void setRequestDevices(Long requestDevices) {
		this.requestDevices = requestDevices;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public ClientDTO getClientDTO() {
		return clientDTO;
	}

	public void setClientDTO(ClientDTO clientDTO) {
		this.clientDTO = clientDTO;
	}

	public StateDTO getStateDTO() {
		return stateDTO;
	}

	public void setStateDTO(StateDTO stateDTO) {
		this.stateDTO = stateDTO;
	}

	public String getIssueSlipNumber() {
		return issueSlipNumber;
	}

	public void setIssueSlipNumber(String issueSlipNumber) {
		this.issueSlipNumber = issueSlipNumber;
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

	public Boolean getIsOurClient() {
		return isOurClient;
	}

	public void setIsOurClient(Boolean isOurClient) {
		this.isOurClient = isOurClient;
	}

	public List<String> getImeiList() {
		return imeiList;
	}

	public void setImeiList(List<String> imeiList) {
		this.imeiList = imeiList;
	}

	public IssueDeviceDTO(Long id, Long clientId, Long stateId, Date issuedDate, Long quantity, Date createdAt,
			Long createdBy, Date updatedAt, Long updatedBy, List<BoxDTO> boxs, Long requestDevices, ClientDTO clientDTO,
			StateDTO stateDTO) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.stateId = stateId;
		this.issuedDate = issuedDate;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.boxs = boxs;
		this.requestDevices = requestDevices;
		this.clientDTO = clientDTO;
		this.stateDTO = stateDTO;
	}

	public IssueDeviceDTO(Long id, Long quantity, Date createdAt, String issueSlipNumber, Long createdBy,
			Date updatedAt, Long updatedBy, ClientDTO clientDTO, StateDTO stateDTO, Long orderId, String orderCode) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.issueSlipNumber = issueSlipNumber;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.clientDTO = clientDTO;
		this.stateDTO = stateDTO;
		this.orderId = orderId;
		this.orderCode = orderCode;
	}

	public IssueDeviceDTO(Long id, Long quantity, Date createdAt, String issueSlipNumber, Long createdBy,
			Date updatedAt, Long updatedBy, ClientDTO clientDTO, StateDTO stateDTO) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.issueSlipNumber = issueSlipNumber;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.clientDTO = clientDTO;
		this.stateDTO = stateDTO;
	}

	public IssueDeviceDTO() {
		super();
	}

	public IssueDeviceDTO(Long id, Long clientId, Long stateId, Date issuedDate, Long quantity, Date createdAt,
			Long createdBy, Date updatedAt, Long updatedBy, List<BoxDTO> boxs, Long requestDevices) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.stateId = stateId;
		this.issuedDate = issuedDate;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.boxs = boxs;
		this.requestDevices = requestDevices;
	}
}
