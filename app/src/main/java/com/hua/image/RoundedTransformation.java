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
 * Created by Caesar on 2016/2/2.
 * 居中裁剪圆角，默认圆角半径为12px
 */
public class RoundedTransformation extends BitmapTransformation {
    private static final float DEFAULT_RADIUS = 12;
    private float mRadius;

    public RoundedTransformation(Context context) {
        this(context, DEFAULT_RADIUS);
    }

    public RoundedTransformation(Context context, float radiusInPx) {
        super(context);
        mRadius = radiusInPx;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        double outRatio = (double) outWidth / (double) outHeight;
        int x = 0;
        int width = sourceWidth;
        int height = (int) (width / outRatio);
        int y = (sourceHeight - height) / 2;
        if (height >= sourceHeight) {
            height = sourceHeight;
            width = (int) (outRatio * height);
            y = 0;
            x = (sourceWidth - width) / 2;
        }
        Bitmap bitmap = Bitmap.createBitmap(source, x, y, width, height);

        // 圆角
        Bitmap output = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF dst = new RectF(0, 0, outWidth, outHeight);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(dst, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        return output;
    }

    @Override
    public String getId() {
        return "SquareRoundedTransformation";
    }
}
