package com.hua.activity.at_demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.bean.User;

import java.util.ArrayList;

public class UserChiceActivity extends Activity {

	private TextView textView;
	private ListView listView;
	private boolean DEBUG = true;
	private ArrayList<User> list = new ArrayList<User>();
	ArrayAdapter adapter = null;
	private Bitmap bitmap;
	private boolean isClick;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice);
		textView = (TextView) findViewById(R.id.textView);
//		textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		textView.setDrawingCacheEnabled(true);

		listView = (ListView) findViewById(R.id.listView);
		for(int i = 0;i<20;i++){
			User user = new User(i+"", "我的名字：name -"+i+"测试"+" " );
			list.add(user);

		}

//		adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,  list);
		listView.setAdapter(new MyAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long arg3) {

				Toast.makeText(getApplicationContext(), "Click position " + position, 300).show();
				user  = (User) arg0.getItemAtPosition(position);
				user.setUser_name("@"+user.getUser_name());
				textView.setText(user.getUser_name());
				textView.setTextColor(Color.BLUE);
				isClick = true;

			}
		});


		textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

				if(isClick && view instanceof  TextView){
					isClick = false;
					Intent intent = new Intent();
					intent.putExtra("user", user);
					intent.putExtra("bitmap",textView.getDrawingCache());
					setResult(200, intent);
					finish();

				}

			}
		});

	}

	// 计算出该TextView中文字的长度(像素)
	public static int getTextViewLength(TextView textView){
		TextPaint paint = textView.getPaint();
		// 得到使用该paint写上text的时候,像素为多少
		int textLength = (int) paint.measureText(textView.getText().toString());
		return textLength;
	}


	class MyAdapter extends BaseAdapter{


		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			HolderView holderView = null;
			if(convertView == null){
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.choice_lis_item, null);
				holderView = new HolderView(convertView);
				convertView.setTag(holderView);

			}else {
				holderView = (HolderView) convertView.getTag();
			}

			holderView.id.setText(list.get(position).getId());
			holderView.name.setText(list.get(position).getUser_name());
//			holderView.name.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//
//					Toast.makeText(getApplicationContext(), "position ："+position,
//						Toast.LENGTH_LONG).show();
//				}
//			});
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

}
