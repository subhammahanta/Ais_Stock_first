package com.watsoo.device.management.dto;
public class MaterialDTO{
    public String material_id;
    public String required_qty;
    public String material_name;
    public String unit_name;
    public String total_current_stock_qty;
    public String approved_stock_qty;
    public String unapproved_stock_qty;
	public String getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}
	public String getRequired_qty() {
		return required_qty;
	}
	public void setRequired_qty(String required_qty) {
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
	public String getTotal_current_stock_qty() {
		return total_current_stock_qty;
	}
	public void setTotal_current_stock_qty(String total_current_stock_qty) {
		this.total_current_stock_qty = total_current_stock_qty;
	}
	public String getApproved_stock_qty() {
		return approved_stock_qty;
	}
	public void setApproved_stock_qty(String approved_stock_qty) {
		this.approved_stock_qty = approved_stock_qty;
	}
	public String getUnapproved_stock_qty() {
		return unapproved_stock_qty;
	}
	public void setUnapproved_stock_qty(String unapproved_stock_qty) {
		this.unapproved_stock_qty = unapproved_stock_qty;
	}
	public MaterialDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MaterialDTO(String material_id, String required_qty, String material_name, String unit_name,
			String total_current_stock_qty, String approved_stock_qty, String unapproved_stock_qty) {
		super();
		this.material_id = material_id;
		this.required_qty = required_qty;
		this.material_name = material_name;
		this.unit_name = unit_name;
		this.total_current_stock_qty = total_current_stock_qty;
		this.approved_stock_qty = approved_stock_qty;
		this.unapproved_stock_qty = unapproved_stock_qty;
	}
    
}