package com.hua.activity.test;

import android.app.Activity;
import android.os.Bundle;

import com.hua.R;
import com.hua.view.PagerSlidingTabStrip;

/**
 * Created by sundh on 2016/3/28.
 */
public class UIDemoActivity extends Activity {

    PagerSlidingTabStrip slidingTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_demo);
    }


    private void initView(){

        slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pager_title);

    }

}
