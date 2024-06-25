package com.watsoo.device.management.dto;

import java.util.Date;

public class DeviceRenewalSavedDataResponse {

    private boolean isUpdated;

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    private Integer requestId;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    private String iccidNo;

    private Date newExpiryDate;


    public DeviceRenewalSavedDataResponse() {
    }

    public String getIccidNo() {
        return iccidNo;
    }

    public void setIccidNo(String iccidNo) {
        this.iccidNo = iccidNo;
    }



    public Date getNewExpiryDate() {
        return newExpiryDate;
    }

    public void setNewExpiryDate(Date newExpiryDate) {
        this.newExpiryDate = newExpiryDate;
    }
}
