package com.hua.activity;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hua.ads.TanxADUtil;
import com.hua.utils.ACacheUtil;
import com.hua.utils.AppTool;
import com.umeng.analytics.AnalyticsConfig;

/**
 * Created by sundh on 2015/9/10.
 */
public class MMApplication extends Application {

    private static MMApplication mAppContext;
    private String platform_id;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;


        // 初始化缓存
        ACacheUtil.initialize(getApplicationContext());
        //
        TanxADUtil.initTanxAD();
    }

    public static Context getAppContext(){
        return  mAppContext;
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
