package com.frame.net;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.frame.event.DataEvent;
import com.frame.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.up72.library.UP72System;
import com.up72.library.utils.LogUtil;
import com.up72.library.utils.security.EncryptUtils;
import com.up72.library.volley.MultipartRequest;
import com.up72.library.volley.MultipartRequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 网络请求基类
 * Created by liyingfeng on 2015/9/22.
 */
public abstract class BaseEngine {
    //增、删、改等操作超时时间
    private static final int TIMEOUT = 15000;
    //最大重试请求次数
    private static final int MAX_RETRIES = 0;
    private String mTag;
    private HashMap<String, String> mParams = null;//请求参数
    private HashMap<String, File> mFiles = null;//上传的文件
    private String requestUrl;

    protected LogUtil log = new LogUtil(getClass());

    public enum Method {
        GET, POST
    }

    public BaseEngine(String tag) {
        this.mTag = tag;
    }


    public BaseEngine(String tag, String requestUrl) {
        this.mTag = tag;
        this.requestUrl = requestUrl;
    }

    protected void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    protected void putParams(String key, String value) {
        this.putParams(key, value, false);
    }

    protected void putParams(String key, String value, boolean isEncode) {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        if (isEncode) {
            mParams.put(key, URLEncoder.encode(value));
        } else {
            mParams.put(key, value);
        }
    }

    protected void putParams(String key, File file) {
        if (mFiles == null) {
            mFiles = new HashMap<>();
        }
        mFiles.put(key, file);
    }

    /**
     * 超时时间(单位毫秒)
     *
     * @return 超时时间(单位毫秒)
     */
    protected int getTimeOut() {
        return TIMEOUT;
    }

    /**
     * 默认GET请求
     */
    public void sendRequest() {
        sendRequest(Method.GET);
    }

    protected void sendRequest(Method method) {
        Request<String> request;
        if (method == Method.POST) {
            if (mFiles != null && mFiles.size() > 0) {
                request = buildPostUploadRequest();
            } else {
                request = buildPostStringRequest();
            }
        } else {
            request = buildGetStringRequest();
        }
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeOut(), MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        log.i("requestUrl--->" + request.getUrl());
        UP72System.getRequestQueue().add(request);
    }

    private StringRequest buildGetStringRequest() {
        String paramsStr = "";
        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                if (paramsStr.length() < 1) {
                    paramsStr = "?" + entry.getKey() + "=" + entry.getValue();
                } else {
                    paramsStr += "&" + entry.getKey() + "=" + entry.getValue();
                }
            }
        }
        return new StringRequest(Request.Method.GET, this.requestUrl + paramsStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onFailure(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getEncrypHeader();
            }
        };
    }

    private StringRequest buildPostStringRequest() {
        return new StringRequest(Request.Method.POST, this.requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onFailure(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getEncrypHeader();
            }

            @Override
            protected Map<String, String> getParams() {
                if (mParams == null || mParams.isEmpty()) {
                    return null;
                }
                log.d("params--->" + mParams.toString());
                return mParams;
            }
        };
    }

    private Request<String> buildPostUploadRequest() {
        MultipartRequestParams multipartRequestParams = new MultipartRequestParams();

        if (mFiles != null && !mFiles.isEmpty()) {
            for (Map.Entry<String, File> entry : mFiles.entrySet()) {
                multipartRequestParams.put(entry.getKey(), entry.getValue());
            }
        }
        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                multipartRequestParams.put(entry.getKey(), entry.getValue());
            }
        }
        return new MultipartRequest(Request.Method.POST, multipartRequestParams, this.requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onFailure(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getEncrypHeader();
            }
        };
    }

    private void onSuccess(String response) {
        log.i(response);
        try {
            JSONObject obj = new JSONObject(response);
            int code = obj.isNull("code") ? 0 : obj.optInt("code");
            if (code == 1) {
                dispatchEvent(parseResult(obj.isNull("data") ? "" : obj.optString("data")), getResponseTypeOnSuccess());
            } else {
                dispatchEvent(obj.isNull("msg") ? "" : obj.optString("msg"), getResponseTypeOnFailure());
            }
        } catch (JSONException e) {
            log.e("onSuccess --> JSON 解析错误");
            dispatchEvent("出错了", getResponseTypeOnFailure());
        }
    }

    private void onFailure(VolleyError error) {
        String msg = "网络不给力";
        if (error != null && error.getCause() != null) {
            msg = error.getCause().toString();
        }
        log.e(msg);
        dispatchEvent("网络不给力", getResponseTypeOnFailure());
    }

    protected void dispatchEvent(Object data, DataEvent.Type type) {
        EventBus.getDefault().post(new DataEvent(type, mTag, data));
    }

    private Map<String, String> getEncrypHeader() {
        HashMap<String, String> header = new HashMap<>();
        try {
            header.put(Constants.REQUEST_HEADER_KEY, EncryptUtils.encryptThreeDESECB(buildEncrypHeader(), Constants.REQUEST_ENCODER_KEY));
            log.i("encrypHeader--->" + header.toString());
        } catch (Exception e) {
            log.e(e);
        }
        return header;
    }

    protected String buildEncrypHeader() {
        return "";
    }

    protected String getEncrypValue(String... key) {
        String result = "";
        if (key != null && key.length > 0) {
            for (String aKey : key) {
                if (this.mParams != null && this.mParams.containsKey(aKey)) {
                    result += this.mParams.get(aKey);
                }
            }
        }
        return result;
    }

    /**
     * 成功返回码
     *
     * @return DataEvent.Type
     */
    protected abstract DataEvent.Type getResponseTypeOnSuccess();

    /**
     * 失败返回码
     *
     * @return DataEvent.Type
     */
    protected abstract DataEvent.Type getResponseTypeOnFailure();

    /**
     * 解析JSON中data的值
     *
     * @return object
     */
    protected abstract Object parseResult(String data);

 /*   protected <T extends Object> Object parseResult(String data,@NonNull Class<? extends Object> cls) {
        if (data != null && data.length() > 0) {
            Gson gson = new Gson();
            return gson.fromJson(data, new TypeToken<>() {
            }.getType());
        }
        return null;
    }*/
}