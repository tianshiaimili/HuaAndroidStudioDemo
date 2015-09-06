package com.hua.received;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by sundh on 2015/9/6.
 */
public class TestPush {

    public static void test(Context context){

        String deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

    }

}
