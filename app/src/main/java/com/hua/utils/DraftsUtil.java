package com.hua.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hua.bean.DraftBean;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 草稿操作类
 */
public class DraftsUtil {

	private static boolean  isOpen = true;
	private static int ImageNum = 0;
	

 
	 
	/**
	 * 保存草稿对话框
	 * 
	 * @param context
	 * @param type 1为圈子。0为同城
	 */
	public static void isSaveDialog(final Activity context,final String type,final DraftBean bean) {
		if(!isOpen){
			return ;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("保存").setMessage("是否要保存为草稿?").setPositiveButton("保存", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			 
				ACacheUtil.put(type, bean);
				
				dialog.dismiss();
				context.finish();
				
			}
		}).setNegativeButton("不保存", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				ACacheUtil.clear(type);
				dialog.dismiss();
				context.finish();
			}
		});
		builder.create().setCanceledOnTouchOutside(false);
		builder.show();
		((InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE))
		.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	 
	/**
	 * 保存内容
	 * @param key   1为圈子。0为同城
	 */
	public static void  onSave(String key,DraftBean bean){
		if(!isOpen){
			return ;
		}
		ACacheUtil.put(key, bean);
		
	}
 
	 
	
	 
	 
	/**
	 * 将内容中的图片获取
	 * @param context
	 * @param ed
	 * @return
	 */
	public static int  getContent(Activity context,DraftBean bean,EditText ed){
		if(bean==null){
			return 0;
		}
		return getContent(context, bean.getContent(), ed);
		 
	}
	/**
	 * 将内容中的图片获取
	 * @param context
	 * @param key
	 * @param ed
	 * @return
	 */
	public static int  getContent(Activity context,String content,EditText ed){
		ImageNum = 0;
		 
		if(StringUtil.isNull(content)){
			ed.setText("");
			return ImageNum;
		}
		String texts[] = content.split("\\[attachimg\\].*?\\[\\/attachimg\\]"); //文字按[img]http://...[/img]进行分组
		ArrayList<String> list_text = new ArrayList<String>();
		//把文字加入list中
		for(int j=0;j<texts.length;j++){
			list_text.add(texts[j]);
		}
		Pattern p = Pattern.compile("\\[attachimg\\].*?\\[\\/attachimg\\]");
		ArrayList<String> list_img = new ArrayList<String>();
		Matcher mc = p.matcher(content);
		while(mc.find()){
			list_img.add(mc.group());  //图片地址
		}
		/*	ArrayList<String> list_face = new ArrayList<String>();
		p = Pattern.compile("\\{([a-z0-9]*)\\}");
		 mc = p.matcher(content);
		while(mc.find()){
			list_face.add(mc.group());  //图片地址
		}*/
		
		ArrayList<String> list_mgs = new ArrayList<String>();
		int img_position = 0;
		for(int k=0;k<list_text.size();k++){
			String text = list_text.get(k);
			if(text.length()>0 && !text.equals("")){
				list_mgs.add(text);
			}
			if(img_position<list_img.size()){
				list_mgs.add(list_img.get(img_position));
				img_position++;
			}
			
		}
		while(img_position<list_img.size()){
			list_mgs.add(list_img.get(img_position));
			img_position++;
		}
		for (int q = 0; q < list_mgs.size(); q++) {
			String text = list_mgs.get(q);
			Pattern pp = Pattern.compile("\\[attachimg\\].*?\\[\\/attachimg\\]");
			Matcher m = pp.matcher(text);
			if(m.find()){
				int index = text.indexOf("[attachimg]");
				int last = text.lastIndexOf("[");
				if(index!=-1&&last!=-1){
					text = text.substring("[attachimg]".length(), last);
				}
				Bitmap bimap =  getDraftBitmap(context,text);
				if(bimap!=null){
					AddContext(context, bimap, text, ed);
				}
				
			} else{
				AddContext(text,ed);
			}
		}
		return ImageNum;
	}
	/*
	 * 添加图片到输入框中
	 */
	private static void AddContext(Context cActivity,Bitmap bm, String aid,EditText eText) {
		// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
		SpannableString spannableString = new SpannableString("[attachimg]"+aid+"[/attachimg]"); // 表情代表的字符串
		int start = eText.getSelectionStart();
		int end = eText.getSelectionEnd();
		ImageSpan imageSpan = new ImageSpan(cActivity, bm);
		spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);// 表情占用多少字符

		// 将随机获得的图像追加到EditText控件的最后

		if (start == end) {//
			eText.getText().insert(start, spannableString);
		} else {// 有内容选中
			eText.getText().replace(start, end, spannableString);
		}
		ImageNum++;
	}

	/*
	 * 添加字体到输入框中
	 */
	private static void AddContext(String content,EditText eText) {
		// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
		int start = eText.getSelectionStart();
		int end = eText.getSelectionEnd();
		
		// 将随机获得的图像追加到EditText控件的最后
		
		if (start == end) {//
			eText.getText().insert(start, content);
		} else {// 有内容选中
			eText.getText().replace(start, end, content);
		}
		
	}
	
	public static void removeDraft(String key){
		if(!isOpen){
			return ;
		}
		ACacheUtil.clear(key);
	}
	/**
	 * 保存草稿图片
	 * @param bm
	 * @param fileName
	 */
	public static void saveDraftImg(Context ct,Bitmap bm,String fileName){
		if(!StringUtil.isNull(fileName)){
			fileName = fileName.replaceAll("/","-");
		}
		fileName = CacheUtil.DRAFTIMG+fileName+".jpg";
		CacheUtil.getInstance().saveImgByBitmap(bm, fileName);
	}
	
	/**
	 * 获取草稿图片
	 * @param bm
	 * @param fileName
	 */
	public static Bitmap getDraftBitmap(Context ct,String fileName){
		if(!StringUtil.isNull(fileName)){
			fileName = fileName.replaceAll("/","-");
		}
		fileName = CacheUtil.DRAFTIMG+fileName+".jpg";
		return CacheUtil.getInstance().getBitmapByName(fileName);
		
	}
}
