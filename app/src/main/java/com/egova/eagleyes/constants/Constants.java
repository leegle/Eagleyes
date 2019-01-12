package com.egova.eagleyes.constants;

import android.os.Environment;

import java.io.File;

public class Constants {
    //    public static final String baseUrl="http://192.168.101.164:8092";
    //    public static final String baseUrl="http://192.168.1.102:8095";
    //数据接口
    public static final String baseUrl="http://192.168.101.21:8018";
    //存放文件目录
    public static final String folderName = Environment.getExternalStorageDirectory() + File.separator + "eagleEyes" + File.separator;
    //图片目录
    public static final String imageFolder = folderName + "Images" + File.separator;
    //错误日志文件目录
    public static final String errorLogFolder = folderName + "ErrorLog" + File.separator;
    //sharedPreference 文件存储名称
    public static final String SHARED_PREFERENCE_FILE_NAME = "eagleEyes";

    //activity跳转时bundle的key 键
    public static final String BUNDLE_KEY="bundle_key";//通用
    public static final String BUNDLE_TYPE="bundle_type";//
    public static final String BUNDLE_USER="bundle_user";//用户信息
    public static final String BUNDLE_VEHICLE_QUERY="bundle_vehicle_query";//搜索条件
    public static final String BUNDLE_VEHICLE_LIST="bundle_vehicle_list";//车辆列表
    public static final String BUNDLE_VEHICLE_SIZE="bundle_vehicle_size";//识别结果数量
    public static final String BUNDLE_VEHICLE="bundle_vehicle";//车辆信息

    public static final  String BUNDLE_REGION="bundle_region";//区域

    public static final String BUNDLE_DISPOSITION="bundle_disposition";//布控信息
    public static final String BUNDLE_QUERY_MODEL="bundle_query_model";


}
