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
import com.watsoo.device.management.model.DeviceLot;

@Repository
public interface DeviceLotRepository extends JpaRepository<DeviceLot, Long> {

	default Page<DeviceLot> findAll(GenericRequestBody requestDTO, Pageable pageRequest) {
		try {
			return findAll(search(requestDTO), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<DeviceLot> findAll(Specification<DeviceLot> search, Pageable pageable);

	static Specification<DeviceLot> search(GenericRequestBody entity) {
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
			if (entity.getClientId() != null && entity.getClientId() > 0) {
				p.getExpressions().add(cb.equal(root.get("client").get("id"), entity.getClientId()));
				isNotPresent = false;
			}
			if (entity.getStateId() != null && entity.getStateId() > 0) {
				p.getExpressions().add(cb.equal(root.get("state").get("id"), entity.getStateId()));
				isNotPresent = false;
			}
			if (entity.getIsCompleted() != null && entity.getIsCompleted()) {
				p.getExpressions().add(cb.equal(root.get("isCompleted"), true));
				isNotPresent = false;
			}
			if (entity.getIsCompleted() != null && !entity.getIsCompleted()) {
				p.getExpressions().add(cb.equal(root.get("isCompleted"), null));
				isNotPresent = false;
			}
			if (entity.getId() != null && entity.getId() > 0) {
				p.getExpressions().add(cb.equal(root.get("id"), entity.getId()));
				isNotPresent = false;
			}
			if (entity.getModelId() != null && entity.getModelId() > 0) {
				p.getExpressions().add(cb.equal(root.get("modelId").get("id"), entity.getModelId()));
				isNotPresent = false;
			}
			if (entity.getSimProviderId() != null && entity.getSimProviderId() > 0) {
				p.getExpressions().add(cb.equal(root.get("provider").get("id"), entity.getSimProviderId()));
				isNotPresent = false;
			}

			if (entity.getSystemLotId() != null && entity.getSystemLotId() != "") {
				p.getExpressions().add(cb.equal(root.get("systemLotId"), entity.getSystemLotId()));
				isNotPresent = false;
			}
			
			if (entity.getMfgLotId() != null && entity.getMfgLotId() != "") {
				p.getExpressions().add(cb.equal(root.get("mfgLotId"), entity.getMfgLotId()));
				isNotPresent = false;
			}
			
			if (entity.getLotId() != null) {
				p.getExpressions().add(cb.equal(root.get("id"), entity.getLotId()));
				isNotPresent = false;
			}
			
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	@Query(value = "SELECT * FROM device_lot ORDER BY id DESC LIMIT 1", nativeQuery = true)
	DeviceLot findLastLot();

	@Query(value = "UPDATE device_lot SET tested_quantity = tested_quantity - 1,pending_quantity = pending_quantity + 1 WHERE id =?1", nativeQuery = true)
	void updateLotQuantity(Long lotId);
}
