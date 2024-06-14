package com.watsoo.device.management.dto;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private int totalPages;
	private int numberOfElements;
	private long totalElements;
	private T data;
	private Long totalGpsEquiptedVehicle;
	private Long testedCount;
	private Long devicePackedCount;
	private Long boxPackedCount;
	private Long totalDevice;
	private Long deviceIssuedCount;
	private Long totalOnlineDevice;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getTotalGpsEquiptedVehicle() {
		return totalGpsEquiptedVehicle;
	}

	public void setTotalGpsEquiptedVehicle(Long totalGpsEquiptedVehicle) {
		this.totalGpsEquiptedVehicle = totalGpsEquiptedVehicle;
	}

	public Long getTestedCount() {
		return testedCount;
	}

	public void setTestedCount(Long testedCount) {
		this.testedCount = testedCount;
	}

	public Long getDevicePackedCount() {
		return devicePackedCount;
	}

	public void setDevicePackedCount(Long devicePackedCount) {
		this.devicePackedCount = devicePackedCount;
	}

	public Long getBoxPackedCount() {
		return boxPackedCount;
	}

	public void setBoxPackedCount(Long boxPackedCount) {
		this.boxPackedCount = boxPackedCount;
	}

	public Long getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(Long totalDevice) {
		this.totalDevice = totalDevice;
	}

	public void setError(String string) {

	}

	public Long getDeviceIssuedCount() {
		return deviceIssuedCount;
	}

	public void setDeviceIssuedCount(Long deviceIssuedCount) {
		this.deviceIssuedCount = deviceIssuedCount;
	}

	public Long getTotalOnlineDevice() {
		return totalOnlineDevice;
	}

	public void setTotalOnlineDevice(Long totalOnlineDevice) {
		this.totalOnlineDevice = totalOnlineDevice;
	}

}