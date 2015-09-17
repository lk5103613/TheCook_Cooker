package com.john.guo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

public class DateUtil {

	@SuppressLint("SimpleDateFormat")
	public static int getYear() {
		long time = System.currentTimeMillis();// long now =
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date date = new Date(time);
		String year = format.format(date);
		return Integer.parseInt(year);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static int getMonth() {
		long time = System.currentTimeMillis();// long now =
		SimpleDateFormat format = new SimpleDateFormat("MM");
		Date date = new Date(time);
		String year = format.format(date);
		return Integer.parseInt(year);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static int getDay() {
		long time = System.currentTimeMillis();// long now =
		SimpleDateFormat format = new SimpleDateFormat("DD");
		Date date = new Date(time);
		String year = format.format(date);
		return Integer.parseInt(year);
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public static int calDayByYearAndMonth(String dyear,String dmouth){
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
		    Calendar rightNow = Calendar.getInstance();
		    try{
		    rightNow.setTime(simpleDate.parse(dyear+"/"+dmouth));
		    }catch(ParseException e){
		    e.printStackTrace();
		    }
		    return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数
	}
	
	public static String formatTime(String timeStr){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
		try {
			Date date = sdf.parse(timeStr);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
			String str = format.format(date);
			return str;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	

}
