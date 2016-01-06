package com.hua.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.hua.R;
import com.hua.activity.taiwanAd.BaseApplication;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Stack;

import dalvik.system.DexClassLoader;


/**
 * Activity跳转
 */
public class ActivityManager {

    private static ActivityManager am = null;
    public final static int REQUEST_CODE = 100;
    public final static int LOGIN_CODE = 500;
    public final static int MODIFY_CODE = 600;//修改用户请求requestCode
    public static final int postsListResultCode = 1001;
    public Stack<Activity> activityStack;
    public void getCodeFinish(String code){};

    public ActivityManager() {
        activityStack = new Stack<Activity>();
    }

    public static synchronized ActivityManager getManager() {
        if (am == null) {
            am = new ActivityManager();

        }
        return am;
    }

    public void addActivity(Activity activity){
        if(activityStack!=null){
            activityStack.add(activity);
        }
    }

    public void goTo(Activity self, Class<? extends Activity> cls) {
        Intent it = new Intent(self, cls);
        self.startActivity(it);
        self.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public void goTo(Activity self, Intent it) {
//		activities.add(target);
        self.startActivity(it);
//        self.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public void goFoResult(Activity self, Intent it) {
//		activities.add(target);
        self.startActivityForResult(it, REQUEST_CODE);
        self.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public void goFoResult(Activity self, Intent it, int RequestCode) {
//		activities.add(target);
        self.startActivityForResult(it, RequestCode);
        self.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    /**orientation 0 代表水平方向动画，1 代表垂直方向*/
    public void goFoResult(Fragment fragment, Activity self,Intent it, int RequestCode,int orientation) {
//		activities.add(target); Activity.RESULT_FIRST_USER
        fragment.startActivityForResult(it,RequestCode );
        if(orientation == 0){
            self.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }if(orientation == 1){
            self.overridePendingTransition(R.anim.activity_vertical_in, R.anim.activity_vertical_out);
        }
    }

    public void ExitApp() {
//        PassportUtil.stop(MMApplication.getAppContext());
//        Glide.get(MMApplication.getAppContext()).clearMemory();
//        for (Activity activity:activityStack){
//            activity.finish();
//        }
//        activityStack.clear();
//        activityStack = null;
////    	MMApplication.default_moduleid = 0;
////        UserInfoUtil.getUserInfo(MMApplication.getAppContext()).destoryUser();
////        if(MMApplication.initBean!=null){
////            MMApplication.initBean.setUpload_pic_url(null);
////        }
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }

//    public void gotoWebView(Activity activity, Intent intent) {
//        if (intent.getStringExtra("urlpath").startsWith("xiaoshuxiong.com")) {
//            intent.setClass(activity, BuyWapActivity.class);
//        } else {
//            intent.setClass(activity, WebViewDetail.class);
//        }
//        activity.startActivity(intent);
//    }

    /**
     * 小树熊code请求
     * @param activity
     */
//    public void requestToXiaoshuxiong(final Activity activity) {
//        UserInfoUtil userInfoUtil =UserInfoUtil.getUserInfo(activity);
////        final LoadDialog loadDialog = new LoadDialog(activity);
////        loadDialog.show();
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("hash", userInfoUtil.getHash());
//        params.put("uid", userInfoUtil.getUid());
//        params.put("key", "android");
//        params.put("deviceuid", PhoneInfoUtil.getInstance(activity).getDeviceId());
//        params.put("sign", Encrypt2.genToken(params,Encrypt2.Algorithm2, Encrypt2.KEY_MM_AI_PASSPORT));
//        String url = StringUtil.makedburlstr(UrlPath.AISHOPPING_GETCODE, params);
//        NewRequest request = new NewRequest(url,String.class,new ResponseListener<String>(activity){
//            @Override
//            protected void onPtSucc(String url, String json) {
//                try {
//                    JSONObject jsonObject = new JSONObject(json);
//                    String code = jsonObject.getString("code");
//                    getCodeFinish(code);
//                } catch (JSONException e) {
////					e.printStackTrace();
//                }
//                super.onPtSucc(url, json);
//            }
//
//            @Override
//            public void onNetworkComplete() {
////                loadDialog.dismiss();
//                super.onNetworkComplete();
//            }
//        }).setShowToast(false).setHaveToken(true);
//        RequestManager.getInstance(activity).addToRequestQueue(request);
//    }



    public Activity newActivity(Class<?> clazz, Context context, IBinder token,
                                Application application, Intent intent, ActivityInfo info,
                                CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance)
            throws InstantiationException, IllegalAccessException {

        String className = "";
        Activity newActivity = null;
        if (intent.getComponent() != null) {
            className = intent.getComponent().getClassName();
        }


//        DexClassLoader dexClassLoader =

//        boolean shouldInterrupted = !BaseApplication.isDexAvailable();
//        if (BaseApplication.sIsDexAvailable.get() || mByPassActivityClassNameList.contains(className)) {
//            shouldInterrupted = false;
//        }
//        if (shouldInterrupted) {
//            intent = new Intent(mContext, WaitingActivity.class);
//            newActivity = mBase.newActivity(clazz, context, token,
//                    application, intent, info, title, parent, id,
//                    lastNonConfigurationInstance);
//        } else {
//            newActivity = mBase.newActivity(clazz, context, token,
//                    application, intent, info, title, parent, id,
//                    lastNonConfigurationInstance);
//        }
        return newActivity;
    }


    /**
     * 设置状态栏背景状态
     *
     * 使用：
     */
    public void initSystemBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(true,activity);

        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setStatusBarTintResource(R.color.bar_color);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
