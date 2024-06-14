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
import com.watsoo.device.management.model.ReConfigureDevices;

@Repository
public interface ReConfigureDevicesRepository extends JpaRepository<ReConfigureDevices, Long> {

	@Query(value = "select * from re_configure_devices rcd where rcd.imei_no =:imeiNo AND rcd.re_config_box_id =:reConfigureBoxId", nativeQuery = true)
	ReConfigureDevices findByImeiAndReConfigBoxId(String imeiNo, Long reConfigureBoxId);

	@Query(value = "select * from re_configure_devices rcd where rcd.re_config_box_id =:reConfigureBoxId AND rcd.is_re_configure =1", nativeQuery = true)
	List<ReConfigureDevices> findByReConfigureBoxIdAndStatusTrue(Long reConfigureBoxId);
	
	@Query(value = "select * from re_configure_devices rcd where rcd.re_config_box_id =:reConfigureBoxId AND rcd.is_re_configure =1", nativeQuery = true)
	List<ReConfigureDevices> findByReConfigureBoxId(Long reConfigureBoxId);

	default Page<ReConfigureDevices> findAll(GenericRequestBody requestDTO, Pageable pageRequest) {
		try {
			return findAll(search(requestDTO), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<ReConfigureDevices> findAll(Specification<ReConfigureDevices> search, Pageable pageable);

	static Specification<ReConfigureDevices> search(GenericRequestBody entity) {
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
			
			if (entity.getReConfigureBoxId() != null && entity.getReConfigureBoxId() > 0) {
				p.getExpressions().add(cb.equal(root.get("reConfigBoxId"), entity.getReConfigureBoxId()));
				isNotPresent = false;
			}

			if (entity.getId() != null && entity.getId() > 0) {
				p.getExpressions().add(cb.equal(root.get("id"), entity.getId()));
				isNotPresent = false;
			}
			
			if (entity.getImeiNumber() != null && !entity.getImeiNumber().isEmpty()) {
				p.getExpressions().add(cb.equal(root.get("imeiNo"), entity.getImeiNumber()));
				isNotPresent = false;
			}
			
			if (entity.getIsCompleted() != null) {
				p.getExpressions().add(cb.equal(root.get("isReConfigure"), entity.getIsCompleted()));
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
