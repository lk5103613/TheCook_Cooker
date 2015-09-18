package com.john.guo.laowangproject.act;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dcjd.cook.cs.R;
import com.google.gson.reflect.TypeToken;
import com.john.guo.adapter.CityPopAdapter;
import com.john.guo.adapter.DistrictsPopAdapter;
import com.john.guo.adapter.ProviencePopAdapter;
import com.john.guo.adapter.SimpleAdapter;
import com.john.guo.entity.BaseResult;
import com.john.guo.entity.City;
import com.john.guo.entity.Districts;
import com.john.guo.entity.Provience;
import com.john.guo.network.APIS;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;
import com.john.guo.util.BitmapUtil;
import com.john.guo.util.LocationUtil;
import com.john.guo.util.UploadUtil;
import com.john.guo.util.ValidateUtil;
import com.john.guo.widget.DatePickerWindow;
import com.john.guo.widget.DeleteableImageView;
import com.john.guo.widget.DeleteableImageView.ImageActionListener;
import com.john.guo.widget.DropdownWindow;
import com.john.guo.widget.DropdownWindow.OnPopChangeListener;
import com.john.guo.widget.RoundImageView;
import com.like.storage.CityManager;
import com.like.storage.DistrictsManager;
import com.like.storage.ProvienceManager;

