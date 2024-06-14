package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDTO {
	public Company company;
	private Long id;
	private SiteDTO site;
	private String number;
	private String image;
	private MultipartFile vehicleImage;
	private String chassisNumber;
	private String engineNumber;
	private Double weightLimit;
	private Date createdAt;
	private Long createdBy;
	private Date updatedAt;
	private Long updatedBy;
	private Boolean isActive;
	private Long gpsReferanceId;
	private Boolean isGpsVehicleOnline;
	private String gpsCurrentLat;
	private String gpsCurrentLong;
	private String currentLocation;
	private Boolean fuelSensorEnabled;
	private Double engineHoursPerLiter;
	private Double kmPerLiter;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long gpsRefId;
	private List<Long> siteIds;
	private Long userId;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Double lat;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Double lang;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String speed;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String ignition;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String status;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Boolean lastdayUpdate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long lastPollingDate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String deviceAlternateNo;
	private Boolean onlyGpsVehicle;
	private Boolean allVehicle;
	private String batteryLevel;
	private Long vendorId;
	private Boolean isRunning;
	private Double course;
	private String version;
	private Double fuelTankCapacity;
	private Double Mileage;
	private Double kmLiterMileage;
	private Double ehLiterMileage;
	private Boolean isFuelSensorPresent;
	private Double currentFuelValue;
	private Boolean activeFuelSensor;
	private Boolean isFuelSensorActive;
	private Long driverId;
	private Long companyId;
	private Long gpsId;
	private Long vehicleTypeId;
	private Boolean inMaintenanceProcess;
	private Long maintenanceId;
	private String uniqueId;
	private Boolean forDropDown;
	private String idleHours;
	private String vtsModel;
	private String vtsTacNo;
	private Long noOfPanicButton;
	private String imeiNo;
	private String rotorSealNo;
	private String invoiceNo;
	private String iccidNo;
	private String vendorAddress;
	private Long userMapId;
	private Boolean sos;
	private Long duration;
	private String noKyc;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date installationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date invoiceDate;

	private String uid;

	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	private String eSimValidity;
	private String simNo;
	private String installedBy;
	private String installerAddress;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registrationDate;
	private String manufactureYear;
	private Long siteId;
	private String driverName;
	private String driverPhone;
	private String vendorName;
	private String vendorPhone;
	private List<Long> vehicleIds;
	private String certificateNo;
	private Long modelNo;
	private String state;
	private String rto;
	private String makerModel;
	private List<String> imageList;
//	private MultipartFile[] files;
	private MultipartFile engineImg;
	private MultipartFile chassisImg;
	private MultipartFile vehicleImg;
	private String engineImgUrl;
	private String chassisImgUrl;
	private String vehicleImgUrl;
	private String vehicleManufacturer;
	private String vehicleModel;
	private String sim2No;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String currentStatus;
	private Integer groupId;
	private GPSDevicesDTO deviceDTO;
	private Long runningTime;
	private Long idleTime;
	private Long stopTime;
	private Long offlineTime;
	private Double distance;
	private Double odometerDistance;
	private Double totalDistance;
	private String address;
	private Boolean isActivated;
	private Date sinceStop;
	private List<UserDTO> userList;
	private Boolean engineImmboiliser;
	private String tcPackets;
	private Boolean getSyncVehicle;
	private Date sinceOffline;
	private Date sinceRunning;
	private Date sinceIdle;
	private Long kycMasterId;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String sim1Operator;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String sim2Operator;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Date sim1ActivationDate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Date sim1ExpiryDate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Date sim2ActivationDate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Date sim2ExpiryDate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String sim1Provider;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String sim2Provider;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Date appExpriyDate;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Boolean pendingActivation;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Boolean isRenewalNotify;
	private Boolean pendingRenewal;
	private Date allocateVehicle;
	private Date deallocateVehicle;
	private Long vehicleType;

	private Long driver;

	private Long gps;

	private Long vendor;

	private Date speedEvent;

	private String lastRfidId;

	private Long panicButtonNo;

	private String esimValidity;

	private Boolean isVehicleActivated;

	private Boolean engineImmboliser;

	private String tcPacket;

	private Date packetTimeStamp;

	private Boolean isNumberSync;

	private Date appExpiryDate;

	private Integer simExpireDays;

	private Integer appExpireDays;

	private Boolean isActivationType;

	public VehicleDTO() {
		super();
	}

	public VehicleDTO(Long id, SiteDTO site, String number, String image, MultipartFile vehicleImage,
			String chassisNumber, String engineNumber, Double weightLimit, Date createdAt, Long createdBy,
			Date updatedAt, Long updatedBy, Boolean isActive, Long gpsReferanceId, Boolean isGpsVehicleOnline,
			String gpsCurrentLat, String gpsCurrentLong, String currentLocation, Boolean fuelSensorEnabled,
			Double engineHoursPerLiter, Double kmPerLiter, Long gpsRefId, List<Long> siteIds, Long userId, Double lat,
			Double lang, String speed, String ignition, String status, Boolean lastdayUpdate, Long lastPollingDate,
			String deviceAlternateNo, Boolean onlyGpsVehicle, Boolean allVehicle, String batteryLevel, Long vendorId,
			Boolean isRunning, Double course, String version, Double fuelTankCapacity, Double mileage,
			Double kmLiterMileage, Double ehLiterMileage, Boolean isFuelSensorPresent, Double currentFuelValue,
			Boolean activeFuelSensor, Boolean isFuelSensorActive, Long driverId, Long companyId, Long gpsId,
			Long vehicleTypeId, Boolean inMaintenanceProcess, Long maintenanceId, String uniqueId, Boolean forDropDown,
			String idleHours, String vtsModel, String vtsTacNo, Long noOfPanicButton, String imeiNo, String rotorSealNo,
			String invoiceNo, String iccidNo, String vendorAddress, Long userMapId, Boolean sos, Long duration,
			String noKyc, Date installationDate, Date invoiceDate, String uid, String eSimValidity, String simNo,
			String installedBy, String installerAddress, Date registrationDate, String manufactureYear, Long siteId,
			String driverName, String driverPhone, String vendorName, String vendorPhone, List<Long> vehicleIds,
			String certificateNo, Long modelNo, String state, String rto, String makerModel, List<String> imageList,
			MultipartFile engineImg, MultipartFile chassisImg, MultipartFile vehicleImg, String engineImgUrl,
			String chassisImgUrl, String vehicleImgUrl, String vehicleManufacturer, String vehicleModel, String sim2No,
			String currentStatus, Integer groupId, GPSDevicesDTO deviceDTO, Long runningTime, Long idleTime,
			Long stopTime, Long offlineTime, Double distance, Double odometerDistance, Double totalDistance,
			String address, Boolean isActivated, Date sinceStop, List<UserDTO> userList, Boolean engineImmboiliser,
			String tcPackets, Boolean getSyncVehicle, Date sinceOffline, Date sinceRunning, Date sinceIdle,
			Long kycMasterId, String sim1Operator, String sim2Operator, Date sim1ActivationDate, Date sim1ExpiryDate,
			Date sim2ActivationDate, Date sim2ExpiryDate, String sim1Provider, String sim2Provider, Date appExpriyDate,
			Boolean pendingActivation, Boolean isRenewalNotify, Boolean pendingRenewal, Date allocateVehicle,
			Date deallocateVehicle) {
		super();
		this.id = id;
		this.site = site;
		this.number = number;
		this.image = image;
		this.vehicleImage = vehicleImage;
		this.chassisNumber = chassisNumber;
		this.engineNumber = engineNumber;
		this.weightLimit = weightLimit;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
		this.gpsReferanceId = gpsReferanceId;
		this.isGpsVehicleOnline = isGpsVehicleOnline;
		this.gpsCurrentLat = gpsCurrentLat;
		this.gpsCurrentLong = gpsCurrentLong;
		this.currentLocation = currentLocation;
		this.fuelSensorEnabled = fuelSensorEnabled;
		this.engineHoursPerLiter = engineHoursPerLiter;
		this.kmPerLiter = kmPerLiter;
		this.gpsRefId = gpsRefId;
		this.siteIds = siteIds;
		this.userId = userId;
		this.lat = lat;
		this.lang = lang;
		this.speed = speed;
		this.ignition = ignition;
		this.status = status;
		this.lastdayUpdate = lastdayUpdate;
		this.lastPollingDate = lastPollingDate;
		this.deviceAlternateNo = deviceAlternateNo;
		this.onlyGpsVehicle = onlyGpsVehicle;
		this.allVehicle = allVehicle;
		this.batteryLevel = batteryLevel;
		this.vendorId = vendorId;
		this.isRunning = isRunning;
		this.course = course;
		this.version = version;
		this.fuelTankCapacity = fuelTankCapacity;
		Mileage = mileage;
		this.kmLiterMileage = kmLiterMileage;
		this.ehLiterMileage = ehLiterMileage;
		this.isFuelSensorPresent = isFuelSensorPresent;
		this.currentFuelValue = currentFuelValue;
		this.activeFuelSensor = activeFuelSensor;
		this.isFuelSensorActive = isFuelSensorActive;
		this.driverId = driverId;
		this.companyId = companyId;
		this.gpsId = gpsId;
		this.vehicleTypeId = vehicleTypeId;
		this.inMaintenanceProcess = inMaintenanceProcess;
		this.maintenanceId = maintenanceId;
		this.uniqueId = uniqueId;
		this.forDropDown = forDropDown;
		this.idleHours = idleHours;
		this.vtsModel = vtsModel;
		this.vtsTacNo = vtsTacNo;
		this.noOfPanicButton = noOfPanicButton;
		this.imeiNo = imeiNo;
		this.rotorSealNo = rotorSealNo;
		this.invoiceNo = invoiceNo;
		this.iccidNo = iccidNo;
		this.vendorAddress = vendorAddress;
		this.userMapId = userMapId;
		this.sos = sos;
		this.duration = duration;
		this.noKyc = noKyc;
		this.installationDate = installationDate;
		this.invoiceDate = invoiceDate;
		this.uid = uid;
		this.eSimValidity = eSimValidity;
		this.simNo = simNo;
		this.installedBy = installedBy;
		this.installerAddress = installerAddress;
		this.registrationDate = registrationDate;
		this.manufactureYear = manufactureYear;
		this.siteId = siteId;
		this.driverName = driverName;
		this.driverPhone = driverPhone;
		this.vendorName = vendorName;
		this.vendorPhone = vendorPhone;
		this.vehicleIds = vehicleIds;
		this.certificateNo = certificateNo;
		this.modelNo = modelNo;
		this.state = state;
		this.rto = rto;
		this.makerModel = makerModel;
		this.imageList = imageList;
		this.engineImg = engineImg;
		this.chassisImg = chassisImg;
		this.vehicleImg = vehicleImg;
		this.engineImgUrl = engineImgUrl;
		this.chassisImgUrl = chassisImgUrl;
		this.vehicleImgUrl = vehicleImgUrl;
		this.vehicleManufacturer = vehicleManufacturer;
		this.vehicleModel = vehicleModel;
		this.sim2No = sim2No;
		this.currentStatus = currentStatus;
		this.groupId = groupId;
		this.deviceDTO = deviceDTO;
		this.runningTime = runningTime;
		this.idleTime = idleTime;
		this.stopTime = stopTime;
		this.offlineTime = offlineTime;
		this.distance = distance;
		this.odometerDistance = odometerDistance;
		this.totalDistance = totalDistance;
		this.address = address;
		this.isActivated = isActivated;
		this.sinceStop = sinceStop;
		this.userList = userList;
		this.engineImmboiliser = engineImmboiliser;
		this.tcPackets = tcPackets;
		this.getSyncVehicle = getSyncVehicle;
		this.sinceOffline = sinceOffline;
		this.sinceRunning = sinceRunning;
		this.sinceIdle = sinceIdle;
		this.kycMasterId = kycMasterId;
		this.sim1Operator = sim1Operator;
		this.sim2Operator = sim2Operator;
		this.sim1ActivationDate = sim1ActivationDate;
		this.sim1ExpiryDate = sim1ExpiryDate;
		this.sim2ActivationDate = sim2ActivationDate;
		this.sim2ExpiryDate = sim2ExpiryDate;
		this.sim1Provider = sim1Provider;
		this.sim2Provider = sim2Provider;
		this.appExpriyDate = appExpriyDate;
		this.pendingActivation = pendingActivation;
		this.isRenewalNotify = isRenewalNotify;
		this.pendingRenewal = pendingRenewal;
		this.allocateVehicle = allocateVehicle;
		this.deallocateVehicle = deallocateVehicle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SiteDTO getSite() {
		return site;
	}

	public void setSite(SiteDTO site) {
		this.site = site;
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

	public MultipartFile getVehicleImage() {
		return vehicleImage;
	}

	public void setVehicleImage(MultipartFile vehicleImage) {
		this.vehicleImage = vehicleImage;
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

	public Double getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(Double weightLimit) {
		this.weightLimit = weightLimit;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getGpsReferanceId() {
		return gpsReferanceId;
	}

	public void setGpsReferanceId(Long gpsReferanceId) {
		this.gpsReferanceId = gpsReferanceId;
	}

	public Boolean getIsGpsVehicleOnline() {
		return isGpsVehicleOnline;
	}

	public void setIsGpsVehicleOnline(Boolean isGpsVehicleOnline) {
		this.isGpsVehicleOnline = isGpsVehicleOnline;
	}

	public String getGpsCurrentLat() {
		return gpsCurrentLat;
	}

	public void setGpsCurrentLat(String gpsCurrentLat) {
		this.gpsCurrentLat = gpsCurrentLat;
	}

	public String getGpsCurrentLong() {
		return gpsCurrentLong;
	}

	public void setGpsCurrentLong(String gpsCurrentLong) {
		this.gpsCurrentLong = gpsCurrentLong;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
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

	public Long getGpsRefId() {
		return gpsRefId;
	}

	public void setGpsRefId(Long gpsRefId) {
		this.gpsRefId = gpsRefId;
	}

	public List<Long> getSiteIds() {
		return siteIds;
	}

	public void setSiteIds(List<Long> siteIds) {
		this.siteIds = siteIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLang() {
		return lang;
	}

	public void setLang(Double lang) {
		this.lang = lang;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getIgnition() {
		return ignition;
	}

	public void setIgnition(String ignition) {
		this.ignition = ignition;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getLastdayUpdate() {
		return lastdayUpdate;
	}

	public void setLastdayUpdate(Boolean lastdayUpdate) {
		this.lastdayUpdate = lastdayUpdate;
	}

	public Long getLastPollingDate() {
		return lastPollingDate;
	}

	public void setLastPollingDate(Long lastPollingDate) {
		this.lastPollingDate = lastPollingDate;
	}

	public String getDeviceAlternateNo() {
		return deviceAlternateNo;
	}

	public void setDeviceAlternateNo(String deviceAlternateNo) {
		this.deviceAlternateNo = deviceAlternateNo;
	}

	public Boolean getOnlyGpsVehicle() {
		return onlyGpsVehicle;
	}

	public void setOnlyGpsVehicle(Boolean onlyGpsVehicle) {
		this.onlyGpsVehicle = onlyGpsVehicle;
	}

	public Boolean getAllVehicle() {
		return allVehicle;
	}

	public void setAllVehicle(Boolean allVehicle) {
		this.allVehicle = allVehicle;
	}

	public String getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(String batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Double getCourse() {
		return course;
	}

	public void setCourse(Double course) {
		this.course = course;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Double getFuelTankCapacity() {
		return fuelTankCapacity;
	}

	public void setFuelTankCapacity(Double fuelTankCapacity) {
		this.fuelTankCapacity = fuelTankCapacity;
	}

	public Double getMileage() {
		return Mileage;
	}

	public void setMileage(Double mileage) {
		Mileage = mileage;
	}

	public Double getKmLiterMileage() {
		return kmLiterMileage;
	}

	public void setKmLiterMileage(Double kmLiterMileage) {
		this.kmLiterMileage = kmLiterMileage;
	}

	public Double getEhLiterMileage() {
		return ehLiterMileage;
	}

	public void setEhLiterMileage(Double ehLiterMileage) {
		this.ehLiterMileage = ehLiterMileage;
	}

	public Boolean getIsFuelSensorPresent() {
		return isFuelSensorPresent;
	}

	public void setIsFuelSensorPresent(Boolean isFuelSensorPresent) {
		this.isFuelSensorPresent = isFuelSensorPresent;
	}

	public Double getCurrentFuelValue() {
		return currentFuelValue;
	}

	public void setCurrentFuelValue(Double currentFuelValue) {
		this.currentFuelValue = currentFuelValue;
	}

	public Boolean getActiveFuelSensor() {
		return activeFuelSensor;
	}

	public void setActiveFuelSensor(Boolean activeFuelSensor) {
		this.activeFuelSensor = activeFuelSensor;
	}

	public Boolean getIsFuelSensorActive() {
		return isFuelSensorActive;
	}

	public void setIsFuelSensorActive(Boolean isFuelSensorActive) {
		this.isFuelSensorActive = isFuelSensorActive;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGpsId() {
		return gpsId;
	}

	public void setGpsId(Long gpsId) {
		this.gpsId = gpsId;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Boolean getForDropDown() {
		return forDropDown;
	}

	public void setForDropDown(Boolean forDropDown) {
		this.forDropDown = forDropDown;
	}

	public String getIdleHours() {
		return idleHours;
	}

	public void setIdleHours(String idleHours) {
		this.idleHours = idleHours;
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

	public Long getNoOfPanicButton() {
		return noOfPanicButton;
	}

	public void setNoOfPanicButton(Long noOfPanicButton) {
		this.noOfPanicButton = noOfPanicButton;
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

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public Long getUserMapId() {
		return userMapId;
	}

	public void setUserMapId(Long userMapId) {
		this.userMapId = userMapId;
	}

	public Boolean getSos() {
		return sos;
	}

	public void setSos(Boolean sos) {
		this.sos = sos;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getNoKyc() {
		return noKyc;
	}

	public void setNoKyc(String noKyc) {
		this.noKyc = noKyc;
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

	public String geteSimValidity() {
		return eSimValidity;
	}

	public void seteSimValidity(String eSimValidity) {
		this.eSimValidity = eSimValidity;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(String manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorPhone() {
		return vendorPhone;
	}

	public void setVendorPhone(String vendorPhone) {
		this.vendorPhone = vendorPhone;
	}

	public List<Long> getVehicleIds() {
		return vehicleIds;
	}

	public void setVehicleIds(List<Long> vehicleIds) {
		this.vehicleIds = vehicleIds;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Long getModelNo() {
		return modelNo;
	}

	public void setModelNo(Long modelNo) {
		this.modelNo = modelNo;
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

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public MultipartFile getEngineImg() {
		return engineImg;
	}

	public void setEngineImg(MultipartFile engineImg) {
		this.engineImg = engineImg;
	}

	public MultipartFile getChassisImg() {
		return chassisImg;
	}

	public void setChassisImg(MultipartFile chassisImg) {
		this.chassisImg = chassisImg;
	}

	public MultipartFile getVehicleImg() {
		return vehicleImg;
	}

	public void setVehicleImg(MultipartFile vehicleImg) {
		this.vehicleImg = vehicleImg;
	}

	public String getEngineImgUrl() {
		return engineImgUrl;
	}

	public void setEngineImgUrl(String engineImgUrl) {
		this.engineImgUrl = engineImgUrl;
	}

	public String getChassisImgUrl() {
		return chassisImgUrl;
	}

	public void setChassisImgUrl(String chassisImgUrl) {
		this.chassisImgUrl = chassisImgUrl;
	}

	public String getVehicleImgUrl() {
		return vehicleImgUrl;
	}

	public void setVehicleImgUrl(String vehicleImgUrl) {
		this.vehicleImgUrl = vehicleImgUrl;
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

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public GPSDevicesDTO getDeviceDTO() {
		return deviceDTO;
	}

	public void setDeviceDTO(GPSDevicesDTO deviceDTO) {
		this.deviceDTO = deviceDTO;
	}

	public Long getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Long runningTime) {
		this.runningTime = runningTime;
	}

	public Long getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(Long idleTime) {
		this.idleTime = idleTime;
	}

	public Long getStopTime() {
		return stopTime;
	}

	public void setStopTime(Long stopTime) {
		this.stopTime = stopTime;
	}

	public Long getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getOdometerDistance() {
		return odometerDistance;
	}

	public void setOdometerDistance(Double odometerDistance) {
		this.odometerDistance = odometerDistance;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	public Date getSinceStop() {
		return sinceStop;
	}

	public void setSinceStop(Date sinceStop) {
		this.sinceStop = sinceStop;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public Boolean getEngineImmboiliser() {
		return engineImmboiliser;
	}

	public void setEngineImmboiliser(Boolean engineImmboiliser) {
		this.engineImmboiliser = engineImmboiliser;
	}

	public String getTcPackets() {
		return tcPackets;
	}

	public void setTcPackets(String tcPackets) {
		this.tcPackets = tcPackets;
	}

	public Boolean getGetSyncVehicle() {
		return getSyncVehicle;
	}

	public void setGetSyncVehicle(Boolean getSyncVehicle) {
		this.getSyncVehicle = getSyncVehicle;
	}

	public Date getSinceOffline() {
		return sinceOffline;
	}

	public void setSinceOffline(Date sinceOffline) {
		this.sinceOffline = sinceOffline;
	}

	public Date getSinceRunning() {
		return sinceRunning;
	}

	public void setSinceRunning(Date sinceRunning) {
		this.sinceRunning = sinceRunning;
	}

	public Date getSinceIdle() {
		return sinceIdle;
	}

	public void setSinceIdle(Date sinceIdle) {
		this.sinceIdle = sinceIdle;
	}

	public Long getKycMasterId() {
		return kycMasterId;
	}

	public void setKycMasterId(Long kycMasterId) {
		this.kycMasterId = kycMasterId;
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

	public Date getAppExpriyDate() {
		return appExpriyDate;
	}

	public void setAppExpriyDate(Date appExpriyDate) {
		this.appExpriyDate = appExpriyDate;
	}

	public Boolean getPendingActivation() {
		return pendingActivation;
	}

	public void setPendingActivation(Boolean pendingActivation) {
		this.pendingActivation = pendingActivation;
	}

	public Boolean getIsRenewalNotify() {
		return isRenewalNotify;
	}

	public void setIsRenewalNotify(Boolean isRenewalNotify) {
		this.isRenewalNotify = isRenewalNotify;
	}

	public Boolean getPendingRenewal() {
		return pendingRenewal;
	}

	public void setPendingRenewal(Boolean pendingRenewal) {
		this.pendingRenewal = pendingRenewal;
	}

	public Date getAllocateVehicle() {
		return allocateVehicle;
	}

	public void setAllocateVehicle(Date allocateVehicle) {
		this.allocateVehicle = allocateVehicle;
	}

	public Date getDeallocateVehicle() {
		return deallocateVehicle;
	}

	public void setDeallocateVehicle(Date deallocateVehicle) {
		this.deallocateVehicle = deallocateVehicle;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getDriver() {
		return driver;
	}

	public void setDriver(Long driver) {
		this.driver = driver;
	}

	public Long getGps() {
		return gps;
	}

	public void setGps(Long gps) {
		this.gps = gps;
	}

	public Long getVendor() {
		return vendor;
	}

	public void setVendor(Long vendor) {
		this.vendor = vendor;
	}

	public Date getSpeedEvent() {
		return speedEvent;
	}

	public void setSpeedEvent(Date speedEvent) {
		this.speedEvent = speedEvent;
	}

	public String getLastRfidId() {
		return lastRfidId;
	}

	public void setLastRfidId(String lastRfidId) {
		this.lastRfidId = lastRfidId;
	}

	public Long getPanicButtonNo() {
		return panicButtonNo;
	}

	public void setPanicButtonNo(Long panicButtonNo) {
		this.panicButtonNo = panicButtonNo;
	}

	public String getEsimValidity() {
		return esimValidity;
	}

	public void setEsimValidity(String esimValidity) {
		this.esimValidity = esimValidity;
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

	public Date getAppExpiryDate() {
		return appExpiryDate;
	}

	public void setAppExpiryDate(Date appExpiryDate) {
		this.appExpiryDate = appExpiryDate;
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
