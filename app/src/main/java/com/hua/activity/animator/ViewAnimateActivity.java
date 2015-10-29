package com.hua.activity.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import com.hua.R;

public class ViewAnimateActivity extends Activity {
	protected static final String TAG = "ViewAnimateActivity";

	/**改变的view*/
	private ImageView mBlueBall;
	private float mScreenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_animator);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		mBlueBall = (ImageView) findViewById(R.id.id_ball);

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void viewAnim(View view) {
		// need API12
		ViewPropertyAnimator propertyAnimator = mBlueBall.animate();
		mBlueBall.animate()//
				.alpha(0)//
				.y(mScreenHeight / 2).x(mScreenHeight / 2).scaleY(3.5f).setDuration(1000)
				// need API 12
				.withStartAction(new Runnable() {
					@Override
					public void run() {
						Log.e(TAG, "START");
					}
					// need API 16
				}).withEndAction(new Runnable() {

					@Override
					public void run() {
						Log.e(TAG, "END");
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mBlueBall.setY(0);
								mBlueBall.setX(0);
								mBlueBall.setAlpha(1.0f);
							}
						});
					}
				}).start();
	}

	public void propertyValuesHolder(View view) {
		mBlueBall.setVisibility(View.VISIBLE);
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
				0f, 1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
				0, 2f);
		PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
				0, 2f);
		ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mBlueBall, pvhX, pvhY, pvhZ);
//		animator.setRepeatCount(1);
//		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
//		animator.start();

		PropertyValuesHolder pvhX2 = PropertyValuesHolder.ofFloat("scaleX", 1f,
				1f, 2f);
		PropertyValuesHolder pvhY2 = PropertyValuesHolder.ofFloat("scaleY", 1f,
				1f, 2f);
		ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(mBlueBall, pvhX2, pvhY2);
		animator2.setRepeatCount(10);
		animator2.setRepeatMode(ValueAnimator.REVERSE);

		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(2000);
		animSet.setInterpolator(new AnticipateOvershootInterpolator());
		//两个动画同时执行
//		animSet.playTogether(anim1,anim2,anim4);
//		animSet.play(anim1).with(anim2).with(anim4);
		animSet.play(animator2);
//		animSet.playSequentially(animator,animator2);
//		animSet.playSequentially(items)
		animSet.setTarget(mBlueBall);
		animSet.start();

	}


	public void play3(View view){
		mBlueBall.setVisibility(View.VISIBLE);
		AnimatorSet animationBottom =(AnimatorSet) AnimatorInflater.loadAnimator(this,
				R.animator.message_tip_scale);
		animationBottom.setTarget(mBlueBall);
		animationBottom.start();

	}

}
