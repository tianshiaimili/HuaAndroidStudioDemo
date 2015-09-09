package com.hua.view;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * 针对Kitkat无法选取文件的兼容模式.
 * 包含该WebView的Activity需重写onActivityResult，并在onActivityResult中通知webview的onActivityResult.
 * Created by vince on 5/29/15.
 */
public class KitkatCompatWebview extends WebView {

    public static final int REQUEST_CHOOSE_FILE = 8888;
    private final String imgDir = Environment.getExternalStorageDirectory() + "/mmcircle/";
    private int limitImgSize = 800;//图片限制大小，KB为单位
    private int limitPixel = 1000;//图片限制尺寸

    private CompatInterface mCompatInterface;

    // Webview内部的按钮控制对象
    private ZoomButtonsController zoomController = null;

    public KitkatCompatWebview(Context context) {
        super(context);
        init();
    }

    public KitkatCompatWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KitkatCompatWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public KitkatCompatWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public KitkatCompatWebview(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init();
    }

    private void init() {
        // 只在4.4系统中注入androidCompat接口
        disableZoomController();
        if ("4.4.1".equals(Build.VERSION.RELEASE) || "4.4.2".equals(Build.VERSION.RELEASE)) {
            mCompatInterface = new CompatInterface();
            addJavascriptInterface(mCompatInterface, "androidCompat");
        }
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            mCompatInterface = new CompatInterface();
//            addJavascriptInterface(mCompatInterface, "androidCompat");
//        }
    }

    class CompatInterface {

        private String mReceivedId;

        /**
         * 页面中input标签点击时调用该方法
         *
         * @param id 由页面定义，客户端回调页面时回传该id(通常用于标识某个input标签)
         */
        @JavascriptInterface
        public void chooseFileOnKitkat(String id) {
            mReceivedId = id;
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((Activity) getContext()).startActivityForResult(intent, REQUEST_CHOOSE_FILE);
        }


        /**
         * 选取图片后通知页面，回调页面的androidCompatFileChoosed(id, base64_file)方法.
         */
        private void notifyChoosed(String file) {
            String base64 = encodeBase64File(whetherCompressImage(file));
            int suffixIndex = file.lastIndexOf(".");
            String suffix = "jpg";
            if (suffixIndex > 0) {
                suffix = file.substring(suffixIndex + 1);
            }
            loadUrl("javascript:androidCompatFileChoosed('" + mReceivedId + "','data:image/" + suffix + ";base64," + base64 + "')");
//            loadUrl("javascript:androidCompatFileChoosed('" + mReceivedId + "','" + base64 + "')");
        }
        private String encodeBase64File(String path) {
            String base64 = null;
            FileInputStream input = null;
            try {
                File file = new File(path);
                input = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                int read, count = 0, length = buffer.length - count;
                while ((read = input.read(buffer, count, length)) >= 0) {
                    count += read;
                    length = buffer.length - count;
                    if (length <= 0) {
                        break;
                    }
                }
                base64 = Base64.encodeToString(buffer, Base64.NO_WRAP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return base64;
        }

    }

    /**
     * activity接收到onActivityResult需通知webview的onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCompatInterface == null) {
            return;
        }
        if (requestCode == REQUEST_CHOOSE_FILE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            String picturePath = filePathByContent(data.getData());
            if (!TextUtils.isEmpty(picturePath)) {
                mCompatInterface.notifyChoosed(picturePath);
            }
        }
    }

    public Uri getFileUri(Uri result){
        if(result!=null) {
            if (ContentResolver.SCHEME_CONTENT.contains(result.getScheme())) {
                String path = filePathByContent(result);
                if(path==null){
                    return result;
                }
                if (!path.contains("file://")) {
                    path = "file://" + path;
                }
                result = Uri.parse(path);
            }
        }
        return result;
    }
    protected String filePathByContent(Uri uri){
        String picturePath;
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getContext().getContentResolver().query(uri, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        picturePath = c.getString(columnIndex);
        c.close();
        return picturePath;
    }

    public static class KitkatCompatWebChromeClient extends WebChromeClient {


        // Android > 4.1.1 调用这个方法
        public final void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//            if (Build.VERSION.SDK_INT != Build.VERSION_CODES.KITKAT) {
                openImageChooser(uploadMsg, acceptType, capture);
//            }
        }

        // 3.0 + 调用这个方法
        public final void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openImageChooser(uploadMsg, acceptType, null);
        }

        // Android < 3.0 调用这个方法
        public final void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openImageChooser(uploadMsg, null, null);
        }

        /**
         * 选择文件统一使用这个回调方法
         *
         * @param uploadMsg
         * @param acceptType
         * @param capture
         */
        public void openImageChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            if ("4.4.1".equals(Build.VERSION.RELEASE) || "4.4.2".equals(Build.VERSION.RELEASE)) {
                return;
            }
        }
    }

    // 使得控制按钮不可用
    @SuppressLint("NewApi")
    private void disableZoomController() {
        // API version 大于11的时候，SDK提供了屏蔽缩放按钮的方法
        if (Build.VERSION.SDK_INT >= 11) {
            this.getSettings().setBuiltInZoomControls(true);
            this.getSettings().setDisplayZoomControls(false);
        } else {
            // 如果是11- 的版本使用JAVA中的映射的办法
            getControlls();
        }
    }


    private void getControlls() {
        try {
            Class webview = Class.forName("android.webkit.WebView");
            Method method = webview.getMethod("getZoomButtonsController");
            zoomController = (ZoomButtonsController) method.invoke(this, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        super.onTouchEvent(ev);
        if (zoomController != null) {
            // 隐藏按钮
            // Hide the controlls AFTER they where made visible by the default
            // implementation.
            zoomController.setVisible(false);
        }
        return true;
    }

    /**
     * 判断图片大小是否进行压缩
     * @param fileStr
     * @return
     */
    private String whetherCompressImage(String fileStr) {
        File file = new File(fileStr);
        if (file.exists() && file.length() / 1024 > limitImgSize) {
            return compressImage(fileStr);
        }
        return fileStr;
    }

    /**
     * 按比例缩小图片的像素对图片进行压缩
     * @param imgPath
     * @return
     */
    private String compressImage(String imgPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;
        int inSampleSize = 1;
        //缩放比,用高或者宽其中较大的一个数据进行计算
        if (width > height && width > limitPixel) {
            inSampleSize = width / limitPixel;
        } else if (width < height && height > limitPixel) {
            inSampleSize = height / limitPixel;
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        newOpts.inSampleSize = inSampleSize;//设置采样率
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 如果系统默认旋转了图片，计算旋转角度，并调整
//        int degree = PhotoUtil.getBitmapDegree(imgPath);
//        if (0 != degree) {
////            bitmap = PhotoUtil.rotateBitmapByDegree(bitmap, degree);
//        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            File dir = new File(imgDir);
            if(!dir.exists()){
                dir.mkdir();
            }
            imgPath = imgDir + "web_upload_img.jpg";
            FileOutputStream fos = new FileOutputStream(new File(imgPath));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgPath;
    }

}
