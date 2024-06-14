package com.watsoo.device.management.repository;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.StateCommandDTO;
import com.watsoo.device.management.exception.ResourceNotFoundException;
import com.watsoo.device.management.model.StateCmdMstrEntity;

@Repository
public interface StateCmdMstrRepository extends JpaRepository<StateCmdMstrEntity, Long> {

//	@Query(value = "select * from state_command_mstr where state_id.id =:stateId and client_id.id =:clientId", nativeQuery = true)
//	List<StateCmdMstrEntity> getAllByStateIdAndClient(@Param("stateId") Long stateId, @Param("clientId") Long clientId);

	default Page<StateCmdMstrEntity> getAllStateCommand(StateCommandDTO dto, Pageable pageRequest) {
		try {
			return findAll(search(dto), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Oops!!!..Something went wrong");
		}
	}

	static Specification<StateCmdMstrEntity> search(StateCommandDTO dto) {
		return (root, cq, cb) -> {
			Boolean isNotPresent = true;
			Predicate p = cb.conjunction();
			if (dto.getId() != null && dto.getId() > 0) {
				p.getExpressions().add(cb.equal(root.get("id"), dto.getId()));
				isNotPresent = false;
			}
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	Page<StateCmdMstrEntity> findAll(Specification<StateCmdMstrEntity> search, Pageable pageRequest);

	@Query(value = "select s from StateCmdMstrEntity s where s.client.id =?1 and s.state.id=?2 and s.isActive=true")
	List<StateCmdMstrEntity> getAllByStateIdAndClientId(Long clientId ,Long stateId);

}
