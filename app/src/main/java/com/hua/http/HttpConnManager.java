package com.hua.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


/**
 * 
 *  网络连接服务类
 *
 */
public class HttpConnManager {
	
        /**
		 * 是否有网络
		 * 
		 * @param context
		 * @return true:有网络 false:无网络
		 */
		public static boolean checkNetWorkStatus(Context context) {
			boolean result;
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netinfo = cm.getActiveNetworkInfo();
			if (netinfo != null && netinfo.isConnected()) {
				result = true;
			} else {
				result = false;
			}
			return result;
		}

	/**
	 * 获取网络MAC地址
	 * @param context
	 * @return
	 */
	public static String getLocalMacAddress(Context context) {  
		try{
			   WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
		        WifiInfo info = wifi.getConnectionInfo();
            if(info.getMacAddress()==null){
                return "";
            }else{
                return info.getMacAddress();
            }

		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
     
    }  
}
