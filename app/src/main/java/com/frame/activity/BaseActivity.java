package com.frame.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.R;
import com.frame.event.ClickEvent;
import com.frame.event.DataEvent;
import com.frame.event.LoginEvent;
import com.up72.library.UP72System;
import com.up72.library.utils.LogUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import de.greenrobot.event.EventBus;

/**
 * activity基类
 * Created by liyingfeng on 2015/9/22.
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    protected LogUtil log = new LogUtil(getClass());
    private String mTag = null;
    private Dialog mDialog;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(getContentView());
        initView();
        initListener();
        initData();
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected void initTitle(String strContent) {
        this.initTitle(0, null, strContent, null, 0);
    }

    protected void initTitle(int resLeft, String strContent) {
        this.initTitle(resLeft, null, strContent, null, 0);
    }

    protected void initTitle(int resLeft, String strContent, String strRight) {
        this.initTitle(resLeft, null, strContent, strRight, 0);
    }

    protected void initTitle(int resLeft, String strContent, int resRight) {
        this.initTitle(resLeft, null, strContent, null, resRight);
    }

    protected void initTitle(String strLeft, String strContent, String strRight) {
        this.initTitle(0, strLeft, strContent, strRight, 0);
    }

    protected void initTitle(int resLeft, String strLeft, String strContent, String strRight, int resRight) {
        ImageView ivTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
        ImageView ivTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        TextView tvTitleLeft = (TextView) findViewById(R.id.tv_title_left);
        TextView tvTitleContent = (TextView) findViewById(R.id.tv_title_content);
        TextView tvTitleRight = (TextView) findViewById(R.id.tv_title_right);

        if (resLeft == 0) {
            ivTitleLeft.setVisibility(View.GONE);
        } else {
            ivTitleLeft.setVisibility(View.VISIBLE);
            ivTitleLeft.setImageResource(resLeft);
            ivTitleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickTitleLeft(v);
                }
            });
        }

        if (resRight == 0) {
            ivTitleRight.setVisibility(View.GONE);
        } else {
            ivTitleRight.setVisibility(View.VISIBLE);
            ivTitleRight.setImageResource(resRight);
            ivTitleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickTitleRight(v);
                }
            });
        }
        if (strLeft == null) {
            tvTitleLeft.setVisibility(View.GONE);
        } else {
            tvTitleLeft.setVisibility(View.VISIBLE);
            tvTitleLeft.setText(strLeft);
            tvTitleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickTitleLeft(v);
                }
            });
        }
        if (strContent == null) {
            tvTitleContent.setVisibility(View.GONE);
        } else {
            tvTitleContent.setVisibility(View.VISIBLE);
            tvTitleContent.setText(strContent);
        }
        if (strRight == null) {
            tvTitleRight.setVisibility(View.GONE);
        } else {
            tvTitleRight.setVisibility(View.VISIBLE);
            tvTitleRight.setText(strRight);
            tvTitleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickTitleRight(v);
                }
            });
        }
    }

    protected void onClickTitleLeft(View v) {
        finish();
    }

    protected void onClickTitleRight(View v) {
    }

    protected String getRequestTag() {
        if (mTag == null) {
            mTag = getClass().getName() + "-" + getClass().hashCode();
        }
        return mTag;
    }

    /**
     * 登录事件
     */
    public void onEventMainThread(LoginEvent event) {
    }

    /**
     * 请求返回事件
     */
    public void onEventMainThread(DataEvent event) {
    }

    /**
     * 点击事件
     */
    public void onEventMainThread(ClickEvent event) {
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showTip(String msg) {
        Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showLoading(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog == null || tvContent == null) {
                    View view = LayoutInflater.from(BaseActivity.this).inflate(R.layout.layout_loading, null);
                    tvContent = (TextView) view.findViewById(R.id.tv_content);
                    mDialog = new Dialog(BaseActivity.this, R.style.dialog_transparent);
                    mDialog.setCancelable(false);
                    mDialog.setContentView(view);
                }
                if (msg == null) {
                    tvContent.setVisibility(View.GONE);
                } else {
                    tvContent.setVisibility(View.VISIBLE);
                    tvContent.setText(msg);
                }
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        });
    }

    protected void cancelLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.out_anim);
    }

    @Override
    protected void onDestroy() {
        UP72System.cancelRequestByTag(getRequestTag());//取消网络请求
        EventBus.getDefault().unregister(this);//卸载事件监听
        super.onDestroy();
    }
}