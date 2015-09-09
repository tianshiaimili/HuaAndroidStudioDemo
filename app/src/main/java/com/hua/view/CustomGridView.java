package com.hua.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
public class CustomGridView extends GridView {
	private boolean mIsScrollable = false;

	public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setScrollaable(boolean isScrollable) {
		this.mIsScrollable = isScrollable;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if(mIsScrollable) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
			super.onMeasure(widthMeasureSpec, expandSpec); 
		}

	}
}
