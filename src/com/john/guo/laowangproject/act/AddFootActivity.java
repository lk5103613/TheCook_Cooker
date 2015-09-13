package com.john.guo.laowangproject.act;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.dcjd.cook.cs.R;
import com.john.guo.adapter.AddFootAdapter;
import com.john.guo.entity.AddFootBean;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class AddFootActivity extends BaseActivity {
	
	private ListView footLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfoot);
	}
	
	@Override
	protected void initView() {
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context, "加菜"));
		footLV = (ListView) findViewById(R.id.listview);
		footLV.setAdapter(new AddFootAdapter(context, initDataSource()));
	}
	
	private ArrayList<AddFootBean> initDataSource(){
		ArrayList<AddFootBean> data = new ArrayList<AddFootBean>();
		data.add(new AddFootBean("广东烧鸭套餐", 28, 1));
		data.add(new AddFootBean("广东烧鸭套餐", 30, 2));
		data.add(new AddFootBean("广东烧鸭套餐", 28, 1));
		data.add(new AddFootBean("广东烧鸭套餐", 30, 2));
		data.add(new AddFootBean("广东烧鸭套餐", 28, 1));
		data.add(new AddFootBean("广东烧鸭套餐", 30, 2));
		data.add(new AddFootBean("广东烧鸭套餐", 28, 1));
		data.add(new AddFootBean("广东烧鸭套餐", 30, 2));
		data.add(new AddFootBean("广东烧鸭套餐", 28, 1));
		data.add(new AddFootBean("广东烧鸭套餐", 30, 2));
		data.add(new AddFootBean("广东烧鸭套餐", 28, 1));
		data.add(new AddFootBean("广东烧鸭套餐", 30, 2));
		return data;
	}

}
