package com.watsoo.device.management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.DeviceConfigMaster;

@Repository
public interface DeviceConfigMasterRepository extends JpaRepository<DeviceConfigMaster, Long> {

	List<DeviceConfigMaster> findAllByImeiNoIn(List<String> imeiNos);

	@Query(value = "SELECT * FROM device_config_master WHERE DATE(last_command_shoot_at) = DATE(:toDay) AND is_success = 0", nativeQuery = true)
	List<DeviceConfigMaster> findAllUnConfigureDevices(@Param("toDay") Date toDay);

}
