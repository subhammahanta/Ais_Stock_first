package com.watsoo.device.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	@Query(value = "select * from state where lower(reference_key) like :stateRefKey limit 1", nativeQuery = true)
	State findbyReferenceKey(String stateRefKey);

}
