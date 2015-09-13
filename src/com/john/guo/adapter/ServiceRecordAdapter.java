package com.john.guo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.commons.adapter.BaseArrayAdapter;
import com.honestwalker.androidutils.commons.adapter.BaseViewHolder;
import com.john.guo.entity.ServiceRecordBean;

public class ServiceRecordAdapter extends BaseArrayAdapter<ServiceRecordBean> {

	public ServiceRecordAdapter(Context context, List<ServiceRecordBean> data) {
		super(context, R.layout.item_servicerecord, data);
	}

	@Override
	protected void addItemData(View convertView, ServiceRecordBean item,int position) {
		ViewHolder holder = getViewHolder(convertView, ViewHolder.class);
		holder.timeTV.setText(item.getTime());
		holder.tipsTV.setText(item.getTips());
		if(item.isOver()){
			holder.overTipsLL.setVisibility(View.VISIBLE);
		}else{
			holder.overTipsLL.setVisibility(View.GONE);
		}
	}

	private class ViewHolder extends BaseViewHolder {

		private TextView timeTV;
		private TextView tipsTV;
		private LinearLayout overTipsLL;

		public ViewHolder(View baseView) {
			super(baseView);
			timeTV = (TextView) findViewById(R.id.textview1);
			tipsTV = (TextView) findViewById(R.id.textview2);
			overTipsLL = (LinearLayout) findViewById(R.id.layout1);
		}

	}

}
