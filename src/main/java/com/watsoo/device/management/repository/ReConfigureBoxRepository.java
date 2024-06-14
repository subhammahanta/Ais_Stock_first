package com.watsoo.device.management.repository;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.ReConfigureBox;

@Repository
public interface ReConfigureBoxRepository extends JpaRepository<ReConfigureBox, Long> {

	@Query(value = "select * from re_configure_box rcb where rcb.box_Id =:boxId and rcb.is_completed=0", nativeQuery = true)
	ReConfigureBox findExistReConfigureBoxByBoxId(Long boxId);

	default Page<ReConfigureBox> findAll(GenericRequestBody requestDTO, Pageable pageRequest) {
		try {
			return findAll(search(requestDTO), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<ReConfigureBox> findAll(Specification<ReConfigureBox> search, Pageable pageable);

	static Specification<ReConfigureBox> search(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			if (entity.getFromDate() != null && entity.getToDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(entity.getFromDate()));
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date dateFrom = cal.getTime();
				Calendar calendar = Calendar.getInstance();
				Date endDate = new Date(entity.getToDate());
				calendar.setTime(endDate);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				Date dateTo = calendar.getTime();
				p.getExpressions().add(cb.between(root.get("createdAt"), dateFrom, dateTo));
				isNotPresent = false;
			}

			if (entity.getId() != null && entity.getId() > 0) {
				p.getExpressions().add(cb.equal(root.get("id"), entity.getId()));
				isNotPresent = false;
			}

			if (entity.getIsCompleted() != null) {
				p.getExpressions().add(cb.equal(root.get("isCompleted"), entity.getIsCompleted()));
				isNotPresent = false;
			}

			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				p.getExpressions()
						.add(cb.or(cb.like(root.get("reConfigBoxCode"), "%" + entity.getSearch() + "%"),
								cb.like(root.get("unsettledBoxCode"), "%" + entity.getSearch() + "%"),
								cb.like(root.get("boxNo"), "%" + entity.getSearch() + "%")));
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