public class ApproveActivity extends MyBaseActivity implements
		ImageActionListener {

	private final static int REQUEST_TAKE_PHOTO = 0;
	private final static int REQUEST_FROM_FILE = 1;
	private final static int PHOTO_FOR_ICON = 0;
	private final static int PHOTO_FOR_CER = 1;

	private DropdownWindow<City> mCityDropdown;
	private DropdownWindow<Districts> mDistrictDropdown;
	private DropdownWindow<Provience> mProvienceDropdown;

	private DatePickerWindow mDatePicker;
	private View mProvienceTarget, mCityTarget, mDistrictTarget;
	private RoundImageView mUserIcon;
	private ViewGroup mCerContainer;
	private String mImgPath = "";
	private List<String> mCertificates = new ArrayList<String>();

	private Dialog mDialog;
	private int mPhotoFor = -1;

	private List<String> mGoodAts = new ArrayList<String>();
	private DataFetcher mDataFetcher;
	private EditText mName;
	private EditText mTel;
	private TextView mProvice, mCity, mDistrict;
	private EditText mIntroduction;
	private TextView mXc, mCC, mBBC, mYC;
	private TextView mWorkFromday;
	private LinearLayout mContainer;
	private DeleteableImageView mImage1;
	private DeleteableImageView mImage2;
	private DeleteableImageView mImage3;
	private DeleteableImageView mImage4;

	private List<Districts> mDistricts = new ArrayList<Districts>();
	private List<City> mCities = new ArrayList<City>();
	private List<Provience> mProviences = new ArrayList<Provience>();

	private CityPopAdapter mCityPopAdapter;
	private DistrictsPopAdapter mDistrictsPopAdapter;
	private ProviencePopAdapter mProviencePopAdapter;

	private CityManager mCityManager;
	private DistrictsManager mDistrictsManager;
	private ProvienceManager mProvienceManager;

	private int mProvienceId;
	private int mCityId;

	private int mCurrentImg;
	private DeleteableImageView[] images;

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
		mTel = (EditText) findViewById(R.id.tel);
		mProvice = (TextView) findViewById(R.id.provice_show);
		mCity = (TextView) findViewById(R.id.city_show);
		mDistrict = (TextView) findViewById(R.id.district_show);
		mIntroduction = (EditText) findViewById(R.id.introduction);
		mXc = (TextView) findViewById(R.id.xc);
		mCC = (TextView) findViewById(R.id.cc);
		mBBC = (TextView) findViewById(R.id.bbc);
		mYC = (TextView) findViewById(R.id.yc);
		mWorkFromday = (TextView) findViewById(R.id.date_picker);
		mContainer = (LinearLayout) findViewById(R.id.approve_container);
		mImage1 = (DeleteableImageView) findViewById(R.id.image1);
		mImage2 = (DeleteableImageView) findViewById(R.id.image2);
		mImage3 = (DeleteableImageView) findViewById(R.id.image3);
		mImage4 = (DeleteableImageView) findViewById(R.id.image4);

		mImage1.setImageActionListenr(this);
		mImage2.setImageActionListenr(this);
		mImage3.setImageActionListenr(this);
		mImage4.setImageActionListenr(this);

		images = new DeleteableImageView[] { mImage1, mImage2, mImage3, mImage4 };

		// 隐藏键盘
		mContainer.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				v.requestFocus();
				// 隐藏键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mName.getWindowToken(), 0);
				return false;
			}
		});

		mCityManager = CityManager.getInstance(this);
		mDistrictsManager = DistrictsManager.getInstance(this);
		mProvienceManager = ProvienceManager.getInstance(this);

		initLocationDB();

		mCityPopAdapter = new CityPopAdapter(this, mCities);
		mDistrictsPopAdapter = new DistrictsPopAdapter(this, mDistricts);
		mProviencePopAdapter = new ProviencePopAdapter(this, mProviences);

	}

	public void openDrop(View v) {
		switch (v.getId()) {
		case R.id.provience:
			getProvience();
			showPopup(mProvienceDropdown, mProvienceTarget, mProvice,
					mProviencePopAdapter);
			mCity.setText("市");
			mDistrict.setText("区");
			break;
		case R.id.city:
			if (mProvice.getText().toString().equals("省")) {
				Toast.makeText(mContext, "请选择省", Toast.LENGTH_SHORT).show();
				return;
			}

			getCity(mProvienceId);
			showPopup(mCityDropdown, mCityTarget, mCity, mCityPopAdapter);
			break;
		case R.id.district:
			if (mCity.getText().toString().equals("市")) {
				Toast.makeText(mContext, "请选择市", Toast.LENGTH_SHORT).show();
				return;
			}
			getDistricts(mCityId);
			if (!mDistricts.isEmpty()) {
				showPopup(mDistrictDropdown, mDistrictTarget, mDistrict,
						mDistrictsPopAdapter);
			}
			
			break;
		case R.id.date_picker:
			showDatePop(mDatePicker, v);
			break;

		default:
			break;
		}
	}

	public void update(View v) throws UnsupportedEncodingException {
		if (!verify()) {
			return;
		}
		List<String> params = new ArrayList<String>();
		params.add(1 + "");// cid
		params.add(URLEncoder.encode(mName.getText().toString(), "utf-8"));// name
		params.add(URLEncoder.encode(mProvice.getText().toString(), "utf-8"));// province
		params.add(URLEncoder.encode(mCity.getText().toString(), "utf-8"));// city
		params.add(URLEncoder.encode(mDistrict.getText().toString(), "utf-8"));// district
		params.add(URLEncoder.encode(mIntroduction.getText().toString(),
				"utf-8"));// introduction
		String cxStyle = (mXc.isSelected() ? mXc.getText().toString() : "")
				+ (mCC.isSelected() ? "," + mCC.getText().toString() : "")
				+ (mBBC.isSelected() ? "," + mBBC.getText().toString() : "")
				+ (mYC.isSelected() ? "," + mYC.getText().toString() : "");
		params.add(URLEncoder.encode(cxStyle, "utf-8"));// caixi_style
		params.addAll(mCertificates);// certificate
		params.add(mWorkFromday.getText().toString());// work_fromday
		params.add(mImgPath);// avatar

		mDataFetcher.fetchApprove(params, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("response " + response);
				BaseResult result = GsonUtil.gson.fromJson(response.toString(), BaseResult.class);
				if (result.code ==1) {
					Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();

			}
		});
	}

	private <T> void showPopup(DropdownWindow<T> popupWindow, View targetView,
			TextView showView, SimpleAdapter<T> adapter) {
		if (popupWindow == null) {
			popupWindow = new DropdownWindow<T>(mContext, targetView, showView,
					adapter);
			popupWindow.setOnChangeListener(new MyOnChangeListener());
		}
		popupWindow.showAsDropDown(targetView, -200, 50);
	}

	private void showDatePop(DatePickerWindow datePickerWindow, View targetView) {
		if (mDatePicker == null) {
			mDatePicker = new DatePickerWindow(mContext, 400, 350,
					(TextView) targetView);
		}
		mDatePicker.showAsDropDown(targetView);
	}

	public void changeUserIcon(View v) {
		mPhotoFor = PHOTO_FOR_ICON;
		showDialog();
	}

	private void showDialog() {
		if (mDialog == null) {
			mDialog = new Dialog(mContext, R.style.Theme_dialog);
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.choice_photo_dialog, null);
			Button btnCamera = (Button) view
					.findViewById(R.id.take_from_camert);
			btnCamera.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
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
		if (view.isSelected())
			mGoodAts.remove(view.getText().toString());
		else
			mGoodAts.add(view.getText().toString());
		if (view.isSelected())
			view.setSelected(false);
		else
			view.setSelected(true);
	}

	private void addCer(Bitmap bmp) {
		images[mCurrentImg].setImageBitmap(bmp);
		if (mCurrentImg < 3) {
			images[++mCurrentImg].setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == REQUEST_FROM_FILE) {
			System.out.println("from file " + data);
			if (data != null) {
				Uri uri = data.getData();
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), uri);
					final File file = BitmapUtil.getFileByUri(
							getContentResolver(), uri);
					Bitmap resizeBmp = BitmapUtil.getResizeBitmap(file);
					
					if (data.getData() != null) {
						uri = data.getData();
					} else {
						uri = Uri.parse(MediaStore.Images.Media.insertImage(
								getContentResolver(), bitmap, null, null));
					}
					
					new UploadTask(resizeBmp).execute(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (requestCode == REQUEST_TAKE_PHOTO) {
			if (data != null) {
				Uri uri;
				Bitmap bitmap = null;
				try {
					Bundle extras = data.getExtras();
					bitmap = (Bitmap) extras.get("data");
					if (data.getData() != null) {
						uri = data.getData();
					} else {
						uri = Uri.parse(MediaStore.Images.Media.insertImage(
								getContentResolver(), bitmap, null, null));
					}
					final File file = BitmapUtil.getFileByUri(
							getContentResolver(), uri);
					Bitmap resizeBmp = BitmapUtil.getResizeBitmap(file);
					new UploadTask(resizeBmp).execute(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void getCity(int provienceId) {
		mCities.clear();
		mCities.addAll(mCityManager.getCityByProId(provienceId));
	}

	private void getProvience() {
		mProviences.clear();
		mProviences.addAll(mProvienceManager.getAll());
	}

	private void getDistricts(int cityId) {
		mDistricts.clear();
		mDistricts.addAll(mDistrictsManager.getDistrictsByCityId(cityId));
	}

	private List<Provience> getAllPro() {
		Type type = new TypeToken<List<Provience>>() {
		}.getType();
		List<Provience> pros = GsonUtil.gson.fromJson(
				new LocationUtil().provienceJson, type);
		return pros;
	}

	private List<City> getAllCity() {
		Type type = new TypeToken<List<City>>() {
		}.getType();
		List<City> cities = GsonUtil.gson.fromJson(new LocationUtil().cityJson,
				type);
		return cities;
	}

	private List<Districts> getAllDistrict() {
		Type type = new TypeToken<List<Districts>>() {
		}.getType();
		List<Districts> districts = GsonUtil.gson.fromJson(
				new LocationUtil().districtJson, type);
		return districts;
	}

	private void initLocationDB() {
		List<Provience> pros = mProvienceManager.getAll();
		if (pros != null && pros.size() > 0)
			return;
		mProvienceManager.addProvience(getAllPro());
		mCityManager.addCity(getAllCity());
		mDistrictsManager.addDistrict(getAllDistrict());
	}

	class MyOnChangeListener implements OnPopChangeListener {

		@Override
		public void onChange(View showView, int position) {
			switch (showView.getId()) {
			case R.id.provice_show:
				mProvienceId = mProviences.get(position).id;
				break;
			case R.id.city_show:
				mCityId = mCities.get(position).id;
				break;
			case R.id.district_show:

				break;

			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image1:
			mCurrentImg = 0;
			break;
		case R.id.image2:
			mCurrentImg = 1;
			break;
		case R.id.image3:
			mCurrentImg = 2;
			break;
		case R.id.image4:
			mCurrentImg = 3;
			break;

		default:
			break;
		}

		mPhotoFor = PHOTO_FOR_CER;
		showDialog();

	}

	@Override
	public void onDelete(View v) {
		((DeleteableImageView) v).removeImage();
		switch (v.getId()) {
		case R.id.image1:
			mCertificates.remove(0);
			break;
		case R.id.image2:
			mCertificates.remove(1);
			break;
		case R.id.image3:
			mCertificates.remove(2);
			break;
		case R.id.image4:
			mCertificates.remove(3);
			break;

		default:
			break;
		}

	}
	
	private class UploadTask extends AsyncTask<File, Void, String>{
			private Bitmap mBitmap;
		
			private UploadTask(Bitmap bitmap){
				this.mBitmap = bitmap;
				showLoading(true);
			}
			@Override
			protected String doInBackground(File... params) {
				
				File uploadFile = params[0];
				DateFormat f2 = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				String day = f2.format(new Date());
				int max = 10000;
				int min = 99999;
				Random random = new Random();
				int s = random.nextInt(max) % (max - min + 1) + min;
				final String serverImgName = day + s;
				try {
					UploadUtil.post(uploadFile, APIS.UPLOAD,
							serverImgName);
					if (mPhotoFor == PHOTO_FOR_ICON)
						mImgPath = "upload/" + serverImgName;
					else if (mPhotoFor == PHOTO_FOR_CER) {
							mCertificates.add(mCurrentImg,"upload/"
									+ serverImgName);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "0";
				}
				return "1";
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (TextUtils.equals(result, "1")) {
					if (mPhotoFor == PHOTO_FOR_ICON) {
						mUserIcon.setImageBitmap(mBitmap);
					} else {
						addCer(mBitmap);
					}
				}
				showLoading(false);
			}
	}
	
	private boolean verify(){
		if (TextUtils.isEmpty(mName.getText().toString()) || 
				TextUtils.isEmpty(mTel.getText().toString())) {
			Toast.makeText(mContext, "请输入必填项", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if (!ValidateUtil.validatePhoneNum(mTel.getText().toString())) {
			Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	

}
