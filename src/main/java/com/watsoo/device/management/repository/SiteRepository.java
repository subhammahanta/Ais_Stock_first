package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.device.management.model.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {

	@Query(value = "select * from site where company_id=?1 and site_name=?2", nativeQuery = true)
	Site findByCompanyIdAndSiteName(Long companyId, String siteName);

	@Query(value = "select * from site where ", nativeQuery = true)
	List<Site> getAllUserSiteRoleAndIsactive(Long userId);

	@Query(value = "select * from site where site_owner_id=?1", nativeQuery = true)
	List<Site> findBySiteOwnerId(Long id);

//	Page<Site> findAll(Long companyId, Pageable pageRequest);
}
