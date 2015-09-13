package com.john.guo.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class IndexOrderEntity {

	@SerializedName("orderid")
	private String orderId;
	@SerializedName("add_time")
	private String addTime;
	@SerializedName("all_money")
	public float allMoney;
	@SerializedName("already_pay")
	public float alreadyPay;
	@SerializedName("dining_time")
	public String diningTime;
	@SerializedName("servicer_usercnt")
	public String servicerUsercnt;
	@SerializedName("zidai_canju")
	public String zidaiCanju;
	@SerializedName("pay_type")
	public String payType;
	public String comment;
	public String status;
	public String uid;
	public String cid;
	@SerializedName("order_no")
	public String orderNo;
	public String address;
	public String linkman;
	@SerializedName("special_memo")
	public String specialMemo;
	@SerializedName("kitchen_size")
	public String kitchenSize;
	public String addtime2;
	@SerializedName("productCnt")
	public String productcnt;
	@SerializedName("logoStr")
	public String logostr;
	public List<OrderDetailItem> itemList;
	
	public class OrderDetailItem {
		public String name;
		@SerializedName("img_path")
		public String imgPath;
		public String price;
		@SerializedName("sold_cnt")
		public String soldCnt;
	}

}
