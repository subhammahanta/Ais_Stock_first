package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ChargesMaster;

@Repository
public interface ChargesMasterRepository extends JpaRepository<ChargesMaster, Long> {

	@Query(value = "select * from charges_master where id IN(:chargesId)", nativeQuery = true)
	List<ChargesMaster> findAllByChargesId(List<Long> chargesId);

	@Query(value = "select * from charges_master where device_issue =:newDevice", nativeQuery = true)
	ChargesMaster findNewDeviceCost(String newDevice);

}
