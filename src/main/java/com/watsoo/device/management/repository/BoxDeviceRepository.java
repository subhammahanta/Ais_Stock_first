package com.watsoo.device.management.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.watsoo.device.management.model.BoxDevice;

public interface BoxDeviceRepository extends JpaRepository<BoxDevice, Long> {

	@Query(value = "select * from box_device bd where bd.box_id=:boxId AND bd.is_active=true", nativeQuery = true)
	List<BoxDevice> FindByBox_idAndIsActiveTrue(@Param("boxId") Long boxId);

	@Query(value = "select * from box_device bd where bd.box_id=:boxId", nativeQuery = true)
	List<BoxDevice> getByBoxIds(List<Long> boxId);

	@Query(value = "select * from box_device bd where bd.box_id=:boxId", nativeQuery = true)
	List<BoxDevice> getByBoxId(Long boxId);

	@Query(value = "select * from box_device bd where bd.box_id IN(:setOfBoxid)", nativeQuery = true)
	List<BoxDevice> getAll(Set<Long> setOfBoxid);

	List<BoxDevice> findByBox_idAndIsActiveTrue(Long id);

	List<BoxDevice> findByBox_idInAndIsActiveTrue(List<Long> boxIdList);

	@Query(value = "delete FROM box_device where id IN(:boxdeviceIds) and id>0", nativeQuery = true)
	void deleteAllByIdIn(List<Long> boxdeviceIds);

	@Query(value = "SELECT * FROM box_device bd where bd.box_id IN(:boxId)", nativeQuery = true)
	List<BoxDevice> findAllByIds(Long boxId);

	// @Modifying
	// @Transactional
	@Query(value = "DELETE FROM box_device bd WHERE bd.box_id = :boxId", nativeQuery = true)
	void deleteByBoxId(@Param("boxId") Long boxId);

	@Query(value = "select * from box_device bd where bd.device_id IN(:devicesId)", nativeQuery = true)
	List<BoxDevice> findByDeviceId(List<Long> devicesId);

	@Query(value = "select * from box_device bd where bd.device_id =:deviceId AND is_present=1", nativeQuery = true)
	BoxDevice findByDeviceIdV2(Long deviceId);

	@Query(value = "select * from box_device bd where  bd.box_id =:boxId AND bd.device_id =:deviceId", nativeQuery = true)
	BoxDevice findByBoxIdAndDeviceId(Long boxId, Long deviceId);
	
	@Query(value = "SELECT * FROM box_device bd where bd.box_id IN(:boxId) AND bd.is_present=1", nativeQuery = true)
	List<BoxDevice> findAllByIdsAndIsPresent(Long boxId);

}
