package com.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.up72.library.utils.StringUtil;

import java.util.UUID;

/**
 * 设备属性获取
 */
public class DevicePropertyUtil {

    /**
     * 获取手机分辨率
     *
     * @return DisplayMetrics
     */
    public static DisplayMetrics getScreenPixel(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.e("DisplayMetrics", "分辨率：" + dm.widthPixels + "x" + dm.heightPixels + ",精度：" + dm.density + ",densityDpi=" + dm.densityDpi);
        return dm;
    }

    /**
     * 获取手机系统版本
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getAndroidSDKVersion() {
        int version;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            return 0;
        }
        return version;
    }

    /**
     * 获取设备token
     */
    public static String getDeviceToken(Activity activity) {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String tokenId = tm.getDeviceId();
        if (StringUtil.isEmpty(tokenId)) {
            tokenId = tm.getSubscriberId();
        }
        if (StringUtil.isEmpty(tokenId)) {
            tokenId = UUID.randomUUID().toString().replace("-", "");
        }
        return tokenId;
    }

    public static String getUUID(Activity activity) {
        final TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;
    }

    /**
     * 获取当前应用版本序号
     *
     * @param context
     * @return 当前应用版本序号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前应用版本名
     *
     * @param context
     * @return 当前应用版本名
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
