package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watsoo.device.management.model.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
}
