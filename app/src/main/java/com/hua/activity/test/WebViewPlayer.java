package com.hua.activity.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hua.R;
import com.hua.utils.LogUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.webview_video)
public class WebViewPlayer extends Activity{

//    intent = new Intent(getActivity(), VODWebActivity_.class);
//    intent.putExtra("id", videoEntity.getData().getId());
//    intent.putExtra("url", videoEntity.getData().getUrl());
//    intent.putExtra("adType", videoEntity.getData().getAdType());
//    intent.putExtra("uid", userInfoUtil.getUid());
//    intent.putExtra("code", PassportUtil.getCode(getActivity()));
//    intent.putExtra("app", "mmq");
//    intent.putExtra("source", 1);
//    intent.putExtra("wifiOn",  PhoneInfoUtil.getInstance(getActivity()).getNetType().equals("WIFI")?1:0);
	
	@Extra("id")
	String id;
	
	@Extra("url")
	String url;
	
	@Extra("adType")
	int adType;
	
	@Extra("uid")
	String uid;
	
	@Extra("code")
	String code;
	
	@Extra("app")
	String app;
	
	@Extra("source")
	int source;
	
	@Extra("wifiOn")
	int wifiOn;
	
	@ViewById
	Button back_btn,switch_btn;
	
	@ViewById(R.id.webview)
	WebView webView;
	
	@ViewById
	FrameLayout video;
	
    @ViewById
    ProgressBar progress_bar;
    
    @ViewById
    LinearLayout dialogbody;
	
	private CustomViewCallback customViewCallback;
	
	@AfterInject
	void init(){
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	}
	
	@AfterViews
	void initVariable(){
		
		intiview();
		LoadUrl();
		
	}
	
	// 初始化
	private void intiview() {
		// TODO Auto-generated method stub
//		webView = (WebView) findViewById(R.id.webview);
//		back_btn = (Button) findViewById(R.id.back_btn);
		// 声明video，把之后的视频放到这里面去
//		video = (FrameLayout) findViewById(R.id.video);
		webView.setWebViewClient(new MyWebViewClient());
		dialogbody.setVisibility(View.VISIBLE);
//		back_btn.setOnClickListener(this);
	}
	
	
	// 加载web
	@SuppressLint({ "SetJavaScriptEnabled", "InlinedApi", "NewApi" })
	private void LoadUrl() {
		// TODO Auto-generated method stubs
		// 设置WebView属性，能够执行Javascript脚本
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUserAgentString(null);//设置WebView的用户代理字符串。如果字符串“ua”是null或空,它将使用系统默认的用户代理字符串  
		webView.getSettings().setUseWideViewPort(true);//设置webview推荐使用的窗口，使html界面自适应屏幕
		webView.getSettings().setBuiltInZoomControls(true); //支持手势缩放 
		webView.getSettings().setPluginState(PluginState.ON);//支持2.2以上所有版本  
		webView.getSettings().setAppCacheEnabled(true);//告诉webview启用应用程序缓存api。
		webView.setWebChromeClient(new DefaultWebChromeClient()); // 播放视频
		webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		webView.getSettings().setLoadWithOverviewMode(true);
//		http://v.163.com/paike/V8H1BIE6U/VAG52A1KT.html 网易视频
//		http://www.iqiyi.com/a_19rrhaymat.html 爱奇艺视频
		webView.loadUrl("http://v.163.com/paike/V8H1BIE6U/VAG52A1KT.html");
	}
	
	
	@Click
	void back_btn(){
		
		if (customViewCallback != null) {
			LogUtils.e("back--------");
			// 隐藏掉
			customViewCallback.onCustomViewHidden();
		}
		// 用户当前的首选方向
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// 退出全屏
		quitFullScreen();
		// 设置WebView可见
		webView.setVisibility(View.VISIBLE);
//		expend_headerV.setVisibility(View.VISIBLE);
//		back_btn_video.setVisibility(View.GONE);
		video.setVisibility(View.GONE);
		
	}
	
	
	private class DefaultWebChromeClient extends WebChromeClient {
		// 一个回调接口使用的主机应用程序通知当前页面的自定义视图已被撤职

		// 进入全屏的时候
		@Override
		public void onShowCustomView(final View view, CustomViewCallback callback) {
			LogUtils.d("onShowCustomView----iew="+view);
			// 赋值给callback
			customViewCallback = callback;
//			// 设置webView隐藏
//			webView.setVisibility(View.GONE);
//			back_btn.setVisibility(View.VISIBLE);
//			// 将video放到当前视图中
//			video.addView(view);
//			// 横屏显示
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//			// 设置全屏
//			setFullScreen();
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					// 设置webView隐藏
					webView.setVisibility(View.GONE);
					back_btn.setVisibility(View.VISIBLE);
					// 将video放到当前视图中
					video.addView(view);
					// 横屏显示
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					// 设置全屏
					setFullScreen();
					
				}
			});
			
		}

		// 退出全屏的时候
		@Override
		public void onHideCustomView() {
			LogUtils.i("onHideCustomView----");
			if (customViewCallback != null) {
				// 隐藏掉
				customViewCallback.onCustomViewHidden();
			}
			
//			// 用户当前的首选方向
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//			// 退出全屏
//			quitFullScreen();
//			// 设置WebView可见
//			webView.setVisibility(View.VISIBLE);
//			back_btn.setVisibility(View.GONE);
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {

					// 用户当前的首选方向
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					// 退出全屏
					quitFullScreen();
					// 设置WebView可见
					webView.setVisibility(View.VISIBLE);
					back_btn.setVisibility(View.GONE);
					
				}
			});
			
		}

		
		
		
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progress_bar.setVisibility(View.VISIBLE);
            progress_bar.setProgress(newProgress);
            if (newProgress == 100) {
                dialogbody.setVisibility(View.GONE);
                progress_bar.setVisibility(View.GONE);
            }
        }
		
	}
	
	
	
	// 关联webview 类
	class MyWebViewClient extends WebViewClient {

		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			LogUtils.i("onPageStarted--");
			dialogbody.setVisibility(View.VISIBLE);
		}
		// 加载结束的时候
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
//
//		@Override
//		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			return super.shouldOverrideUrlLoading(view, url);
//		}
//
//		@Override
//		public void onReceivedError(WebView view, int errorCode,
//				String description, String failingUrl) {
//			super.onReceivedError(view, errorCode, description, failingUrl);
//		}
//
//		@Override
//		public void onReceivedSslError(WebView view, SslErrorHandler handler,
//				SslError error) {
//			super.onReceivedSslError(view, handler, error);
//		}
//
//		@Override
//		public void onReceivedLoginRequest(WebView view, String realm,
//				String account, String args) {
//			super.onReceivedLoginRequest(view, realm, account, args);
//		}

	}
	
	

	/**
	 * 设置全屏
	 */
	private void setFullScreen() {
		// 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 全屏下的状态码：1098974464
		// 窗口下的状态吗：1098973440
	}
	
	/**
	 * 退出全屏
	 */
	private void quitFullScreen() {
		// 声明当前屏幕状态的参数并获取
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
	
	// 手机返回键监听
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// 如果是全屏状态 按返回键则变成非全屏状态，否则执行返回操作
			if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				quitFullScreen();
			} else {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					finish();
				}
			}

			return true;
		default:
			break;
		}
		return false;
	}
	
	
}
