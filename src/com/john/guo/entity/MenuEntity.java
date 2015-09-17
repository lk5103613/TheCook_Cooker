package com.john.guo.entity;

import com.google.gson.annotations.SerializedName;

public class MenuEntity {

	@SerializedName("img_path")
	public String imgPath;
	public String packid;
	public String price;
	public String description;
	@SerializedName("sold_cnt")
	public String soldCnt;
	@SerializedName("add_time")
	public String addTime;
	public String attention;
	public String packname;

}
