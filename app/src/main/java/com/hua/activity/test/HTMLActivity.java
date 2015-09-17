package com.hua.activity.test;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hua.R;
import com.hua.bean.MyObject;

@SuppressLint("JavascriptInterface")
public class HTMLActivity extends Activity {
	private WebView webView = null;
	public Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        
        webView = (WebView)this.findViewById(R.id.webView);
        //设置字符集编码
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebChromeClient(new MyWebViewChromeClick());
        webView.setWebViewClient(new MyWebViewClick());
        //开启JavaScript支持
        webView.getSettings().setJavaScriptEnabled(true);
        //传递一个Java对象，同时给他命名，这个对象可以在js中调用这个对象的方法
        webView.addJavascriptInterface(new MyObject(HTMLActivity.this,handler), "myObject");
        //加载assets目录下的文件
        String url = "file:///android_asset/index.html";
//		String url = "file:///android_asset/duibashare.html";
        webView.loadUrl(url);
    }
    
    
    class MyWebViewClick extends WebViewClient{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			
			super.onPageFinished(view, url);
		}
		
		
    	
    }
    
    class MyWebViewChromeClick extends WebChromeClient{

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			super.onProgressChanged(view, newProgress);
		}

		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			// TODO Auto-generated method stub
			super.onShowCustomView(view, callback);
		}

		@Override
		public void onHideCustomView() {
			// TODO Auto-generated method stub
			super.onHideCustomView();
		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			// TODO Auto-generated method stub
			result.confirm();
			return super.onJsAlert(view, url, message, result);
		}

		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {
			// TODO Auto-generated method stub
			return super.onJsConfirm(view, url, message, result);
		}
    	
    }
    
}
