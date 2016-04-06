package com.hua.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hua.R;


/**
 * Created by Caesar on 2016/4/6.
 * 下拉刷新的HeaderView
 */
public class LoadingView extends ImageView {
    private Bitmap mBitmap;
    private Paint mPaint;
    private Rect mSrc;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setImageResource(R.drawable.logo1);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
        mPaint = new Paint();
        mSrc = new Rect(0, mBitmap.getHeight(), mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mSrc, mSrc, mPaint);
    }

    public void setSrcTop(int top) {
        if (top > mBitmap.getHeight()) {
            mSrc.top = mBitmap.getHeight();
        } else if (top < 0) {
            mSrc.top = 0;
        } else {
            mSrc.top = top;
        }
        postInvalidate();
    }

    public void setSrcTop(float scale) {
        if (scale > 1) {
            scale = 1;
        }
        scale = 1 - scale;
        mSrc.top = (int) (scale * mBitmap.getHeight());
        postInvalidate();
    }

    public int getBitmapHeight() {
        return mBitmap.getHeight();
    }
}
