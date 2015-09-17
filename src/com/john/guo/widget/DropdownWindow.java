package com.john.guo.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.john.guo.adapter.SimpleAdapter;

public class DropdownWindow<T> extends PopupWindow {
	
	private View mTargetView;
	private TextView mShowView;
	private Context mContext;
	private SimpleAdapter<T> mAdapter;
	private OnPopChangeListener mOnChangeListener;
	private int mPosition;
	
	public DropdownWindow(Context context,View target, TextView showView,  SimpleAdapter<T> adapter) {
		this.mContext = context;
		this.mTargetView = target;
		this.mShowView = showView;
		this.mAdapter = adapter;
		init();
	}
	
	private void init() {
		View v = LayoutInflater.from(mContext).inflate(R.layout.dropdown_list, null, false);
		setContentView(v);
		setWidth(300);
		setHeight(400);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		
		final ListView list = (ListView) v.findViewById(R.id.dropdown_list);
		list.setAdapter(mAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				mShowView.setText(mAdapter.getValue(position));
//				
				if (mOnChangeListener != null && mPosition != position) {
					System.out.println("change");
					mOnChangeListener.onChange(mShowView, position);
				}
				
				mPosition = position;
				if(isShowing()){
					dismiss();
				}
			}
		});
	}
	
	public void setOnChangeListener(OnPopChangeListener onChangeListener){
		this.mOnChangeListener = onChangeListener;
		
	}
	
	public void validate(){
		mAdapter.notifyDataSetChanged();
	}
	
	public interface OnPopChangeListener{
		public void onChange(View showView, int position);
	}
	
}
