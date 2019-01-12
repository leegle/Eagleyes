package com.egova.baselibrary.application;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.egova.baselibrary.util.ActivityManagerUtil;
import com.egova.baselibrary.util.AppExecutors;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * 底层application，上层应用需继承
 */
public class EgovaApplication extends MultiDexApplication {
    private static Context context;//整个应用的上下文
    private AppExecutors mAppExecutors;//整个应用使用的线程池

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mAppExecutors = new AppExecutors();
        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
        config();
        autoSizeConfig();
//        resetFontSize();
    }


    private void autoSizeConfig() {
        AutoSizeConfig.getInstance()
                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
                .setExcludeFontScale(true);
    }

    private void resetFontSize() {
        /**
         * 避免用户自己手机字体大小不是默认大小
         */
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static Context getContext() {
        return context;
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    private void config() {
        //注册监听每个activity的生命周期,便于堆栈式管理
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityManagerUtil.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityManagerUtil.getAppManager().removeActivity(activity);
            }
        });
    }
}
