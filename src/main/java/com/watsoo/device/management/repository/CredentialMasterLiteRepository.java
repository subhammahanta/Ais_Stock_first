package com.watsoo.device.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.CredentialMasterLite;

@Repository
public interface CredentialMasterLiteRepository extends JpaRepository<CredentialMasterLite, Long> {

	Optional<CredentialMasterLite> findByEmail(String email);

	@Query(value = "SELECT * FROM credential_master where user_id IN (:userIds)",nativeQuery=true)
	List<CredentialMasterLite> findAllByUserId(@Param("userIds") List<Long> userIds);

}
