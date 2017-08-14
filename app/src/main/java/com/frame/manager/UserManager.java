package com.frame.manager;

import com.frame.model.UserModel;

/**
 * 用户数据管理
 * Created by liyingfeng on 2015/11/11.
 */
public class UserManager {
    private UserModel userModel;
    private static UserManager instance = new UserManager();

    private UserManager() {
    }

    public static UserManager getInstance() {
        return instance;
    }

    /**
     * 判断用户是否登录
     *
     * @return true登录 false未登录
     */
    public boolean isLogin() {
        return userModel != null;
    }

    /**
     * 获取用户的实例
     *
     * @return UserModel
     */
    public UserModel getUserModel() {
        return userModel;
    }

    /**
     * 获取用户实例的克隆
     *
     * @return UserModel
     */
    public UserModel getUserModelClone() {
        if (userModel == null) {
            return null;
        }
        return (UserModel) userModel.clone();
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
