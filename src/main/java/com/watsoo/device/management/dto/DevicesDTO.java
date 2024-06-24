package com.watsoo.device.management.dto;

import java.util.Date;

public class DevicesDTO {

    private String imeiNo;

    private String iccidNo;

    private Date oldExpiryDate;

    private Date newExpiryDate;

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getIccidNo() {
        return iccidNo;
    }

    public void setIccidNo(String iccidNo) {
        this.iccidNo = iccidNo;
    }

    public Date getOldExpiryDate() {
        return oldExpiryDate;
    }

    public void setOldExpiryDate(Date oldExpiryDate) {
        this.oldExpiryDate = oldExpiryDate;
    }

    public Date getNewExpiryDate() {
        return newExpiryDate;
    }

    public void setNewExpiryDate(Date newExpiryDate) {
        this.newExpiryDate = newExpiryDate;
    }
}
