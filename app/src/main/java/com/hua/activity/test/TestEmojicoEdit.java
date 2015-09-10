package com.hua.activity.test;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.R;
import com.hua.bean.DraftBean;
import com.hua.utils.StringUtil;
import com.hua.utils.ToastUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by sundh on 2015/9/10.
 * 测试可放图片的edittext 控件
 */
@EActivity(R.layout.edittext_emojico)
public class TestEmojicoEdit extends  BaseActivity{

    @ViewById
    TextView tv_top;//头部标题

    @ViewById
    TextView publish;//发表

    @ViewById
    ImageView iv_back;//返回按钮

    @ViewById
    EditText et_write_content;


    @AfterInject
    void initVariable(){



    }


    @AfterViews
    void doAfterOncreat(){



    }

    ////////////////////


    @Click
    void iv_back(){
        finish();
    }

    @Click
    void publish(){
    String content = et_write_content.getText().toString();
        if(!StringUtil.isNull(content)){
            DraftBean bean = new DraftBean();
            bean.setContent(content);
            Intent intent = new Intent();
            intent.putExtra("bean",bean);
            setResult(200,intent);
            finish();
        }else {
            ToastUtil.showToast("美数据");
        }

    }

}
