package com.hua.http;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.hua.activity.MMApplication;
import com.hua.constant.CommonVariables;
import com.hua.http.security.Encrypt2;
import com.hua.utils.ApplicationUtil;
import com.hua.utils.PhoneInfoUtil;
import com.hua.utils.PreferenceUtils;
import com.hua.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 */
public class NewRequest<T> extends Request<Result> {

    /**
     * 默认的连接超时和响应超时时间
     */
    public static final int DEFAULT_TIME_OUT = 8000;

    private Class<T> mClassOfT;

    private ResponseListener mmResponseListener;

    private String mRequestUrl;

    private Map<String, Object> mPostParams;

    private Priority priority;

    private Map<String,String> mHeadParams;

    private String uid;


    private boolean showToast = true;

    int token = Encrypt2.KEY_MM_COMMON;


    public int getToken() {
        return token;
    }

    public NewRequest setToken(int token) {
        this.token = token;
        return this;
    }


    public NewRequest setHaveToken(boolean haveToken) {
        this.haveToken = haveToken;
        return this;
    }

    private boolean haveToken = false; //如果设置为ture的话。就代表不用加token的参数

    public boolean isHaveToken() {
        return haveToken;
    }

    public boolean isShowToast() {
        return showToast;
    }

    public NewRequest setShowToast(boolean showToast) {
        this.showToast = showToast;
        return this;
    }

    /**
     * 构建get请求
     *
     * @param url
     */
    public NewRequest(String url, Class<T> classOfT, ResponseListener ptResponseListener) {
        this(false, url, classOfT, ptResponseListener);
    }

    /**
     * 构建http请求
     *
     * @param post false为get请求,true为post请求
     * @param url
     */
    public NewRequest(boolean post, String url, Class<T> classOfT, ResponseListener ptResponseListener) {
        this(post, url, classOfT, ptResponseListener, DEFAULT_TIME_OUT);
    }

    /**
     * 构建http post 请求
     *
     * @param url
     */
    public NewRequest(String url, Map<String, Object> params, Class<T> classOfT, ResponseListener ptResponseListener) {
        this(true, url, classOfT, ptResponseListener, DEFAULT_TIME_OUT);
        this.mPostParams = params;
    }

    /**
     * 构建http请求
     *
     * @param post false为get请求,true为post请求
     * @param url
     */
    private NewRequest(boolean post, String url, Class<T> classOfT, ResponseListener ptResponseListener, int timeout) {
        super(post ? Method.POST : Method.GET, url, null);

        this.mClassOfT = classOfT;
        this.mmResponseListener = ptResponseListener;
        this.mRequestUrl = url;

        //设置请求超时
        setRetryPolicy(new DefaultRetryPolicy(timeout, 0, 1f));
//        uid = UserInfoUtil.getUserInfo(MMApplication.getAppContext()).getUid();
    }

    @Override
    public Priority getPriority() {
        if (priority != null) {
            return priority;
        } else {
            return super.getPriority();
        }
    }

    /**
     * 设置请求优先级,{@link com.android.volley.Request.Priority}
     *
     * @param priority
     */
    public NewRequest setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * 设置post请求参数
     *
     * @param params
     */
    public NewRequest setPostParams(Map<String, Object> params) {
        mPostParams = getMap(params);
        return this;
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mPostParams != null) {
            Map<String, String> tempParams = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : mPostParams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                tempParams.put(key, (String) value);
            }
            return tempParams;
        }

        return super.getParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
//        version = ApplicationUtil.getInstance(MMApplication.getAppContext()).getNowVersion();
//        String userAgentValue = "mmq/"+version+" (android "+currentapiVersion+";"+MMApplication.widthPixels+"*"+MMApplication.heightPixels+")";
        if(mHeadParams!=null){
//            mHeadParams.put(UserAgent,userAgentValue);
            return mHeadParams;
        }
        Map<String,String> map = new HashMap<String, String>();
