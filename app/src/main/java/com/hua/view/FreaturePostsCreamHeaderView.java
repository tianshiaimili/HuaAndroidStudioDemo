package com.hua.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;

import com.hua.R;
import com.hua.interfaces.AdapterItemView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


/**
 * Created by sundh  on 2015/7/14.
 * 圈子 头部视图
 */
@EViewGroup(R.layout.feature_posts_header)
public class FreaturePostsCreamHeaderView extends AdapterItemView {

    @ViewById
    TextView local_titles;

    @ViewById
    Gallery gallery;

    private Context context;
    public FreaturePostsCreamHeaderView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void bindView(Object data) {
        super.bindView(data);
    }

    public void setLocalTitle(String content){

        local_titles.setText(content);

    }

    public void setGalleryAdapter(BaseAdapter adapter){
        gallery.setAdapter(adapter);
        gallery.setSelection(100);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                ToastUtil.showToast("position=" + position);
            }
        });
    }

}
