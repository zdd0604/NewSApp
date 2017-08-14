package com.frame.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @description 文件操作工具类
 *
 * @author 谭杰 E-mail: tanjie9012@163.com
 * @create 2014-2-13 上午09:35:28
 * @version 1.0.0
 * @company 北京开拓明天科技有限公司 Copyright: 版权所有 (c) 2014
 */
public class FileUtil {
    /**
     * SD卡最小空闲大小,若低于此值则认为SD卡不可用，单位MB
     */
    private static final int SDCARD_MIN_SIZE = 50;

    /**
     * 文件复制缓存大小
     */
    public static final int BUFFER_SIZE = 1024;

    private static final int MAXMEMORY = 500 * 1024 * 1024;// 程序运行的最大内存

    public static final int WORD = 1;

    public static final int TXT = 2;

    public static final int EXCEL = 3;

    public static final int PPT = 4;

    public static final int PDF = 5;

    /**
     * 判断系统是否在低内存下运行
     */
    public static boolean hasAcailMemory() {
        // 获取手机内部空间大小
        long memory = getMemoryAvailableSize();
        if (memory < MAXMEMORY) {
            // 应用将处于低内存状态下运行
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true 挂载SD卡并且剩余空间大于SDCARD_MIN_SIZE，否则false
     */
    public static boolean isSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && getSDAvailableCardSize() > SDCARD_MIN_SIZE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取手机外部空间大小
     *
     * @return
     */
    public static long getSDCardTotalSize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();// 获取外部存储目录即
            // SDCard
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            throw new RuntimeException("Don't have sdcard.");
        }
    }

    /**
     * 获取SD卡剩余空间,单位MB
     *
     * @return SD卡剩余空间,单位MB
     */
    public static long getSDAvailableCardSize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(file.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = statFs.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = statFs.getAvailableBlocks();
            // 返回SD卡空闲大小
            // long SDCardSize = freeBlocks * blockSize; //单位Byte
            // long SDCardSize = (freeBlocks * blockSize)/1024; //单位KB
            long SDCardSize = (freeBlocks * blockSize) / 1024 / 1024;
            return SDCardSize; // 单位MB
        }
        return 0;
    }

    /**
     * 获取手机内部空间大小
     *
     * @return
     */
    public static long getMemoryTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();// 获取该区域可用的文件系统数
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机内部可用空间大小
     *
     * @return
     */
    public static long getMemoryAvailableSize() {
        File path = Environment.getDataDirectory();// 获取 Android 数据目录
        StatFs stat = new StatFs(path.getPath());// 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
        long blockSize = stat.getBlockSize();// 返回 Int ，大小，以字节为单位，一个文件系统
        long availableBlocks = stat.getAvailableBlocks();// 返回 Int ，获取当前可用的存储空间
        return availableBlocks * blockSize;
    }

    /**
     * 获取SD卡根目录
     *
     * @return 如果挂载SD卡,返回SD卡根目录,否则返回null
     */
    public static File getSDCard() {
        if (isSDCard()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }

    /**
     * 获取手机内存根目录
     *
     * @param context
     * @return 返回手机内存根目录
     */
    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    /**
     * 获取并创建项目存储目录,优先创建至SD卡
     *
     * @param context
     * @param dirName
     *            子目录名
     * @return 项目资源存储的path目录
     */
    public static File getFileDir(Context context, String dirName) {
        return getFileDir(context, dirName, true);
    }

    /**
     * 获取并创建项目存储目录,指定是否创建至SD卡
     *
     * @param context
     * @param dirName
     *            子目录名
     * @param isSDCard
     *            true 若SD卡挂载则创建至SD卡,未挂载则创建至手机内存。false 直接创建至手机内存
     * @return 项目资源存储的path目录
     */
    public static File getFileDir(Context context, String dirName, boolean isSDCard) {
        File rootDir = null;
        if (isSDCard) {
            rootDir = getSDCard();
            if (rootDir == null) {
                rootDir = getCacheDir(context);
            }
        } else {
            rootDir = getCacheDir(context);
        }
        File fileDir = new File(rootDir, dirName);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * 获取并创建项目资源存储子目录
     */
    public static File getFileDir(File parentsDir, String dirName) {
        File fileDir = new File(parentsDir, dirName);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * 文件复制
     *
     * @param is
     *            复制源文件流
     * @param os
     *            复制目标文件流
     */
    public static void copyFile(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int count = is.read(buffer, 0, BUFFER_SIZE);
                if (count == -1)
                    break;
                os.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断文件夹是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件夹是否为空
     *
     * @param path
     * @return  true--为空   false --不为空
     */
    public static boolean isEmptyFile(String path) {

        if (isFileExist(path)) {
            File file = new File(path);
            File[] files = file.listFiles();
            if (file.isDirectory()) {
                if (files.length == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                FileUtil.deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 获取缓存文件保存位置
     *
     * @author 谭杰
     * @create 2014-6-27 下午4:15:42
     * @param context
     * @param dirName
     * @return
     */
    public static File getFileCacheDir(Context context, String dirName) {
        return FileUtil.getFileDir(getFileCacheDir(context), dirName);
    }

    /**
     * 获取缓存文件保存位置
     *
     * @author 谭杰
     * @create 2014-6-27 下午4:15:42
     * @param context
     * @return
     */
    public static File getFileCacheDir(Context context) {
        File rootFile = getFileDir(context, Constants.FILE_ROOT_DIRECTORY);
        return getFileDir(rootFile, Constants.FILE_CACHE_DIRECTORY);
    }

    @SuppressLint("SdCardPath")
    public static String getExtSdCardPath() {
        String sdcard_path = null;
        String sd_default = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("text", sd_default);
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // 得到路径
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("extSdCard") && line.contains("/storage/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("sdcard1") && line.contains("/storage/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("ext_sd") && line.contains("/storage/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("text", sdcard_path);
        return sdcard_path;
    }

    /**
     * 获取文件类型,支持word,txt,excel,ppt,pdf等类型
     *
     * @author 谭杰
     * @create 2014-9-15 下午2:40:21
     * @param file
     * @return
     */
    public static int getFileType(File file) {
        if (file.isDirectory()) {
            return -1;
        }
        String fileName = file.getName();
        int index = fileName.indexOf(".");
        if (index == -1) {
            return -1;
        }
        String ex = fileName.substring(index);
        if (ex.equalsIgnoreCase(".xls") || ex.equalsIgnoreCase(".xlsx")) {
            return EXCEL;
        } else if (ex.equalsIgnoreCase(".doc") || ex.equalsIgnoreCase(".docx")) {
            return WORD;
        } else if (ex.equalsIgnoreCase(".ppt") || ex.equalsIgnoreCase(".pptx")) {
            return PPT;
        } else if (ex.equalsIgnoreCase(".pdf")) {
            return PDF;
        } else if (ex.equalsIgnoreCase(".txt")) {
            return TXT;
        }
        return -1;
    }

}
