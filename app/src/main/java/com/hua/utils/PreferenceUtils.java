package com.hua.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {

	public static final String IsFirstUsed = "IsFirstUsed"; // 是否第一次使用
	public static final String Uid = "uid";
	public static final String lastNotificationMsg = "lastNotificationMsg";

	public static final String Username = "username";
	public static final String Hash = "hash";
	public static final String Cityname = "cityname";
	public static final String CITYID = "cityId";
	public static final String Site = "site";
	public static final String Bb_birthday = "bb_birthday";
	/**宝宝小名*/
	public static final String BB_LITTLE_NAME = "bb_little_name";
	/**宝宝性别*/
	public static final String BB_SEX = "bb_sex";
	/**宝宝性别状态 标记*/
	public static final String BB_SEX_TYPE = "bb_sex_type";

	public static final String Pic = "pic";
	public static final String Hospital = "hospital";
	public static final String Status = "status"; // 登录、未登录的状态
	public static final String BB_type = "bb_type";
	public static final String Avatar_file_path = "avatar_file_path";

	public static final String ACCESS_TOKEN = "access_Token"; // QQtoken
	public static final String OPENID = "openid"; // QQopenid
	public static final String EXPIRES_IN = "expires_in"; // QQ过期时间


	public static final String MY_Local_CITY = "my_local_city"; // 我的定位城市

	// 未登录和基本配置信息
	public static final String SEARCHCIRCLEKEY = "searchCirclekey"; // 圈子搜索
	public static final String SEARCHUSERKEY = "searchUserkey";// 用户搜索

	public static final String RemindYesorNo = "remindyesorno"; // 是否要消息推送
	public static final String TrendsUpdateTime = "trendsupdatetime"; // 动态更新时间
	public static final String PmnumUpdateTime = "pmnumupdatetime"; // 短消息更新时间


	public static final String DY_DATE = "dy_date";
	public static final String DY_DAYS = "dy_days";
	public static final String DY_WEEK = "dy_week";

	public static final String TIMEERRAND = "TimeErrand"; // 时间差

	public static final String OLD_GUILD = "old_gulid6";// 第一次发现引导页

    public static final String MUSTPOINT = "mustpoint"; //非买不可第一次红点

	// 保存模式：1:String ,2:int ,3:long, 4:boolean, 5:Float
	public static final int DATA_STRING = 1;
	public static final int DATA_INT = 2;
	public static final int DATA_LONG = 3;
	public static final int DATA_BOOLEAN = 4;
	public static final int DATA_FLOAT = 5;

	public static final String defalutShareFileName = "sputil";
	public static final String userShareFileName = "user";
	public static final String minShareFileName = "min_info";
	public static final String circleShareFileName = "circle_info";//进入圈子配置文件名

	public static final String adShareFileName = "ad";// 广告配置文件名

    public static final String CIRCLE_WRITE = "circle_write";// 圈子发帖指导页


	public static final String MAMA_BIRTH = "mama_birth"; // 妈妈生日
	public static final String IS_RAND = "is_rand"; // 是否为随机用户名，1是随机用户名，0不是随机用户名

	public static final String ADIMAGEURL = "adImageUrl"; // 广告图片地址
	public static final String ADNUMBER = "adNumber"; // 广告点击次数

	public static final String HAVESHORTCUT = "shortCut";// 是否创建快捷方式

	public static final String WX_ACCESS_TOKEN = "wx_access_Token"; // 微信token
	public static final String WX_OPENID = "wx_openid"; // 微信openid
	public static final String WX_REFRESH_TOKEN = "wx_refresh_token"; // 微信refresh_token
	public static final String WX_EXPIRES_IN = "wx_expires_in"; // 微信授权accesstoken过期时间
	public static final String WX_UNIONID = "wx_unionid"; // 微信授权unionid
	public static final String WX_OAUTH_TIME = "wx_oauth_time"; // 微信授权时间点，用于计算下次微信登录授权信息是否过期
	
	public static final String PUSH_SILENCE_MODE = "push_silence_mode";//推送静音模式 0：关闭 ，1：开启
	public static final String PUSH_VIRBATE_MODE = "push_virbate_mode";//推送震动模式 0：关闭 ，1：开启
	
    public static final String PASSPORT_CODE = "passport_code";//passport的code
    public static final String PASSPORT_CODE_TIME = "passport_code_time";//passport服务器时间
    public static final String PASSPORT_CACHE_TIME = "passport_cache_time";//passport缓存时间

    public static final String HUATI_COLLECT_TIP = "huati_collect_tip";//话题收藏提示框
    public static final String HUODONG_COLLECT_TIP = "huodong_collect_tip";//活动收藏提示框

    public static final String SHOW_CIRCLE_COUNT = "show_circle_count";//前5次显示我的圈子


    public static final String FRI_BACKGROUND = "fri_background";

    public static final String TUIJIAN_TIP = "tuijian_tip";//帖子详情推荐按钮提示
    public static final String FASHUOSHUO_TIP = "fashuoshuo_tip";//朋友界面发说说提示
	public static final String INTEREST_TIP = "interest_tip";//朋友界面兴趣选择的提示

    public static final String NO_BBTYPE_TIP = "no_bbtype_tip";//没有快乐孕期或者快乐育儿时的首次提醒

    public static final String LAUCHURL = "lauchUrl";//闪屏页地址

    public static final String MUSTBUY_SEARCH_KEYWORD = "mustbuy_search_keyword";//非卖不可搜索关键字

    public static final String MASTER_FIRST = "master_first";
	/**
	 * 获取用户保存SharePrefxml
	 * 
	 * @param context
	 * @param configKey
	 * @param configValue
	 * @return
	 */
	public static boolean setUserSharePref(Context context, String configKey,
			Object configValue) {
		return setSharePref(context, userShareFileName, configKey, configValue);
	}



    public static boolean setCityTimePref(Context context, String uid,String site,
                                           Object configValue) {
        return setSharePref(context, uid, site, configValue);
    }

    public static String getCityTimePref(Context context, String uid,String site) {
        return getSharePref(context, uid, site);
    }



	/**
	 * 设置广告
	 * 
	 * @param context
	 * @param configKey
	 * @param configValue
	 * @return
	 */
	public static boolean setAdSharePref(Context context, String configKey,
			Object configValue) {
		return setSharePref(context, adShareFileName, configKey, configValue);
	}

	/**
	 * 获取我的SharePrefxml
	 * 
	 * @param context
	 * @param configKey
	 * @param configValue
	 * @return
	 */
	public static boolean setMinSharePref(Context context, String configKey,
			Object configValue) {
		return setSharePref(context, minShareFileName, configKey, configValue);
	}

	/**
	 * 获取系统默认SharePrefxml
	 * 
	 * @param context
	 * @param configKey
	 * @param configValue
	 * @return
	 */
	public static boolean setDefalutSharePref(Context context,
			String configKey, Object configValue) {
		return setSharePref(context, defalutShareFileName, configKey,
				configValue);
	}

	/**
	 * 设置用户进入圈子配置
	 *
	 * @param context
	 * @param configKey
	 * @param configValue
	 * @return
	 */
	public static boolean setCircleSharePref(Context context, String configKey,
										 Object configValue) {
		return setSharePref(context, circleShareFileName, configKey, configValue);
	}

	/**
	 * 获取广告配置文件
	 * 
	 * @param context
	 * @param configKey
	 * @return
	 */
	public static String getAdSharePref(Context context, String configKey) {
		return getSharePref(context, adShareFileName, configKey);
	}

	/**
	 * 获取广告配置文件
	 * 
	 * @param context
	 * @param configKey
	 * @return
	 */
	public static Integer getAdSharePrefInt(Context context, String configKey) {
		return getSharePrefInt(context, adShareFileName, configKey);
	}
	/**
	 * 获取广告配置文件
	 *
	 * @param context
	 * @param configKey
	 * @return
	 */
	public static Integer getDefaultSharePrefInt(Context context, String configKey) {
		return getSharePrefInt(context, defalutShareFileName, configKey);
	}

	/**
	 * 获取用户配置文件
	 * 
	 * @param context
	 * @param configKey
	 * @return
	 */
	public static String getUserSharePref(Context context, String configKey) {
		return getSharePref(context, userShareFileName, configKey);
	}



	/**
	 * 获取默认配置文件
	 * 
	 * @param context
	 * @param configKey
	 * @return
	 */
	public static String getDefalutSharePref(Context context, String configKey) {
		return getSharePref(context, defalutShareFileName, configKey);
	}

	/**
	 * 获取我的配置文件
	 * 
	 * @param context
	 * @param configKey
	 * @return
	 */
	public static String getMinSharePref(Context context, String configKey) {
		return getSharePref(context, minShareFileName, configKey);
	}

	/**
	 * 获取用户进入圈子配置
	 *
	 * @param context
	 * @param configKey
	 * @return
	 */
	public static Integer getCircleSharePrefInt(Context context, String configKey) {
		return getSharePrefInt(context, circleShareFileName, configKey);
	}

	/**
	 * 
	 * @param context
	 * @param fileNmae
	 * @param configKey
	 * @return
	 */
	public static String getSharePref(Context context, String fileNmae,
			String configKey) {
		if (context == null) {
			return "";
		}
		SharedPreferences preferences = context.getSharedPreferences(fileNmae,
				Context.MODE_WORLD_READABLE);
		return preferences.getString(configKey, "");
	}

	/**
	 * 
	 * @param context
	 * @param fileNmae
	 * @param configKey
	 * @return
	 */
	public static Integer getSharePrefInt(Context context, String fileNmae,
			String configKey) {
		if (context == null) {
			return 0;
		}
		SharedPreferences preferences = context.getSharedPreferences(fileNmae,
				Context.MODE_WORLD_READABLE);
		return preferences.getInt(configKey, 0);
	}

	// 把配置的键值对 写入配置文件
	public static boolean setSharePref(Context context, String fileName,
			String configKey, Object configValue) {
		if (context == null) {
			return false;
		}
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_WORLD_READABLE);
		if (preferences == null) {
			preferences = context.getSharedPreferences(fileName,
					Context.MODE_PRIVATE);
			if (preferences == null) {
				return false;
			}
		}
		Editor editor = preferences.edit();

		if (configValue instanceof String) // String类型
		{
			String str = (String) configValue;
			editor.putString(configKey, str);

		} else if (configValue instanceof Integer)// Integer类型
		{
			Integer i = (Integer) configValue;
			editor.putInt(configKey, i);

		} else if (configValue instanceof Boolean)// Boolean类型
		{
			Boolean b = (Boolean) configValue;
			editor.putBoolean(configKey, b);

		} else if (configValue instanceof Long)// Long类型
		{
			Long l = (Long) configValue;
			editor.putLong(configKey, l);

		} else if (configValue instanceof Float)// Float类型
		{
			Float f = (Float) configValue;
			editor.putFloat(configKey, f);

		} else {
			editor.putString(configKey, "");
			editor.commit();
			return false;
		}
		editor.commit();
		return true;

	}

	/**
	 * 保存用户信息
	 * 
	 * @param ctx
	 * @param luib
	 */
