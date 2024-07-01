package com.watsoo.device.management.repository;

import com.watsoo.device.management.model.DeviceRenewalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DeviceRenewalRequestRepository extends JpaRepository<DeviceRenewalRequest,Long> {

    @Query(value = "SELECT COUNT(*) FROM device_renewal_request", nativeQuery = true)
    int countTotalItems();



//    List<DeviceRenewalRequest> getRenewalDevices(Pageable pageable);

    //@Query(value = "select * from device_renewal_request",nativeQuery = true)
  //     Page<DeviceRenewalRequest> getAllRenewalDevices(Pageable pageable, @Param("search")String search, @Param("fromDate")Date fromDate,@Param("toDate")Date toDate);

    @Query(value = "SELECT * FROM device_renewal_request  INNER JOIN renewal_device  ON device_renewal_request.id = renewal_device.request_id",nativeQuery = true)
       Page<DeviceRenewalRequest> getAllRenewalDevices(Pageable pageable);


    @Query(value = "select * from device_renewal_request order by created_at desc",nativeQuery = true)
    Page<DeviceRenewalRequest> findAll(Pageable pageable);

    @Query(value="select * from device_renewal_request req where req_code like :reqCode",nativeQuery = true)
    Page<DeviceRenewalRequest> findByReqCode(@Param("reqCode") String reqCode, Pageable pageable);


    @Query(value="select * from device_renewal_request req where req_code = :reqCode",nativeQuery = true)
    Optional<DeviceRenewalRequest> findByReqCode(String reqCode);

    @Query(value = "select * from device_renewal_request where  created_at >=  :fromDate and  created_at <= :toDate",nativeQuery = true)
    Page<DeviceRenewalRequest> findAllCreatedAtBetween(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

}
