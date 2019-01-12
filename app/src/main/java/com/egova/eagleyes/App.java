package com.egova.eagleyes;

import com.egova.baselibrary.application.EgovaApplication;
import com.egova.baselibrary.handler.UncaughtExceptionHandlerImpl;
import com.egova.baselibrary.retrofit.RetrofitClient;
import com.egova.baselibrary.util.SharedPreferencesUtil;
import com.egova.eagleyes.constants.Constants;

public class App extends EgovaApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        //文件存储初始化
        SharedPreferencesUtil.init(this, Constants.SHARED_PREFERENCE_FILE_NAME);
        //错误日志抓取初始化
        UncaughtExceptionHandlerImpl.getInstance().init(this, Constants.errorLogFolder, false, MainActivity.class);
        //网络retrofit 配置初始化
        RetrofitClient.getInstance().init(Constants.baseUrl, null).buildRetrofit(BuildConfig.DEBUG);
    }
}
