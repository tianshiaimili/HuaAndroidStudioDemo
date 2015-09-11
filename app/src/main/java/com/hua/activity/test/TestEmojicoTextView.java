package com.hua.activity.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.R;
import com.hua.bean.DraftBean;
import com.hua.utils.ActivityManager;
import com.hua.view.RefleshListView;
import com.rockerhieu.emojicon.EmojiconTextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundh on 2015/9/10.
 * 测试可放图片的edittext 控件
 */
@EActivity(R.layout.textview_emojico)
public class TestEmojicoTextView extends  BaseActivity{

    @ViewById
    TextView tv_top;//头部标题

    @ViewById
    TextView publish;//发表

    @ViewById
    ImageView iv_back;//返回按钮

    @ViewById
    RefleshListView listview;

    List<DraftBean> beans = new ArrayList<>();

    private MAdapter adapter;

    @AfterInject
    void initVariable(){



    }


    @AfterViews
    void doAfterOncreat(){
    listview.setLoadMoreable(true);

    adapter = new MAdapter(this,beans);
        listview.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 200 && data != null){

        DraftBean bean = (DraftBean) data.getSerializableExtra("bean");
        beans.add(bean);
            adapter.notifyDataSetChanged();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    ////////////////////


    @Click
    void iv_back(){
        finish();
    }

    @Click
    void publish(){
//        finish();
    }

    @Click
    void tv_top(){

        Intent intent = new Intent(this,TestEmojicoEdit_.class);
        ActivityManager.getManager().goFoResult(this,intent);

    }

    class MAdapter extends BaseAdapter{

        List<DraftBean> mList;
        Context mContext;

        public MAdapter(Context context,List<DraftBean> list){
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.emojico_item,null);
            EmojiconTextView con = (EmojiconTextView) view.findViewById(R.id.message);
            con.setText(beans.get(position).getContent());
            return view;
        }
    }

}
