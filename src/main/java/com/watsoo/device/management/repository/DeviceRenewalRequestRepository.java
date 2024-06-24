package com.watsoo.device.management.repository;

import com.watsoo.device.management.dto.DeviceRenewalResponseDTO;
import com.watsoo.device.management.model.DeviceRenewalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRenewalRequestRepository extends JpaRepository<DeviceRenewalRequest,Long> {

    @Query(value = "SELECT COUNT(*) FROM device_renewal_request", nativeQuery = true)
    int countTotalItems();



//    List<DeviceRenewalRequest> getRenewalDevices(Pageable pageable);

    @Query(value = "select * from device_renewal_request",nativeQuery = true)
       Page<DeviceRenewalRequest> getAllRenewalDevices(Pageable pageable);


}
