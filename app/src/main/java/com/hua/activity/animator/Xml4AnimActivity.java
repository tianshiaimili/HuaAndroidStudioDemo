package com.hua.activity.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hua.R;

public class Xml4AnimActivity extends Activity
{

	private ImageView mMv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_for_anim);

		mMv = (ImageView) findViewById(R.id.id_mv);

	}

	public void scaleX(View view)
	{
		// 加载动画
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scalex);
		anim.setTarget(mMv);
		anim.start();
	}

	public void scaleXandScaleY(View view)
	{
		// 加载动画
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scale);
		mMv.setPivotX(0);
		mMv.setPivotY(0);
		//显示的调用invalidate
		mMv.invalidate();
		anim.setTarget(mMv);
		anim.start();
	}
	
	
	public void rotateView(View view)
	{
		// 加载动画
//		translationY 平移  scaleX 缩放  rotationX 旋转
//		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mMv, "rotationX", 0.0F, 360.0F);
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mMv, "scaleX", 1.0F, 2.0F,3.5F);
		objectAnimator.setDuration(500);
		objectAnimator.start();
		
		//		.ofFloat(view, "rotationX", 0.0F, 360.0F)//  
//		         .setDuration(500)//  
//		         .start();  
		
		
	}

}
