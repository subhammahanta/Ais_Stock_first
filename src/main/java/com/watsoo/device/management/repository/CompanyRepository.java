package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watsoo.device.management.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
