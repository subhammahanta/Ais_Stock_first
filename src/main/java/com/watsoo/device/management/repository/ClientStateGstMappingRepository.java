package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ClientStateGstMapping;

@Repository
public interface ClientStateGstMappingRepository extends JpaRepository<ClientStateGstMapping, Long> {

	@Query(value = "select * from client_state_gst where id IN (:clientStateGstIds) AND is_active=1", nativeQuery = true)
	List<ClientStateGstMapping> findbyIds(List<Long> clientStateGstIds);

	@Modifying
	@Query(value = "DELETE FROM client_state_gst WHERE client_id = :clientId", nativeQuery = true)
	void deleteExistingMappingByClientId(@Param("clientId") Long clientId);

	@Query(value = "select * from client_state_gst where client_id =?1 AND is_active=1", nativeQuery = true)
	List<ClientStateGstMapping> findbyClientIds(Long clientId);

}
