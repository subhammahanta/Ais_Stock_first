package com.watsoo.device.management.dto;

public class DashboardSectionDTO {
	
	private String sectionName;
	
	private Integer sectionIndex;
	
	private Integer count;

	public DashboardSectionDTO() {
		super();
	}

	public DashboardSectionDTO(String sectionName, Integer sectionIndex, Integer count) {
		super();
		this.sectionName = sectionName;
		this.sectionIndex = sectionIndex;
		this.count = count;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Integer getSectionIndex() {
		return sectionIndex;
	}

	public void setSectionIndex(Integer sectionIndex) {
		this.sectionIndex = sectionIndex;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
