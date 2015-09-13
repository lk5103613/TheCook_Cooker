package com.john.guo.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.john.guo.util.DateUtil;
import com.like.wheel.widget.adapters.ArrayWheelAdapter;
import com.like.wheel.widget.views.OnWheelChangedListener;
import com.like.wheel.widget.views.WheelView;


public class DatePickerWindow extends PopupWindow implements PopupWindow.OnDismissListener{
	private Context mContext;
	private List<String> mYears = new ArrayList<String>();
	private List<String> mMonths = new ArrayList<String>();
	private List<String> mDates = new ArrayList<String>();

	private WheelView mYearWheel, mMonthWheel, mDateWheel;
	private ArrayWheelAdapter<String> mYearAdapter, mMonthAdapter, mDateAdapter;
	private int mWidth;
	private int mHeight;
	private TextView mTargetView;

	public DatePickerWindow(Context context, int width, int height, TextView target) {
		this.mContext = context;
		this.mWidth = width;
		this.mHeight = height;
		
		this.mTargetView = target;

		initView();
	}

	private void initView() {
		View v = LayoutInflater.from(mContext).inflate(R.layout.popup_date_picker,
				null, false);
		initWheel(v);
		
		setContentView(v);
		setWidth(mWidth);
		setHeight(mHeight);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
	}

	private void initWheel(View parent) {
		
		int year = DateUtil.getYear();
		int month = DateUtil.getMonth();
		int day = DateUtil.getDay();
		
		for (int i = year-20; i <= year; i++) {
			mYears.add(i+"");
		}
		
		for (int i = 1; i <= 12; i++) {
			mMonths.add(i+"");
		}
		
		for (int i = 1; i <= 30; i++) {
			mDates.add(i+"");
		}
		
		mYearWheel = (WheelView) parent.findViewById(R.id.year_picker);
		mMonthWheel = (WheelView) parent.findViewById(R.id.month_picker);
		mDateWheel = (WheelView) parent.findViewById(R.id.day_picker);

		mYearWheel.setDrawShadow(false);
		mMonthWheel.setDrawShadow(false);
		mDateWheel.setDrawShadow(false);

		mYearAdapter = new ArrayWheelAdapter<String>(mContext, (String[])mYears.toArray(new String[mYears.size()]), 70);
		mMonthAdapter = new ArrayWheelAdapter<String>(mContext,(String[])mMonths.toArray(new String[mMonths.size()]), 70);
		mDateAdapter = new ArrayWheelAdapter<String>(mContext, (String[])mDates.toArray(new String[mDates.size()]), 70);
		
		mYearWheel.setViewAdapter(mYearAdapter);
		mMonthWheel.setViewAdapter(mMonthAdapter);
		mDateWheel.setViewAdapter(mDateAdapter);
		
		setCurrentItem(mYearWheel.getCurrentItem(), mYearAdapter, Gravity.RIGHT);
		setCurrentItem(mMonthWheel.getCurrentItem(), mMonthAdapter, Gravity.CENTER);
		setCurrentItem(mDateWheel.getCurrentItem(), mDateAdapter, Gravity.CENTER);

		mYearWheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mYearAdapter, Gravity.RIGHT
						| Gravity.CENTER_VERTICAL);
				setDate();
			}
		});
		mMonthWheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mMonthAdapter, Gravity.CENTER);
				setDate();
				
			}
		});
		mDateWheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mDateAdapter, Gravity.CENTER);
			}
		});
		
		setOnDismissListener(this);
	}
	
	 private void setCurrentItem(int position, ArrayWheelAdapter adapter, int gravity) {
	        int totalCount = adapter.getItemsCount();
	        int minPosition = position - 2 < 0 ? 0 : position - 2;
	        int maxPosition = position + 2 > totalCount - 1 ? totalCount - 1 : position + 2;
	        for(int i=minPosition; i<=maxPosition; i++) {
	            int offset = Math.abs(position - i);
	            List<TextView> vs = adapter.getTextViews();
	            TextView v = vs.get(i);
	            
	            if(v == null) {
	                continue;
	            }
	            v.setTextSize(16 - 2 * offset);
	            v.setGravity(gravity);
	            v.setTextColor(Color.WHITE);
	        }
	    }

	@Override
	public void onDismiss() {
		if (mTargetView != null) {
			String year = (String) mYearAdapter.getItemText(mYearWheel.getCurrentItem());
			String month = (String) mMonthAdapter.getItemText(mMonthWheel.getCurrentItem());
			String date = (String) mDateAdapter.getItemText(mDateWheel.getCurrentItem());
			mTargetView.setText(year+"-" + month+"-" + date);
		}
		
	}
	
	private void setDate(){
		String year = mYears.get(mYearWheel.getCurrentItem());
		String month = mMonths.get(mMonthWheel.getCurrentItem());
		int count = DateUtil.calDayByYearAndMonth(year, month);
		
		if (count == mDates.size()) {
			return;
		}
		
		mDates.clear();
		for (int i = 1; i <=count; i++) {
			mDates.add(i+"");
		}
		
		mDateAdapter = new ArrayWheelAdapter<String>(mContext, (String[])mDates.toArray(new String[mDates.size()]), 70);
		mDateWheel.setViewAdapter(mDateAdapter);
		
		mDateWheel.setCurrentItem((mDateWheel.getCurrentItem() >= count ? count-1 : mDateWheel.getCurrentItem()));
		setCurrentItem((mDateWheel.getCurrentItem() >= count ? count-1 : mDateWheel.getCurrentItem()), mDateAdapter, Gravity.CENTER);
	}

}
