package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ExcelDeviceDatabaseDeviceMapping;

@Repository
public interface ExcelDeviceDatabaseDeviceMappingRepository
		extends JpaRepository<ExcelDeviceDatabaseDeviceMapping, Long> {

}