//        map.put(UserAgent,userAgentValue);
        map.put("Accept-Encoding", "gzip,deflate");
        return map;
    }

    /**
     * 设置头部一些信息
     * @param params
     * @return
     */
    public NewRequest setHeadParams(Map<String, String> params){
        mHeadParams = params;
        return this;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            try {
                parsed = new String(response.data,"UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                parsed = "";
            }
        }
        Result<T> result = parseJson(parsed);

        Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
        // TODO 自定义缓存时间后面版本考虑实现
//        long now = System.currentTimeMillis();
//        // never expire, need explicit setting
//        cacheEntry.ttl = now + 100L * 365 * 24 * 3600 * 1000;
//        cacheEntry.softTtl = now - 1;

        return Response.success(result, cacheEntry);
    }

    private Result<T> parseJson(String json) {

        Result<T> result = new Result<T>();
        Gson gson = new Gson();
        try {
            if (mClassOfT.equals(String.class)) {
                if(isHaveToken()){
                    result.setStatus(Result.STATUS_OK);
                    result.setData((T) json);
                    return result;
                }else{
                    JSONObject jObject = new JSONObject(json);
                    int status = jObject.getInt("status");
                    result.setStatus(status);
                    switch (result.getStatus()){
                        case Result.STATUS_OK:
                            result.setData((T) json);
                            return result;
                        case Result.STATUS_ERR:
                            String errmsgJson = jObject.optString("errmsg");
                            if (TextUtils.isEmpty(errmsgJson)){
                                errmsgJson = jObject.optString("errormsg");
                            }
                            Result.ErrorMsg errMsg = gson.fromJson(errmsgJson,Result.ErrorMsg.class);
                            result.setErrmsg(errMsg);
                            return result;
                        default:
                            result.setStatus(Result.STATUS_UNKNOWN);
                            result.setUnknownBody(new Result.UnknownBody(json));
                            return result;
                    }

                }
            }
            JSONObject jObject = new JSONObject(json);
            int status = jObject.getInt("status");

            result.setStatus(status);
            switch (result.getStatus()) {
                case Result.STATUS_OK:
                    try {
                        JSONArray array = jObject.getJSONArray("data");

                        // 未发生异常说明data是数组
                        result.setArrayResult(true);
                        List<T> ts = new ArrayList<T>(array.length());
                        for (int i = 0; i < array.length(); i++) {
                            ts.add(gson.fromJson(array.get(i).toString(), mClassOfT));
                        }
                        result.setArrayData(ts);
                    } catch (JSONException e) {
                        //data是单个对象
                        result.setArrayResult(false);
                        T t = gson.fromJson(jObject.optString("data"), mClassOfT);
                        result.setData(t);
                    }
                    break;
                case Result.STATUS_ERR:
                    String errmsgJson = jObject.optString("errmsg");
                    if (TextUtils.isEmpty(errmsgJson)){
                        errmsgJson = jObject.optString("errormsg");
                    }
                    Result.ErrorMsg errMsg = gson.fromJson(errmsgJson,Result.ErrorMsg.class);
                    result.setErrmsg(errMsg);
                    break;
                default:
                    result.setStatus(Result.STATUS_UNKNOWN);
                    result.setUnknownBody(new Result.UnknownBody(json));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();

            result.setStatus(Result.STATUS_UNKNOWN);
            result.setUnknownBody(new Result.UnknownBody(json));
        }

        return result;
    }

    @Override
    protected void deliverResponse(Result result) {
        mmResponseListener.onNetworkComplete();

        switch (result.getStatus()) {
            case Result.STATUS_OK:
                if (result.isArrayResult()) {
                    if (result.getArrayData().size() == 0) {
                        mmResponseListener.onPtSucc(mRequestUrl, null);
                    } else {
                        mmResponseListener.onPtSucc(mRequestUrl, result.getArrayData());
                    }
                } else {
                    mmResponseListener.onPtSucc(mRequestUrl, result.getData());
                }
                break;
            case Result.STATUS_ERR:
                if(isShowToast()){
                    mmResponseListener.onPtError(mRequestUrl, result.getErrmsg());
                }

                break;
            case Result.STATUS_UNKNOWN:
                if(isShowToast()){
                   mmResponseListener.onFail(ResponseListener.ErrorType.OTHER_ERROR, "Unknown pt json status:" + result.getStatus());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);

        mmResponseListener.onNetworkComplete();

        int errorType;
        if (error instanceof NoConnectionError) {
            errorType = ResponseListener.ErrorType.NO_CONNECTION;
        } else if (error instanceof TimeoutError) {
            errorType = ResponseListener.ErrorType.TIME_OUT;
        } else if (error instanceof NetworkError) {
            errorType = ResponseListener.ErrorType.NETWORK_ERROR;
        } else if (error instanceof ServerError) {
            errorType = ResponseListener.ErrorType.SERVER_ERROR;
        } else {
            errorType = ResponseListener.ErrorType.OTHER_ERROR;
        }

        String errDesc;
        if (error.networkResponse != null) {
            errDesc = error.getClass().getSimpleName() + " code=" + error.networkResponse.statusCode;
        } else {
            errDesc = error.getClass().getSimpleName() + " " + error.getMessage();
        }

        if(isShowToast()){
            mmResponseListener.onFail(errorType, errDesc);
        }
    }

    private Map<String, Object> getMap( Map<String, ?> temp){
        Map<String, Object> map = new HashMap<String, Object>();
        if(!temp.containsKey("uid")){
            if(StringUtil.isNull(uid)){
                uid = "0";
            }
            map.put("uid", uid);
        }
        for (Map.Entry<String, ?> entry : temp.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue()==null?"": entry.getValue().toString();
            if(!StringUtil.isNull(value)){
                map.put(key, value);
            }
        }

        if(isHaveToken()){
            return map;
        }

        map.put("source", CommonVariables.SOURCE);
        map.put("version", ApplicationUtil.getInstance(MMApplication.getAppContext()).getNowVersion());
        map.put("t", PreferenceUtils.getNowTime(MMApplication.getAppContext()));
        map.put("device_id", PhoneInfoUtil.getInstance(MMApplication.getAppContext()).getDeviceId());
        switch (getToken()) {

            case Encrypt2.KEY_MM_COMMON:
                map.put("token", Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.KEY_MM_COMMON));
                break;
            case Encrypt2.KEY_MM_CITY:
                map.put("token", Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.KEY_MM_COMMON));
                break;
            case Encrypt2.KEY_MM_BUY:
                map.put("appname","mmq");
                map.put("appversion", ApplicationUtil.getInstance(MMApplication.getAppContext()).getNowVersion());
                map.put("token",  Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.KEY_MM_BUY));
                break;
            case Encrypt2.KEY_MM_DUC:
                map.put("token", Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.KEY_MM_DUC));
                break;
            case Encrypt2.KEY_MM_HUODONG:
