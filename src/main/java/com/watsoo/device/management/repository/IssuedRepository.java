package com.watsoo.device.management.repository;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.watsoo.device.management.dto.IssueDeviceDTO;
import com.watsoo.device.management.exception.ResourceNotFoundException;
import com.watsoo.device.management.model.IssuedList;

@Repository
public interface IssuedRepository extends JpaRepository<IssuedList, Long>{
	
	
	default Page<IssuedList> findAll(IssueDeviceDTO dto, Pageable pageRequest) {
		try {
			return findAll(searchAll(dto), pageRequest);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}

	}

	Page<IssuedList> findAll(Specification<IssuedList> searchAll, Pageable pageRequest);

	static Specification<IssuedList> searchAll(IssueDeviceDTO dto) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			if (dto.getId() != null) {
				p.getExpressions().add(cb.equal(root.get("serialNumber"), dto.getId()));
				isNotPresent = false;
			}if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}
	
	
	
//	default Page<IssuedList> getIssuedList(IssuedList dto, Pageable pageRequest) {
//		try {
//			return findAll(search(dto), pageRequest);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ResourceNotFoundException("Oops!!!..Something went wrong");
//		}
//	}
//
//	static Specification<IssuedList> search(IssuedList dto) {
//		
//	    return (root, cq, cb) -> Optional.ofNullable(dto)
//	            .filter(d -> d.getClient() != null && d.getClient().getId() > 0 && d.getClient().getId() != null)
//	            .map(d -> cb.equal(root.get("client").get("id"), d.getClient().getId()))
//	            .orElseGet(() -> Optional.ofNullable(dto)
//	                    .filter(d -> d.getState() != null && d.getState().getId() > 0 && d.getState().getId() != null)
//	                    .map(d -> cb.equal(root.get("state").get("id"), d.getState().getId()))
//	                    .orElse(null));
//	}
//
//
//	Page<IssuedList> findAll(Specification<IssuedList> specification, Pageable pageRequest);

}
