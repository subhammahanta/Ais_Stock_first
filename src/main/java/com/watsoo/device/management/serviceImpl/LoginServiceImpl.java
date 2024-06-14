package com.watsoo.device.management.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.crm.gateway.dto.MenuMstrRespDTO;
import com.watsoo.crm.gateway.dto.SubMenuMasterResponseDto;
import com.watsoo.device.management.constant.Constant;
import com.watsoo.device.management.dto.LoginRequest;
import com.watsoo.device.management.dto.LoginResponse;
import com.watsoo.device.management.dto.LoginResponseV2;
import com.watsoo.device.management.dto.PermissionDTO;
import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.exception.ResourceNotFoundException;
import com.watsoo.device.management.exception.UnauthorizedException;
import com.watsoo.device.management.model.Company;
import com.watsoo.device.management.model.CredentialMaster;
import com.watsoo.device.management.model.MenuMaster;
import com.watsoo.device.management.model.Permission;
import com.watsoo.device.management.model.PermissionCategory;
import com.watsoo.device.management.model.RolePermission;
import com.watsoo.device.management.model.Site;
import com.watsoo.device.management.model.SubMenuMaster;
import com.watsoo.device.management.model.SuperAdmin;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.model.UserMenuMap;
import com.watsoo.device.management.model.UserRawDataPermission;
import com.watsoo.device.management.model.UserSiteRole;
import com.watsoo.device.management.model.UserSubMenuMap;
import com.watsoo.device.management.repository.CompanyRepository;
import com.watsoo.device.management.repository.CredentialMasterRepository;
import com.watsoo.device.management.repository.MenuMasterRepository;
import com.watsoo.device.management.repository.PermissionRepository;
import com.watsoo.device.management.repository.RolePermissionRepository;
import com.watsoo.device.management.repository.SiteRepository;
import com.watsoo.device.management.repository.SubMenuMasterRepository;
import com.watsoo.device.management.repository.SuperAdminRepository;
import com.watsoo.device.management.repository.UserMenuMapRepository;
import com.watsoo.device.management.repository.UserRawDataPermissionRepository;
import com.watsoo.device.management.repository.UserRepository;
import com.watsoo.device.management.repository.UserSiteRoleRepository;
import com.watsoo.device.management.repository.UserSubMenuMapRepository;
import com.watsoo.device.management.service.LoginService;
import com.watsoo.device.management.util.TokenUtility;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private CredentialMasterRepository credentialMstrRepo;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private MenuMasterRepository menuMasterRepository;

	@Autowired
	private SubMenuMasterRepository subMenuMasterRepository;

	@Autowired
	private SuperAdminRepository superAdminRepository;

	@Autowired
	private MenuMasterRepository menuMstrRepo;

	@Autowired
	private SubMenuMasterRepository subMenuMasterRepo;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserSiteRoleRepository userSiteRoleRepository;

	@Autowired
	private UserMenuMapRepository userMenuMapRepo;

	@Autowired
	private UserSubMenuMapRepository userSubMenuMapRepo;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@Autowired
	private UserRawDataPermissionRepository userRawDataPermissionRepo;

	@Override
	public Response<LoginResponse> login(LoginResponse loginDTO) {
		Response<LoginResponse> response = new Response<>();
		try {
			Optional<CredentialMaster> credentialMasterOptional = credentialMstrRepo.findByEmail(loginDTO.getEmail());
			if (credentialMasterOptional.isPresent()) {
				if (credentialMasterOptional.get().passwordMatches(loginDTO.getPassword())) {
					String token = TokenUtility.generateToken(credentialMasterOptional.get());
					LoginResponse loginResponse = new LoginResponse(credentialMasterOptional.get().getId(),
							credentialMasterOptional.get().getName(), credentialMasterOptional.get().getEmail(),
							new Date(), null, token);
					response.setData(loginResponse);
					response.setMessage(HttpStatus.OK.getReasonPhrase());
					response.setResponseCode(HttpStatus.OK.value());
				} else {
					response.setData(null);
					response.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
					response.setResponseCode(HttpStatus.UNAUTHORIZED.value());
				}
			} else {
				response.setData(null);
				response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
				response.setResponseCode(HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public LoginResponse changePasswordV2(LoginResponse loginRequest) {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		loginResponse.setResponsePhrase(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		Optional<CredentialMaster> credentialMasterOptional = credentialMstrRepo.findByEmail(loginRequest.getEmail());
		if (credentialMasterOptional.isPresent()) {
			CredentialMaster credentialMaster = credentialMasterOptional.get();
			if (credentialMaster.getIsActive()) {
				credentialMaster.setPassword(loginRequest.getPassword());
				credentialMstrRepo.save(credentialMaster);
			}
			loginResponse.setResponseCode(HttpStatus.OK.value());
			loginResponse.setResponsePhrase(HttpStatus.OK.getReasonPhrase());
		} else {
			loginResponse.setResponseCode(HttpStatus.NOT_FOUND.value());
			loginResponse.setResponsePhrase(HttpStatus.NOT_FOUND.getReasonPhrase());
		}
		return loginResponse;

	}

	@Override
	public Response<?> loginV2(LoginRequest loginDTO) throws Exception {
		Optional<CredentialMaster> credentialMasterOptional = credentialMstrRepo.findByEmail(loginDTO.getEmail());
		LoginResponseV2 loginResponseV2 = new LoginResponseV2();
		if (credentialMasterOptional.isPresent()) {
			if (credentialMasterOptional.get().passwordMatches(loginDTO.getPassword())) {
				loginResponseV2.setId(credentialMasterOptional.get().getId());
				loginResponseV2.setEmail(credentialMasterOptional.get().getEmail());
				loginResponseV2.setOfficialEmail(credentialMasterOptional.get().getUserId() != null
						? credentialMasterOptional.get().getUserId().getOfficialEmail()
						: null);
				loginResponseV2.setUserProvidedPassword(loginDTO.getPassword());
				loginResponseV2.setCompany(credentialMasterOptional.get().getUserId() != null
						? credentialMasterOptional.get().getUserId().getCompany().convertToCompanyDto()
						: null);
				List<Permission> permissionList = this.permissionRepository.findAll();
				loginResponseV2.setWebPermissionDTOList(permissionList.stream().filter(Permission::getIsActive)
						.filter(Permission::getForWeb).map(e -> e.convertEntityToDTO()).collect(Collectors.toList()));
				loginResponseV2.setMobilePermissionDTOList(
						permissionList.stream().filter(Permission::getIsActive).filter(Permission::getForMobile)
								.map(e -> e.convertEntityToDTO()).collect(Collectors.toList()));
				List<Site> siteBySiteOwner = siteRepository.findBySiteOwnerId(credentialMasterOptional.get().getId());
				loginResponseV2.setSites(siteBySiteOwner != null && siteBySiteOwner.size() > 0 ? siteBySiteOwner
						.stream().filter(Site::getIsActive).map(e -> e.convertToSiteDto()).collect(Collectors.toList())
						: null);
				loginResponseV2.setName(credentialMasterOptional.get().getUserId() != null
						? credentialMasterOptional.get().getUserId().getName()
						: null);
				loginResponseV2.setUserType(credentialMasterOptional.get().getUserId() != null
						&& credentialMasterOptional.get().getUserId().getUserType() != null
								? credentialMasterOptional.get().getUserId().getUserType().getName()
								: null);

				List<MenuMaster> menuMasterList = this.menuMasterRepository.findAll();
				List<Long> visibleMenuIds = menuMasterList.stream().filter(MenuMaster::getIsAdminVisible)
						.map(e -> e.getId()).collect(Collectors.toList());
				List<SubMenuMaster> subMenuMasterList = this.subMenuMasterRepository.findAllByMenuIdIn(visibleMenuIds);
				loginResponseV2.setMenuVisiblity(
						menuMasterList.stream().map(e -> e.convertToMenuMasterRespDto()).collect(Collectors.toList()));
				loginResponseV2.setSubMenuVisibilty(subMenuMasterList.stream()
						.map(e -> e.convertToSubMenuMasterRespDto()).collect(Collectors.toList()));
				return new Response<>(HttpStatus.OK.value(), "Successfully Logedin", loginResponseV2);
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid Password", null);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid Credentials", null);

		}
	}

	@Override
	public Response<?> loginV3(LoginRequest loginRequest) {

		LoginResponseV2 loginResponse = new LoginResponseV2();

		Optional<CredentialMaster> credentialMasterOptional = credentialMstrRepo.findByEmail(loginRequest.getEmail());

		if (credentialMasterOptional.isPresent()) {
			loginResponse.setEmail(loginRequest.getEmail());
			loginResponse.setUserProvidedPassword(loginRequest.getPassword());
			CredentialMaster credentialMaster = credentialMasterOptional.get();

			if (credentialMaster.getIsActive()) {
				if (credentialMaster.passwordMatches(loginRequest.getPassword())) {
					if (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.SUPER_ADMIN)) {
						Optional<SuperAdmin> superAdminOptional = superAdminRepository
								.findById(credentialMaster.getUserId().getId());
						if (superAdminOptional.isPresent()) {

							SuperAdmin superAdmin = superAdminOptional.get();
							loginResponse.setId(superAdmin.getId());
							// TODO: remove company Id later
//								loginResponse.setCompanyId(superAdmin.getId());
							loginResponse.setEmail(superAdmin.getEmail());
							loginResponse.setName(superAdmin.getName());
							loginResponse.setUserType(credentialMaster.getUserTypeId().getName());

							List<Permission> permissionList = permissionRepository.findAll();
							loginResponse.setWebPermissionDTOList(permissionList.stream()
									.filter(Permission::getIsActive).filter(Permission::getForWeb)
									.map(Permission::convertEntityToDTO).collect(Collectors.toList()));
							loginResponse.setMobilePermissionDTOList(permissionList.stream()
									.filter(Permission::getIsActive).filter(Permission::getForMobile)
									.map(Permission::convertEntityToDTO).collect(Collectors.toList()));

							List<MenuMstrRespDTO> menuRespDto = new ArrayList<>();
							List<SubMenuMasterResponseDto> subMenuRespDto = new ArrayList<>();
							try {
								List<MenuMaster> menuMstr = menuMstrRepo.findAll();
								for (MenuMaster userMenuMap : menuMstr) {
									MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMap.getId(),
											userMenuMap.getName(), true, userMenuMap.getLink());
									menuRespDto.add(dto);
								}
								List<Long> visibleMenuIds = menuRespDto.stream()
										.filter(x -> x.getIsVisible().toString().equals("true")).map(y -> y.getId())
										.collect(Collectors.toList());
								List<SubMenuMaster> subMenuMstr = subMenuMasterRepo.findAllByMenuIdIn(visibleMenuIds);
								for (SubMenuMaster userSubMenuMap : subMenuMstr) {
									SubMenuMasterResponseDto subMenudto = new SubMenuMasterResponseDto(
											userSubMenuMap.getId(), userSubMenuMap.getName(), true,
											userSubMenuMap.getMenuId(), userSubMenuMap.getMenuName(),
											userSubMenuMap.getLink());
									subMenuRespDto.add(subMenudto);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							loginResponse.setMenuVisiblity(menuRespDto);
							loginResponse.setSubMenuVisibilty(subMenuRespDto);

						} else {
							throw new ResourceNotFoundException("User not found.");
						}
					} else {
						if (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.ADMIN)) {
							Optional<Company> companyOptional = companyRepository
									.findById(credentialMaster.getUserId().getId());
							if (companyOptional.isPresent()) {
								Company company = companyOptional.get();
								loginResponse.setId(credentialMaster.getId());
								loginResponse.setEmail(credentialMaster.getEmail());
								loginResponse.setName(credentialMaster.getName());
								loginResponse.setUserType(credentialMaster.getUserTypeId().getName());
								loginResponse.setCompanyId(company.getId());
								List<Permission> permissionList = permissionRepository.findAll();
								loginResponse.setWebPermissionDTOList(permissionList.stream()
										.filter(Permission::getIsActive).filter(Permission::getForWeb)
										.map(Permission::convertEntityToDTO).collect(Collectors.toList()));
								loginResponse.setMobilePermissionDTOList(permissionList.stream()
										.filter(Permission::getIsActive).filter(Permission::getForMobile)
										.map(Permission::convertEntityToDTO).collect(Collectors.toList()));
								// List<Site> siteList = siteRepository.findAll(company.getId(), null, null,
								// null, true);
//									List<Site> siteList = siteRepository.findAll(company.getId(), null, null, null,
//											false);
//									 List<Site> siteList = siteRepository.findAll(company.getId(), null, null, null, false,null);
//									loginResponse.setSites(siteList.stream().map(site -> {
//										SiteDTO siteDTO = site.convertToDTO(site);
//										return siteDTO;
//									}).collect(Collectors.toList()));

								List<MenuMstrRespDTO> menuRespDto = new ArrayList<>();
								List<SubMenuMasterResponseDto> subMenuRespDto = new ArrayList<>();
								try {
									List<MenuMaster> menuMstr = menuMstrRepo.findAll();
									for (MenuMaster userMenuMap : menuMstr) {
										if (!(userMenuMap.getIsAdminVisible() != null
												&& !userMenuMap.getIsAdminVisible())) {
											MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMap.getId(),
													userMenuMap.getName(), true, userMenuMap.getLink());
											menuRespDto.add(dto);
										} else {
											MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMap.getId(),
													userMenuMap.getName(), false);
											menuRespDto.add(dto);
										}
									}
									List<Long> visibleMenuIds = menuRespDto.stream()
											.filter(x -> x.getIsVisible().toString().equals("true")).map(y -> y.getId())
											.collect(Collectors.toList());
									List<SubMenuMaster> subMenuMstr = subMenuMasterRepo
											.findAllByMenuIdIn(visibleMenuIds);
									for (SubMenuMaster userSubMenuMap : subMenuMstr) {
										SubMenuMasterResponseDto subMenudto = new SubMenuMasterResponseDto(
												userSubMenuMap.getId(), userSubMenuMap.getName(), true,
												userSubMenuMap.getMenuId(), userSubMenuMap.getMenuName(),
												userSubMenuMap.getLink());
										subMenuRespDto.add(subMenudto);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
								loginResponse.setMenuVisiblity(menuRespDto);
								loginResponse.setSubMenuVisibilty(subMenuRespDto);

							} else {
								throw new ResourceNotFoundException("User not found.");
							}
						} else {
							if (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.USER)) {
								Optional<User> userOptional = userRepository
										.findById(credentialMaster.getUserId().getId());
								if (userOptional.isPresent()) {

									User user = userOptional.get();
									loginResponse.setId(user.getId());
									loginResponse.setEmail(user.getEmail());
									loginResponse.setName(user.getName());
									loginResponse.setUserType(credentialMaster.getUserTypeId().getName());
									loginResponse.setCompanyId(user.getCompany().getId());
//										Optional<Company> companyOptional = companyRepository
//												.findById(user.getCompany().getId());
//										companyOptional.ifPresent(
//												company -> loginResponse.setCompany(company.convertToDTO(company)));
									Set<Long> webPermissionId = new HashSet<>();
									Set<Long> mobilePermissionId = new HashSet<>();
									List<PermissionDTO> webPermissionDTOList = new ArrayList<>();
									List<PermissionDTO> mobilePermissionDTOList = new ArrayList<>();

									List<Site> siteList = user.getSites();
									if (!siteList.isEmpty()) {
										List<Long> filteredSiteIds = siteList.stream().map(o -> o.getId())
												.collect(Collectors.toList());
										List<UserSiteRole> userAllSiteRoleList = userSiteRoleRepository
												.findByUser_idAndSite_idInAndIsActiveTrue(user.getId(),
														filteredSiteIds);

										if (userAllSiteRoleList != null && userAllSiteRoleList.size() > 0) {

											List<Long> filteredRoleIds = userAllSiteRoleList.stream()
													.filter(o -> o != null && o.getRole() != null
															&& o.getRole().getId() != null && o.getRole().getIsActive())
													.map(x -> x.getRole().getId()).collect(Collectors.toList());

											/////////// MENU VISIBILITY CHECK//////////////////////

											List<MenuMstrRespDTO> menuRespDto = new ArrayList<>();
											List<UserMenuMap> accessibleMenuList = new ArrayList<>();
											List<UserMenuMap> userSpecificMenuList = new ArrayList<>();

											List<SubMenuMasterResponseDto> subMenuRespDto = new ArrayList<>();
											List<UserSubMenuMap> accessibleSubMenuList = new ArrayList<>();
											List<UserSubMenuMap> userSpecificSubMenuList = new ArrayList<>();
											try {

												List<MenuMaster> menuMstrList = menuMstrRepo.findAll();
												accessibleMenuList = userMenuMapRepo.findByRoleIds(filteredRoleIds);
												accessibleMenuList = accessibleMenuList.stream()
														.filter(distinctByKey(pr -> Arrays.asList(pr.getId())))
														.collect(Collectors.toList());
												userSpecificMenuList = userMenuMapRepo
														.findByUserId(credentialMaster.getUserId().getId());

												for (UserMenuMap userSpecificMenuMap : userSpecificMenuList) {
													Boolean isExist = false;

													for (Iterator<UserMenuMap> itr = accessibleMenuList.iterator(); itr
															.hasNext();) {
														UserMenuMap userAccessibleMenuMap = itr.next();
														if (userSpecificMenuMap.getMenuMstr()
																.getId() == userAccessibleMenuMap.getMenuMstr().getId()
																&& userSpecificMenuMap.getIsConsiderable() == false) {
															synchronized (this) {
																itr.remove();
															}
															isExist = true;
														} else if (userSpecificMenuMap.getMenuMstr()
																.getId() == userAccessibleMenuMap.getMenuMstr().getId()
																&& userAccessibleMenuMap.getIsActive()) {

														}
														isExist = true;
													}
													if (!isExist && userSpecificMenuMap.getIsConsiderable()) {
														accessibleMenuList.add(userSpecificMenuMap);
													}
												}

												for (MenuMaster userMenuMaster : menuMstrList) {
													UserMenuMap accessible = accessibleMenuList.stream()
															.filter(o -> o.getMenuMstr().getId()
																	.intValue() == userMenuMaster.getId().intValue())
															.findAny().orElse(null);
													MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMaster.getId(),
															userMenuMaster.getName(), false, userMenuMaster.getLink());
													if (accessible != null) {
														dto.setIsVisible(true);
													}
													menuRespDto.add(dto);
												}

												List<Long> visibleMenuIds = menuRespDto.stream()
														.filter(x -> x.getIsVisible().toString().equals("true"))
														.map(y -> y.getId()).collect(Collectors.toList());
												List<SubMenuMaster> subMenuMstr = subMenuMasterRepo
														.findAllByMenuIdIn(visibleMenuIds);
												accessibleSubMenuList = userSubMenuMapRepo
														.findByRoleIds(filteredRoleIds);
												accessibleSubMenuList = accessibleSubMenuList.stream()
														.filter(distinctByKey(pr -> Arrays.asList(pr.getId())))
														.collect(Collectors.toList());
												userSpecificSubMenuList = userSubMenuMapRepo
														.findByUserId(credentialMaster.getUserId().getId());

												for (UserSubMenuMap userSpecificSubMenuMap : userSpecificSubMenuList) {
													Boolean isExist = false;
													for (Iterator<UserSubMenuMap> itr = accessibleSubMenuList
															.iterator(); itr.hasNext();) {
														UserSubMenuMap userAccessibleSubMenuMap = itr.next();
														if (userSpecificSubMenuMap.getSubMenuMstr()
																.getId() == userAccessibleSubMenuMap.getSubMenuMstr()
																		.getId()
																&& userSpecificSubMenuMap
																		.getIsConsiderable() == false) {
															synchronized (this) {
																itr.remove();
															}
															isExist = true;
														} else if (userSpecificSubMenuMap.getSubMenuMstr()
																.getId() == userAccessibleSubMenuMap.getSubMenuMstr()
																		.getId()
																&& userAccessibleSubMenuMap.getIsActive()) {
														}
														isExist = true;
													}
													if (!isExist && userSpecificSubMenuMap.getIsConsiderable()) {
														accessibleSubMenuList.add(userSpecificSubMenuMap);
													}
												}

												for (SubMenuMaster userSubMenuMaster : subMenuMstr) {
													UserSubMenuMap accessible = accessibleSubMenuList.stream()
															.filter(o -> o.getSubMenuMstr().getId()
																	.intValue() == userSubMenuMaster.getId().intValue())
															.findAny().orElse(null);
													SubMenuMasterResponseDto subMenuDto = new SubMenuMasterResponseDto(
															userSubMenuMaster.getId(), userSubMenuMaster.getName(),
															false, userSubMenuMaster.getMenuId(),
															userSubMenuMaster.getMenuName(),
															userSubMenuMaster.getLink());
													if (accessible != null) {
														subMenuDto.setIsVisible(true);
													}
													subMenuRespDto.add(subMenuDto);
												}

											} catch (Exception e) {
												e.printStackTrace();
											}
											loginResponse.setMenuVisiblity(menuRespDto);
											loginResponse.setSubMenuVisibilty(subMenuRespDto);

											/////////////////// MENU VISIBILITY CHECK////////////////////////////

											List<RolePermission> rolePermissionList = rolePermissionRepository
													.findByRoleIds(filteredRoleIds);
											if (rolePermissionList != null && rolePermissionList.size() > 0) {
//													Map<Long, List<RolePermission>> roleListMap = roleList.stream()
//															.collect(Collectors.groupingBy(o -> o.getRole().getId()));

//														List<RolePermission> rolePermissionList = roleListMap
//																.get(role.getId());

												List<Permission> webPermission = rolePermissionList.stream()
														.filter(RolePermission::getForWeb)
														.map(RolePermission::getPermission).filter(Objects::nonNull)
														.collect(Collectors.toList());

												List<Permission> mobilePermission = rolePermissionList.stream()
														.filter(RolePermission::getForMobile)
														.map(RolePermission::getPermission).filter(Objects::nonNull)
														.collect(Collectors.toList());

												List<PermissionCategory> webPermissionCategory = rolePermissionList
														.stream().filter(RolePermission::getForWeb)
														.map(RolePermission::getPermissionCatagory)
														.filter(Objects::nonNull).collect(Collectors.toList());

												List<PermissionCategory> mobilePermissionCategory = rolePermissionList
														.stream().filter(RolePermission::getForMobile)
														.map(RolePermission::getPermissionCatagory)
														.filter(Objects::nonNull).collect(Collectors.toList());

//														for (PermissionCategory permissionCategory : webPermissionCategory) {
//															webPermission.addAll(
//																	permissionRepository.getByPermissionCategoryId(
//																			permissionCategory.getId()));
//														}
//
//														for (PermissionCategory permissionCategory : mobilePermissionCategory) {
//															mobilePermission.addAll(
//																	permissionRepository.getByPermissionCategoryId(
//																			permissionCategory.getId()));
//														}

												for (Permission permission : webPermission) {
													if (!webPermissionId.contains(permission.getId())) {
														webPermissionId.add(permission.getId());
														webPermissionDTOList.add(permission.convertEntityToDTO());
													}
												}

												for (Permission permission : mobilePermission) {
													if (!mobilePermissionId.contains(permission.getId())) {
														mobilePermissionId.add(permission.getId());
														mobilePermissionDTOList.add(permission.convertEntityToDTO());
													}
												}

											}

										}
										loginResponse.setSites(siteList.stream().map(site -> site.convertToSiteDto())
												.collect(Collectors.toList()));
									}
									loginResponse.setWebPermissionDTOList(webPermissionDTOList);
									loginResponse.setMobilePermissionDTOList(mobilePermissionDTOList);

								} else {
									throw new ResourceNotFoundException("User not found.");
								}
							} else {
								throw new ResourceNotFoundException("User not found.");
							}
						}
					}
				} else {
					throw new UnauthorizedException("INVALID_CREDENTIALS");
				} /*
					 * else { if
					 * (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.USER))
					 * { Optional<Configuration> configuration = configurationRepository
					 * .findByKey(Constant.FAIL_LOGIN_LOCK_RELEASE_INMIN); Integer failedCount = 1;
					 * Integer maxFailedCount = 3; if (configuration.isPresent()) { Integer
					 * loginLockReleaseMin = Integer.valueOf(configuration.get().getValue());
					 * Optional<User> userOptional =
					 * userRepository.findById(credentialMaster.getUserId()); if
					 * (userOptional.isPresent()) { User user = userOptional.get(); if
					 * (user.getFailedLoginTimeStamp() != null) { Optional<Configuration>
					 * maxLoginAttemptConfig = configurationRepository
					 * .findByKey(Constant.MAX_LOGIN_ATTEMPT); if(maxLoginAttemptConfig.isPresent())
					 * { maxFailedCount = Integer.valueOf(maxLoginAttemptConfig.get().getValue()); }
					 * failedCount = user.getFailedCount() != null ? user.getFailedCount() : 0; if
					 * (new Date().before(DateUtil.addMinutesToJavaUtilDate(
					 * user.getFailedLoginTimeStamp(), loginLockReleaseMin))) {
					 * user.setFailedCount(failedCount+1); if(user.getFailedCount() != null &&
					 * user.getFailedCount() <= maxFailedCount) { userRepository.saveAndFlush(user);
					 * } else { throw new
					 * UnauthorizedException("You’ve reached the maximum login attempts.try after sometime"
					 * ); } } else { user.setFailedCount(1); user.setFailedLoginTimeStamp(new
					 * Date()); userRepository.saveAndFlush(user); } } else {
					 * user.setFailedCount(failedCount); user.setFailedLoginTimeStamp(new Date());
					 * userRepository.saveAndFlush(user); } } } } else if
					 * (credentialMaster.getUserType().getName().equalsIgnoreCase(Constant.ADMIN)) {
					 * Optional<Configuration> configuration = configurationRepository
					 * .findByKey(Constant.FAIL_LOGIN_LOCK_RELEASE_INMIN); Integer failedCount = 1;
					 * Integer maxFailedCount = 3; if (configuration.isPresent()) { Integer
					 * loginLockReleaseMin = Integer.valueOf(configuration.get().getValue());
					 * Optional<Company> userOptional =
					 * companyRepository.findById(credentialMaster.getUserId()); if
					 * (userOptional.isPresent()) { Company user = userOptional.get(); if
					 * (user.getFailedLoginTimeStamp() != null) { Optional<Configuration>
					 * maxLoginAttemptConfig = configurationRepository
					 * .findByKey(Constant.MAX_LOGIN_ATTEMPT); if(maxLoginAttemptConfig.isPresent())
					 * { maxFailedCount = Integer.valueOf(maxLoginAttemptConfig.get().getValue()); }
					 * failedCount = user.getFailedCount() != null ? user.getFailedCount() : 0; if
					 * (new Date().before(DateUtil.addMinutesToJavaUtilDate(
					 * user.getFailedLoginTimeStamp(), loginLockReleaseMin))) {
					 * user.setFailedCount(failedCount+1); if(user.getFailedCount() != null &&
					 * user.getFailedCount() <= maxFailedCount) {
					 * companyRepository.saveAndFlush(user); } else { throw new
					 * UnauthorizedException("You’ve reached the maximum login attempts.try after sometime"
					 * ); } } else { user.setFailedCount(1); user.setFailedLoginTimeStamp(new
					 * Date()); companyRepository.saveAndFlush(user); } } else {
					 * user.setFailedCount(failedCount); user.setFailedLoginTimeStamp(new Date());
					 * companyRepository.saveAndFlush(user); } } } } throw new
					 * UnauthorizedException("INVALID_CREDENTIALS"); }
					 */
			} else {
				throw new UnauthorizedException("INVALID_CREDENTIALS");
			}
		} else {
			throw new UnauthorizedException("INVALID_CREDENTIALS");
		}
		return new Response<>(HttpStatus.OK.value(), "Successfully Logedin", loginResponse);
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@Override
	public Response<?> loginV4(LoginRequest loginRequest) {

		LoginResponseV2 loginResponse = new LoginResponseV2();

		Optional<CredentialMaster> credentialMasterOptional = credentialMstrRepo.findByEmail(loginRequest.getEmail());

		if (credentialMasterOptional.isPresent()) {
			loginResponse.setEmail(loginRequest.getEmail());
			loginResponse.setUserProvidedPassword(loginRequest.getPassword());
			CredentialMaster credentialMaster = credentialMasterOptional.get();

			if (credentialMaster.getIsActive()) {
				if (credentialMaster.passwordMatches(loginRequest.getPassword())) {
					if (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.SUPER_ADMIN)) {
						Optional<SuperAdmin> superAdminOptional = superAdminRepository
								.findById(credentialMaster.getUserId().getId());
						if (superAdminOptional.isPresent()) {

							SuperAdmin superAdmin = superAdminOptional.get();
							loginResponse.setId(superAdmin.getId());
							// TODO: remove company Id later
//								loginResponse.setCompanyId(superAdmin.getId());
							loginResponse.setEmail(superAdmin.getEmail());
							loginResponse.setName(superAdmin.getName());
							loginResponse.setUserType(credentialMaster.getUserTypeId().getName());
							loginResponse.setShowCommandConfigurator(true);
							
							List<Permission> permissionList = permissionRepository.findAll();
							loginResponse.setWebPermissionDTOList(permissionList.stream()
									.filter(Permission::getIsActive).filter(Permission::getForWeb)
									.map(Permission::convertEntityToDTO).collect(Collectors.toList()));
							loginResponse.setMobilePermissionDTOList(permissionList.stream()
									.filter(Permission::getIsActive).filter(Permission::getForMobile)
									.map(Permission::convertEntityToDTO).collect(Collectors.toList()));

							List<MenuMstrRespDTO> menuRespDto = new ArrayList<>();
							List<SubMenuMasterResponseDto> subMenuRespDto = new ArrayList<>();
							try {
								List<MenuMaster> menuMstr = menuMstrRepo.findAll();
								for (MenuMaster userMenuMap : menuMstr) {
									MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMap.getId(),
											userMenuMap.getName(), true, userMenuMap.getLink());
									menuRespDto.add(dto);
								}
								List<Long> visibleMenuIds = menuRespDto.stream()
										.filter(x -> x.getIsVisible().toString().equals("true")).map(y -> y.getId())
										.collect(Collectors.toList());
								List<SubMenuMaster> subMenuMstr = subMenuMasterRepo.findAllByMenuIdIn(visibleMenuIds);
								for (SubMenuMaster userSubMenuMap : subMenuMstr) {
									SubMenuMasterResponseDto subMenudto = new SubMenuMasterResponseDto(
											userSubMenuMap.getId(), userSubMenuMap.getName(), true,
											userSubMenuMap.getMenuId(), userSubMenuMap.getMenuName(),
											userSubMenuMap.getLink());
									subMenuRespDto.add(subMenudto);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							loginResponse.setMenuVisiblity(menuRespDto);
							loginResponse.setSubMenuVisibilty(subMenuRespDto);

						} else {
							throw new ResourceNotFoundException("User not found.");
						}
					} else {
						if (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.ADMIN)) {
							Optional<Company> companyOptional = companyRepository
									.findById(credentialMaster.getUserId().getId());
							if (companyOptional.isPresent()) {
								Company company = companyOptional.get();
								loginResponse.setShowCommandConfigurator(true);
								loginResponse.setId(credentialMaster.getId());
								loginResponse.setEmail(credentialMaster.getEmail());
								loginResponse.setName(credentialMaster.getName());
								loginResponse.setUserType(credentialMaster.getUserTypeId().getName());
								loginResponse.setCompanyId(company.getId());
								List<Permission> permissionList = permissionRepository.findAll();
								loginResponse.setWebPermissionDTOList(permissionList.stream()
										.filter(Permission::getIsActive).filter(Permission::getForWeb)
										.map(Permission::convertEntityToDTO).collect(Collectors.toList()));
								loginResponse.setMobilePermissionDTOList(permissionList.stream()
										.filter(Permission::getIsActive).filter(Permission::getForMobile)
										.map(Permission::convertEntityToDTO).collect(Collectors.toList()));
								// List<Site> siteList = siteRepository.findAll(company.getId(), null, null,
								// null, true);
//									List<Site> siteList = siteRepository.findAll(company.getId(), null, null, null,
//											false);
//									 List<Site> siteList = siteRepository.findAll(company.getId(), null, null, null, false,null);
//									loginResponse.setSites(siteList.stream().map(site -> {
//										SiteDTO siteDTO = site.convertToDTO(site);
//										return siteDTO;
//									}).collect(Collectors.toList()));

								List<MenuMstrRespDTO> menuRespDto = new ArrayList<>();
								List<SubMenuMasterResponseDto> subMenuRespDto = new ArrayList<>();
								try {
									List<MenuMaster> menuMstr = menuMstrRepo.findAll();
									for (MenuMaster userMenuMap : menuMstr) {
										if (!(userMenuMap.getIsAdminVisible() != null
												&& !userMenuMap.getIsAdminVisible())) {
											MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMap.getId(),
													userMenuMap.getName(), true, userMenuMap.getLink());
											menuRespDto.add(dto);
										} else {
											MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMap.getId(),
													userMenuMap.getName(), false);
											menuRespDto.add(dto);
										}
									}
									List<Long> visibleMenuIds = menuRespDto.stream()
											.filter(x -> x.getIsVisible().toString().equals("true")).map(y -> y.getId())
											.collect(Collectors.toList());
									List<SubMenuMaster> subMenuMstr = subMenuMasterRepo
											.findAllByMenuIdIn(visibleMenuIds);
									for (SubMenuMaster userSubMenuMap : subMenuMstr) {
										// added
										if (userSubMenuMap.getIsAdminVisible() == null) {
											SubMenuMasterResponseDto subMenudto = new SubMenuMasterResponseDto(
													userSubMenuMap.getId(), userSubMenuMap.getName(), true,
													userSubMenuMap.getMenuId(), userSubMenuMap.getMenuName(),
													userSubMenuMap.getLink());
											subMenuRespDto.add(subMenudto);
										} else {
											if (userSubMenuMap.getIsAdminVisible()) {
												SubMenuMasterResponseDto subMenudto = new SubMenuMasterResponseDto(
														userSubMenuMap.getId(), userSubMenuMap.getName(), true,
														userSubMenuMap.getMenuId(), userSubMenuMap.getMenuName(),
														userSubMenuMap.getLink());
												subMenuRespDto.add(subMenudto);
											} else {
												SubMenuMasterResponseDto subMenudto = new SubMenuMasterResponseDto(
														userSubMenuMap.getId(), userSubMenuMap.getName(), false,
														userSubMenuMap.getMenuId(), userSubMenuMap.getMenuName(),
														userSubMenuMap.getLink());
												subMenuRespDto.add(subMenudto);
											}
										}
//										SubMenuMasterResponseDto subMenudto = new SubMenuMasterResponseDto(
//												userSubMenuMap.getId(), userSubMenuMap.getName(), true,
//												userSubMenuMap.getMenuId(), userSubMenuMap.getMenuName(),userSubMenuMap.getLink());
//										subMenuRespDto.add(subMenudto);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
								loginResponse.setMenuVisiblity(menuRespDto);
								loginResponse.setSubMenuVisibilty(subMenuRespDto);

							} else {
								throw new ResourceNotFoundException("User not found.");
							}
						} else {
							if (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.USER)) {
								Optional<User> userOptional = userRepository
										.findById(credentialMaster.getUserId().getId());
								if (userOptional.isPresent()) {
									
									User user = userOptional.get();
									loginResponse.setId(user.getId());
									loginResponse.setEmail(user.getEmail());
									loginResponse.setName(user.getName());
									loginResponse.setUserType(credentialMaster.getUserTypeId().getName());
									loginResponse.setCompanyId(user.getCompany().getId());
									loginResponse.setShowCommandConfigurator(
											user.getShowCmdConfigurator() != null && user.getShowCmdConfigurator()
													? user.getShowCmdConfigurator()
													: false);
//										Optional<Company> companyOptional = companyRepository
//												.findById(user.getCompany().getId());
//										companyOptional.ifPresent(
//												company -> loginResponse.setCompany(company.convertToDTO(company)));
									Set<Long> webPermissionId = new HashSet<>();
									Set<Long> mobilePermissionId = new HashSet<>();
									List<PermissionDTO> webPermissionDTOList = new ArrayList<>();
									List<PermissionDTO> mobilePermissionDTOList = new ArrayList<>();
									List<UserRawDataPermission> userRawDataPermission = userRawDataPermissionRepo.getByUserId(user.getId());
									if(userRawDataPermission != null && userRawDataPermission.size() > 0) {
										loginResponse.setRawDataPermissionMap(userRawDataPermission);
									}
									List<Site> siteList = user.getSites();
									if (!siteList.isEmpty()) {
										List<Long> filteredSiteIds = siteList.stream().map(o -> o.getId())
												.collect(Collectors.toList());
										List<UserSiteRole> userAllSiteRoleList = userSiteRoleRepository
												.findByUser_idAndSite_idInAndIsActiveTrue(user.getId(),
														filteredSiteIds);

										if (userAllSiteRoleList != null && userAllSiteRoleList.size() > 0) {

											List<Long> filteredRoleIds = userAllSiteRoleList.stream()
													.filter(o -> o != null && o.getRole() != null
															&& o.getRole().getId() != null && o.getRole().getIsActive())
													.map(x -> x.getRole().getId()).collect(Collectors.toList());

											/////////// MENU VISIBILITY CHECK//////////////////////

											List<MenuMstrRespDTO> menuRespDto = new ArrayList<>();
											List<UserMenuMap> accessibleMenuList = new ArrayList<>();
											List<UserMenuMap> userSpecificMenuList = new ArrayList<>();

											List<SubMenuMasterResponseDto> subMenuRespDto = new ArrayList<>();
											List<UserSubMenuMap> accessibleSubMenuList = new ArrayList<>();
											List<UserSubMenuMap> userSpecificSubMenuList = new ArrayList<>();
											try {

												List<MenuMaster> menuMstrList = menuMstrRepo.findAll();
												accessibleMenuList = userMenuMapRepo.findByRoleIds(filteredRoleIds);
												accessibleMenuList = accessibleMenuList.stream()
														.filter(distinctByKey(pr -> Arrays.asList(pr.getId())))
														.collect(Collectors.toList());
												userSpecificMenuList = userMenuMapRepo
														.findByUserId(credentialMaster.getUserId().getId());

												for (UserMenuMap userSpecificMenuMap : userSpecificMenuList) {
													Boolean isExist = false;

													for (Iterator<UserMenuMap> itr = accessibleMenuList.iterator(); itr
															.hasNext();) {
														UserMenuMap userAccessibleMenuMap = itr.next();
														if (userSpecificMenuMap.getMenuMstr()
																.getId() == userAccessibleMenuMap.getMenuMstr().getId()
																&& userSpecificMenuMap.getIsConsiderable() == false) {
															synchronized (this) {
																itr.remove();
															}
															isExist = true;
														} else if (userSpecificMenuMap.getMenuMstr()
																.getId() == userAccessibleMenuMap.getMenuMstr().getId()
																&& userAccessibleMenuMap.getIsActive()) {

														}
														isExist = true;
													}
													if (!isExist && userSpecificMenuMap.getIsConsiderable()) {
														accessibleMenuList.add(userSpecificMenuMap);
													}
												}

												for (MenuMaster userMenuMaster : menuMstrList) {
													UserMenuMap accessible = accessibleMenuList.stream()
															.filter(o -> o.getMenuMstr().getId()
																	.intValue() == userMenuMaster.getId().intValue())
															.findAny().orElse(null);
													MenuMstrRespDTO dto = new MenuMstrRespDTO(userMenuMaster.getId(),
															userMenuMaster.getName(), false, userMenuMaster.getLink());
													if (accessible != null) {
														dto.setIsVisible(true);
													}
													menuRespDto.add(dto);
												}

												List<Long> visibleMenuIds = menuRespDto.stream()
														.filter(x -> x.getIsVisible().toString().equals("true"))
														.map(y -> y.getId()).collect(Collectors.toList());
												List<SubMenuMaster> subMenuMstr = subMenuMasterRepo
														.findAllByMenuIdIn(visibleMenuIds);
												accessibleSubMenuList = userSubMenuMapRepo
														.findByRoleIds(filteredRoleIds);
												accessibleSubMenuList = accessibleSubMenuList.stream()
														.filter(distinctByKey(pr -> Arrays.asList(pr.getId())))
														.collect(Collectors.toList());
												userSpecificSubMenuList = userSubMenuMapRepo
														.findByUserId(credentialMaster.getUserId().getId());

												for (UserSubMenuMap userSpecificSubMenuMap : userSpecificSubMenuList) {
													Boolean isExist = false;
													for (Iterator<UserSubMenuMap> itr = accessibleSubMenuList
															.iterator(); itr.hasNext();) {
														UserSubMenuMap userAccessibleSubMenuMap = itr.next();
														if (userSpecificSubMenuMap.getSubMenuMstr()
																.getId() == userAccessibleSubMenuMap.getSubMenuMstr()
																		.getId()
																&& userSpecificSubMenuMap
																		.getIsConsiderable() == false) {
															synchronized (this) {
																itr.remove();
															}
															isExist = true;
														} else if (userSpecificSubMenuMap.getSubMenuMstr()
																.getId() == userAccessibleSubMenuMap.getSubMenuMstr()
																		.getId()
																&& userAccessibleSubMenuMap.getIsActive()) {
														}
														isExist = true;
													}
													if (!isExist && userSpecificSubMenuMap.getIsConsiderable()) {
														accessibleSubMenuList.add(userSpecificSubMenuMap);
													}
												}

												for (SubMenuMaster userSubMenuMaster : subMenuMstr) {
													UserSubMenuMap accessible = accessibleSubMenuList.stream()
															.filter(o -> o.getSubMenuMstr().getId()
																	.intValue() == userSubMenuMaster.getId().intValue())
															.findAny().orElse(null);
													SubMenuMasterResponseDto subMenuDto = new SubMenuMasterResponseDto(
															userSubMenuMaster.getId(), userSubMenuMaster.getName(),
															false, userSubMenuMaster.getMenuId(),
															userSubMenuMaster.getMenuName(),
															userSubMenuMaster.getLink());
													if (accessible != null) {
														subMenuDto.setIsVisible(true);
													}
													subMenuRespDto.add(subMenuDto);
												}

											} catch (Exception e) {
												e.printStackTrace();
											}
											loginResponse.setMenuVisiblity(menuRespDto);
											loginResponse.setSubMenuVisibilty(subMenuRespDto);

											/////////////////// MENU VISIBILITY CHECK////////////////////////////

											List<RolePermission> rolePermissionList = rolePermissionRepository
													.findByRoleIds(filteredRoleIds);
											if (rolePermissionList != null && rolePermissionList.size() > 0) {
//													Map<Long, List<RolePermission>> roleListMap = roleList.stream()
//															.collect(Collectors.groupingBy(o -> o.getRole().getId()));

//														List<RolePermission> rolePermissionList = roleListMap
//																.get(role.getId());

												List<Permission> webPermission = rolePermissionList.stream()
														.filter(RolePermission::getForWeb)
														.map(RolePermission::getPermission).filter(Objects::nonNull)
														.collect(Collectors.toList());

												List<Permission> mobilePermission = rolePermissionList.stream()
														.filter(RolePermission::getForMobile)
														.map(RolePermission::getPermission).filter(Objects::nonNull)
														.collect(Collectors.toList());

												List<PermissionCategory> webPermissionCategory = rolePermissionList
														.stream().filter(RolePermission::getForWeb)
														.map(RolePermission::getPermissionCatagory)
														.filter(Objects::nonNull).collect(Collectors.toList());

												List<PermissionCategory> mobilePermissionCategory = rolePermissionList
														.stream().filter(RolePermission::getForMobile)
														.map(RolePermission::getPermissionCatagory)
														.filter(Objects::nonNull).collect(Collectors.toList());

//														for (PermissionCategory permissionCategory : webPermissionCategory) {
//															webPermission.addAll(
//																	permissionRepository.getByPermissionCategoryId(
//																			permissionCategory.getId()));
//														}
//
//														for (PermissionCategory permissionCategory : mobilePermissionCategory) {
//															mobilePermission.addAll(
//																	permissionRepository.getByPermissionCategoryId(
//																			permissionCategory.getId()));
//														}

												for (Permission permission : webPermission) {
													if (!webPermissionId.contains(permission.getId())) {
														webPermissionId.add(permission.getId());
														webPermissionDTOList.add(permission.convertEntityToDTO());
													}
												}

												for (Permission permission : mobilePermission) {
													if (!mobilePermissionId.contains(permission.getId())) {
														mobilePermissionId.add(permission.getId());
														mobilePermissionDTOList.add(permission.convertEntityToDTO());
													}
												}

											}

										}
										loginResponse.setSites(siteList.stream().map(site -> site.convertToSiteDto())
												.collect(Collectors.toList()));
									}
									loginResponse.setWebPermissionDTOList(webPermissionDTOList);
									loginResponse.setMobilePermissionDTOList(mobilePermissionDTOList);

								} else {
									throw new ResourceNotFoundException("User not found.");
								}
							} else {
								throw new ResourceNotFoundException("User not found.");
							}
						}
					}
				} else {
					throw new UnauthorizedException("INVALID_CREDENTIALS");
				} /*
					 * else { if
					 * (credentialMaster.getUserTypeId().getName().equalsIgnoreCase(Constant.USER))
					 * { Optional<Configuration> configuration = configurationRepository
					 * .findByKey(Constant.FAIL_LOGIN_LOCK_RELEASE_INMIN); Integer failedCount = 1;
					 * Integer maxFailedCount = 3; if (configuration.isPresent()) { Integer
					 * loginLockReleaseMin = Integer.valueOf(configuration.get().getValue());
					 * Optional<User> userOptional =
					 * userRepository.findById(credentialMaster.getUserId()); if
					 * (userOptional.isPresent()) { User user = userOptional.get(); if
					 * (user.getFailedLoginTimeStamp() != null) { Optional<Configuration>
					 * maxLoginAttemptConfig = configurationRepository
					 * .findByKey(Constant.MAX_LOGIN_ATTEMPT); if(maxLoginAttemptConfig.isPresent())
					 * { maxFailedCount = Integer.valueOf(maxLoginAttemptConfig.get().getValue()); }
					 * failedCount = user.getFailedCount() != null ? user.getFailedCount() : 0; if
					 * (new Date().before(DateUtil.addMinutesToJavaUtilDate(
					 * user.getFailedLoginTimeStamp(), loginLockReleaseMin))) {
					 * user.setFailedCount(failedCount+1); if(user.getFailedCount() != null &&
					 * user.getFailedCount() <= maxFailedCount) { userRepository.saveAndFlush(user);
					 * } else { throw new
					 * UnauthorizedException("You’ve reached the maximum login attempts.try after sometime"
					 * ); } } else { user.setFailedCount(1); user.setFailedLoginTimeStamp(new
					 * Date()); userRepository.saveAndFlush(user); } } else {
					 * user.setFailedCount(failedCount); user.setFailedLoginTimeStamp(new Date());
					 * userRepository.saveAndFlush(user); } } } } else if
					 * (credentialMaster.getUserType().getName().equalsIgnoreCase(Constant.ADMIN)) {
					 * Optional<Configuration> configuration = configurationRepository
					 * .findByKey(Constant.FAIL_LOGIN_LOCK_RELEASE_INMIN); Integer failedCount = 1;
					 * Integer maxFailedCount = 3; if (configuration.isPresent()) { Integer
					 * loginLockReleaseMin = Integer.valueOf(configuration.get().getValue());
					 * Optional<Company> userOptional =
					 * companyRepository.findById(credentialMaster.getUserId()); if
					 * (userOptional.isPresent()) { Company user = userOptional.get(); if
					 * (user.getFailedLoginTimeStamp() != null) { Optional<Configuration>
					 * maxLoginAttemptConfig = configurationRepository
					 * .findByKey(Constant.MAX_LOGIN_ATTEMPT); if(maxLoginAttemptConfig.isPresent())
					 * { maxFailedCount = Integer.valueOf(maxLoginAttemptConfig.get().getValue()); }
					 * failedCount = user.getFailedCount() != null ? user.getFailedCount() : 0; if
					 * (new Date().before(DateUtil.addMinutesToJavaUtilDate(
					 * user.getFailedLoginTimeStamp(), loginLockReleaseMin))) {
					 * user.setFailedCount(failedCount+1); if(user.getFailedCount() != null &&
					 * user.getFailedCount() <= maxFailedCount) {
					 * companyRepository.saveAndFlush(user); } else { throw new
					 * UnauthorizedException("You’ve reached the maximum login attempts.try after sometime"
					 * ); } } else { user.setFailedCount(1); user.setFailedLoginTimeStamp(new
					 * Date()); companyRepository.saveAndFlush(user); } } else {
					 * user.setFailedCount(failedCount); user.setFailedLoginTimeStamp(new Date());
					 * companyRepository.saveAndFlush(user); } } } } throw new
					 * UnauthorizedException("INVALID_CREDENTIALS"); }
					 */
			} else {
				throw new UnauthorizedException("INVALID_CREDENTIALS");
			}
		} else {
			throw new UnauthorizedException("INVALID_CREDENTIALS");
		}
		return new Response<>(HttpStatus.OK.value(), "Successfully Logedin", loginResponse);
	}
}
