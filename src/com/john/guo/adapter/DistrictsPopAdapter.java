package com.john.guo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.john.guo.entity.Districts;

public class DistrictsPopAdapter extends SimpleAdapter<Districts>{

	public DistrictsPopAdapter(Context context, List<Districts> datas) {
		super(context, datas);
	}

	@Override
	public int getItemResourceId() {
		return R.layout.dropdown_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		TextView name = holder.findView(android.R.id.text1);
		name.setText(datas.get(position).name);
	}

	@Override
	public String getValue(int position) {
		return getItem(position).name;
	}

	

}
