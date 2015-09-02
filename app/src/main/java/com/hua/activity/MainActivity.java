package com.hua.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.activity.at_demo.TextViewLinkActivity;
import com.hua.activity.at_demo.UserChiceActivity;
import com.hua.bean.User;
import com.hua.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private EditText mEditTextMsg;
	private TextView textView,text3,message;
	private Button button,startButton;
	private ListView listView;
	private boolean DEBUG = true;
	private ArrayAdapter<String> adapter;
	private MAdapter mAdapter;
	private List<String> list = new ArrayList<String>();
	private List<User> mList = new ArrayList<User>();
	private SpannableString mSpan;
	private int idIndex;


	android.os.Handler handler = new android.os.Handler(){
		@Override
		public void handleMessage(Message msg) {
//			super.handleMessage(msg);

			String content = (String) msg.obj;
			message.setText(changeColor(content));


		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView);
		text3 = (TextView) findViewById(R.id.text3);
		message =  (TextView) findViewById(R.id.message);

		listView = (ListView) findViewById(R.id.listView);
		mEditTextMsg = (EditText) findViewById(R.id.editText);
		mEditTextMsg.addTextChangedListener(new EditChangedListener());
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mEditTextMsg.getText().toString() != null) {
					LogUtils.e("???");
//					list.add(0, mEditTextMsg.getText().toString());
					User user = new User("新ID："+idIndex++,mEditTextMsg.getText().toString());
					mList.add(0, user);
//					adapter.notifyDataSetChanged();
					mAdapter.notifyDataSetChanged();
					mEditTextMsg.setText("");
					Message message = new Message();
					message.obj = user.getUser_name();
//					handler.sendMessage(message);
//					if(mSpan != null ) message.setText(mSpan);
				}

			}
		});
		startButton = (Button) findViewById(R.id.startbutton);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), TextViewLinkActivity.class);
				startActivity(intent);
			}
		});
		//
		for (int i = 0; i < 10; i++) {
//			list.add("value - " + i);
			User user = new User("id:"+i,"name is "+i);
			mList.add(user);
		}
//		adapter = new ArrayAdapter<String>(getApplicationContext(),
//				android.R.layout.simple_list_item_1, android.R.id.text1, list);
		mAdapter = new MAdapter(mList);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				Toast.makeText(getApplicationContext(), "选中了："+i,
//						Toast.LENGTH_LONG).show();
			}
		});

		message.setText(changeColor("ClickMe123456"));
