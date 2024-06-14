package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.SiteDTO;
import com.watsoo.device.management.dto.UserSiteRoleDTO;
import com.watsoo.device.management.model.Role;

public interface SiteService {

	Response<?> saveSite(SiteDTO siteDto);

	Response<?> updateSite(SiteDTO siteDto);

	Response<?> getSiteById(Long id);

	void setUserSitRoles(UserSiteRoleDTO userSiteRole);

	void removeUserSitRoles(UserSiteRoleDTO userSiteRole);

	Response<?> getSiteRole(Long userId);

	Response<?> getAllRoles(Long userId);

}
