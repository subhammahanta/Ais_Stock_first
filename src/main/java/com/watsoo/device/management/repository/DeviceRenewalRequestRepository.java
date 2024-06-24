package com.watsoo.device.management.repository;

import com.watsoo.device.management.dto.DeviceRenewalResponseDTO;
import com.watsoo.device.management.model.DeviceRenewalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRenewalRequestRepository extends JpaRepository<DeviceRenewalRequest,Long> {

    @Query(value = "SELECT COUNT(*) FROM device_renewal_request", nativeQuery = true)
    int countTotalItems();


    @Query(value = "SELECT DISTINCT d.id, d.req_code, r.device_id, r.imei_no, r.iccid_no, r.old_expiry_date, r.new_expiry_date " +
            "FROM device_renewal_request d " +
            "INNER JOIN renewal_device r ON d.id = r.request_id " +
            "and d.id = :requestId", nativeQuery = true)
    List<Object[]> getRenewalDevicesById(@Param("requestId") Long requestId);
}
