package com.hua.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.hua.http.HttpConnManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 手机基本信息
 * 
 *
 */
public class PhoneInfoUtil {
	private TelephonyManager telephonyManager;
	private Context context;
	static PhoneInfoUtil phoneinfo;
	private Map<String, Object> infoMap;
    public String deviceId ;

	private PhoneInfoUtil(Context context) {
		telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		this.context = context;
		infoMap = new HashMap<String, Object>();
	}

	public int getSIMState() {
		return telephonyManager.getSimState();
	}


	public static PhoneInfoUtil getInstance(Context context) {
		if (phoneinfo == null) {
			phoneinfo = new PhoneInfoUtil(context);
		}
		return phoneinfo;
	}

	/**
	 * 获取手机mac地址
	 * 
	 * @return
	 * @time 2011-8-12 下午03:45:01
	 * @author:linyg
	 */
	public String macAddress() {
		String macAddress = "";
		if (infoMap.containsKey("mac_address")) {
			macAddress = (String) infoMap.get("mac_address");
		} else {
			try {
				WifiManager wifi = (WifiManager) context
						.getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = wifi.getConnectionInfo();
				macAddress = info.getMacAddress();
			} catch (Exception e) {

			}
			infoMap.put("mac_address", macAddress);
		}
		return macAddress;
	}

	/**
	 * 手机型号
	 * 
	 * @return
	 * @time 2011-6-1 下午03:20:14
	 * @author:linyg
	 */
	public String getModel() {
		String model = "";
		if (infoMap.containsKey("get_model")) {
			model = (String) infoMap.get("get_model");
		} else {
			model = android.os.Build.MODEL;
			infoMap.put("get_model", model);
		}
		return model;
	}

