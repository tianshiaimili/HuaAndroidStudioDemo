package com.hua.activity.test;

import android.app.Activity;
import android.widget.Gallery;

import com.hua.R;
import com.hua.bean.RecommendMessageBean;
import com.hua.view.FreaturePostsCreamHeaderView;
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
		listView.setAdapter(new CreamNewAdapter(this, list));
//		listView.setDivider(getResources().getDrawable(R.drawable.posts_new_list_item_divider));

	}

	
}