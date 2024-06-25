package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

public class RequestRenewalDeviceResponseDTO {

    private String requestCode;

    private Date requestDate;

    private String createdBy;

    private Integer totalDevices;

    List<DeviceDetailsDto> devices;

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(Integer totalDevices) {
        this.totalDevices = totalDevices;
    }

    public List<DeviceDetailsDto> getDetails() {
        return devices;
    }

    public void setDetails(List<DeviceDetailsDto> devices) {
        this.devices = devices;
    }
}
