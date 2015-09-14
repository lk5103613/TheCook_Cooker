package com.john.guo.entity;

import com.google.gson.annotations.SerializedName;

public class ServiceLog {

	public String id;
	@SerializedName("cook_id")
	public String cookId;
	@SerializedName("order_id")
	public String orderId;
	@SerializedName("customer_name")
	public String customerName;
	@SerializedName("add_time")
	public String addTime;

}
