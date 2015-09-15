package com.hua.http;

import android.content.Context;

import com.hua.R;
import com.hua.utils.LogUtils;
import com.hua.utils.ToastUtil;

public class ResponseListener<T> {

    private Context mAppContext;

    public ResponseListener(Context ctx) {
        this.mAppContext = ctx;
    }

//	public void onLoadCacheStart() {
//	}

//	private void onLoadCacheComplete(T result) {
//	}

	/**
	 * DO nothing by default!
	 */
	public void onNetworkComplete() {
	}

	/**
	 * 接口返回数据成功. DO nothing by default!
	 * 
	 * @param url
	 * @param json
	 */
	protected void onPtSucc(String url, T json) {
        LogUtils.i("Http", "url>" + url + " json>" + json);
	}

	/**
	 * 接口返回出错信息. 默认以Toast提示返回的出错信息，子类可重写该处理逻辑.
	 * 
	 * @param url
	 * @param errorMsg
	 */
	protected void onPtError(String url, Result.ErrorMsg errorMsg) {
        LogUtils.i("Http", "url>" + url + " error>" + errorMsg.getMsg());
		ToastUtil.showToast(mAppContext, errorMsg.getMsg());
		
	}

    /**
     * 请求数据失败时回调，默认以toast方式显示出错信息。magic模式下同时显示错误的具体描述信息。
     * @param errorType 出错类型 {@link ErrorType}
     * @param errorDesc 错误描述
     */
    protected void onFail(int errorType, String errorDesc) {
    	 String extraInfo = "";
//        String extraInfo = AppSetting.getInstance(mAppContext).isMagicMode() ? "  \n" + errorDesc:"";
        switch (errorType) {
            case ErrorType.NO_CONNECTION:
            	ToastUtil.showToast(mAppContext,mAppContext.getString(R.string.network_no_connect) + extraInfo);
                break;
            case ErrorType.TIME_OUT:
//                ToastUtil.showToast(mAppContext,mAppContext.getString(R.string.network_status_fail) + extraInfo);
                break;
            case ErrorType.NETWORK_ERROR:
                ToastUtil.showToast(mAppContext,mAppContext.getString(R.string.network_no_connect) + extraInfo);
                break;
            case ErrorType.SERVER_ERROR:
//                ToastUtil.showToast(mAppContext,mAppContext.getString(R.string.network_return_fail) + extraInfo);
                break;
            default:
//                ToastUtil.showToast(mAppContext,mAppContext.getString(R.string.network_return_fail) + extraInfo);
                break;
        }
    }

    /**
     * 定义了请求出错类型
     */
    public static class ErrorType {

        /**
         * 其他类型的错误
         */
        public static final int OTHER_ERROR = 0x1;

        /**
         * 网络未连接
         */
        public static final int NO_CONNECTION = 0x2;

        /**
         * 网络超时，包括连接超时或响应超时
         */
        public static final int TIME_OUT = 0x3;

        /**
         * 其他网络错误
         */
        public static final int NETWORK_ERROR = 0x4;

        /**
         * 服务器返回错误
         */
        public static final int SERVER_ERROR = 0x5;
    }

}
