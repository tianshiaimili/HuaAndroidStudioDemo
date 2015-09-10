package com.hua.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 使用圆角处理类
 */
public class CircleTransform extends BitmapTransformation {
    private Context context;

    public CircleTransform(Context context) {
        super(context);
        this.context = context;
    }

    public CircleTransform(BitmapPool bitmapPool) {
        super(bitmapPool);
    }


    @Override
    public String getId() {
        return "Glide_Circle_Transformation";
    }


    public  Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
            if(bitmap==null){
                return null;
            }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        float mCornerRadius =12; // 圆角半径
//        float mCornerRadius = 8 * context.getResources()
//                .getDisplayMetrics().density; // 圆角半径
//        if(mCornerRadius<=0){
//            mCornerRadius = 12;
//        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    @Override
    protected Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return getRoundedCornerBitmap(bitmap);
    }
}