package com.watsoo.device.management.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.TestedDevice;

@Repository
public interface TestedDeviceRepository extends JpaRepository<TestedDevice, Long> {

	@Query(value = "select * from tested_device t where t.imei_no=?1 AND t.is_active=1", nativeQuery = true)
	TestedDevice findByImeiNo(String imeiNo);

	default Page<TestedDevice> findAll(GenericRequestBody requestDTO, Pageable pageRequest) {
		try {
			return findAll(search(requestDTO), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<TestedDevice> findAll(Specification<TestedDevice> search, Pageable pageable);

	static Specification<TestedDevice> search(GenericRequestBody entity) {
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
			if (entity.getDeviceId() != null && entity.getDeviceId() > 0) {
				p.getExpressions().add(cb.equal(root.get("deviceId"), entity.getDeviceId()));
				isNotPresent = false;
			}
			if (entity.getSoftwareVersion() != null && entity.getSoftwareVersion() != "") {
				p.getExpressions().add(cb.equal(root.get("softwareVersion"), entity.getSoftwareVersion()));
				isNotPresent = false;
			}

			// if (entity.getIsCompleted() != null && entity.getIsCompleted()) {
			// p.getExpressions().add(cb.equal(root.get("isTestingCompleted"), true));
			// isNotPresent = false;
			// }
			// if (entity.getIsProcessing() != null && entity.getIsProcessing()) {
			// p.getExpressions().add(
			// cb.and(cb.equal(root.get("isTestingCompleted"), null),
			// cb.equal(root.get("isRejected"), null)));
			//
			// // p.getExpressions().add(cb.equal(root.get("isTestingCompleted"), null));
			// isNotPresent = false;
			// }
			// if (entity.getIsRejected() != null && entity.getIsRejected()) {
			// p.getExpressions().add(cb.equal(root.get("isRejected"), true));
			// isNotPresent = false;
			// }
			if (entity.getDeviceStatus() != null && entity.getDeviceStatus() != ""
					&& entity.getDeviceStatus().equalsIgnoreCase("REJECTED")) {
				p.getExpressions().add(cb.equal(root.get("isRejected"), true));
				isNotPresent = false;
			}
			if (entity.getDeviceStatus() != null && entity.getDeviceStatus() != ""
					&& entity.getDeviceStatus().equalsIgnoreCase("PROCESSING")) {
				p.getExpressions().add(
						cb.and(cb.equal(root.get("isTestingCompleted"), null), cb.equal(root.get("isRejected"), null)));
				isNotPresent = false;
			}
			if (entity.getDeviceStatus() != null && entity.getDeviceStatus() != ""
					&& entity.getDeviceStatus().equalsIgnoreCase("COMPLETTED")) {
				p.getExpressions().add(cb.equal(root.get("isTestingCompleted"), true));
				isNotPresent = false;
			}
			if (entity.getId() != null && entity.getId() > 0) {
				p.getExpressions().add(cb.equal(root.get("id"), entity.getId()));
				isNotPresent = false;
			}
			if (entity.getLotId() != null && entity.getLotId() > 0) {
				p.getExpressions().add(cb.equal(root.get("lotId"), entity.getLotId()));
				isNotPresent = false;
			}
			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				p.getExpressions()
						.add(cb.or(cb.equal(root.get("imeiNo"), entity.getSearch()),
								cb.equal(root.get("iccidNo"), entity.getSearch()),
								cb.equal(root.get("uuidNo"), entity.getSearch())));
				isNotPresent = false;
			}
			if (entity.getImeiNumber() != null && !entity.getImeiNumber().isEmpty()) {
				p.getExpressions().add(cb.equal(root.get("imeiNo"), entity.getImeiNumber()));
				isNotPresent = false;
			}
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	@Query(value = "SELECT * FROM tested_device t WHERE t.lot_id=?1 ", nativeQuery = true)
	List<TestedDevice> findAllByLotId(Long lotId);
}
