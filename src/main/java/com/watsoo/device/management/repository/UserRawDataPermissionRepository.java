package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.UserRawDataPermission;

@Repository
public interface UserRawDataPermissionRepository extends JpaRepository<UserRawDataPermission, Long> {
	
	@Query(value="SELECT * FROM user_raw_data_permission WHERE user_id =:userId",nativeQuery = true)
	List<UserRawDataPermission> getByUserId(@Param("userId") Long userId);

}
