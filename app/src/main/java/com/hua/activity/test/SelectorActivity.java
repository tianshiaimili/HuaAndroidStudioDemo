package com.hua.activity.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.R;
import com.hua.adapter.SquareGridViewAdapter;
import com.hua.image.ImageLoadUtil;
import com.hua.utils.SelectorUtil;
import com.hua.view.CustomGridView;
import com.hua.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;


public class SelectorActivity extends Activity{

	private TextView textView;
	private ImageView iv1,iv2,iv3;
	private RoundImageView roundImageView;
	private CustomGridView gridView;
	private SquareGridViewAdapter adapter;

	private String url = "http://ico.ooopic.com/ajax/iconpng/?id=53681.png";
	private String url2 = "http://h.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adc4b317334936acaf2edd9852.jpg";
	private String url3 = "http://newspaper.jfdaily.com/xwcb/files/20140325/m_732060.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selector_layout);
		textView = (TextView) findViewById(R.id.option);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,roundImageView,url2,false);
				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,iv1,url2,0);
				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,iv2,url2,1);
				ImageLoadUtil.loadImageWithCover(SelectorActivity.this,iv3,url2,2);

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

	private List<String> getList(){

		List<String> list = new ArrayList<>();
		for(int i = 0; i < 3; i++){
			list.add(url2);
		}

		return list;

	}


}
