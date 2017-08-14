package com.frame.model;

/**
 * 说点什么
 * Created by liyingfeng on 2016/1/12.
 */
public class UserModel implements Cloneable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}