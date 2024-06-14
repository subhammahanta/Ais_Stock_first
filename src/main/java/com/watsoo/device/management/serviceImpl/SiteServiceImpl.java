package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.RoleDTO;
import com.watsoo.device.management.dto.SiteDTO;
import com.watsoo.device.management.dto.UserDTO;
import com.watsoo.device.management.dto.UserSiteRoleDTO;
import com.watsoo.device.management.model.Role;
import com.watsoo.device.management.model.Site;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.model.UserSiteRole;
import com.watsoo.device.management.repository.SiteRepository;
import com.watsoo.device.management.repository.UserSiteRoleRepository;
import com.watsoo.device.management.service.SiteService;

@Service
@Transactional
public class SiteServiceImpl implements SiteService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private UserSiteRoleRepository userSiteRoleRepository;

	@Override
	public void setUserSitRoles(UserSiteRoleDTO userSiteRole) {
		try {
			if (userSiteRole.getUser() == null || userSiteRole.getUser().getId() <= 0) {
				throw new RuntimeException("user not found in this request");
			} else if (userSiteRole.getSite() == null || userSiteRole.getSite().getId() <= 0) {
				throw new RuntimeException("site not found in this request");
			} else if (userSiteRole.getRoles() == null || userSiteRole.getRoles().isEmpty()) {
				throw new RuntimeException("roles not found in this request");
			}

			User user = new User(userSiteRole.getUser().getId());
			Site site = new Site(userSiteRole.getSite().getId());
			UserSiteRole userSiteRoleEntity = new UserSiteRole();
			List<Role> role = new ArrayList<>();
			for (RoleDTO role2 : userSiteRole.getRoles()) {
				role.add(new Role(role2.getId()));
			}
			userSiteRoleEntity.setUser(user);
			userSiteRoleEntity.setSite(site);
			userSiteRoleEntity.setIsActive(true);

			for (Role role2 : role) {
				UserSiteRole userSiteRoleNew = userSiteRoleRepository.findByUserSiteAndRoleAndIsActive(user.getId(),
						site.getId(), role2.getId(), true);
				if (userSiteRoleNew == null) {
					UserSiteRole userSiteRoleObj = new UserSiteRole();
					userSiteRoleObj.setUser(user);
					userSiteRoleObj.setSite(site);
					userSiteRoleObj.setRole(role2);
					userSiteRoleObj.setIsActive(true);
					userSiteRoleRepository.save(userSiteRoleObj);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public void removeUserSitRoles(UserSiteRoleDTO userSiteRole) {
		try {
			if (userSiteRole.getUser() == null || userSiteRole.getUser().getId() <= 0) {
				throw new RuntimeException("user not found in this request");
			} else if (userSiteRole.getSite() == null || userSiteRole.getSite().getId() <= 0) {
				throw new RuntimeException("site not found in this request");
			}
			User user = new User(userSiteRole.getUser().getId());
			Site site = new Site(userSiteRole.getSite().getId());
			UserSiteRole userSiteRoleEntity = new UserSiteRole();
			userSiteRoleEntity.setUser(user);
			userSiteRoleEntity.setSite(site);
			userSiteRoleEntity.setIsActive(true);
			List<UserSiteRole> userSiteRoleNew = userSiteRoleRepository.findByUserSiteAndIsActive(user.getId(),
					site.getId(), true);
			if (!userSiteRoleNew.isEmpty()) {
				userSiteRoleRepository.deleteAll(userSiteRoleNew);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	@Override
	public Response<?> saveSite(SiteDTO siteDto) {
		try {
			Long maxId = siteRepository.count();
			Site siteExist = siteRepository.findByCompanyIdAndSiteName(siteDto.getCompanyId(), siteDto.getSiteName());
			if (siteExist != null)
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), HttpStatus.ALREADY_REPORTED.name());
			Site siteSave = siteDto.convertToSite();
			siteSave.setCreatedAt(new Date());
			siteSave.setSiteCode("SITE-" + (maxId + 1));
			SiteDTO siteResponse = siteRepository.save(siteSave).convertToSiteDto();

			return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), siteResponse);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> updateSite(SiteDTO siteDto) {
		try {
			Optional<Site> siteInDB = siteRepository.findById(siteDto.getId());
			Site siteExist = siteRepository.findByCompanyIdAndSiteName(siteDto.getCompanyId(), siteDto.getSiteName());
			if (siteExist != null && !siteExist.getId().equals(siteDto.getId()))
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), HttpStatus.ALREADY_REPORTED.name());
			Site siteSave = siteDto.convertToSite();
			siteSave.setCreatedAt(siteInDB.get().getCreatedAt());
			siteSave.setCreatedBy(siteInDB.get().getCreatedBy());
			siteSave.setSiteCode(siteInDB.get().getSiteCode());
			siteSave.setModifiedAt(new Date());
			SiteDTO siteResponse = siteRepository.save(siteSave).convertToSiteDto();
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), siteResponse);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> getSiteById(Long id) {
		try {
			Optional<Site> siteById = siteRepository.findById(id);
			SiteDTO siteResponse = siteById.get().convertToSiteDto();
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), siteResponse);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
		}
	}

	@Override
	public Response<?> getSiteRole(Long userId) {
		try {
			UserSiteRoleDTO response = new UserSiteRoleDTO();
			List<UserSiteRole> userSiteRole = userSiteRoleRepository.findbyUserIdAndIsActive(userId);

			Map<Long, List<UserSiteRole>> siteRoleMap = userSiteRole.stream()
					.filter(x -> x != null && x.getSite() != null && x.getSite().getId() != null)
					.collect(Collectors.groupingBy(x -> x.getSite().getId()));
			Map<Long, List<UserSiteRole>> userSiteMap = userSiteRole.stream()
					.filter(x -> x != null && x.getUser() != null && x.getUser().getId() != null)
					.collect(Collectors.groupingBy(x -> x.getUser().getId()));
			List<SiteDTO> siteDtoList = new ArrayList<>();
			for (Map.Entry<Long, List<UserSiteRole>> siteRole : siteRoleMap.entrySet()) {
				SiteDTO siteDto = new SiteDTO();
				siteDto.setId(siteRole.getKey());
				siteDto.setUserId(userId);
				siteDto.setSiteName(siteRole.getValue().get(0).getSite().getSiteName());
				List<RoleDTO> roleDtoList = siteRole.getValue().stream().filter(x -> x != null && x.getRole() != null)
						.map(x -> x.getRole().covertToDTOV2()).collect(Collectors.toList());
				siteDto.setRoles(roleDtoList);
				siteDtoList.add(siteDto);
			}
			Map<Long, List<SiteDTO>> siteDtoMap = siteDtoList.stream().filter(x -> x != null && x.getUserId() != null)
					.collect(Collectors.groupingBy(x -> x.getUserId()));
			Response<?> resp = new Response<>();
			for (Map.Entry<Long, List<UserSiteRole>> userSite : userSiteMap.entrySet()) {
				UserDTO userDto = new UserDTO();
				userDto.setId(userSite.getKey());
				if (siteDtoMap.containsKey(userDto.getId())) {
					userDto.setSiteList(siteDtoMap.get(userDto.getId()));
				}
				response.setUser(userDto);
				resp = new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), response);
			}
			return resp;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Response<?> getAllRoles(Long userId) {
		try {
			List<RoleDTO> roleDtoList = null;
			List<UserSiteRole> userSiteRole = userSiteRoleRepository.findbyUserIdAndIsActive(userId);
			Map<Long, List<UserSiteRole>> siteRoleMap = userSiteRole.stream()
					.filter(x -> x != null && x.getSite() != null && x.getSite().getId() != null)
					.collect(Collectors.groupingBy(x -> x.getSite().getId()));
			for (Map.Entry<Long, List<UserSiteRole>> siteRole : siteRoleMap.entrySet()) {
				SiteDTO siteDto = new SiteDTO();
				siteDto.setId(siteRole.getKey());
				siteDto.setUserId(userId);
				siteDto.setSiteName(siteRole.getValue().get(0).getSite().getSiteName());
				roleDtoList = siteRole.getValue().stream().filter(x -> x != null && x.getRole() != null)
						.map(x -> x.getRole().covertToDTOV2()).collect(Collectors.toList());

			}
			return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
					roleDtoList.stream().map(RoleDTO::convertDTOToEntity).collect(Collectors.toList()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
}
