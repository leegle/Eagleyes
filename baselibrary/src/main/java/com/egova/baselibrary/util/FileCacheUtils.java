package com.egova.baselibrary.util;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 清除缓存工具类，清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 */
public class FileCacheUtils {
    private static DecimalFormat df;

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 按名字清除本应用数据库
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * * 清除自定义路径下的文件
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * * 清除本应用所有的数据
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if(item.isDirectory()){
                    deleteFilesByDirectory(item); // 递规的方式删除文件夹
                }else if(item.isFile()){
                    item.delete();
                }
            }
            directory.delete();// 删除目录本身
        }
    }

    // 获取文件大小
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param filePath
     * @param deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        //保留两位小数
        df = new DecimalFormat("0.00");
        double kiloByte = size / 1024;
        String format = df.format(size);
        if (kiloByte < 1) {
            return format + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "T";
    }

    /***
     * 获取应用缓存大小
     * @param file
     * @return
     * @throws Exception
     */
    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 开始计算缓存大小
     *
     * @param context
     * @param listener
     * @param filepath 自定义存储目录
     */
    public static void startCalculateCacheSize(Context context, CacheSizeCalculateListener listener, String... filepath) {
        CalculateCacheSizeRunnable runnable = new CalculateCacheSizeRunnable(context, listener, filepath);
        Handler mainHandler = new Handler(context.getMainLooper());
        mainHandler.post(runnable);
//        Thread thread = new Thread(runnable);
//        thread.start();
    }

    public static void startClearCacheSize(Context context, ClearCacheListener listener, String... filepath) {
        ClearCacheRunnable runnable = new ClearCacheRunnable(context, listener, filepath);
        Handler mainHandler = new Handler(context.getMainLooper());
        mainHandler.post(runnable);
//        Thread thread = new Thread(runnable);
//        thread.start();
    }


    static class CalculateCacheSizeRunnable implements Runnable {
        CacheSizeCalculateListener listener;
        Context context;
        String[] filePath;

        public CalculateCacheSizeRunnable(Context context, CacheSizeCalculateListener listener, String... filepath) {
            this.context = context;
            this.listener = listener;
            this.filePath = filepath;
        }

        @Override
        public void run() {
            long cacheSize = 0;
            try {
                if (filePath != null) {
                    for (String filePath : filePath) {
                        //计算自定义目录的大小
                        cacheSize += getFolderSize(new File(filePath));
                    }
                }
                cacheSize += getFolderSize(context.getCacheDir());//本应用内部缓存(/data/data/com.xxx.xxx/cache)
                cacheSize += getFolderSize(context.getExternalCacheDir());//外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
                cacheSize += getFolderSize(context.getFilesDir());//本应用内部缓存data/data/com.xxx.xxx/files下的内容
                cacheSize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES));///storage/emulated/0/Android/data/包名/files
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listener != null)
                listener.onCalculateFinished(cacheSize);
        }
    }

    public interface CacheSizeCalculateListener {
        void onCalculateFinished(long cacheSize);
    }

    public static class ClearCacheRunnable implements Runnable {
        ClearCacheListener listener;
        Context context;
        String[] filePath;

        public ClearCacheRunnable(Context context, ClearCacheListener listener, String... filepath) {
            this.listener = listener;
            this.context = context;
            this.filePath = filepath;
        }

        @Override
        public void run() {
            try {
                if (filePath != null) {
                    for (String filePath : filePath) {
                        //删除自定义目录
                        cleanCustomCache(filePath);
                    }
                }
                cleanInternalCache(context);//本应用内部缓存(/data/data/com.xxx.xxx/cache)
                cleanExternalCache(context);//外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
//                cleanDatabases(context);//数据库
//                cleanSharedPreference(context);//sharedPreference文件
                cleanFiles(context);//本应用内部缓存data/data/com.xxx.xxx/files下的内容
                deleteFilesByDirectory(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listener != null) {
                listener.onClearCacheFinished();
            }
        }
    }

    public interface ClearCacheListener {
        void onClearCacheFinished();
    }
}
