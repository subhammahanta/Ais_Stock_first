package com.watsoo.device.management.serviceImpl;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.watsoo.device.management.dto.ReturnReplaceRepairDTO;
import com.watsoo.device.management.model.ReturnReplaceRepair;

public interface ReturnReplaceAndRepairDeviceRepository extends JpaRepository<ReturnReplaceRepair, Long> {

	default Page<ReturnReplaceRepair> findAll(ReturnReplaceRepairDTO dto, Pageable pageRequest) {
		try {
			return findAll(searchAll(dto), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<ReturnReplaceRepair> findAll(Specification<ReturnReplaceRepair> searchAll, Pageable pageRequest);

	static Specification<ReturnReplaceRepair> searchAll(ReturnReplaceRepairDTO dto) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			if (dto.getId() != null) {
				p.getExpressions().add(cb.equal(root.get("id"), dto.getId()));
				isNotPresent = false;
			}
			if (dto.getOperation() != null && !dto.getOperation().name().isEmpty()
					&& dto.getOperation().name() != null) {
				p.getExpressions().add(cb.equal(root.get("operation"), dto.getOperation()));
				isNotPresent = false;
			}
			if (dto.getCreatedBy() != null) {
				p.getExpressions().add(cb.equal(root.get("createdBy"), dto.getCreatedBy()));
				isNotPresent = false;
			}
			if (dto.getImeiNo() != null && !dto.getImeiNo().isEmpty()) {
				p.getExpressions().add(cb.like(root.get("imei"), "%" + dto.getImeiNo() + "%"));
				isNotPresent = false;
			}
			if (dto.getClientName() != null && !dto.getClientName().isEmpty()) {
				p.getExpressions().add(cb.like(root.get("clientName"), "%" + dto.getClientName() + "%"));
				isNotPresent = false;
			}
			if (dto.getFromDate() != null && dto.getToDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(dto.getFromDate()));
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date stDate = cal.getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date(dto.getToDate()));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				Date enDate = calendar.getTime();
				p.getExpressions().add(cb.between(root.get("createdAt"), stDate, enDate));
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
