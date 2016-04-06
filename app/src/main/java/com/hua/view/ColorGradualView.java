package com.hua.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.hua.R;

/**
 * Created by sundh on 2016/4/6.
 */
public class ColorGradualView extends View {

    private Paint paint;
    private Paint _colorPaint;
    private Context mContext;
    /**
     * 当前进度
     */
    private int progress;

    private MyHandler handler;

    public ColorGradualView(Context context) {
        super(context);
        init(context);
    }

    public ColorGradualView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColorGradualView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        paint = new Paint();
//        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
//        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        handler = new MyHandler(this, progress);
        handler.sendEmptyMessageDelayed(0,0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int view_width = getWidth();
        int view_height = getHeight();
        /**
         * 画图片
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect dst = new Rect(view_width / 2, view_height / 2, view_width / 2 + width, view_height / 2 + height);
        Rect src = new Rect(0, 0, width, height);


        int width2 = bitmap2.getWidth();
        int height2 = bitmap2.getHeight();

        Rect dst2 = new Rect(view_width / 2, (view_height / 2) * progress / 10, view_width / 2 + width2, view_height / 2 + height2);
        Rect src2 = new Rect(0, 0, width2, height2);

        canvas.drawBitmap(bitmap, src, dst, paint);
        canvas.drawBitmap(bitmap2, src2, dst2, paint);

    }

    private void setProgress(int p) {
        progress = p;

    }

    static class MyHandler extends Handler {

        ColorGradualView view;
        int mProgress;

        MyHandler(ColorGradualView v, int progress) {
            this.view = v;
            this.mProgress = progress;
        }

        @Override
        public void handleMessage(Message msg) {

            mProgress += 1;
            if (mProgress > 10) {
                mProgress = 0;
            }
            view.setProgress(mProgress);
            view.invalidate();
            sendEmptyMessageDelayed(0, 0);
        }
    }

}
