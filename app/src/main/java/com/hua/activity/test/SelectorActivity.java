package com.hua.activity.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.R;
import com.hua.image.ImageLoadUtil;
import com.hua.utils.SelectorUtil;
import com.hua.view.RoundImageView;


public class SelectorActivity extends Activity{

	private TextView textView;
	private ImageView iv1,iv2,iv3;
	private RoundImageView roundImageView;

	private String url = "http://c.hiphotos.baidu.com/image/pic/item/ac6eddc451da81cb6b1233d05066d01609243132.jpg";
	private String url2 = "http://h.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adc4b317334936acaf2edd9852.jpg";
	private String url3 = "http://a.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82d94b518a6e81800a19d8438c.jpg";
	private String url4 = "http://e.hiphotos.baidu.com/image/pic/item/50da81cb39dbb6fd06b854560a24ab18962b37da.jpg";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selector_layout);
		textView = (TextView) findViewById(R.id.option);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,iv1,url3);
				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,iv2,url2,1);
				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,iv3,url4,2);
				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,roundImageView,url4,false);

			}
		});
		iv1 = (ImageView) findViewById(R.id.iv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		iv3 = (ImageView) findViewById(R.id.iv3);
		roundImageView = (RoundImageView) findViewById(R.id.iv0);
		roundImageView.setType(RoundImageView.TYPE_ROUND);

		setTextViewSelector();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private  void setTextViewSelector(){
		//0xffffffff, 0xffffff00, 0xff0000ff, 0xffff0000
		textView.setTextColor(SelectorUtil.createColorStateListByID(this,R.color.gray12,R.color.white,R.color.white,R.color.gray12));
//		textView.setTextColor(SelectorUtil.createColorStateListByID(this,0xffffffff, 0xffffff00, 0xff0000ff, 0xffff0000));

//		textView.setBackground(SelectorUtil.createDrawableSelector(this,R.drawable.jinghua_label_normal, R.drawable.jinghua_label_press, R.drawable.jinghua_label_press, R.drawable.jinghua_label_normal));
		textView.setBackground(SelectorUtil.createShapeSelectorByID(this, R.dimen.strokeWidth, R.dimen.choiceInterest_radius, R.color.gray12,
				R.color.white, R.color.bar_color));
//		textView.setBackground(SelectorUtil.createColorStateListByID(this,0xffffffff, 0xffffff00, 0xff0000ff, 0xffff0000));

	}


}
