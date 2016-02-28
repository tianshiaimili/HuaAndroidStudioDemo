package com.hua.image;

/**
 * Copyright (C) 2015 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class CropCircleTransformation implements Transformation<Bitmap> {

  private BitmapPool mBitmapPool;

  public CropCircleTransformation(Context context) {
    this(Glide.get(context).getBitmapPool());
  }

  public CropCircleTransformation(BitmapPool pool) {
    this.mBitmapPool = pool;
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    return circleCrop2(resource);
  }

  private BitmapResource circleCrop(Resource<Bitmap> resource){
    Bitmap source = resource.get();
    int size = Math.min(source.getWidth(), source.getHeight());

    int width = (source.getWidth() - size) / 2;
    int height = (source.getHeight() - size) / 2;

    Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
    if (bitmap == null) {
      bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    BitmapShader shader =
            new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
    if (width != 0 || height != 0) {
      // source isn't square, move viewport to center
      Matrix matrix = new Matrix();
      matrix.setTranslate(-width, -height);
      shader.setLocalMatrix(matrix);
    }
    paint.setShader(shader);
    paint.setAntiAlias(true);

    float r = size / 2f;
    canvas.drawCircle(r, r, r, paint);

    return BitmapResource.obtain(bitmap, mBitmapPool);
  }


  private BitmapResource circleCrop2(Resource<Bitmap> resource){
    Bitmap source = resource.get();
    int size = Math.min(source.getWidth(), source.getHeight());

    int width = (source.getWidth() - size) / 2;
    int height = (source.getHeight() - size) / 2;

    Bitmap bitmap = mBitmapPool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    if (bitmap == null) {
      bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    BitmapShader shader =
            new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
//    if (width != 0 || height != 0) {
//      // source isn't square, move viewport to center
//      Matrix matrix = new Matrix();
//      matrix.setTranslate(-width, -height);
//      shader.setLocalMatrix(matrix);
//    }
//    paint.setShader(shader);
    paint.setAntiAlias(true);

//    float r = size / 2f;
//    canvas.drawCircle(r, r, r, paint);
    RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());

    float radius = Resources.getSystem().getDisplayMetrics().density * 5;
    canvas.drawRoundRect(rectF, radius, radius, paint);

    return BitmapResource.obtain(bitmap, mBitmapPool);
  }


  @Override public String getId() {
    return "CropCircleTransformation()";
  }
}
