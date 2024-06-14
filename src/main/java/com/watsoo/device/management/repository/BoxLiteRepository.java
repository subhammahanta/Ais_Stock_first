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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.BoxDTO;
import com.watsoo.device.management.dto.MappingVehicleDTO;
import com.watsoo.device.management.model.BoxLite;

@Repository
public interface BoxLiteRepository extends JpaRepository<BoxLite, Long> {

	default Page<BoxLite> findAll(BoxDTO boxDto, Pageable pageRequest) {
		try {
			return findAll(searchAll(boxDto), pageRequest);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// throw new ResourceNotFoundException("Something went wrong");
		}

	}

	Page<BoxLite> findAll(Specification<BoxLite> searchAll, Pageable pageRequest);

	static Specification<BoxLite> searchAll(BoxDTO boxDto) {
		return (root, cq, cb) -> {
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			if (boxDto.getStartDate() != null && boxDto.getEndDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(boxDto.getStartDate()));
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date stDate = cal.getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date(boxDto.getEndDate()));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				Date enDate = calendar.getTime();
				p.getExpressions().add(cb.between(root.get("createdAt"), stDate, enDate));
				isNotPresent = false;
			}
			if (boxDto.getId() != null) {
				p.getExpressions().add(cb.equal(root.get("id"), boxDto.getId()));
				isNotPresent = false;
			}
			if (boxDto.getStateId() != null) {
				p.getExpressions().add(cb.equal(root.get("state"), boxDto.getStateId()));
				isNotPresent = false;
			}
			if (boxDto.getSearch() != null && !boxDto.getSearch().isEmpty()) {
				p.getExpressions().add(cb.like(root.get("boxNo"), "%" + boxDto.getSearch() + "%"));
				isNotPresent = false;
			}
//			if ((boxDto.getCreatedByName() != null && !boxDto.getCreatedByName().isEmpty())
//					|| (boxDto.getCreatedById() != null && boxDto.getCreatedById() != 0)) {
//				Join<?, ?> joinUser = root.join("createdBy");
//				p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" + boxDto.getCreatedByName() + "%"),
//						cb.equal(joinUser.get("id"), boxDto.getCreatedById())));
//				isNotPresent = false;
//			}
//			if ((boxDto.getUpdatedByName() != null && !boxDto.getUpdatedByName().isEmpty())
//					|| (boxDto.getUpdatedById() != null && boxDto.getUpdatedById() != 0)) {
//				Join<?, ?> joinUser = root.join("updatedBy");
//				p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" + boxDto.getUpdatedByName() + "%"),
//						cb.equal(joinUser.get("id"), boxDto.getUpdatedById())));
//				isNotPresent = false;
//			}
//
//			if (boxDto.getProductStatusMappingId() != null) {
//				p.getExpressions()
//						.add(cb.equal(root.get("productStatusMapping").get("id"), boxDto.getProductStatusMappingId()));
//				isNotPresent = false;
//			}
			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};

	}

	@Query(value = "SELECT * FROM box WHERE status = :status", nativeQuery = true)
	List<BoxLite> findDevicePackedBoxes(String status);

	@Query(value = "SELECT d.id AS Id,d.imei_no AS ImeiNo,d.iccid_no AS IccidNo,d.uuid_no AS UuidNo,"
			+ "d.software_version AS SoftwareVersion,d.detail AS Detail,d.created_at AS CreatedAt,d.updated_at AS UpdatedAt,"
			+ "d.request_body AS RequestBody,d.old_iccid AS OldIccid,"
			+ "d.iccid_updated_at AS IccidUpdatedAt,d.old_imei AS OldImei,"
			+ "d.imei_updated_at AS ImeiUpdatedAt,d.created_by AS CreatedBy," + "d.modified_by AS ModifiedBy,"
			+ "d.state_id AS State,d.status AS Status " + "FROM device d INNER JOIN box_device bd ON d.id=bd.device_id "
			+ "INNER JOIN box b ON b.id=bd.box_id where b.id = ?1", nativeQuery = true)
	List<MappingVehicleDTO> findAllDevicesByBoxId(Long id);

	@Query(value = "SELECT bd.device_id FROM box b INNER JOIN box_device bd ON b.id=bd.box_id WHERE b.id=?1", nativeQuery = true)
	List<Long> getDeviceIds(Long id);

	@Query(value = "select * from box b where b.issue_id = :issuedId", nativeQuery = true)
	List<BoxLite> findByIssueId(Long issuedId);

	@Query(value = "select * from box b where b.id IN(:boxIds) and b.issue_id is not null", nativeQuery = true)
	List<BoxLite> findByIdInAndIssuedList_idNotNull(@Param("boxIds") List<Long> boxIds);

	@Query(value = "delete FROM box where id IN(:boxIds) and id>0", nativeQuery = true)
	void deleteAllByIdIn(@Param("boxIds") List<Long> boxIds);

	@Query(value = "select * from box b where b.box_no IN (:boxIds) AND b.status =:status", nativeQuery = true)
	List<BoxLite> findBoxesByBoxNo(List<String> boxIds, String status);

	@Query(value = "SELECT b.box_no FROM box b inner join box_device bd on bd.box_id=b.id where bd.is_Active=1 and bd.is_present=1 and bd.device_id = :id", nativeQuery = true)
	String findBoxNoByDeviceId(Long id);
	
//	@Modifying
//	@Transactional
	@Query(value = "DELETE FROM box b WHERE b.id = :boxId", nativeQuery = true)
	void deleteByBoxId(@Param("boxId") Long boxId);

}
