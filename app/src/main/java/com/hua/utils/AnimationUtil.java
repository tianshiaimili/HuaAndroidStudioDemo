package com.hua.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

import com.hua.R;


/**
 * 
 *用于操作广告动画效果
 */
public class AnimationUtil {


	/**
	 * 设置进入动画
	 * 
	 * @param view
	 * @param visibility
	 */
	public static void getInAnimation(Context context,View view, int visibility) {
		Animation animation;
		if (visibility == View.VISIBLE) {
			animation = AnimationUtils.loadAnimation(context, R.anim.ad_next_in);
		} else {
			animation = AnimationUtils.loadAnimation(context, R.anim.ad_next_out);
		}
		animation.setDuration(500);
		view.setAnimation(animation);
		view.setVisibility(visibility);

	}

	/**
	 * 设置退出动画
	 * 
	 * @param view
	 * @param visibility
	 */
	public static void getOutAnimation(Context context,View view, int visibility) {
		Animation animation;
		if (visibility == View.VISIBLE) {
			animation = AnimationUtils.loadAnimation(context,
					R.anim.ad_previous_in);
		} else {
			animation = AnimationUtils.loadAnimation(context,
					R.anim.ad_previous_out);
		}
		animation.setDuration(500);
		view.setAnimation(animation);
		view.setVisibility(visibility);

	}
	/**
	 * 旋转动画
	 *
	 * @param v
	 * @param fromDegrees 旋转的开始角度
	 * @param toDegrees   ：旋转的结束角度
	 *                    int pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
	 *                    float pivotXValue：X坐标的伸缩值。
	 *                    int pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
	 *                    float pivotYValue：Y坐标的伸缩值。
	 */
	public static void rotateAnimation(View v, float fromDegrees, float toDegrees) {
		if (v == null) {
			return;
		}
		Animation clockwiseAm = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		clockwiseAm.setDuration(500);
		LinearInterpolator lin = new LinearInterpolator();
		clockwiseAm.setInterpolator(lin);
		clockwiseAm.setFillAfter(true);
		v.startAnimation(clockwiseAm);
	}


    public static Animation getDefaultAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(300 / 2);
        return animation;
    }

	public static  void translationY(View view,int startY,int endY){

		ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "translationY", startY, endY);
		anim3.setRepeatMode(ValueAnimator.REVERSE);
//		anim3.setRepeatCount(ValueAnimator.INFINITE);

		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(200);
		animSet.setInterpolator(new LinearInterpolator());
		animSet.play(anim3);
		animSet.start();

	}

	/**
	 * 放大缩小回
	 * @param view
	 */
	public static void scale(final View view)
	{
//	    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
//                0f, 1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
				1.5f, 1f);
		PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
				1.5f, 1f);
		ObjectAnimator animator=  ObjectAnimator.ofPropertyValuesHolder(view,pvhY,pvhZ);
		animator.setDuration(1000).setInterpolator(new OvershootInterpolator());
		animator.start();
	}


	public static void signAnima(final Context context, final View light, final View ico, final View txt, final View desc)
	{
	    PropertyValuesHolder light_pvhX = PropertyValuesHolder.ofFloat("alpha",0f, 1f);
		PropertyValuesHolder light_pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.5f,
				1.5f, 1f);
		PropertyValuesHolder light_pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.5f,
				1.5f, 1f);
		ObjectAnimator animator_light=  ObjectAnimator.ofPropertyValuesHolder(light,light_pvhX,light_pvhY,light_pvhZ);

		/***/
		PropertyValuesHolder ico_pvhX = PropertyValuesHolder.ofFloat("alpha",0f, 1f);
		PropertyValuesHolder ico_pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.5f,
				1.5f, 1f);
		PropertyValuesHolder ico_pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.5f,
				1.5f, 1f);
		ObjectAnimator animator_ico=  ObjectAnimator.ofPropertyValuesHolder(ico,ico_pvhX,ico_pvhY,ico_pvhZ);

		/***/
		PropertyValuesHolder txt_pvhX = PropertyValuesHolder.ofFloat("alpha",0f, 1f);
		PropertyValuesHolder txt_pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.5f,
				1.5f, 1f);
		PropertyValuesHolder txt_pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.5f,
				1.5f, 1f);
		final ObjectAnimator animator_itxt=  ObjectAnimator.ofPropertyValuesHolder(txt,txt_pvhX,txt_pvhY,txt_pvhZ);


		PropertyValuesHolder desc_pvhX = PropertyValuesHolder.ofFloat("alpha",0f, 1f);
		PropertyValuesHolder desc_pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
				1.5f, 1f);
		PropertyValuesHolder desc_pvhZ = PropertyValuesHolder.ofFloat("scaleY",1f,
				1.5f, 1f);
		final ObjectAnimator animator_desc=  ObjectAnimator.ofPropertyValuesHolder(desc,desc_pvhX,desc_pvhY,desc_pvhZ);

		//
		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(1000);
		animSet.setInterpolator(new OvershootInterpolator());
		animSet.play(animator_light).with(animator_ico);
		animSet.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				light.setVisibility(View.VISIBLE);
				ico.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animator animation) {

				AnimatorSet animSet = new AnimatorSet();
				animSet.setDuration(1000);
				animSet.setInterpolator(new BounceInterpolator());
				animSet.play(animator_itxt).with(animator_desc);
				animSet.start();
				txt.setVisibility(View.VISIBLE);
				desc.setVisibility(View.VISIBLE);

				AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context,
						R.animator.message_scalate2);
				animatorSet.setTarget(light);
				animatorSet.start();

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});

		animSet.start();

	}



}
