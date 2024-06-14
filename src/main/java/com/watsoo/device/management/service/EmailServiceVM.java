package com.watsoo.device.management.service;

public interface EmailServiceVM {
	
	void sendDeviceActRenSupportMail(String email, String date, String link);

	void sendUnConfigureDevicesExcelMail(String email, String date, String link);

	void sendConfigureDevicesExcelMail(String notifyEmailId, String localDateTimeToStringInFormat, String excelUrl, String requestCode);
}
