package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.DashboardSectionDTO;
import com.watsoo.device.management.dto.Response;

public interface DeviceDashboardService {

	Response<List<DashboardSectionDTO>> getDashBoardDTO() throws Exception;

}
