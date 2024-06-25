package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

public class DeviceRenewalResponseDTO {

    private String reqCode;

    private Long deviceId;

    private String imeiNo;

    private String iccidNo;

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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
}
