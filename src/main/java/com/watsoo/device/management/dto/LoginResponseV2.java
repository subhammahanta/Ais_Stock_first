package com.watsoo.device.management.dto;

import java.util.List;
import java.util.Map;

import com.watsoo.crm.gateway.dto.MenuMstrRespDTO;
import com.watsoo.crm.gateway.dto.SubMenuMasterResponseDto;
import com.watsoo.device.management.model.UserRawDataPermission;

public class LoginResponseV2 {

	private Long id;

	private String name;

	private String email;
	private String userType;

	private List<PermissionDTO> webPermissionDTOList;
	private List<PermissionDTO> mobilePermissionDTOList;
	private CompanyDto company;
	private Long companyId;
	private String officialEmail;
	private String userProvidedPassword;
	private List<SiteDTO> sites;
	private List<MenuMstrRespDTO> menuVisiblity;
	private List<SubMenuMasterResponseDto> subMenuVisibilty;
	private String token;
	private Boolean showCommandConfigurator;
	private List<UserRawDataPermission> rawDataPermissionMap;

	public LoginResponseV2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<PermissionDTO> getWebPermissionDTOList() {
		return webPermissionDTOList;
	}

	public void setWebPermissionDTOList(List<PermissionDTO> webPermissionDTOList) {
		this.webPermissionDTOList = webPermissionDTOList;
	}

	public List<PermissionDTO> getMobilePermissionDTOList() {
		return mobilePermissionDTOList;
	}

	public void setMobilePermissionDTOList(List<PermissionDTO> mobilePermissionDTOList) {
		this.mobilePermissionDTOList = mobilePermissionDTOList;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public String getUserProvidedPassword() {
		return userProvidedPassword;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setUserProvidedPassword(String userProvidedPassword) {
		this.userProvidedPassword = userProvidedPassword;
	}

	public List<SiteDTO> getSites() {
		return sites;
	}

	public void setSites(List<SiteDTO> sites) {
		this.sites = sites;
	}

	public List<MenuMstrRespDTO> getMenuVisiblity() {
		return menuVisiblity;
	}

	public void setMenuVisiblity(List<MenuMstrRespDTO> menuVisiblity) {
		this.menuVisiblity = menuVisiblity;
	}

	public List<SubMenuMasterResponseDto> getSubMenuVisibilty() {
		return subMenuVisibilty;
	}

	public void setSubMenuVisibilty(List<SubMenuMasterResponseDto> subMenuVisibilty) {
		this.subMenuVisibilty = subMenuVisibilty;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getShowCommandConfigurator() {
		return showCommandConfigurator;
	}

	public void setShowCommandConfigurator(Boolean showCommandConfigurator) {
		this.showCommandConfigurator = showCommandConfigurator;
	}

	public List<UserRawDataPermission> getRawDataPermissionMap() {
		return rawDataPermissionMap;
	}

	public void setRawDataPermissionMap(List<UserRawDataPermission> rawDataPermissionMap) {
		this.rawDataPermissionMap = rawDataPermissionMap;
	}

}
