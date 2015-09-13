package com.john.guo.network;

import java.util.List;

import com.android.volley.Response.Listener;

public class UrlParamGenerator {
	
	public static String getPath(String url, String ...params) {
		String result = url;
		for(int i=1; i<=params.length; i++) {
			result = result.replace("%" + i, params[i - 1]);
		}
		return result;
	}
	
	public static String getPath(String url, List<String> params) {
		String result = url;
		for(int i=1; i<=params.size(); i++) {
			result = result.replace("%" + i, params.get(i-1));
		}
		return result;
	}

}
