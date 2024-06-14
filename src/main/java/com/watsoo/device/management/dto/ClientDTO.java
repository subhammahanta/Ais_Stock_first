package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.State;

public class ClientDTO {

	private Long id;
	private String companyId;
	private String companyName;
	private String companyAddress;
	private State state;
	private String city;
	private String email;
	private Long phoneNumber;
	private String panNumber;
	private String companyLogo;
	private String companyCode;
	private Date lastIssueDate;
	private Long lastIssueQuantity;
	private Boolean isActive;
	private String gstNumber;
	private Boolean isOwn;
	private Long aisUserId;
	private Long stateId;
	private String clientOwner;
	private String clientPOC;
	private List<StateGstDTO> stateGstList;
    private Long userId;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getLastIssueDate() {
		return lastIssueDate;
	}

	public void setLastIssueDate(Date lastIssueDate) {
		this.lastIssueDate = lastIssueDate;
	}

	public Long getLastIssueQuantity() {
		return lastIssueQuantity;
	}

	public void setLastIssueQuantity(Long lastIssueQuantity) {
		this.lastIssueQuantity = lastIssueQuantity;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public Boolean getIsOwn() {
		return isOwn;
	}

	public void setIsOwn(Boolean isOwn) {
		this.isOwn = isOwn;
	}

	public Long getAisUserId() {
		return aisUserId;
	}

	public void setAisUserId(Long aisUserId) {
		this.aisUserId = aisUserId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getClientOwner() {
		return clientOwner;
	}

	public void setClientOwner(String clientOwner) {
		this.clientOwner = clientOwner;
	}

	public String getClientPOC() {
		return clientPOC;
	}

	public void setClientPOC(String clientPOC) {
		this.clientPOC = clientPOC;
	}

	public List<StateGstDTO> getStateGstList() {
		return stateGstList;
	}

	public void setStateGstList(List<StateGstDTO> stateGstList) {
		this.stateGstList = stateGstList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ClientDTO(Long id, String companyId, String companyName, String companyAddress, State state, String city,
			String email, Long phoneNumber, String panNumber, String companyLogo, String companyCode,
			Date lastIssueDate, Long lastIssueQuantity, Boolean isActive) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.state = state;
		this.city = city;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.panNumber = panNumber;
		this.companyLogo = companyLogo;
		this.companyCode = companyCode;
		this.lastIssueDate = lastIssueDate;
		this.lastIssueQuantity = lastIssueQuantity;
		this.isActive = isActive;
	}

	public ClientDTO(Long id, String companyId, String companyName, String companyAddress, State state, String city,
			String email, Long phoneNumber, String panNumber, String companyLogo, String companyCode,
			Date lastIssueDate, Long lastIssueQuantity, Boolean isActive, String gstNumber, Boolean isOwn) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.state = state;
		this.city = city;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.panNumber = panNumber;
		this.companyLogo = companyLogo;
		this.companyCode = companyCode;
		this.lastIssueDate = lastIssueDate;
		this.lastIssueQuantity = lastIssueQuantity;
		this.isActive = isActive;
		this.gstNumber = gstNumber;
		this.isOwn = isOwn;
	}

	public ClientDTO(Long id, String companyId, String companyName, String companyAddress, Long stateId, String city,
			String email, Long phoneNumber, String panNumber, String companyLogo, String companyCode,
			Date lastIssueDate, Long lastIssueQuantity, Boolean isActive, String gstNumber, Boolean isOwn) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.stateId = stateId;
		this.city = city;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.panNumber = panNumber;
		this.companyLogo = companyLogo;
		this.companyCode = companyCode;
		this.lastIssueDate = lastIssueDate;
		this.lastIssueQuantity = lastIssueQuantity;
		this.isActive = isActive;
		this.gstNumber = gstNumber;
		this.isOwn = isOwn;
	}

	public ClientDTO() {
		super();
	}

	public Client convertDTOToEntity(ClientDTO clientDTO) {
		return new Client(null, clientDTO.getCompanyId(), clientDTO.getCompanyName(), clientDTO.getCompanyAddress(),
				clientDTO.getState(), clientDTO.getCity(), clientDTO.getEmail(), clientDTO.getPhoneNumber(),
				clientDTO.getPanNumber(), clientDTO.getCompanyLogo(), clientDTO.getCompanyCode(),
				clientDTO.getLastIssueDate(), clientDTO.getLastIssueQuantity(), clientDTO.getIsActive(),
				clientDTO.getGstNumber(), clientDTO.getIsOwn());

	}
}
