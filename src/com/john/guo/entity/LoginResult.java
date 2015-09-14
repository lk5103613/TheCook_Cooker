package com.john.guo.entity;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
	
	public final static String UN_PASS = "3";

	public String cid;
	public String avatar;
	public String cmp;
	public String cpwd;
	public String role;
	public String province;
	public String city;
	public String district;
	public String introduction;
	public String score;
	@SerializedName("role_id")
	public String roleId;
	public String status;
	@SerializedName("service_status")
	public String serviceStatus;
	@SerializedName("detail_address")
	public String detailAddress;
	@SerializedName("work_fromday")
	public String workFromday;
	@SerializedName("caixi_style")
	public String caixiStyle;
	public String certificate0;
	public String certificate1;
	public String certificate2;
	public String certificate3;
	public String certificate4;
	public String certificate5;
	public String certificate6;
	public String certificate7;
	public String reason;
	public String truename;
	@SerializedName("service_cnt")
	public String serviceCnt;
	@SerializedName("zan_cnt")
	public String zanCnt;
	public String nickname;
	public int code;

}
