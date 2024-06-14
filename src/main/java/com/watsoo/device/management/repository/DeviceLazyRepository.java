package com.watsoo.device.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.DeviceLazyEntity;

@Repository
public interface DeviceLazyRepository extends JpaRepository<DeviceLazyEntity, Long> {

	Optional<DeviceLazyEntity> findByImeiNo(String imeiNo);

	Optional<DeviceLazyEntity> findByIccidNo(String iccidNo);

}
