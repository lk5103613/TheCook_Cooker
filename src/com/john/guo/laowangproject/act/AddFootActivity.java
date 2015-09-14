package com.john.guo.laowangproject.act;

import java.lang.reflect.Type;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.cs.R;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.john.guo.adapter.AddFootAdapter;
import com.john.guo.adapter.AddFootAdapter.FragmentListener;
import com.john.guo.entity.ListResult;
import com.john.guo.entity.MenuEntity;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;
import com.john.guo.title.TitleArgBuilder;
import com.john.guo.title.TitleBuilder;

public class AddFootActivity extends BaseActivity implements FragmentListener {
	
	private PullToRefreshListView footLV;
	private int mCurrentPage = 0;
	private DataFetcher mDataFetcher;
	private AddFootAdapter mAdapter;
	private Button mBtnAdd;
	private int mCurrentSelected = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfoot);
		mDataFetcher = DataFetcher.getInstance(context);
		updateList();
	}
	
	@Override
	protected void initView() {
		new TitleBuilder(context, TitleArgBuilder.getBackBtnTitle(context, "加菜"));
		footLV = (PullToRefreshListView) findViewById(R.id.listview);
		mBtnAdd = (Button) findViewById(R.id.btn_add);
		mBtnAdd.setText("确认共加"+mCurrentSelected+"道菜");
		mBtnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	private void updateList() {
		mDataFetcher.fetchMenu(mCurrentPage+"", new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Type type = new TypeToken<ListResult<MenuEntity>>(){}.getType();
				ListResult<MenuEntity> menus = GsonUtil.gson.fromJson(response.toString(), type);
				if(mAdapter == null) {
					mAdapter = new AddFootAdapter(context, menus.resultList);
					mAdapter.setFragmentListener(AddFootActivity.this);
					footLV.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(menus.resultList);
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void checkboxCheck(boolean isCheck) {
		if(isCheck)
			mCurrentSelected++;
		else
			mCurrentSelected--;
		mBtnAdd.setText("确认共加"+mCurrentSelected+"道菜");
	}

}
