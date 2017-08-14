package com.frame;

import android.app.Application;

import com.up72.library.UP72System;
import com.up72.library.utils.LogUtil;

/**
 * 程序入口
 * Created by liyingfeng on 2016/1/12.
 */
public class UP72Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.debug = BuildConfig.DEBUG;
        UP72System.init(this);
    }
}