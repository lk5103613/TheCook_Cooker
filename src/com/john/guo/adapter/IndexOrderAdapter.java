package com.john.guo.adapter;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.cs.R;
import com.john.guo.entity.CommonResult;
import com.john.guo.entity.IndexOrderEntity;
import com.john.guo.entity.IndexOrderEntity.OrderDetailItem;
import com.john.guo.laowangproject.act.EditCashActivity;
import com.john.guo.network.APIS;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;
import com.john.guo.network.MyNetworkUtil;

public class IndexOrderAdapter extends BaseAdapter {
	
	private List<IndexOrderEntity> mOrders;
	private LayoutInflater mInflater;
	private ImageLoader mImgLoader;
	private DataFetcher mDataFetcher;
	private Context mContext;
	
	private float mTotalMoney;
	
	public IndexOrderAdapter(Context context, List<IndexOrderEntity> orders) {
		this.mOrders = orders;
		mInflater = LayoutInflater.from(context);
		mImgLoader = MyNetworkUtil.getInstance(context).getImageLoader();
		mDataFetcher = DataFetcher.getInstance(context);
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mOrders.size();
	}

	@Override
	public Object getItem(int position) {
		return mOrders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IndexOrderEntity entity = mOrders.get(position);
		ViewHolder vh;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.index_order_item, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.sLblPrice.setText("订单总额：￥" + entity.allMoney);
		float alreadyPay = entity.alreadyPay;
		float needPay = entity.allMoney - entity.alreadyPay;
		vh.sLblPayInfo.setText("（已付"+alreadyPay+"元，待付"+needPay+"元）");
		vh.sLblDinnerTime.setText("用餐时间：" + entity.diningTime);
		vh.sLblServiceCnt.setText("服务人数：" + entity.servicerUsercnt +"人");
		vh.sLblArea.setText("厨房面积:" + entity.kitchenSize + "平");
		if(entity.zidaiCanju.equals("0")) {
			vh.sLblNeedTool.setText("需要带餐厨具：中式");
		} else {
			vh.sLblNeedTool.setText("需要带餐厨具：西式");
		}
		vh.sLblPayOption.setText("支付方式：上门支付");
		vh.sLblSpecialComment.setText("特殊需求：" + entity.specialMemo);
		vh.sDetailContainer.removeAllViews();
		int status = Integer.valueOf(entity.status);
		if(status <= 1){
			vh.sStep1.setVisibility(View.VISIBLE);
			vh.sStep2.setVisibility(View.GONE);
		} else if(status == 8) {
			vh.sStep1.setVisibility(View.GONE);
			vh.sStep2.setVisibility(View.VISIBLE);
		}
		vh.sOrderConfirm.setTag(entity.orderNo);
		vh.sChangeFigure.setTag(entity.orderNo);
		vh.sOrderComplete.setTag(entity.orderNo);
		
		if(entity.itemList != null) {
			for(int i=0; i<entity.itemList.size(); i++) {
				OrderDetailItem item = entity.itemList.get(i);
				View v = mInflater.inflate(R.layout.order_info_item, vh.sDetailContainer, false);
				ImageView img = (ImageView) v.findViewById(R.id.cook_img);
				TextView name = (TextView) v.findViewById(R.id.cook_name);
				TextView price = (TextView) v.findViewById(R.id.cook_price);
				TextView cnt = (TextView) v.findViewById(R.id.cook_cnt);
				mImgLoader.get(APIS.BASE_URL + item.imgPath, ImageLoader.getImageListener(img, R.color.white, R.color.white));
				name.setText(item.name);
				price.setText(item.price);
				cnt.setText(item.soldCnt);
				vh.sDetailContainer.addView(v);
				mTotalMoney += Float.parseFloat(item.price);
			}
		}
		return convertView;
	}
	
	public void updateList(List<IndexOrderEntity> orders) {
		this.mOrders = orders;
		notifyDataSetChanged();
	}
	
	public class ViewHolder {
		public TextView sLblName;
		public TextView sLblPhone;
		public TextView sLblAddress;
		public TextView sLblPrice;
		public TextView sLblPayInfo;
		public TextView sLblDinnerTime;
		public TextView sLblServiceCnt;
		public TextView sLblArea;
		public TextView sLblNeedTool;
		public TextView sLblPayOption;
		public TextView sLblSpecialComment;
		public ViewGroup sDetailContainer;
		public ViewGroup sStep1;
		public ViewGroup sStep2;
		public Button sOrderConfirm;
		public Button sOrderComplete;
		public Button sChangeFigure;
		public Button sAddDishes;
		
		public ViewHolder(View convertView) {
			sLblName = (TextView) convertView.findViewById(R.id.name);
			sLblPhone = (TextView) convertView.findViewById(R.id.tel);
			sLblAddress = (TextView) convertView.findViewById(R.id.address);
			sLblPrice = (TextView) convertView.findViewById(R.id.price);
			sLblPayInfo = (TextView) convertView.findViewById(R.id.pay_info);
			sLblDinnerTime = (TextView) convertView.findViewById(R.id.dinner_time);
			sLblServiceCnt = (TextView) convertView.findViewById(R.id.serv_cnt);
			sLblArea = (TextView) convertView.findViewById(R.id.area);
			sLblNeedTool = (TextView) convertView.findViewById(R.id.need_tool);
			sLblPayOption = (TextView) convertView.findViewById(R.id.pay_option);
			sLblSpecialComment = (TextView) convertView.findViewById(R.id.spec_comment);
			sDetailContainer = (ViewGroup) convertView.findViewById(R.id.order_detail_container);
			sStep1 = (ViewGroup) convertView.findViewById(R.id.step1);
			sStep2 = (ViewGroup) convertView.findViewById(R.id.step2);
			sOrderConfirm = (Button) convertView.findViewById(R.id.order_confirm);
			sOrderComplete = (Button) convertView.findViewById(R.id.order_complete);
			sChangeFigure = (Button) convertView.findViewById(R.id.change_figure);
			sAddDishes = (Button)convertView.findViewById(R.id.add_dishes);
			
			sOrderConfirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String orderId = (String) v.getTag();
					mDataFetcher.fetchConfirmOrder(orderId, "2", new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
							if(result.code == 1) {
								Toast.makeText(mContext, "确认订单成功", Toast.LENGTH_SHORT).show();
								sStep1.setVisibility(View.GONE);
								sStep2.setVisibility(View.VISIBLE);
							} else {
								Toast.makeText(mContext, "确认订单失败", Toast.LENGTH_SHORT).show();
							}
						}
					}, new ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
						}
					});
				}
			});
			sOrderComplete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String orderId = (String) v.getTag();
					mDataFetcher.fetchConfirmOrder(orderId, "8", new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
							if(result.code == 1) {
								Toast.makeText(mContext, "确认订单成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(mContext, "确认订单失败", Toast.LENGTH_SHORT).show();
							}
						}
					}, new ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
						}
					});
				}
			});
			
			sChangeFigure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String orderId = (String) v.getTag();
					Bundle params = new Bundle();
					params.putString("order_id", orderId);
					params.putFloat("total_money", mTotalMoney);
					Intent intent = new Intent(mContext, EditCashActivity.class);
					intent.putExtras(params);
					mContext.startActivity(intent);
					
				}
			});
			
		}
	}

}
