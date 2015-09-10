package com.hua.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.hua.R;
import com.hua.utils.SystemUtil;


public class LoadDialog extends AlertDialog {

    TextView msg_text;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_dialog);
        msg_text = (TextView) findViewById(R.id.msg);

    }

    public LoadDialog(Context context) {
        super(context, R.style.loaddialog);
        mContext = context;
        this.setCanceledOnTouchOutside(false);

    }

    public void setMessage(String str) {
        if (!TextUtils.isEmpty(str)) {
            msg_text.setText(str);
        }
        SystemUtil.KeyBoardHiddent((Activity) mContext);
    }

    public void setMessage(int str) {
        if (str!=0) {
            msg_text.setText(getContext().getString(str));
        }
        SystemUtil.KeyBoardHiddent((Activity) mContext);
    }

    /**
     * 即使弹出加载框也显示键盘
     *
     * @param str
     */
    public void setMessageDisplayKey(String str) {
        msg_text.setText(str);
    }


}
