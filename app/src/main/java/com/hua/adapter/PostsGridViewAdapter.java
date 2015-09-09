package com.hua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.bean.RecommendMessageBean;

import java.util.List;


public class PostsGridViewAdapter extends BaseAdapter{

	private Context mContext;
	private List<RecommendMessageBean> listBeans;
	public PostsGridViewAdapter(Context context,List<RecommendMessageBean> list){
		this.mContext = context;
		this.listBeans = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listBeans.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.other_option_gridview_item, null);
			holder.option = (TextView) convertView.findViewById(R.id.option);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.option.setText(listBeans.get(position).getMain_title());
		holder.option.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Toast.makeText(mContext, listBeans.get(position).getMain_title(), 300).show();
				
			}
		});
		
		return convertView;
	}

	
	class ViewHolder{
		
		TextView option;
		
	}
	
}
