package com.hua.ads;

import android.app.Activity;
import android.text.TextUtils;

import com.alimama.mobile.sdk.MmuSDK;
import com.alimama.mobile.sdk.config.FeedController;
import com.alimama.mobile.sdk.config.FeedProperties;
import com.alimama.mobile.sdk.config.LoopImageController;
import com.alimama.mobile.sdk.config.MMPromoter;
import com.alimama.mobile.sdk.config.MmuSDKFactory;
import com.hua.bean.CirclePostListBean;
import com.hua.bean.GDTnTanxADBean;
import com.hua.bean.HotLineImgBean;
import com.hua.bean.PostsListBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 阿里广告工具类
 */
public class TanxADUtil {

    private Activity activity;
    private static LoopImageController loopImageController;
    private LoopImageController.MMLargeImage largeImage;
    private static FeedController feedController;
    private static MmuSDK mmuSDK;
    public static String HotLineImgSlotId = ""; //60466
    public static String HotLineDataSlotId = ""; //60466
    public static String PostListSlotId = ""; //60466
    public static String PostDetailSlotId = "";
    private String mSlotId;
    private FeedController.MMFeed mmFeed;

    private int mMPromoterCount = 3;

    public TanxADUtil(Activity activity) {
        this.activity = activity;
    }

    public void setmSlotId(String mSlotId) {
        this.mSlotId = mSlotId;
    }

    public void setmMPromoterCount(int mMPromoterCount) {
        this.mMPromoterCount = mMPromoterCount;
    }

    public static void initTanxAD(){
        mmuSDK = MmuSDKFactory.getMmuSDK();
//        mmuSDK.init(MMApplication.getAppContext());
        //要先注册
//        MmuSDKFactory.registerAcvitity(MMHomeActivity.class);
//        MmuSDKFactory.registerAcvitity(CirclePostsList_.class);
//        MmuSDKFactory.registerAcvitity(CirclePostDetail.class);
//        MmuSDKFactory.registerAcvitity(PostsDetail.class);
//        MmuSDKFactory.registerAcvitity(SameCircleDetail.class);
//        MmuSDKFactory.registerAcvitity(SameCirclePosts_.class);
        feedController = FeedProperties.getMmuController();
        loopImageController = LoopImageController.newInstance();
    }

    public void requestFocusAD(){
        if(loopImageController != null) {
            loopImageController.init(HotLineImgSlotId);
            loopImageController.incubate();
        }
    }

    public HotLineImgBean getAdData(){
        largeImage = loopImageController.getProduct();
        if (largeImage != null) {
            List<MMPromoter> list = largeImage.getPromoters();
            if(list != null && list.size() >0){
                HotLineImgBean bean = new HotLineImgBean();
                bean.setPhonepic(list.get(0).getImg());
                bean.setIsad("2");
                bean.setSubject(list.get(0).getDescription());
                return bean;
            }
        }
        return null;
    }

    public void focusAdClick(){
        if(largeImage!=null)
        largeImage.clickOnPromoter(activity, largeImage.getPromoters().get(0));
    }

    public void focusAdReportImpression(){
        if(largeImage!=null)
        largeImage.reportImpression(activity, largeImage.getPromoters().get(0));
    }

    /**
     * 初始化推广位
     */
    public void requestFeedAD(){
        //如果插件加载失败，将返回null，在使用controller要判断是否为空
//        feedController = FeedProperties.getMmuController();
        FeedProperties prop = new FeedProperties(mSlotId);
        mmuSDK.attach(prop);
    }

        public GDTnTanxADBean getFeedAD(){
        if (feedController != null) {
            mmFeed = feedController.getProduct(mSlotId);
        }
        if (mmFeed == null) {
            return null;
        }

        List<MMPromoter> promoters = mmFeed.getPromoters();
        if (promoters.size() > mMPromoterCount) {
            promoters = promoters.subList(0, mMPromoterCount);
        }
        GDTnTanxADBean adBean = new GDTnTanxADBean();
        adBean.setPromoters(promoters);
        if (promoters != null) {
            ArrayList<String> adImgs = new ArrayList<String>();
            ArrayList<String> adPrices = new ArrayList<String>();
            for (MMPromoter p : promoters) {
                adImgs.add(p.getImg());
                if (TextUtils.isEmpty(String.valueOf(p.getPromoterPrice())) || p.getPromoterPrice() == 0) {
                    adPrices.add(p.getPrice());
                } else {
                    adPrices.add(String.valueOf(p.getPromoterPrice()));
                }
            }
            MMPromoter p = promoters.get(0);
            adBean.setAdtitle(p.getTitle());
            adBean.setAddescription(p.getDescription());
            adBean.setAdicon(p.getIcon());
            adBean.setAdimg(p.getImg());
            adBean.setAdimgs(adImgs);
            adBean.setAdprices(adPrices);
        }
            adBean.setIsWhatAD(1);
        return adBean;
    }

