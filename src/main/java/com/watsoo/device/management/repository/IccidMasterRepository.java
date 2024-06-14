package com.watsoo.device.management.repository;

import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.IccidMaster;

@Repository
public interface IccidMasterRepository extends JpaRepository<IccidMaster, Long> {

	default Page<IccidMaster> findAll(GenericRequestBody requestDTO, Pageable pageRequest) {
		try {
			return findAll(search(requestDTO), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<IccidMaster> findAll(Specification<IccidMaster> search, Pageable pageable);

	static Specification<IccidMaster> search(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;

			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				p.getExpressions()
				.add(cb.or(cb.like(root.get("iccidNo"),  "%" + entity.getSearch() + "%"),
						cb.like(root.get("sim1"),  "%" + entity.getSearch() + "%"),
						cb.like(root.get("sim2"),  "%" + entity.getSearch() + "%")));
				//p.getExpressions().add(cb.like(root.get("iccidNo"), "%" + entity.getSearch() + "%"));
				isNotPresent = false;
			}

			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	Optional<IccidMaster> findByIccidNo(String iccidNo);
	
	@Query(value="SELECT * FROM iccid_master where iccid_no LIKE %:iccidNo% LIMIT 1",nativeQuery = true)
	Optional<IccidMaster> findByLikeIccidNo(String iccidNo);
}
