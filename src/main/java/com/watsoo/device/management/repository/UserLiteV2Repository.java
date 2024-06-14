package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.UserLiteV2;

@Repository
public interface UserLiteV2Repository extends JpaRepository<UserLiteV2, Long> {

}
