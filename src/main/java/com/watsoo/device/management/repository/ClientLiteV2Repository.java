package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ClientLiteV2;

@Repository
public interface ClientLiteV2Repository extends JpaRepository<ClientLiteV2, Long> {

}
