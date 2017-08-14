package com.up72.library.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 */
public class StringUtil {
    public static boolean isEmpty(String value) {
        return null == value || ("").equals(value.trim()) || ("null").equals(value.toLowerCase());
    }

    public static boolean checkEmail(String email) {
        String str = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean checkMobileNO(String value) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(14[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean checkPassword(String value) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean checkUri(String value) {
        Pattern p = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean checkFullName(String value) {
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{2,4}");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean checkClassName(String value) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]{1,}");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean checkNickName(String value) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]{2,7}");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean checkUserName(String value) {
        Pattern p = Pattern.compile("^(?![0-9]+$)[0-9A-Za-z]{6,16}$");
        Matcher m = p.matcher(value);
        return m.matches();
    }
}