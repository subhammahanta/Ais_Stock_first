package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.device.management.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	@Query("select p from Permission p where p.permissionCategory.id=?1")
	List<Permission> getByPermissionCategoryId(Long permissionCategoryId);

	@Query(value = "select * from permission p where p.group_type_id=:id AND p.is_active =:isActive", nativeQuery = true)
	List<Permission> getByGroupId(Long id, boolean isActive);
}
