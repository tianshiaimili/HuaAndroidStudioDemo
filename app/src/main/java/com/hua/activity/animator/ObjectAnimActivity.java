package com.hua.activity.animator;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.R;

public class ObjectAnimActivity extends Activity
{

	TextView click;
	ImageView test1,test2,test3;
	AnimatorSet animation1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.object_animator);
		click = (TextView) findViewById(R.id.click);
		click.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				animation1.start();
//				propertyValuesHolder2();
				propertyValuesHolder3();
			}
		});
		test1 = (ImageView) findViewById(R.id.test1);
		test2 = (ImageView) findViewById(R.id.test2);
		test3 = (ImageView) findViewById(R.id.test3);

		test2.setVisibility(View.GONE);
		test3.setVisibility(View.GONE);

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

	public void propertyValuesHolder2()
	{
		animation1 =(AnimatorSet) AnimatorInflater.loadAnimator(ObjectAnimActivity.this,
			R.animator.tutorail_rotate);
		LinearInterpolator lin = new LinearInterpolator();
		animation1.setInterpolator(lin);
		test2.setVisibility(View.VISIBLE);

		animation1.setTarget(test2);
		animation1.start();
		test2.setVisibility(View.VISIBLE);

	}

	public void propertyValuesHolder3()
	{

		PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat("alpha",0.0f, 1f);
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.0f,1.5f,1.0f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.0f,1.5f,1.0f);
		ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(test2,pvhA,pvhX,pvhY);
		objectAnimator1.setDuration(1500).setInterpolator(new OvershootInterpolator());

		PropertyValuesHolder pvhA2 = PropertyValuesHolder.ofFloat("alpha",0.0f, 1f);
		PropertyValuesHolder pvhX2 = PropertyValuesHolder.ofFloat("rotation", 0.0f,359.0f);
		ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(test3,pvhA2,pvhX2);
		objectAnimator1.setDuration(1500).setInterpolator(new OvershootInterpolator());

		PropertyValuesHolder pvhA3 = PropertyValuesHolder.ofFloat("alpha",0.0f, 1f);
		PropertyValuesHolder pvhX3 = PropertyValuesHolder.ofFloat("rotation", 0.0f,359.0f);
		ObjectAnimator objectAnimator3 = ObjectAnimator.ofPropertyValuesHolder(test1,pvhA3,pvhX3);
		objectAnimator3.setDuration(3000).setInterpolator(new OvershootInterpolator());



		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(objectAnimator1).with(objectAnimator2);
		animatorSet.play(objectAnimator3).after(objectAnimator2);
		animatorSet.start();

		test2.setVisibility(View.VISIBLE);
		test3.setVisibility(View.VISIBLE);
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

