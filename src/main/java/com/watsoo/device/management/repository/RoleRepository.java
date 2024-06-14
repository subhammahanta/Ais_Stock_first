package com.watsoo.device.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watsoo.device.management.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByCompanyId(Long companyId);

	Optional<Role> findByNameAndCompanyId(String name, Long companyId);
}
