package com.frame.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.R;
import com.frame.event.ClickEvent;
import com.frame.event.DataEvent;
import com.frame.event.LoginEvent;
import com.up72.library.UP72System;
import com.up72.library.utils.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * fragment基类
 * Created by liyingfeng on 2015/9/22.
 */
public abstract class BaseFragment extends Fragment {

    protected LogUtil log = new LogUtil(getClass());
    private String mTag = null;
    private Dialog mDialog;
    private TextView tvContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view);

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
    protected void initTitle(String strContent, String strRight) {
        this.initTitle(0, null, strContent, strRight, 0);
    }
    protected void initTitle(int resLeft, String strLeft, String strContent, String strRight, int resRight) {
        View view = getView();
        if (view == null) {
            return;
        }
        ImageView ivTitleLeft = (ImageView) view.findViewById(R.id.iv_title_left);
        ImageView ivTitleRight = (ImageView) view.findViewById(R.id.iv_title_right);
        TextView tvTitleLeft = (TextView) view.findViewById(R.id.tv_title_left);
        TextView tvTitleContent = (TextView) view.findViewById(R.id.tv_title_content);
        TextView tvTitleRight = (TextView) view.findViewById(R.id.tv_title_right);

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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLoading(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog == null || tvContent == null) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading, null);
                    tvContent = (TextView) view.findViewById(R.id.tv_content);
                    mDialog = new Dialog(getActivity(), R.style.dialog_transparent);
                    mDialog.setCancelable(false);
                    mDialog.setContentView(view);
                }
                if (msg == null) {
                    tvContent.setVisibility(View.GONE);
                } else {
                    tvContent.setVisibility(View.VISIBLE);
                    tvContent.setText(msg);
                }
                mDialog.show();
            }
        });
    }

    protected void cancelLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        UP72System.cancelRequestByTag(getRequestTag());//取消网络请求
        EventBus.getDefault().unregister(this);//卸载事件监听
        super.onDestroy();
    }
}