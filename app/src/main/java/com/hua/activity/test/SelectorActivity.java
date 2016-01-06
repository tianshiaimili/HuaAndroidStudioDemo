package com.hua.activity.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.hua.R;
import com.hua.utils.SelectorUtil;


public class SelectorActivity extends Activity{

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selector_layout);
		textView = (TextView) findViewById(R.id.option);
		setTextViewSelector();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private  void setTextViewSelector(){
		//0xffffffff, 0xffffff00, 0xff0000ff, 0xffff0000
		textView.setTextColor(SelectorUtil.createColorStateListByID(this,R.color.gray12,R.color.white,R.color.white,R.color.gray12));
//		textView.setTextColor(SelectorUtil.createColorStateListByID(this,0xffffffff, 0xffffff00, 0xff0000ff, 0xffff0000));

//		textView.setBackground(SelectorUtil.createDrawableSelector(this,R.drawable.jinghua_label_normal, R.drawable.jinghua_label_press, R.drawable.jinghua_label_press, R.drawable.jinghua_label_normal));
		textView.setBackground(SelectorUtil.createShapeSelectorByID(this,R.dimen.strokeWidth,R.dimen.choiceInterest_radius,R.color.gray12,
				R.color.white,R.color.bar_color));
//		textView.setBackground(SelectorUtil.createColorStateListByID(this,0xffffffff, 0xffffff00, 0xff0000ff, 0xffff0000));

	}

}
