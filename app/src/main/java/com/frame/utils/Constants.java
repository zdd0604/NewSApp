package com.frame.utils;

/**
 * 常量类
 * Created by liyingfeng on 2015/11/6.
 */
public class Constants {
    /**
     * 文件缓存名
     */
    public static String FILE_CACHE_DIRECTORY = "cache";
    /**
     * 项目缓存根目录保存名
     */
    public static String FILE_ROOT_DIRECTORY = "rootDir";
    /**
     * image缓存目录
     */
    public static String FILE_IMAGE_DIRECTORY = "image";
    /**
     * 请求头字段的KEY
     */
    public static final String REQUEST_HEADER_KEY = "encryptValue";
    /**
     * 请求头字段的VALUE 加密所需的KEY
     */
    public static final String REQUEST_ENCODER_KEY = "58979a089d22c9f9126bdcb68eb643c1";

    /**
     * 缓存用户名的是否登录
     */
    public static final String KEY_RL_LOGINED = "KEY_RL_LOGINED";


    public interface RequestUrl {
        String baseUrl = "";
    }
}