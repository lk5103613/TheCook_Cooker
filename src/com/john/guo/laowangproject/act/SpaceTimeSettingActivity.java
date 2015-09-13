package com.john.guo.laowangproject.act;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.dcjd.cook.cs.R;
import com.john.guo.adapter.SpaceTimeSettingAdapter;
import com.john.guo.entity.SpaceTimeSettingBean;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class SpaceTimeSettingActivity extends BaseActivity {
	
	private ListView spaceTimeSettingLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spacetimesetting);
	}
	
	@Override
	protected void initView() {
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context, "空挡时间设置"));
		spaceTimeSettingLV = (ListView) findViewById(R.id.listview);
		spaceTimeSettingLV.setAdapter(new SpaceTimeSettingAdapter(context, initDateSource()));
	}
	
	private ArrayList<SpaceTimeSettingBean> initDateSource(){
		ArrayList<SpaceTimeSettingBean> data = new ArrayList<SpaceTimeSettingBean>();
		ArrayList<String> selectedDays = new ArrayList<String>();
		selectedDays.add("周一");
		selectedDays.add("周二");
		selectedDays.add("周三");
		selectedDays.add("周四");
		selectedDays.add("周五");
		ArrayList<String> selectedDays1 = new ArrayList<String>();
		selectedDays1.add("周六");
		selectedDays1.add("周日");
		data.add(new SpaceTimeSettingBean("08:10", "20:10", selectedDays));
		data.add(new SpaceTimeSettingBean("18:10", "20:10", selectedDays1));
		data.add(new SpaceTimeSettingBean("08:10", "20:10", selectedDays));
		data.add(new SpaceTimeSettingBean("18:10", "20:10", selectedDays1));
		data.add(new SpaceTimeSettingBean("08:10", "20:10", selectedDays));
		data.add(new SpaceTimeSettingBean("18:10", "20:10", selectedDays1));
		data.add(new SpaceTimeSettingBean("08:10", "20:10", selectedDays));
		data.add(new SpaceTimeSettingBean("18:10", "20:10", selectedDays1));
		data.add(new SpaceTimeSettingBean("08:10", "20:10", selectedDays));
		data.add(new SpaceTimeSettingBean("18:10", "20:10", selectedDays1));
		data.add(new SpaceTimeSettingBean("08:10", "20:10", selectedDays));
		data.add(new SpaceTimeSettingBean("18:10", "20:10", selectedDays1));
		data.add(new SpaceTimeSettingBean("08:10", "20:10", selectedDays));
		data.add(new SpaceTimeSettingBean("18:10", "20:10", selectedDays1));
		return data;
	}

}
