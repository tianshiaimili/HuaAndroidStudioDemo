package com.hua.activity.animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.hua.R;

public class ValueAnimatorActivity extends Activity
{
	protected static final String TAG = "MainActivity3";

	private ImageView mBlueBall;

	private float mScreenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.animator_activity_main);

		mBlueBall = (ImageView) findViewById(R.id.id_ball);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;

	}

	/**
	 * 自由落体
	 * 
	 * @param view
	 */
	public void verticalRun(View view)
	{
//		ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight
//				- mBlueBall.getHeight());
//		animator.setTarget(mBlueBall);
//		animator.setDuration(1000).start();
//		// animator.setInterpolator(value)
//		animator.addUpdateListener(new AnimatorUpdateListener()
//		{
//			@Override
//			public void onAnimationUpdate(ValueAnimator animation)
//			{
//				mBlueBall.setTranslationY((Float) animation.getAnimatedValue());
//			}
//		});
		
		////////////////////ObjectAnimator 比较简单点
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBlueBall, "translationY", 0, mScreenHeight
				- mBlueBall.getHeight()-20);
		objectAnimator.setDuration(500);
		objectAnimator.setInterpolator(new BounceInterpolator());
		objectAnimator.start();
		
		
	}

	/**
	 * 抛物线
	 * 
	 * @param view
	 */
	public void paowuxian(View view)
	{

		ValueAnimator valueAnimator = new ValueAnimator();
		valueAnimator.setDuration(3000);
		valueAnimator.setObjectValues(new PointF(0, 0));
		valueAnimator.setInterpolator(new LinearInterpolator());
//		valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
//		{
//			// fraction = t / duration
//			@Override
//			public PointF evaluate(float fraction, PointF startValue,
//					PointF endValue)
//			{
////				LogUtils.e(TAG, fraction * 3 + "");
//				LogUtils.d("startValue--"+startValue);
//				// x方向200px/s ，则y方向0.5 * g * t (g = 100px / s*s)
//				PointF point = new PointF();
//				point.x = 200 * fraction * 3;
//				point.y = 0.5f * 100 * (fraction * 3) * (fraction * 3);
//				return point;
//			}
//		});
		valueAnimator.setEvaluator(new BezierEvaluator());

		valueAnimator.start();
		valueAnimator.addUpdateListener(new AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				PointF point = (PointF) animation.getAnimatedValue();
				mBlueBall.setX(point.x);
				mBlueBall.setY(point.y);

			}
		});
	}

	public void fadeOut(View view)
	{
		ObjectAnimator anim = ObjectAnimator.ofFloat(mBlueBall, "alpha", 0.5f);
		
		anim.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				LogUtils.e(TAG, "onAnimationEnd");
				ViewGroup parent = (ViewGroup) mBlueBall.getParent();
				if (parent != null)
					parent.removeView(mBlueBall);
			}
		});
		
		anim.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				LogUtils.e(TAG, "onAnimationStart");
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
				// TODO Auto-generated method stub
				LogUtils.e(TAG, "onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				LogUtils.e(TAG, "onAnimationEnd------------");
				ViewGroup parent = (ViewGroup) mBlueBall.getParent();
				if (parent != null)
					parent.removeView(mBlueBall);
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
				// TODO Auto-generated method stub
				LogUtils.e(TAG, "onAnimationCancel");
			}
		});
		anim.start();
	}
	
	public void onClick(View view)
	{
	}

	
	
	class BezierEvaluator implements TypeEvaluator<PointF>{
		 
        @Override
        public PointF evaluate(float fraction, PointF startValue,
                PointF endValue) {
            final float t = fraction;
//            float oneMinusT = 1.0f - t;
            float oneMinusT =t;
            PointF point = new PointF();
            PointF point0 = (PointF)startValue;
             
            PointF point1 = new PointF();
            point1.set(500, 0);
             
            PointF point2 = new PointF();
            point2.set(0, 300);
             
            PointF point3 = (PointF)endValue;
             
            LogUtils.d("fraction--"+fraction);
            LogUtils.d("point1.x--"+point1.x);
            
            point.x = oneMinusT * oneMinusT * oneMinusT 
                    + 3 * oneMinusT * oneMinusT * t * (point1.x)
                    + 3 * oneMinusT * t * t * (point2.x)
                    + t * t * t * (point3.x);
             
            point.y = oneMinusT * oneMinusT * oneMinusT 
                    + 3 * oneMinusT * oneMinusT * t * (point1.y)
                    + 3 * oneMinusT * t * t * (point2.y)
                    + t * t * t * (point3.y);     
//            point.x = 200 * fraction * 3;
//			point.y = 0.5f * 100 * (fraction * 3) * (fraction * 3);
            
            return point;
        }   
    }
	
}
