package com.egova.baselibrary.repository;

import com.egova.baselibrary.application.EgovaApplication;
import com.egova.baselibrary.model.ExceptionMessage;
import com.egova.baselibrary.retrofit.RetrofitClient;
import com.egova.baselibrary.util.ClassUtil;
import com.egova.baselibrary.util.CommonConstants;
import com.egova.baselibrary.util.NetworkUtils;
import com.egova.baselibrary.util.SharedPreferencesUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

public class BaseRepository<T> {

    protected T service;
    protected String token;

    public BaseRepository() {
        token = SharedPreferencesUtil.getStringFromSP(CommonConstants.SP_TOKEN);
        createService();
    }

    public void createService() {
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        Class<?> clazz = ClassUtil.getRawType(type);
        service = (T) RetrofitClient.getInstance().create(clazz);
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public boolean isNetWorkConnected() {
        return NetworkUtils.isConnected(EgovaApplication.getContext());
    }

    /**
     * 自动判断是网络连接错误还是服务程序报错
     *
     * @param t
     * @return
     */
    public ExceptionMessage buildException(Throwable t) {
        if (t != null && t instanceof SocketTimeoutException) {
            return buildNoNetWorkException("连接服务器超时，请重试");
        } else {
            return buildServerException("服务异常，请重试");
        }
    }

    public ExceptionMessage buildNoNetWorkException() {
        return buildNoNetWorkException("网络连接超时,请重试");
    }

    public ExceptionMessage buildNoNetWorkException(String message) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setType(ExceptionMessage.ExceptionType.NETWORK_TIMEOUT);
        exceptionMessage.setMessage(message);
        return exceptionMessage;
    }

    public ExceptionMessage buildServerException(String message) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setType(ExceptionMessage.ExceptionType.SERVER_ERROR);
        exceptionMessage.setMessage(message);
        return exceptionMessage;
    }

}
