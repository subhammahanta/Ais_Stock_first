package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

	Configuration findByKey(String key);

}
