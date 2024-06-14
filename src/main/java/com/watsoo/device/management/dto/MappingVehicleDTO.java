package com.watsoo.device.management.dto;
import java.util.Date;
//d.id,d.imeiNo,d.iccidNo,d.uuidNo,d.softwareVersion,d.detail,d.createdAt,d.updatedAt,d.requestBody,d.oldIccid,d.iccidUpdatedAt,d.oldImei,d.imeiUpdatedAt,d.createdBy,d.modifiedBy,d.state,d.status
public interface MappingVehicleDTO {
	public Long getId();
	public String getImeiNo();
	public String getIccidNo();
	public String getUuidNo();
	public String getSoftwareVersion();
	public String getDetail();
	public Date getCreatedAt();
	public Date getUpdatedAt();
	public String getRequestBody();
	public String getOldIccid();
	public Date getIccidUpdatedAt();
	public String getOldImei();
	public Date getImeiUpdatedAt();
	public Long getCreatedBy();
	public Long getModifiedBy();
	public Long getState();
	public String getStatus();

}
