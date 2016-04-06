package com.hua.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hua.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundh on 2016/4/1.
 */
public class CirclesSearchActivity extends Activity {

    LinearLayout   search_layout;
    LinearLayout content_layout;
    ListView listView;
    MyAdapter adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_circle_layout);
        initView();
    }

    private  void initView(){
        content_layout = (LinearLayout) findViewById(R.id.content_layout);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content_layout.setVisibility(View.VISIBLE);
                listView.setAdapter(adapter);
            }
        });

        listView = (ListView) findViewById(R.id.circle_list);
        list = new ArrayList<>();
        for(int i = 0; i < 10 ; i ++){
            list.add("test "+ 1);
        }
        adapter = new MyAdapter();

    }

    class MyAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return list.size();
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
            TextView textView = new TextView(CirclesSearchActivity.this);
            textView.setText(list.get(position));
            textView.setTextSize(30);
            return textView;
        }
    }

}
