package com.watsoo.device.management.util;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class Utility{

	public static Double getFileSize(double size,String type) {
		
		double size_kb = size /1024;
		double size_mb = size_kb / 1024;
		double size_gb = size_mb / 1024 ;
		
		if(type.equalsIgnoreCase("GB")) {
			return size_gb;
		} else if (type.equalsIgnoreCase("MB")) {
			return size_mb;
		} else if (type.equalsIgnoreCase("KB")) {
			return size_kb;
		} else {
			return size;
		}
				
	}
	
	public static JsonObject commandJsonObject(Integer deviceId,String command) {

		if (deviceId != null && command != null && !command.isEmpty()) {
			JsonObject mainObject = new JsonObject();
			mainObject.addProperty("description", "New…");
			mainObject.addProperty("id", 0);
			mainObject.addProperty("deviceId", deviceId);
			mainObject.addProperty("textChannel", false);
			mainObject.addProperty("type", "custom");
			JsonObject attributesObject = new JsonObject();
			attributesObject.addProperty("data", command);
			mainObject.add("attributes", attributesObject);
			return mainObject;
		} else {
			return null;
		}
	}
}