//                map.put("code", PassportUtil.getCode(MMApplication.getAppContext()));
                map.put("token",  Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.KEY_MM_HUODONG));
                break;
            case Encrypt2.kEY_MM_FRIEND:
                map.put("ver", ApplicationUtil.getInstance(MMApplication.getAppContext()).getNowVersion());
                map.put("app",CommonVariables.APP);
                map.put("token", Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.kEY_MM_FRIEND));
                break;
            case Encrypt2.KEY_USERINFO:

                map.put("token", Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.KEY_USERINFO));
                break;
            case Encrypt2.KEY_MM_OTHER_COMMON:
                map.put("token", Encrypt2.genToken(map,Encrypt2.Algorithm1, Encrypt2.KEY_MM_OTHER_COMMON));
            default:
                break;

        }
        return map;
    }

    @Override
    public String getCacheKey() {
        String url = super.getCacheKey();
        url = filterURLParams(url);
        return url;
    }

    private String filterURLParams(String url) {
        if (url == null) {
            return null;
        }

        StringBuffer urlBuffer = new StringBuffer();

        int index = url.indexOf("?");
        if (index >= 0) {
            urlBuffer.append(url.substring(0, index + 1));

            String paramStr = url.substring(index + 1);
            String[] params = paramStr.split("&");
            for (String str : params) {
                String[] word = str.split("=");
                if (word.length == 2 && isFilterWord(word[0])) {
                    continue;
                }

                if (!urlBuffer.toString().endsWith("?")) {
                    urlBuffer.append("&");
                }
                urlBuffer.append(str);
            }
        } else {
            urlBuffer.append(url);
        }

        return urlBuffer.toString();
    }

    private boolean isFilterWord(String word) {
        final String[] filterParams = {"t", "token"};
        for (String temp : filterParams) {
            if (temp.equalsIgnoreCase(word)) {
                return true;
            }
        }

        return false;
    }
}
