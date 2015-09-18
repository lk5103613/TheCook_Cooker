package com.john.guo.widget;

import java.io.IOException;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dcjd.cook.cs.R;

public class DeleteableImageView extends RelativeLayout implements
		OnClickListener {
	ImageView image;
	ImageButton delBtn;
	private boolean containImage = false;
	private ImageActionListener mListener;
	private LayoutInflater mInflater;
	private Context mContext;
	private DeleteableImageView mContainer;
	private Bitmap mBitmap;

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

	private void initView(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(
				R.layout.deleteable_img, this, true);
		image = (ImageView) view.findViewById(R.id.image);
		delBtn = (ImageButton) view.findViewById(R.id.delbtn);

		delBtn.setOnClickListener(this);
		image.setOnClickListener(this);
		mContainer = this;
	}

	public void setImageBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			mBitmap = bitmap;
			setVisibility(View.VISIBLE);
			this.image.setImageBitmap(bitmap);
			this.delBtn.setVisibility(View.VISIBLE);
			this.containImage = true;
		}
	}

	public void removeImage() {
		this.image.setImageResource(R.drawable.certificate);
		this.delBtn.setVisibility(View.GONE);
		this.containImage = false;
		this.mBitmap = null;
	}

	public boolean isContainImage() {
		return containImage;
	}

	
	public void setImageActionListenr(ImageActionListener listener) {
		this.mListener = listener;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image:
			
			if (containImage) {
				imageDialog(null);
			} else {
				mListener.onClick(mContainer);
			}
			break;
		case R.id.delbtn:
			mListener.onDelete(mContainer);
			break;

		default:
			break;
		}

	}
	
	public interface ImageActionListener {
		public void onClick(View v);
		public void onDelete(View v);
	}
	
	 public void imageDialog(Bitmap bitmap){
		 Dialog dialog = new Dialog(mContext,R.style.Transparent);
		  View view=mInflater.inflate(R.layout.dialog_image, null);
		  ImageView image=(ImageView)view.findViewById(R.id.detailimg);
//		  Bitmap bmp=Compress.revitionImageSize(path, 800);
		  image.setImageBitmap(mBitmap);
		  dialog.setContentView(view);
		  dialog.show();
	 }
	 
	 public Bitmap getBitmap(){
		 return mBitmap;
	 }

}
