package com.hua.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import cn.mama.activity.HeadImage;
import cn.mama.activity.ImageRotate;
import cn.mama.activityparts.utils.FileUtil;

/**
 * 
 * @author Hejx
 *图片操作类 ，提供压缩，修正图片角度
 */
public class PhotoUtil {
//	public static final String dirName = "gzmama";  //图片放的文件夹
public static final String DIR = Environment.getExternalStorageDirectory()
        + File.separator + "mmcircle" + File.separator;
    public static final String IMGDIR = DIR + "pic" + File.separator;
	public static String name =IMGDIR+ "temp.jpeg";  //存储的文件名称
	public static String ICON = DIR+"icon.jpeg";//头像图片
	public static final int Clip_PIC = 3; // 裁剪图片
	public static final int SELECT_PICTURE = 1; // 选择图片
	public static final int SELECT_PHOTO = 2; // 拍照
	public static int width = 0;
	public static int hight = 0;
	public static Uri u;
	
	public static String fileName ="";
	//转化压缩后的文件
	public static File transImage(String fromFile) {
		Bitmap bitmap = null;
		File myCaptureFile = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;

			bitmap = BitmapFactory.decodeFile(fromFile, opts);
		
			opts.inSampleSize = computeSampleSize(opts, -1, 560 * 700);
			opts.inJustDecodeBounds = false;

			bitmap = BitmapFactory.decodeFile(fromFile, opts);
			
			// 如果系统默认旋转了图片，计算旋转角度，并调整
			int degree = getBitmapDegree(fromFile);
			if (0 != degree)
				bitmap = rotateBitmapByDegree(bitmap, degree);
		
			 File dir = new File(DIR);
		       if(!dir.exists()){    
		    	   dir.mkdir();    
		       }    
		       File dirFile = new File(IMGDIR);
		       if(!dirFile.exists()){    
		    	   dirFile.mkdir();    
		       }    
			 myCaptureFile = new File(name);
			FileOutputStream out = new FileOutputStream(myCaptureFile);
			if(bitmap==null){
				return null;
			}
			width = bitmap.getWidth();
			hight = bitmap.getHeight();
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
				out.flush();
				out.close();
			}
			if (!bitmap.isRecycled()) {
				bitmap.recycle();// 记得释放资源，否则会内存溢出
			}
		//	myCaptureFile = new File(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return myCaptureFile;

	}
	
	/**
	 * 拿到图片旋转角度
	 * @param path
	 * @return
	 */
	public static int getBitmapDegree(String path)
	{
		int degree = 0;
		try
		{
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation)
			{
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return degree;
	}
	
	/**
	 * 将图片按照某个角度进行旋转
	 */
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree)
	{
		Bitmap returnBm = null;

		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try
		{
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e)
		{
		}
		if (returnBm == null)
		{
			returnBm = bm;
		}
		if (bm != returnBm)
		{
			bm.recycle();
			bm = null;
		}
		return returnBm;
	}
	
	//获取缩略图
	public static  Bitmap extractThumbnail(String fileName) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		//Bitmap bitmap = BitmapFactory.(bit,newOpts);;//此时返回bm为空
		BitmapFactory.decodeFile(fileName,newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 70f;//
		float ww = 70f;//
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		Bitmap bitmap = BitmapFactory.decodeFile(fileName,newOpts);
//		return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
		return bitmap;
	}
	//调用系统照相机的方法
	public static File camera(Activity ac){
/*		Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(it,  Activity.DEFAULT_KEYS_DIALER);*/
		File f = null;
		String name = "";
		String status=Environment.getExternalStorageState();
		if(status.equals(Environment.MEDIA_MOUNTED))
		{
		try {
			name = callTime();
		File dir=new File(PhotoUtil.DIR);
		if(!dir.exists())dir.mkdirs();
		 
		Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		f=new File(dir, name+".jpeg");//localTempImgDir和localTempImageFileName是自己定义的名字
		u=Uri.fromFile(f);
		intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
		fileName = f.getAbsolutePath();
		ac.startActivityForResult(intent,2);
		} catch (ActivityNotFoundException  e) {
		// TODO Auto-generated catch block
		ToastUtil.showToast(ac, "没有找到储存目录");
		}
		}else{
			ToastUtil.showToast(ac, "没有储存卡");
		}
		return f;
	}
	
	
	 public  static  String callTime() {

	        long backTime = new Date().getTime();

	        Calendar cal = Calendar.getInstance();

	        cal.setTime(new Date(backTime));

	        int year = cal.get(Calendar.YEAR);

	        int month = cal.get(Calendar.MONTH) + 1;

	        int date = cal.get(Calendar.DAY_OF_MONTH);

	        int hour = cal.get(Calendar.HOUR_OF_DAY);

	        int minute = cal.get(Calendar.MINUTE);

	        int second = cal.get(Calendar.SECOND);

	        String time = "" + year + month + date + hour + minute + second;

	        return time;

	  }
	/**
	 * 动态计算图片缩放大小的方法
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	
   //修正图片的角度
	private static Bitmap turnImage(String fileName, Bitmap bitmap) {
		Bitmap result = null;
		ExifInterface exifInterface = null;
		try {
			exifInterface = new ExifInterface(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int orc = exifInterface.getAttributeInt(
                 ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
		// 然后旋转：
		int degree = 0;
		if (orc == ExifInterface.ORIENTATION_ROTATE_90) {
			degree = 0;
		} else if (orc == ExifInterface.ORIENTATION_ROTATE_180) {
			degree = 0;
		} else if (orc == ExifInterface.ORIENTATION_ROTATE_270) {
			degree = 0;
		}else{
			degree = 0;
		}
		if (degree!=0&&bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degree, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
			result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);

			return result;
		} else

			return bitmap;
	}
	
	public static void getPhoto(Activity context){
		Intent localIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		context.startActivityForResult(	Intent.createChooser(localIntent, "选择图片"),1);
			
	}
	public static void getPhotos(Activity context,Intent intent,int RequestCode){
		 
		context.startActivityForResult(intent, RequestCode);
	}
	
	public static void startHeadZoom(Activity context,Uri uri,float w2hValue) {
		Intent intent = new Intent(context, HeadImage.class);
		intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("w2hValue",w2hValue);
		context.startActivityForResult(intent, Clip_PIC);
	}

    public static void startHeadZoom(Activity context,Uri uri) {
        Intent intent = new Intent(context, HeadImage.class);
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("w2hValue",1);
        context.startActivityForResult(intent, Clip_PIC);
    }
	
	public static void startPhotoZoom(Activity context,Uri uri){
		Intent intent = new Intent(context, ImageRotate.class);
		intent.setDataAndType(uri, "image/jpeg");
		context.startActivityForResult(intent,Clip_PIC);
	}

    //转化压缩后的文件 拿到绝对路径
    public static File transImageGetPath(String fromFile) {
        Bitmap bitmap = null;
        File myCaptureFile = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;

            bitmap = BitmapFactory.decodeFile(fromFile, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 760 * 980);
            opts.inJustDecodeBounds = false;

            bitmap = BitmapFactory.decodeFile(fromFile, opts);

            // 如果系统默认旋转了图片，计算旋转角度，并调整
            int degree = getBitmapDegree(fromFile);
            if (0 != degree)
                bitmap = rotateBitmapByDegree(bitmap, degree);

            File dir = new File(PhotoUtil.DIR);
            if(!dir.exists()){
                dir.mkdir();
            }
            File dirFile = new File(PhotoUtil.IMGDIR);
            if(!dirFile.exists()){
                dirFile.mkdir();
            }

            //重新生成文件名
            String fileName = FileUtil.getTimeMillis();

            myCaptureFile = new File(PhotoUtil.IMGDIR+ UUID.randomUUID().toString().replaceAll("-", "")+".jpeg");
            FileOutputStream out = new FileOutputStream(myCaptureFile);
            if(bitmap==null){
                return null;
            }
            width = bitmap.getWidth();
            hight = bitmap.getHeight();
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();// 记得释放资源，否则会内存溢出
            }
            //	myCaptureFile = new File(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return myCaptureFile;

    }
}
