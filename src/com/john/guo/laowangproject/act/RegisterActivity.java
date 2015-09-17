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
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.cs.R;
import com.john.guo.entity.CommonResult;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;

public class RegisterActivity extends MyBaseActivity {
	
	private ImageView idCategoryIV;
	private PopupWindow popupWindow;
	private Button registerBtn;
	private DataFetcher dataFetcher;
	private String mp="13888888888";
	private String pwd="1";
	private String role="big";
	private EditText roleEditText;
	private EditText phoneEditText;
	private EditText pwdEditText;
	private View popview;
	
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		dataFetcher = DataFetcher.getInstance(this);
		roleEditText = (EditText)findViewById(R.id.role_et);
		
		phoneEditText = (EditText)findViewById(R.id.phone_et);
		pwdEditText = (EditText)findViewById(R.id.psw_et);
		mContext = this;
		
		idCategoryIV = (ImageView) findViewById(R.id.imageview1);
		initPopupWindow();
		idCategoryIV.setOnClickListener(idCategoryIVClick);
		
		registerBtn = (Button)findViewById(R.id.register);
		
		roleEditText.setInputType(InputType.TYPE_NULL); 
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				role = roleEditText.getText().toString();
				mp = phoneEditText.getText().toString();
				pwd = pwdEditText.getText().toString();
				if(TextUtils.isEmpty(role) || TextUtils.isEmpty(mp) || TextUtils.isEmpty(pwd) ){
					Toast.makeText(RegisterActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
					return;
				}
				dataFetcher.fetchRegData(mp, pwd, role, new Response.Listener<JSONObject>(){
					@Override
					public void onResponse(JSONObject response) {
						CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
						if(result.code == 1) {
							Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
						    Intent orderIntent = new Intent(RegisterActivity.this, ApproveActivity.class);
						    RegisterActivity.this.finish();
						    startActivity(orderIntent);
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
	
	private OnClickListener idCategoryIVClick = new OnClickListener() {
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
	
	public void showDrop(View v) {
		if(popupWindow != null){
			if(!popupWindow.isShowing()){
				popupWindow.showAsDropDown(idCategoryIV, 0, 0);
			}else{
				popupWindow.dismiss();
			}
		}
	}
	
	private void dissmissPop(){
		if(popupWindow != null)
			popupWindow.dismiss();
	}
	
	public void toLogin(View v) {
		this.finish();
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
	}

}
