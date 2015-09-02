package com.hua.activity.at_demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hua.R;
import com.hua.bean.BookEntity;
import com.hua.interfaces.ScreenListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity extends Activity {

	/** 执行增加字符串的操作 */
	private Button button;
	private ArrayList<BookEntity> mList = new ArrayList<BookEntity>();
	private EditText editText;
	private int TEXT_CHANGE_LISTENER_FLAG = 0;
	private ScreenListener screenListener;
	private MyTextWatcher watcher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
		editText = (EditText) findViewById(R.id.edit_text);
		button = (Button) findViewById(R.id.add_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int nextInt = new Random().nextInt(100);
				String str = "#测试测试" + nextInt + "# ";
				editText.setText(editText.getText());
				editText.append(str);
				editText.setSelection(editText.getText().toString().length());
				mList.add(new BookEntity(str, nextInt+""));
			}
		});

		/** 监听删除按键，执行删除动作 */
		editText.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) { //当为删除键并且是按下动作时执行
					int selectionStart = editText.getSelectionStart();
					int lastPos = 0;
					for (int i = 0; i < mList.size(); i++) { //循环遍历整个输入框的所有字符
						if ((lastPos = editText.getText().toString().indexOf(mList.get(i).getBookName(), lastPos)) != -1) {
							if (selectionStart > lastPos && selectionStart <= (lastPos + mList.get(i).getBookName().length())) {
								String sss = editText.getText().toString();
								editText.setText(sss.substring(0, lastPos) + sss.substring(lastPos + mList.get(i).getBookName().length())); //字符串替换，删掉符合条件的字符串
								mList.remove(i); //删除对应实体
								editText.setSelection(lastPos); //设置光标位置
								return true;
							}
						} else {
							lastPos += ("#" + mList.get(i).getBookName() + "#").length();
						}
					}
				}
				return false;
			}
		});

		editText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("TAG", ((EditText) v).getSelectionStart() + "");
				int selectionStart = ((EditText) v).getSelectionStart();
				int lastPos = 0;
				for (int i = 0; i < mList.size(); i++) {
					if ((lastPos = editText.getText().toString().indexOf(mList.get(i).getBookName(), lastPos)) != -1) {
						if (selectionStart >= lastPos && selectionStart <= (lastPos + mList.get(i).getBookName().length())) {
							editText.setSelection(lastPos + mList.get(i).getBookName().length());
						}
					} else {
						lastPos += ("#" + mList.get(i).getBookName() + "#").length();
					}
				}
			}
		});

		watcher = new MyTextWatcher(mList);

		/** 监听文字变化，并重新设置颜色 */
		if (TEXT_CHANGE_LISTENER_FLAG == 0) {
			editText.addTextChangedListener(watcher);
			TEXT_CHANGE_LISTENER_FLAG = 1;
		}

		screenListener = new ScreenListener(this);
		screenListener.begin(new ScreenListener.ScreenStateListener() {

			@Override
			public void onUserPresent() {
				Log.e("onUserPresent", "onUserPresent");
			}

			@Override
			public void onScreenOn() {
				Log.e("onScreenOn", "onScreenOn");
				if (TEXT_CHANGE_LISTENER_FLAG == 0) {
					editText.addTextChangedListener(watcher);
					TEXT_CHANGE_LISTENER_FLAG = 1;
				}
			}

			@Override
			public void onScreenOff() {
				Log.e("onScreenOff", "onScreenOff");
			}
		});


		}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		screenListener.unregisterListener();
	}

	class MyTextWatcher implements TextWatcher {
		List<BookEntity> bookList;
		public MyTextWatcher(List<BookEntity> list){
			this.bookList = list;
		}

		@Override
		public synchronized void afterTextChanged(Editable s) {
//			AddNewArticleUI.this.etWriteDynamic.removeTextChangedListener(watcher);
			TEXT_CHANGE_LISTENER_FLAG = 0;
			int findPos = 0;
			int copyPos = 0;
			String sText = s.toString();
			List<Integer> spanIndexes = new ArrayList<Integer>();
			s.clear();
			for (int i = 0; i < bookList.size(); i++) {
				String tempBookName = "#" + bookList.get(i).getBookName() + "#";
				if ((findPos = sText.indexOf(tempBookName, findPos)) != -1) {
					spanIndexes.add(findPos);//bookName 的开始索引，键值为偶数，从0开始
					spanIndexes.add(findPos + tempBookName.length()); //bookName 的结束索引，键值为奇数，从1开始
				}
			}
			if (spanIndexes != null && spanIndexes.size() != 0) {
				for (int i = 0; i < spanIndexes.size(); i++) {
					if (i % 2 == 0) {
						s.append(sText.substring(copyPos, spanIndexes.get(i)));
					} else {
						Spanned htmlText = Html.fromHtml("<font color='blue'>" + sText.substring(copyPos, spanIndexes.get(i)) + "</font>");
						s.append(htmlText);
					}
					copyPos = spanIndexes.get(i);
				}
				s.append(sText.substring(copyPos));
			} else {
				s.append(sText);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
	}

}
