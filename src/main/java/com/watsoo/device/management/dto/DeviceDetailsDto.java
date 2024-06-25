package com.watsoo.device.management.dto;

import java.util.Date;

public class DeviceDetailsDto {

    private String deviceImei;

    private Long deviceId;

    private Long requestId;

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

    private String iccId;

    private Date oldExpDate;

    private Date newExpDate;

    public String getDeviceImei() {
        return deviceImei;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }

    public Date getOldExpDate() {
        return oldExpDate;
    }

    public void setOldExpDate(Date oldExpDate) {
        this.oldExpDate = oldExpDate;
    }

    public Date getNewExpDate() {
        return newExpDate;
    }

    public void setNewExpDate(Date newExpDate) {
        this.newExpDate = newExpDate;
    }
}
