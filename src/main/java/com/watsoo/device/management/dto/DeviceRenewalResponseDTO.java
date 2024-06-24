package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

public class DeviceRenewalResponseDTO {

    private String reqCode;

    private Integer totalDevice;

    private Integer id;

    private Date createdAt;

    private String createdBy;

    private List<DeviceRenewalDetails> details;

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public Integer getTotalDevice() {
        return totalDevice;
    }

    public void setTotalDevice(Integer totalDevice) {
        this.totalDevice = totalDevice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<DeviceRenewalDetails> getDetails() {
        return details;
    }

    public void setDetails(List<DeviceRenewalDetails> details) {
        this.details = details;
    }
}
