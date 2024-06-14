package com.watsoo.device.management.dto;

import java.io.Serializable;
import java.util.Date;

public class VehicleLazyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String number;

	private String image;

	private String chassisNumber;

	private String engineNumber;

	private Long vehicleType;

	private Double weightLimit;

	private Long driver;

	private Long company;

	private Long gps;

	private Long site;

	private Long vendor;

	private String deviceAlternateNo;

	private Boolean fuelSensorEnabled;

	private Double engineHoursPerLiter;

	private Double kmPerLiter;

	private Date createdAt;

	private Long createdBy;

	private Date updatedAt;

	private Long updatedBy;

	private boolean isActive;

	private Long gpsRefId;

	private Boolean isRunning;

	private Date speedEvent;

	private Double fuelTankCapacity;

	private Boolean inMaintenanceProcess;

	private Long maintenanceId;

	private String lastRfidId;

	//private VehicleMaintenanceCategory vehicleMaintenanceCategory;

	private String manufactureYear;

	private Date registrationDate;

	private String vtsModel;

	private String vtsTacNo;

	private Long panicButtonNo;

	private String imeiNo;

	private String rotorSealNo;

	private String invoiceNo;

	private String iccidNo;

	private Date installationDate;

	private Date invoiceDate;

	private String uid;

	private String esimValidity;

	private String simNo;

	private String installedBy;

	private String installerAddress;

	private String certificateNo;

	//private ModelMaster modelNo;

	private String state;

	private String rto;

	private String makerModel;

	private String engineImg;

	private String chassisImg;

	private String vehicleImg;

	private String vehicleManufacturer;

	private String vehicleModel;

	private String sim2No;

	private Boolean isVehicleActivated;

	//private VehicleActivationStatus vehicleActivationStatus;

	private Boolean engineImmboliser;

	private Long kycMasterId;

	//private KycStatusEnum kycStatus;

	private String tcPacket;

	private Date packetTimeStamp;

	private Boolean isNumberSync;
	
	private String sim1Operator;

	private String sim2Operator;

	private Date sim1ActivationDate;

	private Date sim1ExpiryDate;

	private Date sim2ActivationDate;

	private Date sim2ExpiryDate;
	
	private String sim1Provider;
	
	private String sim2Provider;
	
	private Date appExpiryDate;
	
	private Boolean isRenewalNotify;

	private Integer simExpireDays;

	private Integer appExpireDays;

	private Boolean isActivationType;

	public VehicleLazyEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Double getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(Double weightLimit) {
		this.weightLimit = weightLimit;
	}

	public Long getDriver() {
		return driver;
	}

	public void setDriver(Long driver) {
		this.driver = driver;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Long getGps() {
		return gps;
	}

	public void setGps(Long gps) {
		this.gps = gps;
	}

	public Long getSite() {
		return site;
	}

	public void setSite(Long site) {
		this.site = site;
	}

	public Long getVendor() {
		return vendor;
	}

	public void setVendor(Long vendor) {
		this.vendor = vendor;
	}

	public String getDeviceAlternateNo() {
		return deviceAlternateNo;
	}

	public void setDeviceAlternateNo(String deviceAlternateNo) {
		this.deviceAlternateNo = deviceAlternateNo;
	}

	public Boolean getFuelSensorEnabled() {
		return fuelSensorEnabled;
	}

	public void setFuelSensorEnabled(Boolean fuelSensorEnabled) {
		this.fuelSensorEnabled = fuelSensorEnabled;
	}

	public Double getEngineHoursPerLiter() {
		return engineHoursPerLiter;
	}

	public void setEngineHoursPerLiter(Double engineHoursPerLiter) {
		this.engineHoursPerLiter = engineHoursPerLiter;
	}

	public Double getKmPerLiter() {
		return kmPerLiter;
	}

	public void setKmPerLiter(Double kmPerLiter) {
		this.kmPerLiter = kmPerLiter;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getGpsRefId() {
		return gpsRefId;
	}

	public void setGpsRefId(Long gpsRefId) {
		this.gpsRefId = gpsRefId;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Date getSpeedEvent() {
		return speedEvent;
	}

	public void setSpeedEvent(Date speedEvent) {
		this.speedEvent = speedEvent;
	}

	public Double getFuelTankCapacity() {
		return fuelTankCapacity;
	}

	public void setFuelTankCapacity(Double fuelTankCapacity) {
		this.fuelTankCapacity = fuelTankCapacity;
	}

	public Boolean getInMaintenanceProcess() {
		return inMaintenanceProcess;
	}

	public void setInMaintenanceProcess(Boolean inMaintenanceProcess) {
		this.inMaintenanceProcess = inMaintenanceProcess;
	}

	public Long getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(Long maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	public String getLastRfidId() {
		return lastRfidId;
	}

	public void setLastRfidId(String lastRfidId) {
		this.lastRfidId = lastRfidId;
	}

	public String getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(String manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getVtsModel() {
		return vtsModel;
	}

	public void setVtsModel(String vtsModel) {
		this.vtsModel = vtsModel;
	}

	public String getVtsTacNo() {
		return vtsTacNo;
	}

	public void setVtsTacNo(String vtsTacNo) {
		this.vtsTacNo = vtsTacNo;
	}

	public Long getPanicButtonNo() {
		return panicButtonNo;
	}

	public void setPanicButtonNo(Long panicButtonNo) {
		this.panicButtonNo = panicButtonNo;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getRotorSealNo() {
		return rotorSealNo;
	}

	public void setRotorSealNo(String rotorSealNo) {
		this.rotorSealNo = rotorSealNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getIccidNo() {
		return iccidNo;
	}

	public void setIccidNo(String iccidNo) {
		this.iccidNo = iccidNo;
	}

	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEsimValidity() {
		return esimValidity;
	}

	public void setEsimValidity(String esimValidity) {
		this.esimValidity = esimValidity;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getInstalledBy() {
		return installedBy;
	}

	public void setInstalledBy(String installedBy) {
		this.installedBy = installedBy;
	}

	public String getInstallerAddress() {
		return installerAddress;
	}

	public void setInstallerAddress(String installerAddress) {
		this.installerAddress = installerAddress;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRto() {
		return rto;
	}

	public void setRto(String rto) {
		this.rto = rto;
	}

	public String getMakerModel() {
		return makerModel;
	}

	public void setMakerModel(String makerModel) {
		this.makerModel = makerModel;
	}

	public String getEngineImg() {
		return engineImg;
	}

	public void setEngineImg(String engineImg) {
		this.engineImg = engineImg;
	}

	public String getChassisImg() {
		return chassisImg;
	}

	public void setChassisImg(String chassisImg) {
		this.chassisImg = chassisImg;
	}

	public String getVehicleImg() {
		return vehicleImg;
	}

	public void setVehicleImg(String vehicleImg) {
		this.vehicleImg = vehicleImg;
	}

	public String getVehicleManufacturer() {
		return vehicleManufacturer;
	}

	public void setVehicleManufacturer(String vehicleManufacturer) {
		this.vehicleManufacturer = vehicleManufacturer;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getSim2No() {
		return sim2No;
	}

	public void setSim2No(String sim2No) {
		this.sim2No = sim2No;
	}

	public Boolean getIsVehicleActivated() {
		return isVehicleActivated;
	}

	public void setIsVehicleActivated(Boolean isVehicleActivated) {
		this.isVehicleActivated = isVehicleActivated;
	}

	public Boolean getEngineImmboliser() {
		return engineImmboliser;
	}

	public void setEngineImmboliser(Boolean engineImmboliser) {
		this.engineImmboliser = engineImmboliser;
	}

	public Long getKycMasterId() {
		return kycMasterId;
	}

	public void setKycMasterId(Long kycMasterId) {
		this.kycMasterId = kycMasterId;
	}

	public String getTcPacket() {
		return tcPacket;
	}

	public void setTcPacket(String tcPacket) {
		this.tcPacket = tcPacket;
	}

	public Date getPacketTimeStamp() {
		return packetTimeStamp;
	}

	public void setPacketTimeStamp(Date packetTimeStamp) {
		this.packetTimeStamp = packetTimeStamp;
	}

	public Boolean getIsNumberSync() {
		return isNumberSync;
	}

	public void setIsNumberSync(Boolean isNumberSync) {
		this.isNumberSync = isNumberSync;
	}

	public String getSim1Operator() {
		return sim1Operator;
	}

	public void setSim1Operator(String sim1Operator) {
		this.sim1Operator = sim1Operator;
	}

	public String getSim2Operator() {
		return sim2Operator;
	}

	public void setSim2Operator(String sim2Operator) {
		this.sim2Operator = sim2Operator;
	}

	public Date getSim1ActivationDate() {
		return sim1ActivationDate;
	}

	public void setSim1ActivationDate(Date sim1ActivationDate) {
		this.sim1ActivationDate = sim1ActivationDate;
	}

	public Date getSim1ExpiryDate() {
		return sim1ExpiryDate;
	}

	public void setSim1ExpiryDate(Date sim1ExpiryDate) {
		this.sim1ExpiryDate = sim1ExpiryDate;
	}

	public Date getSim2ActivationDate() {
		return sim2ActivationDate;
	}

	public void setSim2ActivationDate(Date sim2ActivationDate) {
		this.sim2ActivationDate = sim2ActivationDate;
	}

	public Date getSim2ExpiryDate() {
		return sim2ExpiryDate;
	}

	public void setSim2ExpiryDate(Date sim2ExpiryDate) {
		this.sim2ExpiryDate = sim2ExpiryDate;
	}

	public String getSim1Provider() {
		return sim1Provider;
	}

	public void setSim1Provider(String sim1Provider) {
		this.sim1Provider = sim1Provider;
	}

	public String getSim2Provider() {
		return sim2Provider;
	}

	public void setSim2Provider(String sim2Provider) {
		this.sim2Provider = sim2Provider;
	}

	public Date getAppExpiryDate() {
		return appExpiryDate;
	}

	public void setAppExpiryDate(Date appExpiryDate) {
		this.appExpiryDate = appExpiryDate;
	}

	public Boolean getIsRenewalNotify() {
		return isRenewalNotify;
	}

	public void setIsRenewalNotify(Boolean isRenewalNotify) {
		this.isRenewalNotify = isRenewalNotify;
	}

	public Integer getSimExpireDays() {
		return simExpireDays;
	}

	public void setSimExpireDays(Integer simExpireDays) {
		this.simExpireDays = simExpireDays;
	}

	public Integer getAppExpireDays() {
		return appExpireDays;
	}

	public void setAppExpireDays(Integer appExpireDays) {
		this.appExpireDays = appExpireDays;
	}

	public Boolean getIsActivationType() {
		return isActivationType;
	}

	public void setIsActivationType(Boolean isActivationType) {
		this.isActivationType = isActivationType;
	}
	
}
