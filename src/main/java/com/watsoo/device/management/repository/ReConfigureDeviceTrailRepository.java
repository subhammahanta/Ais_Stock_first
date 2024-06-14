package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ReConfigureDeviceTrail;

@Repository
public interface ReConfigureDeviceTrailRepository extends JpaRepository<ReConfigureDeviceTrail, Long> {

}
