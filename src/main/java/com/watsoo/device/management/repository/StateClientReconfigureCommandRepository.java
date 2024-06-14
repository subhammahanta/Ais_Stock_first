package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.StateClientReconfigureCommand;

@Repository
public interface StateClientReconfigureCommandRepository extends JpaRepository<StateClientReconfigureCommand, Long> {

	@Query(value = "select * from state_client_reconfigure_command where state_id=:stateId and client_id=:clientId", nativeQuery = true)
	List<StateClientReconfigureCommand> findByStateIdAndClientId(Long stateId, Long clientId);

}
