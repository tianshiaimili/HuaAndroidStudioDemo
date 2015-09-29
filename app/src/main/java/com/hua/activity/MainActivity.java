package com.hua.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;

import com.hua.R;
import com.hua.activity.duiba.DuiBaActivity;
import com.hua.activity.js.HTMLActivityDemo;
import com.hua.activity.test.AT_Activity;
import com.hua.activity.test.ActivityWithExtra_;
import com.hua.activity.test.ChoiceInterest3_;
import com.hua.activity.test.ChoiceInterest_;
import com.hua.activity.test.CreamDetail_;
import com.hua.activity.test.CreditActivity_;
import com.hua.activity.test.HTMLActivity;
import com.hua.activity.test.MultipleItemsList;
import com.hua.activity.test.PersonFragmentNew_;
import com.hua.activity.test.PostsActvity_;
import com.hua.activity.test.SpannableActivity;
import com.hua.activity.test.TestEmojicoTextView_;
import com.hua.activity.test.WebViewPlayer_;
import com.hua.activity.test.XuanFuActivity;
import com.hua.utils.ActivityManager;
import com.hua.utils.LogUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

/***test合拼*/
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {


	public static final String MY_STRING_EXTRA = "myStringExtra";
	public static final String MY_DATE_EXTRA = "myDateExtra";
	public static final String MY_INT_EXTRA = "myIntExtra";

	@ViewById
	Button test1,test2,test3,test4;


	@Extra(MY_INT_EXTRA)
	String classCastExceptionExtra = "啦啦啦啦啦";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.i("onCreate----");
	}



	@AfterInject
	void afterInjectMethod(){
		LogUtils.d("afterInjectMethod----");

	}

	@AfterViews
	void AfterViewsMethod(){
		LogUtils.d("AfterViewsMethod----");

		Html.fromHtml(getString(R.string.test1));
//		test1.setText(Html.fromHtml(getString(R.string.test1)));
		test1.setText(Html.fromHtml("<b> &lt;喀喀喀 &gt; </b> 换吧换吧 <\n>可以的" +
				"<a href='http://baidu.com'>clickMe</a>"));
		test2.setText(getString(R.string.test2));
		int deny = (int)getResources().getDisplayMetrics().densityDpi;
		test3.setText("" + deny);



	}

	@Override
	protected void onStart() {
		LogUtils.i("onStart----");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		LogUtils.i("onRestart----");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		LogUtils.i("onResume----");
		super.onResume();
	}

	@Override
	protected void onPause() {
		LogUtils.i("onPause----");
		super.onPause();
	}

	@Override
	protected void onStop() {
		LogUtils.i("onStop----");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		LogUtils.i("onDestroy----");
		super.onDestroy();
	}

	@Click
	void test1(){

		Intent intent = new Intent(this,ChoiceInterest_.class);
		startActivity(intent);

	}


	@Click
	void test2(){

		Intent intent = new Intent(this,ActivityWithExtra_.class);
		intent.putExtra(MY_STRING_EXTRA, "from AT_Activity");
		intent.putExtra(MY_DATE_EXTRA, new Date());
		startActivity(intent);

	}

	@Click
	void test3(){

//		Intent intent = new Intent(this,PersonFragment_.class);
//		startActivity(intent);

	}


	@Click
	void test4(){

		Intent intent = new Intent(this,WebViewPlayer_.class);
		startActivity(intent);

	}

	@Click
	void test5(){

		Intent intent = new Intent(this,MultipleItemsList.class);
		startActivity(intent);

	}


	@Click
	void test6(){

		Intent intent = new Intent(this,CreamDetail_.class);
		startActivity(intent);

	}

	@Click
	void test7(){

		Intent intent = new Intent(this,ChoiceInterest3_.class);
		startActivity(intent);

	}

	@Click
	void test8(){

		Intent intent = new Intent(this,PostsActvity_.class);
		startActivity(intent);

	}


	@Click
	void test9(){

		Intent intent = new Intent(this,CreditActivity_.class);
		startActivity(intent);

	}

	@Click
	void test10(){

		Intent intent = new Intent(this,HTMLActivity.class);
		startActivity(intent);

	}


	@Click
	void test11(){

		Intent intent = new Intent(this,PersonFragmentNew_.class);
		startActivity(intent);

	}

	@Click
	void test12(){

		Intent intent = new Intent(this,SpannableActivity.class);
		startActivity(intent);

	}

	@Click
	void test13(){

		Intent intent = new Intent(this,AT_Activity.class);
		startActivity(intent);

	}

	@Click
	void test14(){

		Intent intent = new Intent(this,XuanFuActivity.class);
		startActivity(intent);

	}

	@Click
	void test15(){
		Intent intent = new Intent(this,TestEmojicoTextView_.class);
		ActivityManager.getManager().goTo(MainActivity.this, intent);

	}

	@Click
	void test16(){
		Intent intent = new Intent(this,HTMLActivityDemo.class);
		ActivityManager.getManager().goTo(MainActivity.this, intent);

	}

	@Click
	void test17(){
		Intent intent = new Intent(this,DuiBaActivity.class);
		ActivityManager.getManager().goTo(MainActivity.this, intent);

	}

}
