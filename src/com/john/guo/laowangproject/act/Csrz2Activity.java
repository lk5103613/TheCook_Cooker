package com.john.guo.laowangproject.act;

import android.os.Bundle;
import android.view.View;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.views.AsyncCircleImageView;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class Csrz2Activity extends BaseActivity {
	
	private AsyncCircleImageView avatarIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_csrz2);
	}
	
	@Override
	protected void initView() {
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context, "厨师认证"));
		avatarIV = (AsyncCircleImageView) findViewById(R.id.imageview1);
		viewSizeHelper.setSize(avatarIV, screenWidth*150/356, screenWidth*150/356);
		avatarIV.getProgressBar().setVisibility(View.GONE);
		avatarIV.getImageView().setImageResource(R.drawable.image_avatar);
	}

}
