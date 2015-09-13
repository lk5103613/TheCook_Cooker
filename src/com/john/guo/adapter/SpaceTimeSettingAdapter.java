package com.john.guo.adapter;

import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.commons.adapter.BaseArrayAdapter;
import com.honestwalker.androidutils.commons.adapter.BaseViewHolder;
import com.john.guo.entity.SpaceTimeSettingBean;

public class SpaceTimeSettingAdapter extends BaseArrayAdapter<SpaceTimeSettingBean> {

	public SpaceTimeSettingAdapter(Context context, List<SpaceTimeSettingBean> data) {
		super(context, R.layout.item_spacetime, data);
	}

	@Override
	protected void addItemData(View convertView, SpaceTimeSettingBean item, int position) {
		ViewHolder holder = getViewHolder(convertView, ViewHolder.class);
		holder.timeTV.setText(item.getStartTime()+"~"+item.getEndTime());
		for (String dayStr : item.getSelectedDays()) {
			TextView dayTV = new TextView(mContext);
			dayTV.setTextColor(mContext.getResources().getColor(R.color.black));
			dayTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			dayTV.setPadding(0, 0, 20, 0);
			dayTV.setText(dayStr);
			holder.daysLL.addView(dayTV);
		}
	}
	
	private class ViewHolder extends BaseViewHolder{
		
		private TextView timeTV;
		private LinearLayout daysLL;

		public ViewHolder(View baseView) {
			super(baseView);
			timeTV = (TextView) findViewById(R.id.textview1);
			daysLL = (LinearLayout) findViewById(R.id.layout1);
		}
		
	}

}
