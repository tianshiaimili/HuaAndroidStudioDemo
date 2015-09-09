package com.hua.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hua.R;
import com.hua.view.ScaleImageView;


public class PostsGalleryAdapter extends BaseAdapter{
	
	private int[] imageId = new int[]{
			R.drawable.bg01,R.drawable.bg02,R.drawable.bg03,
			R.drawable.bg04,R.drawable.bg05
	};
	private Context mContext;
	
	public PostsGalleryAdapter(Context context){ 
		this.mContext = context;
	}
	
	//得到ImageView图像
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder= null;
		if(convertView == null){
			
			holder = new ViewHolder();
			//
			convertView = LayoutInflater.from(mContext).inflate(R.layout.local_option_gallery_item, null);
			holder.local_iv = (ScaleImageView) convertView.findViewById(R.id.local_iv);
			holder.local_tv_des = (TextView) convertView.findViewById(R.id.local_tv_des);
			convertView.setTag(holder);
			
		}else {
			
			holder = (ViewHolder) convertView.getTag();
			
		}
		
		holder.local_iv.setImageResource(imageId[position%imageId.length]);
//		holder.local_tv_des.setText(text)
		
//		//创建图片组件对象
//		ImageView imageview = new ImageView(mContext);
//		//ImageView设置图片放缩方式
//		imageview.setScaleType(ImageView.ScaleType.FIT_XY);
//		//重新设定Layout的宽高 
//		imageview.setLayoutParams(new Gallery.LayoutParams(180, 135));
//		//到strings.xml创建一个Gallery属性(设置图片Itiem背景)
//		//去调用Gallery的属性
//		TypedArray typearray = mContext.obtainStyledAttributes(R.styleable.Gallery);
//		//为ImageView设置背景
//		imageview.setBackgroundResource(typearray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0));
//		//设置图像的内边距
//		imageview.setPadding(5, 0, 5, 0);
//		//设置默认当前显示图像
//		imageview.setImageResource(imageId[position%imageId.length]);
		return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return position;
	}
	
	//得到图像的总数
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		//return imageId.length;
		return Integer.MAX_VALUE;
	}

	
	class ViewHolder{
		
		ScaleImageView local_iv;
		TextView local_tv_des;
		
	}
	
}
