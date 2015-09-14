package com.hua.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;

import com.hua.activity.MMApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 
 * @author tangzc 图片处理util
 * 
 */
public class ImgUtil {

	private static final int OPTIONS_SCALE_UP = 0x1;
	private static final int OPTIONS_NONE = 0x0;
	public static final int OPTIONS_RECYCLE_INPUT = 0x2;

	/*
	 * 裁剪图片方法实现
	 */
	public static void startPhotoZoom(Activity ac, Uri uri, int requestCode) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		ac.startActivityForResult(intent, requestCode);
	}

	/*
	 * 获取裁剪后的图片
	 */
	public static File getCutoutFile(Intent picdata) {
		File file = null;
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			photo.compress(CompressFormat.PNG, 100, os);
			byte[] bytes = os.toByteArray();
			file = getFileFromBytes(bytes,
					Environment.getExternalStorageDirectory() + "/head.jpg");
		}
		return file;
	}

	/**
	 * 获取缩略图
	 * 
	 * @param bit
	 * @param reqWidth
	 * @param reqHeight
	 * @return Bitmap
	 */
	@SuppressLint("NewApi")
	public static Bitmap gethumbnai(Bitmap bit, int reqWidth, int reqHeight) {
		return ThumbnailUtils.extractThumbnail(bit, reqWidth, reqHeight,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	}

	// /**
	// * 读取图片属性：旋转的角度
	// * @param path 图片绝对路径
	// * @return degree旋转的角度
	// */
	//
	// public static int readPictureDegree(String path) {
	// int degree = 0;
	// try {
	// ExifInterface exifInterface = new ExifInterface(path);
	// int orientation =
	// exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
	// ExifInterface.ORIENTATION_NORMAL);
	// switch (orientation) {
	// case ExifInterface.ORIENTATION_ROTATE_90:
	// degree = 90;
	// break;
	// case ExifInterface.ORIENTATION_ROTATE_180:
	// degree = 180;
	// break;
	// case ExifInterface.ORIENTATION_ROTATE_270:
	// degree = 270;
	// break;
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return degree;
	// }
	//
	//
	//
	// /** 把图片旋转正
	// * @param angle
	// * @param bitmap
	// * @return Bitmap
	// */
	//
	// public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
	// //旋转图片 动作
	// Matrix matrix = new Matrix();;
	// matrix.postRotate(angle);
	// // 创建新的图片
	// Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
	// bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	// return resizedBitmap;
	// }
	//
	//
	//

	// 把字节数组保存为一个文件
	public static File getFileFromBytes(byte[] b, String outputFile) {
		File ret = null;
		BufferedOutputStream stream = null;
		try {
			ret = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	public static byte[] shotScreen(View v) {
		byte[] buff = null;
		// SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd_HH-mm-ss",
		// Locale.US);
		// String fname = MMApplication.DIR +
		// sdf.format(Calendar.getInstance().getTimeInMillis()) + ".png";

		Bitmap bitmap = getScreenBitmap(v);
		if (bitmap != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 50, stream);// (0 -
																	// 100)压缩文件
			buff = stream.toByteArray();
			System.out.println("bitmap got!");
			return buff;
		} else {
			return null;
		}

	}

	public static Bitmap getScreenBitmap(View v) {
		View view = v.getRootView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		bitmap = compressImage(bitmap);
		return bitmap;
	}

	/**
	 * 将Bitmap压缩到一定程度
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		Bitmap bitmap;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(CompressFormat.JPEG, 90, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;// 每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(
					baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		} catch (Exception e) {
			bitmap = image;
		}

		return bitmap;
	}

	// 根据Bitmap转化为byte
	public static byte[] shotScreen(Bitmap bitmap) {
		byte[] buff = null;
		// SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd_HH-mm-ss",
		// Locale.US);
		// String fname = MMApplication.DIR +
		// sdf.format(Calendar.getInstance().getTimeInMillis()) + ".png";
		if (bitmap != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, stream);// (0 -
																		// 100)压缩文件
			buff = stream.toByteArray();

			return buff;
		} else {
			return null;
		}

	}

	/**
	 * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
	 *
	 * @param bytes
	 * @return
	 */
	public static int bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		if (returnValue > 2) {
			return 8;
		} else if (returnValue > 1) {
			return 6;
		}
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		if (returnValue >= 700) {
			return 4;
		} else if (returnValue >= 500) {
			return 3;
		} else if (returnValue >= 300) {
			return 2;
		} else {
			return 1;
		}
	}

	/***
	 * Creates a centered bitmap of the desired size.
	 *
	 * @param source
	 *            original bitmap source
	 * @param width
	 *            targeted width
	 * @param height
	 *            targeted height
	 */
	public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
		return extractThumbnail(source, width, height, OPTIONS_NONE);
	}

	/***
	 * Creates a centered bitmap of the desired size.
	 *
	 * @param source
	 *            original bitmap source
	 * @param width
	 *            targeted width
	 * @param height
	 *            targeted height
	 * @param options
	 *            options used during thumbnail extraction
	 */
	public static Bitmap extractThumbnail(Bitmap source, int width, int height,
			int options) {
		if (source == null) {
			return null;
		}

		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap thumbnail = transform(matrix, source, width, height,
				OPTIONS_SCALE_UP | options);
		return thumbnail;
	}

	/***
	 * Transform source Bitmap to targeted width and height.
	 */
	private static Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, int options) {
		boolean scaleUp = (options & OPTIONS_SCALE_UP) != 0;
		boolean recycle = (options & OPTIONS_RECYCLE_INPUT) != 0;

		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/**
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			if (recycle) {
				source.recycle();
			}
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		if (recycle && b1 != source) {
			source.recycle();
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);

		if (b2 != b1) {
			if (recycle || b1 != source) {
				b1.recycle();
			}
		}

		return b2;
	}

	/**
	 * 获取需要的屏幕宽度
	 *
	 * @param ctx
	 * @param cutDown
	 *            需要减少的距离
	 * @return px
	 */
	public static int getWidthPixels(Activity ctx, int cutDown) {
		DisplayMetrics dm = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 计算两边的距离
		return dm.widthPixels - dip2px(ctx, cutDown);
	}

	/**
	 * dip to px
	 *
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}


	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
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

	/**
	 *
	 * 质量压缩方法
	 *
	 * @param bitmap 源
	 * @param options 开始压缩的质量 《=100
	 * @return 压缩之后的bitmap
	 */
	public static Bitmap compressImage(Bitmap bitmap, int options) {
		Bitmap bit = null;
		try {
			if (null == bitmap) {
				return bit;
			}
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, options, bout);// 质量压缩
																		// 100
																		// 表示不压缩
			System.out.println("*****************大少****************:"
					+ bout.toByteArray().length / 1024);
			ByteArrayInputStream isBm = new ByteArrayInputStream(
					bout.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			bit = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			bout.flush();
			bout.close();
			isBm.close();
			if (!bitmap.isRecycled()) {
				bitmap.recycle();// 记得释放资源，否则会内存溢出
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bit;
	}

	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩)
	 *
	 * @param srcPath
	 *            SD卡路径
	 * @param reqWidth
	 *            需要的宽度
	 * @param reqHeight
	 *            需要的高度
	 * @return Bitmap
	 */
	public static Bitmap CompressImageByPath(String srcPath, int reqWidth,
			int reqHeight) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;// 只是读图片的属性
		BitmapFactory.decodeFile(srcPath, op);
		if (reqWidth == 0 && reqHeight == 0) {
			reqWidth = op.outWidth;
			reqHeight = op.outHeight;
		}
		op.inSampleSize = computeSampleSize(op, -1, reqWidth * reqHeight);// 设置缩放比例
		op.inJustDecodeBounds = false;
		op.inPreferredConfig = Config.RGB_565;
		return compressImage(BitmapFactory.decodeFile(srcPath, op), 80);// 压缩好比例大小后再进行质量压缩

	}

	/**
	 * 根据Bitmap。文件名保存文件
	 *
	 * @param bm
	 * @param fileName
	 */
	public static void saveFileByBitmap(Bitmap bm, String fileName) {
		if (bm == null)
			return;
		File file = new File(fileName);
		try {
			if (file == null) {
				return;
			}
			FileOutputStream out = new FileOutputStream(file);
			if (bm.compress(CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据Bitmap。文件名保存文件
	 * @param fileName
	 */
	public static Bitmap getBitmapByFileName(String fileName) {
		Bitmap bm;
		if (fileName == null)
			return null;

		File file = new File(fileName);

		if (file == null || !file.exists())
			return null;

		bm = BitmapFactory.decodeFile(file.getAbsolutePath());
		return bm;
	}


	/**
	 * 保存网络图片到本地
	 *
	 * @param picDir 保存图片的目录文件
	 * @throws IOException
	 */
	public static void saveImgToSD(String picDir, Bitmap bm) throws IOException {
		File dir = new File(PhotoUtil.DIR);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File dirFile = new File(picDir);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File saveFile = new File(picDir + new Date().getTime() + ".jpeg");
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(saveFile));
		bm.compress(CompressFormat.JPEG, 100, bos);
		bos.flush();
		bos.close();
	}



	/**
	 * 获取网络上的图片
	 *
	 * @param path
	 */
	public static byte[] getImageData(String path) {
		byte[] data = null;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15 * 1000);
			InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
			byte[] buffer = new byte[1024];
			int len = 0;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((len = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.close();
			inputStream.close();
			data = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
        int options = 100;
        //图片大于32K微信无法分享
        while (output.toByteArray().length > 32000) {
            output.reset();
            bmp.compress(CompressFormat.JPEG, options, output);
            options -= 10;
        }
        if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//        System.out.println("result.length ============ " + result.length);
        return result;
	}

	/*
	 * 保存我的头像 bitmap转换成文件-图片
	 */
	public static void saveMyBitmap(Context context, Bitmap mBitmap) {
		try {
			File f = FileUtils.createFile(PhotoUtil.ICON);
			FileOutputStream fOut = new FileOutputStream(f);
			mBitmap.compress(CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();
			PreferenceUtils.setUserSharePref(context,
					PreferenceUtils.Avatar_file_path, PhotoUtil.ICON);
		} catch (Exception e) {

		}
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 保存文件
	 *
	 * @param bmp
	 * @param fileName
	 * @return
	 */
	public static boolean saveBitmapFile(Context ctx, Bitmap bmp,
			String fileName) {
		try {
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			bmp.compress(CompressFormat.JPEG, 100,
					localByteArrayOutputStream);
			File localFile = new File(PhotoUtil.DIR);
			if (!localFile.exists())
				localFile.mkdirs();
			String str2 = PhotoUtil.DIR + "/" + fileName + ".jpg";

			// 刷新图片，用户点开图片或分享图片时能查看到
			ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
					Uri.fromFile(new File(str2))));

			System.out.println("fName : " + str2);
			FileOutputStream localFileOutputStream = new FileOutputStream(str2);
			localFileOutputStream.write(localByteArrayOutputStream
					.toByteArray());
			localFileOutputStream.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

    public static File saveImageFile(Context context,File file,String fileName) {
        if(file == null)
            return null;
        File localFile = new File(PhotoUtil.DIR);
        if (!localFile.exists())
            localFile.mkdirs();
        String str2 = PhotoUtil.DIR +  fileName + ".jpg";
        File newFile = new File(str2);
        try {
        if(!newFile.exists()){
            newFile.createNewFile();
        }
        file.renameTo(newFile);
        // 其次把文件插入到系统图库

//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    newFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(newFile)));/**/
        return newFile;
    }

	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		float dpi;
		try {
			dpi = MMApplication.getAppContext().getResources().getDisplayMetrics().density;
		} catch (Exception e) {
			dpi = 1;
		}
		float circleStrokeW = dpi * 1;
		float circleW = dpi * 2f;

		bitmap = roundBitmap(bitmap);
		int bitmapW = bitmap.getWidth();

		int dstW = (int) (bitmapW + 2 * circleW);
		int dstR = dstW / 2;

		Bitmap dstBitmap = Bitmap.createBitmap(dstW, dstW, Config.ARGB_8888);
		Canvas canvas = new Canvas(dstBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		// 绘制圆形填充区域
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(dstR, dstR, dstR, paint);

		// 绘制圆形描边区域
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(circleStrokeW);
		paint.setColor(0xffeaeaea);
		canvas.drawCircle(dstR, dstR, dstR - circleStrokeW / 2.f, paint);

		// 设定裁剪区，绘制圆形图片
		canvas.drawBitmap(bitmap, circleW, circleW, paint);
		return dstBitmap;
	}
	
	/**
	 * 裁剪内切圆(取左上角区域)
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap roundBitmap(Bitmap bitmap) {
		int bitmapW = bitmap.getWidth();
		int bitmapH = bitmap.getHeight();

		// 计算图片中心点
		int bitmapCx = bitmapW / 2;
		int bitmapCy = bitmapH / 2;

		// 计算内切圆直径和半径
		int innerCircleD = bitmapW < bitmapH ? bitmapW : bitmapH;
		int innerCircleR = innerCircleD / 2;

		Bitmap dstBitmap = Bitmap.createBitmap(innerCircleD, innerCircleD,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(dstBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		// 目标图片中心点和半径
		int dstD = dstBitmap.getWidth();
		int dstR = dstD / 2;
		int dstCx = dstR;
		int dstCy = dstR;

		// 设定裁剪区，绘制圆形图片
		canvas.drawCircle(dstCx, dstCy, dstR, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0, 0, paint);

		return dstBitmap;
	}
}