//	public static void saveUser(Context ctx, LoginUserInfoBean luib) {
//		if (luib == null) {
//			return;
//		}
//		PushRequestUtil.loginoutMqtt(ctx);
//		setUserSharePref(ctx, Uid, luib.getUid());
//		setUserSharePref(ctx, Username, luib.getUsername());
//		setUserSharePref(ctx, Hash, luib.getHash());
//		setUserSharePref(ctx, Cityname, luib.getCityname());
//		setUserSharePref(ctx, Site, luib.getSite());
//		setUserSharePref(ctx, Bb_birthday, luib.getBb_birthday());
//		setUserSharePref(ctx, Pic, luib.getPic());
//		setUserSharePref(ctx, Hospital, luib.getHospital());
//		setUserSharePref(ctx, Status, luib.getStatus());
//		setUserSharePref(ctx, BB_type, luib.getBb_type());
//		setUserSharePref(ctx, MAMA_BIRTH, luib.getMama_birth());
//	}

	/**
	 * 获取本地和服务器的时间差
	 * 
	 * @param
	 * @return
	 */
//	public static String getNowTime(Context context) {
//		long time = 0;
//		if (context != null) {
//			if (MMApplication.TimeErrand == 0) {
//				SharedPreferences sp = context.getSharedPreferences(
//						defalutShareFileName, Context.MODE_WORLD_READABLE);
//				time = sp.getLong(TIMEERRAND, 0);
//				MMApplication.TimeErrand = time;
//			} else {
//				time = MMApplication.TimeErrand;
//			}
//		}
//		if (0 == time) {
//			return String.valueOf(new Date().getTime() / 1000);
//		} else {
//			return String.valueOf(new Date().getTime() / 1000 - time);
//		}
//	}

	/**
	 * 保存服务器时间差
	 * 
	 * @param ctx
	 * @param serviceTime
	 */
	public static void saveTimeErrand(Context ctx, String serviceTime) {
		if (ctx == null) {
			return;
		}
		SharedPreferences sp = ctx.getSharedPreferences(defalutShareFileName,
				Context.MODE_WORLD_READABLE);
		Editor et = sp.edit();
		long time = getTimeErrand(new Date().getTime() / 1000,
				Long.parseLong(serviceTime));
		et.putLong(TIMEERRAND, time);
		et.commit();
	}

	/**
	 * 返回客户端和服务端的差值
	 */
	private static long getTimeErrand(long currentTime, long serviceTime) {
		return currentTime - serviceTime;
	}


