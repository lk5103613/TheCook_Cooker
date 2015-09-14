package com.john.guo.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

public class DataFetcher {

	private static DataFetcher mInstance;
	private Context mContext;
	private RequestQueue mQueue;
	private ImageLoader mLoader;

	private DataFetcher(Context context) {
		this.mContext = context;
		mQueue = MyNetworkUtil.getInstance(mContext).getRequestQueue();
		mLoader = MyNetworkUtil.getInstance(mContext).getImageLoader();
	}

	public static DataFetcher getInstance(Context context) {
		if (mInstance == null)
			mInstance = new DataFetcher(context);
		return mInstance;
	}

	public void fetchRegData(final String mp, final String pwd, String role,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		try {
			role = URLEncoder.encode(role, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = UrlParamGenerator.getPath(APIS.REG, mp, pwd, role);
		System.out.println(url);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

	public void fetchLoginData(String mp, String pwd,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		String url = UrlParamGenerator.getPath(APIS.LOGIN, mp, pwd);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

	public void fetchIndexOrder(String cid, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		String url = UrlParamGenerator.getPath(APIS.GET_INDEX_ORDER, cid);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

	public void fetchLeftMenu(String cid, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		String url = UrlParamGenerator.getPath(APIS.GET_LEFT_MENU_INFO, cid);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

	public void fetchExit(String cid, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		String url = UrlParamGenerator.getPath(APIS.EXIT_SERVER, cid);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

	public void fetchSuspend(String cid, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		String url = UrlParamGenerator.getPath(APIS.SUSPEND_SERVER, cid);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

	public void fetchConfirmOrder(String orderId, String status,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		String url = UrlParamGenerator.getPath(APIS.ORDER_CONFIRM, orderId,
				status);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

	public void fetchApprove(List<String> params,
			Listener<JSONObject> listener, ErrorListener errorListener) {

		String url = UrlParamGenerator.getPath(APIS.UPDATE_INFO, params);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, listener, errorListener);
		mQueue.add(request);
	}
	
	public void fetchAddSpaceTime(String cookId, String cookMp, String weekDays, String fromTime, String toTime,
			Listener<JSONObject> listener, ErrorListener errorListener){
		
		String url = UrlParamGenerator.getPath(APIS.ADD_SPACE_TIME, cookId, cookMp,weekDays,fromTime,toTime);
		System.out.println("url " + url);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, listener, errorListener);
		mQueue.add(request);
	}
	
	public void fetchUpdateFigure(String orderId, String cookId, String oldMoney, String money, String flag,Listener<JSONObject> listener, ErrorListener errorListener){
		String url = UrlParamGenerator.getPath(APIS.UPDATE_ORDER_FIGURE, orderId, cookId,oldMoney,money,flag);
		System.out.println("url " + url);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				null, listener, errorListener);
		mQueue.add(request);
	}

}
