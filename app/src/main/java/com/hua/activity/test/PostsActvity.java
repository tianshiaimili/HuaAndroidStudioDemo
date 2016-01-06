package com.hua.activity.test;

import android.app.Activity;
import android.widget.LinearLayout;

import com.hua.R;
import com.hua.adapter.TestAdapter;
import com.hua.listener.QuickReturnListViewOnScrollListener;
import com.hua.listener.QuickReturnType;
import com.hua.utils.LogUtils;
import com.hua.utils.PhoneInfoUtil;
import com.hua.view.RefleshListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.posts_layout)
public class PostsActvity extends Activity{

	@ViewById
	RefleshListView listView;
//	@ViewById
//	ListView listView;
	@ViewById
	LinearLayout bottom_layout;
	
	private TestAdapter adapter;
	
	@AfterViews
	void initViews(){
		adapter = new TestAdapter(this);
		listView.setAdapter(adapter);
		int footerHeight = getResources().getDimensionPixelSize(R.dimen.footer_height);
		listView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnType.FOOTER, null, 0, bottom_layout, footerHeight).setCanSlideInIdleScrollState(true));
		listView.setLoadMoreable(true);
		
		PhoneInfoUtil util = PhoneInfoUtil.getInstance(this);
		String type = util.getNetType();
		LogUtils.e("type =" + type);
		
	}
	
	
	
	
}
