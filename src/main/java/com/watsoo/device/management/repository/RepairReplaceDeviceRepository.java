package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.RepairReplaceDevice;

@Repository
public interface RepairReplaceDeviceRepository extends JpaRepository<RepairReplaceDevice, Long> {
	@Query(value = "select * from repair_replace_device where id = :repairDeviceId AND repair_replace_device_master_id = :repairDeviceMasterId ", nativeQuery = true)
	RepairReplaceDevice findByRepairDeviceIdAndRepairDeviceMasterId(@Param("repairDeviceId") Long repairDeviceId,@Param("repairDeviceMasterId") Long repairDeviceMasterId);

}
