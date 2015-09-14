package com.john.guo.entity;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ServiceRecordBean implements Serializable {
//	private String time;
//	private String tips;
//	private boolean isOver;

	public String id;
	@SerializedName("cook_id")
	public String cookId;
	@SerializedName("order_id")
	public String orderId;
	@SerializedName("customer_name")
	public String customerName;
	@SerializedName("add_time")
	public String addTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCookId() {
		return cookId;
	}
	public void setCookId(String cookId) {
		this.cookId = cookId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	
//	public ServiceRecordBean(String time, String tips, boolean isOver) {
//		super();
//		this.time = time;
//		this.tips = tips;
//		this.isOver = isOver;
//	}
//
//	public String getTime() {
//		return time;
//	}
//
//	public void setTime(String time) {
//		this.time = time;
//	}
//
//	public String getTips() {
//		return tips;
//	}
//
//	public void setTips(String tips) {
//		this.tips = tips;
//	}
//
//	public boolean isOver() {
//		return isOver;
//	}
//
//	public void setOver(boolean isOver) {
//		this.isOver = isOver;
//	}

}
