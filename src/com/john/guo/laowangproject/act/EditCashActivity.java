package com.john.guo.laowangproject.act;

import android.os.Bundle;

import com.dcjd.cook.cs.R;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class EditCashActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcash);
	}

	@Override
	protected void initView() {
		
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context, "修改金额"));
		
	}

}
