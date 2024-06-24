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


    @Query(value = "select distinct d.created_by,d.created_at,d.id,d.req_code ,r.device_id,r.iccid_no,r.old_expiry_date," +
            "r.new_expiry_date from device_renewal_request d inner join renewal_device r where d.id=r.request_id and d.id=:requestId;  ", nativeQuery = true)
    List<Object[]> getRenewalDevicesById(@Param("requestId") Long requestId);
}
