package com.hua.activity.input;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.hua.R;

public class InputMethodActivity extends Activity {
	private static final String URL = "http://www.apkbus.com";
	private EditText edittext;
	private WebView webview;

/*	private OnKeyListener onKeyListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_ENTER){
				隐藏软键盘
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if(inputMethodManager.isActive()){
					inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
				}

				edittext.setText("success");
				webview.loadUrl(URL);

				return true;
			}
			return false;
		}
	};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_text_layout);

		edittext = (EditText)findViewById(R.id.edittext);
		webview = (WebView)findViewById(R.id.webview);
		webview.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});


//		edittext.setOnKeyListener(onKeyListener);
		/*输入框监听事件*/
		edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				/*判断是否是“GO”键*/
				if(actionId == EditorInfo.IME_ACTION_GO){
					/*隐藏软键盘*/
					InputMethodManager imm = (InputMethodManager) v
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					if (imm.isActive()) {
						imm.hideSoftInputFromWindow(
								v.getApplicationWindowToken(), 0);
					}

					edittext.setText("success");
					webview.loadUrl(URL);

					return true;
				}
				return false;
			}
		});
	}

/*	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
			隐藏软键盘
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if(inputMethodManager.isActive()){
				inputMethodManager.hideSoftInputFromWindow(CustomListViewActivity.this.getCurrentFocus().getWindowToken(), 0);
			}
			
			edittext.setText("success");
			webview.loadUrl(URL);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
*/
}
