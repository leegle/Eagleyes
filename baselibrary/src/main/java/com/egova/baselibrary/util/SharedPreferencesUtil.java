package com.egova.baselibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 */
public class SharedPreferencesUtil {


    /**
     * 上下文
     */
    private static Context mContext;
    /**
     * 保存的文件名
     */
    private static String fileName;
    /**
     * 初始化SharedPreferences，必须使用该方法
     */
    public static void init(Context context, String _fileName) {
        mContext = context;
        fileName=_fileName;
    }
    /**
     * 抛出异常
     */
    private static void throwInit(){
        if (mContext == null||fileName==null) {
            throw new NullPointerException("在使用该方法前，需要使用init()方法，推荐将init()放入Application中");
        }
    }

    public static void saveStringAsSP(String key, String value){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getStringFromSP(String key){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key,CommonConstants.EMPTY_STR);
    }

    public static void saveLongAsSP(String key, Long value){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).apply();
    }

    public static Long getLongFromSP(String key){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getLong(key,CommonConstants.LONG_NULL);
    }

    public static void saveIntAsSP(String key, Integer value){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static Integer getIntFromSP(String key){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key,CommonConstants.INT_NULL);
    }

    public static void saveBooleanAsSP(String key, Boolean value){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static Boolean getBooleanFromSP(String key){
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }
}
