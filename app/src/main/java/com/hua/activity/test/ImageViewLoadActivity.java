package com.hua.activity.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hua.R;
import com.hua.adapter.SquareGridViewAdapter;
import com.hua.utils.ImgUtil;
import com.hua.utils.SelectorUtil;
import com.hua.view.CustomGridView;
import com.hua.view.CustomsHorizontalScrollView;
import com.hua.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;


public class ImageViewLoadActivity extends Activity{

	private TextView textView;
	private ImageView iv1,iv2,iv3;
	private RoundImageView roundImageView;
	private CustomGridView gridView;
	private SquareGridViewAdapter adapter;
	private CustomsHorizontalScrollView horizontalScrollView;

	private String url = "http://ico.ooopic.com/ajax/iconpng/?id=53681.png";
	private String url2 = "http://h.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adc4b317334936acaf2edd9852.jpg";
	private String url3 = "http://h.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adc4b317334936acaf2edd9852.jpg";
	private String url4 = "http://e.hiphotos.baidu.com/image/pic/item/50da81cb39dbb6fd06b854560a24ab18962b37da.jpg";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_load_layout);
		textView = (TextView) findViewById(R.id.option);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				ImageLoadUtil.loadImageWithCover(ImageViewLoadActivity.this,roundImageView,url4,false);
//				ImageLoadUtil.loadImageWithCover(ImageViewLoadActivity.this,iv1,url2,0);
//				ImageLoadUtil.loadImageWithCover(ImageViewLoadActivity.this,iv2,url2,1);
//				ImageLoadUtil.loadImageWithCover(ImageViewLoadActivity.this,iv3,url2,2);
				gridView.setAdapter(adapter);
				horizontalScrollView.setData(getList());

			}
		});
//		iv1 = (ImageView) findViewById(R.id.iv1);
//		iv2 = (ImageView) findViewById(R.id.iv2);
//		iv3 = (ImageView) findViewById(R.id.iv3);
		int width = ImgUtil.getWidthPixels((Activity) this, 15);
		gridView = (CustomGridView) findViewById(R.id.gridView);
//		gridView.setVisibility(View.GONE);
		List<String> list = getList();
		adapter = new SquareGridViewAdapter(this, width,list);
		setGridView();

		horizontalScrollView = (CustomsHorizontalScrollView) findViewById(R.id.custom_gridView);
//		horizontalScrollView.setVisibility(View.GONE);

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
		for(int i = 0; i < 16; i++){
			list.add(url3);
		}
		return list;

	}
	/**设置GirdView参数，绑定数据*/
	private void setGridView() {
		int size = getList().size();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int allWidth = (int) (68 * size * density);
		int itemWidth = (int) (65 * density);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				allWidth, LinearLayout.LayoutParams.FILL_PARENT);
		gridView.setLayoutParams(params);
		gridView.setColumnWidth(itemWidth);
		gridView.setHorizontalSpacing(6);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setNumColumns(size);
	}

}
