package com.egova.baselibrary.model;

public class Loading {
    public String key;
    public long currentLength;//当前已加载大小
    public long totalLength;//总大小

    public Loading(String key, long currentLength, long totalLength) {
        this.key = key;
        this.currentLength = currentLength;
        this.totalLength = totalLength;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }
}
