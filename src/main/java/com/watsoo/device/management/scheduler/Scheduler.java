//package com.watsoo.device.management.scheduler;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.watsoo.device.management.service.CommandService;
//import com.watsoo.device.management.service.DeviceService;
//
//@Component
//public class Scheduler {
//
//	@Autowired
//	private DeviceService deviceService;
//
////	@Autowired
////	private IssuedConfigurationServiceImpl service;
////	
////	@Autowired
////	private DeviceServiceImpl impl;
//	
//	@Autowired
//	private CommandService commandService;
//	
//	@Scheduled(cron = "00 15 18 * * *")
//	public void DailyProductionReportScheduler() {
//		deviceService.countNumberOfDeviceCreatedToday();
//	}
//
//	@Scheduled(cron = "00 */5 * * * *") 
//	public void shootCommands() {
//		commandService.shootCommandsSchedular();
//	}
//
//}
