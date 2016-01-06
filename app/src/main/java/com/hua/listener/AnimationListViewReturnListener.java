package com.hua.listener;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.hua.utils.AnimationUtil;
import com.hua.utils.QuickReturnUtils;

/**
 * Created by sundh on 2016/1/5.
 */
public class AnimationListViewReturnListener implements OnScrollListener {

    private int mMinFooterTranslation;
    private int mPrevScrollY = 0;
    private int mPrevTranslationY = 0;
    private int mFooterDiffTotal = 0;
    private View mFooter;

    public AnimationListViewReturnListener(View footerView, int footerTranslation){
        mFooter =  footerView;
        mMinFooterTranslation = footerTranslation;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        int scrollY = QuickReturnUtils.getScrollY(view);
        int diff = mPrevScrollY - scrollY;

        if(diff != 0){
                    if(diff < 0){ //mFooter scrolling down
                        mFooterDiffTotal = Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation);
                        AnimationUtil.translationY(mFooter,mPrevTranslationY,mMinFooterTranslation);
                    } else { // scrolling up
                        mFooterDiffTotal = Math.min(Math.max(mFooterDiffTotal + diff, -mMinFooterTranslation), 0);
                        AnimationUtil.translationY(mFooter,mPrevTranslationY,0);
                    }
            mPrevTranslationY = -mFooterDiffTotal;
            }
        mPrevScrollY = scrollY;

    }
}
