package com.hua.activity.animator;

import android.app.Activity;
import android.widget.LinearLayout;

import com.hua.R;
import com.hua.adapter.TestAdapter;
import com.hua.listener.AnimationListViewReturnListener;
import com.hua.utils.LogUtils;
import com.hua.utils.PhoneInfoUtil;
import com.hua.view.RefleshListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.posts_layout)
public class QuickReutrnActvity extends Activity{

	@ViewById
	RefleshListView listView;
	@ViewById
	LinearLayout bottom_layout;
	
	private TestAdapter adapter;
	
	@AfterViews
	void initViews(){
		adapter = new TestAdapter(this);
		listView.setAdapter(adapter);
		int footerHeight = getResources().getDimensionPixelSize(R.dimen.footer_height);
		listView.setOnScrollListener(new AnimationListViewReturnListener(bottom_layout, footerHeight));
		listView.setLoadMoreable(true);
		
		PhoneInfoUtil util = PhoneInfoUtil.getInstance(this);
		String type = util.getNetType();
		LogUtils.e("type =" + type);
		
	}
	
	
	
	
}
