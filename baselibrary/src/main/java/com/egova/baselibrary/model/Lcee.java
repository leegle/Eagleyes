package com.egova.baselibrary.model;

import java.util.HashMap;

/**
 */

public class Lcee<T> {
    public final Status status;
    public final T data;
    public final ExceptionMessage error;
    public final HashMap<String, Loading> loadingMap;

    public Lcee(Status status, T data, ExceptionMessage error, HashMap<String, Loading> loadingMap) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.loadingMap = loadingMap;
    }

    public static <T> Lcee<T> content(T data) {
        return new Lcee<>(Status.Content, data, null, null);
    }

    public static <T> Lcee<T> error(T data, ExceptionMessage error) {
        return new Lcee<>(Status.Error, data, error, null);
    }

    public static <T> Lcee<T> error(ExceptionMessage error) {
        return error(null, error);
    }

    public static <T> Lcee<T> empty(T data) {
        return new Lcee<>(Status.Empty, data, null, null);
    }

    public static <T> Lcee<T> empty() {
        return empty(null);
    }

    public static <T> Lcee<T> loading(HashMap<String, Loading> loadingMap) {
        return new Lcee<>(Status.Loading, null, null, loadingMap);
    }

    public static <T> Lcee<T> loading() {
        return new Lcee<>(Status.Loading, null, null, null);
    }

}
