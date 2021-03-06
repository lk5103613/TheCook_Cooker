package com.john.guo.adapter;

import java.util.List;
import java.util.Map;

import com.dcjd.cook.cs.R;
import com.john.guo.entity.City;
import com.john.guo.entity.Provience;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ProviencePopAdapter extends SimpleAdapter<Provience>{

	public ProviencePopAdapter(Context context, List<Provience> datas) {
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
