package com.hua.activity.taiwanAd;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.hua.ads.TanxADUtil;
import com.hua.utils.ACacheUtil;
import com.hua.utils.AppTool;
import com.intowow.sdk.SplashAdActivity;
import com.intowow.sdk.WebViewActivity;
import com.umeng.analytics.AnalyticsConfig;

/**
 * if your project has already used another Application class,
 * then you can copy these code to your Application class too
 */
public class BaseApplication extends Application {
    ///change foe the MMApplication
    private static BaseApplication mAppContext;
    private String platform_id;
    public static long TimeErrand = 0;
//end


    //**********************TW_Ad**************************
    //	common setting
    //
    private static final long SESSION_END_TIMEOUT = 2000L;
    private static final long REQUEST_AD_DELAY_TIME = 1000L;
    private static final boolean IS_SUPPORT_LIFECYCLE_CALLBACKS = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH);
    private static BaseApplication mInstance = null;
    private int mActiveReferenceCount = 0;
    private String mActiveActivityName = null;
    private Handler mHandler = null;
    private boolean mIsEnterFromBackground = true;
    private boolean mIsShowingSplashAd = false;


    //************************************************
    //	Enter Foreground Splash Ad
    //
    private static final String PLACEMENT = "OPEN_SPLASH_FOREGROUND";
//    private SplashAD mEnterForegroundSplashAd = null;

    //XXX@OpenSplash-FILTER_ACTIVITY_NAMES@#OpenSplash-FILTER_ACTIVITY_NAMES#
    //	TODO
    //	you can modify this String array.
    //	these classes added here will not show
    //	the enter foreground splash ad
    //
    private final static String[] FILTER_ACTIVITY_NAMES = new String[]{

            //	================================================
            //	replace these classes to your launcher activity
            //	or any other class which you don't want to show the splash ad
            //
            SingleDeferAdapterActivity.class.getName(),

            //=====  do not remove SDK's activity
            //
            SplashAdActivity.class.getName(),// this is SDK's activity, don't remove it
            WebViewActivity.class.getName()// this is SDK's activity, don't remove it
    };
    //end

    // ===================================
    // Override Method
    // ===================================
    @Override
    public void onCreate() {

        super.onCreate();

///change foe the MMApplication
        mAppContext = this;


        // 初始化缓存
        ACacheUtil.initialize(getApplicationContext());
        //
        TanxADUtil.initTanxAD();

        ////end

/*----------------------------台湾AD*/
        mHandler = new Handler();
        mInstance = this;

        //	register every activity's life-cycle here,
        //
        if (IS_SUPPORT_LIFECYCLE_CALLBACKS) {
            mInstance.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

                @Override
                public void onActivityCreated(Activity activity,
                                              Bundle savedInstanceState) {
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    mActiveReferenceCount++;
                }

                @Override
                public void onActivityResumed(Activity activity) {

                    mActiveReferenceCount++;

                    mActiveActivityName = activity.getClass().getName();

                    if (mHandler != null) {
                        mHandler.removeCallbacks(mEnterBackgroundTimer);
                    }

                    if (mIsEnterFromBackground) {

                        mIsEnterFromBackground = false;
                        //	XXX
                        //	request the enter foreground splash ad here
                        //
//                        requestEnterForegroundSplashAd(activity);
                    }

                }

                @Override
                public void onActivityPaused(Activity activity) {
                    mActiveReferenceCount--;
//                    checkBackground();
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    mActiveReferenceCount--;
//                    checkBackground();
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity,
                                                        Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                }
            });
        }

    }

//    private void checkBackground() {
//        if (mHandler != null && mActiveReferenceCount == 0) {
//            mHandler.removeCallbacks(mEnterBackgroundTimer);
//            mHandler.postDelayed(mEnterBackgroundTimer, SESSION_END_TIMEOUT);
//        }
//    }

    // ===================================
    // 	Enter Foreground method
    // ===================================

    /**
     * this timer will check if the APP is in the background
     */
    private Runnable mEnterBackgroundTimer = new Runnable() {
        @Override
        public void run() {
            mIsEnterFromBackground = (mActiveReferenceCount == 0);

            if (!mIsShowingSplashAd) {
                //	release the ad during the REQUEST_AD_DELAY_TIME
                //
//                releaseEnterForegroundSplashAd();
            }
        }
    };

//    private void requestEnterForegroundSplashAd(final Activity requestActivity) {
//
////        if (mEnterForegroundSplashAd != null) {
////            //	the ad had been requested by the previous Activity
////            //
////            return;
////        }
//
//        //	check if this activity does not need to request the enter foreground ad
//        //	such as the splash ad
//        //
//        for (String filerActivityName : FILTER_ACTIVITY_NAMES) {
//            if (filerActivityName.equals(requestActivity.getClass().getName())) {
//                return;
//            }
//        }
//
//        if (mHandler != null) {
//            //	when the APP enter foreground,
//            //	in order not to let the user see the black screen that moment,
//            //	we can use Handler.postDelayed() for requesting the splash ad later
//            //
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    //	request the splash ad
//                    //
////                    mEnterForegroundSplashAd = I2WAPI.requesSingleOfferAD(requestActivity, PLACEMENT);
////                    if (mEnterForegroundSplashAd != null) {
////                        mEnterForegroundSplashAd.setListener(new SplashAdListener() {
////
////                            @Override
////                            public void onLoaded() {//XXX
////
////                                if ((mActiveReferenceCount == 0)) {
////                                    releaseEnterForegroundSplashAd();
////                                    return;
////                                }
////
////                                if (requestActivity == null || requestActivity.isFinishing()) {
////                                    releaseEnterForegroundSplashAd();
////                                    return;
////                                }
////
////                                //	check if the active Activity is the same
////                                //	with the request Activity
////                                //
////                                if (!mActiveActivityName.equals(requestActivity.getClass().getName())) {
////                                    releaseEnterForegroundSplashAd();
////                                    return;
////                                }
////
////                                //	check if the ad had been released by the mEnterBackgroundTimer
////                                //
////                                if (mEnterForegroundSplashAd != null) {
////                                    mIsShowingSplashAd = true;
////
////                                    //	start the splash ad activity
////                                    //
////                                    mEnterForegroundSplashAd.show(
////                                            R.anim.slide_in_from_bottom,
////                                            R.anim.no_animation);
////                                } else {
////                                    //	the ad has been released
////                                    //
////                                }
////                            }
////
////                            @Override
////                            public void onLoadFailed() {
////                                releaseEnterForegroundSplashAd();
////                            }
////
////                            @Override
////                            public void onClosed() {
////                                mIsShowingSplashAd = false;
////                                // be sure to release the splash ad here
////                                //
////                                releaseEnterForegroundSplashAd();
////                            }
////                        });
////                    } else {
////                        //	the splash ad is not serving now
////                        //
////                    }
//                }
//            }, REQUEST_AD_DELAY_TIME);
//        }
//
//    }

//    private void releaseEnterForegroundSplashAd() {//XXX
////        if (mEnterForegroundSplashAd != null) {
////            mEnterForegroundSplashAd.release();
////            mEnterForegroundSplashAd = null;
////        }
//
//    }

///change foe the MMApplication

    public static Context getAppContext() {
        return mAppContext;
    }

    public void getChannel() {
        platform_id = AppTool.getChannel(this);
        if (TextUtils.isEmpty(platform_id)) {
            platform_id = AppTool.getManifestChannelId(this);
        }
        Log.e("channel", platform_id);
        AnalyticsConfig.setChannel(platform_id);
    }

}
