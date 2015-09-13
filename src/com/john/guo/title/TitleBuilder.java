package com.john.guo.title;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.StringUtil;
import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.ViewUtils.ViewSizeHelper;
import com.honestwalker.androidutils.equipment.DisplayUtil;

public class TitleBuilder {
	
	private Context context;
	
	private RelativeLayout 	titleLayout;
	private RelativeLayout 	titleLeftLayout;
	private TextView			titleLeftTV;
	private ImageView			titleLeftIV;
	private ImageView			titleRightIV;
	private RelativeLayout 	titleRightLayout;
	private TextView			titleRightTV;
	private TextView			titleTV;
	private ImageView			titleIV;
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	private ViewSizeHelper viewSizeHelper;
	
	public int titleHeight = 0;
	public float titleHeightScale = 0.08f;
	
	public TitleBuilder(Context context) {
		this(context, null);
	}
	
	public TitleBuilder(Fragment fragment , View view , TitleArg titleArg) {
		
		this.context = fragment.getActivity();
		
		viewSizeHelper = ViewSizeHelper.getInstance(fragment.getActivity());
		screenWidth = DisplayUtil.getInstance(fragment.getActivity()).getWidth();
		screenHeight = DisplayUtil.getInstance(fragment.getActivity()).getHeight();

		LogCat.d("TITLE", "screenWidth=" + screenWidth);
		
		initTitleFromFragment(view);
		
		setTitleViewAttr();
		
		if(titleArg != null) {
			setTitle(titleArg);
		}
		
	}
	
	public TitleBuilder(Context context , TitleArg titleArg) {
		this.context = context;
		
		viewSizeHelper = ViewSizeHelper.getInstance(context);
		screenWidth = DisplayUtil.getInstance(context).getWidth();
		screenHeight = DisplayUtil.getInstance(context).getHeight();
		
		initTitle();
		
		setTitleViewAttr();
		
		if(titleArg != null) {
			setTitle(titleArg);
		}
		
	}
	
	public void initTitle() {
		titleLayout  	  = (RelativeLayout) ((Activity)context).findViewById(R.id.title_layout);
		titleTV 	 	 	  = (TextView) ((Activity)context).findViewById(R.id.title_middle_tv);
		titleIV			  = (ImageView) ((Activity)context).findViewById(R.id.title_middle_iv);
		titleLeftLayout  = (RelativeLayout) ((Activity)context).findViewById(R.id.title_left_layout);
		titleLeftTV 	  = (TextView) ((Activity)context).findViewById(R.id.title_left_tv);
		titleLeftIV 	  = (ImageView) ((Activity)context).findViewById(R.id.title_left_iv);
		titleRightLayout = (RelativeLayout) ((Activity)context).findViewById(R.id.title_right_layout);
		titleRightTV 	  = (TextView)  ((Activity)context).findViewById(R.id.title_right_tv);
		titleRightIV 	  = (ImageView) ((Activity)context).findViewById(R.id.title_right_iv);
	}
	
	public void initTitleFromFragment(View fragmentContentView) {
		titleLayout  	  = (RelativeLayout) fragmentContentView.findViewById(R.id.title_layout);
		titleTV 	 	 	  = (TextView) fragmentContentView.findViewById(R.id.title_middle_tv);
		titleIV			  = (ImageView) fragmentContentView.findViewById(R.id.title_middle_iv);
		titleLeftLayout  = (RelativeLayout) fragmentContentView.findViewById(R.id.title_left_layout);
		titleLeftTV 	  = (TextView) fragmentContentView.findViewById(R.id.title_left_tv);
		titleLeftIV 	  = (ImageView) fragmentContentView.findViewById(R.id.title_left_iv);
		titleRightLayout = (RelativeLayout) fragmentContentView.findViewById(R.id.title_right_layout);
		titleRightTV 	  = (TextView)  fragmentContentView.findViewById(R.id.title_right_tv);
		titleRightIV 	  = (ImageView) fragmentContentView.findViewById(R.id.title_right_iv);
		LogCat.d("TITLE", "titleTV=" + titleTV);
	}
	
	/** 设置标题的宽和高 */
	protected void setTitleViewAttr() {
		
		titleHeight = (int) (screenHeight * titleHeightScale);
		
		viewSizeHelper.setWidth(titleTV, screenWidth/3*2);
		
//		viewSizeHelper.setHeight(titleLayout, titleHeight);
		
	}
	
	/**
	 * 标题设置
	 * @param titleArg
	 */
	public void setTitle(TitleArg titleArg) {
		if (!StringUtil.isEmptyOrNull(titleArg.getTitleTVStr())) {
			LogCat.d("TITLE", "设置标题:"  + titleArg.getTitleTVStr());
			titleTV.setText(titleArg.getTitleTVStr());
		}
		if (titleArg.getMiddleIconRes() != 0) {
			titleIV.setImageResource(titleArg.getMiddleIconRes());
		}
		//左布局显示与否
		if (titleArg.isLeftBackVisible()) {
			titleLeftLayout.setVisibility(View.VISIBLE);
			if (titleArg.getLeftIconRes() != 0) {
				titleLeftIV.setImageResource(titleArg.getLeftIconRes());
				viewSizeHelper.setWidth(titleLeftIV, context.getResources().
						getDimensionPixelSize(R.dimen.title_icon_width), 1, 1);
				titleLeftTV.setVisibility(View.GONE);
				((RelativeLayout.LayoutParams)titleLeftIV.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
				viewSizeHelper.setWidth(titleLeftLayout, context.getResources().
						getDimensionPixelSize(R.dimen.title_icon_layout_width));
			}else {
				viewSizeHelper.setWidth(titleLeftIV, context.getResources().
						getDimensionPixelSize(R.dimen.title_icon_back_width), 1, 1);
			}
			if (titleArg.getLeftBackClickListener() != null) {
				titleLeftLayout.setOnClickListener(titleArg.getLeftBackClickListener());
			}
		}else {
			titleLeftLayout.setVisibility(View.GONE);
		}
		//右布局显示与否
		if (titleArg.isRightBtnVisible()) {
			titleRightLayout.setVisibility(View.VISIBLE);
			if (titleArg.getRightBtnClickListener() != null) {
				titleRightLayout.setOnClickListener(titleArg.getRightBtnClickListener());
			}
			if (!StringUtil.isEmptyOrNull(titleArg.getRightBtnStr())) {
				titleRightIV.setVisibility(View.GONE);
				titleRightTV.setVisibility(View.VISIBLE);
				
				Paint paint = new Paint();
				paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 
						context.getResources().getDimensionPixelSize(R.dimen.title_btn_text_size), 
						context.getResources().getDisplayMetrics()));
				titleRightTV.setText(titleArg.getRightBtnStr());
				if (paint.measureText(titleArg.getRightBtnStr()) < DisplayUtil.getInstance(context).dip2px(70)) {
					viewSizeHelper.setWidth(titleRightTV,
							context.getResources().getDimensionPixelSize(R.dimen.title_right_text_min_width));
				}else {
					viewSizeHelper.setWidth(titleRightTV, 
							Math.round(paint.measureText(titleArg.getRightBtnStr())) + 40);
				}
			}else if (titleArg.getRightIconRes() != 0) {
				titleRightIV.setVisibility(View.VISIBLE);
				titleRightTV.setVisibility(View.GONE);
				titleRightIV.setImageResource(titleArg.getRightIconRes());
			}
		}else {
			titleRightLayout.setVisibility(View.GONE);
		}
	}
	
}
