package com.watsoo.device.management.dto;

public class DeviceRenewal {

    private String iccidNo;

    private String date;


    public DeviceRenewal() {
    }

    public DeviceRenewal(String iccidNo, String date) {
        this.iccidNo = iccidNo;
        this.date = date;
    }

    public String getIccidNo() {
        return iccidNo;
    }

    public void setIccidNo(String iccidNo) {
        this.iccidNo = iccidNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
