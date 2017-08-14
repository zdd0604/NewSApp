package com.frame.model;

import java.io.Serializable;

/**
 * Created by wangchang on 2016/2/23.
 */
public class ListModel implements Serializable{
    private String imgUrl="";
    private String text="";
    private String price="";

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
