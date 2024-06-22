package com.watsoo.device.management.repository;

import com.watsoo.device.management.model.DeviceRenewalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRenewalRequestRepository extends JpaRepository<DeviceRenewalRequest,Long> {

    @Query(value = "SELECT COUNT(*) FROM device_renewal_request", nativeQuery = true)
    int countTotalItems();
}
