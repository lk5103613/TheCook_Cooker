package com.john.guo.laowangproject.act;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.cs.R;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.john.guo.adapter.IndexOrderAdapter;
import com.john.guo.adapter.ServiceRecordAdapter;
import com.john.guo.entity.CommonResult;
import com.john.guo.entity.IndexOrderEntity;
import com.john.guo.entity.LeftMenuEntity;
import com.john.guo.entity.ListResult;
import com.john.guo.entity.LoginResult;
import com.john.guo.entity.ServiceRecordBean;
import com.john.guo.network.APIS;
import com.john.guo.network.GsonUtil;
import com.john.guo.widget.RoundImageView;

public class IndexOrderUI extends MyBaseActivity {
	private final static int IDLE = 0;
	private final static int EXIT = 1;
	private final static int SUSPEND = 2;
	
	private final static int STATUS_COFIRM = 2;
	private final static int STATUS_COMPLETE = 8;
	
	private DrawerLayout mDrawerLayout;
	private PullToRefreshListView mOrderList;
	private IndexOrderAdapter mAdapter;
	private LoginResult mUser;
	private PullToRefreshListView mServiceRecordList;
	
	private RoundImageView mCookIcon;
	private TextView mLblCookName;
	private TextView mLblZanCnt;
	private TextView mLblCommentCnt;
	
	private Dialog mDialog;
	private ServiceRecordAdapter mServiceAdapter;
	
	private int mCurrentOperate = IDLE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_frame);
		
		mUser = getLoginUser();
		if(mUser == null) {
			this.finish();
			Intent intent = new Intent(mContext, LoginActivity.class);
			startActivity(intent);
			return;
		}
		mServiceRecordList = (PullToRefreshListView) findViewById(R.id.service_record_lst);
		mOrderList = (PullToRefreshListView) findViewById(R.id.order_list);
		mCookIcon = (RoundImageView) findViewById(R.id.cook_icon);
		mLblCookName = (TextView) findViewById(R.id.cook_name);
		mLblZanCnt = (TextView) findViewById(R.id.zan_cnt);
		mLblCommentCnt = (TextView) findViewById(R.id.comment_cnt);
		
		mOrderList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				updateOrder();
			}
		});
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		initLeftMenu();
		initRightMenu();
		updateOrder();
	}
	
	public void toggleMenu(View v){
		if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)){
			mDrawerLayout.closeDrawer(Gravity.RIGHT);
		}
		if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}else {
			mDrawerLayout.openDrawer(Gravity.LEFT);
		}
	}
	
	private void initLeftMenu() {
		mDataFetcher.fetchLeftMenu(mUser.cid + "", new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LeftMenuEntity entity = GsonUtil.gson.fromJson(response.toString(), LeftMenuEntity.class);
				mImgLoader.get(APIS.BASE_URL + entity.avatar, ImageLoader.getImageListener(mCookIcon, R.drawable.img_error, R.drawable.img_error));
				mLblCookName.setText(entity.truename);
				mLblCommentCnt.setText(entity.commentcnt);
				mLblZanCnt.setText(entity.zanCnt);
			}
		}, mErrorListener);
	}
	
	private void initRightMenu() {
		mDataFetcher.fetchServiceLog(mUser.cid, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Type type = new TypeToken<List<ServiceRecordBean>>(){}.getType();
				List<ServiceRecordBean> logs = GsonUtil.gson.fromJson(response.toString(), type);
				if(mServiceAdapter == null) {
					mServiceAdapter = new ServiceRecordAdapter(mContext, logs);
					mServiceRecordList.setAdapter(mServiceAdapter);
				} else {
					mServiceAdapter.updateList(logs);
				}
			}
		}, mErrorListener);
	}
	
	private void updateOrder() {
		mDataFetcher.fetchIndexOrder(mUser.cid, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Type type = new TypeToken<ListResult<IndexOrderEntity>>(){}.getType();
				ListResult<IndexOrderEntity> result = GsonUtil.gson.fromJson(response.toString(), type);
				if(mAdapter == null) {
					mAdapter = new IndexOrderAdapter(mContext, result.list);
					mOrderList.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(result.list);
				}
				if(mOrderList.isRefreshing())
					mOrderList.onRefreshComplete();
			}
		}, mErrorListener);
	}
	
	public void toComment(View v){
		if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}
		if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)){
			mDrawerLayout.closeDrawer(Gravity.RIGHT);
		}else {
			mDrawerLayout.openDrawer(Gravity.RIGHT);
		}
	}
	
	private void showDialog(boolean show) {
		if(show) {
			if(mDialog == null) {
				mDialog = new Dialog(mContext, R.style.Theme_dialog);
				View view = LayoutInflater.from(mContext).inflate(R.layout.confirm_dialog, null);
				Button btnOk = (Button)view.findViewById(R.id.ok);
				Button btnCancel = (Button) view.findViewById(R.id.cancel);
				btnOk.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(mCurrentOperate == EXIT) {
							logout();
						} else if(mCurrentOperate == SUSPEND) {
							suspend();
						} else 
							mDialog.dismiss();
					}
				});
				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mCurrentOperate = IDLE;
						mDialog.dismiss();
					}
				});
				mDialog.setContentView(view);
			}
			mDialog.show();
		} else {
			if(mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
			}
		}

	}
	
	private void logout() {
		if(mDialog.isShowing())
			mDialog.dismiss();
		new Thread(new Runnable() {
			@Override
			public void run() {
				mLoginSharef.edit().putString(LOGIN_USER, null).commit();
			}
		}).start();
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
	}
	
	private void suspend() {
		mDataFetcher.fetchExit(mUser.cid+"", new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
				if(mDialog.isShowing())
					mDialog.dismiss();
				if(result.code == 1) {
					Toast.makeText(mContext, "暂停服务成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "暂停服务失败", Toast.LENGTH_SHORT).show();
				}
				mCurrentOperate = IDLE;
			}
		}, mErrorListener);
	}
	
	public void suspend(View v) {
		mCurrentOperate = SUSPEND;
		showDialog(true);
	}
	
	public void exit(View v) {
		mCurrentOperate = EXIT;
		showDialog(true);
	}
	
	public void setSpaceTime(View v) {
		Intent intent = new Intent(mContext, SpaceTimeSetting2Activity.class);
		startActivity(intent);
	}
	
}
