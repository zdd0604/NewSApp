package com.frame.net;

import com.frame.event.DataEvent;

/**
 * 登录
 * Created by liyingfeng on 2016/1/28.
 */
public class LoginEngine extends BaseEngine {
    public LoginEngine(String tag) {
        super(tag, "");
    }

    public void setParams(String userName, String password) {
        putParams("userName", userName);
        putParams("password", password);
    }

    @Override
    protected DataEvent.Type getResponseTypeOnSuccess() {
        return DataEvent.Type.LOGIN_SUCCESS;
    }

    @Override
    protected DataEvent.Type getResponseTypeOnFailure() {
        return DataEvent.Type.LOGIN_FAILURE;
    }

    @Override
    protected Object parseResult(String data) {
        return null;
    }
}