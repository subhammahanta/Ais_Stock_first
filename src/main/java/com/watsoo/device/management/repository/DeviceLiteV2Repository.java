package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.DeviceLiteV2;

@Repository
public interface DeviceLiteV2Repository extends JpaRepository<DeviceLiteV2, Long> {

}
