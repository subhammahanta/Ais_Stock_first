package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.BoxDeviceV2;

@Repository
public interface BoxDeviceV2Repository extends JpaRepository<BoxDeviceV2, Long> {

}
