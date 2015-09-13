package com.john.guo.laowangproject.act;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcjd.cook.cs.R;
import com.honestwalker.androidutils.views.AsyncCircleImageView;

public class WsdcActivity extends Fragment {
	
	private AsyncCircleImageView avatarIV;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {
	 
		View view = inflater.inflate(R.layout.menu_frame, container, false); 
		
		avatarIV = (AsyncCircleImageView) view.findViewById(R.id.imageview1);
		avatarIV.getImageView().setBorderColor(Color.WHITE);
		avatarIV.getImageView().setBorderWidth(4);
		avatarIV.loadUrl("https://www.baidu.com/img/bd_logo1.png");
		
		return view;
	}

}
