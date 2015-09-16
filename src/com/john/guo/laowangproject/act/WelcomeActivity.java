package com.john.guo.laowangproject.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;

import com.dcjd.cook.cs.R;

public class WelcomeActivity extends MyBaseActivity {
	
	private final static int NOT_LOGIN = 0;
	private final static int LOGIN = 1;
	
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		mHandler = new Handler(new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				Intent intent;
				switch (msg.what) {
				case NOT_LOGIN:
					intent = new Intent(mContext, LoginActivity.class);
					break;
				case LOGIN: 
					intent = new Intent(mContext, IndexOrderUI.class);
					break;
				default:
					return false;
				}
				startActivity(intent);
				WelcomeActivity.this.finish();
				return false;
			}
		});
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				int what = -1;
				String loginUser = mLoginSharef.getString(LOGIN_USER, "");
				if(!TextUtils.isEmpty(loginUser)) {
					what = LOGIN;
				} else {
					what = NOT_LOGIN;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(what);
			}
		}).start();
	}

}
