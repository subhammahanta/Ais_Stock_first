package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.DeviceLotMaster;

@Repository
public interface DeviceLotMasterRepository extends JpaRepository<DeviceLotMaster, Long> {

	@Query(value = "select * from device_lot_master d where d.lot_id=?1 AND d.tested_device_id=?2", nativeQuery = true)
	List<DeviceLotMaster> getAllCommandResponseByDeviceId(Long lotId, Long deviceId);

}
