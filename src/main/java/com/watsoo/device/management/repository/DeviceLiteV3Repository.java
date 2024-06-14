package com.watsoo.device.management.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.DeviceLiteV3;

@Repository
public interface DeviceLiteV3Repository extends JpaRepository<DeviceLiteV3, Long> {

	default Page<DeviceLiteV3> findAll(GenericRequestBody genericRequestBody, Pageable pageRequest) {
		try {
			return findAll(search(genericRequestBody), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<DeviceLiteV3> findAll(Specification<DeviceLiteV3> search, Pageable pageable);
	
	static Specification<DeviceLiteV3> search(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			// To ensure we start with a predicate
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			boolean isDateSelected = false;
			//
			//LocalDate currentDate = LocalDate.now();
			//LocalTime midnight = LocalTime.MIDNIGHT;
			//LocalDateTime midnightToday = LocalDateTime.of(currentDate, midnight);
			//Date dateMidnightToday = Date.from(midnightToday.atZone(ZoneId.systemDefault()).toInstant());
			// LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
			//Date today = new Date();
			// Date yesterday =
			// Date.from(twentyFourHoursAgo.atZone(ZoneId.systemDefault()).toInstant());
			// Date fromDateFilter = new Date();
//			Date fromDateFilter = new Date(121, 0, 1, 0, 0, 0);


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
				
				if (!isDateSelected) {
					p.getExpressions().add(cb.between(root.get("createdAt"), dateFrom, dateTo));
				}
				isNotPresent = false;
			}

			if ((entity.getCreatedByName() != null && !entity.getCreatedByName().isEmpty())
					|| (entity.getCreatedById() != null && entity.getCreatedById() != 0)) {
				Join<?, ?> joinUser = root.join("createdBy");
				p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" + entity.getCreatedByName() + "%"),
						cb.equal(joinUser.get("id"), entity.getCreatedById())));
				isNotPresent = false;
			}
			if (entity.getStateId() != null) {
				p.getExpressions().add(cb.equal(root.get("state").get("id"), entity.getStateId()));
				isNotPresent = false;
			}

			if (entity.getStatusMaster() != null && !entity.getStatusMaster().name().isEmpty()
					&& entity.getStatusMaster().name() != null) {
				p.getExpressions().add(cb.equal(root.get("status"), entity.getStatusMaster()));
				isNotPresent = false;
			}
			if (entity.getImeiNumber() != null && !entity.getImeiNumber().isEmpty()) {
				p.getExpressions().add(cb.equal(root.get("imeiNo"), entity.getImeiNumber()));
				isNotPresent = false;
			}
			if (entity.getClientIds() != null && !entity.getClientIds().isEmpty()) {
				p.getExpressions().add(cb.in(root.get("clientId")).value(entity.getClientIds()));

				isNotPresent = false;
			}
			// change to like operator //
			if (entity.getSoftwareVersion() != null && !entity.getSoftwareVersion().isEmpty()) {
				p.getExpressions().add(cb.like(root.get("softwareVersion"), "%" + entity.getSoftwareVersion() + "%"));

				isNotPresent = false;
			}

			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				p.getExpressions()
						.add(cb.or(cb.like(root.get("imeiNo"), "%" + entity.getSearch() + "%"),
								cb.equal(root.get("iccidNo"), entity.getSearch()),
								cb.equal(root.get("uuidNo"), entity.getSearch())));
				isNotPresent = false;
			}

			
			if (entity.getSimOperator() != null && !entity.getSimOperator().isEmpty()) {
				p.getExpressions().add(cb.like(root.get("sim1Provider"), entity.getSimOperator()));
				isNotPresent = false;
			}

			// list of software version search
			if (entity.getSoftwareVersionList() != null && !entity.getSoftwareVersionList().isEmpty()) {
				List<Predicate> predicates = new ArrayList<>();
				for (String version : entity.getSoftwareVersionList()) {
					predicates.add(cb.like(root.get("softwareVersion"), "%" + version + "%"));
				}
				if (predicates.size() > 0) {
					p.getExpressions().add(cb.or(predicates.toArray(new Predicate[0])));
					isNotPresent = false;
				}
			}

			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}
}
