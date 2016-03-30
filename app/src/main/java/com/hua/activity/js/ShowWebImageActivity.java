package com.hua.activity.js;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.image.ImageLoadUtil;

import java.io.IOException;

public class ShowWebImageActivity extends Activity {
	private TextView imageTextView = null;
	private String imagePath = null;
	private ZoomableImageView imageView = null;
	private ImageView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webimage);
		this.imagePath = getIntent().getStringExtra("image");

		this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
		imageTextView.setText(this.imagePath);
		imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);
		test = (ImageView) findViewById(R.id.test);

		ImageLoadUtil.loadImageWithCover(this,test,imagePath);
		Toast.makeText(this,imagePath,Toast.LENGTH_LONG).show();



//		new Runnable(){
//			@Override
//			public void run() {
//
//				try {
//					imageView.setImageBitmap(((BitmapDrawable) ShowWebImageActivity.loadImageFromUrl(imagePath)).getBitmap());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}.run();

	}

	public void loadImageFromUrl(String url) throws IOException {


//		Glide.with(ShowWebImageActivity.this).load(url).listener(new RequestListener<String, GlideDrawable>() {
//			@Override
//			public boolean onException(Exception e, String s, Target<GlideDrawable> glideDrawableTarget, boolean b) {
//				return false;
//			}
//
//			@Override
//			public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> glideDrawableTarget, boolean b, boolean b2) {
//				return false;
//			}
//		}).error(R.drawable.cover)
//				.override(PhoneInfoUtil.widthPixels, PhoneInfoUtil.heightPixels).into(imageView);

	}
}
