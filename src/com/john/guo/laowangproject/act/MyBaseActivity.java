package com.john.guo.laowangproject.act;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.john.guo.entity.LoginResult;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;
import com.john.guo.network.MyNetworkUtil;

public class MyBaseActivity extends Activity {
	
	protected final String LOGIN_PREF_NAME = "login_pref";
	protected final String LOGIN_USER = "login_user";
	
	protected Context mContext;
	protected DataFetcher mDataFetcher;
	protected ErrorListener mErrorListener;
	protected SharedPreferences mLoginSharef;
	protected ImageLoader mImgLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = this;
		this.mDataFetcher = DataFetcher.getInstance(mContext);
		this.mErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		};
		mLoginSharef = getSharedPreferences(LOGIN_PREF_NAME, Context.MODE_PRIVATE);
		mImgLoader = MyNetworkUtil.getInstance(mContext).getImageLoader();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(mContext);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(mContext);
	}
	
	public void back(View v) {
		this.finish();
	}
	
	protected LoginResult getLoginUser() {
		String loginUser = mLoginSharef.getString(LOGIN_USER, "");
		if(TextUtils.isEmpty(loginUser))
			return null;
		LoginResult result = GsonUtil.gson.fromJson(loginUser, LoginResult.class);
		return result;
	}

}
