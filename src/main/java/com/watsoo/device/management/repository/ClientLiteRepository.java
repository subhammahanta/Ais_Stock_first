package com.watsoo.device.management.repository;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.device.management.dto.ClientDTO;
import com.watsoo.device.management.dto.StateClientDTO;
import com.watsoo.device.management.model.ClientLite;

public interface ClientLiteRepository extends JpaRepository<ClientLite, Long> {

	default Page<ClientLite> findAll(ClientDTO clientDTO, Pageable pageRequest) {
		try {
			return findAll(searchAll(clientDTO), pageRequest);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}
	}

	Page<ClientLite> findAll(Specification<ClientLite> searchAll, Pageable pageRequest);

	static Specification<ClientLite> searchAll(ClientDTO clientDTO) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			if (clientDTO.getId() != null) {
				p.getExpressions().add(cb.equal(root.get("id"), clientDTO.getId()));
				isNotPresent = false;
			}
			if (clientDTO.getCompanyName() != null) {
				p.getExpressions().add(cb.equal(root.get("companyName"), clientDTO.getCompanyName()));
				isNotPresent = false;
			}
			if (clientDTO.getState() != null) {
				p.getExpressions().add(cb.equal(root.get("state"), clientDTO.getState()));
				isNotPresent = false;
			}
			if (clientDTO.getCity() != null) {
				p.getExpressions().add(cb.equal(root.get("city"), clientDTO.getCity()));
				isNotPresent = false;
			}
			if (clientDTO.getEmail() != null) {
				p.getExpressions().add(cb.equal(root.get("email"), clientDTO.getEmail()));
				isNotPresent = false;
			}
			if (clientDTO.getPhoneNumber() != null) {
				p.getExpressions().add(cb.equal(root.get("phoneNumber"), clientDTO.getPhoneNumber()));
				isNotPresent = false;
			}
			if (clientDTO.getPanNumber() != null) {
				p.getExpressions().add(cb.equal(root.get("panNumber"), clientDTO.getPanNumber()));
				isNotPresent = false;
			}
			if (clientDTO.getCompanyLogo() != null) {
				p.getExpressions().add(cb.equal(root.get("companyLogo"), clientDTO.getCompanyLogo()));
				isNotPresent = false;
			}
			if (clientDTO.getCompanyCode() != null) {
				p.getExpressions().add(cb.equal(root.get("companyCode"), clientDTO.getCompanyCode()));
				isNotPresent = false;
			}
			if (clientDTO.getGstNumber() != null) {
				p.getExpressions().add(cb.equal(root.get("gstNumber"), clientDTO.getGstNumber()));
				isNotPresent = false;
			}
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	ClientLite findByCompanyNameAndEmail(String companyName, String email);

	@Query(value = " SELECT c.company_name AS CompanyName,s.name AS Name FROM client c INNER JOIN state s ON c.state_id=s.id GROUP BY c.company_name,c.state_id", nativeQuery = true)
	List<StateClientDTO> findAllStateClient();

}
