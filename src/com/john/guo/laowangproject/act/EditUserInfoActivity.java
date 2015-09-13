package com.john.guo.laowangproject.act;

import android.graphics.Color;
import android.os.Bundle;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.views.AsyncCircleImageView;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class EditUserInfoActivity extends BaseActivity {

	private AsyncCircleImageView avantarIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edituserinfo);
	}

	@Override
	protected void initView() {
		
		new TitleBuilder(context, TitleArgBuilder.getTitle("个人编辑资料"));
		
		avantarIV = (AsyncCircleImageView) findViewById(R.id.imageview1);
		avantarIV.getImageView().setBorderColor(Color.WHITE);
		avantarIV.getImageView().setBorderWidth(4);
		avantarIV.loadUrl("https://www.baidu.com/img/bd_logo1.png",screenWidth * 151 / 531, screenWidth * 151 / 531);
	}
}
