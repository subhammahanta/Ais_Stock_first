package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watsoo.device.management.model.UserDeviceCommand;

public interface UserDeviceCommandRepository extends JpaRepository<UserDeviceCommand, Long> {

}
