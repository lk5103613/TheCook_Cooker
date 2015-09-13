package com.john.guo.laowangproject.act;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.views.AsyncCircleImageView;
import com.honestwalker.androidutils.window.ToastHelper;

public class MyOrder2Activity extends BaseActivity {
	
	private AsyncCircleImageView avatarIV;
	private TextView phoneTV;
	private TextView price1;
	private TextView price2;
	private Button completeBTN;
	
	private Dialog dialog;
	private View dialogView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder2);
	}

	@Override
	protected void initView() {
		avatarIV = (AsyncCircleImageView) findViewById(R.id.imageview1);
		phoneTV  =(TextView) findViewById(R.id.textview1);
		price1  =(TextView) findViewById(R.id.textview2);
		price2  =(TextView) findViewById(R.id.textview3);
		completeBTN = (Button) findViewById(R.id.btn1);
		
		avatarIV.getImageView().setBorderColor(Color.WHITE);
		avatarIV.getImageView().setBorderWidth(4);
		avatarIV.loadUrl("https://www.baidu.com/img/bd_logo1.png");
		phoneTV.setText(Html.fromHtml("贵宾万先生&nbsp;&nbsp;<font color=#b64920><u>15867654324</u></font>"));
		price1.setText(Html.fromHtml("￥<font color=#FF9101>28</font>"));
		price2.setText(Html.fromHtml("￥<font color=#FF9101>28</font>"));
		
//		initDialog();
		completeBTN.setOnClickListener(completeBTNClick);
	}
	
//	private void initDialog(){
//		dialog = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Dialog).create();
//		dialogView = inflater.inflate(R.layout.dialog_myorder, null);
//		
//		dialogView.findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				ToastHelper.alert(context, "还没有");
//				dialog.dismiss();
//			}
//		});
//		
//		dialogView.findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				ToastHelper.alert(context, "确认");
//				dialog.dismiss();
//			}
//		});
//	}
	
	private OnClickListener completeBTNClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
//			dialog.show();
//			dialog.setContentView(dialogView);
			ToastHelper.alert(context, "确认订单");
		}
	};

}
