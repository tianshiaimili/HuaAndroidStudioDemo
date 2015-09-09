package com.hua.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hua.R;

/**
 * 只是当作普通的listview 的测试adapter
 * @author Yue
 *
 */
public class TestAdapter extends BaseAdapter{

	private int count = 20;
	
	private Context context;
	
	public TestAdapter(Context tempContext){
		this.context = tempContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.test_adapter_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.textView.setText("the position is "+position);
		
		return convertView;
	}

	class ViewHolder{
		
		TextView textView;
		public ViewHolder(View view){
			textView = (TextView) view.findViewById(R.id.test);
		}
		
	}
	
}
