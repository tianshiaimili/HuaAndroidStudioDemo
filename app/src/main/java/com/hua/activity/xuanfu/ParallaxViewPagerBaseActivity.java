package com.hua.activity.xuanfu;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;


/**
 * Created by desmond on 1/6/15.
 */
public abstract class ParallaxViewPagerBaseActivity extends FragmentActivity implements ScrollTabHolder {

    public static final String TAG = ParallaxViewPagerBaseActivity.class.getSimpleName();

    protected static final String IMAGE_TRANSLATION_Y = "image_translation_y";
    protected static final String HEADER_TRANSLATION_Y = "header_translation_y";

    protected View mHeader;
    protected ViewPager mViewPager;
    protected ParallaxFragmentPagerAdapter mAdapter;

    protected int mMinHeaderHeight;
    protected int mHeaderHeight;
    protected int mMinHeaderTranslation;
    protected int mNumFragments = 4;

    protected abstract void initValues();
    protected abstract void scrollHeader(int scrollY);
    protected abstract void setupAdapter();

    protected int getScrollYOfListView(AbsListView view) {
        View child = view.getChildAt(0);
        if (child == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = child.getTop();
        Log.d("Test","top=="+top);
        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }
        Log.d("Test","mHeaderHeight=="+mHeaderHeight);
        Log.d("Test","firstVisiblePosition=="+firstVisiblePosition);
        Log.d("Test","firstVisiblePosition * child.getHeight()=="+firstVisiblePosition * child.getHeight()+"\n");


        Log.d("Test","mMinHeaderTranslation=="+mMinHeaderTranslation);
        Log.d("Test","headerHeight=="+headerHeight);
        Log.d("Test","-top + firstVisiblePosition * child.getHeight=="+ (-top + firstVisiblePosition * child.getHeight() + headerHeight));
        return -top + firstVisiblePosition * child.getHeight() + headerHeight;
    }

    protected ParallaxViewPagerChangeListener getViewPagerChangeListener() {
        return new ParallaxViewPagerChangeListener(mViewPager, mAdapter, mHeader);
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(getScrollYOfListView(view));
        }
    }

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(view.getScrollY());
        }
    }

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(scrollY);
        }
    }
}
