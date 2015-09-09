package com.hua.activity.test;


import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;

import com.hua.R;
import com.hua.utils.LogUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EActivity(R.layout.activity_with_extra)
public class ActivityWithExtra extends Activity {

	public static final String MY_STRING_EXTRA = "myStringExtra";
	public static final String MY_DATE_EXTRA = "myDateExtra";
	public static final String MY_INT_EXTRA = "myIntExtra";
          
	@ViewById
	TextView extraTextView;

	@Extra(MY_STRING_EXTRA)
	String myMessage;

	@Extra(MY_DATE_EXTRA)
	Date myDate;

	@Extra("unboundExtra")
	String unboundExtra = "如果这边赋值，则以这边为准";

	/**
	 * The logs will output a classcast exception, but the program flow won't be interrupted
	 */
	@Extra(MY_INT_EXTRA)
	String classCastExceptionExtra /*= "classCastExceptionExtraDefaultValue"*/;

	 int wifiString = ConnectivityManager.TYPE_WIFI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		LogUtils.e("the MY_STRING_EXTRA==" + intent.getStringExtra(MY_STRING_EXTRA));
		
	}
	
	@AfterViews
	protected void init() {
		extraTextView.setText(myMessage + " " + myDate + " " + unboundExtra + " " + classCastExceptionExtra);
	}

}
