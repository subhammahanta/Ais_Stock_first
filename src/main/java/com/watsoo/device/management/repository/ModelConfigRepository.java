package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.model.ModelConfig;

@Repository
public interface ModelConfigRepository extends JpaRepository<ModelConfig, Long> {

	@Query(value = "select * from model_config m where m.state_id =:stateId AND m.model_id =:modelId AND m.is_active=1", nativeQuery = true)
	List<ModelConfig> findByStateAndModelId(Long stateId, Long modelId);

	@Query(value = "select * from model_config m where m.state_id =:stateId AND m.model_id =:modelId AND m.provider_id =:providerId AND m.operator_id=:operatorId AND m.client_id=:clientId AND m.is_active=1 ", nativeQuery = true)
	List<ModelConfig> findByStateIdAndModelIdAndProviderIdAndOperatorIdAndClientId(Long stateId, Long modelId,
			Long providerId, Long operatorId, Long clientId);

	@Query(value = "select * from model_config m where m.state_id =:stateId AND m.model_id =:modelId AND m.provider_id =:providerId AND m.operator_id=:operatorId AND m.is_active=1 AND m.is_default=1 ", nativeQuery = true)
	List<ModelConfig> findByStateIdAndModelIdAndProviderIdAndOperatorId(Long stateId, Long modelId, Long providerId,
			Long operatorId);
	
	@Query(value = "select * from model_config m where m.state_id =:stateId AND m.is_active=1 AND m.is_default=1", nativeQuery = true)
	List<ModelConfig> findByState(Long stateId);

}
