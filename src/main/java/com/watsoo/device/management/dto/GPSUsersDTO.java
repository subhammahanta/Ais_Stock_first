package com.watsoo.device.management.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class GPSUsersDTO {

	private Long id;
	private String name;
	private String email;
	private String hashedpassword;
	private String salt;
	private boolean readonly;
	private boolean admin;
	private String map;
	private double latitude;
	private double longitude;
	private int zoom;
	private boolean twelvehourformat;
	private String attributes;
	private String coordinateformat;
	private boolean disabled;
	private Date expirationtime;
	private int devicelimit;
	private String token;
	private int userlimit;
	private boolean devicereadonly;
	private String phone;
	private List<GPSDevicesDTO> device;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashedpassword() {
		return hashedpassword;
	}

	public void setHashedpassword(String hashedpassword) {
		this.hashedpassword = hashedpassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public boolean isTwelvehourformat() {
		return twelvehourformat;
	}

	public void setTwelvehourformat(boolean twelvehourformat) {
		this.twelvehourformat = twelvehourformat;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getCoordinateformat() {
		return coordinateformat;
	}

	public void setCoordinateformat(String coordinateformat) {
		this.coordinateformat = coordinateformat;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getExpirationtime() {
		return expirationtime;
	}

	public void setExpirationtime(Date expirationtime) {
		this.expirationtime = expirationtime;
	}

	public int getDevicelimit() {
		return devicelimit;
	}

	public void setDevicelimit(int devicelimit) {
		this.devicelimit = devicelimit;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUserlimit() {
		return userlimit;
	}

	public void setUserlimit(int userlimit) {
		this.userlimit = userlimit;
	}

	public boolean isDevicereadonly() {
		return devicereadonly;
	}

	public void setDevicereadonly(boolean devicereadonly) {
		this.devicereadonly = devicereadonly;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<GPSDevicesDTO> getDevice() {
		return device;
	}

	public void setDevice(List<GPSDevicesDTO> device) {
		this.device = device;
	}
}