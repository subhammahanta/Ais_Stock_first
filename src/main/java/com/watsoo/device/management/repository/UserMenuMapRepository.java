package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.watsoo.device.management.model.UserMenuMap;

public interface UserMenuMapRepository extends JpaRepository<UserMenuMap, Long> {

	@Query(value = "select * from user_menu_map a where a.role_id = :roleId AND a.is_active = 1", nativeQuery = true)
	List<UserMenuMap> findByRoleId(@Param("roleId") Long roleId);
	
	@Query(value = "select * from user_menu_map a where a.role_id IN (:roleIds) AND a.is_active = 1", nativeQuery = true)
	List<UserMenuMap> findByRoleIds(@Param("roleIds") List<Long> roleIds);

	List<UserMenuMap> findByUserId(Long id);
	
}
