package com.frame.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 文件缓存
 */
public class FileCache {

	public static String fileCacheDir;// 文件缓存目录

	/**
	 * 
	 * 读取本地缓存文件
	 * 
	 * @param fileName
	 *            : 文件名（不带任何的 “/ ”和后缀名）
	 * @param expirationTime
	 *            : 缓存文件过期时间（毫秒）
	 * @return Object: 当缓存过期或读取异常时返回null
	 */
	public static Object readCache(String fileName, long expirationTime) {
		if (!checkSDdir())
			return null;
		Object returnObj = null;
		try {
			File file = new File(fileCacheDir + fileName + ".cache");
			if (System.currentTimeMillis() - file.lastModified() < expirationTime) {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				returnObj = ois.readObject();
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	/**
	 * 将对象写入文件
	 * 
	 * @param fileName
	 *            文件名（不带任何的 “/ ”和后缀名）
	 * @param obj
	 *            写入的对象
	 */
	public static void writeObject(Context context,String fileName, Object obj) {
		fileCacheDir = FileUtil.getCacheDir(context).getAbsolutePath();
		if (!checkSDdir())
			return;
		try {
			File file = new File(fileCacheDir + fileName + ".cache");
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件
	 *
	 * @param fileName
	 *            文件名（不带任何的 “/ ”和后缀名）
	 * @return object 读取失败时，返回null
	 */
	public static Object readObject(Context context,String fileName) {
		fileCacheDir = FileUtil.getCacheDir(context).getAbsolutePath();
		Object returnObj = null;
		try {
			File file = new File(fileCacheDir + fileName + ".cache");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			returnObj = ois.readObject();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	/**
	 * 删除缓存目录下指定的缓存文件
	 * 
	 * @param fileName
	 * @return boolean 当返回值为ture的时候，表示该缓存文件已删除或不存在
	 */
	public static boolean deleteFile(Context context,String fileName) {
		fileCacheDir = FileUtil.getCacheDir(context).getAbsolutePath();
		boolean b = false;
		try {
			File file = new File(fileCacheDir + fileName + ".cache");
			if (file.exists()) // 文件存在则删除
				b = file.delete();
			else
				b = true;// 文件不存在也认为文件删除成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	private static boolean checkSDdir() {
		try {
			// 判断SD卡是否可用
			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				// 当前不可用
				Log.e("FileCache", "SD卡检测   -------------->   SD卡不可用");
				return false;
			}

			File file = new File(fileCacheDir);
			// 不存在，则创建下载目录
			if (!file.exists()) {
				file.mkdirs();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}