package com.hua.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.hua.R;
import com.hua.utils.AnimationUtil;


/**
 */
public class ImageLoadUtil {


    public static void loadImageWithCover(Context context, ImageView imageView, String url, boolean asBitmap) {
        loadImageWithUrl(context, imageView, url, R.drawable.cover, R.drawable.cover, asBitmap);
    }

    public static void loadImageWithCover(Context context, ImageView imageView, String url) {
        loadImageWithUrl(context, imageView, url, R.drawable.cover, R.drawable.cover, false, new CircleTransform(context));
    }

    public static void loadImageWithCover(Context context, ImageView imageView, String url,int type) {
        if(type == 0){
            loadImageWithUrl(context, imageView, url, R.drawable.cover, R.drawable.cover, false, new GlideCircleTransform(context));
        }else if(type == 1){
            loadImageWithUrl(context, imageView, url, R.drawable.cover, R.drawable.cover, false, new GlideRoundTransform(context,30));
        }else if(type == 2){
            loadImageWithUrl(context, imageView, url, R.drawable.cover, R.drawable.cover, false, new RoundedCornersTransformation(context,30,10, RoundedCornersTransformation.CornerType.LEFT));
        }
    }


    public static void loadImageWithDepic(Context context, ImageView imageView, String url) {
        loadImageWithUrl(context, imageView, url, R.drawable.de_pic, R.drawable.pic_error, false);
    }

    public static void loadImageWithDepic(Context context, ImageView imageView, String url, boolean asBitmap) {
        loadImageWithUrl(context, imageView, url, R.drawable.de_pic, R.drawable.pic_error, asBitmap);
    }

    public static void loadImageWithDepic(Context context, ImageView imageView, String url, int drawableId) {
        loadImageWithUrl(context, imageView, url, drawableId, drawableId, false);
    }

    public static void loadImageWithUrl(Context context, ImageView imageView, String url, int drawableId, int errorId) {
        loadImageWithUrl(context, imageView, url, drawableId, errorId, false);
    }


    public static void loadImageWithUrl(Context context, ImageView imageView, String url, int drawableId, int errorId, boolean asBitmap) {
        loadImageWithUrl(context, imageView, url, drawableId, errorId, asBitmap, null);
    }

    public static void loadImageWithUrl(Context context, ImageView imageView, String url, int drawableId, int errorId, boolean asBitmap, Transformation bitmapTransformation) {
        try {
            DrawableTypeRequest request = Glide.with(context).load(url);
            if (asBitmap) {
                request.asBitmap().animate(AnimationUtil.getDefaultAnimation()).placeholder(drawableId).error(errorId).into(imageView);
            } else {

                if (bitmapTransformation != null) {
                    request.bitmapTransform(bitmapTransformation).placeholder(drawableId).error(errorId).into(imageView);
                } else {
                    request.placeholder(drawableId).error(errorId).into(imageView);
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void loadImageWithNone(Context context, ImageView imageView, String url) {
        loadImageWithUrl(context, imageView, url, 0, 0, false);
    }

    public static void loadImageWithDefalut(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).crossFade().into(imageView);
    }

    public static void loadImageWithWidth(Context context, ImageView imageView, String url, int width) {
        if (width <= 0) {
            DisplayMetrics dm = new DisplayMetrics();
            dm = context.getApplicationContext().getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            width = screenWidth;
        }
        Glide.with(context).load(url).placeholder(R.drawable.de_pic).error(R.drawable.pic_error).override(width, width).into(imageView);
    }

    public static void loadImageWithWidthAndHeight(Context context, ImageView imageView, String url, int width, int height) {
        Glide.with(context).load(url).placeholder(R.drawable.de_pic).error(R.drawable.pic_error).override(width, height).into(imageView);
    }

//    public static void loadIconMenu(Context context,String imageUrl, final TipButton view){
//
//        Glide.with(context).load(imageUrl).into(new SimpleTarget<GlideDrawable>(300,300) {
//            @Override
//            public void onResourceReady(GlideDrawable bm, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                if (bm != null) {
//                        view.setCompoundDrawablesWithIntrinsicBounds(null, bm, null, null);
//
//                }
//
//            }
//        });
//
//
//    }

//    public static DisplayImageOptions  optionsImage = new DisplayImageOptions
//            .Builder()
//            .showImageForEmptyUri(R.drawable.pic_error)
//            .showImageOnFail(R.drawable.pic_error)
//            .bitmapConfig(Bitmap.Config.RGB_565)
//            .cacheOnDisk(true)
//            .resetViewBeforeLoading(true)
//            .cacheInMemory(false)
//            .considerExifParams(true)
//            .imageScaleType(ImageScaleType.EXACTLY)
//            .build();
//}

    /**
     * 将图片等比例缩放 setAdjustViewBounds setMaxWidth setMaxWidth必须同时设置才有效
     *
     * @param context
     * @param image
     *            图片控件
     * @param source
     *            图片资源
     */
    public static void setImageViewMathParent(Activity context,ImageView image, int source) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                source);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        float scalew = (float) displayMetrics.widthPixels
                / (float) bitmap.getWidth();
        image.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = new Matrix();
        image.setAdjustViewBounds(true);
        if (displayMetrics.widthPixels < bitmap.getWidth()) {
            matrix.postScale(scalew, scalew);
        } else {
            matrix.postScale(1 / scalew, 1 / scalew);
        }
        image.setMaxWidth(displayMetrics.widthPixels);
        float ss = displayMetrics.heightPixels > bitmap.getHeight() ? displayMetrics.heightPixels
                : bitmap.getHeight();
        image.setMaxWidth((int) ss);
        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
        }

    }

}
