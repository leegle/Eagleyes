package com.egova.baselibrary.retrofit;

import com.egova.baselibrary.BuildConfig;
import com.egova.baselibrary.retrofit.interceptor.BaseInterceptor;
import com.egova.baselibrary.retrofit.interceptor.logging.Level;
import com.egova.baselibrary.retrofit.interceptor.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 60;

    private static RetrofitClient instance;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private String baseUrl;

    private Interceptor tokenInterceptor;

    private RetrofitClient() {
    }

    /**
     *  建议在自己的application中进行初始化网络配置
     * @param baseUrl  基础url
     * @param tokenInterceptor  token失效拦截器
     * @return
     */
    public RetrofitClient init(String baseUrl, Interceptor tokenInterceptor) {
        this.baseUrl = baseUrl;
        this.tokenInterceptor = tokenInterceptor;
        return instance;
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    private Interceptor getTokenInterceptor() {
        if (tokenInterceptor == null) {
            tokenInterceptor = new BaseInterceptor(null);
        }
        return tokenInterceptor;
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        if (retrofit == null) {
            buildRetrofit(BuildConfig.DEBUG);
        }
        return retrofit.create(service);
    }


    public void buildRetrofit(boolean isLogEnable) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(getTokenInterceptor())//添加token失效监听
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        .loggable(isLogEnable) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .addHeader("log-header", "I am the log request header.") // 添加请求头, 注意 key 和 value 都不能是中文
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }
}
