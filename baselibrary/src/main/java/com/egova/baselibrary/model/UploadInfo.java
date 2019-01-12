package com.egova.baselibrary.model;

/**
 * 上传文件到服务器
 */
public class UploadInfo {
    private String name;//文件名称
    private String code;//文件格式类型，png
    private String value;//文件在服务器的路径

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isImage(){
        if(code!=null&&code.matches("^(JPEG|jpeg|JPG|jpg|GIF|gif|BMP|bmp|PNG|png)$")){
            return true;
        }
        return false;
    }

    public boolean isAudio(){
        if(code!=null&&code.matches("^(amr|AMR)$")){
            return true;
        }
        return false;
    }
}
