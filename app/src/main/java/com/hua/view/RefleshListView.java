package com.hua.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hua.R;
import com.hua.http.HttpConnManager;

import java.lang.reflect.Constructor;
import java.util.Date;

public class RefleshListView extends ListView implements OnScrollListener {

	private final static int RELEASE_TO_LOAD = 0x1000; // 松开刷新
	private final static int PULL_TO_LOAD = 0x1001; // 下拉刷新
	private final static int LOADING = 0x1002; // 刷新
	private final static int DONE = 0x1003; // 初始状态
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;
	private LayoutInflater mLayoutInflater;
	private LinearLayout mHeadView;
	private LinearLayout mFootView;
	private TextView mHeadViewTitleTv;
	private TextView mHeadViewLableTv;
	private ImageView mHeadViewArrowImg;
	private ProgressBar mHeadViewProgressBar;
	private TextView mFootViewTitleTv;
	private TextView mFootViewLableTv;
	private ProgressBar mFootViewProgressBar;
	private ImageView mFootViewArrowImg;
	private RotateAnimation mDownToUpAnimation;
	private RotateAnimation mUpToDownAnimation;
	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean mIsRecored;
	private int mHeadViewHeight;
	private int mFootViewHeight;
	private int mStartY;
	private int mFirstItemIndex;
	private int mLastItemIndex;
	private int mTotalItemCount;
	private int mState;
	private boolean isBack;
	private OnRefreshListener mRefreshListener;
	private OnLoadMoreListener mLoadMoreListener;
	private OnHiddentListener mHiddentListener;
	private OnHiddentTopListener mHiddentTopListener;
	private boolean mIsRefreshable = false;
	private boolean mIsLoadMoreable = false;

	private boolean mIsRefresh = false;
	private boolean mIsLoadMore = false;
	private int locationY = 0;
	private boolean isPreLoadMore;
	private boolean mIsFirstLoad = true;
	private LinearLayout innerLayout;
	public LinearLayout footLayout;
	private Context context;
	private boolean isHiddentMore = false;
	private OnScrollBottom onScrollBottom;
	private String  loadMoreTextDown = "松开加载更多";
	private String  loadMoreTextUp = "上拉加载更多";

	public boolean ismIsLoadMore() {
		return mIsLoadMore;
	}

	public void setmIsLoadMore(boolean mIsLoadMore) {
		this.mIsLoadMore = mIsLoadMore;
	}

	private boolean isRefreshNow = false;

	public boolean isRefreshNow() {
		return isRefreshNow;
	}

	public void setRefreshNow(boolean isRefreshNow) {
		this.isRefreshNow = isRefreshNow;
	}

	public boolean isHiddentMore() {
		return isHiddentMore;
	}

	public void setHiddentMore(boolean isHiddentMore) {
		this.isHiddentMore = isHiddentMore;
	}

	ListViewScrollListener listViewScrollListener;

	public interface ListViewScrollListener{
		public void onListViewScrollStateChanged(AbsListView arg0, int arg1);
		public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
	}

	public void setListViewScrollListener(ListViewScrollListener listViewScrollListener){
		this.listViewScrollListener = listViewScrollListener;
	}

	/**
	 * {@link Constructor}
	 *
	 * @param context
	 */
	public RefleshListView(Context context) {
		super(context);
		this.context = context;
		init(context);
	}

