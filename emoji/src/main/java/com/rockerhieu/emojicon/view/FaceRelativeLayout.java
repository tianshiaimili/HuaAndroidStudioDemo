package com.rockerhieu.emojicon.view;

import java.util.ArrayList;
import java.util.List;

import com.rockerhieu.emojicon.EmojiAdapter;
import com.rockerhieu.emojicon.emoji.Emojicon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.mama.emoji.R;

/**
 * <带表情的自定义输入框>
 *
 * @author fulushan
 * @date 创建时间：2015年03月30日 上午11:00:04
 */
public class FaceRelativeLayout extends RelativeLayout implements OnItemClickListener {

	private Context context;

	/** 表情页的监听事件 */
	private OnEmojiconClickedListener mOnEmojiconClickedListener;

	/** 显示表情页的viewpager */
	private ViewPager vp_face;

	/** 表情页界面集合 */
	private ArrayList<View> pageViews;

	/** 游标显示布局 */
	private LinearLayout layout_point;

	/** 游标点集合 */
	private ArrayList<ImageView> pointViews;

	/** 表情集合 */
	private List<List<Emojicon>> emojis;

	/** 表情包选项按钮 */
	private View optionMamaButton;
	private View optionEmojiButton;
	private boolean usingEmoji = false;
	private boolean singleTab = true;

	public FaceRelativeLayout(Context context) {
		super(context);
		init(context, null);
	}

