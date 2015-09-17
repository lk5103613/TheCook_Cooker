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
import com.google.gson.reflect.TypeToken;
import com.john.guo.adapter.CityPopAdapter;
import com.john.guo.adapter.DistrictsPopAdapter;
import com.john.guo.adapter.ProviencePopAdapter;
import com.john.guo.adapter.SimpleAdapter;
import com.john.guo.entity.City;
import com.john.guo.entity.Districts;
import com.john.guo.entity.Provience;
import com.john.guo.network.APIS;
import com.john.guo.network.DataFetcher;
import com.john.guo.network.GsonUtil;
import com.john.guo.util.BitmapUtil;
import com.john.guo.util.LocationUtil;
import com.john.guo.util.UploadUtil;
import com.john.guo.widget.DatePickerWindow;
import com.john.guo.widget.DropdownWindow;
import com.john.guo.widget.RoundImageView;
import com.john.guo.widget.DropdownWindow.OnPopChangeListener;
import com.like.storage.CityManager;
import com.like.storage.DistrictsManager;
import com.like.storage.ProvienceManager;

public class ApproveActivity extends MyBaseActivity {

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
	private EditText mDel;
	private TextView mProvice, mCity, mDistrict;
	private EditText mIntroduction;
	private TextView mXc, mCC, mBBC, mYC;
	private TextView mWorkFromday;
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
			showPopup(mDistrictDropdown, mDistrictTarget, mDistrict,
					mDistrictsPopAdapter);
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
			mDatePicker = new DatePickerWindow(mContext, 300, 350,
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
		if (mCertificates.size() >= 3) {
			return;
		} else {
			((ImageView) mCerContainer.getChildAt(mCertificates.size()))
					.setVisibility(View.VISIBLE);
			((ImageView) mCerContainer.getChildAt(mCertificates.size()))
					.setImageBitmap(bmp);
		}
		// mCerContainer.addView(img);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_FROM_FILE) {
			if (data != null) {
				Uri uri = data.getData();
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), uri);
					final File file = BitmapUtil.getFileByUri(
							getContentResolver(), uri);
					Bitmap resizeBmp = BitmapUtil.getResizeBitmap(file);
					if (mPhotoFor == PHOTO_FOR_ICON) {
						mUserIcon.setImageBitmap(resizeBmp);
					} else {
						addCer(resizeBmp);
					}
					if (data.getData() != null) {
						uri = data.getData();
					} else {
						uri = Uri.parse(MediaStore.Images.Media.insertImage(
								getContentResolver(), bitmap, null, null));
					}
					new AsyncTask<File, Void, String>() {
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
								mImgPath = "upload/" + serverImgName;
							} catch (Exception e) {
								e.printStackTrace();
							}
							return null;
						}
					}.execute(file);
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
					if (mPhotoFor == PHOTO_FOR_ICON) {
						mUserIcon.setImageBitmap(resizeBmp);
					} else {
						addCer(resizeBmp);
					}
					new AsyncTask<File, Void, String>() {
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
									if (mCertificates.size() <= 3) {
										mCertificates.add("upload/"
												+ serverImgName);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return null;
						}
					}.execute(file);
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
		System.out.println("province " + mProviences.size());
	}

	private void getDistricts(int cityId) {
		System.out.println("cityid " + cityId);
		mDistricts.clear();
		mDistricts.addAll(mDistrictsManager.getDistrictsByCityId(cityId));
		System.out.println("districts " + mDistricts.size());
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

}
