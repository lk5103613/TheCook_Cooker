package com.john.guo.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dcjd.cook.cs.R;

public class DropdownWindow extends PopupWindow {
	
	private View mTargetView;
	private TextView mShowView;
	private Context mContext;
	private ArrayAdapter<String> mAdapter;
	
	public DropdownWindow(Context context, TextView showView, View target, String...strings) {
		this.mContext = context;
		this.mTargetView = target;
		this.mShowView = showView;
		this.mAdapter = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, strings);
		init();
	}
	
	private void init() {
		View v = LayoutInflater.from(mContext).inflate(R.layout.dropdown_list, null, false);
		setContentView(v);
		setWidth(300);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		
		ListView list = (ListView) v.findViewById(R.id.dropdown_list);
		list.setAdapter(mAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String value = mAdapter.getItem(position);
				mShowView.setText(value);
				if(isShowing())
					dismiss();
			}
		});
	}

}
