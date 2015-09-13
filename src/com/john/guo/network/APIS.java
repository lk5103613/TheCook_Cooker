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

}
