package com.frame.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * 通用model 主要用于recyclerView中包含多个不同的item
 * Created by wangchang on 2016/2/23.
 */
public class ItemModel {

    /**
     * 首页推荐列表
     */
    public static final int ITEM_LIST = 10001;
    /**
     * 首页viewpager列表
     */
    public static final int ITEM_VIEWPAGERE = 10002;
    /**
     * 购物车头部
     */
    public static final int ITEM_BUY_TITLE = 10003;
    /**
     * 购物车内容
     */
    public static final int ITEM_BUY_CONTENT = 10004;


    public int type;
    public Object data;

    public ItemModel(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public static String toJsons(ArrayList<ItemModel> models) {
        Gson gson = new Gson();
        return gson.toJson(models, new TypeToken<ArrayList<ItemModel>>() {
        }.getType());
    }

    public static ArrayList<ItemModel> fromJsons(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<ItemModel>>() {
        }.getType());
    }
}