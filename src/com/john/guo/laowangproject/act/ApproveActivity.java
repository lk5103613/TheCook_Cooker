package com.john.guo.laowangproject.act;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.cs.R;
import com.john.guo.network.APIS;
import com.john.guo.network.DataFetcher;
import com.john.guo.util.BitmapUtil;
import com.john.guo.util.UploadUtil;
import com.john.guo.widget.DatePickerWindow;
import com.john.guo.widget.DropdownWindow;
import com.john.guo.widget.RoundImageView;

public class ApproveActivity extends MyBaseActivity{
	
	private final static int REQUEST_TAKE_PHOTO = 0;
	private final static int REQUEST_FROM_FILE = 1;
	private final static int PHOTO_FOR_ICON = 0;
	private final static int PHOTO_FOR_CER = 1;
	
	private PopupWindow mProvienceDropdown, mCityDropdown, mDistrictDropdown;
	private DatePickerWindow mDatePicker;
	private View mProvienceTarget, mCityTarget, mDistrictTarget;
	private TextView mProvienceShow, mCityShow, mDistrictShow;
	private RoundImageView mUserIcon;
	private ViewGroup mCerContainer;
	private String mImgPath ="";
	private List<String> mCertificates = new ArrayList<String>();
	
	private Dialog mDialog;
	private int mPhotoFor = -1;
	
