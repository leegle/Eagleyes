package com.egova.baselibrary.model;

public class ExceptionMessage extends Throwable {

    public enum ExceptionType {
        NETWORK_TIMEOUT,//网络连接报错，或者服务连接不上
        SERVER_ERROR//服务程序内部报错
    }

    private ExceptionType type;
    private String message;
    private String describe;//详细描述

    public ExceptionType getType() {
        return type;
    }

    public void setType(ExceptionType type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
