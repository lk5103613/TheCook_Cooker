package com.john.guo.laowangproject.act;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.dcjd.cook.cs.R;
import com.john.guo.entity.CommonResult;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;
import com.john.guo.util.ValidateCodeUtil;
import com.john.guo.util.ValidateUtil;

public class RegisterActivity extends MyBaseActivity {
	
	private ImageView idCategoryIV;
	private PopupWindow popupWindow;
	private Button registerBtn;
	private DataFetcher dataFetcher;
	private String mp="13888888888";
	private String pwd="1";
	private String role="big";
	private String mCode;
	private TextView roleEditText;
	private EditText phoneEditText;
	private EditText pwdEditText;
	private EditText codeEditText;
	private View popview;
	private LinearLayout mRoleContainer;
	private String mValidateCode;
	
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		dataFetcher = DataFetcher.getInstance(this);
		
		mRoleContainer = (LinearLayout) findViewById(R.id.role);
		roleEditText = (TextView)findViewById(R.id.role_et);
		phoneEditText = (EditText)findViewById(R.id.phone_et);
		pwdEditText = (EditText)findViewById(R.id.psw_et);
		codeEditText = (EditText)findViewById(R.id.code);
		
		mContext = this;
		
		idCategoryIV = (ImageView) findViewById(R.id.imageview1);
		initPopupWindow();
		mRoleContainer.setOnClickListener(roleClickListener);
		registerBtn = (Button)findViewById(R.id.register);
		
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				role = roleEditText.getText().toString();
				mp = phoneEditText.getText().toString();
				pwd = pwdEditText.getText().toString();
				mCode = codeEditText.getText().toString();
				
				if(TextUtils.isEmpty(role) || TextUtils.isEmpty(mp) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(mCode) ){
					Toast.makeText(RegisterActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!ValidateUtil.validatePhoneNum(mp)) {
					Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (!TextUtils.equals(mCode, mValidateCode)) {
					Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				dataFetcher.fetchRegData(mp, pwd, role, new Response.Listener<JSONObject>(){
					@Override
					public void onResponse(JSONObject response) {
						CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
						if(result.code == 1) {
							Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
							Intent intent = null;
							//如果是厨师则跳到厨师认证
							if (TextUtils.equals("厨师", role)) {
								 intent = new Intent(RegisterActivity.this, ApproveActivity.class);
								 
							} else{
								intent = new Intent(RegisterActivity.this, LoginActivity.class);
							}
							
							RegisterActivity.this.finish();
							startActivity(intent);
						   
						} else if(result.code == 7) {
							Toast.makeText(mContext, "用户名已 存在", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();
						}
					}
					
				}, new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
	}
	
	private void initPopupWindow(){
		LayoutInflater inflater = LayoutInflater.from(this);
		popview = inflater.inflate(R.layout.view_idcategory, null);
		popupWindow = new PopupWindow(popview, 
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		popview.findViewById(R.id.bang).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dissmissPop();
				roleEditText.setText("帮厨");
			}
		});
		popview.findViewById(R.id.big).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dissmissPop();
				roleEditText.setText("大厨");
				
			}
		});
		popview.findViewById(R.id.fu).setOnClickListener(new OnClickListener() {
	
	        @Override
	        public void onClick(View arg0) {
	        	dissmissPop();
	        	roleEditText.setText("服务员");
	        }
        });
	}
	
	private OnClickListener roleClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(popupWindow != null){
				if(!popupWindow.isShowing()){
					popupWindow.showAsDropDown(idCategoryIV, 0, 0);
				}else{
					popupWindow.dismiss();
				}
			}
		}
	};
	
	private void dissmissPop(){
		if(popupWindow != null)
			popupWindow.dismiss();
	}
	
	public void toLogin(View v) {
		this.finish();
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
	}
	
	public void sendCode(final View v) {
		mp = phoneEditText.getText().toString();
		mValidateCode = ValidateCodeUtil.getValidateCode();
		
		if(TextUtils.isEmpty(mp)) {
			Toast.makeText(mContext, "请输入手机号码", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!ValidateUtil.isMobileNO(mp)) {
			Toast.makeText(mContext, "输入的手机号格式错误", Toast.LENGTH_SHORT).show();
			return;
		}
		mDataFetcher.fetchSendCode(mp, mValidateCode, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
				v.setEnabled(false);
			}
		}, mErrorListener);
	}
	
}
