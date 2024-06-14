package com.watsoo.device.management.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.enums.LastOnline;
import com.watsoo.device.management.exception.ResourceNotFoundException;
import com.watsoo.device.management.model.DeviceLite;
import com.watsoo.device.management.util.DateUtil;

@Repository
public interface DeviceLiteRepository extends JpaRepository<DeviceLite, Long> {

	Optional<DeviceLite> findByImeiNo(String imeiNo);

	Optional<DeviceLite> findByIccidNo(String iccidNo);

	default Page<DeviceLite> findAll(GenericRequestBody genericRequestBody, Pageable pageRequest) {
		try {
			return findAll(search(genericRequestBody), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	default Page<DeviceLite> findAllV2(GenericRequestBody genericRequestBody, Pageable pageRequest) {
		try {
			return findAll(search2(genericRequestBody), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<DeviceLite> findAll(Specification<DeviceLite> search, Pageable pageable);

	static Specification<DeviceLite> search(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			// To ensure we start with a predicate
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
				p.getExpressions().add(cb.between(root.get("updatedAt"), dateFrom, dateTo));
				isNotPresent = false;
			}

			// if (entity.getCreatedBy() != null) {
			// p.getExpressions().add(cb.equal(root.get("createdBy"),
			// entity.getCreatedBy()));
			// isNotPresent = false;
			// }

			if ((entity.getCreatedByName() != null && !entity.getCreatedByName().isEmpty())
					|| (entity.getCreatedById() != null && entity.getCreatedById() != 0)) {
				Join<?, ?> joinUser = root.join("createdBy");
				p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" + entity.getCreatedByName() + "%"),
						cb.equal(joinUser.get("id"), entity.getCreatedById())));
				isNotPresent = false;
			}
			// if ((entity.getUpdatedByName() != null &&
			// !entity.getUpdatedByName().isEmpty())
			// || (entity.getUpdatedById() != null && entity.getUpdatedById() != 0)) {
			// Join<?, ?> joinUser = root.join("updatedBy");
			// p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" +
			// entity.getUpdatedByName() + "%"),
			// cb.equal(joinUser.get("id"), entity.getUpdatedById())));
			// isNotPresent = false;
			// }

			if (entity.getStateId() != null) {
				p.getExpressions().add(cb.equal(root.get("state").get("id"), entity.getStateId()));
				isNotPresent = false;
			}

			if (entity.getStatusMaster() != null && !entity.getStatusMaster().name().isEmpty()
					&& entity.getStatusMaster().name() != null) {
				p.getExpressions().add(cb.equal(root.get("status"), entity.getStatusMaster()));
				isNotPresent = false;
			}

			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				p.getExpressions()
						.add(cb.or(cb.equal(root.get("imeiNo"), entity.getSearch()),
								cb.equal(root.get("iccidNo"), entity.getSearch()),
								cb.equal(root.get("uuidNo"), entity.getSearch())));
				isNotPresent = false;
			}

			if (entity.getImeiOrBoxNoList() != null && !entity.getImeiOrBoxNoList().isEmpty()) {
				p.getExpressions().add(cb.or(cb.in(root.get("imeiNo")).value(entity.getImeiOrBoxNoList()),
						cb.in(root.get("boxCode")).value(entity.getImeiOrBoxNoList())));
				isNotPresent = false;
			}

			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	static Specification<DeviceLite> search2(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			// To ensure we start with a predicate
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			boolean isDateSelected = false;
			//
			LocalDate currentDate = LocalDate.now();
			LocalTime midnight = LocalTime.MIDNIGHT;
			LocalDateTime midnightToday = LocalDateTime.of(currentDate, midnight);
			Date dateMidnightToday = Date.from(midnightToday.atZone(ZoneId.systemDefault()).toInstant());
			// LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
			Date today = new Date();
			// Date yesterday =
			// Date.from(twentyFourHoursAgo.atZone(ZoneId.systemDefault()).toInstant());
			// Date fromDateFilter = new Date();
			Date fromDateFilter = new Date(121, 0, 1, 0, 0, 0);
			Date toDateFilter = new Date();
			if (entity.getLastOnline() != null && !entity.getLastOnline().name().isEmpty()
					&& entity.getLastOnline().name() != null) {
				if (entity.getLastOnline() == LastOnline.TWO_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(2);
				} else if (entity.getLastOnline() == LastOnline.THREE_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(3);
				} else if (entity.getLastOnline() == LastOnline.SEVEN_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(7);
				} else if (entity.getLastOnline() == LastOnline.TEN_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(10);
				} else if (entity.getLastOnline() == LastOnline.FIFTEEN_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(15);
				} else if (entity.getLastOnline() == LastOnline.TWENTY_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(20);
				} else if (entity.getLastOnline() == LastOnline.THIRTY_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(30);
				} else if (entity.getLastOnline() == LastOnline.MORE_THEN_THIRTY_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(31);
				}
			}

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
				if (entity.getIsConfigActive() != null) {
					if (entity.getIsConfigActive()) {
						p.getExpressions().add(cb.equal(root.get("isConfigActivated"), true));
						p.getExpressions().add(cb.between(root.get("deviceActivatedDate"), dateFrom, dateTo));
						isDateSelected = true;
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isConfigActivated"), false),
								cb.isNull(root.get("isConfigActivated"))));
					}

				}
				if (entity.getIsConfigDone() != null) {
					if (!isDateSelected && entity.getIsConfigDone()) {
						p.getExpressions().add(cb.between(root.get("configDoneDate"), dateFrom, dateTo));
						isDateSelected = true;
					}
					if (entity.getIsConfigDone()) {
						p.getExpressions().add(cb.equal(root.get("isConfigurationComplete"), true));
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isConfigurationComplete"), false),
								cb.isNull(root.get("isConfigurationComplete"))));
					}
				}
				if (entity.getIsConfigSent() != null) {

					if (!isDateSelected && entity.getIsConfigSent()) {
						p.getExpressions().add(cb.between(root.get("commandSendDate"), dateFrom, dateTo));
						isDateSelected = true;
					}
					if (entity.getIsConfigSent()) {
						p.getExpressions().add(cb.equal(root.get("isCommandSend"), true));
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isCommandSend"), false),
								cb.isNull(root.get("isCommandSend"))));
					}

				}

				if (!isDateSelected) {
					p.getExpressions().add(cb.between(root.get("createdAt"), dateFrom, dateTo));
				}
				isNotPresent = false;
			}

			// if (entity.getCreatedBy() != null) {
			// p.getExpressions().add(cb.equal(root.get("createdBy"),
			// entity.getCreatedBy()));
			// isNotPresent = false;
			// }

			if ((entity.getCreatedByName() != null && !entity.getCreatedByName().isEmpty())
					|| (entity.getCreatedById() != null && entity.getCreatedById() != 0)) {
				Join<?, ?> joinUser = root.join("createdBy");
				p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" + entity.getCreatedByName() + "%"),
						cb.equal(joinUser.get("id"), entity.getCreatedById())));
				isNotPresent = false;
			}
			// if ((entity.getUpdatedByName() != null &&
			// !entity.getUpdatedByName().isEmpty())
			// || (entity.getUpdatedById() != null && entity.getUpdatedById() != 0)) {
			// Join<?, ?> joinUser = root.join("updatedBy");
			// p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" +
			// entity.getUpdatedByName() + "%"),
			// cb.equal(joinUser.get("id"), entity.getUpdatedById())));
			// isNotPresent = false;
			// }

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

			if (entity.getOnlineDevice() != null && entity.getOnlineDevice()) {
				p.getExpressions().add(cb.between(root.get("packedDate"), dateMidnightToday, today));
				isNotPresent = false;
			}

			if (entity.getLastOnline() != null && !entity.getLastOnline().name().isEmpty()
					&& entity.getLastOnline().name() != null) {
				p.getExpressions().add(cb.between(root.get("packedDate"), fromDateFilter, toDateFilter));
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

	@Query(value = "select * from device where DATE(created_at)=DATE(:today)", nativeQuery = true)
	List<DeviceLite> findByCreatedAt(@Param("today") String today);

	Page<DeviceLite> findAllByStateId(Long stateId, Pageable pageRequest);

	@Query(value = "select * from box_device where box_id=:boxId", nativeQuery = true)
	List<DeviceLite> findByBoxId(Long boxId);

	@Query(value = "SELECT bd.box_id FROM device b INNER JOIN box_device bd ON b.id=bd.box_id WHERE b.id=?1", nativeQuery = true)
	List<Long> getBoxIds(Long id);

	@Query(value = "SELECT d.* FROM device d INNER JOIN box_device bd ON d.box_id = bd.box_id WHERE bd.box_id IN :boxIds", nativeQuery = true)
	List<DeviceLite> getByBoxId(@Param("boxIds") List<Long> boxIds);

	@Query(value = "select * from device bd where bd.id IN(:deviceSet)", nativeQuery = true)
	List<DeviceLite> findBySetOfIds(Set<Long> deviceSet);

	List<DeviceLite> findAllByImeiNoIn(List<String> imeiNos);

	@Query(value = "select * from device where issue_date IS NOT NULL AND issue_date <=:today AND ( is_configuration_complete IS NULL OR is_configuration_complete != 1)", nativeQuery = true)
	List<DeviceLite> findNotConfigDevices(@Param("today") String today);

	default Page<DeviceLite> findAllV3(GenericRequestBody genericRequestBody, Pageable pageRequest) {
		try {
			return findAll(searchV3(genericRequestBody), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static Specification<DeviceLite> searchV3(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				// p.getExpressions().add(cb.like(root.get("imeiNo"), "%" + entity.getSearch() +
				// "%"));
				// isNotPresent = false;
				p.getExpressions()
						.add(cb.or(cb.like(root.get("imeiNo"), "%" + entity.getSearch() + "%"),
								cb.equal(root.get("iccidNo"), "%" + entity.getSearch() + "%"),
								cb.equal(root.get("uuidNo"), "%" + entity.getSearch() + "%")));
				isNotPresent = false;
			}
			if (entity.getStatus() != null && !entity.getStatus().isEmpty()) {
				p.getExpressions().add(cb.in(root.get("status")).value(entity.getStatus()));
				isNotPresent = false;
			}
			if (entity.getClientId() != null) {
				p.getExpressions().add(cb.equal(root.get("clientId"), entity.getClientId()));
				isNotPresent = false;
			}
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	default List<DeviceLite> findAllBySearch(GenericRequestBody request) {
		try {
			return findAll(searchAll(request));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Something went wrong");
		}
	}

	static Specification<DeviceLite> searchAll(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			// To ensure we start with a predicate
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			boolean isDateSelected = false;
			LocalDate currentDate = LocalDate.now();
			LocalTime midnight = LocalTime.MIDNIGHT;
			LocalDateTime midnightToday = LocalDateTime.of(currentDate, midnight);
			Date dateMidnightToday = Date.from(midnightToday.atZone(ZoneId.systemDefault()).toInstant());
			Date today = new Date();
			Date fromDateFilter = new Date(121, 0, 1, 0, 0, 0);
			Date toDateFilter = new Date();
			if (entity.getLastOnline() != null && !entity.getLastOnline().name().isEmpty()
					&& entity.getLastOnline().name() != null) {
				if (entity.getLastOnline() == LastOnline.TWO_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(2);
				} else if (entity.getLastOnline() == LastOnline.THREE_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(3);
				} else if (entity.getLastOnline() == LastOnline.SEVEN_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(7);
				} else if (entity.getLastOnline() == LastOnline.TEN_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(10);
				} else if (entity.getLastOnline() == LastOnline.FIFTEEN_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(15);
				} else if (entity.getLastOnline() == LastOnline.TWENTY_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(20);
				} else if (entity.getLastOnline() == LastOnline.THIRTY_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(30);
				} else if (entity.getLastOnline() == LastOnline.MORE_THEN_THIRTY_DAYS_AGO) {
					toDateFilter = DateUtil.getStartDateTimeOfTheDay(31);
				}
			}

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
				if (entity.getIsConfigActive() != null) {
					if (entity.getIsConfigActive()) {
						p.getExpressions().add(cb.equal(root.get("isConfigActivated"), true));
						p.getExpressions().add(cb.between(root.get("deviceActivatedDate"), dateFrom, dateTo));
						isDateSelected = true;
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isConfigActivated"), false),
								cb.isNull(root.get("isConfigActivated"))));
					}

				}
				if (entity.getIsConfigDone() != null) {
					if (!isDateSelected && entity.getIsConfigDone()) {
						p.getExpressions().add(cb.between(root.get("configDoneDate"), dateFrom, dateTo));
						isDateSelected = true;
					}
					if (entity.getIsConfigDone()) {
						p.getExpressions().add(cb.equal(root.get("isConfigurationComplete"), true));
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isConfigurationComplete"), false),
								cb.isNull(root.get("isConfigurationComplete"))));
					}
				}
				if (entity.getIsConfigSent() != null) {

					if (!isDateSelected && entity.getIsConfigSent()) {
						p.getExpressions().add(cb.between(root.get("commandSendDate"), dateFrom, dateTo));
						isDateSelected = true;
					}
					if (entity.getIsConfigSent()) {
						p.getExpressions().add(cb.equal(root.get("isCommandSend"), true));
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isCommandSend"), false),
								cb.isNull(root.get("isCommandSend"))));
					}

				}

				if (!isDateSelected) {
					p.getExpressions().add(cb.between(root.get("updatedAt"), dateFrom, dateTo));
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

			if (entity.getOnlineDevice() != null && entity.getOnlineDevice()) {
				p.getExpressions().add(cb.between(root.get("packedDate"), dateMidnightToday, today));
				isNotPresent = false;
			}

			if (entity.getLastOnline() != null && !entity.getLastOnline().name().isEmpty()
					&& entity.getLastOnline().name() != null) {
				p.getExpressions().add(cb.between(root.get("packedDate"), fromDateFilter, toDateFilter));
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

	List<DeviceLite> findAll(Specification<DeviceLite> search);

	@Query(value = "select * from device where imei_no = :search OR iccid_no = :search OR uuid_no = :search", nativeQuery = true)
	DeviceLite findByImeiOrIccidOrUuid(@Param("search") String search);

	@Query(value = "select * from device where box_code=:boxCode", nativeQuery = true)
	List<DeviceLite> findByBoxCode(String boxCode);

	@Query(value = "select * from device where imei_no = :imei ", nativeQuery = true)
	List<DeviceLite> findByImei(String imei);
}
