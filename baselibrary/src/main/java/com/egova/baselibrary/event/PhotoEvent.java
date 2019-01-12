package com.egova.baselibrary.event;


import org.devio.takephoto.model.TResult;

/**
 * 选择相册，拍照后发送的事件
 */
public class PhotoEvent {
    public enum photoType {
        takeSuccess, //成功
        takeFail,  //失败
        takeCancel  //取消
    }

    photoType mPhotoType;
    TResult result;
    String message;

    public PhotoEvent.photoType getPhotoType() {
        return mPhotoType;
    }

    public void setPhotoType(PhotoEvent.photoType photoType) {
        this.mPhotoType = photoType;
    }

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