	public FaceRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public FaceRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		this.context = context;
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FaceRelativeLayout);
			usingEmoji = a.getBoolean(R.styleable.FaceRelativeLayout_usingEmoji, false);
			singleTab = a.getBoolean(R.styleable.FaceRelativeLayout_singleTab, true);
			a.recycle();
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		emojis = FaceConversionUtil.getInstace(usingEmoji).emojiLists;
		onCreate();
	}

	private void onCreate() {
		if(emojis!=null){
			emojis.clear();
		}
		FaceConversionUtil.getInstace(false).ParseData();
		FaceConversionUtil.getInstace(true).ParseData();
		Init_View();
		Init_viewPager();
		Init_Point();
		Init_Data();
		Init_option_buttons();
	}

	/**
	 * 隐藏表情选择框
	 */
	public void hideFaceView() {
		// 隐藏表情选择框
		if (getVisibility() == View.VISIBLE) {
			setVisibility(View.GONE);
		}
	}
	/**
	 * 展示表情选择框
	 */
	public void showFaceView() {
        // 隐藏表情选择框
		if (getVisibility() == View.GONE) {
			setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 初始化控件
	 */
	private void Init_View() {

		vp_face = (ViewPager) findViewById(R.id.vp_contains);
		layout_point = (LinearLayout) findViewById(R.id.iv_image);
		if (!singleTab) {
			optionEmojiButton = findViewById(R.id.option_emoji_button);
			optionMamaButton = findViewById(R.id.option_mama_button);
		}
	}

	/**
	 * 初始化表情包选项按钮
	 */
	private void Init_option_buttons() {

		if (singleTab)
			return;
		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				setOptionToEmoji(view == optionEmojiButton);
			}
		};
		optionMamaButton.setOnClickListener(onClickListener);
		optionEmojiButton.setOnClickListener(onClickListener);
		optionMamaButton.setSelected(true);
	}

	/**
	 * 切换选项包
	 */
	private void setOptionToEmoji(boolean toEmoji) {
		if (singleTab)
			return;
		if (toEmoji && optionEmojiButton.isSelected())
			return;
		if (!toEmoji && optionMamaButton.isSelected())
			return;
		optionMamaButton.setSelected(!toEmoji);
		optionEmojiButton.setSelected(toEmoji);
		usingEmoji = toEmoji;
		emojis = FaceConversionUtil.getInstace(usingEmoji).emojiLists;
		Init_viewPager();
		Init_Point();
		vp_face.setAdapter(new ViewPagerAdapter(pageViews));
		vp_face.setCurrentItem(1);
	}

	/**
	 * 初始化显示表情的viewpager
	 */
	private void Init_viewPager() {
		pageViews = new ArrayList<>();
		// 左侧添加空页
		View nullView1 = new View(context);
		// 设置透明背景
		nullView1.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView1);

		// 中间添加表情页
		for (int i = 0; i < emojis.size(); i++) {
			GridView view = new GridView(context);
			EmojiAdapter adapter = new EmojiAdapter(usingEmoji, context, emojis.get(i));
			view.setAdapter(adapter);
			view.setOnItemClickListener(this);
			if (usingEmoji) {
				view.setNumColumns(7);
				view.setPadding(dpToPx(14), dpToPx(14), dpToPx(14), 0);
			} else {
				view.setNumColumns(6);
				view.setPadding(dpToPx(10), dpToPx(14), dpToPx(10), 0);
			}
			view.setBackgroundColor(Color.TRANSPARENT);
			view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			view.setCacheColorHint(0);
			view.setSelector(new ColorDrawable(Color.TRANSPARENT));
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			view.setGravity(Gravity.CENTER);
			pageViews.add(view);
		}

		 // 右侧添加空页面
		 View nullView2 = new View(context);
		 // 设置透明背景
		 nullView2.setBackgroundColor(Color.TRANSPARENT);
		 pageViews.add(nullView2);
	}

	/**
	 * 初始化游标
	 */
	private void Init_Point() {

		layout_point.removeAllViews();
		pointViews = new ArrayList<>();
		ImageView imageView;
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(context);
			imageView.setBackgroundResource(R.drawable.write_huidic);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 4;
			layoutParams.rightMargin = 4;
			// layoutParams.width = 8;
			// layoutParams.height = 8;
			layout_point.addView(imageView, layoutParams);
			if (i == 0 || i == pageViews.size() - 1) {
				imageView.setVisibility(View.GONE);
			}
			if (i == 1) {
				imageView.setBackgroundResource(R.drawable.write_greendic);
			}
			pointViews.add(imageView);
		}
	}

	/**
	 * 填充数据
	 */
	private void Init_Data() {
		vp_face.setAdapter(new ViewPagerAdapter(pageViews));

		vp_face.setCurrentItem(1);
		vp_face.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// 描绘分页点
				draw_Point(arg0);
				// 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
				if (arg0 == pointViews.size() - 1 || arg0 == 0) {
					if (arg0 == 0) {
						vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
						pointViews.get(1).setBackgroundResource(
								R.drawable.write_greendic);
					} else {
						vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
						pointViews.get(arg0 - 1).setBackgroundResource(
								R.drawable.write_greendic);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	/**
	 * 绘制游标背景
	 */
	public void draw_Point(int index) {
		for (int i = 1; i < pointViews.size(); i++) {
			if (index == i) {
				pointViews.get(i).setBackgroundResource(
						R.drawable.write_greendic);
			} else {
				pointViews.get(i)
						.setBackgroundResource(R.drawable.write_huidic);
			}
		}
	}

	

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		if (mOnEmojiconClickedListener != null) {
			if (position == FaceConversionUtil.getInstace(usingEmoji).pageSize) {
				mOnEmojiconClickedListener.onEmojiconBackspaceClicked(this);
			} else {
				mOnEmojiconClickedListener.onEmojiconClicked((Emojicon) parent
						.getItemAtPosition(position));
			}
			
		}
	}

	public interface OnEmojiconClickedListener {
		void onEmojiconClicked(Emojicon emojicon);

		void onEmojiconBackspaceClicked(View v);
	}

	public void input(EditText editText, Emojicon emojicon, int maxLength) {
		if (editText == null || emojicon == null) {
			return;
		}

		String emojiString = emojicon.getEmoji();
		if (emojiString == null || emojiString.length() == 0)
			return;
		int currentLength = editText.getText().length();
		int emojiLength = emojiString.length();
		int start = editText.getSelectionStart();
		int end = editText.getSelectionEnd();
		if (start < 0) {
			if (currentLength + emojiLength > maxLength)
				return;
			editText.append(emojiString);
		} else {
			if (currentLength + emojiLength - (end - start) > maxLength)
				return;
			editText.getText().replace(Math.min(start, end),
					Math.max(start, end), emojiString, 0, emojiLength);
		}
	}

	public void backspace(EditText editText) {
		KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0,
				0, KeyEvent.KEYCODE_ENDCALL);
		editText.dispatchKeyEvent(event);
	}

//	public OnEmojiconClickedListener getmOnEmojiconClickedListener() {
//		return mOnEmojiconClickedListener;
//	}

	public void setmOnEmojiconClickedListener(
			OnEmojiconClickedListener mOnEmojiconClickedListener) {
		this.mOnEmojiconClickedListener = mOnEmojiconClickedListener;
	}

	private int dpToPx(int dp)
	{
		float density = getResources().getDisplayMetrics().density;
		return Math.round((float)dp * density);
	}
}
