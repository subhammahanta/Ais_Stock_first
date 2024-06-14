package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.DeviceModel;

@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {

}
