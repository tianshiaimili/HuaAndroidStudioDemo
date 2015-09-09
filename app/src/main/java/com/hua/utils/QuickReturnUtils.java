package com.hua.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by etiennelawlor on 6/28/14.
 */
public class QuickReturnUtils {

    private static TypedValue sTypedValue = new TypedValue();
    private static int sActionBarHeight;
    private static Dictionary<Integer, Integer> sListViewItemHeights = new Hashtable<Integer, Integer>();

    public static int dp2px(Context context, int dp) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);

        return (int) (dp * displaymetrics.density + 0.5f);
    }

    public static int px2dp(Context context, int px) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);

        return (int) (px / displaymetrics.density + 0.5f);
    }

    public static int getScrollY(ListView lv) {
        View c = lv.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int top = c.getTop();

        int scrollY = -top + firstVisiblePosition * c.getHeight();
        return scrollY;
    }

    public static int getScrollY(AbsListView lv) {
//    	LogUtils.d("the lv=="+(lv));
//    	LogUtils.i("the lv=="+(lv.getChildCount()));
        View c = lv.getChildAt(0);//拿到的是当前屏幕可见的第一个item，而不是listview的第一个
//        LogUtils.e("the c=="+(c));
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int scrollY = -(c.getTop());
        LogUtils.d("the c.getTop()=="+(c.getTop()));
//        int scrollY = 0;



        sListViewItemHeights.put(lv.getFirstVisiblePosition(), c.getHeight());
        LogUtils.e("the firstVisiblePosition=="+(firstVisiblePosition));
//        if(scrollY>0)
//            Log.d("QuickReturnUtils", "getScrollY() : -(c.getTop()) - "+ -(c.getTop()));
//        else
//            Log.i("QuickReturnUtils", "getScrollY() : -(c.getTop()) - "+ -(c.getTop()));

        if(scrollY<0)
            scrollY = 0;

        for (int i = 0; i < firstVisiblePosition; ++i) {
//            Log.d("QuickReturnUtils", "getScrollY() : i - "+i);

//            Log.d("QuickReturnUtils", "getScrollY() : sListViewItemHeights.get(i) - "+sListViewItemHeights.get(i));

            if (sListViewItemHeights.get(i) != null) // (this is a sanity check)
                scrollY += sListViewItemHeights.get(i); //add all heights of the views that are gone

        }

        LogUtils.d( "getScrollY() : scrollY - "+scrollY);

        return scrollY;
    }


    public static int getActionBarHeight(Context context) {
        if (sActionBarHeight != 0) {
            return sActionBarHeight;
        }

        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, sTypedValue, true);
        sActionBarHeight = TypedValue.complexToDimensionPixelSize(sTypedValue.data, context.getResources().getDisplayMetrics());
        return sActionBarHeight;
    }
}
