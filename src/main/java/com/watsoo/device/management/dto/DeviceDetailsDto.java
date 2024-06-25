package com.watsoo.device.management.dto;

import java.util.Date;

public class DeviceDetailsDto {

//    private String deviceImei;
//
//    private Long deviceId;
//
//    private Long requestId;
//
//    public Long getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(Long deviceId) {
//        this.deviceId = deviceId;
//    }
//
//
//    public Long getRequestId() {
//        return requestId;
//    }
//
//    public void setRequestId(Long requestId) {
//        this.requestId = requestId;
//    }
//
//    private String iccId;
//
//    private Date oldExpDate;
//
//    private Date newExpDate;
//
//    public String getDeviceImei() {
//        return deviceImei;
//    }
//
//    public void setDeviceImei(String deviceImei) {
//        this.deviceImei = deviceImei;
//    }
//
//    public String getIccId() {
//        return iccId;
//    }
//
//    public void setIccId(String iccId) {
//        this.iccId = iccId;
//    }
//
//    public Date getOldExpDate() {
//        return oldExpDate;
//    }
//
//    public void setOldExpDate(Date oldExpDate) {
//        this.oldExpDate = oldExpDate;
//    }
//
//    public Date getNewExpDate() {
//        return newExpDate;
//    }
//
//    public void setNewExpDate(Date newExpDate) {
//        this.newExpDate = newExpDate;
//    }

    private  Long id;

    private Long deviceId;

    private Long requestId;

    private String imeiNo;

    private String iccidNo;

    private Date oldExpiryDate;

    private Date newExpiryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

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
