package com.john.guo.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.commons.adapter.BaseArrayAdapter;
import com.honestwalker.androidutils.commons.adapter.BaseViewHolder;
import com.john.guo.entity.AddFootBean;

public class AddFootAdapter extends BaseArrayAdapter<AddFootBean> {

	public AddFootAdapter(Context context, List<AddFootBean> data) {
		super(context, R.layout.item_addfoot, data);
	}

	@Override
	protected void addItemData(View convertView, AddFootBean item, int position) {
		ViewHolder holder = getViewHolder(convertView, ViewHolder.class);
		holder.nameTV.setText(item.getName());
		holder.priceTV.setText(Html.fromHtml("￥"+"<font color=#F39801>"+item.getPrice()+"</font>"));
		holder.numberTV.setText(Html.fromHtml("<font color=#F39801>"+item.getNumber()+"</font>"+"份"));
	}

	private class ViewHolder extends BaseViewHolder {

		private TextView nameTV;
		private TextView priceTV;
		private TextView numberTV;

		public ViewHolder(View baseView) {
			super(baseView);
			nameTV = (TextView) findViewById(R.id.textview1);
			priceTV = (TextView) findViewById(R.id.textview2);
			numberTV = (TextView) findViewById(R.id.textview3);
		}

	}

}
