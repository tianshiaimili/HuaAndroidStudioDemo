package com.hua.utils;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.activity.MMApplication;


/**
 * @author tangzc
 */
public class ToastUtil {

    public final static String Login_TYPE1 = "1";
    public final static String Login_TYPE2 = "2";
    public final static String Login_TYPE3 = "3";
    public final static String Login_TYPE4 = "4";
    public final static String Login_TYPE5 = "5";
    public final static String Login_TYPE6 = "6";
    public final static String Login_TYPE7 = "7";
    public final static String Login_TYPE8 = "8";
    public final static String Login_TYPE9 = "9";
    public final static String Login_TYPE10 = "10";
    public final static String Login_TYPE11 = "11";
    public final static String Login_TYPE12 = "12";

    public static void showConnFail(Context ctx) {
        if (ctx != null) {
            View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
            Toast toast = new Toast(ctx);
            toast.setView(toastRoot);
            TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
            tv.setText(ctx.getResources().getText(R.string.network_no_connect));
            toast.show();
        }
    }

    public static void showToast(Context ctx, String msg) {
        if (ctx == null) {
            return;
        }
        View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
        Toast toast = new Toast(ctx);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
        tv.setText(msg);
        toast.show();
    }
    public static void showToast(String msg) {
        Context ctx = MMApplication.getAppContext();
        if (ctx == null) {
            return;
        }
        View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
        Toast toast = new Toast(ctx);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
        tv.setText(msg);
        toast.show();
    }
    public static void showToast(int msg) {
        Context ctx = MMApplication.getAppContext();
        if (ctx == null||msg==0) {
            return;
        }
        View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
        Toast toast = new Toast(ctx);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
        tv.setText(ctx.getResources().getString(msg));
        toast.show();
    }

    public static void showToast(Context ctx, int res) {
        if (ctx == null) {
            return;
        }
        View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
        Toast toast = new Toast(ctx);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
        tv.setText(ctx.getResources().getString(res));
        toast.show();
    }

//    public static void showReportToast(Context ctx, String msg) {
//        if (ctx == null) {
//            return;
//        }
//        View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.report_toast, null);
//        Toast toast = new Toast(ctx);
//        toast.setView(toastRoot);
//        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
//        tv.setText(msg);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//    }

    public static void showToastByBackGround(Context ctx, String msg, int height) {
        if (ctx == null) {
            return;
        }
        showToastByBackGround(ctx, msg, height, R.drawable.message_line, R.color.white);
    }

    public static void showToastByBackGround(Context ctx, String msg, int height, int bg, int color) {
        View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        toastRoot.setLayoutParams(layoutParams);
        Toast toast = new Toast(ctx);
        toast.setView(toastRoot);
        toastRoot.setBackgroundColor(ctx.getResources().getColor(android.R.color.transparent));
        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
        LayoutParams teLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        teLayoutParams.leftMargin = 5;
        teLayoutParams.rightMargin = 5;
        tv.setLayoutParams(teLayoutParams);
        tv.setTextColor(19);
        tv.setBackgroundResource(bg);
        tv.setText(getMessAgeInfo(msg));
        tv.setTextColor(ctx.getResources().getColor(color));
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, height);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToastByBackGround(Context ctx, String msg) {
        View toastRoot = LayoutInflater.from(ctx).inflate(R.layout.toast, null);
        Toast toast = new Toast(ctx);
        toast.setView(toastRoot);
        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
        tv.setTextColor(19);
        toastRoot.setBackgroundResource(R.drawable.message_line);
        tv.setText(getMessAgeInfo(msg));
        tv.setTextColor(Color.parseColor("#75886B"));
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 68);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private static String getMessAgeInfo(String type) {
        if (Login_TYPE1.equals(type)) {
            return "美妈，要先登录才能发表新话题哟!";
        } else if (Login_TYPE2.equals(type)) {
            return "美妈，要先登录才能发表回复哟!";
        } else if (Login_TYPE3.equals(type)) {
            return "美妈，要先登录才能关注TA哟!";
        } else if (Login_TYPE4.equals(type)) {
            return "美妈，要先登录才能发送短消息哟!";
        } else if (Login_TYPE5.equals(type)) {
            return "美妈，要先登录才能添加圈子哟!";
        } else if (Login_TYPE6.equals(type)) {
            return "美妈，要先登录才能关注圈子哟!";
        } else if (Login_TYPE7.equals(type)) {
            return "美妈，还不知道你的城市哟，设置一下吧!";
        } else if (Login_TYPE8.equals(type)) {
            return "美妈，要先登录才能收藏哟!";
        } else if (Login_TYPE9.equals(type)) {
            return "美妈，要先登录才能喜欢哟!";
        } else if (Login_TYPE10.equals(type)) {
            return "美妈，要先登录才能评论哟!";
        } else if (Login_TYPE11.equals(type)) {
            return "美妈，要先登录才能举报哟!";
        } else if (Login_TYPE12.equals(type)) {
            return "美妈，要先登录才能报名活动哟!";
        } else {
            return type;
        }
    }

}
