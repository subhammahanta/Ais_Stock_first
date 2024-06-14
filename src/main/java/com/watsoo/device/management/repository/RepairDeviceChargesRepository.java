package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.RepairDeviceCharges;

@Repository
public interface RepairDeviceChargesRepository extends JpaRepository<RepairDeviceCharges, Long> {

}
