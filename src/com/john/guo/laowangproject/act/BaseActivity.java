package com.john.guo.laowangproject.act;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.httpclient.util.ExceptionUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ListView;

import com.honestwalker.androidutils.UIHandler;
import com.honestwalker.androidutils.EventAction.ActionClick;
import com.honestwalker.androidutils.EventAction.ActionItemClick;
import com.honestwalker.androidutils.EventAction.ActionLongClick;
import com.honestwalker.androidutils.IO.SharedPreferencesLoader;
import com.honestwalker.androidutils.ViewUtils.ViewSizeHelper;
import com.honestwalker.androidutils.equipment.DisplayUtil;
import com.honestwalker.androidutils.pool.ThreadPool;
import com.honestwalker.androidutils.window.DialogHelper;
import com.honestwalker.androidutils.window.ToastHelper;

/**
 * Created by honestwalker on 13-8-8.
 */
public abstract class BaseActivity extends FragmentActivity {
	
	
	//================================
	//
	//            公共控件
	//
	//================================
	
	private View dimShadeView;
	public View getDimShadeView(){ return dimShadeView;}
	
	//================================
	//
	//            公共参数
	//
	//================================
	
	public static DisplayUtil displayUtil;
	public static ViewSizeHelper viewSizeHelper;
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	public static int statusBarHeight = 0;
	protected LayoutInflater inflater;
	protected BaseActivity context;
	
	public static final int REQUEST_CODE_LOGIN_SUCCESS = 1000;
	public static final int REQUEST_CODE_LOGIN_CANCLE = 1001;
	
	
	private int backAnimCode = 0;
	
	public View contentView;
	
	private long onResumeTime = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
//		initLocale();
		init();
//		MainApplication.addSingleInstanceActivity(this);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		
//		MainApplication.lastPage = this.getClass().getSimpleName();
		
