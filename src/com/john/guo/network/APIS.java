package com.john.guo.network;

public class APIS {

	public final static String BASE_URL = "http://120.26.67.29";

	public final static String REG = BASE_URL
			+ "/cook/index.php/appCook/reg?mp=%1&pwd=%2&role=%3";

	public final static String LOGIN = BASE_URL
			+ "/cook/index.php/appCook/login?mp=%1&pwd=%2";

	public final static String GET_INDEX_ORDER = BASE_URL
			+ "/cook/index.php/CookOrder/orderPage?cid=%1";

	public final static String GET_LEFT_MENU_INFO = BASE_URL
			+ "/cook/index.php/AppCook/menu?cid=%1";

	public final static String EXIT_SERVER = BASE_URL
			+ "/cook/index.php/AppCook/stopServiceFn?cid=%1";

	public final static String SUSPEND_SERVER = BASE_URL
			+ "/cook/index.php/AppCook/suspendServiceFn?cid=%1";

	public final static String ORDER_CONFIRM = BASE_URL
			+ "/cook/index.php/CookOrder/confirmFn?orderId=%1&status=%2";

	public final static String UPLOAD = BASE_URL + "/yw_uploadify.php";

	public final static String UPDATE_INFO = BASE_URL
			+ "/cook/index.php/appCook/updateCookInfoFn?cid=%1&truename=%2&mp=%3&province=%4&city=%5&district=%6&introduction=%7&caixi_style=%8&certificate0=%9&certificate1=%10&certificate2=%11&certificate3=%12&work_fromday=%13&avatar=%14";

	//添加空闲时间
	public final static String ADD_SPACE_TIME = BASE_URL+"/cook/index.php/Cook/setCookTimeFn?cook_id=%1&cook_mp=%2&week_days=%3&from_time=%4&to_time=%5";
	
	//修改订单金额
	public final static String UPDATE_ORDER_FIGURE = BASE_URL+"/cook/index.php/CookOrder/editPriceFn?order_id=%1&cook_id=%2&old_money=%3&money=%4&flag=%5&reason=%6";
	public final static String GET_SERVICE_LOG = BASE_URL + "/cook/index.php/AppCook/cookServiceLogFn?cid=%1";
	
	public static final String GET_MENU = BASE_URL + "/index.php/appPack/findPageList?currentPage=%1";
	
	public static final String SEND_CODE = "http://222.73.117.158/msg/HttpBatchSendSM?account=vip_lb_dcjd&pswd=vip_lb_dcjd001&mobile=%1&msg=%2&needstatus=true";
}
