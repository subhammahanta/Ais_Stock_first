package com.watsoo.device.management.dto;

public class DeviceStoreResponseDto {

	private Long material_id;

	private Double required_qty;

	private String material_name;

	private String unit_name;

	private Long total_current_stock_qty;

	private Long approved_stock_qty;

	private Long unapproved_stock_qty;

	public DeviceStoreResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(Long material_id) {
		this.material_id = material_id;
	}

	public Double getRequired_qty() {
		return required_qty;
	}

	public void setRequired_qty(Double required_qty) {
		this.required_qty = required_qty;
	}

	public String getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public Long getTotal_current_stock_qty() {
		return total_current_stock_qty;
	}

	public void setTotal_current_stock_qty(Long total_current_stock_qty) {
		this.total_current_stock_qty = total_current_stock_qty;
	}

	public Long getApproved_stock_qty() {
		return approved_stock_qty;
	}

	public void setApproved_stock_qty(Long approved_stock_qty) {
		this.approved_stock_qty = approved_stock_qty;
	}

	public Long getUnapproved_stock_qty() {
		return unapproved_stock_qty;
	}

	public void setUnapproved_stock_qty(Long unapproved_stock_qty) {
		this.unapproved_stock_qty = unapproved_stock_qty;
	}

}
