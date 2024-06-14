package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.watsoo.device.management.model.Role;
import com.watsoo.device.management.model.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

	List<RolePermission> findByRole(Role role);
	
	@Query(value = "select * from role_permission where role_id IN (:roleIds)", nativeQuery = true)
    List<RolePermission> findByRoleIds(@Param("roleIds") List<Long> roleIds);

}
