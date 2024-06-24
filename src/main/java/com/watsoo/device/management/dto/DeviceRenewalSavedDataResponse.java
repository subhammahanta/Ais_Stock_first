package com.watsoo.device.management.dto;

import java.util.Date;

public class DeviceRenewalSavedDataResponse {

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
