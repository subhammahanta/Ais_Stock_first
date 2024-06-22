package com.watsoo.device.management.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "renewal_device")
public class RenewalDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "request_id")
    private Long requestId;

    public RenewalDevice() {
    }

    public RenewalDevice(Long deviceId, Long requestId, String imeiNo, String iccidNo, Date oldExpiryDate, Date newExpiryDate) {
        this.deviceId = deviceId;
        this.requestId = requestId;
        this.imeiNo = imeiNo;
        this.iccidNo = iccidNo;
        this.oldExpiryDate = oldExpiryDate;
        this.newExpiryDate = newExpiryDate;
    }



    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    @Column(name = "imei_no")
    private String imeiNo;

    @Column(name = "iccid_no")
    private String iccidNo;

    @Column(name = "old_expiry_date")
    private Date oldExpiryDate;

    @Column(name = "new_expiry_date")
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
