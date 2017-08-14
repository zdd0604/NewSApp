package com.frame.event;

/**
 * 数据传递事件
 * Created by liyingfeng on 2015/9/22.
 */
public class DataEvent {
    public enum Type {
        /**
         * 登陆成功
         */
        LOGIN_SUCCESS,
        /**
         * 登陆失败
         */
        LOGIN_FAILURE,
    }

    public Type type;
    public String tag;
    public Object data;

    public DataEvent(Type type, String tag, Object data) {
        this.type = type;
        this.tag = tag;
        this.data = data;
    }
}