	private List<String> mGoodAts = new ArrayList<String>();
	private DataFetcher mDataFetcher;
	private EditText mName;
	private EditText mDel;
	private TextView mProvice, mCity, mDistrict;
	private EditText mIntroduction;
	private TextView mXc, mCC, mBBC, mYC;
	private TextView mWorkFromday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approve);
		mDataFetcher = DataFetcher.getInstance(this);
		
		mProvienceTarget = findViewById(R.id.provience_target);
		mCityTarget = findViewById(R.id.city_target);
		mDistrictTarget = findViewById(R.id.district_target);
		mUserIcon = (RoundImageView) findViewById(R.id.user_icon);
		mCerContainer = (ViewGroup) findViewById(R.id.cer_container);
		mName = (EditText) findViewById(R.id.name);
		mDel = (EditText) findViewById(R.id.tel);
		mProvice = (TextView) findViewById(R.id.provice_show);
		mCity = (TextView) findViewById(R.id.city_show);
		mDistrict = (TextView) findViewById(R.id.district_show);
		mIntroduction = (EditText) findViewById(R.id.introduction);
		mXc = (TextView) findViewById(R.id.xc);
		mCC = (TextView) findViewById(R.id.cc);
		mBBC = (TextView) findViewById(R.id.bbc);
		mYC = (TextView) findViewById(R.id.yc);
		mWorkFromday = (TextView) findViewById(R.id.date_picker);
		
	}
	
	public void openDrop(View v) {
		switch (v.getId()) {
		case R.id.provience:
			showPopup(mProvienceDropdown, mProvienceTarget, mProvienceShow, "省1", "省2", "省3");
			break;
		case R.id.city:
			showPopup(mCityDropdown, mCityTarget, mCityShow, "市1", "市2", "市3");
			break;
		case R.id.district:
			showPopup(mDistrictDropdown, mDistrictTarget, mDistrictShow, "区1", "区2", "区3");
			break;
		case R.id.date_picker:
			showDatePop(mDatePicker, v);
			break;
		
		default:
			break;
		}
	}
	
	public void update(View v) throws UnsupportedEncodingException {
		List<String> params = new ArrayList<String>();
		params.add(1+"");//cid
		params.add(URLEncoder.encode(mName.getText().toString(), "utf-8"));//name
		params.add(URLEncoder.encode(mProvice.getText().toString(), "utf-8"));//province
		params.add(URLEncoder.encode(mCity.getText().toString(), "utf-8"));//city
		params.add(URLEncoder.encode(mDistrict.getText().toString(), "utf-8"));//district
		params.add(URLEncoder.encode(mIntroduction.getText().toString(), "utf-8"));//introduction
		String cxStyle = (mXc.isSelected()? mXc.getText().toString():"") +( mCC.isSelected()?","+mCC.getText().toString():"")
				+ (mBBC.isSelected()?","+mBBC.getText().toString():"") +(mYC.isSelected()?","+mYC.getText().toString():"");
		params.add(URLEncoder.encode(cxStyle, "utf-8"));//caixi_style
		params.addAll(mCertificates);//certificate
		params.add(mWorkFromday.getText().toString());//work_fromday
		params.add(mImgPath);//avatar
		
		mDataFetcher.fetchApprove( params, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				
				System.out.println("response " + response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
				
			}
		});
	}

	public void uploadCer(View v) {
		mPhotoFor = PHOTO_FOR_CER;
		showDialog();
	}
	
	private void showPopup(PopupWindow popupWindow, View targetView, TextView showView, String...strings) {
		if(popupWindow == null) {
			popupWindow = new DropdownWindow(mContext, showView, targetView, strings);
		}
		popupWindow.showAsDropDown(targetView, -200, 50);
	}
	
	private void showDatePop(DatePickerWindow datePickerWindow, View targetView) {
		if(mDatePicker == null) {
			mDatePicker = new DatePickerWindow(mContext, 300, 350, (TextView)targetView);
		}
		
		mDatePicker.showAsDropDown(targetView);
	}
	
	public void changeUserIcon(View v) {
		mPhotoFor = PHOTO_FOR_ICON;
		showDialog();
	}
	
	private void showDialog() {
		if(mDialog == null) {
			mDialog = new Dialog(mContext, R.style.Theme_dialog);
			View view = LayoutInflater.from(mContext).inflate(R.layout.choice_photo_dialog, null);
			Button btnCamera = (Button)view.findViewById(R.id.take_from_camert);
			btnCamera.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					startActivityForResult(intent, REQUEST_TAKE_PHOTO);
				}
			});
			Button btnGalley = (Button) view.findViewById(R.id.take_from_file);
			btnGalley.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, REQUEST_FROM_FILE);
				}
			});
			mDialog.setContentView(view);
		}
		mDialog.show();
	}
	
	public void checkGoodAt(View v) {
		TextView view = (TextView) v;
		if(view.isSelected())
			mGoodAts.remove(view.getText().toString());
		else
			mGoodAts.add(view.getText().toString());
		if(view.isSelected())
			view.setSelected(false);
		else
			view.setSelected(true);
	}
	
	
	private void addCer(Bitmap bmp) {
		if(mCertificates.size() >= 3) {
			return;
		} else {
			((ImageView)mCerContainer.getChildAt(mCertificates.size())).setVisibility(View.VISIBLE);
			((ImageView)mCerContainer.getChildAt(mCertificates.size())).setImageBitmap(bmp);
		}
//		mCerContainer.addView(img);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_FROM_FILE) {
			if (data != null) {
				Uri uri = data.getData();
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), uri);
					final File file = BitmapUtil.getFileByUri(getContentResolver(), uri);
					Bitmap resizeBmp = BitmapUtil.getResizeBitmap(file);
					if(mPhotoFor == PHOTO_FOR_ICON) {
						mUserIcon.setImageBitmap(resizeBmp);
					} else {
						addCer(resizeBmp);
					}
					if (data.getData() != null) { 
			            uri = data.getData();
			        } 
			        else { 
			            uri  = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));      
			        } 
					new AsyncTask<File, Void, String>() {
						@Override
						protected String doInBackground(File... params) {
							File uploadFile = params[0];
							DateFormat f2 = new SimpleDateFormat("yyyyMMddHHmmss");
							String day = f2.format(new Date());
							int max = 10000;
							int min = 99999;
							Random random = new Random();
							int s = random.nextInt(max) % (max - min + 1) + min;
							final String serverImgName = day + s;
							try {
								UploadUtil.post(uploadFile, APIS.UPLOAD,
										serverImgName);
								mImgPath = "upload/" + serverImgName;
							} catch(Exception e) {
								e.printStackTrace();
							}
							return null;
						}
					}.execute(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if(requestCode == REQUEST_TAKE_PHOTO) {
			if (data != null) {
				Uri uri;
				Bitmap bitmap = null;
				try {
					Bundle extras = data.getExtras();
				    bitmap = (Bitmap) extras.get("data");
					if (data.getData() != null) { 
			            uri = data.getData();
			        } 
			        else { 
			            uri  = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));      
			        } 
					final File file = BitmapUtil.getFileByUri(getContentResolver(), uri);
					Bitmap resizeBmp = BitmapUtil.getResizeBitmap(file);
					if(mPhotoFor == PHOTO_FOR_ICON) {
						mUserIcon.setImageBitmap(resizeBmp);
					} else {
						addCer(resizeBmp);
					}
					new AsyncTask<File, Void, String>() {
						@Override
						protected String doInBackground(File... params) {
							File uploadFile = params[0];
							DateFormat f2 = new SimpleDateFormat("yyyyMMddHHmmss");
							String day = f2.format(new Date());
							int max = 10000;
							int min = 99999;
							Random random = new Random();
							int s = random.nextInt(max) % (max - min + 1) + min;
							final String serverImgName = day + s;
							try {
								UploadUtil.post(uploadFile, APIS.UPLOAD,
										serverImgName);
								if(mPhotoFor == PHOTO_FOR_ICON)
									mImgPath = "upload/" + serverImgName;
								else if(mPhotoFor == PHOTO_FOR_CER) {
									if(mCertificates.size() <= 3) {
										mCertificates.add("upload/" + serverImgName);
									}
								}
							} catch(Exception e) {
								e.printStackTrace();
							}
							return null;
						}
					}.execute(file);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
