package com.hua.activity.test;

import android.app.Activity;
import android.widget.AbsListView;
import android.widget.Gallery;

import com.hua.R;
import com.hua.adapter.CreamNewAdapter;
import com.hua.adapter.PostsGalleryAdapter;
import com.hua.bean.RecommendMessageBean;
import com.hua.view.FreaturePostsCreamHeaderView;
import com.hua.view.FreaturePostsCreamHeaderView_;
import com.hua.view.RefleshListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2015/7/2.
 */
@EActivity(R.layout.second_posts_list)
public class PersonFragmentNew extends Activity {

	private int[] imageId = new int[] { R.drawable.bg01, R.drawable.bg02,
			R.drawable.bg03, R.drawable.bg04, R.drawable.bg05 };

	@ViewById
	RefleshListView listView;

	Gallery gallery;

	PostsGalleryAdapter galleryAdapter;

	FreaturePostsCreamHeaderView headerView;

	List<RecommendMessageBean> list = new ArrayList<RecommendMessageBean>();

	@AfterInject
	void init() {

		for (int i = 0; i < 20; i++) {

			RecommendMessageBean bean = new RecommendMessageBean(i+".name-" + i, "desc - " + i);
			list.add(bean);

		}

	}

	@AfterViews
	void initViews() {
		headerView = FreaturePostsCreamHeaderView_.build(this);
		headerView.setGalleryAdapter(new PostsGalleryAdapter(this));
		listView.addChild(headerView);
		listView.setPreLoadMore(true);
		listView.setAdapter(new CreamNewAdapter(this, list));
		listView.setOnRefreshListener(new RefleshListView.OnRefreshListener() {

			@Override
			public void onRefresh() {
			}
		});
		listView.setOnLoadMoreListener(new RefleshListView.OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
			}
		});


		listView.setListViewScrollListener(new RefleshListView.ListViewScrollListener() {

			@Override
			public void onListViewScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onListViewScroll(AbsListView view, int firstVisibleItem,
										 int visibleItemCount, int totalItemCount) {
			}
		});
//		listView.setDivider(getResources().getDrawable(R.drawable.posts_new_list_item_divider));

	}

	
}