package com.frame.model;

import java.io.Serializable;

/**
 * Created by wangchang on 2016/3/1.
 */
public class BuysModel implements Serializable{

    private String factory_name;
    private String factory_title;
    private String factory_cheap;
    private String imageUrl;
    private String goods_title;
    private double goods_price;
    private int goods_count;

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    public String getFactory_title() {
        return factory_title;
    }

    public void setFactory_title(String factory_title) {
        this.factory_title = factory_title;
    }

    public String getFactory_cheap() {
        return factory_cheap;
    }

    public void setFactory_cheap(String factory_cheap) {
        this.factory_cheap = factory_cheap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }
}