//	public static void saveMinInfo(Context ctx, UserIndexInfoBean luib) {
//		if (ctx == null || luib == null) {
//			return;
//		}
//		SharedPreferences sp = ctx.getSharedPreferences(userShareFileName,
//				Context.MODE_WORLD_READABLE);
//		Editor e = sp.edit();
//		e = sp.edit();
//		if (!StringUtil.isNull(luib.getBb_type())) {
//			e.putString(BB_type, luib.getBb_type());
//		}
//		if (!StringUtil.isNull(luib.getBb_birthday())) {
//			e.putString(Bb_birthday, luib.getBb_birthday());
//		}
//		if (!StringUtil.isNull(luib.getUsername())) {
//			e.putString(Username, luib.getUsername());
//		}
//		if (!StringUtil.isNull(luib.getCityname())) {
//			e.putString(Cityname, luib.getCityname());
//		}
//		if (!StringUtil.isNull(luib.getHospital())) {
//			e.putString(Hospital, luib.getHospital());
//		}
//		if (!StringUtil.isNull(luib.getUid())) {
//			e.putString(Uid, luib.getUid());
//		}
//		if (!StringUtil.isNull(luib.getUsername())) {
//			e.putString(Username, luib.getUsername());
//		}
//
//		e.commit();
//
//	}

	public static String[] getQQValue(Context ctx) {
		if (ctx == null) {
			return null;
		}
		SharedPreferences sp = ctx.getSharedPreferences(userShareFileName,
				Context.MODE_WORLD_READABLE);
		String token = sp.getString(ACCESS_TOKEN, "");
		if ("".equals(token)) {
			return null;
		} else {
			String[] s = new String[3];
			s[0] = token;
			s[1] = sp.getString(OPENID, "");
			s[2] = sp.getString(EXPIRES_IN, "");
			return s;
		}

	}

	public static void assembly(Context context, String keyWorlds,
			String searchKey) {
		StringBuilder sb = new StringBuilder();
		String[] value = split(context, keyWorlds, searchKey);
		if (value != null) {
			sb.append(keyWorlds).append(",");
			for (String world : value) {
				sb.append(world).append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			setDefalutSharePref(context, searchKey, sb.toString());
		} else {
			setDefalutSharePref(context, searchKey, keyWorlds);
		}

	}

	public static String[] split(Context ctx, String name, String searchKey) {
		String[] keyWorlds = null;
		List<String> keyList = new ArrayList<String>();
		SharedPreferences sp = ctx.getSharedPreferences(defalutShareFileName,
				Context.MODE_WORLD_READABLE);
		String common = sp.getString(searchKey, "");
		if ("".equals(common))
			return keyWorlds;
		keyWorlds = common.split(",");
		if (name == null) {
			return keyWorlds;
		} else {
			if (keyWorlds.length > 0) {
				for (int i = 0; i < keyWorlds.length && i < 50; i++) {
					if (!keyWorlds[i].equals(name)) {
						keyList.add(keyWorlds[i]);
					}
				}
			}
			keyWorlds = keyList.toArray(new String[keyList.size()]);

		}

		return keyWorlds;

	}

	public static String[] getWXValue(Context ctx) {
		if (ctx == null) {
			return null;
		}
		SharedPreferences sp = ctx.getSharedPreferences(userShareFileName,
				Context.MODE_WORLD_READABLE);
		String token = sp.getString(WX_ACCESS_TOKEN, "");
		if ("".equals(token)) {
			return null;
		} else {
			String[] s = new String[5];
			s[0] = token;
			s[1] = sp.getString(WX_OPENID, "");
			s[2] = sp.getString(WX_EXPIRES_IN, "");
			s[3] = sp.getString(WX_REFRESH_TOKEN, "");
			s[4] = sp.getString(WX_OAUTH_TIME, "");
			return s;
		}

	}

	public static String getWXOauthTime(Context ctx) {
		String oauth_time = "";
		if (ctx == null) {
			return null;
		}
		SharedPreferences sp = ctx.getSharedPreferences(userShareFileName,
				Context.MODE_WORLD_READABLE);
		if (sp != null) {
			sp = ctx.getSharedPreferences(userShareFileName,
					Context.MODE_PRIVATE);
			if (sp == null) {
				return oauth_time;
			}
		}

		oauth_time = sp.getString(WX_OAUTH_TIME, "");

		return oauth_time;

	}
}
