package com.john.guo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.commons.adapter.BaseViewHolder;
import com.john.guo.entity.ServiceRecordBean;

public class ServiceRecordAdapter extends BaseAdapter{
	
	private List<ServiceRecordBean> mLogs;
	private LayoutInflater mInflater;

	public ServiceRecordAdapter(Context context, List<ServiceRecordBean> data) {
		this.mLogs = data;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mLogs.size();
	}

	@Override
	public Object getItem(int position) {
		return mLogs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void updateList(List<ServiceRecordBean> logs) {
		this.mLogs = logs;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ServiceRecordBean log = mLogs.get(position);
		ViewHolder vh;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.item_servicerecord, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.timeTV.setText(log.addTime);
		vh.tipsTV.setText(log.customerName);
		vh.overTipsLL.setVisibility(View.GONE);
		return convertView;
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
