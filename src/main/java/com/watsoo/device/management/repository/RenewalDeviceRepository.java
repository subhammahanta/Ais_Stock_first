package com.watsoo.device.management.repository;

import com.watsoo.device.management.model.RenewalDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RenewalDeviceRepository extends JpaRepository<RenewalDevice,Long> {
    @Query(value = "select * from renewal_device where request_id =:reqId ", nativeQuery = true)
    Page<RenewalDevice> findAllByRequestId(@Param("reqId")Long requestId, Pageable pageable);

    @Query(value = "select * from renewal_device where request_id =:reqId ", nativeQuery = true)
    List<RenewalDevice> findAllByRequestId(@Param("reqId") Long requestId);

    @Query(value = "select count(*) from renewal_device where request_id = :requestId",nativeQuery = true)
    Integer deviceCountForRequest(@Param("requestId")Long requestId);

}
