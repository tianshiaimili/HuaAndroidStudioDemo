package com.hua.activity.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.utils.DensityUtil;

public class AnimatorSetActivity extends Activity {
//    private ImageView mBlueBall;
    TextView mBlueBall;
    private float mScreenHeight;
    private float mScreenWidth;
    private DensityUtil densityUti;

    private ObjectAnimator anim1;
    private ObjectAnimator anim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_set);

//        mBlueBall = (ImageView) findViewById(R.id.id_ball);
        mBlueBall = (TextView) findViewById(R.id.test);
        mBlueBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimatorSetActivity.this,"lala",300).show();
            }
        });
        densityUti = new DensityUtil(this);
        mScreenHeight = densityUti.getScreenHeight();
        mScreenWidth = densityUti.getScreenWidth();

    }

    public void togetherRun(View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBlueBall, "scaleX",
                1f, 2f);
        anim1.setRepeatMode(ValueAnimator.REVERSE);
        anim1.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mBlueBall, "scaleY",
                1f, 2f);
        anim2.setRepeatMode(ValueAnimator.REVERSE);
        anim2.setRepeatCount(ValueAnimator.INFINITE);
//		ObjectAnimator anim3 = ObjectAnimator.ofFloat(mBlueBall, "translationX",
//				1.0f, 2f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mBlueBall, "translationY", 0, -mScreenHeight / 10);
        anim3.setRepeatMode(ValueAnimator.REVERSE);
        anim3.setRepeatCount(ValueAnimator.INFINITE);


        //
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(mBlueBall, "alpha", 0f, 1f);
        anim4.setRepeatMode(ValueAnimator.REVERSE);
        anim4.setRepeatCount(1);


        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0f, 1f);
        ObjectAnimator anim5 = ObjectAnimator.ofPropertyValuesHolder(mBlueBall, pvhX, pvhY, pvhZ);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(2000);
        animSet.setInterpolator(new AnticipateOvershootInterpolator());
        //两个动画同时执行
//		animSet.playTogether(anim1,anim2,anim4);
        animSet.play(anim1).with(anim2).with(anim4).after(anim5);
//		animSet.play(anim5).after(anim5);
//		animSet.playSequentially(items)
        animSet.start();
    }

    public void playWithAfter(View view) {
        float cx = mBlueBall.getX();
        LogUtils.i("cx--" + cx);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBlueBall, "scaleX",
                1.0f, 2f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mBlueBall, "scaleY",
                1.0f, 2f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mBlueBall,
                "x", cx, 0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(mBlueBall,
                "x", cx);

        ////
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0, 1f);
        ObjectAnimator anim5 = ObjectAnimator.ofPropertyValuesHolder(mBlueBall, pvhX, pvhY, pvhZ);
        ////

        PropertyValuesHolder pvhX2 = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY2 = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0, 1f);
        PropertyValuesHolder pvhZ2 = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0, 1f);
        ObjectAnimator anim6 = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ);

        /**
         * anim1，anim2,anim3同时执行
         * anim4接着执行
         */
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        animSet.play(anim2).with(anim3);
        animSet.play(anim4).after(anim3);
        animSet.play(anim5).after(anim4);
        animSet.play(anim6).after(anim4);
        animSet.setDuration(1000);
        animSet.start();
    }

    public void scaleAction(View view) {


        anim1 = ObjectAnimator.ofFloat(mBlueBall, "scaleX",
                1f, 2f);
        anim1.setRepeatMode(ValueAnimator.REVERSE);
        anim1.setRepeatCount(ValueAnimator.INFINITE);
        anim2 = ObjectAnimator.ofFloat(mBlueBall, "scaleY",
                1f, 2f);
        anim2.setRepeatMode(ValueAnimator.REVERSE);
        anim2.setRepeatCount(ValueAnimator.INFINITE);


       final AnimatorSet animation2=(AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.message_scalate);
        mBlueBall.setVisibility(View.VISIBLE);
        animation2.setTarget(mBlueBall);
        animation2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

//                AnimatorSet animSet = new AnimatorSet();
//                animSet.setDuration(2000);
//                animSet.setInterpolator(new AnticipateOvershootInterpolator());
//                //两个动画同时执行
//                animSet.playTogether(anim1, anim2);
//                animSet.start();

                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(AnimatorSetActivity.this,
                        R.animator.message_scalate2);
                animatorSet.setTarget(mBlueBall);
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animation2.start();


    }

    /**
     *
     android:propertyName
     对view可以设置一下值:
     translationX and translationY:
     These properties control where the View is located
     as a delta from its left and top coordinates which
     are set by its layout container.
     rotation, rotationX, and rotationY:
     These properties control the rotation
     in 2D (rotation property) and 3D around the pivot point.

     scaleX and scaleY:
     These properties control the 2D scaling of a View around
     its pivot point.

     pivotX and pivotY:
     These properties control the location of the pivot point,
     around which the rotation and scaling transforms occur.
     By default, the pivot point is located at the center of
     the object.

     x and y:
     These are simple utility properties to describe
     the final location of the View in its container,
     as a sum of the left and top values and translationX
     and translationY values.

     alpha:
     Represents the alpha transparency on the View.
     This value is 1 (opaque) by default, with a value of 0
     representing full transparency (not visible).

     还可以设置"backgroundColor"等值
     */

}
