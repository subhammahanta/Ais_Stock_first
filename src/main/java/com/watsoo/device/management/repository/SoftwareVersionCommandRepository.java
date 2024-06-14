package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.SoftwareVersionCommand;

@Repository
public interface SoftwareVersionCommandRepository extends JpaRepository<SoftwareVersionCommand, Long> {

	@Query(value = "select * from software_version_command where software_version='REVERT'", nativeQuery = true)
	SoftwareVersionCommand findRevertCommand();

}
