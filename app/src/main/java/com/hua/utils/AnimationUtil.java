package com.hua.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.hua.R;


/**
 * 
 * @author 何小喜
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
}
