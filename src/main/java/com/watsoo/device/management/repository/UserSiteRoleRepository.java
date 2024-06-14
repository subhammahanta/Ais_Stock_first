package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.watsoo.device.management.model.UserSiteRole;

public interface UserSiteRoleRepository extends JpaRepository<UserSiteRole, Long> {

	@Query(value = "select * from user_site_role usr where usr.user_id =:userId AND usr.site_id =:siteId AND usr.role_id =:roleId AND usr.is_active =:b", nativeQuery = true)
	UserSiteRole findByUserSiteAndRoleAndIsActive(@Param("userId") Long userId, @Param("siteId") Long siteId,
			@Param("roleId") Long roleId, @Param("b") boolean b);

	@Query("select us from UserSiteRole us where us.user.id=?1 and us.site.id=?2")
	List<UserSiteRole> findByUserIdAndSiteId(Long userId, Long siteId);

	@Query(value = "select * from user_site_role usr where usr.user_id =:userId AND usr.site_id =:siteId AND usr.is_active =:b", nativeQuery = true)
	List<UserSiteRole> findByUserSiteAndIsActive(@Param("userId") Long userId, @Param("siteId") Long siteId,
			@Param("b") boolean b);

	@Query(value = "select * from user_site_role usr where usr.user_id = :userId AND usr.is_active = true", nativeQuery = true)
	List<UserSiteRole> findbyUserIdAndIsActive(Long userId);

	@Query(value = "select * from user_site_role usr where usr.site_id = :id AND usr.is_active = true", nativeQuery = true)
	List<UserSiteRole> findBySiteId(Long id);
	
	List<UserSiteRole> findByUser_idAndSite_idInAndIsActiveTrue(Long id, List<Long> filteredSiteIds);

}
