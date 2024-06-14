package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ReConfigureCommand;

@Repository
public interface ReConfigureCommandRepository extends JpaRepository<ReConfigureCommand, Long> {

	@Query(value = "select * from re_configure_command rcm where rcm.reconfigure_box_id =:reConfigureBoxId ", nativeQuery = true)
	List<ReConfigureCommand> findByReconfigureBoxId(Long reConfigureBoxId);

}
