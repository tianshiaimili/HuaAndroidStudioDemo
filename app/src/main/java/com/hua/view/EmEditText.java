/**
 * 
 */
package com.hua.view;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.hua.R;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.MamaIconSpan;
import com.rockerhieu.emojicon.emoji.Mama;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 搜索表情标记替换成表情图片
 * 
 * @author chenshuide 2014年7月17日上午10:45:44 TODO
 */
public class EmEditText extends EmojiconEditText
{
	private int faceSize;
	private Pattern p;
	private Context context;
	private int textSize;

	public EmEditText(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public EmEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public EmEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		// TODO Auto-generated constructor stub
	}

	private void init(Context context) {
		// TODO Auto-generated method stub
		this.context = context;
		textSize = (int) getTextSize();
		faceSize = (int) context.getResources().getDimension(R.dimen.face_height);
		p = Pattern.compile("\\{xiguamamaquan[0-9][0-9]\\}");// 匹配{}

		addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				scan();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * 扫描
	 */
	private void scan() {
		Spannable editable = getText();
		String text = getText().toString();

		// remove spans throughout all text
		MamaIconSpan[] oldSpans = editable.getSpans(0, editable.length(), MamaIconSpan.class);
		for (MamaIconSpan oldSpan : oldSpans)
			editable.removeSpan(oldSpan);

		Matcher m = p.matcher(text);
		while (m.find()) {
			String mamaString = m.group();
			Integer resId = Mama.mamaEmojiMap.get(mamaString);
			if (resId != null) {
				MamaIconSpan span = new MamaIconSpan(context, resId, faceSize, textSize);
				editable.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
	}
}
