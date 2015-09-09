package com.hua.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 
 * This view will auto determine the width or height by determining if the
 * height or width is set and scale the other dimension depending on the images
 * dimension
 * 
 * This view also contains an ImageChangeListener which calls changed(boolean
 * isEmpty) once a change has been made to the ImageView
 * 
 * @author Maurycy Wojtowicz
 * 
 */
public class ScaleImageView extends ImageView
{
	private Bitmap currentBitmap;
	private ImageChangeListener imageChangeListener;
	private boolean scaleToWidth = false; // this flag determines if should
											// measure height manually dependent
											// of width

	public ScaleImageView(Context context)
	{
		super(context);
		init();
	}

	public ScaleImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public ScaleImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		this.setScaleType(ScaleType.FIT_XY);
	}

	public void recycle()
	{
		setImageBitmap(null);
		if ((this.currentBitmap == null) || (this.currentBitmap.isRecycled()))
			return;
		this.currentBitmap.recycle();
		this.currentBitmap = null;
	}
 

	@Override
	public void setImageBitmap(Bitmap bm)
	{
		currentBitmap = bm;
		super.setImageBitmap(currentBitmap);
		if (imageChangeListener != null)
			imageChangeListener.changed((currentBitmap == null));
	}

	@Override
	public void setImageDrawable(Drawable d)
	{
		super.setImageDrawable(d);
		if (imageChangeListener != null)
			imageChangeListener.changed((d == null));
	}

	@Override
	public void setImageResource(int id)
	{
		super.setImageResource(id);
	}

	public interface ImageChangeListener
	{
		// a callback for when a change has been made to this imageView
		void changed(boolean isEmpty);
	}

	public ImageChangeListener getImageChangeListener()
	{
		return imageChangeListener;
	}

	public void setImageChangeListener(ImageChangeListener imageChangeListener)
	{
		this.imageChangeListener = imageChangeListener;
	}

	private int imageWidth;
	private int imageHeight;

	public void setImageWidth(int w)
	{
		imageWidth = w;
	}

	public void setImageHeight(int h)
	{
		imageHeight = h;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		/**
		 * if both width and height are set scale width first. modify in future
		 * if necessary
		 */

		if (widthMode == MeasureSpec.EXACTLY
				|| widthMode == MeasureSpec.AT_MOST)
		{
			scaleToWidth = true;
		} else if (heightMode == MeasureSpec.EXACTLY
				|| heightMode == MeasureSpec.AT_MOST)
		{
			scaleToWidth = false;
		} else
			throw new IllegalStateException(
					"width or height needs to be set to match_parent or a specific dimension");

		if (imageWidth == 0)
		{
			// nothing to measure
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		} else
		{
			if (scaleToWidth)
			{
				int iw = imageWidth;
				int ih = imageHeight;
				int heightC = width * ih / iw;
				if (height > 0)
					if (heightC > height)
					{
						// dont let hegiht be greater then set max
						heightC = height;
						width = heightC * iw / ih;
					}

				this.setScaleType(ScaleType.FIT_XY);
				setMeasuredDimension(width, heightC);

			} else
			{
				// need to scale to height instead
				int marg = 0;
				if (getParent() != null)
				{
					if (getParent().getParent() != null)
					{
						marg += ((RelativeLayout) getParent().getParent())
								.getPaddingTop();
						marg += ((RelativeLayout) getParent().getParent())
								.getPaddingBottom();
					}
				}

				int iw = imageWidth;
				int ih = imageHeight;

				width = height * iw / ih;
				height -= marg;
				setMeasuredDimension(width, height);
			}

		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Drawable maiDrawable = getDrawable();
		float mCornerRadius = 8 * getContext().getResources()
				.getDisplayMetrics().density; // 圆角半径
		if (maiDrawable instanceof BitmapDrawable && mCornerRadius > 0)
		{
			Paint paint = ((BitmapDrawable) maiDrawable).getPaint();
			final int color = 0xffffffff;

			final RectF rectF = new RectF(0, 0, getWidth(), getHeight());
			int saveCount = canvas.saveLayer(rectF, null,
					Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
							| Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
							| Canvas.FULL_COLOR_LAYER_SAVE_FLAG
							| Canvas.CLIP_TO_LAYER_SAVE_FLAG);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);

			Xfermode oldMode = paint.getXfermode();
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			super.onDraw(canvas);
			paint.setXfermode(oldMode);
			canvas.restoreToCount(saveCount);
			this.setBackgroundResource(0);
		} else
		{
			super.onDraw(canvas);
		}
	}

}
