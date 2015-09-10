package com.hua.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by sundh on 2015/9/10.
 */
public class MMApplication extends Application {

    private static MMApplication mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    public static Context getAppContext(){
        return  mAppContext;
    }

}
