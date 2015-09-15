package com.hua.utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 该类主要用于QQ分享操作
 * 
 */
public class QQShareUtil {

	private Activity context;
	private Tencent mTencent;
	private static final String APP_ID = "";
	public static final String TAG = "QQ_SHARE";
	Bundle shareParams = null;
	public void onDone(){};

	public QQShareUtil(Activity context) {
		super();
		this.context = context;
		mTencent = Tencent.createInstance(APP_ID, context);

	}

	Handler shareHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.v(TAG, "handleMessage:" + msg.arg1);
		}
	};

	// 线程类，该类使用匿名内部类的方式进行声明
	Runnable shareThread = new Runnable() {
		public void run() {
			Log.v(TAG, "Begin Thread");
			doShareToQQ(shareParams);
			Message msg = shareHandler.obtainMessage();
			// 将Message对象加入到消息队列当中
			shareHandler.sendMessage(msg);
		}
	};

	/**
	 * 设置分享内容
	 * 
	 * @param bundle
	 */
	public void setValus(Bundle bundle) {
		shareParams = bundle;
		Thread thread = new Thread(shareThread);
		thread.start();
	}

	private void doShareToQQ(Bundle params) {

		mTencent.shareToQQ(context, params, new IUiListener() {
			
			@Override
			public void onError(UiError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(Object arg0) {
				// TODO Auto-generated method stub
				onDone();
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
