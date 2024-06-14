package com.watsoo.device.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city_state_mapping")
public class CityStateMapping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="city_id")
	private Long cityId;
	
	@Column(name="state_id")
	private Long stateId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public CityStateMapping(Long id, Long cityId, Long stateId) {
		super();
		this.id = id;
		this.cityId = cityId;
		this.stateId = stateId;
	}

	public CityStateMapping() {
		super();
	
	}
		
}