    public void adImgClick(GDTnTanxADBean bean, int position) {
        if (position == -1) {
            mmFeed.clickOnPromoter(activity, bean.getPromoters().get(0));
        } else {
            MMPromoter mmPromoter = bean.getPromoters().get(position);
            mmFeed.clickOnPromoter(activity, mmPromoter);
        }
    }

    public void adReportImpression(GDTnTanxADBean bean){
        for (MMPromoter p : bean.getPromoters()) {
            if (mmFeed != null && p != null) {
                mmFeed.reportImpression(activity, p);
            }
        }
    }

//    public SquareBean.SquareListBean getSquareBeanAd() {
//        GDTnTanxADBean adBean = getFeedAD();
//        if (adBean != null) {
//            SquareBean.SquareListBean tanxAdBean = new SquareBean.SquareListBean();
//            tanxAdBean.setAdimg(adBean.getAdimg());
//            tanxAdBean.setAdtitle(adBean.getAdtitle());
//            tanxAdBean.setAdimgs(adBean.getAdimgs());
//            tanxAdBean.setAdicon(adBean.getAdicon());
//            tanxAdBean.setAddescription(adBean.getAddescription());
//            tanxAdBean.setIsWhatAD(adBean.getIsWhatAD());
//            tanxAdBean.setAdprices(adBean.getAdprices());
//            tanxAdBean.setPromoters(adBean.getPromoters());
//            return tanxAdBean;
//        } else {
//            return null;
//        }
//    }
//    public SquareBean getSquareBeanAd() {
//        GDTnTanxADBean adBean = getFeedAD();
//        if (adBean != null) {
//            SquareBean tanxAdBean = new SquareBean();
//            tanxAdBean.setAdimg(adBean.getAdimg());
//            tanxAdBean.setAdtitle(adBean.getAdtitle());
//            tanxAdBean.setAdimgs(adBean.getAdimgs());
//            tanxAdBean.setAdicon(adBean.getAdicon());
//            tanxAdBean.setAddescription(adBean.getAddescription());
//            tanxAdBean.setIsWhatAD(adBean.getIsWhatAD());
//            tanxAdBean.setAdprices(adBean.getAdprices());
//            tanxAdBean.setPromoters(adBean.getPromoters());
//            return tanxAdBean;
//        } else {
//            return null;
//        }
//    }

    public CirclePostListBean getCirclePostListBeanAd() {
        GDTnTanxADBean adBean = getFeedAD();
        if (adBean != null) {
            CirclePostListBean tanxAdBean = new CirclePostListBean();
            tanxAdBean.setAdimg(adBean.getAdimg());
            tanxAdBean.setAdtitle(adBean.getAdtitle());
            tanxAdBean.setAdimgs(adBean.getAdimgs());
            tanxAdBean.setAdicon(adBean.getAdicon());
            tanxAdBean.setAddescription(adBean.getAddescription());
            tanxAdBean.setIsWhatAD(adBean.getIsWhatAD());
            tanxAdBean.setAdprices(adBean.getAdprices());
            tanxAdBean.setPromoters(adBean.getPromoters());
            return tanxAdBean;
        } else {
            return null;
        }
    }

    public PostsListBean getPostsListBeanAd() {
        GDTnTanxADBean adBean = getFeedAD();
        if (adBean != null) {
            PostsListBean tanxAdBean = new PostsListBean();
            tanxAdBean.setAdimg(adBean.getAdimg());
            tanxAdBean.setAdtitle(adBean.getAdtitle());
            tanxAdBean.setAdimgs(adBean.getAdimgs());
            tanxAdBean.setAdicon(adBean.getAdicon());
            tanxAdBean.setAddescription(adBean.getAddescription());
            tanxAdBean.setIsWhatAD(adBean.getIsWhatAD());
            tanxAdBean.setAdprices(adBean.getAdprices());
            tanxAdBean.setPromoters(adBean.getPromoters());
            return tanxAdBean;
        } else {
            return null;
        }
    }

//    public PostsDetaiBean getPostsDetailBeanAd() {
//        GDTnTanxADBean adBean = getFeedAD();
//        if (adBean != null) {
//            PostsDetaiBean tanxAdBean = new PostsDetaiBean();
//            tanxAdBean.setAdimg(adBean.getAdimg());
//            tanxAdBean.setAdtitle(adBean.getAdtitle());
//            tanxAdBean.setAdimgs(adBean.getAdimgs());
//            tanxAdBean.setAdicon(adBean.getAdicon());
//            tanxAdBean.setAddescription(adBean.getAddescription());
//            tanxAdBean.setIsWhatAD(adBean.getIsWhatAD());
//            tanxAdBean.setAdprices(adBean.getAdprices());
//            tanxAdBean.setPromoters(adBean.getPromoters());
//            return tanxAdBean;
//        } else {
//            return null;
//        }
//    }

}