		if(getIntent() != null) {
			backAnimCode = getIntent().getIntExtra("backAnimCode", 0);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		onResumeTime = System.currentTimeMillis();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		
		if(System.currentTimeMillis() - onResumeTime < 400) { // 避免连续按后退动画瑕疵
			return;
		}
		
		super.onBackPressed();
		finish();
		if(backAnimCode != 0) {
//			StartActivityHelper.activityAnim(context, backAnimCode);
			backAnimCode = 0;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/** 页面控件初始化 */
	protected abstract void initView();
	
	/** 加载皮肤资源，需要载入皮肤资源的页面重写这个方法 */
	protected void loadTheme(){};

	/**
	 * 初始化公共参数
	 */
	public void init() {
		if (displayUtil == null) {
			displayUtil = DisplayUtil.getInstance(this);
		}
		if (viewSizeHelper == null) {
			viewSizeHelper = ViewSizeHelper.getInstance(this);
		}
		if (screenWidth == 0) {
			screenWidth = DisplayUtil.getInstance(this).getWidth();
			screenHeight = DisplayUtil.getInstance(this).getHeight();
			statusBarHeight = DisplayUtil.getInstance(this).getStatusBarHeight();
		}
		if (inflater == null) {
			inflater = getLayoutInflater();
		}
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
//		initPublicView();
		initView();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
	}
	
	protected Activity getContext() {
		context = this;
		return context;
	}

	/**
	 * 获取缓存路径 ， 末尾已经包含 /
	 */
	public String getCachePath() {
//		return ContextProperties.getConfig().cachePath;
		return "";
	}
	
	/**
	 * 获取sd卡缓存目录
	 * @return
	 */
	public String getSDCachePath() {
//		return SDCardUtil.getSDRootPath() +  ContextProperties.getConfig().sdcartCacheName;
		return "";
	}
	/** 
	 * sd卡缓存图片位置
	 * @return
	 */
	public String getSDImageCachePath() {
		String imageCachePath = getSDCachePath() + "/image/";
		File imageCacheFile = new File(imageCachePath);
		if(!imageCacheFile.exists()){
			imageCacheFile.mkdirs();	//此处记得加对sd卡操作到权限
		}
		return imageCachePath;
	}
	
	public void recyleBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}
	
	/** 获取当前登录的账户 */
//	public User getLoginUser() {
//		try {
//			User user = (User) ObjectStreamIO.input(getCachePath(), ObjectStreamType.LOGIN_USER);
//			return user;
//		} catch (Exception e) {
//		}
//		return null;
//	}
//	/** 判断是否需要登录 */
//	protected boolean needLogin() {
//		return (getLoginUser() == null || StringUtil.isEmptyOrNull(getLoginUser().getSessionkey()));
//	}
	
	
	/** 启动线程 */
	public void threadPool(Runnable runnable) {
		ThreadPool.threadPool(runnable);
	}
	
	/*=================================
	 * 
	 *             Intent 相关操作开始
	 * 
	 *=================================*/
	
	public Intent getIntent() {
		return super.getIntent() == null ? new Intent():super.getIntent();
	}
	
	/** 从intent取得一个Serializable对象 */
	public Object getIntentSerializableExtra(String key){
		Intent intent = getIntent();
		Object value = intent.getSerializableExtra(key);
		return value;
	}
	
	public Object getIntentSerializableExtra(Intent intent , String key){
		Object value = intent.getSerializableExtra(key);
		return value;
	}
	
	/*=================================
	 * 
	 *      Intent 相关操作结束
	 * 
	 *=================================*/
	
	/*=================================
	 * 
	 *             公共控件操作
	 * 
	 *=================================*/
	
	/**
	 * 判断是否登录
	 * @param toLogin 未登录时是否直接跳登录界面
	 * @return
	 */
//	public abstract boolean isLogin(boolean toLogin);
	
//	/** 获取登录用户 */
//	public abstract UserDetail getLoginSuccessUser();
	
	/**
	 * 修改登录状态
	 * @param login
	 */
	public void setLogin(boolean login){
		SharedPreferencesLoader.putBoolean("login", login);
	}
	 
	public void loading(final boolean show) {
		
//		LoadingBuilder.loading(context, show);
		
//		UIHandler.post(new Runnable() {
//			@Override
//			public void run() {
//				if(show) {
//					LoadingHelper.show(context, "loading");
//				} else {
//					LoadingHelper.dismiss(context);
//				}
//			}
//		});
		
//		UIHandler.post(new Runnable() {
//			@SuppressLint("HandlerLeak")
//			@Override
//			public void run() {
//				
//				View contentView = (View) LayoutInflater.from(context).inflate(R.layout.view_pop_loading, null);
//				View loadingView = contentView.findViewById(R.id.view1);
//				ViewSizeHelper.getInstance(context).setWidth(loadingView, screenWidth/4,200,67);
//				AnimationDrawable anim = (AnimationDrawable) context.getResources().getDrawable(R.drawable.frame_loading);
//				loadingView.setBackgroundDrawable(anim);
//				anim.start();
//				LoadingHelper.registerDialog("loading",contentView ,screenWidth , screenHeight,true);
//				
//				if (show) {
//					LoadingHelper.show(BaseActivity.this, "loading" , new Handler(){
//						@Override
//						public void handleMessage(Message msg) {
//							onBackPressed();
//						}
//					});
//				} else {
//					LoadingHelper.dismiss(BaseActivity.this);
//				}
//			}
//		});
		
	}
	
	public OnClickListener backBtnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onBackPressed();
		}
	};

	/*=================================
	 * 
	 *         公共控件操作结束
	 * 
	 *=================================*/

	/*=================================
	 * 
	 *        公共控件点击事件开始
	 * 
	 *=================================*/

	/*=================================
	 * 
	 *       公共控件点击事件结束
	 * 
	 *=================================*/
	
	
	/*================================
	 * 
	 *            控件事件重写开始 
	 * 
	 *================================*/
	
	/** 点击事件code */
	private final int ACTION_CLICK = 1; 
	/** 长按事件code */
	private final int ACTION_LONGCLICK = 2;
	/** 列表点击事件code */
	private final int ACTION_ITEMCLICK = 3; 
	
	/** 设置按钮点击事件 */
	protected void setClick(final View view , final String clickMethod ,final Object... args) {
		setActionListener(view, ACTION_CLICK, clickMethod, args);
	}
	/** 设置按钮点击事件 */
	protected void setClick(final View view , final String clickMethod) {
		setClick(view, clickMethod, new Object[0]);
	}
	
	/** 设置按钮长按事件 */
	protected void setLongClick(final View view , final String clickMethod ,final Object... args) {
		setActionListener(view, ACTION_LONGCLICK, clickMethod, args);
	}
	/** 设置按钮长按事件 */
	protected void setLongClick(final View view , final String clickMethod) {
		setLongClick(view, clickMethod, new Object[0]);
	}
	
	/** 设置按钮长按事件 */
	protected void setItemClick(final View view , final String clickMethod , final Object... args) {
		Object[] newArgs;
		if(args != null && args.length > 0) {
			newArgs = new Object[1 + args.length];
			newArgs[0] = (int)0;
			for(int i=1 ; i < newArgs.length; i++) {
				newArgs[i] = args;
			}
		} else {
			newArgs = new Object[1];
			newArgs[0] = (int)0;
		}
		setActionListener(view, ACTION_ITEMCLICK ,clickMethod, newArgs);
	}
	protected void setItemClick(final View view , final String clickMethod) {
		setItemClick(view, clickMethod, new Object[0]);
	}
	
	/** 设置按钮事件 */
	private void setActionListener(final View view , final int actionCode , final String clickMethod ,final Object... args) {
		if(view != null) {
			view.setClickable(true);
			try {
				// 获取参数列表类型
				Class[] cargs = new Class[args == null?0:args.length];
				for(int i=0;i<cargs.length;i++) {
					cargs[i] = args[i].getClass();
				}
				// 获得方法
			    final Method method = this.getClass().getMethod(clickMethod, cargs);
			    if(method != null) {
			    	switch (actionCode) {
						case ACTION_CLICK: {
							view.setOnClickListener(new ActionClick(this,method,args));
						} break;
						case ACTION_LONGCLICK: {
							view.setOnLongClickListener(new ActionLongClick(this, method, args));
						} break;
						case ACTION_ITEMCLICK: {
							if(view instanceof ListView) {
								((ListView)view).setOnItemClickListener(new ActionItemClick(this, method, args));
							} else if(view instanceof GridView) {
								((GridView)view).setOnItemClickListener(new ActionItemClick(this, method, args));
							}
						} break;
					}
			    }
			} catch (Exception e) {
//				ExceptionUtil.showException(e);
			}
		}
	}
	/*================================
	 * 
	 *            控件事件重写结束
	 * 
	 *================================*/
	
	
	/*================================
	 * 
	 *          业务逻辑开始
	 *
	 *================================*/
	
	
	/** 登录成功回调 */
