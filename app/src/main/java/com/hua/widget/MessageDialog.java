package com.hua.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hua.R;
import com.hua.activity.MMApplication;
import com.hua.utils.PublicMethod;
import com.hua.utils.StringUtil;


public class MessageDialog {
    private LayoutInflater inflater;
    private Context context;
    private MyDialogListener mydialoglistener;

    public interface MyDialogListener {
        public void DialogListene_btn_1();//按钮1事件

        public void DialogListene_btn_2();//按钮2 事件
    }

    public MessageDialog(Context context, MyDialogListener mydialoglistener) {
        this.context = context;
        this.mydialoglistener = mydialoglistener;
        this.inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);// 加载数据项
    }

    /**
     * 自定义 dialog
     *
     * @param titile   标题
     * @param contents 内容
     * @param str_btn1 按钮1的文字
     * @param str_btn2 按钮2的文字
     * @return dialog
     */
    public Dialog initDialog(String titile, String contents, String str_btn1, String str_btn2, boolean flag) {
        View view_dilog = inflater.inflate(R.layout.message_dialog, null);
        Button btn_1 = (Button) view_dilog.findViewById(R.id.dilog_btn_1);
        Button btn_2 = (Button) view_dilog.findViewById(R.id.dilog_btn_2);
        if (flag) {
            btn_2.setVisibility(View.GONE);
        } else {
            btn_2.setVisibility(View.VISIBLE);
        }
        TextView txt_content = (TextView) view_dilog.findViewById(R.id.message_dilog_txt);// 内容

        TextView txt_title = (TextView) view_dilog.findViewById(R.id.dialog_title_txt);// 标题

        btn_1.setText(str_btn1 == null ? "确定" : str_btn1);
        btn_2.setText(str_btn2 == null ? "取消" : str_btn2);
        txt_content.setText(contents);
        txt_title.setText(titile == null ? "温馨提示" : titile);
        final Dialog dialog = new Dialog(context, R.style.messagedialog);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // lp.alpha=0.1f;
        lp.dimAmount = 0.5f;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(view_dilog);
        dialog.getWindow().setLayout(getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mydialoglistener.DialogListene_btn_1();
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mydialoglistener.DialogListene_btn_2();
            }
        });
        dialog.show();
        return dialog;
    }


    /**
     * 自定义 dialog
     *用于删除帖子和禁言
     * @param titile   标题
     * @param contents 内容
     * @param str_btn1 按钮1的文字
     * @param str_btn2 按钮2的文字
     * @return dialog
     */
    public Dialog initOperationTipDialog(String titile, String contents, String str_btn1, String str_btn2, boolean flag) {
        View view_dilog = inflater.inflate(R.layout.operation_tip_dialog, null);
        Button cancel = (Button) view_dilog.findViewById(R.id.dilog_cancel);
        Button sure = (Button) view_dilog.findViewById(R.id.dilog_sure);
        LinearLayout botton_layout = (LinearLayout) view_dilog.findViewById(R.id.botton_layout);
        if (flag) {
            botton_layout.setVisibility(View.GONE);
        } else {
            botton_layout.setVisibility(View.VISIBLE);
        }
        TextView txt_content = (TextView) view_dilog.findViewById(R.id.message_dilog_txt);// 内容
        if(!StringUtil.isNull(titile)){
            //不为空则显示标题布局
            LinearLayout title_layout = (LinearLayout) view_dilog.findViewById(R.id.title_layout);
            title_layout.setVisibility(View.VISIBLE);

            TextView txt_title = (TextView) view_dilog.findViewById(R.id.dialog_title_txt);// 标题
            txt_title.setText(titile == null ? "温馨提示" : titile);
        }


        cancel.setText(str_btn1 == null ? "取消" : str_btn1);
        sure.setText(str_btn2 == null ? "确定" : str_btn2);
        txt_content.setText(contents);
        final Dialog dialog = new Dialog(context, R.style.messagedialog);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // lp.alpha=0.1f;
        lp.dimAmount = 0.5f;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(view_dilog);
//        dialog.set
        dialog.getWindow().setLayout(getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mydialoglistener.DialogListene_btn_1();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mydialoglistener.DialogListene_btn_2();
            }
        });
        dialog.show();
        return dialog;
    }

    public Dialog initDialog(int titile, int contents, boolean flag) {
        Context ctx = MMApplication.getAppContext();
        return initDialog(ctx.getString(titile), ctx.getString(contents), null, null, flag);
    }
    public Dialog initDialog(int titile, String contents, boolean flag) {
        Context ctx = MMApplication.getAppContext();
        return initDialog(ctx.getString(titile), contents, null, null, flag);
    }

    public int getWidth() {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width - PublicMethod.dip2px(context, 40);
    }
}
