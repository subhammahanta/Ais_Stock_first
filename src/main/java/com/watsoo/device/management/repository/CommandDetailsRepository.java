package com.watsoo.device.management.repository;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.CommandDetails;

public interface CommandDetailsRepository extends JpaRepository<CommandDetails, Long> {

	@Query(value = "SELECT * FROM command_details where command_request_master_id=?1 ORDER BY id ASC",nativeQuery=true)
	List<CommandDetails> findByRequestId(Long reqId);

	@Query(value = "SELECT * FROM command_details where command_request_master_id= ?1 and id>=?2 order by id ASC", nativeQuery = true)
	List<CommandDetails> findByRequestIdAndProcessId(Long reqId, Long procesId);

	default Page<CommandDetails> findAllV2(GenericRequestBody genericRequestBody, Pageable pageRequest) {
		try {
			return findAll(search2(genericRequestBody), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	Page<CommandDetails> findAll(Specification<CommandDetails> search, Pageable pageable);
	
	static Specification<CommandDetails> search2(GenericRequestBody entity) {
		return (root, cq, cb) -> {
		
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			
			if (entity.getMasterId() != null) {
				p.getExpressions().add(cb.equal(root.get("commandRequestMaster"), entity.getMasterId()));
				isNotPresent = false;
			}
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

}