	/**
	 * 固件号
	 * 
	 * @return
	 * @time 2011-6-1 下午03:20:23
	 * @author:linyg
	 */
	public String getFramework() {
		String firewall = "";
		if (infoMap.containsKey("get_fire_wall")) {
			firewall = (String) infoMap.get("get_fire_wall");
		} else {
			firewall = android.os.Build.VERSION.SDK;
			infoMap.put("get_fire_wall", firewall);
		}
		return firewall;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 * @time 2011-6-1 下午03:20:35
	 * @author:linyg
	 */
	public String getPhoneNum() {
		return telephonyManager.getLine1Number();
	}

	/**
	 * 获取当前系统语言
	 * 
	 * @return
	 * @time 2011-8-8 上午11:57:15
	 * @author:linyg
	 */
	public String getLanguage() {
		return Locale.getDefault().getLanguage();
	}

	public String getSettingLang() {
		return context.getResources().getConfiguration().locale.getCountry();
	}

	/**
	 * 获取当前国家和地区
	 * 
	 * @return
	 * @time 2011-8-8 上午11:57:52
	 * @author:linyg
	 */
	public String getCountry() {
		return Locale.getDefault().getCountry();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return
	 */
	public String getNetType() {
		String netType = "";
		try {
			ConnectivityManager conn = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			String type = conn.getActiveNetworkInfo().getTypeName();
			// 若当前是wifi网络，则直接返回wifi; 否则返回详细的网络连接类型
			if (type != null && type.equalsIgnoreCase("wifi")) {
				netType = type;
			} else {
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mobNetInfo = conn
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				netType = mobNetInfo.getExtraInfo();
			}
		} catch (Exception e) {
		}
		return netType;
	}

	/**
	 * 获取设备唯一码
	 *
	 * @return
	 */
	public String getDeviceId() {
        if(!StringUtil.isNull(deviceId)){
            return deviceId;
        }
        StringBuffer sbDeviceMeta = new StringBuffer();
        String imei = getIMEI(context);
        if (imei != null) {
            sbDeviceMeta.append(imei.trim());
        }

        String mac = HttpConnManager.getLocalMacAddress(context);
        if (mac != null) {
            sbDeviceMeta.append(mac.trim());
        }

//		String bluetoothMac = getBluetoothMac(ctx);
//		if (bluetoothMac != null) {
//			sbDeviceMeta.append(bluetoothMac.trim());
//		}

        deviceId = MD5.Md5(sbDeviceMeta.toString());
        byte[] bytes = deviceId.getBytes();
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (i % 2 == 0) {
                sum1 += bytes[i];
            } else {
                sum2 += bytes[i];
            }
        }

        final char[] codes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int checkSum1 = sum1 % codes.length;
        int checkSum2 = sum2 % codes.length;
        deviceId = deviceId + codes[checkSum1] + codes[checkSum2];

        return deviceId;
	}
//
//	/**
//	 * 获取设备唯一码
//	 *
//	 * @return
//	 */
//	public String getDeviceId() {
//        if(!StringUtil.isNull(mDeviceId)){
//            return mDeviceId;
//        }
//        boolean invalidDeviceID = false;
//        // 获取imei号
//        String deviceID = "";
//        try {
//            deviceID = telephonyManager.getDeviceId();
//            if (deviceID == null) {
//                invalidDeviceID = true;
//            } else if (deviceID.length() == 0
//                    || deviceID.startsWith("000000000000") || deviceID.equals("0")) {
//                invalidDeviceID = true;
//            }
//            if(invalidDeviceID){
//                deviceID = Settings.Secure.getString(context.getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
//                if(deviceID!=null){
//                    invalidDeviceID = true;
//                }
//            }
//
//            // 如果未获取到 2.2以上的系统才能使用
//            if (invalidDeviceID
//                    && Integer.parseInt(android.os.Build.VERSION.SDK) >= 9) {
//                try {
//                    Class<?> c = Class.forName("android.os.SystemProperties");
//                    Method get = c.getMethod("get", String.class, String.class);
//                    deviceID = (String) (get.invoke(c, "ro.serialno", "unknown"));
//                } catch (Exception e) {
//                }
//
//                if (deviceID == null) {
//                    invalidDeviceID = true;
//                } else if (deviceID.length() == 0
//                        || deviceID.startsWith("000000000000")
//                        || deviceID.equals("0") || deviceID.equals("unknown")) {
//                    invalidDeviceID = true;
//                } else {
//                    invalidDeviceID = false;
//                }
//            }
//
//            if(deviceID==null||"".equals(deviceID)){
//                return "0";
//            }
//            if(deviceID!=null&&!"".equals(deviceID)){
//                if("unknown".equals(deviceID.toLowerCase())){
//                    return "0";
//                }
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            deviceID = "";
//
//        }
//        mDeviceId = deviceID;
//
//		return deviceID;
//	}

	/**
	 * 生产厂商
	 * 
	 * @param @return
	 * @return String
	 */
	public String phoneManufacturer() {
		return android.os.Build.MANUFACTURER;
	}

	/**
	 * 运营商名称
	 * 
	 * @param @return
	 * @return String
	 */
	public String phoneCarrierName() {
		return telephonyManager.getNetworkOperatorName();
	}

	/**
	 * 运营商国家码
	 * 
	 * @param @return
	 * @return String
	 */
	public String phoneCarrierCountryIso() {
		return telephonyManager.getNetworkCountryIso();
	}

	/**
	 * 手机国家码
	 * 
	 * @param @return
	 * @return String
	 */
	public String phoneOperator() {
		return telephonyManager.getNetworkOperator();
	}

	/**
	 * 获取电信运营商
	 * 
	 * @return String
	 */
	public String getIMSI() {
		String imsi = "";
		if (infoMap.containsKey("imsi")) {
			imsi = (String) infoMap.get("imsi");
		} else {
			String subscriberId = telephonyManager.getSubscriberId();
			if (subscriberId != null) {
				if (imsi.startsWith("46000")
						|| subscriberId.startsWith("46002")) {// 中国移动
					imsi = "中国移动";
				} else if (subscriberId.startsWith("46001")) {// 中国联通
					imsi = "中国联通";
				} else if (imsi.startsWith("46003")) {// 中国电信
					imsi = "中国电信";
				}
			}
			infoMap.put("imsi", imsi);
		}
		return imsi;
	}

	/**
	 * 是否为中国大陆
	 * 
	 * @return boolean
	 */
	public boolean isChinaCarrier() {
		boolean isChina = false;
		if (infoMap.containsKey("is_china_carrier")) {
			isChina = (Boolean) infoMap.get("is_china_carrier");
		} else {
			String imsi = telephonyManager.getSubscriberId();
			if (imsi != null) { // 含手机卡
				if (imsi.startsWith("460"))
					isChina = true;
			} else { // 不含手机卡，根据地区
				if (getCountry().toLowerCase().equals("cn")) {
					isChina = true;
				}
			}
			infoMap.put("is_china_carrier", isChina);
		}
		return isChina;
	}

	/**
	 * imei号
	 * 
	 * @return
	 */
	public String getIMEI() {
		String deviceID = telephonyManager.getDeviceId();
		return deviceID;
	}
    public  String getIMEI(Context context) {
        String imei = "";
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (manager != null) {
            imei = manager.getDeviceId();
        }

        return imei;
    }

 
	
}

