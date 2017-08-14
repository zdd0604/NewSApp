package com.frame.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.frame.R;

import java.io.File;

import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.SimpleDListener;

/**
 * 下载服务
 * Created by liyingfeng on 2015/12/25.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class DownLoadService extends Service {
    public static final String DOWNLOAD_URL = "DOWNLOAD_URL";
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private String dirPath = "";
    private String currentUrl = "";
    private int currentLength = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        DLManager.getInstance(getApplicationContext()).setMaxTask(1);
        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/";
        notificationManager = (NotificationManager) getSystemService(Context
                .NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null && dirPath != null && dirPath.length() > 0) {
            String downloadUrl = intent.getExtras().getString(DOWNLOAD_URL, "");
            if (downloadUrl != null && downloadUrl.length() > 0) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                if (fileName.length() > 0) {
                    File file = new File(dirPath, fileName);
                    if (file.exists()) {
                        openFile(file.getAbsolutePath());
                        return super.onStartCommand(intent, Service.START_FLAG_REDELIVERY, startId);
                    }
                    DLManager.getInstance(getApplicationContext()).setDebugEnable(true).setMaxTask(1).dlStart(downloadUrl, dirPath, fileName + ".temp", new SimpleDListener() {
                        @Override
                        public void onStart(String fileName, String realUrl, int fileLength) {
                            DownLoadService.this.currentUrl = realUrl;
                            DownLoadService.this.currentLength = fileLength;
                            builder.setContentTitle(fileName);
                            notificationManager.notify(0, builder.build());
                        }

                        @Override
                        public void onProgress(int progress) {
                            builder.setProgress(currentLength, progress, false);
                            notificationManager.notify(0, builder.build());
                        }

                        @Override
                        public void onFinish(File file) {
                            notificationManager.cancel(0);
                            File f = new File(file.getAbsolutePath().replace(".temp", ""));
                            if (f.exists()) {
                                f.delete();
                            }
                            if (file.exists()) {
                                String path;
                                file.renameTo(new File(path = file.getAbsolutePath().replace(".temp", "")));
                                openFile(path);
                            }
                        }

                        @Override
                        public void onError(int status, String error) {
                            DLManager.getInstance(getApplicationContext()).dlCancel(currentUrl);
                            Toast.makeText(getApplicationContext(), "下载失败，请重试", Toast.LENGTH_LONG).show();
                            super.onError(status, error);
                        }
                    });
                }
            }
        }
        return super.onStartCommand(intent, Service.START_FLAG_REDELIVERY, startId);
    }

    @Override
    public void onDestroy() {
        stopSelf();
        DLManager.getInstance(this).dlStop(currentUrl);
        super.onDestroy();
    }

    /**
     * 打开应用
     *
     * @param filePath 文件路径
     */
    private void openFile(String filePath) {
        if (filePath != null && filePath.length() > 0) {
            String suffix = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
            switch (suffix) {
                case "apk":
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);
                    File file = new File(filePath);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    startActivity(intent);
                    break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}