package com.watsoo.device.management.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.dto.IssueDeviceDTO;

@Entity
@Table(name = "issued_list")
public class IssuedList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serial_number")
	private Long serialNumber;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@OneToOne
	@JoinColumn(name = "state_id")
	private State state;

	@Column(name = "issued_date")
	private Date issuedDate;

	@Column(name = "issue_slip_number")
	private String issueSlipNumber;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@OneToMany(mappedBy = "issuedList")
	private List<Box> boxs;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "order_code")
	private String orderCode;

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getIssueSlipNumber() {
		return issueSlipNumber;
	}

	public void setIssueSlipNumber(String issueSlipNumber) {
		this.issueSlipNumber = issueSlipNumber;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public IssuedList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IssuedList(Long serialNumber, Client client, State state, Date issuedDate, Long quantity) {
		super();
		this.serialNumber = serialNumber;
		this.client = client;
		this.state = state;
		this.issuedDate = issuedDate;
		this.quantity = quantity;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Box> getBoxs() {
		return boxs;
	}

	public void setBoxs(List<Box> boxs) {
		this.boxs = boxs;
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

	public IssuedList(Long serialNumber, Client client, State state, Date issuedDate, Long quantity, Long createdBy,
			Date createdAt, Long updatedBy, Date updatedAt) {
		super();
		this.serialNumber = serialNumber;
		this.client = client;
		this.state = state;
		this.issuedDate = issuedDate;
		this.quantity = quantity;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
	}

	public IssuedList(Long serialNumber, Client client, State state, Date issuedDate, Long quantity, Date createdAt,
			Long createdBy, Date updatedAt, Long updatedBy, List<Box> boxs, List<BoxDTO> list) {
		super();
		this.serialNumber = serialNumber;
		this.client = client;
		this.state = state;
		this.issuedDate = issuedDate;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.boxs = boxs;
	}

	public IssuedList(Long serialNumber, Client client, State state, Date issuedDate, String issueSlipNumber,
			Long quantity, Date createdAt, Long createdBy, Date updatedAt, Long updatedBy, List<Box> boxs) {
		super();
		this.serialNumber = serialNumber;
		this.client = client;
		this.state = state;
		this.issuedDate = issuedDate;
		this.issueSlipNumber = issueSlipNumber;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.boxs = boxs;
	}

//	public IssueDeviceDTO(Long id, Long quantity, Date createdAt, String issueSlipNumber, Long createdBy,
//			Date updatedAt, Long updatedBy, List<BoxDTO> boxs, ClientDTO clientDTO, StateDTO stateDTO) {

	public IssueDeviceDTO convertEntityToDto(IssuedList issuedList) {
		Client client = issuedList.getClient();
		State state = issuedList.getState();
		return new IssueDeviceDTO(issuedList.getSerialNumber(), issuedList.getQuantity(), issuedList.getCreatedAt(),
				issuedList.getIssueSlipNumber(), issuedList.getCreatedBy(), issuedList.getUpdatedAt(),
				issuedList.getUpdatedBy(), issuedList.getClient().convertEntityToDto(client),
				issuedList.getState().convertEntityToDto(state));
	}

	public IssueDeviceDTO convertEntityToDtoV2(IssuedList issuedList) {
		Client client = issuedList.getClient();
		State state = issuedList.getState();
		return new IssueDeviceDTO(issuedList.getSerialNumber(), issuedList.getQuantity(), issuedList.getCreatedAt(),
				issuedList.getIssueSlipNumber(), issuedList.getCreatedBy(), issuedList.getUpdatedAt(),
				issuedList.getUpdatedBy(), issuedList.getClient().convertEntityToDto(client),
				issuedList.getState().convertEntityToDto(state), issuedList.getOrderId(), issuedList.getOrderCode());
	}
}
