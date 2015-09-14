package com.john.guo.laowangproject.act;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dcjd.cook.cs.R;
import com.john.guo.entity.LoginResult;
import com.john.guo.network.GsonUtil;

public class LoginActivity extends MyBaseActivity {
	
	private EditText mTxtPhoneNum;
	private EditText mTxtPwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		String loginUser = mLoginSharef.getString(LOGIN_USER, "");
		if(!TextUtils.isEmpty(loginUser)) {
			toIndex();
			this.finish();
		}
		mTxtPhoneNum = (EditText) findViewById(R.id.phone_et);
		mTxtPwd = (EditText) findViewById(R.id.psw_et);
	}
	
	public void login(View v) {
		String phoneNum = mTxtPhoneNum.getText().toString();
		String pwd = mTxtPwd.getText().toString();
		if(TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(mContext, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
		}
		mDataFetcher.fetchLoginData(phoneNum, pwd, new Listener<JSONObject>() {
			@Override
			public void onResponse(final JSONObject response) {
				System.out.println(response);
				LoginResult result = GsonUtil.gson.fromJson(response.toString(), LoginResult.class);
				if(result.code == 1) {
					Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
					new Thread(new Runnable() {
						@Override
						public void run() {
							mLoginSharef.edit().putString(LOGIN_USER, response.toString()).commit();
						}
					}).start();
					if(result.status.equals(LoginResult.UN_PASS)) {
						Intent intent = new Intent(mContext, CsrzActivity.class);
						intent.putExtra("login_result", response.toString());
						startActivity(intent);
					} else {
						toIndex();
					}
					LoginActivity.this.finish();
				} else {
					Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
				}
			}
		}, mErrorListener);
	}
	
	private void toIndex() {
		Intent intent = new Intent(mContext, IndexOrderUI.class);
		startActivity(intent);
	}
	
	public void toReg(View v) {
		Intent intent = new Intent(mContext, RegisterActivity.class);
		LoginActivity.this.finish();
		startActivity(intent);
	}

}
