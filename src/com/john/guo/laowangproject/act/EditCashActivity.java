package com.john.guo.laowangproject.act;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dcjd.cook.cs.R;
import com.john.guo.entity.CommonResult;
import com.john.guo.entity.LoginResult;
import com.john.guo.network.GsonUtil;

public class EditCashActivity extends MyBaseActivity {

	private TextView mTitle;
	private TextView mAfterChange;
	private EditText mDifEdit;
	private TextView mTotalMoney;
	
	private float oldMoney;
	private float money;
	private Context mContext;
	private int flag=1;
	private String orderId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcash);
		mContext = this;
		
		mTitle = (TextView) findViewById(R.id.title_middle_tv);
		mAfterChange = (TextView) findViewById(R.id.after_change);
		mDifEdit = (EditText) findViewById(R.id.dif_edit);
		mTotalMoney = (TextView) findViewById(R.id.total_money);
		
		mTitle.setText("修改金额");
		
		Bundle params = getIntent().getExtras();
		orderId = params.getString("order_id");
		oldMoney = params.getFloat("total_money");
		mTotalMoney.setText(oldMoney+"");
		

	}

	public void change(View v) {
		String moneyStr = mDifEdit.getText().toString();
		if (TextUtils.isEmpty(moneyStr)) {
			Toast.makeText(mContext, "请输入金额", Toast.LENGTH_LONG).show();
			return;
		}
		
		switch (v.getId()) {
		case R.id.plus_btn:
			flag = 1;
			break;
		case R.id.minus_btn:
			flag = 2;
			break;

		default:
			break;
		}
		
		money = Integer.parseInt(mDifEdit.getText().toString());
		float newMoney =  (float) (oldMoney + Math.pow(-1, flag+1) * money);
		mAfterChange.setText(newMoney+"");
	}
	
	public void commit(View v){
		
		LoginResult loginResult = getLoginUser();
		mDataFetcher.fetchUpdateFigure(orderId, loginResult.cid, oldMoney+"", money+"", flag+"",new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
				if(result.code == 1){
					Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
				}
				
			}

		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

}
