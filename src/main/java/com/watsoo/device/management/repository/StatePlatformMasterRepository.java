package com.watsoo.device.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.device.management.model.DeviceMaster;
import com.watsoo.device.management.model.StatePlatformMaster;

public interface StatePlatformMasterRepository extends JpaRepository<StatePlatformMaster, Integer> {

//	@Query(value = "SELECT * FROM ais_stock22jantesting.state_platform_master where state_id=1", nativeQuery = true)
	List<StatePlatformMaster> findByStateId(Long stateId);
	@Query(value = "SELECT * FROM state_platform_master where state_id=?1", nativeQuery = true)
	StatePlatformMaster findByStateIdV2(Long stateId);

}
