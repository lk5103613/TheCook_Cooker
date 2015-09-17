package com.john.guo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.commons.adapter.BaseViewHolder;
import com.john.guo.entity.MenuEntity;
import com.john.guo.network.APIS;
import com.john.guo.network.MyNetworkUtil;

public class AddFootAdapter extends BaseAdapter {
	
	private List<MenuEntity> mMenus;
	private ImageLoader mImgLoader;
	private LayoutInflater mInflater;
	private List<Integer> mSelected = new ArrayList<Integer>();
	private FragmentListener mListener;

	public AddFootAdapter(Context context, List<MenuEntity> data) {
		this.mMenus = data;
		mImgLoader = MyNetworkUtil.getInstance(context).getImageLoader();
		mInflater = LayoutInflater.from(context);
	}

	private class ViewHolder extends BaseViewHolder {
		private TextView nameTV;
		private TextView priceTV;
		private TextView numberTV;
		private ViewGroup add;
		private ViewGroup remove;
		private ViewGroup delete;
		private ImageView img;
		private CheckBox check;
		private ViewGroup checkContainer;

		public ViewHolder(View baseView) {
			super(baseView);
			nameTV = (TextView) findViewById(R.id.tc_name);
			priceTV = (TextView) findViewById(R.id.money);
			numberTV = (TextView) findViewById(R.id.count);
			add = (ViewGroup) findViewById(R.id.jia);
			remove = (ViewGroup) findViewById(R.id.jian);
			delete = (ViewGroup) findViewById(R.id.delete);
			img = (ImageView) findViewById(R.id.tc_img);
			check = (CheckBox) findViewById(R.id.check);
			checkContainer = (ViewGroup) findViewById(R.id.check_container);
			
			add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					MenuEntity menu = (MenuEntity) getItem(position);
					int soldCnt = Integer.valueOf(menu.soldCnt);
					menu.soldCnt = soldCnt + 1 + "";
					notifyDataSetChanged();
				}
			});
			remove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					MenuEntity menu = (MenuEntity) getItem(position);
					int soldCnt = Integer.valueOf(menu.soldCnt);
					if(soldCnt != 0)
						menu.soldCnt = soldCnt - 1 + "";
					notifyDataSetChanged();
				}
			});
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					mMenus.remove(position);
					notifyDataSetChanged();
					if(mListener != null)
						mListener.checkboxCheck(false);
				}
			});
			check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int position = (Integer) check.getTag();
					if(mListener != null) {
						mListener.checkboxCheck(isChecked);
					}
					if(isChecked)
						mSelected.add(position);
				}
			});
			checkContainer.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					check.performClick();
				}
			});
		}
	}
	
	public List<MenuEntity> getSelectedItem() {
		List<MenuEntity> menus = new ArrayList<MenuEntity>();
		for(int i=0; i<mSelected.size(); i++) {
			menus.add(mMenus.get(mSelected.get(i)));
		}
		return menus;
	}
	
	public void setFragmentListener(FragmentListener listener) {
		this.mListener = listener;
	}
	
	public interface FragmentListener {
		void checkboxCheck(boolean isCheck);
	}

	@Override
	public int getCount() {
		return mMenus.size();
	}

	@Override
	public Object getItem(int position) {
		return mMenus.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void updateList(List<MenuEntity> menus) {
		this.mMenus = menus;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		MenuEntity menu = (MenuEntity) getItem(position);
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.item_addfoot, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.check.setChecked(mSelected.contains(position));
		mImgLoader.get(APIS.BASE_URL + menu.imgPath, ImageLoader.getImageListener(vh.img, R.drawable.img_error, R.drawable.img_error));
		vh.nameTV.setText(menu.packname);
		vh.priceTV.setText(menu.price + "");
		vh.numberTV.setText(menu.soldCnt + "");
		vh.add.setTag(position);
		vh.remove.setTag(position);
		vh.delete.setTag(position);
		vh.check.setTag(position);
		return convertView;
	}

}
