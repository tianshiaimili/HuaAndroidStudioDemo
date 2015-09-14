package com.hua.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * 缓存操作类
 * @author hejinxi
 *
 */
public class CacheUtil {

	/**
	 * 项目存储目录
	 */
	private static String DIR = Environment.getExternalStorageDirectory() + File.separator+"mmcircle"+File.separator;
	/**
	 * 发布存储目录
	 */
	public static  String DRAFTIMG = DIR+"draftimg"+File.separator;
	private static CacheUtil cacheUtil;
	private CacheUtil(){
		File dir = new File(DIR);
		 if(dir!=null&&!dir.exists()){    
	    	   dir.mkdir();    
	       } 
		 dir = new File(DRAFTIMG);
		 if(dir!=null&&!dir.exists()){    
			   dir.mkdir();    
		 } 
	}
	
	
	public static synchronized CacheUtil getInstance(){
		if(cacheUtil==null){
			cacheUtil = new CacheUtil();
		}
		return cacheUtil;
	}
	

	/**
	 * 保存草稿图片
	 * @param bm
	 * @param fileName
	 */
	public void saveImgByBitmap(Bitmap bm,String fileName){
		ImgUtil.saveFileByBitmap(bm, fileName);
	}

	/**
	 * 获取草稿图片
	 * @param fileName
	 */
	public Bitmap getBitmapByName(String fileName){
		return ImgUtil.getBitmapByFileName(fileName);

	}

    public static void delete(File file) {
             if (file.isFile()) {
                       file.delete();
                     return;
                }

             if(file.isDirectory()){
                     File[] childFiles = file.listFiles();
                      if (childFiles == null || childFiles.length == 0) {
                            file.delete();
                            return;
                         }

                   for (int i = 0; i < childFiles.length; i++) {
                             delete(childFiles[i]);
                        }
                     file.delete();
                  }
         }
    public static String getCacheSize(Context context){

        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        File file =  imageLoader.getDiskCache().getDirectory();

		//File file2 = ImageLoader.getInstance().getDiskCache().getDirectory();
	
		try {
            long fileSize1 = FileUtils.getFileSize(Glide.getPhotoCacheDir(context));
			long fileSize = FileUtils.getFileSize(file);
			//long fileSize3 = FileUtils.getFileSize(file2);
			long fileSize2 = FileUtils.getFileSize(new File(DRAFTIMG));
			fileSize = fileSize+fileSize2+fileSize1;
			String number = FileSizeFormat.getMbSize(fileSize);
			return number+"M ";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0.00M  ";
	}
	
	/**
	 * 清楚缓存
	 * @param context
	 */
	public static void clearCacheSize(final Context context){
        new Thread(){
            @Override
            public void run() {
//                BitmapAjaxCallback.clearCache();  //Clear the bitmap memcache
//                AQUtility.cleanCacheAsync(context, 0, 0);//Clear File Cache
                final ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                imageLoader.clearDiskCache();
                ACacheUtil.clearAll();
                CacheUtil.delete(Glide.getPhotoCacheDir(context));
                File file = new File(DIR);
                if(file!=null&&file.exists()){
                    CacheUtil.delete(file);
                }
                super.run();
            }
        }.start();

	}
}
