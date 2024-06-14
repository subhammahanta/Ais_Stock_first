package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.GetCommandKey;

@Repository
public interface GetCommandKeyRepository extends JpaRepository<GetCommandKey, Long> {

}
