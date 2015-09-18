package com.john.guo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dcjd.cook.cs.R;

public class DeleteableImageView extends RelativeLayout{
	ImageView image;
	ImageButton delBtn;
	private boolean containImage = false;
	
	public DeleteableImageView(Context context) {
		super(context);
		initView(context);
	}
	public DeleteableImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public DeleteableImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}
	
	private void initView(Context context){
		View view = LayoutInflater.from(context).inflate(R.layout.deleteable_img, this,true);
		image = (ImageView) view.findViewById(R.id.image);
		delBtn = (ImageButton) view.findViewById(R.id.delbtn);
	}
	
	public void setImageBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			setVisibility(View.VISIBLE);
			this.image.setImageBitmap(bitmap);
			this.delBtn.setVisibility(View.VISIBLE);
			this.containImage = true;
		}
	}
	
	public void removeImage(){
		this.image.setImageResource(R.drawable.certificate);
		this.delBtn.setVisibility(View.GONE);
		this.containImage = false;
	}
	
	public boolean isContainImage(){
		return containImage;
	}

}
