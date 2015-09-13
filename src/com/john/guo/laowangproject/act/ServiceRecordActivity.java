package com.john.guo.laowangproject.act;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.dcjd.cook.cs.R;
import com.john.guo.adapter.ServiceRecordAdapter;
import com.john.guo.entity.ServiceRecordBean;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class ServiceRecordActivity extends BaseActivity {
	
	private ListView serverRecordLV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicerecord);
	}

	@Override
	protected void initView() {
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context, "服务记录"));
		serverRecordLV = (ListView) findViewById(R.id.listview);
		serverRecordLV.setAdapter(new ServiceRecordAdapter(context, initDataSource()));
	}
	
	private ArrayList<ServiceRecordBean> initDataSource(){
		ArrayList<ServiceRecordBean> data = new ArrayList<ServiceRecordBean>();
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "王先生家服务4小时", false));
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "李先生家服务6小时", false));
		data.add(new ServiceRecordBean("2015-7-7\t17:30", "该订单已经结算共计", true));
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "王先生家服务4小时", false));
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "李先生家服务6小时", false));
		data.add(new ServiceRecordBean("2015-7-7\t17:30", "该订单已经结算共计", true));
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "王先生家服务4小时", false));
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "李先生家服务6小时", false));
		data.add(new ServiceRecordBean("2015-7-7\t17:30", "该订单已经结算共计", true));
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "王先生家服务4小时", false));
		data.add(new ServiceRecordBean("2015-6-8\t17:30", "李先生家服务6小时", false));
		data.add(new ServiceRecordBean("2015-7-7\t17:30", "该订单已经结算共计", true));
		return data;
	}

}
