package com.hua.activity.popupWindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.hua.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Caesar on 2016/2/17.
 * 分享PopupWindow
 */
public class SharePopupWindow extends PopupWindow implements View.OnClickListener, Animation.AnimationListener {
    private View share_friend_dynamic;
    private View report;

    private List<View> mViews = new ArrayList<>();
    private List<Animation> mShowAnimations = new ArrayList<>();
    private List<Animation> mDismissAnimations = new ArrayList<>();

//    private ShareUtil mShareUtil;

    public SharePopupWindow(Context context/* @NonNull ShareUtil shareUtil*/) {
        super(context);
//        mShareUtil = shareUtil;
        View view = LayoutInflater.from(context).inflate(R.layout.share_pw, null);

        int width = context.getResources().getDisplayMetrics().widthPixels / 3;
        ImageView out_side = (ImageView) view.findViewById(R.id.out_side);
        out_side.setOnClickListener(this);
        View share_wechat = view.findViewById(R.id.share_tv1);
        share_wechat.setOnClickListener(this);
        share_wechat.getLayoutParams().width = width;
        View share_friend = view.findViewById(R.id.share_tv2);
        share_friend.setOnClickListener(this);
        share_friend.getLayoutParams().width = width;
        View share_weibo = view.findViewById(R.id.share_tv3);
        share_weibo.setOnClickListener(this);
        share_weibo.getLayoutParams().width = width;
        share_friend_dynamic = view.findViewById(R.id.share_tv5);
        share_friend_dynamic.setOnClickListener(this);

        View share_qzone = view.findViewById(R.id.share_tv4);
        share_qzone.setOnClickListener(this);
        View share_qq = view.findViewById(R.id.share_tv6);
        share_qq.setOnClickListener(this);
        report = view.findViewById(R.id.report);
        report.setOnClickListener(this);
        View bt_cancel = view.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);

        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(false);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mViews.add(share_wechat);
        mViews.add(share_friend);
        mViews.add(share_weibo);
        mViews.add(share_qzone);
        mViews.add(share_qq);
        mViews.add(share_friend_dynamic);
        mViews.add(report);
        mViews.add(bt_cancel);

        initAnimations();
    }

    private void initAnimations() {
        long startOffset = 0;
        final int size = mViews.size();
        for (int i = 0; i < size; i++) {
            Animation animation = createShowAnimation();
            animation.setStartOffset(startOffset);
            mShowAnimations.add(animation);
            animation = createDismissAnimation();
            animation.setStartOffset(startOffset);
            mDismissAnimations.add(animation);
            startOffset += 20;
        }
    }

    private Animation createShowAnimation() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, 0
        );
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(200);
        return animation;
    }

    private Animation createDismissAnimation() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 1.0f
        );
        animation.setFillAfter(true);
        animation.setDuration(200);
        return animation;
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.share_tv1:
//                mShareUtil.shareToWeChat();
//                break;
//            case R.id.share_tv2:
//                mShareUtil.shareToFriend();
//                break;
//            case R.id.share_tv3:
//                mShareUtil.shareToWeiBo();
//                break;
//            case R.id.share_tv4:
//                mShareUtil.shareToQZone();
//                break;
//            case R.id.share_tv6:
//                mShareUtil.shareToQQ();
//                break;
//            case R.id.share_tv5:
//                mShareUtil.shareToFriendDynamic();
//                break;
//            case R.id.report:
//                mShareUtil.report();
//                break;
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        int i = 0;
        for (View view : mViews) {
            if (view.getVisibility() == View.VISIBLE) {
                view.startAnimation(mShowAnimations.get(i));
                i++;
            }
        }
    }

    private boolean mDismissing;

    @Override
    public void dismiss() {
        if (mDismissing) {
            return;
        }
        mDismissing = true;
        int j = 0;
        final int size = mViews.size();
        Animation animation = null;
        for (int i = size - 1; i >= 0; i--) {
            View view = mViews.get(i);
            if (view.getVisibility() == View.VISIBLE) {
                animation = mDismissAnimations.get(j);
                view.startAnimation(animation);
                j++;
            }
        }
        if (animation != null) {
            animation.setAnimationListener(this);
        } else {
            super.dismiss();
            mDismissing = false;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        super.dismiss();
        mDismissing = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void setShowFriendDynamic(boolean showFriend) {
        if (showFriend) {
            share_friend_dynamic.setVisibility(View.VISIBLE);
        } else {
            share_friend_dynamic.setVisibility(View.GONE);
        }
    }

    public void enableReport() {
        report.setVisibility(View.VISIBLE);
    }

    public void disableReport() {
        report.setVisibility(View.GONE);
    }
}
