package com.hua.activity.animator;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.hua.R;

public class ObjectAnimActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.object_animator);
	}

	public void rotateyAnimRun(final View view)
	{
		ObjectAnimator anim = ObjectAnimator//
				.ofFloat(view, "zhy", 1.0F,  0.2F)//
				.setDuration(500);//
		anim.start();
		
		anim.addUpdateListener(new AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				float cVal = (Float) animation.getAnimatedValue();
				view.setAlpha(cVal);
				view.setScaleX(cVal);
				view.setScaleY(cVal);
				view.setTranslationX(cVal);
				view.setTranslationY(cVal);
			}
		});
	}

	public void propertyValuesHolder(final View view)
	{

	    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.5f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.5f, 1f);

		ObjectAnimator animator=  ObjectAnimator.ofPropertyValuesHolder(view,pvhX,pvhY,pvhZ);
		animator.setDuration(1000).setInterpolator(new OvershootInterpolator());
		animator.start();

//		 ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.25f, 1f).setDuration(500);
//		animX.setInterpolator(new LinearInterpolator());
//		ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.25f, 1f).setDuration(500);
//		AnimatorSet animatorSet = new AnimatorSet();
//		animatorSet.play(animX).with(animY);

	}
	
	// ObjectAnimator anim = ObjectAnimator//
	// .ofFloat(view, "alpha", 1.0f, 0.25f, 0.75f, 0.15f, 0.5f, 0.0f)//
	// .setDuration(5000);
	// anim.setInterpolator(new LinearInterpolator());
	// anim.start();
	// anim.setInterpolator(TimeInterpolator)

}
