package com.watsoo.device.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "provider")
public class Provider {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "iccid_length_limit")
    private int iccidLengthLimit;
	
	public Provider() {
		super();
	}

	public Provider(Long id, String name, int iccidLengthLimit) {
		super();
		this.id = id;
		this.name = name;
		this.iccidLengthLimit = iccidLengthLimit;
	}

	public Provider(Long id) {
		super();
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIccidLengthLimit() {
		return iccidLengthLimit;
	}

	public void setIccidLengthLimit(int iccidLengthLimit) {
		this.iccidLengthLimit = iccidLengthLimit;
	}

}
