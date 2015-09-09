package com.hua.bean;

import android.os.Handler;
import android.webkit.WebView;

import com.hua.R;
import com.hua.activity.test.HTMLActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyObject {
	private Handler handler = null;
	private WebView webView = null;

	public MyObject(HTMLActivity htmlActivity, Handler handler) {
		this.webView = (WebView) htmlActivity.findViewById(R.id.webView);
		this.handler = handler;
	}

	public void init() {
		// 通过handler来确保init方法的执行在handler绑定的Activity的主线程中
		handler.post(new Runnable() {

			public void run() {
				// 调用客户端setContactInfo方法
				webView.loadUrl("javascript:setContactInfo('" + getJsonStr()
						+ "')");
			}
		});
	}     

	public static String getJsonStr() {
		try {
			JSONObject object1 = new JSONObject();
			object1.put("id", 1);
			object1.put("name", "张三");
			object1.put("phone", "123456");

			JSONObject object2 = new JSONObject();
			object2.put("id", 2);
			object2.put("name", "李四");
			object2.put("phone", "456789");

			JSONArray jsonArray = new JSONArray();
			jsonArray.put(object1);
			jsonArray.put(object2);
			return jsonArray.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
