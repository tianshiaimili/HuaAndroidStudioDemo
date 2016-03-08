package com.hua.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hua.R;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 */
public class PublicMethod {

    public static final String DESCRIPTOR = "hua";
    public static UMSocialService controller = UMServiceFactory.getUMSocialService(DESCRIPTOR, RequestType.SOCIAL);

    public static QQtencentUtil util;
    public static QQtencentUtil util2;

    public static boolean isContextDestroy = false;

    public static QQtencentUtil getUtil(Context context) {
        if (util == null) {
            util = new QQtencentUtil(context);
        }
        return util;
    }

    public static QQtencentUtil getUtil2(Context context) {
        if (util2 == null) {
            util2 = new QQtencentUtil(context);
        }
        return util2;
    }

    /**
     * 添加积分。
     *
     * @param context
     * @param view    action = true代表帖子。false代表应用
     */
//    public static void addScorce(final Context context, final View view, boolean isAPPShare) {
//        HashMap<String, Object> param = new HashMap<String, Object>();
//        param.put("uid", PreferenceUtils.getUserSharePref(context, PreferenceUtils.Uid));
//        param.put("t", String.valueOf(new Date().getTime() / 1000));
//        if (isAPPShare) {
//            param.put("action", "share_mamaquan_app");
//        } else {
//            param.put("action", "share_mamaquan_threa");
//        }
//        final String url = RequestHelper.buildCommonToken(UrlPath.SCORE, param);
//
//        NewRequest mmRequest = new NewRequest(url, ScoreBean.class, new ResponseListener<ScoreBean>(context) {
//            @Override
//            protected void onPtSucc(String url, ScoreBean bean) {
//                super.onPtSucc(url, bean);
//                String credit = null;
//                if (bean != null) {
//                    credit = bean.getAddcredit();
//                }
//                if (!StringUtil.isNull(credit)&& !"0".equals(credit)) {
//                    if (!isContextDestroy) {
//                        new ScoreUtil((Activity) context).showScore(view, credit);
//                    }
//                }
//            }
//        }).setShowToast(false);
//        RequestManager.getInstance(context).addToRequestQueue(mmRequest, "PublicMethod_addScorce");
//    }


    /**
     * 初始化数据
     *
     * @param context
     */
//    public static synchronized void requestInit(final Context context) {
//        Map<String,String> parmap = new HashMap<String,String>();
//        parmap.put("channelid", MMApplication.getAppContext().getPlatform_id());
//        String url = RequestHelper.buildCommonToken(UrlPath.SERVICETIME,parmap);
//        NewRequest mrequest = new NewRequest(url, InitBean.class,new ResponseListener<InitBean>(context) {
//            @Override
//            protected void onPtSucc(String url, InitBean bean) {
//
//                if (bean != null) {
//                    MMApplication.initBean = bean;
////                    MMApplication.initBean.setLauch_url("http://img1.126.net/channel6/017256/8001280_0731_sh.jpg?686723");
//                    if (!StringUtil.isNull(bean.getApi_time())) {
//                        PreferenceUtils.saveTimeErrand(context, bean.getApi_time());
//                        MMApplication.TimeErrand = 0;
//                    }
//                    if (bean.getFilter_mmqfids() != null && bean.getFilter_mmqfids().size() != 0) {
//                        ActionContace.uploadFids = bean.getFilter_mmqfids();
//                    }
//                }
//                super.onPtSucc(url, bean);
//
//            }
//
//            @Override
//            protected void onFail(int errorType, String errorDesc) {
////                super.onFail(errorType, errorDesc);
//            }
//
//            @Override
//            protected void onPtError(String url, Result.ErrorMsg errorMsg) {
////                super.onPtError(url, errorMsg);
//            }
//        });
//        RequestManager.getInstance(context).getRequestQueue().add(mrequest);
//
//    }

//    public static void clearUserCache(Activity context) {
//        PreferenceUtils.setUserSharePref(context, PreferenceUtils.Pic, "");
//        PreferenceUtils.setUserSharePref(context, PreferenceUtils.CITYID, "");
//        // SharedPUtil.saveMinInfo(context, new UserIndexInfoBean());
////		PreferenceUtils.setDefalutSharePref(context, PreferenceUtils.BB_type,
////				"");
//        ACacheUtil.clear(ACacheKey.CACHE_MINE_INFO);
//        MMApplication.isshowhead = true;
//        if (context instanceof BaseActivity) {
//            UserInfoUtil.getUserInfo(context).clearUser();
//        }
//        UserInfoUtil userInfo = UserInfoUtil.getUserInfo(context);
//        String uid = userInfo.getUid();
//        userInfo.getHash();
//        userInfo.setUid(uid);
//        userInfo.getCitySite();
//
//        PassportUtil.getPassPort(context);
//        PushPrefUtil.makeZero(context, PushReceiver.TYPE_ALL);
//        // MQTT操作
//        PushPrefUtil.sendMqttBroadcast(context);
//        PushRequestUtil.registerAndConnectionMqtt(context);
//    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    // 替换、过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        str = ToDBC(str);
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replace("，", ",").replace("。", ".");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 获取需要的屏幕宽度
     *
     * @param ctx
     * @param cutDown 需要减少的距离
     * @return px
     */
    public static int getWidthPixels(Activity ctx, int cutDown) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 计算两边的距离
        return dm.widthPixels - dip2px(ctx, cutDown);
    }

    /**
     * get device width
     *
     * @param appContext
     * @return context==null will return 0
     */
    public static int getScreenWidth(Context appContext) {
        if (appContext == null) {
            return 0;
        }
        return appContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取需要的屏幕高度
     *
     * @param ctx 需要减少的距离
     * @return px
     */
    public static int getHegithPixels(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 计算两边的距离
        return dm.heightPixels;
    }


    /**
     * dip to px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px to dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

//    public static void showCollectTipWindow(final Context context, View parent, final String key) {
//        String str = PreferenceUtils.getDefalutSharePref(context, key);
//        if (!StringUtil.isNull(str)) {
//            return;
//        }
//        View collectTipView = LayoutInflater.from(context).inflate(R.layout.collect_tips_layout, null);
//        final PopupWindow collectTipWindow = new PopupWindow(collectTipView, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        collectTipWindow.setBackgroundDrawable(new BitmapDrawable());
//        collectTipWindow.setFocusable(true);
//        collectTipWindow.setOutsideTouchable(false);
//        collectTipWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
//        Button iKnow = (Button) collectTipView.findViewById(R.id.i_know);
//        iKnow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PreferenceUtils.setDefalutSharePref(context, key, key);
//                collectTipWindow.dismiss();
//            }
//        });
//    }


    /**
     * 隐藏帖子列表或者详情页中的返回顶部ico
     * @param context
     * @param iv_top_back
     */
    public static void hideBackView(Context context,View iv_top_back){
        if (iv_top_back.getVisibility() == View.VISIBLE) {
            Animation ad_previous_out = AnimationUtils.loadAnimation(
                    context, R.anim.ad_previous_out);
            iv_top_back.setAnimation(ad_previous_out);
            iv_top_back.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏帖子列表或者详情页中的返回顶部ico
     * @param context
     * @param iv_top_back
     */
    public static void showBackView(Context context,View iv_top_back){
        if (iv_top_back.getVisibility() != View.VISIBLE) {
            Animation ad_next_in = AnimationUtils.loadAnimation(
                    context, R.anim.ad_next_in);
            iv_top_back.setVisibility(View.VISIBLE);
            iv_top_back.setAnimation(ad_next_in);
        }
    }


}
