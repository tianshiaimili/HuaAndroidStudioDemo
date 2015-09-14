package com.hua.utils;

import java.io.Serializable;

import android.content.Context;
import android.util.Log;

/**
 * 缓存操作工具类
 * @author ck
 * @since 2014年4月23日 09:46:52
 *
 */
public class ACacheUtil {

	public static final String LOG_TAG = "cache";
	
	//是否需要使用缓存
	public static boolean isUsed = true;
	
	private static ACache aCache = null;
	
	/**
	 * 在application里面初始化
	 * @param context
	 */
	public static void initialize(Context context)
	{
		aCache = ACache.get(context);
	}
	
	/**
	 * 把对象根据key来放入缓存中
	 * @param key 存放对角的关键字
	 * @param obj 存放的对象
	 * @return true 存放成功；false 存放失败
	 */
	public static boolean put(String key, Serializable obj)
	{
		if(isUsed)
		{
			if(isInitialized())
			{
				aCache.put(key, obj);
				return true;
			}
		}
		return false;
	}
	/**
	 * 把对象根据key来放入缓存中
	 * @param key 存放对角的关键字
	 * @param obj 存放的对象
	 * @param saveTime 存放的时间
	 * @return true 存放成功；false 存放失败
	 */
	public static boolean put(String key, Serializable obj,int saveTime)
	{
		if(isUsed)
		{
			if(isInitialized())
			{
				aCache.put(key, obj, saveTime);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据key来得到缓存中的对象
	 * @param key 缓存对应关键字
	 * @return 存在时返回对象，不存在时返回null
	 */
	public static Object get(String key)
	{
		if(isUsed)
		{
			if(isInitialized())
				return aCache.getAsObject(key);
		}
		
		return null;
	}
	
	/**
	 * 清除某个关键字所对应的缓存
	 * @param key
	 * @return 操作状态
	 */
	public static boolean clear(String key)
	{
		if(isUsed)
		{
			if(isInitialized())
				return aCache.remove(key);
			
		}
		return false;
	}
	
	
	/**
	 * 清除所有缓存
	 * @return 操作状态
	 */
	public static boolean clearAll()
	{
		if(isUsed)
		{
			if(isInitialized())
			{
				aCache.clear();
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断缓存是否没初始化
	 * @return 缓存是否为null
	 */
	public static boolean isInitialized()
	{
		if(aCache != null)
			return true;
		else
		{
			Log.e(LOG_TAG, "缓存没有初始化");
			return false;
		}
	}
	
	/**
	 * 根据参数返回Type
	 * @param params
	 * @return
	 */
	public static String getCacheName(String... params){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < params.length; i++) {
			if(null!=params[i]){
				sb.append(params[i]);
				
			}
			if(i!=params.length-1){
				sb.append("_");
			}
		}
		return sb.toString();
	}
}
