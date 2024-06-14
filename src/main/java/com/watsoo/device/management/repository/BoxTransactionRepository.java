package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.BoxTransaction;

@Repository
public interface BoxTransactionRepository extends JpaRepository<BoxTransaction, Long> {

	@Query(value = "select * from box_transaction b where b.box_id =:boxId", nativeQuery = true)
	BoxTransaction findByBoxId(Long boxId);

}
