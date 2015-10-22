package com.hua.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.view.RefleshListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundh on 2015/7/27.
 * 测试在adapter中的getview方法中的lsit添加元素的效果
 * 以实现固定位置
 */
@EActivity(R.layout.list_position_change)
public class ListPsoitionChange extends Activity  {

    @ViewById
    RefleshListView listView;
    MAdapter adapter;

    List<String> list = new ArrayList<>();


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @AfterViews
    void initViews(){

        for(int i= 0;i<30;i++){
            list.add("啦啦啦德玛西亚"+i);
        }

        adapter = new MAdapter(list);
        listView.setPreLoadMore(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Click=="+position,300).show();
                Intent intent = new Intent(ListPsoitionChange.this,MultipleItemsList.class);
                startActivity(intent);
            }
        });

        listView.setOnRefreshListener(new RefleshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("添加新一行-----");
                        listView.refreshCompleted();
                    }
                },500);

            }
        });

    }

    class MAdapter extends BaseAdapter{

        List<String> listData;
        public MAdapter(List l){
            this.listData = l;
        }

        public int getPosition(){

            if(listData.size() <40){
                return 3;
            }else if(listData.size() >= 40){
                return 8;
            }

            return 2;
        }


        @Override
        public int getCount() {
            return listData.size();
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

            TextView textView = new TextView(getApplication());
            textView.setTextSize(24);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setBackgroundColor(Color.parseColor("#CCCCCC"));
            if(position == getPosition() ){
                if(!listData.contains("在"+getPosition()+"这里添加")){
                    listData.add(position,"在"+getPosition()+"这里添加");
                }else{
                    listData.set(position, "在"+getPosition()+"这里添加");
                }
                notifyDataSetChanged();
            }else if(position == 5){
                if(!listData.contains("在5这里添加")){
                    listData.add(position,"在5这里添加");
                }else{
                    listData.set(position, "在5这里添加");
                }
                notifyDataSetChanged();
            }



            textView.setText(listData.get(position));

            return textView;
        }
    }

}

