package com.frame.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.frame.activity.LoginActivity;
import com.frame.activity.RegisterActivity;

/**
 * 页面跳转
 * Created by liyingfeng on 2015/11/2.
 */
public class RouteManager {
    private static RouteManager instance = new RouteManager();

    private RouteManager() {
    }

    public static RouteManager getInstance() {
        return instance;
    }

    private void startActivity(Activity act, Class<?> cls) {
        startActivity(act, cls, -1, null, -1);
    }

    private void startActivity(Activity act, Class<?> cls, int flags) {
        startActivity(act, cls, -1, null, flags);
    }

    private void startActivity(Activity act, Class<?> cls, Bundle data) {
        startActivity(act, cls, -1, data, -1);
    }

    private void startActivity(Activity act, Class<?> cls, int requestCode, Bundle data) {
        startActivity(act, cls, requestCode, data, -1);
    }

    private void startActivity(Activity act, Class<?> cls, Bundle data, int flags) {
        startActivity(act, cls, -1, data, flags);
    }

    private void startActivity(Activity act, Class<?> cls, int requestCode, Bundle data, int flags) {
        Intent intent = new Intent(act, cls);
        if (data != null) {
            intent.putExtras(data);
        }
        if (flags != -1) {
            intent.addFlags(flags);
        }
        act.startActivityForResult(intent, requestCode);
//        act.overridePendingTransition(R.anim.in_anim, 0);
        act.overridePendingTransition(0, 0);
    }

    /**
     * 打开内置网页
     *
     * @param act   当前所在activity
     * @param title 标题
     * @param url   网址
     */
   /* public void toWebView(Activity act, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        this.startActivity(act, WebVewActivity.class, bundle);
    }*/

    /**
     * 登录
     *
     * @param act 当前所在activity
     */
    public void toLoginActivity(Activity act) {
        this.startActivity(act, LoginActivity.class);
    }

    /**
     * 注册
     *
     * @param act 当前所在activity
     */
    public void toRegister(Activity act) {
        startActivity(act, RegisterActivity.class);
    }
}