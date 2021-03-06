package com.hua.activity.taiwanAd;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.desmond.parallaxviewpager.LogUtil;
import com.hua.R;
import com.hua.bean.Person;
import com.hua.utils.BeanUtils;
import com.hua.utils.QuickReturnUtils;
import com.hua.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * to let the SDK know the App status. (foreground or background)
 * you can let your activity extend BaseActivity simply.
 * */
public class SingleDeferAdapterActivity extends BaseActivity {//TODO extends BaseActivity
	
	//******************************************
	//	common UI
	//
	private final static int ITEM_SIZE = 8;
	private ListView mListView = null;
	private List<Object> mItems = new ArrayList<Object>(ITEM_SIZE);
	float scale = 0;
	private View title;
	
	//*******************************************
	//	stream ad
	//
	//XXX@Stream-init@#Stream-init#
	/**
	 *	you can hardcode this placement value in the source code, 
	 *	or replace it by calling your server API 	
	**/
	private final static String  mPlacement = Config.STREAM_PLACEMENT;
	//end
	private ExtendDeferStreamAdapter mAdapter = null;
	private View headView;
	private TextView title_tv;

	private String scrollY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//**********************************
		//	common UI
		//
		setContentView(R.layout.activity_stream);

		LayoutManager lm = LayoutManager.getInstance(this);
		title = findViewById(R.id.title);
		title_tv = (TextView) findViewById(R.id.title_tv);
		title_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				mListView.scrollTo(0,SingleDeferAdapterActivity.this.getResources().getDisplayMetrics().heightPixels/2);
//				mListView.setSelection(9);
				Person person = new Person();
				boolean value = BeanUtils.checkFieldValueNull(person);
				ToastUtil.showToast("value== "+value);

			}
		});
//		title.getLayoutParams().height = lm.getMetric(LayoutManager.LayoutID.STREAM_TITLE_HEIGHT);
		mListView = (ListView)findViewById(R.id.listView);
		headView = LayoutInflater.from(getApplication()).inflate(R.layout.sub_head,null);
		mListView.addHeaderView(headView);

		TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.sub_head,null);
		mListView.addHeaderView(textView);

		for(int i=0; i<ITEM_SIZE; i++) {
			mItems.add(new Object());
		}
		
		//***********************************
		//	stream ad
		//
		
		//XXX@Stream-defer-init@#Stream-defer-init#
		mAdapter = new ExtendDeferStreamAdapter(
				this, 
				mPlacement, 
				mItems);
		//end
		
		//XXX@Stream-active@#Stream-active#
		//	let the SDK know that this placement is active now
		//
		mAdapter.setActive();
		//end
		
		//	ListView
		//
		//XXX@Stream-onScroll-defer@#Stream-onScroll-defer#
		//	let the SDK know the scroll status
		//
//		mListView.setOnScrollListener(mAdapter);
		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {

			}

			@Override
			public void onScroll(AbsListView absListView, int i, int i1, int i2) {

				float height = (float)headView.getHeight();
				float scrollY = (float)QuickReturnUtils.getScrollY(absListView);
				LogUtil.d("Color","color .height== "+height);
				LogUtil.d("Color","color.scrollY== "+scrollY);
				float tempY = (float)Math.min(scrollY, height);
				LogUtil.d("Color","color.tempY== "+tempY);
				if(height != 0 && tempY <= height){
					float test = 2/4;
					LogUtil.d("Color","color.test== "+test);
					scale	= tempY / height;
				}
				LogUtil.d("Color","color.scale== "+scale);
				ArgbEvaluator evaluator = new ArgbEvaluator();
				int evaluate = (Integer) evaluator.evaluate(scale, 0XFF00796B,0XFFFFFFFF);
				LogUtil.d("Color","color.evaluate== "+scale);
				title.setBackgroundColor(evaluate);

			}
		});
		//end
		mListView.setAdapter(mAdapter);
		
		/*
		//XXX@Stream-setOnItemClickListener@#Stream-setOnItemClickListener#
		//	if you have not implemented setOnItemClickListener,
		//	skip this code
		//
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//	check is this position is a ad first
				//
				if(mAdapter != null && mAdapter.isAd(position)) {
					return;
				}
				
				//	...
				//	then add your original code here
				//	...
				
			}
			
		});
		//end
		*/
		
		/**
		 *  if you have already implemented OnScrollListener
		 *  you can use follows
		 *  
		//XXX@Stream-onScroll@#Stream-onScroll#
		mListView.setOnScrollListener(new OnScrollListener() {

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						// ...
						// if you have already implemented this listener,
						// add your original code here
						// ...

						if (mAdapter != null) {
							mAdapter.onScrollStateChanged(view,
									scrollState);
						}
					}

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						// ...
						// if you have already implemented this listener,
						// add your original code here
						// ...

						if (mAdapter != null) {
							mAdapter.onScroll(
									view,
									firstVisibleItem,
									visibleItemCount,
									totalItemCount);
						}
					}
				});
		//end
		*/
		
	}
	
	//XXX@Stream-life@#Stream-life#
	@Override
	public void onResume() {
		super.onResume();
		
		if(mAdapter != null) {
			mAdapter.onResume();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mAdapter != null) {
			mAdapter.onPause();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if(mAdapter != null) {
			mAdapter.release();
			mAdapter = null;
		}
		
	}
	//end
	
//	@Override
//	public void onBackPressed() {
////		Intent intent = new Intent();
////		intent.setClass(this, TreePartLoginActivity.class);
////		startActivity(intent);
////		finish();
//	}
}
