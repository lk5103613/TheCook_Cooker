package com.john.guo.laowangproject.act;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.cs.R;
import com.john.guo.entity.CommonResult;
import com.john.guo.entity.LoginResult;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;
import com.john.guo.util.DateUtil;
import com.like.wheel.widget.adapters.ArrayWheelAdapter;
import com.like.wheel.widget.views.OnWheelChangedListener;
import com.like.wheel.widget.views.WheelView;

public class SpaceTimeSetting2Activity extends MyBaseActivity implements
		OnClickListener, OnFocusChangeListener {

	private WheelView mHourWheel, mMinWheel, mUnitWheel;
	private int defaultTextSize = 18;
	private ArrayWheelAdapter<String> mAdapter1, mAdapter2, mAdapter3;
	private int mItemHeight = 130;
	private int[] colors = new int[] { R.color.choise_color_4,
			R.color.choise_color_3, R.color.choise_color_2,
			R.color.choise_color_1 };
	private String mLoginUser;
	private TextView mBaoCun;
	private TextView mTitleView;
	private List<String> weeks = new ArrayList<String>();
	private EditText mFromTime;
	private EditText mToTime;
	private EditText mCurrentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spacetimesetting2);
		mBaoCun = (TextView) findViewById(R.id.action);
		mTitleView = (TextView) findViewById(R.id.title_middle_tv);
		mFromTime = (EditText) findViewById(R.id.from_time);
		mToTime = (EditText) findViewById(R.id.to_time);
//		mFromTime.setInputType(InputType.TYPE_NULL); 
//		mToTime.setInputType(InputType.TYPE_NULL);

		mFromTime.setOnFocusChangeListener(this);
		mToTime.setOnFocusChangeListener(this);

		mTitleView.setText("空档时间设置");
		mBaoCun.setOnClickListener(this);
		mBaoCun.setVisibility(View.VISIBLE);

		initWheel();
		mLoginUser = mLoginSharef.getString(LOGIN_USER, "");
	}

	private void initWheel() {
		mHourWheel = (WheelView) findViewById(R.id.hour_wheel);
		mMinWheel = (WheelView) findViewById(R.id.min_wheel);
		mUnitWheel = (WheelView) findViewById(R.id.unit_wheel);
		mHourWheel.setVisibleItems(7);
		mMinWheel.setVisibleItems(7);
		mUnitWheel.setVisibleItems(7);
		mAdapter1 = new ArrayWheelAdapter<String>(this, getHour(), mItemHeight);
		mAdapter2 = new ArrayWheelAdapter<String>(this, getMinu(), mItemHeight);
		mAdapter3 = new ArrayWheelAdapter<String>(this, new String[] { "AM",
				"PM" }, mItemHeight);
		mHourWheel.setViewAdapter(mAdapter1);
		mMinWheel.setViewAdapter(mAdapter2);
		mUnitWheel.setViewAdapter(mAdapter3);
		mHourWheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mAdapter1);
				invalidateTime();
			}
		});
		mMinWheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = wheel.getCurrentItem();
				setCurrentItem(position, mAdapter2);
				invalidateTime();
			}
		});
		mUnitWheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int position = mUnitWheel.getCurrentItem();
				setCurrentItem(position, mAdapter3);
				invalidateTime();
			}
		});
	}

	private void invalidateTime() {
		String hour = mAdapter1.getItemText(mHourWheel.getCurrentItem())
				.toString();
		String min = mAdapter2.getItemText(mMinWheel.getCurrentItem())
				.toString();
		String unit = mAdapter3.getItemText(mUnitWheel.getCurrentItem())
				.toString();
		if (mCurrentView != null) {
			mCurrentView.setText(hour + ":" + min + " " + unit);
		}
	}

	private String[] getHour() {
		String[] hour = new String[12];
		for (int i = 1; i <= 12; i++) {
			if (i < 10) {
				hour[i - 1] = "0" + i;
			} else {
				hour[i - 1] = i + "";
			}
		}
		return hour;
	}

	private String[] getMinu() {
		String[] min = new String[60];
		for (int i = 1; i <= 60; i++) {
			if (i < 10) {
				min[i - 1] = "0" + i;
			} else {
				min[i - 1] = i + "";
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
				weeks.remove(tv.getText().toString());
			} else {
				tv.setSelected(true);
				weeks.add(tv.getText().toString());
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action:
			if (TextUtils.isEmpty(mFromTime.getText().toString()) || TextUtils.isEmpty(mToTime.getText().toString())) {
				Toast.makeText(mContext, "请选择开始与结束时间", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (weeks.isEmpty()) {
				Toast.makeText(mContext, "请选择日期", Toast.LENGTH_LONG).show();
				return;
			}
			addSpaceTime();
			break;
		default:
			break;
		}

	}

	private void addSpaceTime() {
		LoginResult result = getLoginUser();
		String weekDays = list2Str(weeks, ',');
		String fromTime = DateUtil.formatTime(mFromTime.getText().toString());
		String toTime = DateUtil.formatTime(mToTime.getText().toString());

		System.out.println("format time " + fromTime);

		mDataFetcher.fetchAddSpaceTime(result.cid, result.cmp, weekDays,
				fromTime, toTime, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
						if (result.code==1) {
							Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext, "设置失败", Toast.LENGTH_SHORT).show();
						}
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	private String list2Str(List<String> list, char separator) {
		System.out.println("list " + list.size());

		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0; i < list.size(); i++) {

				sb.append(URLEncoder.encode(list.get(i), "utf-8")).append(separator);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String result = sb.toString().substring(0, sb.toString().length() - 1);

		return result;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.from_time:
			if (hasFocus) {
				mCurrentView = mFromTime;
			}
			break;
		case R.id.to_time:
			if (hasFocus) {
				mCurrentView = mToTime;
			}
			break;

		default:
			break;
		}

	}

}
