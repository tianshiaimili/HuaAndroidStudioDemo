package com.hua.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sundh on 2016/2/25.
 */
public class CustomsHorizontalScrollView extends LinearLayout {

    private Context mContext;
    private ImageView headIco, rearIco;
    private HorizontalScrollView contentLayout;


    public CustomsHorizontalScrollView(Context context) {
        super(context);
        init(context);
    }

    public CustomsHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomsHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        this.mContext = context;


    }

}
