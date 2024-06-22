package com.watsoo.device.management.dto;

import java.util.List;

public class DeviceRenewalRequestDTO {



    private int userId;

   private   List<DeviceRenewal> deviceRenewalList;

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<DeviceRenewal> getDeviceRenewalList() {
        return deviceRenewalList;
    }

    public void setDeviceRenewalList(List<DeviceRenewal> deviceRenewalList) {
        this.deviceRenewalList = deviceRenewalList;
    }

    public DeviceRenewalRequestDTO(int userId, List<DeviceRenewal> devicRenewalList) {
        this.userId = userId;
        this.deviceRenewalList = devicRenewalList;
    }

    public DeviceRenewalRequestDTO() {

    }
}
