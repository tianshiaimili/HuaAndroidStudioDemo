/*
 * @(#)ApplicationUtil.java		       Project:com.sinaapp.msdxblog.androidkit
 * Date:2012-9-13
 *
 * Copyright (c) 2011 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hua.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 应用工具类。
 * 
 */
public class ApplicationUtil {

    private Context context;
    static ApplicationUtil applicationUtil;
    private String version;
    private ApplicationUtil(Context context){

        this.context = context;
    }

    public static ApplicationUtil getInstance(Context context) {
        if (applicationUtil == null) {
            applicationUtil = new ApplicationUtil(context);
        }
        return applicationUtil;
    }

	/**
	 * 通过包名获取应用程序的名称。
	 * 
	 * @param context
	 *            Context对象。
	 * @param packageName
	 *            包名。
	 * @return 返回包名所对应的应用程序的名称。
	 */
	public static String getProgramNameByPackageName(Context context,
			String packageName) {
		PackageManager pm = context.getPackageManager();
		String name = null;
		try {
			name = pm.getApplicationLabel(
					pm.getApplicationInfo(packageName,
							PackageManager.GET_META_DATA)).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * 判断是否存在本应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		ApplicationInfo info;
		try {
			 info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			
		} catch (NameNotFoundException e) {
			return false;
		}
		if(info==null){
			return false;
		}else{
			return true;
		}
	}
	

	/**
	 * 根据包名启动APP
	 * @param context
	 * @param packageName
	 */
	public static void LaunchForPackage(Context context,String packageName){
		  final PackageManager pm = context.getPackageManager();  
		  if(pm!=null){
			  Intent i = pm.getLaunchIntentForPackage(packageName);  
	           //如果该程序不可启动（像系统自带的包，有很多是没有入口的）会返回NULL  
	           if (i != null) {
	        	   context.startActivity(i);  
	           }
	        	  
		  }
		 
           
	}
	/**
	 * 获取虚拟机的最大的可用内存
	 * 
	 * @return 虚拟机最大的可用内存。
	 */
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}
	
	
	/**
	 * method:获得当前版本
	 */
	public  String getNowVersion() {
        if(!StringUtil.isNull(version)){
            return version;

        }
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(
                    context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
//		float appVersion = new Float().floatValue();
        if(info!=null){
            version = info.versionName;
        }else{
            version = "5.4.0";
        }

		return version;
	}
	
	


	
}
