package com.frame.event;

import android.view.View;

/**
 * 点击事件事件
 * Created by liyingfeng on 2015/9/22.
 */
public class ClickEvent {
    public enum Type {
    }

    public Type type;
    public View view;
    public Object data;

    public ClickEvent(Type type, View view, Object data) {
        this.type = type;
        this.view = view;
        this.data = data;
    }
}