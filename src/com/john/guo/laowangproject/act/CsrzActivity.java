package com.john.guo.laowangproject.act;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.views.AsyncCircleImageView;
import com.john.guo.entity.LoginResult;
import com.john.guo.network.APIS;
import com.john.guo.network.GsonUtil;
import com.john.guo.network.MyNetworkUtil;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class CsrzActivity extends BaseActivity {
	
	private AsyncCircleImageView avatarIV;
	
	private LoginResult mResult;
	private ImageLoader mImgLoader;
	private TextView mLblReason;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_csrz);
		
		
	}

	@Override
	protected void initView() {
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context, "厨师认证"));
		avatarIV = (AsyncCircleImageView) findViewById(R.id.imageview1);
		viewSizeHelper.setSize(avatarIV, screenWidth*150/356, screenWidth*150/356);
		avatarIV.getProgressBar().setVisibility(View.GONE);
//		avatarIV.getImageView().setImageResource(R.drawable.image_avatar);
		
		mImgLoader = MyNetworkUtil.getInstance(context).getImageLoader();
		Intent intent = getIntent();
		if(intent != null) {
			String json = intent.getStringExtra("login_result");
			mResult = GsonUtil.gson.fromJson(json, LoginResult.class);
			mLblReason = (TextView) findViewById(R.id.reason);
			mImgLoader.get(APIS.BASE_URL + mResult.avatar, ImageLoader.
					getImageListener(avatarIV.getImageView(), R.color.white, R.color.white));
			mLblReason.setText(mResult.reason);
		}
	}

}