//		message.setMovementMethod(LinkMovementMethod.getInstance());

	}

	public SpannableString changeColor(String content){

		SpannableString spannableString = new SpannableString(content);
//		int startIndex = content.indexOf(temp);
//		int endIndex = startIndex + 5;
//		ImageSpan imageSpan = new ImageSpan(getApplicationContext(),button.getDrawingCache());
//		spannableString.setSpan(imageSpan,startIndex, endIndex,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//		spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),0,5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//		spannableString.setSpan(new MURLSpan("http:www.baidu.com"),0,5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new MURLSpan("http://www.baidu.com"), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //网络
		return spannableString;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogUtils.d("the resultCode = " + resultCode);
		if (resultCode == 200) {

			if (data != null) {
				User user = (User) data.getSerializableExtra("user");
				mEditTextMsg.getText().delete(mEditTextMsg.length() - 1, mEditTextMsg.length());
				Bitmap bm = data.getParcelableExtra("bitmap");
				insertIntoEditText(getBitmapMime(user,bm));

			}

		}

	}

	class EditChangedListener implements TextWatcher {
		private CharSequence temp;//
		private int editStart;//
		private int editEnd;//
		private final int charMaxNum = 20;

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {
			temp = s;
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
								  int count) {
			textView.setText("你还可以输入" + (charMaxNum - s.length()) + "个子");
			LogUtils.i("the s is ==" + s.toString());
			LogUtils.e("the start is ==" + start);
			LogUtils.e("the count is ==" + count);
			LogUtils.e("the before is ==" + before);
			if (count == 1 && s.length() >= 0 && s.toString().endsWith("@")) {
				Intent intent = new Intent(getApplicationContext(),
						UserChiceActivity.class);
				startActivityForResult(intent, 100);

			}

		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = mEditTextMsg.getSelectionStart();
			editEnd = mEditTextMsg.getSelectionEnd();
			mEditTextMsg.setSelection(s.length());
			if (temp.length() > charMaxNum) {
				Toast.makeText(getApplicationContext(), "数字够了哦",
						Toast.LENGTH_LONG).show();
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				mEditTextMsg.setText(s);
				mEditTextMsg.setSelection(s.length());
			}

		}
	};

	private SpannableString getBitmapMime(User user,Bitmap bm) {
		String content = user.getUser_name();
		SpannableString spannableString = new SpannableString(content);
		ImageSpan span = new ImageSpan(this, bm);
		//设置字体前景色
		spannableString.setSpan(span, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
//		ss.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//设置图片
//		Drawable drawable = getResources().getDrawable(R.drawable.abc);
//		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//		//设置字体背景色
////		ss.setSpan(new BackgroundColorSpan(Color.CYAN), 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		ss.setSpan(new ImageSpan(drawable), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mSpan = spannableString;
		return spannableString;
	}

	private void insertIntoEditText(SpannableString ss) {
		Editable et = mEditTextMsg.getText();//
		int start = mEditTextMsg.getSelectionStart();
		et.insert(start, ss);//
		mEditTextMsg.setText(et);//
		mEditTextMsg.setSelection(start + ss.length());//
	}

	/**
	 *
	 * @return bitmap
	 */
	public Bitmap stringtoBitmap(String string) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}


	class MAdapter extends BaseAdapter{

		List<User> mList ;
		public  MAdapter(List<User> list ){
			this.mList = list;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int i) {
			return mList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup viewGroup) {
			HolderView holderView = null;
			if(convertView == null){
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.choice_item, null);
				holderView = new HolderView(convertView);
				convertView.setTag(holderView);

			}else {
				holderView = (HolderView) convertView.getTag();
			}

			holderView.id.setText(mList.get(position).getId());
			holderView.id.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(getApplicationContext(),"=id="+position,Toast.LENGTH_SHORT).show();
				}
			});
			if(mList.get(position).getId() != null){
				if(mList.get(position).getUser_name().contains("@")) {
					holderView.name.setText(getUserNameFromMessage(mList.get(position).getUser_name(), mList.get(position),holderView.name));
					holderView.name.setMovementMethod(LinkMovementMethod.getInstance());
				}else {
					holderView.name.setText(mList.get(position).getUser_name());
				}
			}else {
				holderView.name.setText(mList.get(position).getUser_name());
			}
			holderView.name.setClickable(true);
			holderView.name.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(getApplicationContext(),"=name="+position,Toast.LENGTH_SHORT).show();
				}
			});

			return convertView;
		}
	}

	class HolderView{
		TextView id;
		TextView name;
		public HolderView(View convertView) {

			id = (TextView) convertView.findViewById(R.id.user_id);
			name = (TextView) convertView.findViewById(R.id.user_name);

		}
	}


	public SpannableString getUserNameFromMessage(String content, User user,TextView textView){
		String temp = "@";
		SpannableString spannableString = new SpannableString(content);
		int startIndex = content.indexOf(temp);
		int endIndex = startIndex + 5;
//		ImageSpan imageSpan = new ImageSpan(getApplicationContext(),button.getDrawingCache());
//		spannableString.setSpan(imageSpan,startIndex, endIndex,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),startIndex,endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

		MURLSpan murlSpan = new MURLSpan("http:www.baidu.com");
		MClickSpan mClickSpan = new MClickSpan();
//		murlSpan.onClick(textView);
		spannableString.setSpan(mClickSpan, startIndex, endIndex,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;

	}

	class MURLSpan extends  URLSpan{

		public MURLSpan(String url) {
			super(url);
		}

		@Override
		public void onClick(View widget) {
//			super.onClick(widget);
			Toast.makeText(getApplicationContext(),"123456"+widget,Toast.LENGTH_SHORT).show();

		}
	}


	class MClickSpan extends  ClickableSpan{

		@Override
		public void onClick(View view) {
			Toast.makeText(getApplicationContext(),"123456",Toast.LENGTH_SHORT).show();
		}
	}


}
