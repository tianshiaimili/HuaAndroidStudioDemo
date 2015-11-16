package com.hua.activity.html.upload;

import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * ReWebChomeClient
 *
 * @Author KenChung
 */
public class ReWebChomeClient extends WebChromeClient {

    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public ReWebChomeClient(OpenFileChooserCallBack openFileChooserCallBack) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        mOpenFileChooserCallBack.openFilesChooserCallBack(filePathCallback,"");
//        selectImg();
        return  true;
    }

    //For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    public interface OpenFileChooserCallBack {
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);
        void openFilesChooserCallBack(ValueCallback<Uri[]> uploadMsg, String acceptType);
    }

    private void LollipopVersion( ValueCallback<Uri[]> filePathCallback) {
//        mLollipopUploadMessage = filePathCallback;
        selectImg();
    }

    private void selectImg(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "完成操作需要使用"), FILECHOOSER_RESULTCODE);
    }

}
