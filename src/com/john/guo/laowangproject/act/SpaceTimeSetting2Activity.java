package com.john.guo.laowangproject.act;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;
import com.like.wheel.widget.adapters.ArrayWheelAdapter;
import com.like.wheel.widget.views.OnWheelChangedListener;
import com.like.wheel.widget.views.WheelView;

public class SpaceTimeSetting2Activity extends BaseActivity {

	private WheelView mWheel1, mWheel2, mWheel3;
	private int defaultTextSize = 18;
	private ArrayWheelAdapter<String> mAdapter1, mAdapter2, mAdapter3;
	private int mItemHeight = 130;
	private int[] colors = new int[] { R.color.choise_color_4,
			R.color.choise_color_3, R.color.choise_color_2,
			R.color.choise_color_1 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spacetimesetting2);
		initWheel();
	}

	@Override
	protected void initView() {
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context,
				"空挡时间设置"));
	}
	
	private void initWheel() {
		mWheel1 = (WheelView) findViewById(R.id.numberpick1);
		mWheel2 = (WheelView) findViewById(R.id.numberpick2);
		mWheel3 = (WheelView) findViewById(R.id.numberpick3);
		mWheel1.setVisibleItems(7);
		mWheel2.setVisibleItems(7);
		mWheel3.setVisibleItems(7);
		mAdapter1 = new ArrayWheelAdapter<String>(this, getHour(), mItemHeight);
		mAdapter2 = new ArrayWheelAdapter<String>(this, getMinu(), mItemHeight);
		mAdapter3 = new ArrayWheelAdapter<String>(this, new String[] {"AM", "PM"}, mItemHeight);
		mWheel1.setViewAdapter(mAdapter1);
		mWheel2.setViewAdapter(mAdapter2);
		mWheel3.setViewAdapter(mAdapter3);
		mWheel1.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mAdapter1);
			}
		});
		mWheel2.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mAdapter2);
			}
		});
		mWheel3.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = mWheel3.getCurrentItem();
				setCurrentItem(position, mAdapter3);
			}
		});
	}
	
	private String[] getHour() {
		String[] hour = new String[12];
		for(int i=1; i<=12; i++) {
			if(i < 10) {
				hour[i-1] = "0" + i;
			} else {
				hour[i-1] = i+"";
			}
		}
		return hour;
	}
	
	private String[] getMinu() {
		String[] min = new String[60];
		for(int i=1; i<=60; i++) {
			if(i < 10) {
				min[i-1] = "0" + i;
			} else {
				min[i-1] = i+"";
			}
		}
		return min;
	}

	private void setCurrentItem(int position, ArrayWheelAdapter adapter) {
		int totalCount = adapter.getItemsCount();
		int minPosition = position - 3 < 0 ? 0 : position - 3;
		int maxPosition = position + 3 > totalCount - 1 ? totalCount - 1
				: position + 3;
		for (int i = minPosition; i <= maxPosition; i++) {
			int offset = Math.abs(position - i);
			List<TextView> vs = adapter.getTextViews();
			TextView v = vs.get(i);
			if (v == null) {
				continue;
			}
			v.setTextSize(defaultTextSize + 6 - 5 * offset);
			v.setTextColor(getResources().getColor(colors[offset]));
		}
	}

	public void weekClick(View v) {
		if (v instanceof TextView) {
			TextView tv = (TextView) v;
			if (tv.isSelected()) {
				tv.setSelected(false);
			} else {
				tv.setSelected(true);
			}
		}
	}

}
