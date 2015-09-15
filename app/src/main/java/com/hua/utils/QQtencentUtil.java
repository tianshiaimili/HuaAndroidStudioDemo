package com.hua.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.hua.widget.LoadDialog;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class QQtencentUtil {
	
	private Context context;
	public Tencent mTencent;

	onQQResult onResult;
	OnShareDone onShareDone;
//	public static String access_Token;
//	public static String OpenId;
	private static final String SCOPE = "all";
	LoadDialog loadDialog;
	public static String mAppid = "";// 申请时分配的appid//
	public static String scope = "get_user_info,get_info,add_share,add_t ";// 授权范围
//	String openid;
	
	public Tencent getmTencent() {
		return mTencent;
	}

	public void setmTencent(Tencent mTencent) {
		this.mTencent = mTencent;
	}

	public QQtencentUtil(Context context) {
		this.context = context;
		loadDialog = new LoadDialog(context);
		mTencent = Tencent.createInstance(mAppid, context.getApplicationContext());
	}

	public QQtencentUtil(Context context, onQQResult onResult) {
		this.context = context;
		this.onResult = onResult;
		loadDialog = new LoadDialog(context);
		mTencent = Tencent.createInstance(mAppid, context.getApplicationContext());
	}

	public OnShareDone getOnShareDone() {
		return onShareDone;
	}

	public void setOnShareDone(OnShareDone onShareDone) {
		this.onShareDone = onShareDone;
	}

	public onQQResult getOnResult() {
		return onResult;
	}

	public void setOnResult(onQQResult onResult) {
		this.onResult = onResult;
	}

	public QQtencentUtil() {
		super();
	}

	public boolean isLogin() {
		boolean flag = false;
		String[] s = PreferenceUtils.getQQValue(context);
		if (s != null) {
			mTencent.setOpenId(s[1]);
			mTencent.setAccessToken(s[0], s[2]);
			if (ready()) {
				flag = true;
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		if (!flag) {
			Login();
		}
		return flag;
	}

	/**
	 * 获取QQ的登录状态。是否登录了
	 * 
	 * @return
	 */
	public boolean isAccessToke() {
		String[] s = PreferenceUtils.getQQValue(context);
		if (s != null) {
			mTencent.setOpenId(s[1]);
			mTencent.setAccessToken(s[0], s[2]);
		}
		boolean ready = mTencent.isSessionValid() && mTencent.getOpenId() != null;
		return ready;
	}

	private boolean ready() {
		boolean ready = mTencent.isSessionValid() && mTencent.getOpenId() != null;
		if (!ready) {
			ToastUtil.showToast(context, "QQ登录已过期，请重新登录!");
		}
		return ready;
	}

	public void Login() {
		if (!mTencent.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
					try {
						String openid = values.getString("openid");
						String access_token = values.getString("access_token");
						String expires_in = values.getString("expires_in"); // 实际值需要通过上面介绍的方法来计算
						long time = System.currentTimeMillis() + Long.parseLong(expires_in) * 1000;
						mTencent.setOpenId(openid);
						mTencent.setAccessToken(access_token, time + "");
						PreferenceUtils.setUserSharePref(context, PreferenceUtils.ACCESS_TOKEN, access_token);
						PreferenceUtils.setUserSharePref(context, PreferenceUtils.OPENID, openid);
						PreferenceUtils.setUserSharePref(context, PreferenceUtils.EXPIRES_IN, time + "");
						if (onResult != null) {
							onResult.onResult(values);
						}
					} catch (Exception e) {
//						System.out.println(e);
					}
					mTencent.logout(context);
				}
			};
			mTencent.login((Activity) context, SCOPE, listener);
		} else {
			mTencent.logout(context);
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {
			
		}

		@Override
		public void onError(UiError e) {
			
		}

		@Override
		public void onCancel() {
			
		}
	}

	// 分享到腾讯
	public void AddShareTencen(String message) {
		if (isLogin()) {
			Bundle parmas = new Bundle();
			mTencent.requestAsync("t/add_t", parmas, Constants.HTTP_POST, new BaseApiListener(), null);
			loadDialog.show();
			loadDialog.setMessage("分享中..");
		}
	}

	// 分享到腾讯
	public void AddShareTencenPic(String message, String fleName) {
		if (isLogin()) {
			Bundle parmas = new Bundle();
			File file = new File(fleName);
			byte[] buff = null;
			try {
				InputStream is = new FileInputStream(file);
				ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outSteam.write(buffer, 0, len);
				}
				outSteam.close();
				is.close();
				buff = outSteam.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mTencent.requestAsync("t/add_pic_t", parmas, Constants.HTTP_POST, new BaseApiListener(), null);

			loadDialog.show();
			loadDialog.setMessage("分享中..");
		}
	}

	// 分享到腾讯
	public void AddShareTencenPic(String message, View v) {
		if (isLogin()) {
			Bundle parmas = new Bundle();
			byte[] buff = ImgUtil.shotScreen(v);
			parmas.putString("", message);
			parmas.putString("", PreferenceUtils.getUserSharePref(context, PreferenceUtils.OPENID));
			parmas.putString("", mAppid);
			parmas.putString("", PreferenceUtils.getUserSharePref(context, PreferenceUtils.ACCESS_TOKEN));
			parmas.putByteArray("pic", buff);
			mTencent.requestAsync("t/add_pic_t", parmas, Constants.HTTP_POST, new BaseApiListener(), null);

			loadDialog.show();
			loadDialog.setMessage("分享中..");
		}
	}

	// 默默分享到腾讯
	public void AddShareBackTencenPic(String message, View v) {
		Bundle parmas = new Bundle();
		byte[] buff = ImgUtil.shotScreen(v);
		mTencent.requestAsync("t/add_pic_t", parmas, Constants.HTTP_POST, new BaseApiListener(), null);
	}

	// 分享到腾讯
	public void AddShareTencenPic(String message, Bitmap b) {
		if (isLogin()) {
			Bundle parmas = new Bundle();
			byte[] buff = ImgUtil.shotScreen(b);
			parmas.putByteArray("pic", buff);
			mTencent.requestAsync("t/add_pic_t", parmas, Constants.HTTP_POST, new BaseApiListener(), null);
			loadDialog.show();
			loadDialog.setMessage("分享中..");
		}
	}

	private void ShowMessage(String value) {
		if (onShareDone != null) {
			if ("0".equals(value)) {
				onShareDone.OnShare(true);
			} else {
				onShareDone.OnShare(false);
			}
		}
	}

	private class BaseApiListener implements IRequestListener {

		@Override
		public void onComplete(JSONObject response) {
			if (loadDialog.isShowing()) {
				loadDialog.dismiss();
			}

			try {
				ShowMessage(response.getString("ret"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onConnectTimeoutException(ConnectTimeoutException arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

		@Override
		public void onIOException(IOException arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

		@Override
		public void onJSONException(JSONException arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

		@Override
		public void onMalformedURLException(MalformedURLException arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

		@Override
		public void onSocketTimeoutException(SocketTimeoutException arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

		@Override
		public void onUnknowException(Exception arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

		@Override
		public void onHttpStatusException(HttpStatusException arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

		@Override
		public void onNetworkUnavailableException(NetworkUnavailableException arg0) {
			// TODO Auto-generated method stub
//			System.out.println("arg0 === " + arg0);
		}

	}

	// 回调接口
	public interface onQQResult {
		void onResult(JSONObject value);
	}

	public interface OnShareDone {
		void OnShare(boolean flag);
	}

	// 分享到空间
	public void AddShareQQ(String title, String url, String summary, String imgUrl) {
		Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
		ArrayList<String> imageUrls = new ArrayList<String>();
		if (imgUrl != null && !imgUrl.equals("")) {
			imageUrls.add(imgUrl);
		} else {
			imageUrls.add("");
		}
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
		doShareToQzone(params);
	}

	// 默默分享到空间
	public void AddShareBackQQ(String title, String url, String summary) {
		PublicMethod.isContextDestroy = false;
		Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
		ArrayList<String> imageUrls = new ArrayList<String>();
		imageUrls.add("");
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
		doShareToQzone(params);
	}

	public void AddShare(String title, String message, String summary) {
		Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "" + "#" + System.currentTimeMillis());
		ArrayList<String> imageUrls = new ArrayList<String>();
		imageUrls.add("");
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
		doShareToQzone(params);
	}

	/**
	 * 用异步方式启动分享
	 * 
	 * @param params
	 */
	private void doShareToQzone(final Bundle params) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				PublicMethod.isContextDestroy = false;
				mTencent.shareToQzone((Activity) context, params, new IUiListener() {
					@Override
					public void onCancel() {
//						System.out.println("sss");
					}

					@Override
					public void onError(UiError e) {
						// TODO Auto-generated method stub
//						System.out.println(e);
					}

					protected void doComplete(JSONObject response) {
						try {
							ShowMessage(response.getString("ret"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onComplete(Object response) {
						if (loadDialog.isShowing()) {
							loadDialog.dismiss();
						}
						doComplete((JSONObject) response);
					}
				});
			}
		}).start();
	}
}
