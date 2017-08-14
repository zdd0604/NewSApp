package com.frame.activity;

import android.view.View;
import android.widget.EditText;

import com.frame.R;
import com.frame.event.DataEvent;
import com.frame.net.LoginEngine;

/**
 * 登录页面
 * Created by liyingfeng on 2016/1/19.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText etUserName, etPassword;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                // TODO: 2016/1/19 登录
                String userName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (userName.length() < 1) {
                    showTip("用户名不能为空");
                } else if (password.length() < 1) {
                    showTip("密码不能为空");
                } else {
                    LoginEngine engine = new LoginEngine(getRequestTag());
                    engine.setParams(userName, password);
                    engine.sendRequest();
                }
                break;
        }
    }

    @Override
    public void onEventMainThread(DataEvent event) {
        if (event.tag.equals(getRequestTag())) {
            switch (event.type) {
                case LOGIN_SUCCESS:
                    break;
                case LOGIN_FAILURE:
                    break;
            }
        }
    }
}