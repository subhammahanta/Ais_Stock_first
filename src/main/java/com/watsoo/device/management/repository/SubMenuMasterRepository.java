package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watsoo.device.management.model.SubMenuMaster;

public interface SubMenuMasterRepository extends JpaRepository<SubMenuMaster, Long> {

	List<SubMenuMaster> findAllByMenuIdIn(List<Long> visibleMenuIds);

}
