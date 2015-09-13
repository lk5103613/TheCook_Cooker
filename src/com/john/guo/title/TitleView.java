package com.john.guo.title;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView {
	
	/** 标题控件layout */
	private RelativeLayout	titleLayout;
	/** 左边layout */
	private RelativeLayout 	titleLeftLayout;
	/** 左边icon */
	private ImageView 		titleLeftIV;
	/** 左边按钮btn文字 */
	private TextView			titleLeftTV;
	
	/** 右边layout */
	private RelativeLayout 	titleRightLayout;
	/** 右边按钮btn */
	private Button			titleRightBtn;
	/** 标题 */
	private TextView		titleTV;
	/** 搜索layout */
	private RelativeLayout	searchLayout;
	/** 搜索框et */
	private EditText		searchET;
	/** 搜素icon */
	private ImageView		searchIV;
	/** 中间logo图片 */
	private ImageView		titleImageIV;
	public RelativeLayout getTitleLayout() {
		return titleLayout;
	}
	public void setTitleLayout(RelativeLayout titleLayout) {
		this.titleLayout = titleLayout;
	}
	public RelativeLayout getTitleLeftLayout() {
		return titleLeftLayout;
	}
	public void setTitleLeftLayout(RelativeLayout titleLeftLayout) {
		this.titleLeftLayout = titleLeftLayout;
	}
	public ImageView getTitleLeftIV() {
		return titleLeftIV;
	}
	public void setTitleLeftIV(ImageView titleLeftIV) {
		this.titleLeftIV = titleLeftIV;
	}
	public TextView getTitleLeftTV() {
		return titleLeftTV;
	}
	public void setTitleLeftTV(TextView titleLeftTV) {
		this.titleLeftTV = titleLeftTV;
	}
	public RelativeLayout getTitleRightLayout() {
		return titleRightLayout;
	}
	public void setTitleRightLayout(RelativeLayout titleRightLayout) {
		this.titleRightLayout = titleRightLayout;
	}
	public Button getTitleRightBtn() {
		return titleRightBtn;
	}
	public void setTitleRightBtn(Button titleRightBtn) {
		this.titleRightBtn = titleRightBtn;
	}
	public TextView getTitleTV() {
		return titleTV;
	}
	public void setTitleTV(TextView titleTV) {
		this.titleTV = titleTV;
	}
	public RelativeLayout getSearchLayout() {
		return searchLayout;
	}
	public void setSearchLayout(RelativeLayout searchLayout) {
		this.searchLayout = searchLayout;
	}
	public EditText getSearchET() {
		return searchET;
	}
	public void setSearchET(EditText searchET) {
		this.searchET = searchET;
	}
	public ImageView getSearchIV() {
		return searchIV;
	}
	public void setSearchIV(ImageView searchIV) {
		this.searchIV = searchIV;
	}
	public ImageView getTitleImageIV() {
		return titleImageIV;
	}
	public void setTitleImageIV(ImageView titleImageIV) {
		this.titleImageIV = titleImageIV;
	}
	
}