//	public abstract void loginSuccessCallback(UserDetail userInfoBean);
	/** 登录取消回调 */
//	public abstract void loginCancleCallback();
	
	/*================================
	 * 
	 *          业务逻辑结束
	 *
	 *================================*/
	
	/*================================
	 * 
	 *            页面跳转相关 开始
	 * 
	 *================================ */

//	public void toActivity(Class<? extends Activity> descActivityClass) {
//		StartActivityHelper.toActivity(this, descActivityClass);
//	}
//
//	public void toActivity(Class<? extends Activity> descActivityClass, Intent intent) {
//		StartActivityHelper.toActivity(this, descActivityClass, intent);
//	}
//
//	public void toActivity(Class<? extends Activity> descActivityClass, int animCode) {
//		StartActivityHelper.toActivity(this, descActivityClass, animCode);
//	}
//
//	public void toActivity(Class<? extends Activity> descActivityClass, Bundle data, int animCode) {
//		StartActivityHelper.toActivity(this, descActivityClass, data , animCode);
//	}
//
//	public void toActivity(Class<? extends Activity> descActivityClass, Intent intent, int animCode) {
//		StartActivityHelper.toActivity(this, descActivityClass, intent , animCode);
//	}
//	
//	public void toActivityForResult(Class<? extends Activity> descActivityClass,int requestCode) {
//		StartActivityHelper.toActivityForResult(this, descActivityClass, requestCode);
//	}
//
//	public void toActivityForResult(Class<? extends Activity> descActivityClass, Intent intent ,  int requestCode) {
//		StartActivityHelper.toActivityForResult(this, descActivityClass, intent , requestCode);
//	}
//
//	public void toActivityForResult(Class<? extends Activity> descActivityClass , int requestCode , int animCode) {
//		StartActivityHelper.toActivityForResult(this, descActivityClass, requestCode , animCode);
//	}
//
//	public void toActivityForResult(Class<? extends Activity> descActivityClass, Intent intent, int requestCode , int animCode) {
//		StartActivityHelper.toActivityForResult(this, descActivityClass, intent , requestCode , animCode);
//	}
//	
	/*================================
	 * 
	 *            页面跳转相关 结束
	 * 
	 *================================ */
	
	

	/*================================
	 * 
	 *             对话框相关
	 * 
	 *===============================*/
	public void alertDialog(final String title, final String msg) {
		UIHandler.post(new Runnable() {
			@Override
			public void run() {
				DialogHelper.alert(BaseActivity.this, title, msg);
			}
		});
	}

	public void alertDialog(final String msg) {
		UIHandler.post(new Runnable() {
			@Override
			public void run() {
				DialogHelper.alert(BaseActivity.this, msg);
			}
		});
	}
	
	public void alertToast(final String msg) {
		UIHandler.post(new Runnable() {
			@Override
			public void run() {
				ToastHelper.alert(BaseActivity.this, msg);
			}
		});
	}

	public void alertToast(final String msg, final int time) {
		UIHandler.post(new Runnable() {
			@Override
			public void run() {
				ToastHelper.alert(BaseActivity.this, msg, time);
			}
		});
	}

	/*================================
	 * 
	 *          对话框相关结束
	 *
	 *===============================*/

}
