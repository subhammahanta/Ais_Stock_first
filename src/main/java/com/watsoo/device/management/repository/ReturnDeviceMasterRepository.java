package com.watsoo.device.management.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.Client;
import com.watsoo.device.management.model.RepairReplaceDeviceMaster;
import com.watsoo.device.management.model.ReturnDeviceMaster;

@Repository
public interface ReturnDeviceMasterRepository extends JpaRepository<ReturnDeviceMaster, Long> {

	default	Page<ReturnDeviceMaster> findAll(GenericRequestBody requestDTO, Pageable pageRequest){
	
		try {
			return findAll(search(requestDTO), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<ReturnDeviceMaster> findAll(Specification<ReturnDeviceMaster> search, Pageable pageable);

	static Specification<ReturnDeviceMaster> search(GenericRequestBody entity) {
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
			
			if (entity.getId() != null && entity.getId() > 0) {
				p.getExpressions().add(cb.equal(root.get("id"), entity.getId()));
				isNotPresent = false;
			}
//			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
//				p.getExpressions().add(cb.like(root.get("reqCode"), "%" + entity.getSearch() + "%"));
//				isNotPresent = false;
//			}
			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				Join<ReturnDeviceMaster, Client> clientJoin = root.join("client");
				p.getExpressions()
						.add(cb.or(cb.like(clientJoin.get("companyName"), "%" + entity.getSearch() + "%"),
								cb.like(root.get("reqCode"), "%" + entity.getSearch() + "%"),
								cb.like(root.get("ewayBillNo"), "%" + entity.getSearch() + "%"),
								cb.like(root.get("debitNoteNo"), "%" + entity.getSearch() + "%")));
				isNotPresent = false;
			}
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	@Query(value = "SELECT * FROM return_device_master r WHERE r.debit_note_no =?1 OR r.eway_bill_no =?2", nativeQuery = true)
	List<ReturnDeviceMaster> findByDebitNoteAndEwayBill(String debitNoteNo, String ewayBillNo);


}
