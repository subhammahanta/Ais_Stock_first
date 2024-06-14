package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.device.management.model.UserLite;

public interface UserLiteRepository extends JpaRepository<UserLite, Long> {

	@Query(value = "select * from user where email=?1 and name=?2", nativeQuery = true)
	List<UserLite> findAll(String email, String name);

}
