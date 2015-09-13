package com.john.guo.laowangproject.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dcjd.cook.cs.R;

public class DemoActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
	}

	@Override
	protected void initView() {
	}
	
	//编辑用户信息
	public void btn1Click(View v){
		startActivity(new Intent(context, EditUserInfoActivity.class));
	}
	
	//修改金额
	public void btn2Click(View v){
		startActivity(new Intent(context, EditCashActivity.class));
	}
	
	//我的订单
	public void btn3Click(View v){
		startActivity(new Intent(context, IndexOrderUI.class));
	}
	
	//厨师认证
	public void btn4Click(View v){
		startActivity(new Intent(context, CsrzActivity.class));
	}
	
	//我是大厨
	public void btn5Click(View v){
		startActivity(new Intent(context, WsdcActivity.class));
	}
	
	//确认订单
	public void btn6Click(View v){
		startActivity(new Intent(context, MyOrder2Activity.class));
	}
	
	//确认订单
	public void btn7Click(View v){
		startActivity(new Intent(context, MyOrder3Activity.class));
	}
	
	//确认订单
	public void btn8Click(View v){
		startActivity(new Intent(context, Csrz2Activity.class));
	}
	
	//空挡时间设置
	public void btn9Click(View v){
		startActivity(new Intent(context, SpaceTimeSettingActivity.class));
	}
	
	//注册
	public void btn10Click(View v){
		startActivity(new Intent(context, RegisterActivity.class));
	}
	
	//厨师认证
	public void btn11Click(View v){
		startActivity(new Intent(context, ApproveActivity.class));
	}
	
	//加菜
	public void btn12Click(View v){
		startActivity(new Intent(context, AddFootActivity.class));
	}
	
	//服务记录
	public void btn13Click(View v){
		startActivity(new Intent(context, ServiceRecordActivity.class));
	}

}
