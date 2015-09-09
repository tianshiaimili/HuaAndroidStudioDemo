package com.hua.listener;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.AbsListView;

import com.example.annotationdemo.utils.QuickReturnUtils;

/**
 * Created by etiennelawlor on 7/10/14.
 */
public class QuickReturnListViewOnScrollListener implements AbsListView.OnScrollListener {

    // region Member Variables
    private int mMinFooterTranslation;
    private int mMinHeaderTranslation;
    private int mPrevScrollY = 0;
    private int mHeaderDiffTotal = 0;
    private int mFooterDiffTotal = 0;
    private View mHeader;
    private View mFooter;
    private QuickReturnType mQuickReturnType;
    private boolean mCanSlideInIdleScrollState = false;

    private List<AbsListView.OnScrollListener> mExtraOnScrollListenerList = new ArrayList<AbsListView.OnScrollListener>();
    // endregion

    // region Constructors
    public QuickReturnListViewOnScrollListener(QuickReturnType quickReturnType, View headerView, int headerTranslation, View footerView, int footerTranslation){
        mQuickReturnType = quickReturnType;
        mHeader =  headerView;
        mMinHeaderTranslation = headerTranslation;
        mFooter =  footerView;
        mMinFooterTranslation = footerTranslation;
    }
    // endregion

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // apply another list' s on scroll listener
        for (AbsListView.OnScrollListener listener : mExtraOnScrollListenerList) {
          listener.onScrollStateChanged(view, scrollState);
        }
        if(scrollState == SCROLL_STATE_IDLE && mCanSlideInIdleScrollState){

            int midHeader = -mMinHeaderTranslation/2;
            int midFooter = mMinFooterTranslation/2;

            switch (mQuickReturnType) {
                case FOOTER:
                    if (-mFooterDiffTotal > 0 && -mFooterDiffTotal < midFooter) { // slide up
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), 0);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = 0;
                    } else if (-mFooterDiffTotal < mMinFooterTranslation && -mFooterDiffTotal >= midFooter) { // slide down
                        ObjectAnimator anim = ObjectAnimator.ofFloat(mFooter, "translationY", mFooter.getTranslationY(), mMinFooterTranslation);
                        anim.setDuration(100);
                        anim.start();
                        mFooterDiffTotal = -mMinFooterTranslation;
                    }
                    break;
            }

        }
    }

    @Override
    public void onScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // apply extra on scroll listener
        for (AbsListView.OnScrollListener listener : mExtraOnScrollListenerList) {
          listener.onScroll(listview, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        int scrollY = QuickReturnUtils.getScrollY(listview);
        int diff = mPrevScrollY - scrollY;

//        Log.d(getClass().getSimpleName(), "onScroll() : scrollY - "+scrollY);
//        Log.d(getClass().getSimpleName(), "onScroll() : diff - "+diff);
//        Log.d(getClass().getSimpleName(), "onScroll() : mMinHeaderTranslation - "+mMinHeaderTranslation);
//        Log.d(getClass().getSimpleName(), "onScroll() : mMinFooterTranslation - "+mMinFooterTranslation);

        if(diff != 0){
            switch (mQuickReturnType){
                case FOOTER:
                    if(diff < 0){ // scrolling down
                        mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                    } else { // scrolling up
                        mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                    }

                    mFooter.setTranslationY(-mFooterDiffTotal);
                    break;
            }
        }

        mPrevScrollY = scrollY;
    }

    public QuickReturnListViewOnScrollListener setCanSlideInIdleScrollState(boolean canSlideInIdleScrollState){
        mCanSlideInIdleScrollState = canSlideInIdleScrollState;
        return this;
    }

    public void registerExtraOnScrollListener(AbsListView.OnScrollListener listener) {
        mExtraOnScrollListenerList.add(listener);
    }
}
