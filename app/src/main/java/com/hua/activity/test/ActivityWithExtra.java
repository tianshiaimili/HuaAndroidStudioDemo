package com.hua.activity.test;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hua.R;
import com.hua.utils.AnimationUtil;
import com.hua.utils.LogUtils;
import com.hua.utils.StringUtil;
import com.hua.utils.secret.DES;
import com.hua.utils.secret.DESSecret;
import com.hua.view.OnWheelChangedListener;
import com.hua.view.OnWheelScrollListener;
import com.hua.view.WheelView;
import com.hua.view.wheelview_adapter.NumericWheelAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EActivity(R.layout.activity_with_extra)
public class ActivityWithExtra extends com.hua.activity.taiwanAd.BaseActivity {

    public static final String MY_STRING_EXTRA = "myStringExtra";
    public static final String MY_DATE_EXTRA = "myDateExtra";
    public static final String MY_INT_EXTRA = "myIntExtra";

    @ViewById
    TextView extraTextView;

    @ViewById
    WheelView passw_1;

    @ViewById
    WheelView passw_2;

    @ViewById
    TextView share_tv1;

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
        extraTextView.setText("123"+myMessage + " " + myDate + " " + unboundExtra + " " + classCastExceptionExtra);
        extraTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        Spannable spannable = new SpannableStringBuilder("1"+extraTextView.getText());
        Drawable drawable = getResources().getDrawable(R.drawable.vote_tip);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannable.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        extraTextView.setText(spannable);

        initWheel();
        share_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                passw_2.scroll(25, 2000);
//                passw_1.scroll(-20, 2000);
                passw_1.setCurrentItem(8,true);

                DESSecret.testSecret();

                DES.test();

                StringUtil.setData("啦啦啦mm");

            }
        });

    }

    private PopupWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.sign_pw, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView light = (ImageView) contentView.findViewById(R.id.light);
        ImageView gold = (ImageView) contentView.findViewById(R.id.gold);
        TextView score = (TextView) contentView.findViewById(R.id.score);
        TextView prcent = (TextView) contentView.findViewById(R.id.prcent);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(extraTextView, Gravity.CENTER_VERTICAL, 0, 0);
        AnimationUtil.signAnima(this, light, gold, score, prcent);

    }

    /**
     * Initializes wheel
     */
    private void initWheel() {
        passw_1.setViewAdapter(new NumericWheelAdapter(this, 0, 9));
        passw_1.setCurrentItem((int)(Math.random() * 10));


        passw_1.addChangingListener(changedListener);
        passw_1.addScrollingListener(scrolledListener);
        passw_1.setCyclic(true);
        passw_1.setInterpolator(new AnticipateOvershootInterpolator());

        passw_2.setViewAdapter(new NumericWheelAdapter(this, 0, 9));
//        passw_2.setCurrentItem((int)(Math.random() * 10));
        passw_2.setCurrentItem(0);
        passw_2.addChangingListener(changedListener);
        passw_2.addScrollingListener(scrolledListener);
        passw_2.setCyclic(true);
        passw_2.setInterpolator(new AnticipateOvershootInterpolator());

    }

    // Wheel scrolled flag
    private boolean wheelScrolled = false;

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            updateStatus();
        }
    };

    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
                updateStatus();
            }
        }
    };

    /**
     * Updates entered PIN status
     */
    private void updateStatus() {
            extraTextView.setText("Congratulation!");
    }

}
