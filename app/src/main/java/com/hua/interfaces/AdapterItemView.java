package com.hua.interfaces;

import android.content.Context;
import android.widget.LinearLayout;

/**
 */
public class AdapterItemView extends LinearLayout implements CommonItemView {

    public Context mContext;
    public final int LOGIN_CODE = 500;//登录请求requestCode
    public final int MODIFY_CODE = 600;//修改用户请求requestCode

    public AdapterItemView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void bindView(Object data) {
        if (data == null)
            return;
    }

    @Override
    public void bindView(Object data, int position) {
        if (data == null||position<0)
            return;
    }

    @Override
    public void bindView(Object data, int type, int position) {

    }
}
