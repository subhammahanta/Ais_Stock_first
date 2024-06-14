package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.UserSubMenuMap;

@Repository
public interface UserSubMenuMapRepository extends JpaRepository<UserSubMenuMap, Long> {

	List<UserSubMenuMap> findByUserId(Long userId);
	
	@Query(value = "select * from user_sub_menu_map a where a.role_id IN (:roleIds) AND a.is_active = 1", nativeQuery = true)
	List<UserSubMenuMap> findByRoleIds(@Param("roleIds") List<Long> roleIds);
	

}
