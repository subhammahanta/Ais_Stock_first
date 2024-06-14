//package com.watsoo.device.management.model;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "vts_tac")
//public class VtsTac implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@Column(name = "vts_tac_no")
//	private String vtsTacNo;
//
//	public VtsTac(Long id, String vtsTacNo) {
//		super();
//		this.id = id;
//		this.vtsTacNo = vtsTacNo;
//	}
//	
//	public VtsTac(Long id) {
//		super();
//		this.id = id;
//	}
//
//	public VtsTac() {
//		super();
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getVtsTacNo() {
//		return vtsTacNo;
//	}
//
//	public void setVtsTacNo(String vtsTacNo) {
//		this.vtsTacNo = vtsTacNo;
//	}
//
//}
