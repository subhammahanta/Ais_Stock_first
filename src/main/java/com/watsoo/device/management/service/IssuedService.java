package com.watsoo.device.management.service;

import java.util.List;

import com.watsoo.device.management.dto.IssueDeviceDTO;
import com.watsoo.device.management.dto.Pagination;
import com.watsoo.device.management.dto.Response;

public interface IssuedService {

	Pagination<List<IssueDeviceDTO>>  getIssuedList(int pageNo, int pageSize, IssueDeviceDTO dto);

	Response<?> saveIssueDeviceDetails(IssueDeviceDTO dto);

	Response<?> getByIssuedId(Long issuedId);

	Response<?> saveIssueDeviceDetailsV2(IssueDeviceDTO dto);

	Response<?> unissueListOfDevice(IssueDeviceDTO dto);

}
