package com.frame.model;

import java.io.Serializable;

/**
 * Created by wangchang on 2016/2/26.
 */
public class BannerModel implements Serializable {

    private String imgUrl = "";

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
