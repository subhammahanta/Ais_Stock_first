package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ConfigChangeTrail;

@Repository
public interface ConfigChangeTrailRepository extends JpaRepository<ConfigChangeTrail, Long>{

	@Query("select e from ConfigChangeTrail e where e.stateCmdMstrId=?1")
	List<ConfigChangeTrail> getByConfigId(Long stateCmdMstrId);
	
	@Query("select e from ConfigChangeTrail e where e.stateCmdMstrId IN (:stateCmdMstrId)")
	List<ConfigChangeTrail> getByConfigIds(@Param("stateCmdMstrId") List<Long> stateCmdMstrId);
	
//	@Query("select count(v) from Vehicle v where v.company.id=:companyId and v.site.id IN (:siteIds)")
//	Long countByCompanyIdAndSiteIds(@Param("companyId") Long companyId, @Param("siteIds") List<Long> siteIds);
}