	/**
	 * {@link Constructor}
	 *
	 * @param context
	 */
	public RefleshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);
	}

	/**
	 * {@link Constructor}
	 *
	 * @param context
	 */
	public RefleshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		innerLayout = new LinearLayout(context);
		innerLayout.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		innerLayout.setOrientation(LinearLayout.VERTICAL);
		footLayout = new LinearLayout(context);
		footLayout.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		footLayout.setOrientation(LinearLayout.VERTICAL);
		mDownToUpAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mDownToUpAnimation.setInterpolator(new LinearInterpolator());
		mDownToUpAnimation.setDuration(250);
		mDownToUpAnimation.setFillAfter(true);

		mUpToDownAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mUpToDownAnimation.setInterpolator(new LinearInterpolator());
		mUpToDownAnimation.setDuration(250);
		mUpToDownAnimation.setFillAfter(true);

		mLayoutInflater = LayoutInflater.from(context);

		mHeadView = (LinearLayout) mLayoutInflater.inflate(R.layout.head_refresh_view, null);
		mHeadViewArrowImg = (ImageView) mHeadView.findViewById(R.id.head_refresh_view_arrow);
		// mHeadViewArrowImg.setMinimumWidth(50);
		// mHeadViewArrowImg.setMinimumHeight(50);
		mHeadViewProgressBar = (ProgressBar) mHeadView.findViewById(R.id.head_refresh_view_progressBar);
		mHeadViewTitleTv = (TextView) mHeadView.findViewById(R.id.head_refresh_view_title);
		mHeadViewLableTv = (TextView) mHeadView.findViewById(R.id.head_refresh_view_label);
		measureView(mHeadView);
		mHeadViewHeight = mHeadView.getMeasuredHeight();
		mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
		mHeadView.invalidate();

		innerLayout.addView(mHeadView);
		addHeaderView(innerLayout, null, false);

		mFootView = (LinearLayout) mLayoutInflater.inflate(R.layout.foot_load_more_view, null);
		mFootViewArrowImg = (ImageView) mFootView.findViewById(R.id.foot_load_more_view_arrow);
		// mFootViewArrowImg.setMinimumWidth(50);
		// mFootViewArrowImg.setMinimumHeight(50);
		mFootViewProgressBar = (ProgressBar) mFootView.findViewById(R.id.foot_load_more_view_progressBar);
		mFootViewTitleTv = (TextView) mFootView.findViewById(R.id.foot_load_more_view_title);
		mFootViewLableTv = (TextView) mFootView.findViewById(R.id.foot_load_more_view_label);
		mFootViewLableTv.setVisibility(View.GONE);
		measureView(mFootView);
		mFootViewHeight = mFootView.getMeasuredHeight();
		mFootView.setPadding(0, 0, 0, -mFootViewHeight);
		mFootView.invalidate();
		// View view = mLayoutInflater.inflate(R.layout.circle_list_footer, null);
		// footLayout.addView(view);
		footLayout.addView(mFootView);
		addFooterView(footLayout, null, false);

		mState = DONE;
		setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		mFirstItemIndex = firstVisibleItem;
		mLastItemIndex = firstVisibleItem + visibleItemCount;
		mTotalItemCount = totalItemCount;
		if (mHiddentTopListener != null) {
			if (firstVisibleItem == 0) {
				mHiddentTopListener.onHiddent(1);
			}
		}
		if (onScrollBottom != null) {
			if (firstVisibleItem == 0) {
				onScrollBottom.onTop();
			}
		}
		if (onScrollBottom != null) {
			if (firstVisibleItem != 0) {
				onScrollBottom.onBottom();
			}
		}
		//预加载更多
		if(isPreLoadMore && (visibleItemCount - this.getHeaderViewsCount() < totalItemCount)){
			int preItem = (int) (totalItemCount - 5);
			if (mIsLoadMoreable && !mIsLoadMore && mLastItemIndex == preItem && HttpConnManager.checkNetWorkStatus(context)) {
				mState = LOADING;
				changeFooterViewByState();
				loadMore();
			}
		}
		if(listViewScrollListener != null) {
			listViewScrollListener.onListViewScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
			case SCROLL_STATE_TOUCH_SCROLL:
				if (mHiddentListener != null) {
					mHiddentListener.onHiddent();
				}
				break;
		}
		if(listViewScrollListener != null) {
			listViewScrollListener.onListViewScrollStateChanged(view, scrollState);
		}
	}

	private int getListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null){
			return 0;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		// totalHeight = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		return totalHeight;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mHiddentListener != null) {
					mHiddentListener.onHiddent();
				}
				mStartY = locationY = (int) event.getY();
				if (mIsFirstLoad) {
					mIsRefresh = false;
					mIsLoadMore = false;
				} else {
					if (mIsRefreshable && mFirstItemIndex == 0 && !mIsRecored) {
						mIsRecored = true;
						mIsRefresh = true;
						mIsLoadMore = false;
					}
				/*
				 * if(getListViewHeightBasedOnChildren(this)<getHeight()){
				 * mIsRecored = false; }
				 */
					if (mIsLoadMoreable && mLastItemIndex == mTotalItemCount && !mIsRecored) {
						mIsRecored = true;
						mIsRefresh = false;
						mIsLoadMore = true;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mIsRefreshable) {
					if (mIsRefresh) {
						if (mState != LOADING) {
							switch (mState) {
								case DONE:
									// 什么都不做
									break;
								case PULL_TO_LOAD:
									// 下拉刷新
									mState = DONE;
									changeHeaderViewByState();
									break;
								case RELEASE_TO_LOAD:
									// 松开刷新
									mState = LOADING;
									changeHeaderViewByState();
									refresh();
									break;
								default:
									break;
							}
						}
					}
					mIsRecored = false;
					isBack = false;
				}
				if (mIsLoadMoreable) {
					if (mIsLoadMore) {
						if (mState != LOADING) {
							switch (mState) {
								case DONE:
									// 什么都不做
									break;
								case PULL_TO_LOAD:
									// 下拉刷新
									mState = DONE;
									changeFooterViewByState();
									break;
								case RELEASE_TO_LOAD:
									// 松开刷新
									mState = LOADING;
									changeFooterViewByState();
									loadMore();
									break;
								default:
									break;
							}
						}
					}
					mIsRecored = false;
					isBack = false;
				} else {
					mFootView.setPadding(0, 0, 0, -mFootViewHeight);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				if (tempY - locationY > 160) {
					if (mHiddentTopListener != null) {
						mHiddentTopListener.onHiddent(1);
					}
				} else if (tempY - locationY < -160) {
					if (mHiddentTopListener != null) {
						mHiddentTopListener.onHiddent(2);
					}
				}
				if (mIsRefreshable) {
					if (mIsRefresh) {
						if (!mIsRecored && mFirstItemIndex == 0) {
							mIsRecored = true;
							mStartY = tempY;
						}
						if (mState != LOADING && mIsRecored) {
							// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
							switch (mState) {
								case RELEASE_TO_LOAD: // 可以松手去刷新了
									setSelection(0);
									// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
									if (((tempY - mStartY) / RATIO < mHeadViewHeight) && (tempY - mStartY) > 0) {
										mState = PULL_TO_LOAD;
										changeHeaderViewByState();
									}
									// 一下子推到顶了
									else if (tempY - mStartY <= 0) {
										mState = DONE;
										changeHeaderViewByState();
									}
									mHeadView.setPadding(0, -1 * mHeadViewHeight + (tempY - mStartY) / RATIO, 0, 0);
									break;
								case PULL_TO_LOAD:
									setSelection(0);
									// 下拉到可以进入RELEASE_TO_REFRESH的状态
									if ((tempY - mStartY) / RATIO >= mHeadViewHeight) {
										mState = RELEASE_TO_LOAD;
										isBack = true;
										changeHeaderViewByState();
									} else if (tempY - mStartY <= 0) {// 上推到顶了
										mState = DONE;
										changeHeaderViewByState();
									} else {// 更新headView的paddingTop
										mHeadView.setPadding(0, (tempY - mStartY) / RATIO - mHeadViewHeight, 0, 0);
									}
									break;
								case DONE:
									if (tempY - mStartY > 0) {
										mState = PULL_TO_LOAD;
									}
									changeHeaderViewByState();
									break;
								default:
									break;
							}
						}
					}
				}
				if (mIsLoadMoreable) {
					if (mIsLoadMore) {
						if (!mIsRecored && mLastItemIndex == mTotalItemCount && !mIsRecored) {
							mIsRecored = true;
							mStartY = tempY;
						}
						if (mState != LOADING && mIsRecored) {
							// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
							switch (mState) {
								case RELEASE_TO_LOAD: // 可以松手去刷新了
									setSelection(mTotalItemCount);
									// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
									if (((mStartY - tempY) / RATIO < mFootViewHeight) && (mStartY - tempY) > 0) {
										mState = PULL_TO_LOAD;
										changeFooterViewByState();
									} else if (mStartY - tempY <= 0) { // 底部没哟被拉出来
										mState = DONE;
										changeFooterViewByState();
									}
									// 更新headView的size
									mFootView.setPadding(0, 0, 0, (mStartY - tempY) / RATIO - mFootViewHeight);
									break;
								case PULL_TO_LOAD:
									setSelection(mTotalItemCount);
									// 下拉到可以进入RELEASE_TO_REFRESH的状态
									if ((mStartY - tempY) / RATIO >= mFootViewHeight) {
										mState = RELEASE_TO_LOAD;
										isBack = true;
										changeFooterViewByState();
									} else if (mStartY - tempY <= 0) {
										mState = DONE;
										changeFooterViewByState();
									} else {// 更新headView的paddingTop
										mFootView.setPadding(0, 0, 0, (mStartY - tempY) / RATIO - mFootViewHeight);
									}
									break;
								case DONE:
									if (mStartY - tempY > 0) {
										mState = PULL_TO_LOAD;
									}
									changeFooterViewByState();
									break;

								default:
									break;
							}
						}
					}
				}
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (mState) {
			case RELEASE_TO_LOAD:
				mHeadViewArrowImg.setVisibility(View.VISIBLE);
				mHeadViewProgressBar.setVisibility(View.GONE);
				mHeadViewTitleTv.setVisibility(View.VISIBLE);
				mHeadViewLableTv.setVisibility(View.VISIBLE);
				mHeadViewArrowImg.clearAnimation();
				mHeadViewArrowImg.startAnimation(mDownToUpAnimation);
				mHeadViewTitleTv.setText("松开刷新");
				break;
			case PULL_TO_LOAD:
				mHeadViewProgressBar.setVisibility(View.GONE);
				mHeadViewTitleTv.setVisibility(View.VISIBLE);
				mHeadViewLableTv.setVisibility(View.VISIBLE);
				mHeadViewArrowImg.clearAnimation();
				mHeadViewArrowImg.setVisibility(View.VISIBLE);
				// 是由RELEASE_To_REFRESH状态转变来的
				if (isBack) {
					isBack = false;
					mHeadViewArrowImg.clearAnimation();
					mHeadViewArrowImg.startAnimation(mUpToDownAnimation);
					mHeadViewTitleTv.setText("下拉刷新");
				} else {
					mHeadViewTitleTv.setText("下拉刷新");
				}
				break;
			case LOADING:
				mHeadView.setPadding(0, 0, 0, 0);
				mHeadViewProgressBar.setVisibility(View.VISIBLE);
				mHeadViewArrowImg.clearAnimation();
				mHeadViewArrowImg.setVisibility(View.GONE);
				mHeadViewTitleTv.setText("正在刷新...");
				mHeadViewLableTv.setVisibility(View.VISIBLE);
				break;
			case DONE:
				mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
				mHeadViewProgressBar.setVisibility(View.GONE);
				mHeadViewArrowImg.clearAnimation();
				mHeadViewArrowImg.setImageResource(R.drawable.arrow_down);
				mHeadViewTitleTv.setText("下拉刷新");
				mHeadViewLableTv.setVisibility(View.VISIBLE);
				loadMoreTextUp = "上拉加载更多";
				loadMoreTextDown = "松开加载更多";
				break;
		}
	}

	public void setRefleshHeadVisibility() {
		mState = LOADING;
		mHeadViewLableTv.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeFooterViewByState() {
		switch (mState) {
			case RELEASE_TO_LOAD:
				mFootViewArrowImg.setVisibility(View.VISIBLE);
				mFootViewProgressBar.setVisibility(View.GONE);
				mFootViewTitleTv.setVisibility(View.VISIBLE);
				// mFootViewLableTv.setVisibility(View.VISIBLE);
				mFootViewArrowImg.clearAnimation();
				mFootViewArrowImg.startAnimation(mDownToUpAnimation);
				mFootViewTitleTv.setText(loadMoreTextDown);
				break;
			case PULL_TO_LOAD:
				mFootViewProgressBar.setVisibility(View.GONE);
				mFootViewTitleTv.setVisibility(View.VISIBLE);
				// mFootViewLableTv.setVisibility(View.VISIBLE);
				mFootViewArrowImg.clearAnimation();
				mFootViewArrowImg.setVisibility(View.VISIBLE);
				// 是由RELEASE_To_REFRESH状态转变来的
				if (isBack) {
					isBack = false;
					mFootViewArrowImg.clearAnimation();
					mFootViewArrowImg.startAnimation(mUpToDownAnimation);
					mFootViewTitleTv.setText(loadMoreTextUp);
				} else {
					mFootViewTitleTv.setText(loadMoreTextUp);
				}
				break;
			case LOADING:
				mFootView.setPadding(0, 0, 0, 0);
				mFootViewProgressBar.setVisibility(View.VISIBLE);
				mFootViewArrowImg.clearAnimation();
				mFootViewArrowImg.setVisibility(View.GONE);
				mFootViewTitleTv.setText("正在加载...");
				// mFootViewLableTv.setVisibility(View.VISIBLE);

				break;
			case DONE:
				mFootView.setPadding(0, 0, 0, -mFootViewHeight);
				mFootViewProgressBar.setVisibility(View.GONE);
				mFootViewArrowImg.setVisibility(View.VISIBLE);
				mFootViewArrowImg.clearAnimation();
				mFootViewArrowImg.setImageResource(R.drawable.arrow_up);
				mFootViewTitleTv.setText(loadMoreTextUp);
				// mFootViewLableTv.setVisibility(View.VISIBLE);
				break;
		}
	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.mRefreshListener = refreshListener;
		mIsRefreshable = true;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
		this.mLoadMoreListener = loadMoreListener;
		mIsLoadMoreable = true;
	}

	public void setOnHiddentListener(OnHiddentListener hiddentListener) {
		this.mHiddentListener = hiddentListener;
	}

	// 估计view的宽以及高
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 设置适配器
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
	}

	/**
	 * 底部加载更多的label是否可以见
	 *
	 * @param visibility
	 */
	public void setFootLabelVisibility(int visibility) {
		mFootViewLableTv.setVisibility(visibility);
	}

	/**
	 * 底部加载更多label的文字
	 *
	 * @param text
	 */
	public void setFootLabelText(CharSequence text) {
		mFootViewLableTv.setText(text);
	}

	/**
	 * 是否正在刷新
	 *
	 * @return
	 */
	public boolean isRefresh() {
		return mIsRefresh;
	}

	public void setRefresh(boolean isRefresh) {
		this.mIsRefresh = isRefresh;
	}

	/**
	 * 是否正在加载更多
	 *
	 * @return
	 */
	public boolean isLoadMore() {
		return mIsLoadMore;
	}

	/**
	 * 设置是否可以加载更多
	 *
	 * @param isLoadMoreable
	 */
	public void setLoadMoreable(boolean isLoadMoreable) {
		this.mIsLoadMoreable = isLoadMoreable;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	/**
	 * 刷新完毕
	 */
	public void refreshCompleted() {
		setRefreshNow(false);
		mState = DONE;
		mIsRefresh = false;
		mHeadViewLableTv.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		if (mRefreshListener != null) {
			setRefreshNow(true);
			mRefreshListener.onRefresh();
		}
	}

	/**
	 * 用户一进来就刷新页面
	 */
	public void refreshHead() {
		mHeadView.setPadding(0, 0, 0, 0);
		mHeadViewProgressBar.setVisibility(View.VISIBLE);
		mHeadViewArrowImg.clearAnimation();
		mHeadViewArrowImg.setVisibility(View.GONE);
		mHeadViewTitleTv.setText("正在刷新...");
		refresh();
		// mHeadViewLableTv.setVisibility(View.VISIBLE);
	}

	/**
	 * 用户一进来就刷新页面
	 */
	public void refreshHead(long time) {
		mHeadView.setPadding(0, 0, 0, 0);
		mHeadViewProgressBar.setVisibility(View.VISIBLE);
		mHeadViewArrowImg.clearAnimation();
		mHeadViewArrowImg.setVisibility(View.GONE);
		mHeadViewTitleTv.setText("正在刷新...");

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				refresh();
			}
		}, time);
		// mHeadViewLableTv.setVisibility(View.VISIBLE);
	}

	public interface OnLoadMoreListener {
		public void onLoadMore();
	}

	public void loadMoreCompleted() {
		mState = DONE;
		mIsLoadMore = false;
		mFootView.setPadding(0, 0, 0, -mFootViewHeight);
		mIsFirstLoad = false;
		changeFooterViewByState();
	}

	/**
	 * 每次加载完数据后都要调用该方法
	 */
	public void loadCompleted() {
		refreshCompleted();
		loadMoreCompleted();
	}

	/**
	 * 加载更多
	 */
	private void loadMore() {
		if (mLoadMoreListener != null) {
			mIsLoadMore = true;
			mLoadMoreListener.onLoadMore();
		}
	}

	public interface OnHiddentListener {
		public void onHiddent();
	}

	public void addChild(View child) {
		innerLayout.addView(child);
	}

	public void addChild(View child, int position) {
		innerLayout.addView(child, position);
	}

	public void addfooterChild(View child) {
		footLayout.addView(child);
	}

	public void addfooterChild(View child, int position) {
		footLayout.addView(child, position);
	}

	public interface OnHiddentTopListener {
		public void onHiddent(int item);
	}

	public OnHiddentTopListener getmHiddentTopListener() {
		return mHiddentTopListener;
	}

	public void setmHiddentTopListener(OnHiddentTopListener mHiddentTopListener) {
		this.mHiddentTopListener = mHiddentTopListener;
	}

	public interface OnScrollBottom {
		public void onBottom();
		public void onTop();
	}

	public OnScrollBottom getOnScrollBottom() {
		return onScrollBottom;
	}

	public void setOnScrollBottom(OnScrollBottom onScrollBottom) {
		this.onScrollBottom = onScrollBottom;
	}

	public void setPreLoadMore(boolean isPreLoadMore) {
		this.isPreLoadMore = isPreLoadMore;
	}

	public void noMoreData(){
		loadMoreTextUp = "没有了哦";
		loadMoreTextDown = "没有了哦";
	}

}
