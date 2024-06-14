package com.watsoo.device.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.CredentialMaster;

@Repository
public interface CredentialMasterRepository extends JpaRepository<CredentialMaster, Long> {

	Optional<CredentialMaster> findByEmail(String email);

}
