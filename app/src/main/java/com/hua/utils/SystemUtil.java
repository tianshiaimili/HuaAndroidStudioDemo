package com.hua.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @Title SystemUtil
 * @Package cn.mama.util
 * @Description 获取系统的一些东东
 * @author hejinxi
 * @date 2013-9-12 
 * @version V1.0
 */

public class SystemUtil {

	
	/**
	 * 关闭键盘
	 */
	public static void KeyBoardCancle(Activity activity) {
		if(activity==null){
			return;
		}
		View view = activity.getWindow().peekDecorView();
		if (view != null) {

			InputMethodManager inputmanger = (InputMethodManager) activity
					.getSystemService(activity.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 开启键盘
	 */
	public static void KeyBoardOpen(Activity activity, View view) {

		InputMethodManager inputmanger = (InputMethodManager) activity
				.getSystemService(activity.INPUT_METHOD_SERVICE);
		inputmanger.showSoftInput(view, 0);
	}
	
 
	/**
	 * 隐藏键盘
	 * @param activity
	 */
	public static void KeyBoardHiddent(Activity activity){
		if(activity==null){
			return;
		}
		InputMethodManager manager = ((InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE));
		if(manager!=null){
		View view= 	activity.getCurrentFocus();
		  if(view!=null){
			  manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		  }
			
		}
	
	}
	

	/**
	 * 获取渠道号
	 * @param context
	 * @return
	 */
	public static String getChannelId(Context context){
		if(context==null){
			return "channel";
		}
		PackageManager pm = context.getPackageManager();
		ApplicationInfo appinfo;
		String platform_id = "";
		try {
			appinfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			Bundle metaData = appinfo.metaData;
			platform_id = metaData.getString("UMENG_CHANNEL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return platform_id;
	}
	


}
